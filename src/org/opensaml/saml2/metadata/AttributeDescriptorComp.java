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

import org.opensaml.saml2.core.Attribute;

/**
 * A functional interface RoleDescriptors may use to deal with "Attribute" elements.
 *
 */
public interface AttributeDescriptorComp {

    /**
     * Checks if the given attribute is supported by this authority.
     * 
     * @return true if the attribute is supported, false if not
     */
    public boolean isSupportedAttribute(Attribute attribute);

    /**
     * Gets an immutable list of the {@link Attribute}s supported by this authority.
     * 
     * @return list of the {@link Attribute}s
     */
    public Set /* <Attribute> */getAttributes();

    /**
     * Adds an {@link Attribute} supported by this authority.
     * 
     * @param attribute the attribute
     */
    public void addAttribute(Attribute attribute);

    /**
     * Adds a list of {@link Attribute}s supported by this authority.
     * 
     * @param attributes the list of attributes
     */
    public void addAttributes(Set /*<Attribute>*/attributes);

    /**
     * Removes an {@link Attribute} supported by this authority.
     * 
     * @param attribute the attribute
     */
    public void removeAttributes(Attribute attribute);

    /**
     * Removes a list {@link Attribute}s supported by this authority.
     * 
     * @param attributes the list attributes
     */
    public void removeAttributes(Set /*<Attribute>*/attributes);

    /**
     * Removes all the attributes supported by this authority.
     */
    public void removeAllAttributes();

}