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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import javolution.util.FastList;

import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.SecurityPolicyRule;
import org.opensaml.common.binding.SecurityPolicyRuleFactory;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Request;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.security.TrustEngine;
import org.opensaml.security.X509EntityCredential;
import org.opensaml.security.X509Util;
import org.opensaml.security.impl.HttpX509EntityCredential;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.DatatypeHelper;

public class ClientCertAuthRuleFactory implements SecurityPolicyRuleFactory<HttpServletRequest> {

    /** Metadata provider to lookup issuer information */
    private MetadataProvider metadataProvider;

    /** Trust engine used to verify metadata */
    private TrustEngine<X509EntityCredential> trustEngine;

    /** The SAML role the issuer is meant to be operating in */
    private QName issuerRole;

    /**
     * Gets the metadata provider used to lookup issuer data.
     * 
     * @return metadata provider used to lookup issuer data
     */
    public MetadataProvider getMetadataProvider() {
        return metadataProvider;
    }

    /**
     * Sets the metadata provider used to lookup issuer data.
     * 
     * @param provider metadata provider used to lookup issuer data
     */
    public void setMetadataProvider(MetadataProvider provider) {
        metadataProvider = provider;
    }

    /**
     * Gets the engine used to validate the trustworthiness of digital certificates.
     * 
     * @return engine used to validate the trustworthiness of digital certificates
     */
    public TrustEngine<X509EntityCredential> getTrustEngine() {
        return trustEngine;
    }

    /**
     * Sets the engine used to validate the trustworthiness of digital certificates.
     * 
     * @param engine engine used to validate the trustworthiness of digital certificates
     */
    public void setTrustEngine(TrustEngine<X509EntityCredential> engine) {
        trustEngine = engine;
    }

    /**
     * Gets the role the issuer is meant to be operating in.
     * 
     * @return role the issuer is meant to be operating in
     */
    public QName getIssuerRole() {
        return issuerRole;
    }

    /**
     * Sets role the issuer is meant to be operating in.
     * 
     * @param issuerRole role the issuer is meant to be operating in
     */
    public void setIssuerRole(QName issuerRole) {
        this.issuerRole = issuerRole;
    }

    /** {@inheritDoc} */
    public SecurityPolicyRule<HttpServletRequest> createRuleInstance() {
        return new ClientCertAuthRule(getMetadataProvider(), getTrustEngine(), getIssuerRole());
    }

    /**
     * Policy rule that checks if the client cert used to authenticate the request is valid and trusted.
     */
    protected class ClientCertAuthRule implements SecurityPolicyRule<HttpServletRequest> {
        /** Metadata provider to lookup issuer information */
        private MetadataProvider metadataProvider;

        /** Trust engine used to verify metadata */
        private TrustEngine<X509EntityCredential> trustEngine;

        /** SAML role the issuer is meant to be operating in */
        private QName issuerRole;

        /** Issuer as determined by this rule */
        private String issuer;

        /**
         * Constructor
         * 
         * @param provider metadata provider used to look up entity information
         * @param engine trust engine used to validate client cert against issuer's metadata
         * @param role role the issuer is meant to be operating in
         */
        public ClientCertAuthRule(MetadataProvider provider, TrustEngine<X509EntityCredential> engine, QName role) {
            metadataProvider = provider;
            trustEngine = engine;
            issuerRole = role;
        }

        /** {@inheritDoc} */
        public String getIssuer() {
            return issuer;
        }

        /** {@inheritDoc} */
        public void evaluate(HttpServletRequest request, XMLObject message) throws BindingException {
            String requestIssuerName = evaluateCredential(request);
            String messageIssuerName = getSAML2IssuerName(message);

            if (DatatypeHelper.safeEquals(requestIssuerName, messageIssuerName)) {
                throw new BindingException("SAML 2 message issuer name does not match request issuer name");
            }

        }

        /**
         * Evaluates the client cert used by the request against the metadata for the given role using the given trust
         * engine. The issuer is the cert subject DN common name or subject alt name for which metadata exists.
         * 
         * @param request the request to validate
         * 
         * @return the issuer name extracted from the credential
         * 
         * @throws BindingException thrown if the client cert can not be validated
         */
        private String evaluateCredential(HttpServletRequest request) throws BindingException {
            HttpX509EntityCredential credential;
            try {
                credential = new HttpX509EntityCredential(request);
            } catch (IllegalArgumentException e) {
                throw new BindingException("Unable to extract client certificate from request");
            }

            POSSIBLE_ISSUERS: for (String issuerName : getIssuerNames(credential)) {
                try {
                    List<RoleDescriptor> roleDescriptors = metadataProvider.getRole(issuerName, issuerRole);
                    if (roleDescriptors == null || roleDescriptors.size() == 0) {
                        continue POSSIBLE_ISSUERS;
                    }

                    for (RoleDescriptor roleDescriptor : roleDescriptors) {
                        if (trustEngine.validate(credential, roleDescriptor)) {
                            issuer = issuerName;
                            break POSSIBLE_ISSUERS;
                        } else {
                            throw new BindingException(
                                    "Issuer credentials do not match entity's credentials in metadata");
                        }
                    }
                } catch (MetadataProviderException e) {
                    throw new BindingException("Unable to query metadata provider for issuer metadata", e);
                }
            }

            throw new BindingException("Issuer can bot be located in metadata");
        }

        /**
         * Gets the list of possible issuer names, pulled from the credential's entity certificate subject and subject
         * alt names.
         * 
         * @param credential the credental of the entity that issued the request
         * 
         * @return possible issuer names
         */
        private List<String> getIssuerNames(HttpX509EntityCredential credential) {
            FastList<String> issuerNames = new FastList<String>();

            List<String> entityCertCNs = X509Util.getCommonNames(credential.getEntityCertificate()
                    .getSubjectX500Principal());
            issuerNames.add(entityCertCNs.get(0));

            Integer[] altNameTypes = { X509Util.DNS_ALT_NAME, X509Util.URI_ALT_NAME };
            issuerNames.addAll(X509Util.getAltNames(credential.getEntityCertificate(), altNameTypes));

            return issuerNames;
        }

        /**
         * Gets the issure name from the SAML 2 message.
         * 
         * @param message the SAML 2 message
         * 
         * @return the issuer name from the SAML 2 message
         * 
         * @throws BindingException thrown if the SAML 2 message does not contain a valid issuer name
         */
        private String getSAML2IssuerName(XMLObject message) throws BindingException {
            Issuer issuer = null;
            if (message instanceof Request) {
                issuer = ((Request) message).getIssuer();
            } else if (message instanceof Response) {
                issuer = ((Response) message).getIssuer();
            }

            if (issuer == null || DatatypeHelper.isEmpty(issuer.getValue())) {
                throw new BindingException("Expected SAML2 Request message does not contain a valid Issuer.");
            }
            return issuer.getValue();
        }
    }
}