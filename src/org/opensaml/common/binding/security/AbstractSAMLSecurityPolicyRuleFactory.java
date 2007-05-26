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
import javax.xml.namespace.QName;

import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.ws.security.SecurityPolicyRuleFactory;

/**
 * Base class for SAML-specific {@link SecurityPolicyRuleFactory} implementations.
 * 
 * @param <RequestType> type of incoming protocol request
 */
public abstract class AbstractSAMLSecurityPolicyRuleFactory<RequestType extends ServletRequest> implements
        SecurityPolicyRuleFactory<RequestType> {

    /** Metadata provider to lookup issuer information. */
    private MetadataProvider metadataProvider;

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
}