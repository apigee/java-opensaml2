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

package org.opensaml.saml2.binding.PAOS;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javolution.lang.TextBuilder;
import javolution.util.FastList;
import javolution.util.FastList.Node;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;
import org.opensaml.common.binding.CommunicationException;
import org.opensaml.common.binding.MessageFilterException;
import org.opensaml.common.binding.client.AbstractBindingClient;
import org.opensaml.common.binding.client.SynchronousClient;
import org.opensaml.common.xml.ParserPoolManager;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.XMLParserException;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A synchronous implementation of the Liberty PAOS protocol. Outgoing message filters will be applied only to the
 * message sent by way of {@link #sendRequest(XMLObject)}, incoming message filters will not be applied.
 */
public class PAOSSynchronousClient extends AbstractBindingClient<XMLObject, InputStream> implements
        SynchronousClient<XMLObject, InputStream> {

    /** Logger */
    private final Logger log = Logger.getLogger(PAOSSynchronousClient.class);

    /** HTTP Accept header name */
    public final static String ACCEPT_HEADER_NAME = "Accept";

    /** Media type used with PAOS */
    public final static String PAOS_MEDIA_TYPE = "application/vnd.paos+xml";

    /** PAOS HTTP header name */
    public final static String PAOS_HEADER_NAME = "PAOS";

    /** PAOS HTTP header value */
    public final static String PAOS_VERSION = "urn:liberty:paos:2003-08";

    /** URL of the service to contact */
    private String serviceURL;

    /** Extension identifier used in the PAOS header */
    private String extension;

    /** List of service URI supported by this PAOS client */
    private FastList<String> services;

    /** Value of the PAOS header used during first part of request */
    private String paosHeaderValue;

    /** Whether a request has been made before. Another requests would signify a Request-Response MEP */
    private boolean previousRequestMade;

    /** HttpClient used to make HTTP requests */
    private HttpClient httpClient;

    /** The SOAP envelope returned from the previous request */
    private XMLObject previousResponse;

    /**
     * Constructor
     * 
     * @param serviceURL the URL of the service to contact
     * @param extension the extensions identifier for this PAOS client, may be null
     * @param services the services, with options, for this PAOS client, may be null or empty
     * 
     * @throws IllegalArgumentException thrown if the service URL is null or empty, or the extensions string contains a
     *             semi-colon
     */
    public PAOSSynchronousClient(String serviceURL, String extension, List<String> services)
            throws IllegalArgumentException {
        super();
        this.serviceURL = DatatypeHelper.safeTrimOrNullString(serviceURL);
        if (serviceURL == null) {
            throw new IllegalArgumentException("ServiceURL may not be null or empty");
        }

        setExtension(extension);
        services = new FastList<String>(services);
        constructPAOSHeader();
        previousRequestMade = false;
        httpClient = new HttpClient();
    }

    /**
     * Gets the extension component of the PAOS HTTP header.
     * 
     * @return the extension component of the PAOS HTTP header
     */
    public String getExtension() throws IllegalStateException {
        return extension;
    }

    /**
     * Sets the extension component of the PAOS HTTP header.
     * 
     * @param newExtension the extension component of the PAOS HTTP header
     * 
     * @throws IllegalArgumentException thrown if the extension string contains a semicolon (";")
     */
    private void setExtension(String newExtension) throws IllegalArgumentException, IllegalStateException {
        if (newExtension.indexOf(";") != -1) {
            throw new IllegalArgumentException("Extension string may not contain a semicolon (\";\")");
        }
        extension = DatatypeHelper.safeTrimOrNullString(newExtension);
    }

    /**
     * Gets an immutable list of services support by this PAOS endpoint including any options supported by each service.
     * 
     * @return the list of services support by this PAOS endpoint
     */
    public List<String> getServices() {
        return services.unmodifiable();
    }

    /**
     * Gets the response from the last <code>sendRequest</code> invocation.
     * 
     * @return the response from the last <code>sendRequest</code> invocation
     */
    public XMLObject getResponse() {
        return previousResponse;
    }

    /**
     * A convience method for sending the initial PAOS request. This is equivalent to calling
     * {@link #sendRequest(Object)} with any object.
     * 
     * @return the SOAP envelope from the server
     */
    public XMLObject sendRequest() throws CommunicationException, UnmarshallingException, MessageFilterException {
        if (log.isDebugEnabled()) {
            log.debug("Preparing PAOS request to service " + serviceURL);
        }

        // Configure the GET request with the appropriate headers
        GetMethod getRequest = new GetMethod(serviceURL);

        if (log.isDebugEnabled()) {
            log.debug("Adding PAOS header with value " + paosHeaderValue);
        }
        getRequest.addRequestHeader(PAOS_HEADER_NAME, paosHeaderValue);

        if (log.isDebugEnabled()) {
            log.debug("Adding Accept header with value " + PAOS_MEDIA_TYPE);
        }
        getRequest.addRequestHeader(ACCEPT_HEADER_NAME, PAOS_MEDIA_TYPE);

        try {
            if (log.isDebugEnabled()) {
                log.debug("Making request to service");
            }
            httpClient.executeMethod(getRequest);

            if (log.isDebugEnabled()) {
                log.debug("Unmarshalling DOM in a SOAP envelope");
            }
            Document responseDoc = ParserPoolManager.getInstance().parse(getRequest.getResponseBodyAsStream());
            Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(
                    responseDoc.getDocumentElement());
            XMLObject soapEnv = unmarshaller.unmarshall(responseDoc.getDocumentElement());

            previousResponse = soapEnv;
            return soapEnv;
        } catch (HttpException e) {
            throw new CommunicationException(e);
        } catch (IOException e) {
            throw new CommunicationException(e);
        } catch (XMLParserException e) {
            throw new CommunicationException(e);
        }
    }

    /**
     * Send the PAOS request to the server.
     * 
     * @param request may be anything, the request is ignored
     * 
     * @return the SOAP envelope from the server
     */
    public InputStream sendRequest(XMLObject request) throws CommunicationException, MarshallingException,
            UnmarshallingException, MessageFilterException, IllegalStateException {
        if (!previousRequestMade) {
            throw new IllegalStateException(
                    "sendRequest() must be invoked prior to this method in order to start the session with PAOS peer");
        } else {

            applyOutgoingFilters(request);

            Marshaller marshaller = Configuration.getMarshallerFactory().getMarshaller(request);
            Element soapEnvelope = marshaller.marshall(request);

            // TODO Get the responseConsumerURL from the previous response and use it as the arg to PostMethod(String);
            PostMethod postRequest = new PostMethod("FOO");
            if (log.isDebugEnabled()) {
                log.debug("Adding PAOS header with value " + paosHeaderValue);
            }
            postRequest.addRequestHeader(PAOS_HEADER_NAME, paosHeaderValue);

            if (log.isDebugEnabled()) {
                log.debug("Adding Accept header with value " + PAOS_MEDIA_TYPE);
            }
            postRequest.addRequestHeader(ACCEPT_HEADER_NAME, PAOS_MEDIA_TYPE);

            // TODO Need to set Content-type as well, check to see if there is a special method for it

            StringRequestEntity requestContent = new StringRequestEntity(XMLHelper.nodeToString(soapEnvelope));
            postRequest.setRequestEntity(requestContent);

            try {
                httpClient.executeMethod(postRequest);
                return postRequest.getResponseBodyAsStream();
            } catch (HttpException e) {
                throw new CommunicationException(e);
            } catch (IOException e) {
                throw new CommunicationException(e);
            }
        }
    }

    /**
     * Constructs the PAOS HTTP header value.
     * 
     * @return the PAOS HTTP header value
     */
    private void constructPAOSHeader() {
        if (log.isDebugEnabled()) {
            log.debug("Constructing PAOS header value");
        }
        TextBuilder textBuilder = new TextBuilder();
        textBuilder.append("ver=\"");
        textBuilder.append(PAOS_VERSION);
        textBuilder.append("\"");

        if (extension != null) {
            textBuilder.append(", ext=\"");
            textBuilder.append(extension);
            textBuilder.append("\"");
        }

        if (services.size() > 0) {
            String service;
            Node<String> lastNode = services.tail();
            for (Node<String> currentNode = services.head(); currentNode != lastNode; currentNode = currentNode
                    .getNext()) {
                service = currentNode.getValue();
                textBuilder.append("; \"");
                textBuilder.append(service);
                textBuilder.append("\"");
                if (currentNode != lastNode) {
                    textBuilder.append(", ");
                }
            }
        }

        paosHeaderValue = textBuilder.toString();
    }
}