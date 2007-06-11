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

import org.apache.log4j.Logger;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.ws.security.SecurityPolicyContext;
import org.opensaml.ws.security.SecurityPolicyException;
import org.opensaml.ws.security.provider.BasicSecurityPolicy;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * A policy used to verify the security of an incoming SAML request. Its security mechanisms may be used to check
 * transport layer items (e.g client certificates and basic auth passwords) and the payload valiators may be used to
 * check the payload of a request to ensure it meets certain criteria (e.g. valid digital signature).
 */
public class SAMLSecurityPolicy extends BasicSecurityPolicy<ServletRequest> {

    /** Class logger. */
    private final Logger log = Logger.getLogger(SAMLSecurityPolicy.class);

    /** Metadata provider used to look up entity information. */
    private MetadataProvider metadataProvider;

    /** SAML role the issuer is meant to be operating in. */
    private QName issuerRole;

    /** The message protocol used by the issuer. */
    private String issuerProtocol;

    /** Metadata about the role of the issuer. */
    private RoleDescriptor issuerRoleMetadata;

    /**
     * Constructor.
     * 
     * @param role expected role of the issuer
     * @param protocol expected protocol of the issuer
     */
    public SAMLSecurityPolicy(QName role, String protocol) {
        super(true);
        issuerRole = role;
        issuerProtocol = DatatypeHelper.safeTrimOrNullString(protocol);

        if (issuerRole == null || issuerProtocol == null) {
            throw new IllegalArgumentException("Issuer role and protocol may not be null");
        }
    }

    /**
     * Constructor.
     * 
     * @param role expected role of the issuer
     * @param protocol expected protocol of the issuer
     * @param requireAuthenticatedIssuer whether the issuer of the message must be authenticated in order for the policy
     *            to pass
     */
    public SAMLSecurityPolicy(QName role, String protocol, boolean requireAuthenticatedIssuer) {
        super(requireAuthenticatedIssuer);
        issuerRole = role;
        issuerProtocol = DatatypeHelper.safeTrimOrNullString(protocol);

        if (issuerRole == null || issuerProtocol == null) {
            throw new IllegalArgumentException("Issuer role and protocol may not be null");
        }
    }

    /** {@inheritDoc} */
    public void evaluate(ServletRequest request, XMLObject message) throws SecurityPolicyException {
        super.evaluate(request, message);

        try {
            if (metadataProvider != null) {
                issuerRoleMetadata = metadataProvider.getRole(getIssuer(), issuerRole, issuerProtocol);
            }
        } catch (MetadataProviderException e) {
            log.warn("Could not look up role metadata for issuer " + getIssuer(), e);
        }
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
     * Sets the metadata provider used to look up entity information.
     * 
     * @param provider metadata provider used to look up entity information
     */
    public void setMetadataProvider(MetadataProvider provider) {
        metadataProvider = provider;
    }

    /**
     * Gets the role metadata for the issuer, after the security policy has been successfully evaluated.
     * 
     * @return role metadata for the issuer
     */
    public RoleDescriptor getIssuerRoleMetadata() {
        return issuerRoleMetadata;
    }

    /** {@inheritDoc} */
    protected SecurityPolicyContext createNewContext() {
        SAMLSecurityPolicyContext context = new SAMLSecurityPolicyContext();
        context.setMetadataProvider(metadataProvider);
        context.setIssuerProtocol(issuerProtocol);
        context.setIssuerRole(issuerRole);
        return context;
    }
}