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

import org.opensaml.common.SAMLObject;

/**
 * SAML 2.0 Metadata AttributeAuthorityDescriptor
 */
public interface AttributeConsumingService extends SAMLObject, NameDescriptorComp {
    
    /** Element name, no namespace */
    public final static String LOCAL_NAME = "AttributeConsumingService";
    
    /** "index" attribute's local name */
    public final static String INDEX_ATTRIB_NAME = "index";
    
    /** "isDefault" attribute's local name */
    public final static String IS_DEFAULT_ATTRIB_NAME = "affiliationOwnerID";

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
    public boolean isDefault();
    
    /**
     * Sets if this is the default service for the service provider.
     * 
     * @param isDefault true if this is the default service, false if not
     */
    public void setIsDefault(boolean isDefault);
    
    /**
     * Gets an immutable list of the descriptions, {@link LocalizedString}s, for this service.
     * 
     * @return list of descriptions
     */
    public List<LocalizedString> getDescriptions();

    /**
     * Gets the localized description of this service in a given language.
     * 
     * @param language the language
     * @return the description for this service localized to the given language
     */
    public LocalizedString getDescription(String language);

    /**
     * Convience method for get the localized string of the description for this service in a given language. This is
     * the same as calling this.getDescription(String).getLocalString().
     * 
     * @param language the language of the name
     * @return the description for this service localized to the given language
     */
    public String getDescriptionAsString(String language);

    /**
     * Adds a localized description for this services. If a localized description in the same language is already
     * present it is replaced.
     * 
     * @param description the description
     */
    public void addDescription(LocalizedString description);

    /**
     * Removes a localized description for this services.
     * 
     * @param description the description
     */
    public void removeDescription(LocalizedString description);

    /**
     * Removes a list of localized description for this services.
     * 
     * @param descriptions the list descriptions
     */
    public void removeDescriptions(Collection<LocalizedString> descriptions);

    /**
     * Removes all the localized description for this services.
     */
    public void removeAllDescriptions();

    /**
     * Checks to see if the attribute is requested by this service.
     * 
     * @param attribute the attribute
     * @return true if the attribute is in the list of requested attribute for this service false if not
     */
    public boolean isRequestedAttributes(RequestedAttribute attribute);

    /**
     * Checks to see if the attribute is requested and required by this service.
     * 
     * @param attribute the attribute
     * @return true if the attribute is in the list of requested attribute for this service and is marked as required
     *         false if not
     */
    public boolean isRequiredAttribute(RequestedAttribute attribute);

    /**
     * Gets an immutable list of attributes requested by this service.
     * 
     * @return list of attributes requested by this service
     */
    public List<RequestedAttribute> getRequestedAttributes();

    /**
     * Adds an attribute to the list of attributes requested by this service.
     * 
     * @param attribute the attribute
     */
    public void addRequestedAttribute(RequestedAttribute attribute);

    /**
     * Removes an attribute from the list of attributes requested by this service.
     * 
     * @param attribute the attribute
     */
    public void removeRequestedAttribute(RequestedAttribute attribute);

    /**
     * Removes a list of attribute from the list of attributes requested by this service.
     * 
     * @param attributes the attribute
     */
    public void removeRequestedAttributes(Collection<RequestedAttribute> attributes);

    /**
     * Removes all the attribute from the list of attributes requested by this service.
     */
    public void removeAllRequestedAttributes();
}
