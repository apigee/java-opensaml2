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

import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.SecurityPolicyRule;
import org.opensaml.common.binding.impl.BaseX509CredentialAuthRule;
import org.opensaml.common.binding.impl.BaseX509CredentialAuthRuleFactory;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.ws.security.HttpX509EntityCredential;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.security.EntityCredentialTrustEngine;
import org.opensaml.xml.security.X509EntityCredential;
import org.opensaml.xml.security.X509KeyInfoResolver;

/**
 * Factory that produces rules that check if the client cert used to authenticate a request is valid and trusted. The
 * rule identifies the issuer as the entity identified by the cert's DN common name or subject alt names that match an
 * entity within the metadata from the given provider and where the trust engine validates the entity cert against the
 * information given in the assumed issuer's metadata.
 */
public class ClientCertAuthRuleFactory extends BaseX509CredentialAuthRuleFactory {

    /** {@inheritDoc} */
    public SecurityPolicyRule<HttpServletRequest> createRuleInstance() {
        return new ClientCertAuthRule(getMetadataProvider(), getTrustEngine(), getKeyResolver(), getIssuerRole(),
                getIssuerProtocol());
    }

    /**
     * Policy rule that checks if the client cert used to authenticate the request is valid and trusted.
     */
    protected class ClientCertAuthRule extends BaseX509CredentialAuthRule<HttpServletRequest> {

        /**
         * Constructor.
         * 
         * @param provider metadata provider used to look up entity information
         * @param engine trust engine used to validate client cert against issuer's metadata
         * @param x509KeyResolver resolver used to extract key information from a key source
         * @param role role the issuer is meant to be operating in
         * @param protocol protocol the issuer used in the request
         */
        public ClientCertAuthRule(MetadataProvider provider,
                EntityCredentialTrustEngine<X509EntityCredential, X509KeyInfoResolver> engine,
                X509KeyInfoResolver x509KeyResolver, QName role, String protocol) {
            super(provider, engine, x509KeyResolver, role, protocol);
        }
        
        /** {@inheritDoc} */
        public void evaluate(HttpServletRequest request, XMLObject message) throws BindingException{
            HttpX509EntityCredential credential = new HttpX509EntityCredential(request);
            String issuer = evaluateCredential(credential, message);
            setIssuer(issuer);
            
            try{
                RoleDescriptor role = getMetadataProvider().getRole(issuer, getIssuerRole(), getIssuerProtocol());
                setIssuerMetadata(role);
            }catch(MetadataProviderException e){
                throw new BindingException("Unable to get issuer role descriptor from metadata", e);
            }
        }
    }
}