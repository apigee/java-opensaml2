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

package org.opensaml.saml1.binding;

import javax.servlet.ServletRequest;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.SAMLSecurityPolicyContext;
import org.opensaml.common.binding.impl.AbstractSAMLSecurityPolicyRule;
import org.opensaml.common.binding.impl.AbstractSAMLSecurityPolicyRuleFactory;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.RequestAbstractType;
import org.opensaml.saml1.core.Response;
import org.opensaml.saml1.core.ResponseAbstractType;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.ws.security.SecurityPolicyContext;
import org.opensaml.ws.security.SecurityPolicyException;
import org.opensaml.ws.security.SecurityPolicyRule;
import org.opensaml.ws.security.SecurityPolicyRuleFactory;
import org.opensaml.ws.soap.util.SOAPConstants;
import org.opensaml.xml.XMLObject;

/**
 * An implementation of {@link SecurityPolicyRuleFactory} which generates rules which process
 * SAML 1 messages and extract relevant information out for use in other rules.
 */
public class SAML1MessageRuleFactory extends AbstractSAMLSecurityPolicyRuleFactory<ServletRequest, String>
    implements SecurityPolicyRuleFactory<ServletRequest, String> {


    /** {@inheritDoc} */
    public SecurityPolicyRule<ServletRequest, String> createRuleInstance() {
        return new SAML2MessageRule(getMetadataProvider(), getIssuerRole(), getIssuerProtocol());
    }

    /**
     * An implementation of {@link SecurityPolicyRule} which processes SAML 1 messages and extracts relevant 
     * information out for use in other rules.
     */
    public class SAML2MessageRule extends AbstractSAMLSecurityPolicyRule<ServletRequest, String> 
        implements SecurityPolicyRule<ServletRequest, String> {

        /**
         * Constructor.
         *
         * @param provider metadata provider used to look up entity information
         * @param role role the issuer is meant to be operating in
         * @param protocol protocol the issuer used in the request
         */
        public SAML2MessageRule(MetadataProvider provider, QName role, String protocol) {
            super(provider, role, protocol);
        }

        /** {@inheritDoc} */
        public void evaluate(ServletRequest request, XMLObject message,  SecurityPolicyContext<String> context)
            throws SecurityPolicyException {
            
            Logger log = Logger.getLogger(SAML2MessageRule.class);
            
            SAMLSecurityPolicyContext<String> samlContext = (SAMLSecurityPolicyContext<String>) context;
            if (samlContext == null) {
                log.error("Supplied context was not an instance of SAMLSecurityPolicyContext");
                throw new IllegalArgumentException("Supplied context was not an instance of SAMLSecurityPolicyContext");
            }
            
            QName msgQName = message.getElementQName();
            if (msgQName.getNamespaceURI().equals(SOAPConstants.SOAP11_NS)) {
                log.debug("Processing a SOAP 1.1 message");
            } else if (msgQName.getNamespaceURI().equals(SAMLConstants.SAML1P_NS)) {
                log.debug("Processing a SAML 1.x protocol message");
            } else {
                log.debug("Message was neither a SOAP envelope nor a SAML 1.x protocol message");
                return;
            }
            
            SAMLObject samlMsg = getSAMLMessage(message);
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
            }
            
            if (samlContext.getIssuer() == null) {
                log.warn("Issuer could not be extracted from SAML message");
                return;
            }
            
            if (log.isDebugEnabled()) {
                log.debug("Issuer entityID extracted was: " + samlContext.getIssuer());
            }
            
            RoleDescriptor rd = resolveIssuerRole(samlContext.getIssuer());
            samlContext.setIssuerMetadata(rd);
        }

        /**
         * Extract information from a SAML StatusResponse message.
         * 
         * @param samlContext the security policy context in which to store information
         * @param response the SAML message to process
         */
        private void extractResponseInfo(SAMLSecurityPolicyContext<String> samlContext, 
                ResponseAbstractType response) {
            Logger log = Logger.getLogger(SAML2MessageRule.class);
            
             samlContext.setMessageID(response.getID());
             samlContext.setIssueInstant(response.getIssueInstant());
             
             // samlp:Response is known to carry issuer only via payload in standard SAML 1.x.   
             //
             // Look at the first enclosed assertion
             // TODO - do we support case where assertions issued by mulitple providers
             if (response instanceof Response) {
                 log.info("Attempting to extract issuer from enclosed SAML 1.x Assertion");
                 Assertion assertion = ((Response) response).getAssertions().get(0);
                 if (assertion != null && assertion.getIssuer() != null) {
                     samlContext.setIssuer(assertion.getIssuer());
                 }
             }
        }

        /**
         * Extract information from a SAML RequestAbstractType message.
         * 
         * @param samlContext the security policy context in which to store information
         * @param request the SAML message to process
         */
        private void extractRequestInfo(SAMLSecurityPolicyContext<String> samlContext, RequestAbstractType request) {
            samlContext.setMessageID(request.getID());
            samlContext.setIssueInstant(request.getIssueInstant());
            // Standard SAML 1.x request does not carry an issuer
        }

    }
    
}
