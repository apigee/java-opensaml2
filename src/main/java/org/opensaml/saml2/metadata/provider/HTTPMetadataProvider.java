/*
 * Copyright [2006] [University Corporation for Advanced Internet Development, Inc.]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.saml2.metadata.provider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.joda.time.DateTime;
import org.opensaml.saml2.common.SAML2Helper;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.UnmarshallingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

/**
 * A metadata provider that pulls metadata using an HTTP GET. Metadata is cached until one of these criteria is met:
 * <ul>
 * <li>The smallest cacheDuration within the metadata is exceeded</li>
 * <li>The earliest validUntil time within the metadata is exceeded</li>
 * <li>The maximum cache duration is exceeded</li>
 * </ul>
 * 
 * Metadata is filtered prior to determining the cache expiration data. This allows a filter to remove XMLObjects that
 * may effect the cache duration but for which the user of this provider does not care about.
 * 
 * It is the responsibility of the caller to re-initialize, via {@link #initialize()}, if any properties of this
 * provider are changed.
 */
public class HTTPMetadataProvider extends AbstractObservableMetadataProvider {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(HTTPMetadataProvider.class);

    /** URL to the Metadata. */
    private URI metadataURI;

    /** Cached, filtered, unmarshalled metadata. */
    private XMLObject cachedMetadata;

    /** The ETag provided when the currently cached metadata was fetched. */
    private String cachedMetadataETag;

    /** The Last-Modified information provided when the currently cached metadata was fetched. */
    private String cachedMetadataLastModified;

    /** Whether cached metadata should be discarded if it expires and can't be refreshed. */
    private boolean maintainExpiredMetadata;

    /** HTTP Client used to pull the metadata. */
    private HttpClient httpClient;

    /** URL scope that requires authentication. */
    private AuthScope authScope;

    /** Maximum amount of time to keep metadata cached. */
    private int maxCacheDuration;

    /** When the cached metadata becomes stale. */
    private DateTime mdExpirationTime;

    /**
     * Constructor.
     * 
     * @param metadataURL the URL to fetch the metadata
     * @param requestTimeout the time, in milliseconds, to wait for the metadata server to respond
     * 
     * @throws MetadataProviderException thrown if the URL is not a valid URL or the metadata can not be retrieved from
     *             the URL
     */
    public HTTPMetadataProvider(String metadataURL, int requestTimeout) throws MetadataProviderException {
        super();
        try {
            metadataURI = new URI(metadataURL);
            maintainExpiredMetadata = true;

            HttpClientParams clientParams = new HttpClientParams();
            clientParams.setSoTimeout(requestTimeout);
            httpClient = new HttpClient(clientParams);
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(requestTimeout);
            authScope = new AuthScope(metadataURI.getHost(), metadataURI.getPort());

            // 24 hours
            maxCacheDuration = 60 * 60 * 24;
        } catch (URISyntaxException e) {
            throw new MetadataProviderException("Illegal URL syntax", e);
        }
    }

    /**
     * Initializes the provider and prepares it for use.
     * 
     * @throws MetadataProviderException thrown if there is a problem fetching, parsing, or processing the metadata
     */
    public void initialize() throws MetadataProviderException {
        refreshMetadata();
    }

    /**
     * Gets the URL to fetch the metadata.
     * 
     * @return the URL to fetch the metadata
     */
    public String getMetadataURI() {
        return metadataURI.toASCIIString();
    }

    /**
     * Gets whether cached metadata should be discarded if it expires and can not be refreshed.
     * 
     * @return whether cached metadata should be discarded if it expires and can not be refreshed
     */
    public boolean maintainExpiredMetadata() {
        return maintainExpiredMetadata;
    }

    /**
     * Sets whether cached metadata should be discarded if it expires and can not be refreshed.
     * 
     * @param maintain whether cached metadata should be discarded if it expires and can not be refreshed
     */
    public void setMaintainExpiredMetadata(boolean maintain) {
        maintainExpiredMetadata = maintain;
    }

    /**
     * Sets the username and password used to access the metadata URL. To disable BASIC authentication set the username
     * and password to null;
     * 
     * @param username the username
     * @param password the password
     */
    public void setBasicCredentials(String username, String password) {
        if (username == null && password == null) {
            httpClient.getState().setCredentials(null, null);
        } else {
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
            httpClient.getState().setCredentials(authScope, credentials);
        }
    }

