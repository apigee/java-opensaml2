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

package org.opensaml.saml1.binding.security;

import java.util.List;

import org.apache.log4j.Logger;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.RequestAbstractType;
import org.opensaml.saml1.core.Response;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.security.SecurityPolicyException;
import org.opensaml.ws.security.SecurityPolicyRule;

/**
 * An implementation of {@link SecurityPolicyRule} which processes SAML 1 messages and extracts relevant information out
 * for use in other rules.
 * 
 * {@link SAML1ProtocolMessageRule}s pass if, and only if:
 * <ul>
 * <li>The SAML message is a {@link RequestAbstractType}, or</li>
 * <li>The SAML message is a {@link Response} and all the issuers, in the contained assertions, are identical</li>
 * </ul>
 * 
 * {@link SAML1ProtocolMessageRule}s rules operate on {@link SAMLMessageContext}s.
 */
public class SAML1ProtocolMessageRule implements SecurityPolicyRule {

    /** Logger. */
    private static Logger log = Logger.getLogger(SAML1ProtocolMessageRule.class);

    /** {@inheritDoc} */
    public void evaluate(MessageContext messageContext) throws SecurityPolicyException {
        if (!(messageContext instanceof SAMLMessageContext)) {
            log.debug("Invalid message context type, this policy rule only supports SAMLMessageContext");
            return;
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
        } else if (samlMsg instanceof Response) {
            log.debug("Extracting ID, issuer and issue instant from response");
            extractResponseInfo(samlMsgCtx, (Response) samlMsg);
        } else {
            throw new SecurityPolicyException("SAML 1.x message was not a request or a response");
        }

    }

    /**
     * Extract information from a SAML StatusResponse message.
     * 
     * @param messageContext current message context
     * @param response the SAML message to process
     * 
     * @throws SecurityPolicyException thrown if the assertions within the response contain differening issuer IDs
     */
    protected void extractResponseInfo(SAMLMessageContext messageContext, Response response)
            throws SecurityPolicyException {

        messageContext.setInboundSAMLMessageId(response.getID());
        messageContext.setInboundSAMLMessageIssueInstant(response.getIssueInstant());

        String issuer = null;
        List<Assertion> assertions = ((Response) response).getAssertions();
        if (assertions != null && assertions.size() > 0) {
            log.info("Attempting to extract issuer from enclosed SAML 1.x Assertion(s)");
            for (Assertion assertion : assertions) {
                if (assertion != null && assertion.getIssuer() != null) {
                    if (issuer != null && !issuer.equals(assertion.getIssuer())) {
                        throw new SecurityPolicyException("SAML 1.x assertions, within response " + response.getID()
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
     * Extract information from a SAML RequestAbstractType message.
     * 
     * @param messageContext current message context
     * @param request the SAML message to process
     */
    protected void extractRequestInfo(SAMLMessageContext messageContext, RequestAbstractType request) {
        messageContext.setInboundSAMLMessageId(request.getID());
        messageContext.setInboundSAMLMessageIssueInstant(request.getIssueInstant());
        // Standard SAML 1.x request does not carry an issuer
    }
}