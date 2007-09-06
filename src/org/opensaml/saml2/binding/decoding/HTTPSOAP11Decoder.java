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

import java.util.List;

import org.apache.log4j.Logger;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.common.binding.decoding.SAMLMessageDecoder;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.ws.soap.soap11.Envelope;
import org.opensaml.ws.transport.http.HTTPInTransport;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.parse.ParserPool;

/**
 * SAML 2.0 SOAP 1.1 over HTTP binding decoder.
 */
public class HTTPSOAP11Decoder extends BaseSAML2MessageDecoder implements SAMLMessageDecoder {

    /** Class logger. */
    private final Logger log = Logger.getLogger(HTTPSOAP11Decoder.class);

    /** Constructor. */
    public HTTPSOAP11Decoder() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param pool parser pool used to deserialize messages
     */
    public HTTPSOAP11Decoder(ParserPool pool) {
        super(pool);
    }

    /** {@inheritDoc} */
    public String getBindingURI() {
        return SAMLConstants.SAML2_SOAP11_BINDING_URI;
    }

    /** {@inheritDoc} */
    protected void doDecode(MessageContext messageContext) throws MessageDecodingException {
        if (!(messageContext instanceof SAMLMessageContext)) {
            log.error("Invalid message context type, this decoder only support SAMLMessageContext");
            throw new MessageDecodingException(
                    "Invalid message context type, this decoder only support SAMLMessageContext");
        }

        if (!(messageContext.getInboundMessageTransport() instanceof HTTPInTransport)) {
            log.error("Invalid inbound message transport type, this decoder only support HTTPInTransport");
            throw new MessageDecodingException(
                    "Invalid inbound message transport type, this decoder only support HTTPInTransport");
        }

        SAMLMessageContext samlMsgCtx = (SAMLMessageContext) messageContext;
        HTTPInTransport inTransport = (HTTPInTransport) samlMsgCtx.getInboundMessageTransport();

        if (log.isDebugEnabled()) {
            log.debug("Unmarshalling SOAP message");
        }
        Envelope soapMessage = (Envelope) unmarshallMessage(inTransport.getIncomingStream());
        samlMsgCtx.setInboundMessage(soapMessage);

        List<XMLObject> soapBodyChildren = soapMessage.getBody().getUnknownXMLObjects();
        if (soapBodyChildren.size() < 1 || soapBodyChildren.size() > 1) {
            log.error("Unexpected number of children in the SOAP body, " + soapBodyChildren.size()
                    + ".  Unable to extract SAML message");
            throw new MessageDecodingException(
                    "Unexpected number of children in the SOAP body, unable to extract SAML message");
        }

        SAMLObject samlMessage = (SAMLObject) soapBodyChildren.get(0);
        if (log.isDebugEnabled()) {
            log.debug("Decoded SOAP messaged which included SAML message of type " + samlMessage.getElementQName());
        }
        samlMsgCtx.setInboundSAMLMessage(samlMessage);
        
        populateMessageContext(samlMsgCtx);
    }
}