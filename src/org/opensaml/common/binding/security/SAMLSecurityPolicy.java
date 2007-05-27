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

import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.ws.security.SecurityPolicyContext;
import org.opensaml.ws.security.SecurityPolicyException;
import org.opensaml.ws.security.provider.BasicSecurityPolicy;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * A policy used to verify the security of an incoming SAML request.  Its security mechanisms may be used to 
 * check transport layer items (e.g client certificates and basic auth passwords) and the payload valiators 
 * may be used to check the payload of a request to ensure it meets certain criteria (e.g. valid digital signature).
 * 
 * @param <RequestType> type of incoming protocol request
 */
public class SAMLSecurityPolicy<RequestType extends ServletRequest> 
        extends BasicSecurityPolicy<RequestType> {
    
    /** Metadata provider to lookup issuer information. */
    private MetadataProvider metadataProvider;

    /** SAML role the issuer is meant to be operating in. */
    private QName issuerRole;

    /** The message protocol used by the issuer. */
    private String issuerProtocol;
    
    /** SAML role descriptor for the issuer. */
    private RoleDescriptor issuerRoleDescriptor;
    
    /**
     * Constructor.
     *
     * @param metadata metadata provider used to lookup entity information
     * @param role expected role of the issuer
     * @param protocol expected protocol of the issuer
     */
    public SAMLSecurityPolicy(MetadataProvider metadata, QName role, String protocol){
        super(true);
        metadataProvider = metadata;
        issuerRole = role;
        issuerProtocol = DatatypeHelper.safeTrimOrNullString(protocol);
        
        if(metadataProvider == null || issuerRole == null || issuerProtocol == null){
            throw new IllegalArgumentException("Metadata provider, issuer role, and issuer protocol may not be null");
        }
    }
    
    /** {@inheritDoc} */
    public void evaluate(RequestType request, XMLObject message) throws SecurityPolicyException {
        super.evaluate(request, message);
        
        try {
            issuerRoleDescriptor = metadataProvider.getRole(getIssuer(), issuerRole, issuerProtocol);
        } catch (MetadataProviderException e) {
            throw new SecurityPolicyException("Error while resolving issuer's metadata", e);
        }
    }
    
    /**
     * Convenience method for getting the metadata for the role in which the issuer is 
     * operating, obtained from the security policy's context instance.
     * 
     * @return metadata for the role in which the issuer is operating
     */
    public RoleDescriptor getIssuerMetadata(){
        return issuerRoleDescriptor;
    }
    
    /** {@inheritDoc} */
    protected SecurityPolicyContext createNewContext() {
        SAMLSecurityPolicyContext context = new SAMLSecurityPolicyContext();
        context.setIssuerProtocol(issuerProtocol);
        context.setIssuerRole(issuerRole);
        context.setMetadataProvider(metadataProvider);
        return context;
    }
}