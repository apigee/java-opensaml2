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

package org.opensaml.saml2.binding.security;

import java.util.List;

import org.apache.log4j.Logger;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.saml2.core.RequestAbstractType;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.StatusResponseType;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.security.SecurityPolicyException;
import org.opensaml.ws.security.SecurityPolicyRule;

/**
 * An implementation of {@link SecurityPolicyRuleFactory} which generates rules which process SAML 2 messages and
 * extract relevant information out for use in other rules.
 * 
 * {@link SAML2ProtocolMessageRule}s pass if, and only if:
 * <ul>
 * <li>Evaluated {@link Issuer}s are of format {@link NameIDType#ENTITY} or do not contain a format</li>
 * <li>The SAML message is a {@link RequestAbstractType}, or</li>
 * <li>The SAML message is a {@link StatusResponseType} and
 * <ul>
 * <li> The {@link StatusResponseType} contains an {@link Issuer}, or</li>
 * <li> The {@link StatusResponseType} is a {@link Response} and the {@link Issuer}s within its {@link Assertion}s are
 * identical</li>
 * </ul>
 * </li>
 * </ul>
 */
public class SAML2ProtocolMessageRule implements SecurityPolicyRule {

    /** Logger. */
    private static Logger log = Logger.getLogger(SAML2ProtocolMessageRule.class);

    /** Constructor. */
    public SAML2ProtocolMessageRule() {

    }

    /** {@inheritDoc} */
    public boolean evaluate(org.opensaml.ws.message.MessageContext messageContext) throws SecurityPolicyException {
        if (!(messageContext instanceof MessageContext)) {
            log.debug("Invalid message context type, this policy rule only support SAMLMessageContext");
            return false;
        }
        SAMLMessageContext samlMsgCtx = (SAMLMessageContext) messageContext;

        SAMLObject samlMsg = samlMsgCtx.getInboundSAMLMessage();
        if (samlMsg == null) {
            log.error("Message context did not contain inbound SAML message");
            throw new SecurityPolicyException("Message context did not contain inbound SAML message");
        }

        if (samlMsg instanceof RequestAbstractType) {
            log.debug("Extracting ID, issuer and issue instant from request");
            extractRequestInfo(samlMsgCtx, (RequestAbstractType) samlMsg);
        } else if (samlMsg instanceof StatusResponseType) {
            log.debug("Extracting ID, issuer and issue instant from status response");
            extractResponseInfo(samlMsgCtx, (StatusResponseType) samlMsg);
        } else {
            throw new SecurityPolicyException("SAML 2 message was not a request or a response");
        }

        if (samlMsgCtx.getRelyingPartyEntityId() == null) {
            log.error("Issuer could not be extracted from SAML 2 message");
            throw new SecurityPolicyException("Issuer could not be extracted from SAML 2 message");
        }

        return true;
    }

    /**
     * Extract information from a SAML StatusResponse message.
     * 
     * @param messageContext current message context
     * @param statusResponse the SAML message to process
     * 
     * @throws SecurityPolicyException thrown if the response issuer has a format other than {@link NameIDType#ENTITY}
     *             or, if the response does not contain an issuer, if the contained assertions contain issuers that are
     *             not of {@link NameIDType#ENTITY} format or if the assertions contain different issuers
     */
    protected void extractResponseInfo(SAMLMessageContext messageContext, StatusResponseType statusResponse)
            throws SecurityPolicyException {

        messageContext.setInboundSAMLMessageId(statusResponse.getID());
        messageContext.setInboundSAMLMessageIssueInstant(statusResponse.getIssueInstant());

        // If response doesn't have an issuer, look at the first
        // enclosed assertion
        String messageIssuer = null;
        if (statusResponse.getIssuer() != null) {
            messageIssuer = extractEntityId(statusResponse.getIssuer());
        } else if (statusResponse instanceof Response) {
            List<Assertion> assertions = ((Response) statusResponse).getAssertions();
            if (assertions != null && assertions.size() > 0) {
                log.info("Status response message had no issuer, "
                        + "attempting to extract issuer from enclosed Assertion(s)");
                String assertionIssuer;
                for (Assertion assertion : assertions) {
                    if (assertion != null && assertion.getIssuer() != null) {
                        assertionIssuer = extractEntityId(assertion.getIssuer());
                        if (messageIssuer != null && !messageIssuer.equals(assertionIssuer)) {
                            throw new SecurityPolicyException("SAML 2 assertions, within response "
                                    + statusResponse.getID() + " contain different issuer IDs");
                        }
                        messageIssuer = assertionIssuer;
                    }
                }
            }
        }

        messageContext.setRelyingPartyEntityId(messageIssuer);
    }

    /**
     * Extract information from a SAML RequestAbstractType message.
     * 
     * @param messageContext current message context
     * @param request the SAML message to process
     * 
     * @throws SecurityPolicyException thrown if the request issuer has a format other than {@link NameIDType#ENTITY}
     */
    protected void extractRequestInfo(SAMLMessageContext messageContext, RequestAbstractType request)
            throws SecurityPolicyException {
        messageContext.setInboundSAMLMessageId(request.getID());
        messageContext.setInboundSAMLMessageIssueInstant(request.getIssueInstant());
        messageContext.setRelyingPartyEntityId(extractEntityId(request.getIssuer()));
    }

    /**
     * Extracts the entity ID from the SAML 2 Issuer.
     * 
     * @param issuer issuer to extract the entityID from
     * 
     * @return entity ID of the issuer
     * 
     * @throws SecurityPolicyException thrown if the given issuer has a format other than {@link NameIDType#ENTITY}
     */
    protected String extractEntityId(Issuer issuer) throws SecurityPolicyException {
        if (issuer != null) {
            if (issuer.getFormat() == null || issuer.getFormat().equals(NameIDType.ENTITY)) {
                return issuer.getValue();
            } else {
                throw new SecurityPolicyException("SAML 2 Issuer is not of ENTITY format type");
            }
        }

        return null;
    }
}