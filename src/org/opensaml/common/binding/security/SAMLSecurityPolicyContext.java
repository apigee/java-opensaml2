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

import javax.xml.namespace.QName;

import org.joda.time.DateTime;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.ws.security.SecurityPolicyContext;

/**
 * SAML-specific class which stores state used, and produced, by security policy rules.
 */
public class SAMLSecurityPolicyContext extends SecurityPolicyContext {

    /** Metadata provider to lookup issuer information. */
    private MetadataProvider metadataProvider;

    /** SAML role the issuer is meant to be operating in. */
    private QName issuerRole;

    /** The message protocol used by the issuer. */
    private String issuerProtocol;

    /** Date and time of message issuance. */
    private DateTime issueInstant;

    /** Message identifier. */
    private String messageID;

    /**
     * Gets the message protocol used by the issuer.
     * 
     * @return message protocol used by the issuer
     */
    public String getIssuerProtocol() {
        return issuerProtocol;
    }

    /**
     * Sets the message protocol used by the issuer.
     * 
     * @param protocol message protocol used by the issuer
     */
    public void setIssuerProtocol(String protocol) {
        issuerProtocol = protocol;
    }

    /**
     * Gets the SAML role the issuer is meant to be operating in.
     * 
     * @return SAML role the issuer is meant to be operating in
     */
    public QName getIssuerRole() {
        return issuerRole;
    }

    /**
     * Sets the SAML role the issuer is meant to be operating in.
     * 
     * @param role SAML role the issuer is meant to be operating in
     */
    public void setIssuerRole(QName role) {
        issuerRole = role;
    }

    /**
     * Gets the metadata provider used to lookup issuer information.
     * 
     * @return metadata provider used to lookup issuer information
     */
    public MetadataProvider getMetadataProvider() {
        return metadataProvider;
    }

    /**
     * Sets the metadata provider used to lookup issuer information.
     * 
     * @param provider metadata provider used to lookup issuer information
     */
    public void setMetadataProvider(MetadataProvider provider) {
        metadataProvider = provider;
    }

    /**
     * Get the date and time of a message's issuance.
     * 
     * @return the date/time a message was issued
     */
    public DateTime getIssueInstant() {
        return issueInstant;
    }

    /**
     * Set the date and time of a message's issuance.
     * 
     * @param newIssueInstant the date and time of the message's issuance
     */
    public void setIssueInstant(DateTime newIssueInstant) {
        issueInstant = newIssueInstant;
    }

    /**
     * Gets the ID of the SAML request/response.
     * 
     * @return ID of the SAML request/response
     */
    public String getMessageID() {
        return messageID;
    }

    /**
     * Sets the ID of the SAML request/response.
     * 
     * @param id ID of the SAML request/response
     */
    public void setMessageID(String id) {
        messageID = id;
    }
}