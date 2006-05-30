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

import java.util.List;

import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastList.Node;

import org.apache.log4j.Logger;
import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataCache;
import org.opensaml.saml2.metadata.provider.MetadataCacheObserver;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataCache.CacheEntry;

/**
 * This metadata provider leverages a metadata cache instance in order to cache and refresh the underlying metadata. In
 * the event that an entity ID appears in more than one metadata file, the first one encountered, while iterating
 * through the list of cache entries from the metadata cache, will be used.
 */
public class CachingMetadataProvider implements MetadataProvider {
    
    /** Logger */
    private final Logger log = Logger.getLogger(CachingMetadataProvider.class);

    /** Entity IDs to entity descriptors index */
    private CacheIndex cacheIndex;

    /**
     * Constructor
     */
    public CachingMetadataProvider(MetadataCache metadataCache) {
        FastList<MetadataCache> metadataCaches = new FastList<MetadataCache>(2);
        metadataCaches.add(metadataCache);
        cacheIndex = new CacheIndex(metadataCaches);
    }
    
    /**
     * Constructor
     */
    public CachingMetadataProvider(List<MetadataCache> metadataCaches) {
        cacheIndex = new CacheIndex(metadataCaches);
    }

    /**
     * {@inheritDoc}
     */
    public EntityDescriptor getEntityDescriptor(String entityID, boolean requiredValidMetadata) {
        EntityDescriptor descriptor = cacheIndex.getEntityDescriptor(entityID);
        return descriptor;
    }
    
    /**
     * {@inheritDoc}
     */
    public EntityDescriptor getEntityDescriptor(String entityID){
        return getEntityDescriptor(entityID, true);
    }

    /**
     * An index over the metadata cache to allow for fast lookups of EntityDescriptors given their ID.
     */
    private class CacheIndex implements MetadataCacheObserver {

        /** Metadata cache */
        private FastList<MetadataCache> metadataCaches;

        /** Entity ID to EntityDescriptor index */
        private FastMap<String, EntityDescriptor> entityDescIndex;

        protected CacheIndex(List<MetadataCache> caches) {
            metadataCaches = new FastList<MetadataCache>(caches.size() + 1);
            metadataCaches.addAll(caches);
            
            Node<MetadataCache> lastEntry = metadataCaches.tail();
            for(Node<MetadataCache> entry = metadataCaches.head(); entry != lastEntry; entry = entry.getNext()){
                entry.getValue().getCacheObservers().add(this);
            }
            entityDescIndex = new FastMap<String, EntityDescriptor>();
            rebuildIndex();
        }

        /** {@inheritDoc   */
        public void notify(String resolverID, short operation) {
            if(log.isDebugEnabled()){
                log.debug("Cache change detected, rebuilding index");
            }
            rebuildIndex();
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

        /**
         * Rebuilds the entity descriptor index for the given metadata cache.
         */
        protected void rebuildIndex() {
            entityDescIndex.clear();
            SAMLObject metadata;

            MetadataCache metadataCache;
            Node<MetadataCache> lastEntry = metadataCaches.tail();
            for(Node<MetadataCache> cachesEntry = metadataCaches.head(); cachesEntry != lastEntry; cachesEntry = cachesEntry.getNext()){
                metadataCache = cachesEntry.getValue();
                for (CacheEntry entry : metadataCache.getCacheEntries()) {
                    if(log.isDebugEnabled()){
                        log.debug("Preparing to index metadata from resolver " + entry.getMetadataResolver().getID());
                    }
                    metadata = entry.getMetadata();
                    if (metadata != null) {
                        if (metadata instanceof EntitiesDescriptor) {
                            indexEntitiesDecriptor((EntitiesDescriptor) metadata);
                        }
    
                        if (metadata instanceof EntityDescriptor) {
                            indexEntityDescriptor((EntityDescriptor) metadata);
                        }
                    }
                }
            }
        }

        /**
         * Reads an EntitiesDescriptor and indexes all of it's children EntityDescriptors.
         * 
         * @param descriptor the EntitiesDescriptor to index
         */
        protected void indexEntitiesDecriptor(EntitiesDescriptor descriptor) {
            List<EntityDescriptor> entityDescriptors = descriptor.getEntityDescriptors();
            if (entityDescriptors != null) {
                for (EntityDescriptor entityDescriptor : entityDescriptors) {
                    indexEntityDescriptor(entityDescriptor);
                }
            }
            
            List<EntitiesDescriptor> entitiesDescriptors = descriptor.getEntitiesDescriptors();
            if(entitiesDescriptors != null){
                for(EntitiesDescriptor entitiesDescriptor : entitiesDescriptors){
                    indexEntitiesDecriptor(entitiesDescriptor);
                }
            }
        }

        /**
         * Reads an EntityDescriptor and adds it to the index if another descriptor with its ID is not already
         * registered.
         * 
         * @param descriptor the EntityDescriptor to index
         */
        protected void indexEntityDescriptor(EntityDescriptor descriptor) {
            String entityID = descriptor.getEntityID();
            if (!entityDescIndex.containsKey(entityID)) {
                if(log.isDebugEnabled()){
                    log.debug("Adding EntityDescriptor " + entityID + " to the cache index");
                }
                entityDescIndex.put(entityID, descriptor);
            }else{
                if(log.isDebugEnabled()){
                    log.debug("Previous EntityDescriptor " + entityID + " already cached, skipping this one");
                }
            }
        }
    }
}