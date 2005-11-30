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

/**
 * A functional interface RoleDescriptors may use to deal with "KeyDescriptor" elements.
 *
 */
public interface KeyDescriptorDescriptorComp {

    /**
     * Gets an immutable list of KeyDescriptors for this affiliation.
     * 
     * @return list of {@link KeyDescriptor}s for this affiliation
     */
    public Set /* <KeyDescriptor> */getKeyDescriptors();

    /**
     * Adds a {@link KeyDescriptor} to the list of descriptors for this affiliation.
     * 
     * @param keyDescriptor the descriptor
     */
    public void addKeyDescriptor(KeyDescriptor keyDescriptor);

    /**
     * Adds a list of {@link KeyDescriptor}s to descriptors for this affiliation.
     * 
     * @param keyDescriptors the descriptors
     */
    public void addKeyDescriptors(Set /*<KeyDescriptor>*/keyDescriptors);

    /**
     * Removes a {@link KeyDescriptor} from the list of descriptors for this affiliation.
     * 
     * @param keyDescriptor the descriptor
     */
    public void removeKeyDescriptor(KeyDescriptor keyDescriptor);

    /**
     * Removes a list of {@link KeyDescriptor}s from the list of descriptors for this affiliation.
     * 
     * @param keyDescriptors the descriptors
     */
    public void removeKeyDescriptors(Set /*<KeyDescriptor>*/keyDescriptors);

    /**
     * Removes all the {@link KeyDescriptor}s from this affiliation.
     */
    public void removeAllKeyDescriptors();

}