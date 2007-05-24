/*
 * Copyright [2007] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.saml1.binding.decoding;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.decoding.impl.AbstractHTTPMessageDecoder;
import org.opensaml.xml.util.Base64;

/**
 * SAML 1.X HTTP POST message decoder.
 */
public class HTTPPostDecoder extends AbstractHTTPMessageDecoder {
    
    /** Binding URI. */
    public static final String BINDING_URI = "urn:oasis:names:tc:SAML:1.0:profiles:browser-post";

    /** HTTP request param name for SAML response. */
    public static final String RESPONSE_PARAM = "SAMLResponse";

    /** HTTP request param name for the TARGET. */
    public static final String TARGET_PARAM = "TARGET";

    /** Class logger. */
    private static Logger log = Logger.getLogger(HTTPPostDecoder.class);

    /** {@inheritDoc} */
    public String getBindingURI() {
        return BINDING_URI;
    }

    /** {@inheritDoc} */
    public void decode() throws BindingException {
        if (log.isDebugEnabled()) {
            log.debug("Beginning decode of request using HTTP POST binding");
        }
        InputStream decodedMessage = getBase64DecodedMessage();

        SAMLObject samlMessage = (SAMLObject) unmarshallMessage(decodedMessage);

        evaluateSecurityPolicy(samlMessage);
        
        setSAMLMessage(samlMessage);

        if (log.isDebugEnabled()) {
            log.debug("HTTP request successfully decoded using SAML 2 HTTP POST binding");
        }
    }

    /**
     * Gets the Base64 encoded message from the request and decodes it.
     * 
     * @return decoded message
     * 
     * @throws BindingException thrown if the message does not contain a base64 encoded SAML message
     */
    protected InputStream getBase64DecodedMessage() throws BindingException {
        if (log.isDebugEnabled()) {
            log.debug("Getting Base64 encoded message from request");
        }
        String encodedMessage = getRequest().getParameter(RESPONSE_PARAM);

        if (log.isDebugEnabled()) {
            log.debug("Base64 decoding SAML message");
        }
        byte[] decodedMessage = Base64.decode(encodedMessage);

        return new ByteArrayInputStream(decodedMessage);
    }
}