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

package org.opensaml.common.binding.security;

import javax.servlet.ServletRequest;
import javax.xml.namespace.QName;

import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.security.MetadataCredentialResolver;
import org.opensaml.ws.security.SecurityPolicyContext;
import org.opensaml.ws.security.SecurityPolicyException;
import org.opensaml.ws.security.SecurityPolicyRule;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.security.trust.TrustEngine;
import org.opensaml.xml.security.x509.X509Credential;

/**
 * Policy rule that checks if the client cert used to authenticate the request is valid and trusted.
 * 
 * @param <RequestType> type of request to extract the credential from
 */
public abstract class BaseX509CredentialAuthRule<RequestType extends ServletRequest> extends
        AbstractSAMLSecurityPolicyRule<RequestType> implements SecurityPolicyRule<RequestType> {

    /** Trust engine used to verify metadata. */
    private TrustEngine<X509Credential, X509Credential> trustEngine;

    /** Resolver used to extract credential information from SAML 2 metadata. */
    private MetadataCredentialResolver metadataResolver;

    /**
     * Constructor.
     * 
     * @param engine trust engine used to validate client cert against issuer's metadata
     * @param resolver resolver used to extract credential information from metadata
     * @param provider metadata provider used to look up entity information
     * @param role role the issuer is meant to be operating in
     * @param protocol protocol the issuer used in the request
     */
    public BaseX509CredentialAuthRule(TrustEngine<X509Credential, X509Credential> engine,
            MetadataCredentialResolver resolver, MetadataProvider provider, QName role, String protocol) {

        super(provider, role, protocol);
        trustEngine = engine;
        this.metadataResolver = resolver;

    }

    /**
     * Gets the trust engine used to validate the X509 credential.
     * 
     * @return trust engine used to validate the X509 credential
     */
    public TrustEngine<X509Credential, X509Credential> getTrustEngine() {
        return trustEngine;
    }

    /**
     * Gets the key resolver used to extract keying information from the metadata.
     * 
     * @return key resolver used to extract keying information from the metadata
     */
    public MetadataCredentialResolver getMetadataResolver() {
        return metadataResolver;
    }

    /** {@inheritDoc} */
    public abstract void evaluate(RequestType request, XMLObject message, SecurityPolicyContext context)
            throws SecurityPolicyException;

    /**
     * Evaluates the given X509 entity credential against the given keying information.
     * 
     * @param credential the credential to evaluate
     * @param message the message being checked, unused in this check
     * 
     * @return the issuer of the message as extracted from the entity credential
     * 
     * @throws SecurityPolicyException thrown if there is a problem getting key information or evalauting the
     *             trustworthiness of the credential
     */
    protected String evaluateCredential(X509Credential credential, XMLObject message) throws SecurityPolicyException {

        // TODO - implement

        return null;
    }
}