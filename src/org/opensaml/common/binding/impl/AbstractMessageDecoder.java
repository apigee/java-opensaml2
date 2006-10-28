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

import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.MessageDecoder;
import org.opensaml.common.binding.SecurityPolicy;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.security.TrustEngine;

/**
 * Base class for message decoder handling much of the boilerplate code.
 *
 * @param <RequestType> request type that will be decoded
 */
public abstract class AbstractMessageDecoder<RequestType extends ServletRequest> implements MessageDecoder<RequestType> {

    /** Issuer of the request */
    private String issuer;
    
    /** Role the issuer is acting in */
    private RoleDescriptor issuerMetadata;
    
    /** Metadata provider used to lookup information about the issuer */
    private MetadataProvider metadataProvider;
    
    /** Request to decode */
    private RequestType request;
    
    /** Decoded SAML message */
    private SAMLObject message;
    
    /** Security policy to apply to the request and payload */
    private SecurityPolicy<RequestType> securityPolicy;
    
    /** Trust engine used to validate request credentials */
    private TrustEngine trustEngine;

    /** {@inheritDoc} */
    public String getIssuer() {
        return issuer;
    }

    /** {@inheritDoc} */
    public RoleDescriptor getIssuerMetadata() {
        return issuerMetadata;
    }

    /** {@inheritDoc} */
    public MetadataProvider getMetadataProvider() {
        return metadataProvider;
    }

    /** {@inheritDoc} */
    public RequestType getRequest() {
        return request;
    }

    /** {@inheritDoc} */
    public SAMLObject getSAMLMessage() {
        return message;
    }

    /** {@inheritDoc} */
    public SecurityPolicy<RequestType> getSecurityPolicy() {
        return securityPolicy;
    }

    /** {@inheritDoc} */
    public TrustEngine getTrustEngine() {
        return trustEngine;
    }
    
    /**
     * Sets the issuer of the request.
     * 
     * @param issuer issuer of the request
     */
    public void setIssuer(String issuer){
        this.issuer = issuer;
    }
    
    /**
     * Sets the request issuer's role metadata.
     * 
     * @param issuerMetadata request issuer's role metadata
     */
    public void setIssuerMetadata(RoleDescriptor issuerMetadata){
        this.issuerMetadata = issuerMetadata;
    }

    /** {@inheritDoc} */
    public void setMetadataProvider(MetadataProvider metadataProvider) {
        this.metadataProvider = metadataProvider;
    }

    /** {@inheritDoc} */
    public void setRequest(RequestType request) {
        this.request = request;
    }
    
    /**
     * Sets the decoded SAML message.
     * 
     * @param message decoded SAML message
     */
    public void setSAMLMessage(SAMLObject message){
        this.message = message;
    }

    /** {@inheritDoc} */
    public void setSecurityPolicy(SecurityPolicy<RequestType> policy) {
        securityPolicy = policy;
    }

    /** {@inheritDoc} */
    public void setTrustEngine(TrustEngine trustEngine) {
        this.trustEngine = trustEngine;
    }
    
    /** {@inheritDoc} */
    abstract public void decode() throws BindingException;
}