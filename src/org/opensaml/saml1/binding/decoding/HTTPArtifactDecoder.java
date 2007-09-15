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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.opensaml.Configuration;
import org.opensaml.common.binding.artifact.SAMLArtifactMap;
import org.opensaml.common.binding.artifact.SAMLArtifactMap.SAMLArtifactMapEntry;
import org.opensaml.common.binding.decoding.SAMLMessageDecoder;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.binding.SAML1ArtifactMessageContext;
import org.opensaml.saml1.binding.artifact.AbstractSAML1Artifact;
import org.opensaml.saml1.binding.artifact.SAML1ArtifactBuilderFactory;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.ws.transport.http.HTTPInTransport;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * SAML 1.X HTTP Artifact message decoder.
 */
public class HTTPArtifactDecoder extends BaseSAML1MessageDecoder implements SAMLMessageDecoder {

    /** Class logger. */
    private final Logger log = Logger.getLogger(HTTPArtifactDecoder.class);

    /** Builder of SAML 1 artifacts. */
    private SAML1ArtifactBuilderFactory artifactBuilder;

    /**
     * Constructor.
     * 
     * @param map Artifact to SAML map
     */
    public HTTPArtifactDecoder(SAMLArtifactMap map) {
        super(map);

        artifactBuilder = Configuration.getSAML1ArtifactBuilderFactory();
    }
    
    /**
     * Constructor.
     * 
     * @param map used to map artifacts to SAML
     * @param pool parser pool used to deserialize messages
     */
    public HTTPArtifactDecoder(SAMLArtifactMap map, ParserPool pool) {
        super(map, pool);
        
        artifactBuilder = Configuration.getSAML1ArtifactBuilderFactory();
    }

    /** {@inheritDoc} */
    public String getBindingURI() {
        return SAMLConstants.SAML1_ARTIFACT_BINDING_URI;
    }

    /** {@inheritDoc} */
    protected void doDecode(MessageContext messageContext) throws MessageDecodingException {
        if (!(messageContext instanceof SAML1ArtifactMessageContext)) {
            log.error("Invalid message context type, this decoder only support SAML1ArtifactMessageContext");
            throw new MessageDecodingException(
                    "Invalid message context type, this decoder only support SAML1ArtifactMessageContext");
        }

        if (!(messageContext.getInboundMessageTransport() instanceof HTTPInTransport)) {
            log.error("Invalid inbound message transport type, this decoder only support HTTPInTransport");
            throw new MessageDecodingException(
                    "Invalid inbound message transport type, this decoder only support HTTPInTransport");
        }

        SAML1ArtifactMessageContext artifactContext = (SAML1ArtifactMessageContext) messageContext;
        decodeTarget(artifactContext);
        decodeArtifacts(artifactContext);

        populateMessageContext(artifactContext);
    }

    /**
     * Decodes the TARGET parameter and adds it to the message context.
     * 
     * @param artifactContext current message context
     * 
     * @throws MessageDecodingException thrown if there is a problem decoding the TARGET parameter.
     */
    protected void decodeTarget(SAML1ArtifactMessageContext artifactContext) throws MessageDecodingException {
        HTTPInTransport inTransport = (HTTPInTransport) artifactContext.getInboundMessageTransport();

        String target = DatatypeHelper.safeTrim(inTransport.getParameterValue("TARGET"));
        if (target == null) {
            log.error("URL TARGET parameter was missing or did not contain a value.");
            throw new MessageDecodingException("URL TARGET parameter was missing or did not contain a value.");
        }
        artifactContext.setRelayState(target);
    }

    /**
     * Decodes the SAMLart parameter(s) and adds the decoded artifacts and relying party information to the message
     * context.
     * 
     * @param artifactContext current message context
     * 
     * @throws MessageDecodingException thrown if there is a problem decoding the SAMLart parameter(s)
     */
    protected void decodeArtifacts(SAML1ArtifactMessageContext artifactContext) throws MessageDecodingException {
        HTTPInTransport inTransport = (HTTPInTransport) artifactContext.getInboundMessageTransport();
        List<String> encodedArtifacts = inTransport.getParameterValues("SAMLart");
        if (encodedArtifacts == null || encodedArtifacts.size() == 0) {
            log.error("URL SAMLart parameter was missing or did not contain a value.");
            throw new MessageDecodingException("URL SAMLart parameter was missing or did not contain a value.");
        }

        ArrayList<AbstractSAML1Artifact> artifacts = new ArrayList<AbstractSAML1Artifact>();

        String relyingPartyId = null;
        SAMLArtifactMapEntry artifactEntry;
        AbstractSAML1Artifact artifact;
        for (String encodedArtifact : encodedArtifacts) {
            artifactEntry = getArtifactMap().get(Base64.decode(encodedArtifact));

            if (relyingPartyId == null) {
                relyingPartyId = artifactEntry.getRelyingPartyId();
            } else {
                if (!relyingPartyId.equals(artifactEntry.getRelyingPartyId())) {
                    throw new MessageDecodingException("Request SAML artifacts do have the same relying party ID");
                }
            }

            artifact = artifactBuilder.buildArtifact(artifactEntry.getArtifact());
            artifacts.add(artifact);
        }

        artifactContext.setInboundMessageIssuer(relyingPartyId);
    }
}