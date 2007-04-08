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

package org.opensaml.common.binding.impl;

import javax.servlet.ServletRequest;
import javax.xml.namespace.QName;

import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.security.MetadataCredentialResolver;
import org.opensaml.ws.security.SecurityPolicyRule;
import org.opensaml.ws.security.SecurityPolicyRuleFactory;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xml.security.trust.TrustEngine;
import org.opensaml.xml.security.x509.X509Credential;

/**
 * Factory that produces rules that check if the client cert used to authenticate a request is valid and trusted. The
 * rule identifies the issuer as the entity identified by the cert's DN common name or subject alt names that match an
 * entity within the metadata from the given provider and where the trust engine validates the entity cert against the
 * information given in the assumed issuer's metadata.
 * 
 * @param <RequestType> type of request to extract the credential from
 * @param <IssuerType> the message issuer type
 */
public abstract class BaseX509CredentialAuthRuleFactory<RequestType extends ServletRequest, IssuerType> 
        extends AbstractSAMLSecurityPolicyRuleFactory<RequestType, IssuerType>
        implements SecurityPolicyRuleFactory<RequestType, IssuerType> {

    /** Trust engine used to verify metadata. */
    private TrustEngine<X509Credential, X509Credential> trustEngine;

    /** Resolver used to extract key information from a key source. */
    private KeyInfoCredentialResolver keyInfoResolver;
    
    /** Metadata credential resolver.  Will be constructed by using Metadata provider, 
     * role, protocol and KeyInfo credential provider information. */
    private MetadataCredentialResolver metadataResolver;

    /**
     * Gets the engine used to validate the trustworthiness of digital certificates.
     * 
     * @return engine used to validate the trustworthiness of digital certificates
     */
    public TrustEngine<X509Credential, X509Credential> getTrustEngine() {
        return trustEngine;
    }

    /**
     * Sets the engine used to validate the trustworthiness of digital certificates.
     * 
     * @param engine engine used to validate the trustworthiness of digital certificates
     */
    public void setTrustEngine(TrustEngine<X509Credential, X509Credential> engine) {
        trustEngine = engine;
    }

    /**
     * Gets the resolver used to extract credential information from KeyInfo elements in
     * SAML 2 metadata.
     * 
     * @return resolver used to extract credential information from KeyInfo in metadata
     */
    public KeyInfoCredentialResolver getKeyInfoCredentialResolver() {
        return keyInfoResolver;
    }

    /**
     * Sets the resolver used to extract credential information from KeyInfo elements in 
     * SAML 2 metadata.
     * 
     * @param credentialResolver resolver used to extract credential information from KeyInfo
     *          in metadata
     */
    public void setKeyInfoCredentialResolver(KeyInfoCredentialResolver credentialResolver) {
        keyInfoResolver = credentialResolver;

    }
    
    /** {@inheritDoc} */
    public void setIssuerProtocol(String protocol) {
        super.setIssuerProtocol(protocol);
        metadataResolver = null;
    }

    /** {@inheritDoc} */
    public void setIssuerRole(QName role) {
        super.setIssuerRole(role);
        metadataResolver = null;
    }

    /** {@inheritDoc} */
    public void setMetadataProvider(MetadataProvider provider) {
        super.setMetadataProvider(provider);
        metadataResolver = null;
    }

    /**
     * Get the resolver used to resolve credentials from SAML 2 metadata.
     * 
     * Note; This resolver is constructed dynamically based on the current factory values
     * for 1) metadata provider 2) role name and 3) protocol.  If any of these changes, the 
     * metadata resolver will be reinstantiated with the new values.
     * 
     * @return an instance of MetadtaCredentialResolver
     */
    public MetadataCredentialResolver getMetadataResolver() {
        if (metadataResolver == null) {
            metadataResolver = buildNewMetadataResolver();
        }
        return metadataResolver;
    }
    
    /**
     * Build a new instance of MetadataCredentialResolver based on the current factory
     * values for metadata provider, role name and protocol.
     * 
     * @return new instance of MetadataCredentialResolver
     */
    private MetadataCredentialResolver buildNewMetadataResolver() {
        MetadataCredentialResolver resolver = 
            new MetadataCredentialResolver(getMetadataProvider());
        
        if (getKeyInfoCredentialResolver() != null) {
            resolver.setKeyInfoCredentialResolver(this.getKeyInfoCredentialResolver());
        }
        return resolver;
    }

    /** {@inheritDoc} */
    public abstract SecurityPolicyRule<RequestType, IssuerType> createRuleInstance();
}