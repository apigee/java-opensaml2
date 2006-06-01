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
import org.opensaml.common.binding.CommunicationException;
import org.opensaml.common.binding.client.AbstractBindingClient;
import org.opensaml.common.binding.client.SynchronousClient;
import org.opensaml.common.xml.ParserPoolManager;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.XMLParserException;
import org.opensaml.xml.util.DatatypeHelper;
import org.w3c.dom.Document;

/**
 * A synchronous implementation of the Liberty PAOS protocol.
 */
public class PAOSSynchronousClient extends AbstractBindingClient<Object, XMLObject> implements SynchronousClient<Object, XMLObject> {
    
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

    /**
     * Constructor
     *
     * @param serviceURL the URL of the service to contact
     * 
     * @throws IllegalArgumentException thrown if the service URL is null or empty
     */
    public PAOSSynchronousClient(String serviceURL)throws IllegalArgumentException{
        super();
        this.serviceURL = DatatypeHelper.safeTrimOrNullString(serviceURL);
        if(serviceURL == null){
            throw new IllegalArgumentException("ServiceURL may not be null or empty");
        }
        services = new FastList<String>();
    }
    
    /**
     * Gets the extension component of the PAOS HTTP header.
     * 
     * @return the extension component of the PAOS HTTP header
     */
    public String getExtension(){
        return extension;
    }
    
    /**
     * Sets the extension component of the PAOS HTTP header.
     * 
     * @param newExtension the extension component of the PAOS HTTP header
     * 
     * @throws IllegalArgumentException thrown if the extension string contains a semicolon (";")
     */
    public void setExtension(String newExtension) throws IllegalArgumentException{
        if(newExtension.indexOf(";") != -1){
            throw new IllegalArgumentException("Extension string may not contain a semicolon (\";\")");
        }
        extension = DatatypeHelper.safeTrimOrNullString(newExtension);
    }
    
    /**
     * Gets the list of services support by this PAOS endpoint including any options supported by each service.
     * 
     * @return the list of services support by this PAOS endpoint
     */
    public List<String> getServices(){
        return services;
    }
    
    /**
     * A convience method for sending the request.  This is equivalent to calling {@link #sendRequest(Object)} with any object.
     * 
     * @return the SOAP envelope from the server
     */
    public XMLObject sendRequest() throws CommunicationException, UnmarshallingException{
        HttpClient httpClient = new HttpClient();
        
        // Configure the GET request with the appropriate headers
        GetMethod getRequest = new GetMethod(serviceURL);
        getRequest.addRequestHeader(PAOS_HEADER_NAME, constructPAOSHeader());
        getRequest.addRequestHeader(ACCEPT_HEADER_NAME, PAOS_MEDIA_TYPE);
        
        try{
            httpClient.executeMethod(getRequest);
            InputStream responseStream = getRequest.getResponseBodyAsStream();
            Document responseDoc = ParserPoolManager.getInstance().parse(responseStream);
            Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(responseDoc.getDocumentElement());
            return unmarshaller.unmarshall(responseDoc.getDocumentElement());
        }catch(HttpException e){
            throw new CommunicationException(e);
        }catch(IOException e){
            throw new CommunicationException(e);
        }catch(XMLParserException e){
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
    public XMLObject sendRequest(Object request) throws CommunicationException, UnmarshallingException {
        return sendRequest();
    }
    
    /**
     * Constructs the PAOS HTTP header value.
     * 
     * @return the PAOS HTTP header value
     */
    private String constructPAOSHeader(){
        TextBuilder textBuilder = new TextBuilder();
        textBuilder.append("ver=\"");
        textBuilder.append(PAOS_VERSION);
        textBuilder.append("\"");
        
        if(extension != null){
            textBuilder.append(", ext=\"");
            textBuilder.append(extension);
            textBuilder.append("\"");
        }
        
        if(services.size() > 0){
            String service;
            Node<String> lastNode = services.tail();
            for(Node<String> currentNode = services.head(); currentNode != lastNode; currentNode = currentNode.getNext()){
                service = currentNode.getValue();
                textBuilder.append("; \"");
                textBuilder.append(service);
                textBuilder.append("\"");
                if(currentNode != lastNode){
                    textBuilder.append(", ");
                }
            }
        }
        
        return textBuilder.toString();
    }
}