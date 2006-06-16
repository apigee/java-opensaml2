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

package org.opensaml.common.transport.http.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;
import org.opensaml.common.transport.http.GetRequest;
import org.opensaml.common.transport.http.HTTPRequest;
import org.opensaml.common.transport.http.HTTPResponse;
import org.opensaml.common.transport.http.HTTPTransport;
import org.opensaml.common.transport.http.POSTRequest;

/**
 * An {@link org.opensaml.common.transport.http.HTTPTransport} that uses the Apache Jarkart Commons HttpClient libary in
 * order to establish HTTP connections. This transport is thread-safe and may be used by multiple threads to send
 * messages to the same location. Note, however, that changing transport properties will effect all subsequent
 * transimission, in all threads using a particular instance.
 */
public class CommonsHTTPTransport implements HTTPTransport {
    
    /** The HTTP client */
    private HttpClient httpClient;

    /** The endpoint this transport connects to */
    private String endpointURL;
    
    /** The port on the endpoint to connect to */
    private int port;

    /** Socket factory used during SSL requests */
    private SSLProtocolSocketFactory socketyFactory;

    /** HTTP version to use */
    private HTTPVersion httpVerion = HTTPVersion.HTTP11;

    /** Whether to keep connections live between requests */
    private boolean keepAlive = false;

    /** Whether to follow redirects */
    private boolean followRedirects = false;

    /** Timeout for requests */
    private long timeout = 30 * 1000;

    /**
     * Constructor
     * 
     * @param endpointURL the endpoint this transport will send messages to
     */
    public CommonsHTTPTransport(String endpointURL) {
        this(endpointURL, 80, null);
    }

    /**
     * Constructor
     * 
     * @param endpointURL the endpoint this transport will send messages to
     * @param socketFactory the sockety factory to use for SSL connections
     */
    public CommonsHTTPTransport(String endpointURL, int port, SSLProtocolSocketFactory socketFactory) {
        this.endpointURL = endpointURL;
        this.port = port;
        this.socketyFactory = socketFactory;
    }

    /** {@inheritDoc} */
    public HTTPVersion getHTTPVersion() {
        return httpVerion;
    }

    /**
     * Sets the HTTP version used by this transport.
     * 
     * @param version the HTTP version used by this transport
     */
    public void setHTTPVersion(HTTPVersion version) {
        httpVerion = version;
    }

    /** {@inheritDoc} */
    public boolean keepConnectionAlive() {
        return keepAlive;
    }

    /**
     * Sets whether to keep connections alive between requests.
     * 
     * @param keepAlive whether to keep connections alive between requests
     */
    public void setKeepConnectionAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    /** {@inheritDoc} */
    public boolean followRedirects() {
        return followRedirects;
    }

    /**
     * Sets whether this transport should follow HTTP redirects.
     * 
     * @param followRedirects whether this transport should follow HTTP redirects
     */
    public void setFollowRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
    }

    /** {@inheritDoc} */
    public String getLocationURI() {
        return endpointURL;
    }

    /** {@inheritDoc} */
    public long getConnectionTimeout() {
        return timeout;
    }

    /**
     * Sets the length of time to wait, in milliseconds, for the other party to respond when a request is made.
     * 
     * @param millisecondsTimeout the length of time to wait, in milliseconds, for the other party to respond when a
     *            request is made
     */
    public void setConnectionTimeout(long millisecondsTimeout) {
        timeout = millisecondsTimeout;
    }
    

    /** {@inheritDoc} */
    public void connect() throws IOException {
        // TODO Auto-generated method stub
    }

    /** {@inheritDoc} */
    public void disconnect() {
        // TODO Auto-generated method stub
    }

    /** {@inheritDoc} */
    public boolean isConnected() {
        // TODO Auto-generated method stub
        return false;
    }

    /** {@inheritDoc} */
    public HTTPResponse sendRequest(HTTPRequest request) {
        if(request instanceof GetRequest){
            return doGet((GetRequest) request);
        }else if(request instanceof POSTRequest){
            return doPost((POSTRequest) request);
        }else{
            // error
            return null;
        }
    }
    
    /**
     * Processes a GET request.
     * 
     * @param request the GET request
     * 
     * @return the response
     */
    protected HTTPResponse doGet(GetRequest request){
        GetMethod requestMethod = new GetMethod();
        requestMethod.setFollowRedirects(followRedirects);
        //TODO
        return null;
    }
    
    /**
     * Processes a POST request
     * 
     * @param request the POST request
     * 
     * @return the response
     */
    protected HTTPResponse doPost(POSTRequest request){
        PostMethod requestMethod = new PostMethod();
        requestMethod.setFollowRedirects(followRedirects);
//      TODO
        return null;
    }
    
    /**
     * Adds HTTP request headers to the method.
     * 
     * @param requestMethod the request method
     * 
     * @param headers headers to add to the method
     */
    protected void addHeaders(HttpMethodBase requestMethod, Map<String, List<String>> headers){
        String headerName;
        String headerValue;
        
        Iterator<String> names = headers.keySet().iterator();
        Iterator<String> values;
        while(names.hasNext()){
            headerName = names.next();
            
            values = headers.get(headerName).iterator();
            while(values.hasNext()){
                headerValue = values.next();
                requestMethod.addRequestHeader(new Header(headerName, headerValue));
            }
        }
    }
    
    protected void reconfigureHTTPClient(){
        if(socketyFactory != null){
            Protocol protocolHander = new Protocol("https", socketyFactory, port);
            httpClient.getHostConfiguration().setHost(endpointURL, port, protocolHander);
        }
        
        httpClient.getParams().setConnectionManagerTimeout(timeout);
        
        if(httpVerion == HTTPVersion.HTTP10){
            httpClient.getParams().setVersion(HttpVersion.HTTP_1_0);
        }else{
            httpClient.getParams().setVersion(HttpVersion.HTTP_1_1);
        }
    }
}