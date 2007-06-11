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

import javax.servlet.ServletRequest;

import org.apache.log4j.Logger;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.security.SAMLSecurityPolicyContext;
import org.opensaml.common.binding.security.SAMLSecurityPolicyHelper;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.RequestAbstractType;
import org.opensaml.saml1.core.Response;
import org.opensaml.saml1.core.ResponseAbstractType;
import org.opensaml.ws.security.SecurityPolicyContext;
import org.opensaml.ws.security.SecurityPolicyException;
import org.opensaml.ws.security.SecurityPolicyRule;
import org.opensaml.ws.security.SecurityPolicyRuleFactory;
import org.opensaml.xml.XMLObject;

/**
 * An implementation of {@link SecurityPolicyRuleFactory} which generates rules which process SAML 1 messages and
 * extract relevant information out for use in other rules.
 * 
 * {@link SAML1ProtocolMessageRule}s pass if, and only if:
 * <ul>
 * <li>The SAML message is a {@link RequestAbstractType}, or</li>
 * <li>The SAML message is a {@link Response} and all the issuers, in the contained assertions, are identical</li>
 * </ul>
 * 
 * {@link SAML1ProtocolMessageRule}s rules operate on {@link SAMLSecurityPolicyContext}s.
 */
public class SAML1ProtocolMessageRuleFactory implements SecurityPolicyRuleFactory<ServletRequest> {

    /** {@inheritDoc} */
    public SecurityPolicyRule<ServletRequest> createRuleInstance() {
        return new SAML1ProtocolMessageRule();
    }

    /**
     * An implementation of {@link SecurityPolicyRule} which processes SAML 1 messages and extracts relevant information
     * out for use in other rules.
     */
    public class SAML1ProtocolMessageRule implements SecurityPolicyRule<ServletRequest> {

        /** {@inheritDoc} */
        public void evaluate(ServletRequest request, XMLObject message, SecurityPolicyContext context)
                throws SecurityPolicyException {

            Logger log = Logger.getLogger(SAML1ProtocolMessageRule.class);

            SAMLSecurityPolicyContext samlContext = (SAMLSecurityPolicyContext) context;
            if (samlContext == null) {
                log.error("Supplied context was not an instance of SAMLSecurityPolicyContext");
                throw new IllegalArgumentException("Supplied context was not an instance of SAMLSecurityPolicyContext");
            }

            SAMLObject samlMsg = SAMLSecurityPolicyHelper.getSAMLMessage(message);
            if (samlMsg == null) {
                log.warn("Could not extract SAML message");
                return;
            }

            if (samlMsg instanceof RequestAbstractType) {
                log.debug("Extracting ID, issuer and issue instant from request");
                extractRequestInfo(samlContext, (RequestAbstractType) samlMsg);
            } else if (samlMsg instanceof ResponseAbstractType) {
                log.debug("Extracting ID, issuer and issue instant from response");
                extractResponseInfo(samlContext, (ResponseAbstractType) samlMsg);
            } else {
                throw new SecurityPolicyException("SAML 1.x message was not a request or a response");
            }

            if (samlContext.getIssuer() == null) {
                log.warn("Issuer could not be extracted from SAML message");
                return;
            }
        }

        /**
         * Extract information from a SAML StatusResponse message.
         * 
         * @param samlContext the security policy context in which to store information
         * @param response the SAML message to process
         * 
         * @throws SecurityPolicyException thrown if the assertions within the response contain differening issuer IDs
         */
        protected void extractResponseInfo(SAMLSecurityPolicyContext samlContext, ResponseAbstractType response)
                throws SecurityPolicyException {
            Logger log = Logger.getLogger(SAML1ProtocolMessageRule.class);

            samlContext.setMessageID(response.getID());
            samlContext.setIssueInstant(response.getIssueInstant());

            // samlp:Response is known to carry issuer only via assertion(s) payload in standard SAML 1.x.
            if (response instanceof Response) {
                log.info("Attempting to extract issuer from enclosed SAML 1.x Assertion");
                String issuer = null;
                List<Assertion> assertions = ((Response) response).getAssertions();
                if (assertions != null) {
                    for (Assertion assertion : assertions) {
                        if (assertion != null && assertion.getIssuer() != null) {
                            if (issuer != null && !issuer.equals(assertion.getIssuer())) {
                                throw new SecurityPolicyException("SAML 1.x assertions, within response "
                                        + response.getID() + " contain different issuer IDs");
                            }
                            issuer = assertion.getIssuer();
                        }
                    }
                }

                samlContext.setIssuer(issuer);
            }
        }

        /**
         * Extract information from a SAML RequestAbstractType message.
         * 
         * @param samlContext the security policy context in which to store information
         * @param request the SAML message to process
         */
        protected void extractRequestInfo(SAMLSecurityPolicyContext samlContext, RequestAbstractType request) {
            samlContext.setMessageID(request.getID());
            samlContext.setIssueInstant(request.getIssueInstant());
            // Standard SAML 1.x request does not carry an issuer
        }
    }
}