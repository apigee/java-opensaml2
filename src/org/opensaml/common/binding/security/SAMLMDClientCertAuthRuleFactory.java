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

package org.opensaml.common.binding.security;

import javax.servlet.ServletRequest;

import org.apache.log4j.Logger;
import org.opensaml.security.MetadataCriteria;
import org.opensaml.ws.security.SecurityPolicyContext;
import org.opensaml.ws.security.SecurityPolicyRule;
import org.opensaml.ws.security.provider.ClientCertAuthRuleFactory;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.trust.TrustEngine;
import org.opensaml.xml.security.x509.X509Credential;

/**
 * SAML specialization of {@link ClientCertAuthRuleFactory} which produces 
 * instances of {@link SAMLMDClientCertAuthRule}.
 */
public class SAMLMDClientCertAuthRuleFactory extends ClientCertAuthRuleFactory {

    /** {@inheritDoc} */
    public SecurityPolicyRule<ServletRequest> createRuleInstance() {
        return new SAMLMDClientCertAuthRule(getTrustEngine(), getCertificateNameOptions());
    }

    /**
     * SAML specialization of {@link ClientCertAuthRule} which provides support for 
     * X509Credential trust engine validation based on SAML metadta.
     */
    protected class SAMLMDClientCertAuthRule extends ClientCertAuthRule {
        
        /** Logger. */
        private Logger log = Logger.getLogger(SAMLMDClientCertAuthRule.class);

        /**
         * Constructor.
         *
         * @param engine Trust engine used to verify the request X509Credential
         * @param nameOptions options for deriving issuer names from an X.509 certificate
         */
        public SAMLMDClientCertAuthRule(TrustEngine<X509Credential> engine, CertificateNameOptions nameOptions) {
            super(engine, nameOptions);
        }

        /** {@inheritDoc} */
        protected CriteriaSet buildCriteriaSet(String entityID, ServletRequest request, XMLObject message, 
                SecurityPolicyContext context) {
            

            SAMLSecurityPolicyContext samlContext = (SAMLSecurityPolicyContext) context;
            if (samlContext == null) {
                log.error("Supplied context was not an instance of SAMLSecurityPolicyContext");
                throw new IllegalArgumentException("Supplied context was not an instance of SAMLSecurityPolicyContext");
            }
            
            CriteriaSet criteriaSet = super.buildCriteriaSet(entityID, request, message, context);
            MetadataCriteria mdCriteria = 
                new MetadataCriteria(samlContext.getIssuerRole(), samlContext.getIssuerProtocol());
            criteriaSet.add(mdCriteria);
            
            return criteriaSet;
        }

    }

}
