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

import java.util.List;

import org.opensaml.common.SAMLObject;

/**
 * SAML 2.0 Metadata AttributeAuthorityDescriptor
 */
public interface AttributeConsumingService extends SAMLObject {
    
    /** Element name, no namespace */
    public final static String LOCAL_NAME = "AttributeConsumingService";
    
    /** "index" attribute's local name */
    public final static String INDEX_ATTRIB_NAME = "index";
    
    /** "isDefault" attribute's local name */
    public final static String IS_DEFAULT_ATTRIB_NAME = "isDefault";

    /**
     * Gets the index for this service.
     * 
     * @return the index for this service
     */
    public int getIndex();
    
    /**
     * Sets the index for this service.
     *
     *@param index the index for this service
     */
    public void setIndex(int index);
    
    /**
     * Checks if this is the default service for the service provider.
     * 
     * @return true if this is the default service, false if not
     */
    public Boolean isDefault();
    
    /**
     * Sets if this is the default service for the service provider.
     * 
     * @param isDefault true if this is the default service, false if not
     */
    public void setIsDefault(Boolean isDefault);
    
    /**
     * Gets the list of names this service has.
     * 
     * @return list of names this service has
     */
    public List<ServiceName> getNames();
    
    /**
     * Gets the descriptions for this service.
     * 
     * @return descriptions for this service
     */
    public List<ServiceDescription> getDescriptions();
    
    /**
     * Gets the attributes this service requests.
     * 
     * @return attributes this service requests
     */
    public List<RequestedAttribute> getRequestAttributes();
}