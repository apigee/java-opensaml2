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

package org.opensaml.saml2.binding;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import org.opensaml.common.binding.impl.BaseX509CredentialAuthRule;
import org.opensaml.common.binding.impl.BaseX509CredentialAuthRuleFactory;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.RequestAbstractType;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.ws.security.HttpRequestX509CredentialAdapter;
import org.opensaml.ws.security.SecurityPolicyContext;
import org.opensaml.ws.security.SecurityPolicyException;
import org.opensaml.ws.security.SecurityPolicyRule;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.security.X509KeyInfoResolver;
import org.opensaml.xml.security.trust.EntityCredentialTrustEngine;
import org.opensaml.xml.security.x509.X509Credential;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * Factory that produces rules that check if the client cert used to authenticate a request is valid and trusted. The
 * rule identifies the issuer as the entity identified by the cert's DN common name or subject alt names that match an
 * entity within the metadata from the given provider and where the trust engine validates the entity cert against the
 * information given in the assumed issuer's metadata.
 */
public class ClientCertAuthRuleFactory  extends BaseX509CredentialAuthRuleFactory<Issuer> {

    /** {@inheritDoc} */
    public SecurityPolicyRule<HttpServletRequest, Issuer> createRuleInstance() {
        return new ClientCertAuthRule(getMetadataProvider(), getTrustEngine(), getKeyResolver(), getIssuerRole(),
                getIssuerProtocol());
    }

    /**
     * Policy rule that checks if the client cert used to authenticate the request is valid and trusted.
     */
    protected class ClientCertAuthRule extends BaseX509CredentialAuthRule<HttpServletRequest, Issuer> {
        
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
                EntityCredentialTrustEngine<X509Credential, X509KeyInfoResolver> engine,
                X509KeyInfoResolver x509KeyResolver, QName role, String protocol) {
            super(provider, engine, x509KeyResolver, role, protocol);
        }

        /** {@inheritDoc} */
        public void evaluate(HttpServletRequest request, 
                             XMLObject message, 
                             SecurityPolicyContext<Issuer> context) 
                throws SecurityPolicyException {
            //TODO re-evaluate all this code
            Issuer requestIssuerName = evaluateCredential(new HttpRequestX509CredentialAdapter(request), message);
            Issuer messageIssuerName = getSAML2Issuer(message);

            //if (DatatypeHelper.safeEquals(requestIssuerName, messageIssuerName)) {
            //    throw new SecurityPolicyException("SAML 2 message issuer does not match request issuer name");
            //}
        }

        /**
         * Gets the issure name from the SAML 2 message.
         * 
         * @param message the SAML 2 message
         * 
         * @return the issuer name from the SAML 2 message
         * 
         * @throws SecurityPolicyException thrown if the SAML 2 message does not contain a valid issuer name
         */
        private Issuer getSAML2Issuer(XMLObject message) throws SecurityPolicyException {
            Issuer issuer = null;
            if (message instanceof RequestAbstractType) {
                issuer = ((RequestAbstractType) message).getIssuer();
            } else if (message instanceof Response) {
                issuer = ((Response) message).getIssuer();
            }

            if (issuer == null || DatatypeHelper.isEmpty(issuer.getValue())) {
                throw new SecurityPolicyException("Expected SAML2 RequestAbstractType message does not contain a valid Issuer.");
            }
            return issuer;
        }
    }
}