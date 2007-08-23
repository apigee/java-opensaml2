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

import org.apache.log4j.Logger;
import org.opensaml.Configuration;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.common.binding.artifact.SAMLArtifactMap;
import org.opensaml.common.binding.encoding.SAMLMessageEncoder;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.binding.artifact.AbstractSAML1Artifact;
import org.opensaml.saml1.binding.artifact.SAML1ArtifactBuilder;
import org.opensaml.saml1.binding.artifact.SAML1ArtifactType0001;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.NameIdentifier;
import org.opensaml.saml1.core.Response;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.message.encoder.BaseMessageEncoder;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.ws.transport.http.HTTPOutTransport;

/**
 * SAML 1.X HTTP Artifact message encoder.
 */
public class HTTPArtifactEncoder extends BaseMessageEncoder implements SAMLMessageEncoder {

    /** Class logger. */
    private final Logger log = Logger.getLogger(HTTPArtifactEncoder.class);

    /** SAML artifact map used to store created artifacts for later retrival. */
    private SAMLArtifactMap artifactMap;

    /** Default artifact type to use when encoding messages. */
    private byte[] defaultArtifactType;

    /**
     * Constructor.
     * 
     * @param map SAML artifact map used to store created artifacts for later retrival
     */
    public HTTPArtifactEncoder(SAMLArtifactMap map) {
        artifactMap = map;
        defaultArtifactType = SAML1ArtifactType0001.TYPE_CODE;
    }

    /** {@inheritDoc} */
    public String getBindingURI() {
        return SAMLConstants.SAML1_ARTIFACT_BINDING_URI;
    }

    /** {@inheritDoc} */
    protected void doEncode(MessageContext messageContext) throws MessageEncodingException {
        if (!(messageContext instanceof SAMLMessageContext)) {
            log.error("Invalid message context type, this encoder only support SAMLMessageContext");
            throw new MessageEncodingException(
                    "Invalid message context type, this encoder only support SAMLMessageContext");
        }

        if (!(messageContext.getOutboundMessageTransport() instanceof HTTPOutTransport)) {
            log.error("Invalid outbound message transport type, this encoder only support HTTPOutTransport");
            throw new MessageEncodingException(
                    "Invalid outbound message transport type, this encoder only support HTTPOutTransport");
        }

        SAMLMessageContext<SAMLObject, Response, NameIdentifier> artifactContext = (SAMLMessageContext) messageContext;
        HTTPOutTransport outTransport = (HTTPOutTransport) artifactContext.getOutboundMessageTransport();

        outTransport.addParameter("TARGET", artifactContext.getRelayState());

        SAML1ArtifactBuilder artifactBuilder;
        if (artifactContext.getOutboundMessageArtifactType() != null) {
            artifactBuilder = Configuration.getSAML1ArtifactBuilderFactory().getArtifactBuilder(
                    artifactContext.getOutboundMessageArtifactType());
        } else {
            artifactBuilder = Configuration.getSAML1ArtifactBuilderFactory().getArtifactBuilder(defaultArtifactType);
            artifactContext.setOutboundMessageArtifactType(defaultArtifactType);
        }

        AbstractSAML1Artifact artifact;
        String artifactString;
        for (Assertion assertion : artifactContext.getOutboundSAMLMessage().getAssertions()) {
            artifact = artifactBuilder.buildArtifact(artifactContext, assertion);
            artifactMap.put(artifact.getArtifactBytes(), messageContext.getInboundMessageIssuer(), messageContext
                    .getOutboundMessageIssuer(), assertion);
            artifactString = artifact.base64Encode();
            outTransport.addParameter("SAMLArt", artifactString);
        }
    }
}