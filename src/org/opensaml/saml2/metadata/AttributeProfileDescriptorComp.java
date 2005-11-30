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

import java.net.URI;
import java.util.Set;

/**
 * A functional interface RoleDescriptors may use to deal with "AttributeProfile" elements.
 *
 */
public interface AttributeProfileDescriptorComp {

    /**
     * Checks if the given attribute profile is supported by this authority.
     * 
     * @param format the attribute profile
     * 
     * @return true if the given attribute profile is supported, false if not
     */
    public boolean isSupportedAttributeProfile(URI format);

    /**
     * Gets an immutable list of attribute profile {@link URI}s supported by this authority.
     * 
     * @return list of NameID format {@link URI}s
     */
    public Set /* <URI> */getAttributeProfiles();

    /**
     * Adds an attribute profile {@link URI} supported by this authority.
     * 
     * @param profile an attribute profile
     */
    public void addAttributeProfile(URI profile);

    /**
     * Adds a list of attribute profile {@link URI}s supported by this authority.
     * 
     * @param profiles a list attribute profiles
     */
    public void addAttributeProfiles(Set /*<URI>*/profiles);

    /**
     * Removes an attribute profile {@link URI} supported by this authority.
     * 
     * @param profile an attribute profile
     */
    public void removeAttributeProfile(URI profile);

    /**
     * Removes a list of attribute profile {@link URI}s supported by this authority.
     * 
     * @param profiles a list attribute profiles
     */
    public void removeAttributeProfiles(Set /*<URI>*/profiles);

    /**
     * Removes all the attribute profiles supported by this authority.
     */
    public void removeAllAttributeProfiles();

}