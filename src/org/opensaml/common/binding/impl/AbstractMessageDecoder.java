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

package org.opensaml.common.binding.impl;

import java.io.InputStream;

import javax.servlet.ServletRequest;

import org.apache.log4j.Logger;
import org.opensaml.Configuration;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.MessageDecoder;
import org.opensaml.common.binding.SecurityPolicy;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.ws.security.SecurityPolicyException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.parse.XMLParserException;
import org.opensaml.xml.security.TrustEngine;
import org.w3c.dom.Document;

/**
 * Base class for message decoder handling much of the boilerplate code.
 *
 * @param <RequestType> request type that will be decoded
 */
public abstract class AbstractMessageDecoder<RequestType extends ServletRequest> 
        implements MessageDecoder<RequestType> {
    
    /** Class logger. */
    private static Logger log = Logger.getLogger(AbstractHTTPMessageDecoder.class);

    /** Pool of parsers used to parse XML messages. */
    private ParserPool parser;
    
    /** Metadata provider used to lookup information about the issuer. */
    private MetadataProvider metadataProvider;
    
    /** Request to decode. */
    private RequestType request;
    
    /** Decoded SAML message. */
    private SAMLObject message;
    
    /** Security policy to apply to the request and payload. */
    private SecurityPolicy securityPolicy;
    
    /** Trust engine used to validate request credentials. */
    private TrustEngine trustEngine;
    
    /**
     * Gets the pool of parsers to use to parse XML.
     * 
     * @return pool of parsers to use to parse XML
     */
    public ParserPool getParserPool(){
        return parser;
    }
    
    /**
     * Sets the pool of parsers to use to parse XML.
     * 
     * @param pool pool of parsers to use to parse XML
     */
    public void setParserPool(ParserPool pool){
        parser = pool;
    }

    /** {@inheritDoc} */
    public MetadataProvider getMetadataProvider() {
        return metadataProvider;
    }

    /** {@inheritDoc} */
    public RequestType getRequest() {
        return request;
    }

    /** {@inheritDoc} */
    public SAMLObject getSAMLMessage() {
        return message;
    }

    /** {@inheritDoc} */
    public SecurityPolicy getSecurityPolicy() {
        return securityPolicy;
    }

    /** {@inheritDoc} */
    public TrustEngine getTrustEngine() {
        return trustEngine;
    }

    /** {@inheritDoc} */
    public void setMetadataProvider(MetadataProvider newProvider) {
        metadataProvider = newProvider;
    }

    /** {@inheritDoc} */
    public void setRequest(RequestType newRequest) {
        request = newRequest;
    }
    
    /**
     * Sets the decoded SAML message.
     * 
     * @param newMessage decoded SAML message
     */
    protected void setSAMLMessage(SAMLObject newMessage){
        message = newMessage;
    }

    /** {@inheritDoc} */
    public void setSecurityPolicy(SecurityPolicy policy) {
        securityPolicy = policy;
    }

    /** {@inheritDoc} */
    public void setTrustEngine(TrustEngine newEngine) {
        trustEngine = newEngine;
    }
    
    /** {@inheritDoc} */
    public abstract void decode() throws BindingException;


    /**
     * Parses the incoming message into a DOM and then unmarshalls it into a SAMLObject.
     * 
     * @param samlMessage message to unmarshall
     * 
     * @return SAMLObject representation of the message
     * 
     * @throws BindingException thrown if the incoming XML can not be parsed and unmarshalled
     */
    protected XMLObject unmarshallMessage(InputStream samlMessage) throws BindingException {
        if(log.isDebugEnabled()){
            log.debug("Unmarshalling message");
        }
        
        try {
            if (log.isDebugEnabled()) {
                log.debug("Parsing message XML into a DOM");
            }
            Document domMessage = parser.parse(samlMessage);
            
            if(log.isDebugEnabled()){
                log.debug("Unmarshalling DOM");
            }
            Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(
                    domMessage.getDocumentElement());
            return unmarshaller.unmarshall(domMessage.getDocumentElement());
        } catch (XMLParserException e) {
            log.error("Unable to parse message XML", e);
            throw new BindingException("Unable to parse message XML", e);
        } catch (UnmarshallingException e) {
            log.error("Unable to unmarshall message DOM", e);
            throw new BindingException("Unable to unmarshaller message DOM", e);
        }
    }

    /**
     * Evaluates the registered security policy, if there is one, against the provided request and message.
     * 
     * This method will also set the issuer and issuer role metadata if provided by the operating security rules.
     * 
     * @param message message to evaluate the policy against
     * 
     * @throws BindingException thrown if the given request/message do not meet the requirements of the security policy
     */
    protected void evaluateSecurityPolicy(XMLObject message) throws BindingException {
        if(log.isDebugEnabled()){
            log.debug("Evaluating request and SAML message against security policy");
        }
        
        SecurityPolicy policy = getSecurityPolicy();
        if(policy != null){
            try {
                policy.evaluate(getRequest(), message);
            } catch (SecurityPolicyException e) {
                log.error("Security policy exception thrown during message decoding", e);
                throw new BindingException("Message decoding failed security policy evaluation", e);
            }
        }
    }
}