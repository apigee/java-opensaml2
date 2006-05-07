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

package org.opensaml.saml2.metadata.provider.impl;

import javolution.util.FastMap;

import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataCache;
import org.opensaml.saml2.metadata.provider.MetadataCacheObserver;
import org.opensaml.saml2.metadata.provider.MetadataProvider;

/**
 * This metadata provider caches metadata and maintains a single background thread that refreshes the cached metadata
 * when it expires. Metadata is considered expired when either the shortest cache duration, the earliest valid until
 * time has been reached, or the max cache duration has been reached, whichever occurs first.
 */
public class CachingMetadataProvider implements MetadataProvider {

    /** Entity IDs to entity descriptors index */
    private CacheIndex cacheIndex;

    /**
     * Constructor
     */
    public CachingMetadataProvider(MetadataCache metadataCache) {
        cacheIndex = new CacheIndex(metadataCache);
    }

    /**
     * {@inheritDoc}
     */
    public EntityDescriptor getEntityDescriptor(String entityID, boolean requiredValidMetadata) {
        EntityDescriptor descriptor = cacheIndex.getEntityDescriptor(entityID);
        return descriptor;
    }

    /**
     * An index over the metadata cache to allow for fast lookups of EntityDescriptors given their ID.
     */
    private class CacheIndex implements MetadataCacheObserver {

        /** Metadata cache */
        private MetadataCache metadataCache;

        /** Entity ID to EntityDescriptor index */
        private FastMap<String, EntityDescriptor> entityDescIndex;

        protected CacheIndex(MetadataCache cache) {
            metadataCache = cache;
            metadataCache.getCacheObservers().add(this);
        }

        /** {@inheritDoc  */
        public void notify(String resolverID, short operation) {
            switch(operation){
                case MetadataCache.ADD_RESOLVER:
                    
                    break;
                    
                case MetadataCache.UPDATE_CACHE:
                    
                    break;
                    
                case MetadataCache.REMOVE_RESOLVER:
                    
                    break;
            }
        }
        
        /**
         * Gets the EntityDescriptor for the given entity ID.
         * 
         * @param entityID the entity ID
         * 
         * @return the corresponding entity descriptor or null
         */
        protected EntityDescriptor getEntityDescriptor(String entityID) {
            return entityDescIndex.get(entityID);
        }
    }
}