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
 * A functional interface RoleDescriptors may use to deal with "Name" elements.
 *
 */
public interface NameDescriptorComp {

    /**
     * Gets an immutable list of names, {@link LocalizedString}s, for this service.
     * 
     * @return list of names
     */
    public List<LocalizedString> getNames();

    /**
     * Gets the localized name of this service in a given language.
     * 
     * @param language the language
     * @return the name for this service localized to the given language
     */
    public LocalizedString getName(String language);

    /**
     * Convience method for get the localized string of the name for this service in a given language. This is the same
     * as calling this.getName(String).getLocalString().
     * 
     * @param language the language of the name
     * @return the name for this service localized to the given language
     */
    public String getNameAsString(String language);

    /**
     * Adds a localized name for this services. If a localized name in the same language is already present it is
     * replaced.
     * 
     * @param name the name
     */
    public void addName(LocalizedString name);

    /**
     * Removes a localized name for this services.
     * 
     * @param name the name
     */
    public void removeName(LocalizedString name);

    /**
     * Removes a list of localized name for this services.
     * 
     * @param names the names
     */
    public void removeNames(Collection<LocalizedString> names);

    /**
     * Removes all the localized name for this service.
     */
    public void removeAllNames();

}