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

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.ws.security.SecurityPolicyRule;
import org.opensaml.ws.security.SecurityPolicyRuleFactory;
import org.opensaml.xml.security.X509KeyInfoResolver;
import org.opensaml.xml.security.trust.EntityCredentialTrustEngine;
import org.opensaml.xml.security.x509.X509Credential;
import org.opensaml.xml.security.x509.X509Util;

/**
 * Factory that produces rules that check if the client cert used to authenticate a request is valid and trusted. The
 * rule identifies the issuer as the entity identified by the cert's DN common name or subject alt names that match an
 * entity within the metadata from the given provider and where the trust engine validates the entity cert against the
 * information given in the assumed issuer's metadata.
 * 
 * @param <IssuerType> the message issuer type
 */
public abstract class BaseX509CredentialAuthRuleFactory<IssuerType> 
        implements SecurityPolicyRuleFactory<HttpServletRequest, IssuerType> {
    
    /** Subject alt names checked by the rule produced. */
    public static final Integer[] SUBJECT_ALT_NAMES = {X509Util.DNS_ALT_NAME, X509Util.URI_ALT_NAME};

    /** Metadata provider to lookup issuer information. */
    private MetadataProvider metadataProvider;

    /** Trust engine used to verify metadata. */
    private EntityCredentialTrustEngine<X509Credential, X509KeyInfoResolver> trustEngine;

    /** Resolver used to extract key information from a key source. */
    private X509KeyInfoResolver keyResolver;

    /** The SAML role the issuer is meant to be operating in. */
    private QName issuerRole;

    /** The message protocol used by the issuer. */
    private String issuerProtocol;

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
    public EntityCredentialTrustEngine<X509Credential, X509KeyInfoResolver> getTrustEngine() {
        return trustEngine;
    }

    /**
     * Sets the engine used to validate the trustworthiness of digital certificates.
     * 
     * @param engine engine used to validate the trustworthiness of digital certificates
     */
    public void setTrustEngine(EntityCredentialTrustEngine<X509Credential, X509KeyInfoResolver> engine) {
        trustEngine = engine;
    }

    /**
     * Gets the resolver used to extract key information from a key source.
     * 
     * @return resolver used to extract key information from a key source
     */
    public X509KeyInfoResolver getKeyResolver() {
        return keyResolver;
    }

    /**
     * Sets the resolver used to extract key information from a key source.
     * 
     * @param x509KeyResolver resolver used to extract key information from a key source
     */
    public void setKeyResolver(X509KeyInfoResolver x509KeyResolver) {

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
     * @param role role the issuer is meant to be operating in
     */
    public void setIssuerRole(QName role) {
        issuerRole = role;
    }

    /**
     * Gets the message protocol used by the issuer.
     * 
     * @return message protocol used by the issuer
     */
    public String getIssuerProtocol() {
        return issuerProtocol;
    }

    /**
     * Sets message protocol used by the issuer.
     * 
     * @param protocol message protocol used by the issuer
     */
    public void setIssuerProtocol(String protocol) {
        this.issuerProtocol = protocol;
    }

    /** {@inheritDoc} */
    public abstract SecurityPolicyRule<HttpServletRequest, IssuerType> createRuleInstance();
}