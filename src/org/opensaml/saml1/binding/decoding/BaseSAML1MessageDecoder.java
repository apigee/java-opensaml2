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

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.common.binding.artifact.SAMLArtifactMap;
import org.opensaml.common.binding.artifact.SAMLArtifactMap.SAMLArtifactMapEntry;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.AssertionArtifact;
import org.opensaml.saml1.core.AttributeQuery;
import org.opensaml.saml1.core.AuthorizationDecisionQuery;
import org.opensaml.saml1.core.Request;
import org.opensaml.saml1.core.RequestAbstractType;
import org.opensaml.saml1.core.Response;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.ws.message.decoder.BaseMessageDecoder;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * Base class for SAML 1 message decoders.
 */
public abstract class BaseSAML1MessageDecoder extends BaseMessageDecoder {

    /** Class logger. */
    private Logger log = Logger.getLogger(BaseSAML1MessageDecoder.class);

    /** Map used to map artifacts to SAML. */
    private SAMLArtifactMap artifactMap;

    /** Whether to use the resource of an attribute query as the relying party entity ID. */
    private boolean useQueryResourceAsEntityId;

    /**
     * Constructor.
     * 
     * @param map used to map artifacts to SAML
     */
    public BaseSAML1MessageDecoder(SAMLArtifactMap map) {
        super();
        artifactMap = map;
        useQueryResourceAsEntityId = true;
    }

    /**
     * Constructor.
     * 
     * @param map used to map artifacts to SAML
     * @param pool parser pool used to deserialize messages
     */
    public BaseSAML1MessageDecoder(SAMLArtifactMap map, ParserPool pool) {
        super(pool);
        artifactMap = map;
        useQueryResourceAsEntityId = true;
    }

    /**
     * Gets the artifact map used to retrieve SAML information from an artifact.
     * 
     * @return artifact map used to retrieve SAML information from an artifact
     */
    public SAMLArtifactMap getArtifactMap() {
        return artifactMap;
    }

    /**
     * Gets whether to use the Resource attribute of some SAML 1 queries as the entity ID of the inbound message issuer.
     * 
     * @return whether to use the Resource attribute of some SAML 1 queries as the entity ID of the inbound message
     *         issuer
     */
    public boolean getUseQueryResourceAsEntityId() {
        return useQueryResourceAsEntityId;
    }

    /**
     * Sets whether to use the Resource attribute of some SAML 1 queries as the entity ID of the inbound message issuer.
     * 
     * @param useResource whether to use the Resource attribute of some SAML 1 queries as the entity ID of the inbound
     *            message issuer
     */
    public void setUseQueryResourceAsEntityId(boolean useResource) {
        useQueryResourceAsEntityId = useResource;
    }

    /**
     * Populates the message context with the message ID, issue instant, and issuer as well as the peer's entity
     * descriptor if a metadata provider is present in the message context and the peer's role descriptor if its entity
     * descriptor was retrieved and the message context has a populated peer role name.
     * 
     * @param messageContext message context to populate
     * 
     * @throws MessageDecodingException thrown if there is a problem populating the message context
     */
    protected void populateMessageContext(SAMLMessageContext messageContext) throws MessageDecodingException {
        populateMessageIdIssueInstantIssuer(messageContext);
        populateRelyingPartyMetadata(messageContext);
    }

    /**
     * Extracts the message ID, issue instant, and issuer from the incoming SAML message and populates the message
     * context with it.
     * 
     * @param messageContext current message context
     * 
     * @throws MessageDecodingException thrown if there is a problem populating the message context
     */
    protected void populateMessageIdIssueInstantIssuer(SAMLMessageContext messageContext)
            throws MessageDecodingException {
        SAMLObject samlMsg = messageContext.getInboundSAMLMessage();
        if (samlMsg == null) {
            return;
        }

        if (samlMsg instanceof RequestAbstractType) {
            log.debug("Extracting ID, issuer and issue instant from request");
            extractRequestInfo(messageContext, (RequestAbstractType) samlMsg);
        } else if (samlMsg instanceof Response) {
            log.debug("Extracting ID, issuer and issue instant from response");
            extractResponseInfo(messageContext, (Response) samlMsg);
        } else {
            throw new MessageDecodingException("SAML 1.x message was not a request or a response");
        }
    }

    /**
     * Extract information from a SAML RequestAbstractType message.
     * 
     * @param messageContext current message context
     * @param abstractRequest the SAML message to process
     */
    protected void extractRequestInfo(SAMLMessageContext messageContext, RequestAbstractType abstractRequest) {
        messageContext.setInboundSAMLMessageId(abstractRequest.getID());
        messageContext.setInboundSAMLMessageIssueInstant(abstractRequest.getIssueInstant());

        if (abstractRequest instanceof Request) {
            Request request = (Request) abstractRequest;
            if (request.getAttributeQuery() != null) {
                extractAttributeQueryInfo(messageContext, request.getAttributeQuery());
            }

            if (request.getAuthorizationDecisionQuery() != null) {
                extractAuthorizationDecisionQueryInfo(messageContext, request.getAuthorizationDecisionQuery());
            }

            if (request.getAssertionArtifacts() != null) {
                extractAssertionArtifactInfo(messageContext, request.getAssertionArtifacts());
            }
        }
    }