    /**
     * Gets the length of time in milliseconds to wait for the server to respond.
     * 
     * @return length of time in milliseconds to wait for the server to respond
     */
    public int getRequestTimeout() {
        return httpClient.getParams().getSoTimeout();
    }

    /**
     * Sets the socket factory used to create sockets to the HTTP server.
     * 
     * @see <a href="http://jakarta.apache.org/commons/httpclient/sslguide.html">HTTPClient SSL guide</a>
     * 
     * @param newSocketFactory the socket factory used to produce sockets used to connect to the server
     */
    public void setSocketFactory(ProtocolSocketFactory newSocketFactory) {
        log.debug("Using the custom socket factory {} to connect to the HTTP server", newSocketFactory.getClass()
                .getName());
        Protocol protocol = new Protocol(metadataURI.getScheme(), newSocketFactory, metadataURI.getPort());
        httpClient.getHostConfiguration().setHost(metadataURI.getHost(), metadataURI.getPort(), protocol);
    }

    /**
     * Gets the maximum amount of time, in seconds, metadata will be cached for.
     * 
     * @return the maximum amount of time metadata will be cached for
     */
    public int getMaxCacheDuration() {
        return maxCacheDuration;
    }

    /**
     * Sets the maximum amount of time, in seconds, metadata will be cached for.
     * 
     * @param newDuration the maximum amount of time metadata will be cached for
     */
    public void setMaxCacheDuration(int newDuration) {
        maxCacheDuration = newDuration;
    }

    /** {@inheritDoc} */
    public XMLObject getMetadata() throws MetadataProviderException {
        if (mdExpirationTime.isBeforeNow()) {
            log.debug("Cached metadata is stale, refreshing");
            refreshMetadata();
        }

        return cachedMetadata;
    }

    /**
     * Caches the metadata.
     * 
     * @param metadata metadata to cache
     */
    protected void cacheMetadata(XMLObject metadata) {
        cachedMetadata = metadata;
    }

    /**
     * Refreshes the metadata cache. Metadata is fetched from the URL through an HTTP get, unmarshalled, and then
     * filtered. This method also clears out the entity ID to entity descriptor cache.
     * 
     * @throws MetadataProviderException thrown if the metadata can not be read, unmarshalled, and filtered
     */
    protected synchronized void refreshMetadata() throws MetadataProviderException {
         if (mdExpirationTime != null && !mdExpirationTime.isBeforeNow()) {
             // In case other requests stacked up behind the synchronize lock
             return;
         }

        log.debug("Refreshing cache of metadata from URL {}, max cache duration set to {} seconds", metadataURI,
                maxCacheDuration);
        try {
            byte[] metadataBytes = getMetadataFromUrl();
            if (metadataBytes == null) {
                return;
            }
            
            log.debug("Unmarshalling metadata retrieved from '{}'", metadataURI);
            XMLObject metadata = unmarshallMetadata(new ByteArrayInputStream(metadataBytes));
            log.debug("Unmarshalled metadata from '{}'", metadataURI);
                

            log.debug("Calculating expiration time");
            DateTime now = new DateTime();
            mdExpirationTime = SAML2Helper.getEarliestExpiration(metadata, now.plus(maxCacheDuration * 1000), now);
            log.debug("Metadata cache expires on " + mdExpirationTime);

            if (mdExpirationTime != null && !maintainExpiredMetadata() && mdExpirationTime.isBeforeNow()) {
                cachedMetadata = null;
            } else {
                filterMetadata(metadata);
                releaseMetadataDOM(metadata);
                cacheMetadata(metadataBytes, metadata);
            }

            emitChangeEvent();
        } catch (IOException e) {
            String errorMsg = "Unable to read metadata from server";
            log.error(errorMsg, e);
            throw new MetadataProviderException(errorMsg, e);
        } catch (UnmarshallingException e) {
            String errorMsg = "Unable to unmarshall metadata";
            log.error(errorMsg, e);
            throw new MetadataProviderException(errorMsg, e);
        } catch (FilterException e) {
            String errorMsg = "Unable to filter metadata";
            log.error(errorMsg, e);
            throw new MetadataProviderException(errorMsg, e);
        }
    }

