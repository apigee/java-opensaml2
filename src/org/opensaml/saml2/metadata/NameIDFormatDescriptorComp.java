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

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.util.UnmodifiableOrderedSet;

/**
 * A functional interface RoleDescriptors may use to deal with "NameIDFormat" elements.
 *
 */
public interface NameIDFormatDescriptorComp {

    /**
     * Checks if the given NameID format is supported by this authority.
     * 
     * @param format the NameID format
     * 
     * @return true if the given NameID format is supported, false if not
     */
    public boolean isSupportedNameIDFormat(String format);

    /**
     * Gets an immutable list of NameID formats supported by this authority.
     * 
     * @return list of NameID formats supported by this authority
     */
    public UnmodifiableOrderedSet<NameIDFormat> getNameIDFormats();

    /**
     * Adds a NameID format supported by this authority.
     * 
     * @param format the format to add
     * 
     * @throws IllegalAddException thrown if the given format is alread owned by another SAMLObject
     */
    public void addNameIDFormat(NameIDFormat format) throws IllegalAddException;

    /**
     * Removes a NameID format supported by this authority.
     * 
     * @param format the format
     */
    public void removeNameIDFormat(NameIDFormat format);

    /**
     * Removes a list of NameID formats supported by this authority.
     * 
     * @param formats the list of formats
     */
    public void removeNameIDFormats(Collection<NameIDFormat> formats);

    /**
     * Removes all the NameID formats supported by this authority.
     */
    public void removeAllNameIDFormats();

}