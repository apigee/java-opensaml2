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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.xml.namespace.QName;

import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.ws.security.SecurityPolicyContext;
import org.opensaml.ws.security.SecurityPolicyException;
import org.opensaml.ws.security.SecurityPolicyRule;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.security.EntityCredentialTrustEngine;
import org.opensaml.xml.security.UsageType;
import org.opensaml.xml.security.X509EntityCredential;
import org.opensaml.xml.security.X509KeyInfoResolver;

/**
 * Policy rule that checks if the client cert used to authenticate the request is valid and trusted.
 * 
 * @param <RequestType> type of request to extract the credential from
 * @param <IssuerType> the message issuer type
 */
public abstract class BaseX509CredentialAuthRule<RequestType extends ServletRequest, IssuerType> 
        implements SecurityPolicyRule<RequestType, IssuerType> {

    /** Metadata provider to lookup issuer information. */
    private MetadataProvider metadataProvider;

    /** Trust engine used to verify metadata. */
    private EntityCredentialTrustEngine<X509EntityCredential, X509KeyInfoResolver> trustEngine;

    /** Resolver used to extract key information from a key source. */
    private X509KeyInfoResolver keyResolver;

    /** Types of keys used in the verificiation process. */
    private List<UsageType> keyUsageTypes;

    /** SAML role the issuer is meant to be operating in. */
    private QName issuerRole;

    /** The message protocol used by the issuer. */
    private String issuerProtocol;

    /**
     * Constructor.
     * 
     * @param provider metadata provider used to look up entity information
     * @param engine trust engine used to validate client cert against issuer's metadata
     * @param x509KeyResolver resolver used to extract key information from a key source
     * @param role role the issuer is meant to be operating in
     * @param protocol protocol the issuer used in the request
     */
    public BaseX509CredentialAuthRule(MetadataProvider provider,
            EntityCredentialTrustEngine<X509EntityCredential, X509KeyInfoResolver> engine,
            X509KeyInfoResolver x509KeyResolver, QName role, String protocol) {
        metadataProvider = provider;
        trustEngine = engine;
        keyResolver = x509KeyResolver;
        issuerRole = role;
        issuerProtocol = protocol;

        keyUsageTypes = new ArrayList<UsageType>();
        keyUsageTypes.add(UsageType.SIGNING);
        keyUsageTypes.add(UsageType.UNSPECIFIED);
    }

    /**
     * Gets the metadata provider used to look up entity information.
     * 
     * @return metadata provider used to look up entity information
     */
    public MetadataProvider getMetadataProvider() {
        return metadataProvider;
    }

    /**
     * Gets the trust engine used to validate the X509 credential.
     * 
     * @return trust engine used to validate the X509 credential
     */
    public EntityCredentialTrustEngine<X509EntityCredential, X509KeyInfoResolver> getTrustEngine() {
        return trustEngine;
    }

    /**
     * Gets the key resolver used to extract keying information from the metadata.
     * 
     * @return key resolver used to extract keying information from the metadata
     */
    public X509KeyInfoResolver getKeyResolver() {
        return keyResolver;
    }

    /**
     * Gets the role the issuer is operating in.
     * 
     * @return role the issuer is operating in
     */
    public QName getIssuerRole() {
        return issuerRole;
    }

    /**
     * Gets the protocol the issuer is using.
     * 
     * @return protocol the issuer is using
     */
    public String getIssuerProtocol() {
        return issuerProtocol;
    }

    /** {@inheritDoc} */
    public abstract void evaluate(RequestType request, XMLObject message, SecurityPolicyContext<IssuerType> context) 
            throws SecurityPolicyException;

    /**
     * Evaluates the given X509 entity credential against the given keying information.
     * 
     * @param credential the credential to evaluate
     * @param message the message being checked, unused in this check
     * 
     * @return the issuer of the message as extracted from the entity credential
     * 
     * @throws SecurityPolicyException thrown if there is a problem getting key 
     *           information or evalauting the trustworthiness of the credential
     */
    protected IssuerType evaluateCredential(X509EntityCredential credential, XMLObject message) 
            throws SecurityPolicyException {
        //TODO - this logic is wrong, need to reassess
        //     - also need to make work with generic IssuerType
     /*   List issuerNames = X509Util.getSubjectNames(credential.getEntityCertificate(),
                BaseX509CredentialAuthRuleFactory.SUBJECT_ALT_NAMES);

        for (Object issuerName : issuerNames) {
            try {
                MetadataKeyInfoSource keyInfoSrc = new MetadataKeyInfoSource(keyUsageTypes, getMetadataProvider(),
                        issuerName.toString(), getIssuerRole(), getIssuerProtocol());
                
                if (getTrustEngine().validate(credential, keyInfoSrc, getKeyResolver())) {
                    return issuerName.toString();
                }
                
            } catch (SecurityException e) {
                throw new SecurityPolicyException("Unable to validate credential", e);
            }
        }

        throw new SecurityPolicyException("Issuer can bot be located in metadata");
        */
        return null;
    }
}