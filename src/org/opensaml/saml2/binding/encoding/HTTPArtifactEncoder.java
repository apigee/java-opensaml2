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
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.opensaml.Configuration;
import org.opensaml.common.binding.artifact.SAMLArtifactMap;
import org.opensaml.common.binding.encoding.SAMLMessageEncoder;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Response;
import org.opensaml.saml2.binding.SAML2ArtifactMessageContext;
import org.opensaml.saml2.binding.artifact.AbstractSAML2Artifact;
import org.opensaml.saml2.binding.artifact.SAML2ArtifactBuilder;
import org.opensaml.saml2.binding.artifact.SAML2ArtifactType0004;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.RequestAbstractType;
import org.opensaml.util.URLBuilder;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.ws.transport.http.HTTPOutTransport;
import org.opensaml.ws.transport.http.HTTPTransportUtils;
import org.opensaml.xml.util.Pair;

/**
 * SAML 2 Artifact Binding encoder, support both HTTP GET and POST.
 */
public class HTTPArtifactEncoder extends BaseSAML2MessageEncoder implements SAMLMessageEncoder {

    /** Class logger. */
    private static Logger log = Logger.getLogger(HTTPArtifactEncoder.class);

    /** Velocity engine used to evaluate the template when performing POST encoding. */
    private VelocityEngine velocityEngine;

    /** ID of the velocity template used when performing POST encoding. */
    private String velocityTemplateId;

    /** SAML artifact map used to store created artifacts for later retrieval. */
    private SAMLArtifactMap artifactMap;

    /** Default artifact type to use when encoding messages. */
    private byte[] defaultArtifactType;

    /**
     * Constructor.
     * 
     * @param engine velocity engine used to construct the POST form
     * @param template ID of velocity template used to contruct the POST form
     * @param map artifact map used to store artifact/message bindings
     */
    public HTTPArtifactEncoder(VelocityEngine engine, String template, SAMLArtifactMap map) {
        super();
        velocityEngine = engine;
        velocityTemplateId = template;
        artifactMap = map;
        defaultArtifactType = SAML2ArtifactType0004.TYPE_CODE;
    }

    /** {@inheritDoc} */
    public String getBindingURI() {
        return SAMLConstants.SAML2_ARTIFACT_BINDING_URI;
    }

    /** {@inheritDoc} */
    protected void doEncode(MessageContext messageContext) throws MessageEncodingException {
        if (!(messageContext instanceof SAML2ArtifactMessageContext)) {
            log.error("Invalid message context type, this encoder only support SAML2ArtifactMessageContext");
            throw new MessageEncodingException(
                    "Invalid message context type, this encoder only support SAML2ArtifactMessageContext");
        }

        if (!(messageContext.getOutboundMessageTransport() instanceof HTTPOutTransport)) {
            log.error("Invalid outbound message transport type, this encoder only support HTTPOutTransport");
            throw new MessageEncodingException(
                    "Invalid outbound message transport type, this encoder only support HTTPOutTransport");
        }

        SAML2ArtifactMessageContext<?, ?, ?> artifactContext = (SAML2ArtifactMessageContext) messageContext;
        HTTPOutTransport outTransport = (HTTPOutTransport) artifactContext.getOutboundMessageTransport();
        outTransport.setCharacterEncoding("UTF-8");

        if (outTransport.getHTTPMethod().equals("GET")) {
            getEncode(artifactContext, outTransport);
        } else if (outTransport.getHTTPMethod().equals("POST")) {
            postEncode(artifactContext, outTransport);
        }

        throw new MessageEncodingException("Outbound HTTP transport using method " + outTransport.getHTTPMethod()
                + ", only POST and GET are supported by this encoder");
    }

    /**
     * Performs HTTP POST based encoding.
     * 
     * @param artifactContext current request context
     * @param outTransport outbound HTTP transport
     * 
     * @throws MessageEncodingException thrown if there is a problem POST encoding the artifact
     */
    protected void postEncode(SAML2ArtifactMessageContext artifactContext, HTTPOutTransport outTransport)
            throws MessageEncodingException {
        if (log.isDebugEnabled()) {
            log.debug("Performing HTTP POST SAML 2 artifact encoding");
        }

        if (log.isDebugEnabled()) {
            log.debug("Creating velocity context");
        }
        VelocityContext context = new VelocityContext();
        context.put("action", getEndpointURL(artifactContext));
        context.put("SAMLArt", buildArtifact(artifactContext).base64Encode());

        if (checkRelayState(artifactContext.getRelayState())) {
            context.put("RelayState", HTTPTransportUtils.urlEncode(artifactContext.getRelayState()));
        }

        try {
            if (log.isDebugEnabled()) {
                log.debug("Invoking velocity template");
            }
            OutputStreamWriter outWriter = new OutputStreamWriter(outTransport.getOutgoingStream());
            velocityEngine.mergeTemplate(velocityTemplateId, "UTF-8", context, outWriter);
        } catch (Exception e) {
            log.error("Error invoking velocity template to create POST form", e);
            throw new MessageEncodingException("Error creating output document", e);
        }
    }

    /**
     * Performs HTTP GET based encoding.
     * 
     * @param artifactContext current request context
     * @param outTransport outbound HTTP transport
     * 
     * @throws MessageEncodingException thrown if there is a problem GET encoding the artifact
     */
    protected void getEncode(SAML2ArtifactMessageContext artifactContext, HTTPOutTransport outTransport)
            throws MessageEncodingException {
        if (log.isDebugEnabled()) {
            log.debug("Performing HTTP GET SAML 2 artifact encoding");
        }

        URLBuilder urlBuilder = new URLBuilder(getEndpointURL(artifactContext));

        List<Pair<String, String>> params = urlBuilder.getQueryParams();

        params.add(new Pair<String, String>("SAMLArt", buildArtifact(artifactContext).base64Encode()));

        if (checkRelayState(artifactContext.getRelayState())) {
            params.add(new Pair<String, String>("RelayState", artifactContext.getRelayState()));
        }

        outTransport.sendRedirect(urlBuilder.buildURL());
    }

    /**
     * Builds the SAML 2 artifact for the outgoing message.
     * 
     * @param artifactContext current request context
     * 
     * @return SAML 2 artifact for outgoing message
     */
    protected AbstractSAML2Artifact buildArtifact(
            SAML2ArtifactMessageContext<RequestAbstractType, Response, NameID> artifactContext) {

        SAML2ArtifactBuilder artifactBuilder;
        if (artifactContext.getOutboundMessageArtifactType() != null) {
            artifactBuilder = Configuration.getSAML2ArtifactBuilderFactory().getArtifactBuilder(
                    artifactContext.getOutboundMessageArtifactType());
        } else {
            artifactBuilder = Configuration.getSAML2ArtifactBuilderFactory().getArtifactBuilder(defaultArtifactType);
            artifactContext.setOutboundMessageArtifactType(defaultArtifactType);
        }

        AbstractSAML2Artifact artifact = artifactBuilder.buildArtifact(artifactContext);
        artifactMap.put(artifact.base64Encode(), artifactContext.getInboundMessageIssuer(), artifactContext
                .getOutboundMessageIssuer(), artifactContext.getOutboundSAMLMessage());
        artifactContext.setArtifact(artifact);
        return artifact;
    }
}