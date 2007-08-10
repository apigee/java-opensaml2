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

import org.apache.log4j.Logger;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.common.binding.decoding.SAMLMessageDecoder;
import org.opensaml.log.Level;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.message.decoder.BaseMessageDecoder;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.ws.security.SecurityPolicy;
import org.opensaml.ws.transport.http.HTTPInTransport;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.util.Base64;

/**
 * SAML 1.X HTTP POST message decoder.
 */
public class HTTPPostDecoder extends BaseMessageDecoder implements SAMLMessageDecoder {

    /** Class logger. */
    private final Logger log = Logger.getLogger(HTTPPostDecoder.class);

    /** Constructor. */
    public HTTPPostDecoder() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param policy security policy to evaluate a message context against
     */
    public HTTPPostDecoder(SecurityPolicy policy) {
        super(policy);
    }

    /**
     * Constructor.
     * 
     * @param policy security policy to evaluate a message context against
     * @param pool parser pool used to deserialize messages
     */
    public HTTPPostDecoder(SecurityPolicy policy, ParserPool pool) {
        super(policy, pool);
    }

    /** {@inheritDoc} */
    public String getBindingURI() {
        return "urn:oasis:names:tc:SAML:1.0:profiles:browser-post";
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
            log.debug("Decoded SAML relay state of: " + relayState);
        }

        String base64Message = inTransport.getParameter("SAMLResponse");
        if (log.isEnabledFor(Level.TRAIL)) {
            log.log(Level.TRAIL, "Decoding base64 message:\n" + base64Message);
        }
        SAMLObject inboundMessage = (SAMLObject) unmarshallMessage(new ByteArrayInputStream(Base64
                .decode(base64Message)));
        samlMsgCtx.setInboundMessage(inboundMessage);
        samlMsgCtx.setInboundSAMLMessage(inboundMessage);
        if (log.isDebugEnabled()) {
            log.debug("Decoded SAML message");
        }
    }
}