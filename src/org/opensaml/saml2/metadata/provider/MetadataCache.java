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

import java.util.List;

import org.joda.time.DateTime;
import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.metadata.resolver.MetadataResolver;

/**
 * Caches and refreshes metadata fetched from a list of metadata resolvers. This is similar to what
 * {@link org.opensaml.saml2.metadata.resolver.impl.CachingMetadataResolver} does except it provides the functionality
 * over a set of resolvers and, hopefully, gains some performance and robustness in doing so.
 * 
 * Callbacks can registered with this cache when information is adde, updated, and deleted.
 */
public interface MetadataCache {

    /** Operation designator sent to observers when a new resolver is added */
    public final static short ADD_RESOLVER = 0;

    /** Operation designator sent to observers when a resolver's cached metadata is updated */
    public final static short UPDATE_CACHE = 1;

    /** Operation designator sent to observers when a resolver and its cached metadata is removed */
    public final static short REMOVE_RESOLVER = 2;

    /**
     * Gets the metadata for the registered resolver.
     * 
     * @param resolverID the ID of the registered resolver who metadata should be fetched
     * 
     * @return the metadata
     */
    public SAMLObject getMetadata(String resolverID);
    
    /**
     * Gets the cache entry for a particular resolver.
     * 
     * @param resolverID the ID of the resolver
     * 
     * @return the cache entry or null if there is none
     */
    public CacheEntry getCacheEntry(String resolverID);
    
    /**
     * Gets all the cache entries for this cache.
     * 
     * @return cache entries for this cache
     */
    public List<? extends CacheEntry> getCacheEntries();

    /**
     * Gets an immutable list of the resolvers registered with this cache.
     * 
     * @return an immutable list of the resolvers registered with this cache
     */
    public List<MetadataResolver> getMetadataResolvers();

    /**
     * Adds a resolver to this cache.
     * 
     * @param newResolver the resolver to add
     */
    public void addMetadataResolver(MetadataResolver newResolver);

    /**
     * Removes a sresolver from this cache.
     * 
     * @param oldResolver the resolver to be removed
     */
    public void removeMetadataResolver(MetadataResolver oldResolver);

    /**
     * Clears the cache of all resolvers and cached metadata.
     */
    public void clearCache();

    /**
     * Clears all the cached entries and frees up an allocated resources.
     */
    public void destroyCache();

    /**
     * Gets a list of observers that will be notified when cache changes occur. New observers can be added to the list
     * in order to register them with the cache or removed from the list to unregister them.
     * 
     * @return list of observers that will be notified when cache changes occur
     */
    public List<MetadataCacheObserver> getCacheObservers();

    /**
     * An entry within the cache.
     */
    public interface CacheEntry {

        /**
         * Gets the metadata resolver used to fetch this entries metadata.
         * 
         * @return metadata resolver used to fetch this entries metadata
         */
        public MetadataResolver getMetadataResolver();

        /**
         * Gets the metadata cached in this entry.
         * 
         * @return metadata cached in this entry or null if an error occured fetching metadata
         */
        public SAMLObject getMetadata();

        /**
         * Gets whether the metadata in this entry is expired. Metadata is considered expired when either the shortest
         * cache duration, the earliest valid until time has been reached, or the max cache duration has been reached,
         * whichever occurs first. Behavior for the case when metadata failed to resolve but the cache entry was kept is
         * undefined here but should be defined by an implementing class.
         * 
         * @return whether the metadata in this entry is expired
         */
        public boolean isExpired();

        /**
         * Gets the date/time when this entry expires.
         * 
         * @return date/time when this entry expires
         */
        public DateTime getExpirationTime();

        /**
         * Gets the number of times, since the last successful attempt, the fetching of metadata by the resolver has
         * failed.
         * 
         * @return number of times, since the last successful attempt, the fetching of metadata by the resolver has
         *         failed
         */
        public int getFailedResolveAttempts();

        /**
         * Gets the latest exception from the latest failed attempt to fetch the metadata.
         * 
         * @return the latest exception from the latest failed attempt to fetch the metadata or null if the last attempt
         *         was successful
         */
        public Exception getFailure();
    }
}