    /**
     * Extract the issuer, and populate message context, from the Resource attribute of the Attribute query if
     * {@link #useQueryResourceAsEntityId} is true.
     * 
     * @param messageContext current message context
     * @param query query to extract resource name from
     */
    protected void extractAttributeQueryInfo(SAMLMessageContext messageContext, AttributeQuery query) {
        if (useQueryResourceAsEntityId) {
            if (log.isDebugEnabled()) {
                log.debug("Attempting to extract issuer from SAML 1 AttributeQuery Resource attribute");
            }
            String resource = DatatypeHelper.safeTrimOrNullString(query.getResource());

            if (resource != null) {
                messageContext.setInboundMessageIssuer(resource);
                if (log.isDebugEnabled()) {
                    log.debug("Extracted issuer from SAML 1.x AttributeQuery: " + resource);
                }
            }
        }
    }

    /**
     * Extract the issuer, and populate message context, from the Resource attribute of the AuthorizationDecisionQuery
     * query if {@link #useQueryResourceAsEntityId} is true.
     * 
     * @param messageContext current message context
     * @param query query to extract resource name from
     */
    protected void extractAuthorizationDecisionQueryInfo(SAMLMessageContext messageContext,
            AuthorizationDecisionQuery query) {
        if (useQueryResourceAsEntityId) {
            if (log.isDebugEnabled()) {
                log.debug("Attempting to extract issuer from SAML 1 AuthorizationDecisionQuery Resource attribute");
            }
            String resource = DatatypeHelper.safeTrimOrNullString(query.getResource());

            if (resource != null) {
                messageContext.setInboundMessageIssuer(resource);
                if (log.isDebugEnabled()) {
                    log.debug("Extracted issuer from SAML 1.x AuthorizationDecisionQuery: " + resource);
                }
            }
        }
    }

    /**
     * Extract the issuer, and populate message context, as the relying party corresponding to the first
     * AssertionArtifact in the message.
     * 
     * @param messageContext current message context
     * @param artifacts AssertionArtifacts in the request
     */
    protected void extractAssertionArtifactInfo(SAMLMessageContext messageContext, List<AssertionArtifact> artifacts) {
        if (artifacts.size() == 0) {
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("Attempting to extract issuer based on first AssertionArtifact in request");
        }
        AssertionArtifact artifact = artifacts.get(0);
        SAMLArtifactMapEntry artifactEntry = artifactMap.get(artifact.getAssertionArtifact());
        messageContext.setInboundMessageIssuer(artifactEntry.getRelyingPartyId());

        if (log.isDebugEnabled()) {
            log.debug("Extracted issuer from SAML 1.x AssertionArtifact: " + messageContext.getInboundMessageIssuer());
        }
    }

    /**
     * Extract information from a SAML StatusResponse message.
     * 
     * @param messageContext current message context
     * @param response the SAML message to process
     * 
     * @throws MessageDecodingException thrown if the assertions within the response contain differening issuer IDs
     */
    protected void extractResponseInfo(SAMLMessageContext messageContext, Response response)
            throws MessageDecodingException {

        messageContext.setInboundSAMLMessageId(response.getID());
        messageContext.setInboundSAMLMessageIssueInstant(response.getIssueInstant());

        String issuer = null;
        List<Assertion> assertions = ((Response) response).getAssertions();
        if (assertions != null && assertions.size() > 0) {
            log.info("Attempting to extract issuer from enclosed SAML 1.x Assertion(s)");
            for (Assertion assertion : assertions) {
                if (assertion != null && assertion.getIssuer() != null) {
                    if (issuer != null && !issuer.equals(assertion.getIssuer())) {
                        throw new MessageDecodingException("SAML 1.x assertions, within response " + response.getID()
                                + " contain different issuer IDs");
                    }
                    issuer = assertion.getIssuer();
                }
            }
        }

        if (issuer == null) {
            log.warn("Issuer could not be extracted from standard SAML 1.x response message");
        }

        messageContext.setInboundMessageIssuer(issuer);
    }

    /**
     * Populates the peer's entity metadata if a metadata provide is present in the message context. Populates the
     * peer's role descriptor if the entity metadata was available and the role name is present in the message context.
     * 
     * @param messageContext current message context
     * 
     * @throws MessageDecodingException thrown if there is a problem populating the message context
     */
    protected void populateRelyingPartyMetadata(SAMLMessageContext messageContext) throws MessageDecodingException {
        MetadataProvider metadataProvider = messageContext.getMetadataProvider();
        try {
            if (metadataProvider != null) {
                EntityDescriptor relyingPartyMD = metadataProvider.getEntityDescriptor(messageContext
                        .getInboundMessageIssuer());
                messageContext.setPeerEntityMetadata(relyingPartyMD);

                QName relyingPartyRole = messageContext.getPeerEntityRole();
                if (relyingPartyMD != null && relyingPartyRole != null) {
                    List<RoleDescriptor> roles = relyingPartyMD.getRoleDescriptors(relyingPartyRole,
                            SAMLConstants.SAML11P_NS);
                    if (roles != null && roles.size() > 0) {
                        messageContext.setPeerEntityRoleMetadata(roles.get(0));
                    }
                }
            }
        } catch (MetadataProviderException e) {
            log.error("Error retrieving metadata for relying party " + messageContext.getInboundMessageIssuer(), e);
            throw new MessageDecodingException("Error retrieving metadata for relying party "
                    + messageContext.getInboundMessageIssuer(), e);
        }
    }
}