    /**
     * Fetches the metadata from the remote server and unmarshalls it.
     * 
     * @return the unmarshalled metadata, or null if the metadata has not changed since its last retrieval
     * 
     * @throws IOException thrown if the metadata can not be fetched from the remote server
     * @throws UnmarshallingException thrown if the metadata can not be unmarshalled
     */
    protected XMLObject fetchMetadata() throws IOException, UnmarshallingException {
        byte[] metadtaBytes = getMetadataFromUrl();
        if (metadtaBytes != null) {
            log.debug("Unmarshalling metadata retrieved from '{}'", metadataURI);
            XMLObject metadata = unmarshallMetadata(new ByteArrayInputStream(metadtaBytes));
            log.debug("Unmarshalled metadata from '{}'", metadataURI);
            return metadata;
        }

        return null;
    }

    /**
     * Gets the metadata document from the remote server.
     * 
     * @return the metadata from remote server, or null if the metadata document has not changed since the last
     *         retrieval
     * 
     * @throws IOException thrown if there is a problem contacting the remote server
     */
    protected byte[] getMetadataFromUrl() throws IOException {
        log.debug("Attempting to fetch metadata document from '{}'", metadataURI);
        GetMethod getMethod = buildGetMethod();

        log.debug("Fetching metadata document from '{}'", metadataURI);
        httpClient.executeMethod(getMethod);
        int httpStatus = getMethod.getStatusCode();

        if (httpStatus == HttpStatus.SC_NOT_MODIFIED) {
            log.debug("Metadata document from '{}' has not changed since last retrieval", getMetadataURI());
            return null;
        }

        if (getMethod.getStatusCode() != HttpStatus.SC_OK) {
            String errMsg = MessageFormatter.format(
                    "Non-ok status code '{}' returned from remote metadata source '{}'", getMethod.getStatusCode(),
                    metadataURI);
            log.error(errMsg);
            throw new IOException(errMsg);
        }

        Header httpHeader = getMethod.getResponseHeader("ETag");
        if (httpHeader != null) {
            cachedMetadataETag = httpHeader.getValue();
        }

        httpHeader = getMethod.getResponseHeader("Last-Modified");
        if (httpHeader != null) {
            cachedMetadataLastModified = httpHeader.getValue();
        }
        InputStream ins = getMethod.getResponseBodyAsStream();

        httpHeader = getMethod.getResponseHeader("Content-Encoding");
        if (httpHeader != null) {
            String contentEncoding = httpHeader.getValue();
            if ("deflate".equalsIgnoreCase(contentEncoding)) {
                log.debug("Metadata document from '{}' was deflate compressed, decompressing it", metadataURI);
                ins = new InflaterInputStream(ins);
            }

            if ("gzip".equalsIgnoreCase(contentEncoding)) {
                log.debug("Metadata document from '{}' was GZip compressed, decompressing it", metadataURI);
                ins = new GZIPInputStream(ins);
            }
        }

        log.debug("Retrieved metadata document from '{}'", metadataURI);
        return inputstreamToByteArray(ins);
    }

    /**
     * Builds the HTTP GET method used to fetch the metadata. The returned method advertises support for GZIP and
     * deflate compression, enables conditional GETs if the cached metadata came with either an ETag or Last-Modified
     * information, and sets up basic authentication if such is configured.
     * 
     * @return the constructed GET method
     */
    protected GetMethod buildGetMethod() {
        GetMethod getMethod = new GetMethod(getMetadataURI());

        getMethod.setRequestHeader("Accept-Encoding", "gzip,deflate");
        if (cachedMetadataETag != null) {
            getMethod.setRequestHeader("If-None-Match", cachedMetadataETag);
        }
        if (cachedMetadataLastModified != null) {
            getMethod.setRequestHeader("If-Modified-Since", cachedMetadataLastModified);
        }

        if (httpClient.getState().getCredentials(authScope) != null) {
            log.debug("Using BASIC authentication when retrieving metadata from '{}", metadataURI);
            getMethod.setDoAuthentication(true);
        }

        return getMethod;
    }
    
    /**
     * Converts an InputStream into a byte array.
     * 
     * @param ins input stream to convert
     * 
     * @return resultant byte array
     * 
     * @throws IOException thrown if there is a problem reading the resultant byte array
     */
    private byte[] inputstreamToByteArray(InputStream ins) throws IOException {
        // 1 MB read buffer
        byte[] buffer = new byte[1024*1024];
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        
        long count = 0;
        int n = 0;
        while (-1 != (n = ins.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }

        ins.close();
        return output.toByteArray();
    }
    
    protected void cacheMetadata(byte[] metadataBytes, XMLObject metadata) throws MetadataProviderException {
        cachedMetadata = metadata;
    }
}