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
import org.opensaml.ws.security.provider.BaseTrustEngineRule;
import org.opensaml.ws.security.provider.BaseTrustEngineRuleFactory;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.credential.EntityCriteria;
import org.opensaml.xml.security.credential.UsageCriteria;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.security.trust.TrustEngine;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * Base class for a factory which produces SAML security policy rules which evaluate a signature 
 * with a signature trust engine.
 */
public abstract class BaseSAMLSignatureSecurityPolicyRuleFactory 
        extends BaseTrustEngineRuleFactory<Signature, ServletRequest> {
    
    /**
     * Base class for SAML security policy rules which evaluate a signature with a signature trust engine.
     */
    protected abstract class BaseSAMLSignatureSecurityPolicyRule 
        extends BaseTrustEngineRule<Signature, ServletRequest> {
        
        /** Logger. */
        private Logger log = Logger.getLogger(BaseSAMLSignatureSecurityPolicyRule.class);
        
        /**
         * Constructor.
         *
         * @param engine Trust engine used to verify the signature
         */
        public BaseSAMLSignatureSecurityPolicyRule(TrustEngine<Signature> engine) {
            super(engine);
        }

        /** {@inheritDoc} */
        protected CriteriaSet buildCriteriaSet(String entityID, ServletRequest request, XMLObject message,
                SecurityPolicyContext context) {
            
            SAMLSecurityPolicyContext samlContext = (SAMLSecurityPolicyContext) context;
            if (samlContext == null) {
                log.error("Supplied context was not an instance of SAMLSecurityPolicyContext");
                throw new IllegalArgumentException("Supplied context was not an instance of SAMLSecurityPolicyContext");
            }
            
            CriteriaSet criteriaSet = new CriteriaSet();
            if (! DatatypeHelper.isEmpty(entityID)) {
                criteriaSet.add(new EntityCriteria(entityID, null));
            }
            
            MetadataCriteria mdCriteria = 
                new MetadataCriteria(samlContext.getIssuerRole(), samlContext.getIssuerProtocol());
            criteriaSet.add(mdCriteria);
            
            criteriaSet.add( new UsageCriteria(UsageType.SIGNING) );
            
            return criteriaSet;
        }
        
    } 

}
