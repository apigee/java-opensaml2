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
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.impl.AbstractHTTPMessageDecoder;
import org.opensaml.xml.util.Base64;

/**
 * SAML 2.0 HTTP Redirect decoder using the DEFLATE encoding method.
 * 
 * This decoder only supports DEFLATE compression and DSA-SHA1 and RSA-SHA1 signatures.
 */
public class HTTPRedirectDeflateDecoder extends AbstractHTTPMessageDecoder {

    /** Class logger */
    private final static Logger log = Logger.getLogger(HTTPRedirectDeflateDecoder.class);

    /** Whether the message was signed */
    private boolean isSigned;

    /** Signature used to sign message */
    private String signatureAlgorithm;

    /**
     * Gets whether the decoded message was signed.
     * 
     * @return whether the decoded message was signed
     */
    public boolean isSigned() {
        return isSigned;
    }

    /**
     * Gets the signature algorithm used to sign the message.
     * 
     * @return signature algorithm used to sign the message, or null if the message was not signed
     */
    public String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    /** {@inheritDoc} */
    public void decode() throws BindingException {
        if (log.isDebugEnabled()) {
            log.debug("Beginning SAML 2 HTTP Redirect decoding");
        }

        HttpServletRequest request = getRequest();
        setHttpMethod("GET");
        setRelayState(request.getParameter("RelayState"));

        InputStream samlMessageIns;
        if (request.getParameter("SAMLRequest") != null) {
            samlMessageIns = decodeMessage(request.getParameter("SAMLRequest"));
        } else if (request.getParameter("SAMLResponse") != null) {
            samlMessageIns = decodeMessage(request.getParameter("SAMLResponse"));
        } else {
            throw new BindingException(
                    "No SAMLRequest or SAMLResponse query path parameter, invalid SAML 2 HTTP Redirect message");
        }

        SAMLObject samlMessage = (SAMLObject) unmarshallMessage(samlMessageIns);
        setSAMLMessage(samlMessage);

        if (request.getParameter("Signature") != null) {
            isSigned = true;
            signatureAlgorithm = request.getParameter("SigAlg");
        }

        evaluateSecurityPolicy(samlMessage);
    }

    /**
     * Base64 decodes the SAML message and then decompresses the message.
     * 
     * @param message Base64 encoded, DEFALTE compressed, SAML message
     * 
     * @return the SAML message
     * 
     * @throws BindingException thrown if the message can not be decoded
     */
    protected InputStream decodeMessage(String message) throws BindingException {
        if (log.isDebugEnabled()) {
            log.debug("Base64 decoding and inflating SAML message");
        }

        try {
            ByteArrayInputStream encodedMessage = new ByteArrayInputStream(message.getBytes());
            Base64.InputStream base64In = new Base64.InputStream(encodedMessage);
            InflaterInputStream inflater = new InflaterInputStream(base64In, new Inflater(true));
            return inflater;
        } catch (Exception e) {
            log.error("Unable to Base64 decode and inflate SAML message", e);
            throw new BindingException("Unable to Base64 decode and inflate SAML message", e);
        }
    }
}