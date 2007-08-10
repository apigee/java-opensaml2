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

package org.opensaml.saml2.binding.decoding;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.apache.log4j.Logger;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.common.binding.decoding.SAMLMessageDecoder;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.message.decoder.BaseMessageDecoder;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.ws.transport.http.HTTPInTransport;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * SAML 2.0 HTTP Redirect decoder using the DEFLATE encoding method.
 * 
 * This decoder only supports DEFLATE compression and DSA-SHA1 and RSA-SHA1 signatures.
 */
public class HTTPRedirectDeflateDecoder extends BaseMessageDecoder implements SAMLMessageDecoder {

    /** Class logger. */
    private static Logger log = Logger.getLogger(HTTPRedirectDeflateDecoder.class);

    /** {@inheritDoc} */
    public String getBindingURI() {
        return "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect";
    }

    /** {@inheritDoc} */
    protected void doDecode(MessageContext messageContext) throws MessageDecodingException {
        if (!(messageContext instanceof SAMLMessageContext)) {
            log.error("Invalid message context type, this decoder only support SAMLMessageContext");
            throw new MessageDecodingException(
                    "Invalid message context type, this decoder only support SAMLMessageContext");
        }

        if (!(messageContext.getMessageInTransport() instanceof HTTPInTransport)) {
            log.error("Invalid inbound message transport type, this decoder only support HTTPInTransport");
            throw new MessageDecodingException(
                    "Invalid inbound message transport type, this decoder only support HTTPInTransport");
        }

        SAMLMessageContext samlMsgCtx = (SAMLMessageContext) messageContext;
        HTTPInTransport inTransport = (HTTPInTransport) samlMsgCtx.getMessageInTransport();

        String relayState = inTransport.getParameter("RelayState");
        samlMsgCtx.setRelayState(relayState);
        if (log.isDebugEnabled()) {
            log.debug("Decoded RelayState: " + relayState);
        }

        InputStream samlMessageIns;
        if (!DatatypeHelper.isEmpty(inTransport.getParameter("SAMLRequest"))) {
            samlMessageIns = decodeMessage(inTransport.getParameter("SAMLRequest"));
        } else if (!DatatypeHelper.isEmpty(inTransport.getParameter("SAMLResponse"))) {
            samlMessageIns = decodeMessage(inTransport.getParameter("SAMLResponse"));
        } else {
            throw new MessageDecodingException(
                    "No SAMLRequest or SAMLResponse query path parameter, invalid SAML 2 HTTP Redirect message");
        }

        SAMLObject samlMessage = (SAMLObject) unmarshallMessage(samlMessageIns);
        samlMsgCtx.setInboundSAMLMessage(samlMessage);
        samlMsgCtx.setInboundMessage(samlMessage);
        if (log.isDebugEnabled()) {
            log.debug("Decoded SAML message");
        }

        // TODO validate signature
    }

    /**
     * Base64 decodes the SAML message and then decompresses the message.
     * 
     * @param message Base64 encoded, DEFALTE compressed, SAML message
     * 
     * @return the SAML message
     * 
     * @throws MessageDecodingException thrown if the message can not be decoded
     */
    protected InputStream decodeMessage(String message) throws MessageDecodingException {
        if (log.isDebugEnabled()) {
            log.debug("Base64 decoding and inflating SAML message");
        }

        try {
            byte[] decodedBytes = Base64.decode(message);
            ByteArrayInputStream bytesIn = new ByteArrayInputStream(decodedBytes);
            InflaterInputStream inflater = new InflaterInputStream(bytesIn, new Inflater(true));
            return inflater;
        } catch (Exception e) {
            log.error("Unable to Base64 decode and inflate SAML message", e);
            throw new MessageDecodingException("Unable to Base64 decode and inflate SAML message", e);
        }
    }
}