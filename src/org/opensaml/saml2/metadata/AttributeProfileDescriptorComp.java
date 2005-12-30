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

import org.opensaml.xml.IllegalAddException;

/**
 * A functional interface RoleDescriptors may use to deal with "AttributeProfile" elements.
 */
public interface AttributeProfileDescriptorComp {
    
    /**
     * Checks if the given attribute profile is supported by this authority.
     * 
     * @param profile the attribute profile
     * 
     * @return true if the given attribute profile is supported, false if not
     */
    public boolean isSupportedAttributeProfile(AttributeProfile profile);

    /**
     * Gets an immutable list of attribute profile URIs supported by this authority.
     * 
     * @return the list of supported {@link AttributeProfile}s
     */
    public List<AttributeProfile> getAttributeProfiles();
    
    /**
     * Adds an attribute profile URIs supported by this authority.
     * 
     * @param profile an attribute profile
     * 
     * @throws IllegalAddException thrown if the given profile is already a child of another SAMLObject
     */
    public void addAttributeProfile(AttributeProfile profile) throws IllegalAddException;
    
    /**
     * Removes an attribute profile URIs supported by this authority.
     * 
     * @param profile an attribute profile
     */
    public void removeAttributeProfile(AttributeProfile profile);

    /**
     * Removes a list of attribute profile URIs supported by this authority.
     * 
     * @param profiles a list attribute profiles
     */
    public void removeAttributeProfiles(Collection<AttributeProfile> profiles);

    /**
     * Removes all the attribute profiles supported by this authority.
     */
    public void removeAllAttributeProfiles();
}