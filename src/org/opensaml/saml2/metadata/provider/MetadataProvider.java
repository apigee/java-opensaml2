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

package org.opensaml.saml2.metadata.provider;

import org.opensaml.saml2.metadata.EntityDescriptor;

/**
 * A local store into which metadata can be loaded and queried. Specific implemenations may perform additional logic
 * such as caching (and refreshing) metadata and merging metadata, about a single entity, from multiple sources.
 */
public interface MetadataProvider {

    /**
     * Gets the metadata for a given entity.
     * 
     * @param entityID the ID of the entity
     * @param requiredValidMetadata whether the metadata must be valid, based on the validUntil and cacheDuration
     *            attributes of the entity descritpor
     * 
     * @return the entity's metadata or null if there is no metadata or no valid metadata
     */
    public EntityDescriptor getEntityDescriptor(String entityID, boolean requiredValidMetadata);

    /**
     * Gets the metadata for a given entity if the metadata is valid. This is the same as calling
     * {@link #getEntityDescriptor(String, boolean)} passing in true for the second parameter;
     * 
     * @param entityID the ID of the entity
     * 
     * @return the entity's metadata or null if there is no metadata or no valid metadata
     */
    public EntityDescriptor getEntityDescriptor(String entityID);
}