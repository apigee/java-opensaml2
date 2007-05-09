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

import org.apache.log4j.Logger;
import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.ws.security.SecurityPolicyContext;
import org.opensaml.ws.security.SecurityPolicyException;
import org.opensaml.ws.security.SecurityPolicyRule;
import org.opensaml.ws.soap.soap11.Envelope;
import org.opensaml.xml.XMLObject;

/**
 * Base class for SAML-specific {@link SecurityPolicyRule} implementations.
 * 
 * @param <RequestType> type of incoming protocol request
 * @param <IssuerType> the message issuer type
 */
public abstract class AbstractSAMLSecurityPolicyRule<RequestType extends ServletRequest, IssuerType>
        implements SecurityPolicyRule<RequestType, IssuerType> {
    
    /** Metadata provider to lookup issuer information. */
    private MetadataProvider metadataProvider;
    
    /** SAML role the issuer is meant to be operating in. */
    private QName issuerRole;
    
    /** The message protocol used by the issuer. */
    private String issuerProtocol;
    
    /**
     * Constructor.
     * 
     * @param provider metadata provider used to look up entity information
     * @param role role the issuer is meant to be operating in
     * @param protocol protocol the issuer used in the request
     *
     */
    public AbstractSAMLSecurityPolicyRule(MetadataProvider provider, QName role, String protocol) {
       metadataProvider = provider; 
       issuerRole = role;
       issuerProtocol = protocol;
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
     * Helper method to obtain the SAML message object from the more
     * generic XMLObject message object.
     * 
     * @param message XMLObject message presumed to contain a SAML message
     * @return the SAML message object, or null if no SAML message found
     */
    protected SAMLObject getSAMLMessage(XMLObject message) {
        
        if (message instanceof SAMLObject) {
           return (SAMLObject)  message;
        }
        
        if (message instanceof Envelope) {
            Envelope env = (Envelope) message;
            
            XMLObject xmlObject = env.getBody().getUnknownXMLObjects().get(0);
            if (xmlObject instanceof SAMLObject) {
                return (SAMLObject) xmlObject;
            }
        }
        
        return null;
    }
    
    /**
     * Resolve the given system entity ID into a SAML 2 metadata {@link RoleDescriptor}.
     * 
     * @param entityID the entity ID URI to resolve
     * @return the matching SAML metadata role descriptor
     * @throws SecurityPolicyException thrown if a metadata exception occurs during resolution
     */
    protected RoleDescriptor resolveIssuerRole(String entityID) throws SecurityPolicyException {
        Logger log = Logger.getLogger(AbstractSAMLSecurityPolicyRule.class);
        
        if (getMetadataProvider() != null && getIssuerRole() != null && getIssuerProtocol() != null) {
            log.debug("Attempting to lookup issuer metadata");
            
            RoleDescriptor rd = null;
            try {
                rd = getMetadataProvider().getRole(entityID, getIssuerRole(), getIssuerProtocol());
            } catch (MetadataProviderException e) {
                log.warn("Exception while retrieving issuer metadata", e);
                throw new SecurityPolicyException("Error while resolving issuer's metadata", e);
            }
            return rd;
        }
        return null;
    }
}
