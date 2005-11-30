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

import java.util.Set;

import javax.xml.namespace.QName;

import org.opensaml.common.util.xml.XMLConstants;


/**
 * SAML 2.0 Metadata SSODescriptor
 */
public interface SSODescriptor extends RoleDescriptor, NameIDFormatDescriptorComp {

    /** Element name, no namespace */
    public final static String LOCAL_NAME = "SSODescriptor";
    
    /** QName for this element */
    public final static QName QNAME = new QName(XMLConstants.SAML20MD_NS, LOCAL_NAME, XMLConstants.SAML20MD_PREFIX);
    
    /**
     * Gets an immutable list of artifact resolution {@link Endpoint}s for this service.
     * 
     * @return list of artifact resolution {@link Endpoint}s for this service
     */
	public Set /*<Endpoint>*/ getArtifactResolutionServices();
    
    /**
     * Adds an artifact resolution {@link Endpoint} for this service.
     * 
     * @param service an artifact resolution {@link Endpoint} for this service
     */
    public void addArtifactResolutionService(Endpoint service);
    
    /**
     * Adds a list of artifact resolution {@link Endpoint}s for this service.
     * 
     * @param services a list of artifact resolution {@link Endpoint}s for this service
     */
    public void addArtifactResolutionServices(Set /*<Endpoint>*/ services);
    
    /**
     * Removes an artifact resolution {@link Endpoint} for this service.
     * 
     * @param service an artifact resolution {@link Endpoint} for this service
     */
    public void removeArtifactResolutionService(Endpoint service);
    
    /**
     * Removes a list of artifact resolution {@link Endpoint}s for this service.
     * 
     * @param services a list of artifact resolution {@link Endpoint}s for this service
     */
    public void removeArtifactResolutionServices(Set /*<Endpoint>*/ services);
    
    /**
     * Removes all the artifact resolution {@link Endpoint}s for this service.
     */
    public void removeAllArtifactResolutionServices();

    /**
     * Gets an immutable list of single logout {@link Endpoint}s for this service.
     * 
     * @return list of single logout {@link Endpoint}s for this service
     */
	public Set /*<Endpoint>*/ getSingleLogoutServices();
    
    /**
     * Adds a single logout {@link Endpoint} for this service.
     * 
     * @param service a single logout {@link Endpoint} for this service
     */
    public void addSingleLogoutService(Endpoint service);
    
    /**
     * Adds a list of single logout {@link Endpoint}s for this service.
     * 
     * @param services a list of single logout {@link Endpoint}s for this service
     */
    public void addSingleLogoutServices(Set /*<Endpoint>*/ services);
    
    /**
     * Removes a single logout {@link Endpoint} for this service.
     * 
     * @param service a single logout {@link Endpoint} for this service
     */
    public void removeSingleLogoutService(Endpoint service);
    
    /**
     * Removes a list of single logout {@link Endpoint}s for this service.
     * 
     * @param services a list of single logout {@link Endpoint}s for this service
     */
    public void removeSingleLogoutServices(Set /*<Endpoint>*/ services);
    
    /**
     * Removes all the single logout {@link Endpoint}s for this service.
     */
    public void removeAllSingleLogoutServices();

    /**
     * Gets an immutable list of manage NameId {@link Endpoint}s for this service.
     * 
     * @return list of manage NameId {@link Endpoint}s for this service
     */
	public Set /*<Endpoint>*/ getManageNameIDServices();
    
    /**
     * Adds a manage NameId {@link Endpoint} for this service.
     * 
     * @param service a manage NameId {@link Endpoint} for this service
     */
    public void addManageNameIDService(Endpoint service);
    
    /**
     * Adds a list of manage NameId {@link Endpoint}s for this service.
     * 
     * @param services a list of manage NameId {@link Endpoint}s for this service
     */
    public void addManageNameIDServices(Set /*<Endpoint>*/ services);
    
    /**
     * Removes a manage NameId {@link Endpoint} for this service.
     * 
     * @param service a manage NameId {@link Endpoint} for this service
     */
    public void removeManageNameIDService(Endpoint service);
    
    /**
     * Removes a list of manage NameId {@link Endpoint}s for this service.
     * 
     * @param services a list of manage NameId {@link Endpoint}s for this service
     */
    public void removeManageNameIDServices(Set /*<Endpoint>*/ services);
    
    /**
     * Removes all the manage NameId {@link Endpoint}s for this service.
     */
    public void removeAllManageNameIDServices();
}
