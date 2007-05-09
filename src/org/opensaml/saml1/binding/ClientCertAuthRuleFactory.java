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

package org.opensaml.saml1.binding;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import org.opensaml.common.binding.security.BaseX509CredentialAuthRule;
import org.opensaml.common.binding.security.BaseX509CredentialAuthRuleFactory;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.security.MetadataCredentialResolver;
import org.opensaml.ws.security.SecurityPolicyContext;
import org.opensaml.ws.security.SecurityPolicyException;
import org.opensaml.ws.security.SecurityPolicyRule;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.security.trust.TrustEngine;
import org.opensaml.xml.security.x509.X509Credential;

/**
 * Factory that produces rules that check if the client cert used to authenticate a request is valid and trusted. The
 * rule identifies the issuer as the entity identified by the cert's DN common name or subject alt names that match an
 * entity within the metadata from the given provider and where the trust engine validates the entity cert against the
 * information given in the assumed issuer's metadata.
 */
public class ClientCertAuthRuleFactory extends BaseX509CredentialAuthRuleFactory<HttpServletRequest, String> {

    /** {@inheritDoc} */
    public SecurityPolicyRule<HttpServletRequest, String> createRuleInstance() {
        return new ClientCertAuthRule(getTrustEngine(), getMetadataResolver(), 
                getMetadataProvider(), getIssuerRole(), getIssuerProtocol());
    }

    /**
     * Policy rule that checks if the client cert used to authenticate the request is valid and trusted.
     */
    protected class ClientCertAuthRule extends BaseX509CredentialAuthRule<HttpServletRequest, String> {
        
        /**
         * Constructor.
         * 
         * @param engine trust engine used to validate client cert against issuer's metadata
         * @param resolver resolver used to extract credential information from metadata
         * @param provider metadata provider used to look up entity information
         * @param role role the issuer is meant to be operating in
         * @param protocol protocol the issuer used in the request
         */
        public ClientCertAuthRule(
                TrustEngine<X509Credential, X509Credential> engine,
                MetadataCredentialResolver resolver,
                MetadataProvider provider, QName role, String protocol) {
                
            super(engine, resolver, provider, role, protocol);
        }
 
        
        /** {@inheritDoc} */
        public void evaluate(HttpServletRequest request, XMLObject message, SecurityPolicyContext<String> context)
                throws SecurityPolicyException {
            
            //TODO re-evaluate all this code
            //HttpRequestX509CredentialAdapter credential = new HttpRequestX509CredentialAdapter(request);
            //String issuer = evaluateCredential(credential, message);
            //context.setIssuer(issuer);
            
            /*
            try{
                RoleDescriptor role = getMetadataProvider().getRole(issuer, getIssuerRole(), getIssuerProtocol());
                //TODO type issue
                //context.setIssuerMetadata(role);
            }catch(MetadataProviderException e){
                throw new SecurityPolicyException("Unable to get issuer role descriptor from metadata", e);
            }
            */
        }
    }
}