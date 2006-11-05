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

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.opensaml.Configuration;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.HTTPMessageDecoder;
import org.opensaml.common.binding.SecurityPolicy;
import org.opensaml.common.xml.ParserPoolManager;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.XMLParserException;
import org.w3c.dom.Document;

/**
 * Base class for HTTP message decoders handling much of boilerplate code.
 */
public abstract class AbstractHTTPMessageDecoder extends AbstractMessageDecoder<HttpServletRequest> implements HTTPMessageDecoder {
    
    /** Class logger */
    public final static Logger log = Logger.getLogger(AbstractHTTPMessageDecoder.class);

    /** HTTP method used in the request */
    protected String httpMethod;
    
    /** Request relay state */
    protected String relayState;    
    
    /** {@inheritDoc} */
    public String getMethod(){
        return httpMethod;
    }
    
    /**
     * Sets the HTTP method used by the request.
     * 
     * @param httpMethod HTTP method used by the request
     */
    protected void setHttpMethod(String httpMethod){
        this.httpMethod = httpMethod.toUpperCase();
    }
    
    /** {@inheritDoc} */
    public String getRelayState() {
        return relayState;
    }
    
    /**
     * Sets the relay state of the request.
     * 
     * @param relayState relay state of the request
     */
    protected void setRelayState(String relayState){
        this.relayState = relayState;
    }

    /** {@inheritDoc} */
    abstract public void decode() throws BindingException;

    /**
     * Parses the incoming message into a DOM and then unmarshalls it into a SAMLObject.
     * 
     * @param samlMessage message to unmarshall
     * 
     * @return SAMLObject representation of the message
     * 
     * @throws BindingException thrown if the incoming XML can not be parsed and unmarshalled
     */
    protected SAMLObject unmarshallSAMLMessage(InputStream samlMessage) throws BindingException {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Parsing message XML into a DOM");
            }
            Document domMessage = ParserPoolManager.getInstance().parse(samlMessage);
            
            if(log.isDebugEnabled()){
                log.debug("Unmarshalling DOM into SAMLObject");
            }
            Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(
                    domMessage.getDocumentElement());
            return (SAMLObject) unmarshaller.unmarshall(domMessage.getDocumentElement());
        } catch (XMLParserException e) {
            log.error("Unable to parse SAML message XML", e);
            throw new BindingException("Unable to parse SAML message XML", e);
        } catch (UnmarshallingException e) {
            log.error("Unable to unmarshall SAML message DOM", e);
            throw new BindingException("Unable to unmarshaller SAML message DOM", e);
        }
    }

    /**
     * Evaluates the registered security policy, if there is one, against the provided request and message.
     * 
     * @param securityPolicy security policy to evaluate
     * @param request HTTP request
     * @param samlMessage SAML message
     * 
     * @throws BindingException thrown if the given request/message do not meet the requirements of the security policy
     */
    protected void evaluateSecurityPolicy(SecurityPolicy<HttpServletRequest> securityPolicy, HttpServletRequest request, SAMLObject samlMessage) throws BindingException {
        if(log.isDebugEnabled()){
            log.debug("Evaluating request and SAML message against security policy");
        }
        securityPolicy.evaluate(request, samlMessage);
    }
}