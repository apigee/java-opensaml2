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

package org.opensaml.saml2.binding.encoding;

import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.RequestAbstractType;
import org.opensaml.saml2.core.StatusResponseType;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.ws.transport.http.HTTPOutTransport;
import org.opensaml.ws.transport.http.HTTPTransportUtils;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.util.XMLHelper;

/**
 * SAML 2.0 HTTP Post binding message encoder.
 */
public class HTTPPostEncoder extends BaseSAML2MessageEncoder {

    /** Class logger. */
    private final Logger log = Logger.getLogger(HTTPPostEncoder.class);

    /** Velocity engine used to evaluate the template when performing POST encoding. */
    private VelocityEngine velocityEngine;

    /** ID of the velocity template used when performing POST encoding. */
    private String velocityTemplateId;

    /**
     * Constructor.
     * 
     * @param engine velocity engine instance used to create POST body
     * @param templateId ID of the template used to create POST body
     */
    public HTTPPostEncoder(VelocityEngine engine, String templateId) {
        super();
        velocityEngine = engine;
        velocityTemplateId = templateId;
    }

    /** {@inheritDoc} */
    public String getBindingURI() {
        return SAMLConstants.SAML2_POST_BINDING_URI;
    }

    /** {@inheritDoc} */
    protected void doEncode(MessageContext messageContext) throws MessageEncodingException {
        if (!(messageContext instanceof SAMLMessageContext)) {
            log.error("Invalid message context type, this encoder only support SAMLMessageContext");
            throw new MessageEncodingException(
                    "Invalid message context type, this encoder only support SAMLMessageContext");
        }

        if (!(messageContext.getOutboundMessageTransport() instanceof HTTPOutTransport)) {
            log.error("Invalid outbound message transport type, this encoder only support HTTPInTransport");
            throw new MessageEncodingException(
                    "Invalid outbound message transport type, this encoder only support HTTPInTransport");
        }

        SAMLMessageContext samlMsgCtx = (SAMLMessageContext) messageContext;

        SAMLObject outboundMessage = samlMsgCtx.getOutboundSAMLMessage();
        if (outboundMessage == null) {
            throw new MessageEncodingException("No outbound SAML message contained in message context");
        }
        String endpointURL = getEndpointURL(samlMsgCtx);

        if (samlMsgCtx.getOutboundSAMLMessage() instanceof StatusResponseType) {
            ((StatusResponseType) samlMsgCtx.getOutboundSAMLMessage()).setDestination(endpointURL);
        }

        signMessage(samlMsgCtx);
        samlMsgCtx.setOutboundMessage(outboundMessage);

        postEncode(samlMsgCtx, endpointURL);
    }

    /**
     * Base64 and POST encodes the outbound message and writes it to the outbound transport.
     * 
     * @param messageContext current message context
     * @param endpointURL endpoint URL to encode message to
     * 
     * @throws MessageEncodingException thrown if there is a problem encoding the message
     */
    protected void postEncode(SAMLMessageContext messageContext, String endpointURL) throws MessageEncodingException {
        if (log.isDebugEnabled()) {
            log.debug("Invoking velocity template to create POST body");
        }
        try {
            VelocityContext context = new VelocityContext();

            if (log.isDebugEnabled()) {
                log.debug("Encoding action url of: " + endpointURL);
            }
            context.put("action", endpointURL);

            if (log.isDebugEnabled()) {
                log.debug("Marshalling and Base64 encoding SAML message");
            }
            String messageXML = XMLHelper.nodeToString(marshallMessage(messageContext.getOutboundSAMLMessage()));
            String encodedMessage = Base64.encodeBytes(messageXML.getBytes(), Base64.DONT_BREAK_LINES);
            if (log.isDebugEnabled()) {
                log.debug("Encoding SAML message of: " + encodedMessage);
            }
            if (messageContext.getOutboundSAMLMessage() instanceof RequestAbstractType) {
                context.put("SAMLRequest", HTTPTransportUtils.urlEncode(encodedMessage));
            } else {
                context.put("SAMLResponse", HTTPTransportUtils.urlEncode(encodedMessage));
            }

            String relayState = messageContext.getRelayState();
            if (checkRelayState(relayState)) {
                if (log.isDebugEnabled()) {
                    log.debug("Encoding relay state of: " + relayState);
                }
                context.put("RelayState", HTTPTransportUtils.urlEncode(relayState));
            }

            HTTPOutTransport outTransport = (HTTPOutTransport) messageContext.getOutboundMessageTransport();
            HTTPTransportUtils.addNoCacheHeaders(outTransport);
            HTTPTransportUtils.setUTF8Encoding(outTransport);
            HTTPTransportUtils.setContentType(outTransport, "application/xhtml+xml");

            Writer out = new OutputStreamWriter(outTransport.getOutgoingStream(), "UTF-8");
            velocityEngine.mergeTemplate(velocityTemplateId, "UTF-8", context, out);
            out.flush();
        } catch (Exception e) {
            log.error("Error invoking velocity template", e);
            throw new MessageEncodingException("Error creating output document", e);
        }
    }
}