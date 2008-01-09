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

import org.opensaml.common.binding.artifact.SAMLArtifactMap;
import org.opensaml.common.binding.artifact.SAMLArtifactMap.SAMLArtifactMapEntry;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.binding.SAML1ArtifactMessageContext;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.AssertionArtifact;
import org.opensaml.saml1.core.Request;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.ws.transport.http.HTTPInTransport;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.util.DatatypeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SAML 1.X HTTP Artifact message decoder.
 */
public class HTTPArtifactDecoder extends BaseSAML1MessageDecoder {

    /** Class logger. */
    private final Logger log = LoggerFactory.getLogger(HTTPArtifactDecoder.class);

    /** Map used to map artifacts to SAML. */
    private SAMLArtifactMap artifactMap;

    /**
     * Constructor.
     * 
     * @param map used to map artifacts to SAML
     * @param pool parser pool used to deserialize messages
     */
    public HTTPArtifactDecoder(SAMLArtifactMap map, ParserPool pool) {
        super(pool);

        artifactMap = map;
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
        HTTPInTransport inTransport = (HTTPInTransport) messageContext.getInboundMessage();

        if (!inTransport.getHTTPMethod().equalsIgnoreCase("GET")) {
            throw new MessageDecodingException("This message deocoder only supports the HTTP GET method");
        }

        SAML1ArtifactMessageContext artifactContext = (SAML1ArtifactMessageContext) messageContext;
        decodeTarget(artifactContext);
        decodeArtifacts(artifactContext);
        dereferenceArtifacts(artifactContext);

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

        String relyingPartyId = null;
        SAMLArtifactMapEntry artifactEntry;
        for (String encodedArtifact : encodedArtifacts) {
            artifactEntry = artifactMap.get(encodedArtifact);

            if (relyingPartyId == null) {
                relyingPartyId = artifactEntry.getRelyingPartyId();
            } else {
                if (!relyingPartyId.equals(artifactEntry.getRelyingPartyId())) {
                    throw new MessageDecodingException("Request SAML artifacts do not have the same relying party ID");
                }
            }
        }

        artifactContext.setInboundMessageIssuer(relyingPartyId);
        artifactContext.setArtifacts(encodedArtifacts);
    }

    /**
     * Derferences the artifacts within the incoming request and stores them in the request context.
     * 
     * @param requestContext current request context
     * 
     * @throws MessageDecodingException thrown if the incoming request does not contain any {@link AssertionArtifact}s.
     */
    protected void dereferenceArtifacts(SAML1ArtifactMessageContext requestContext) throws MessageDecodingException {
        Request request = (Request) requestContext.getInboundSAMLMessage();
        List<AssertionArtifact> assertionArtifacts = request.getAssertionArtifacts();

        if (assertionArtifacts == null || assertionArtifacts.size() == 0) {
            log.error("No AssertionArtifacts available in request");
            throw new MessageDecodingException("No AssertionArtifacts available in request");
        }

        ArrayList<Assertion> assertions = new ArrayList<Assertion>();
        SAMLArtifactMapEntry artifactEntry;
        Assertion assertion;
        for (AssertionArtifact assertionArtifact : assertionArtifacts) {
            artifactEntry = artifactMap.get(assertionArtifact.getAssertionArtifact());
            if (artifactEntry == null || artifactEntry.isExpired()) {
                continue;
            }

            artifactMap.remove(assertionArtifact.getAssertionArtifact());
            try {
                assertions.add((Assertion) artifactEntry.getSamlMessage());
            } catch (Exception e) {
                log.error("Unable to unmarshall assertion associated with artifact", e);
                throw new MessageDecodingException("Unable to unmarshall assertion associated with artifact", e);
            }
        }

        requestContext.setDereferencedAssertions(assertions);
    }
}