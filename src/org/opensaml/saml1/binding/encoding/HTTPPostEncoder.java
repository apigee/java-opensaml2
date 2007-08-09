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

package org.opensaml.saml1.binding.encoding;

import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.opensaml.Configuration;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.common.binding.encoding.SAMLMessageEncoder;
import org.opensaml.common.impl.SAMLObjectContentReference;
import org.opensaml.saml1.core.Response;
import org.opensaml.saml1.core.ResponseAbstractType;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.message.encoder.BaseMessageEncoder;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.ws.transport.http.HTTPOutTransport;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.Signer;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.util.XMLHelper;

/**
 * SAML 1.X HTTP POST message encoder.
 */
public class HTTPPostEncoder extends BaseMessageEncoder implements SAMLMessageEncoder {

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
        velocityEngine = engine;
        velocityTemplateId = templateId;
    }

    /** {@inheritDoc} */
    public String getBindingURI() {
        return "urn:oasis:names:tc:SAML:1.0:profiles:browser-post";
    }

    /** {@inheritDoc} */
    protected void doEncode(MessageContext messageContext) throws MessageEncodingException {
        if (!(messageContext instanceof SAMLMessageContext)) {
            log.error("Invalid message context type, this encoder only support SAMLMessageContext");
            throw new MessageEncodingException(
                    "Invalid message context type, this encoder only support SAMLMessageContext");
        }

        if (!(messageContext.getMessageOutTransport() instanceof HTTPOutTransport)) {
            log.error("Invalid inbound message transport type, this encoder only support HTTPInTransport");
            throw new MessageEncodingException(
                    "Invalid inbound message transport type, this encoder only support HTTPInTransport");
        }

        SAMLMessageContext samlMsgCtx = (SAMLMessageContext) messageContext;

        SAMLObject outboundMessage = samlMsgCtx.getOutboundSAMLMessage();
        if (outboundMessage == null) {
            throw new MessageEncodingException("No outbound SAML message contained in message context");
        }
        String endpointURL = getEndpointURL(samlMsgCtx);

        if (samlMsgCtx.getOutboundSAMLMessage() instanceof ResponseAbstractType) {
            ((ResponseAbstractType) samlMsgCtx.getOutboundSAMLMessage()).setRecipient(endpointURL);
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
            context.put("SAMLResponse", encodedMessage);

            if (messageContext.getRelayState() != null) {
                if (log.isDebugEnabled()) {
                    log.debug("Setting Target parameter to: " + messageContext.getRelayState());
                }
                context.put("Target", messageContext.getRelayState());
            }

            HTTPOutTransport outTransport = (HTTPOutTransport) messageContext.getMessageOutTransport();
            // getResponse().setCharacterEncoding("UTF-8");
            // getResponse().addHeader("Cache-control", "no-cache, no-store");
            // getResponse().addHeader("Pragma", "no-cache");
            Writer out = new OutputStreamWriter(outTransport.getOutgoingStream(), "UTF-8");
            velocityEngine.mergeTemplate(velocityTemplateId, "UTF-8", context, out);
        } catch (Exception e) {
            log.error("Error invoking velocity template", e);
            throw new MessageEncodingException("Error creating output document", e);
        }
    }

    /**
     * Gets the response URL from the relying party endpoint. If the outbound SAML message is a {@link Response} and the
     * relying party endpoint contains a response location then that location is returned otherwise the normal endpoint
     * location is returned.
     * 
     * @param messageContext current message context
     * 
     * @return endpoint URL
     * 
     * @throws MessageEncodingException throw if no relying party endpoint is available
     */
    protected String getEndpointURL(SAMLMessageContext messageContext) throws MessageEncodingException {
        Endpoint endpoint = messageContext.getRelyingPartyEndpoint();
        if (endpoint == null) {
            throw new MessageEncodingException("Relying party endpoint provided we null.");
        }

        String endpointURL = null;
        if (messageContext.getOutboundSAMLMessage() instanceof Response
                && !DatatypeHelper.isEmpty(endpoint.getResponseLocation())) {
            endpointURL = endpoint.getResponseLocation();
        } else {
            if (DatatypeHelper.isEmpty(endpoint.getLocation())) {
                throw new MessageEncodingException("Relying party endpoint location was null or empty.");
            }
            endpointURL = endpoint.getLocation();
        }

        if (log.isDebugEnabled()) {
            log.debug("Endpoint URL for relying party " + messageContext.getRelyingPartyEntityId()
                    + " determined to be " + endpointURL);
        }
        return endpointURL;
    }

    /**
     * Signs the given SAML message if it a {@link SignableSAMLObject} and this encoder has signing credentials.
     * 
     * @param messageContext current message context
     */
    @SuppressWarnings("unchecked")
    protected void signMessage(SAMLMessageContext messageContext) {
        SAMLObject outboundMessage = messageContext.getOutboundSAMLMessage();
        if (outboundMessage instanceof SignableSAMLObject
                && messageContext.getOuboundSAMLMessageSigningCredential() != null) {
            if (log.isDebugEnabled()) {
                log.debug("Signing outbound SAML message.");
            }
            SignableSAMLObject signableMessage = (SignableSAMLObject) outboundMessage;

            SAMLObjectContentReference contentRef = new SAMLObjectContentReference(signableMessage);
            XMLObjectBuilder<Signature> signatureBuilder = Configuration.getBuilderFactory().getBuilder(
                    Signature.DEFAULT_ELEMENT_NAME);
            Signature signature = signatureBuilder.buildObject(Signature.DEFAULT_ELEMENT_NAME);
            signature.getContentReferences().add(contentRef);
            signature.setSigningCredential(messageContext.getOuboundSAMLMessageSigningCredential());
            signableMessage.setSignature(signature);

            Signer.signObject(signature);
        }
    }
}