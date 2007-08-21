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

import org.apache.log4j.Logger;
import org.opensaml.Configuration;
import org.opensaml.common.binding.decoding.SAMLMessageDecoder;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.binding.SAML2ArtifactMessageContext;
import org.opensaml.saml2.binding.artifact.SAML2ArtifactBuilderFactory;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.message.decoder.BaseMessageDecoder;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.ws.transport.http.HTTPInTransport;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.util.DatatypeHelper;

/** SAML 2 Artifact Binding decoder, support both HTTP GET and POST. */
public class HTTPArtifactDecoder extends BaseMessageDecoder implements SAMLMessageDecoder {

    /** Class logger. */
    private static Logger log = Logger.getLogger(HTTPArtifactDecoder.class);
    
    /** Factory used to build artifacts from byte representation. */
    private SAML2ArtifactBuilderFactory artifactFactory;

    /** Constructor. */
    public HTTPArtifactDecoder() {
        super();
        artifactFactory = Configuration.getSAML2ArtifactBuilderFactory();
    }

    /**
     * Constructor.
     * 
     * @param pool parser pool used to deserialize messages
     */
    public HTTPArtifactDecoder(ParserPool pool) {
        super(pool);
        artifactFactory = Configuration.getSAML2ArtifactBuilderFactory();
    }

    /** {@inheritDoc} */
    public String getBindingURI() {
        return SAMLConstants.SAML2_ARTIFACT_BINDING_URI;
    }

    /** {@inheritDoc} */
    protected void doDecode(MessageContext messageContext) throws MessageDecodingException {
        if (!(messageContext instanceof SAML2ArtifactMessageContext)) {
            log.error("Invalid message context type, this decoder only support SAML2ArtifactMessageContext");
            throw new MessageDecodingException(
                    "Invalid message context type, this decoder only support SAML2ArtifactMessageContext");
        }

        if (!(messageContext.getInboundMessageTransport() instanceof HTTPInTransport)) {
            log.error("Invalid inbound message transport type, this decoder only support HTTPInTransport");
            throw new MessageDecodingException(
                    "Invalid inbound message transport type, this decoder only support HTTPInTransport");
        }

        SAML2ArtifactMessageContext artifactContext = (SAML2ArtifactMessageContext) messageContext;
        HTTPInTransport inTransport = (HTTPInTransport) artifactContext.getInboundMessageTransport();

        String relayState = DatatypeHelper.safeTrim(inTransport.getParameterValue("RelayState"));
        artifactContext.setRelayState(relayState);

        String encodedArtifact = DatatypeHelper.safeTrimOrNullString(inTransport.getParameterValue("SAMLart"));
        if (encodedArtifact == null) {
            log.error("URL SAMLart parameter was missing or did not contain a value.");
            throw new MessageDecodingException("URL TARGET parameter was missing or did not contain a value.");
        }
        
        byte[] base64DecodedArtifact = Base64.decode(encodedArtifact);
        artifactContext.setArtifact(artifactFactory.buildArtifact(base64DecodedArtifact));
    }
}