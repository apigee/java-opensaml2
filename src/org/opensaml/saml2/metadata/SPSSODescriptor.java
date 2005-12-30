/*
 * Copyright [2005] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.saml2.metadata;

import java.util.Collection;
import java.util.List;

/**
 * SAML 2.0 Metadata SPSSODescriptorType
 */
public interface SPSSODescriptor extends SSODescriptor {
    /** Element name, no namespace */
    public final static String LOCAL_NAME = "SPSSODescriptor";
    
    /** "AuthnRequestsSigned" attribute's local name */
    public final static String AUTH_REQUETS_SIGNED_ATTRIB_NAME = "AuthnRequestsSigned";
    
    /** "WantAssertionsSigned" attribute's local name */
    public final static String WANT_ASSERTIONS_SIGNED_ATTRIB_NAME = "WantAssertionsSigned";
    
    /**
     * Gets whether this service signs AuthN requests.
     * 
     * @return true of this service signs requests, false if not
     */
	public boolean isAuthnRequestsSigned();
    
    /**
     * Sets whether this service signs AuthN requests.
     * 
     * @param isSigned true of this service signs requests, false if not
     */
    public void setAuthnRequestsSigned(boolean isSigned);

    /**
     * Gets whether this service wants assertions signed.
     * 
     * @return true if this service wants assertions signed, false if not
     */
	public boolean wantAssertionsSigned();
    
    /**
     * Sets whether this service wants assertions signed.
     * 
     * @param wantAssestionSigned true if this service wants assertions signed, false if not
     */
    public void setWantAssertionsSigned(boolean wantAssestionSigned);
    
    /**
     * Gets an immutable list of assertion consumer service {@link Endpoint}s for this service.
     * 
     * @return list of assertion consumer service {@link Endpoint}s for this service
     */
    public List<Endpoint> getAssertionConsumerServices();
    
    /**
     * Adds an assertion consumer service {@link Endpoint} for this service.
     * 
     * @param service the service endpoint
     */
    public void addAssertionConsumerService(Endpoint service);
    
    /**
     * Removes an assertion consumer service {@link Endpoint} for this service.
     * 
     * @param service the service endpoint
     */
    public void removeAssertionConsumerService(Endpoint service);
    
    /**
     * Removes a list of assertion consumer service {@link Endpoint}s for this service.
     * 
     * @param services the service endpoint
     */
    public void removeAssertionConsumerServices(Collection<Endpoint> services);
    
    /**
     * Removes all the assertion consumer service endpoints from this service.
     *
     */
    public void removeAllAssertionConsumerServices();
    
    /**
     * Gets an immutable list of attribute consuming service {@link Endpoint}s for this service.
     * 
     * @return list of attribute consuming service {@link Endpoint}s for this service
     */
    public List<Endpoint> getAttributeConsumingServices();
    
    /**
     * Adds an attribute consuming service {@link Endpoint} for this service.
     * 
     * @param service the service endpoint
     */
    public void addAttributeConsumingService(Endpoint service);
    
    /**
     * Removes an attribute consuming service {@link Endpoint} for this service.
     * 
     * @param service the service endpoint
     */
    public void removeAttributeConsumingService(Endpoint service);
    
    /**
     * Removes a list of attribute consuming service {@link Endpoint}s for this service.
     * 
     * @param services the service endpoint
     */
    public void removeAttributeConsumingServices(Collection<Endpoint> services);
    
    /**
     * Removes all the attribute consuming service endpoints from this service.
     *
     */
    public void removeAllAttributeConsumingServices();
}
