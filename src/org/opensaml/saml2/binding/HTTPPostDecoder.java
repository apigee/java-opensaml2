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

package org.opensaml.saml2.binding;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.SecurityPolicy;
import org.opensaml.common.binding.impl.AbstractHTTPMessageDecoder;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * Message decoder implementing the SAML 2.0 HTTP POST profile.
 */
public class HTTPPostDecoder extends AbstractHTTPMessageDecoder {

    /** Class logger */
    public final static Logger log = Logger.getLogger(HTTPPostDecoder.class);

    /** HTTP request param name for SAML request */
    public final static String REQUEST_PARAM = "SAMLRequest";

    /** HTTP request param name for SAML response */
    public final static String RESPONSE_PARAM = "SAMLResponse";

    /** HTTP request param name for relay state */
    public final static String RELAY_STATE_PARAM = "RelayState";

    /** {@inheritDoc} */
    public void decode() throws BindingException {
        if (log.isDebugEnabled()) {
            log.debug("Beginning decode of request using HTTP POST binding");
        }
        HttpServletRequest request = getRequest();

        InputStream decodedMessage = getBase64DecodedMessage(request);
        
        SAMLObject samlMessage = unmarshallSAMLMessage(decodedMessage);
        
        SecurityPolicy<HttpServletRequest> securityPolicy = getSecurityPolicy();
        if(securityPolicy != null){
            evaluateSecurityPolicy(securityPolicy, request, samlMessage);
            setIssuer(securityPolicy.getIssuer());
            setIssuerMetadata(securityPolicy.getIssuerMetadata());
        }

        setRelayState(request.getParameter(RELAY_STATE_PARAM));
        setSAMLMessage(samlMessage);
        
        if(log.isDebugEnabled()){
            log.debug("HTTP request successfully decoded using SAML 2 HTTP POST binding");
        }
    }
    
    /**
     * Gets the Base64 encoded message from the request and decodes it.
     * 
     * @param request HTTP request that carries the base64 encoded message
     * 
     * @return decoded message
     * 
     * @throws BindingException thrown if the message does not contain a base64 encoded SAML message
     */
    protected InputStream getBase64DecodedMessage(HttpServletRequest request) throws BindingException{
        if (log.isDebugEnabled()) {
            log.debug("Getting Base64 encoded message from request");
        }
        String encodedMessage = request.getParameter(REQUEST_PARAM);
        if (DatatypeHelper.isEmpty(encodedMessage)) {
            encodedMessage = request.getParameter(RESPONSE_PARAM);
        }

        if (DatatypeHelper.isEmpty(encodedMessage)) {
            log.error("Request did not contain either a " + REQUEST_PARAM + " or " + RESPONSE_PARAM
                    + " paramter in request.  Invalid request for SAML 2 HTTP POST binding.");
            throw new BindingException("No SAML message present in request");
        }

        if (log.isDebugEnabled()) {
            log.debug("Base64 decoding SAML message");
        }
        byte[] decodedMessage = Base64.decode(encodedMessage);
        
        return new ByteArrayInputStream(decodedMessage);
    }
}