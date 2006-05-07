/*
 * Copyright [2006] [University Corporation for Advanced Internet Development, Inc.]
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

import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml2.common.TimeBoundSAMLObject;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataCache;
import org.opensaml.saml2.metadata.provider.MetadataCacheObserver;
import org.opensaml.saml2.metadata.resolver.MetadataResolver;
import org.opensaml.xml.XMLObject;

/**
 * A metadata cache that keeps caches metadata using soft references and refreshes cached metadata when it expires.
 * Metadata is considered expired when either the shortest cache duration, the earliest valid until time has been
 * reached, or the max cache duration has been reached, whichever occurs first.
 */
public class SoftReferenceMetadataCache implements MetadataCache {

    /** Registered cache observers */
    private FastList<MetadataCacheObserver> observers;

    /** Registered metadata resolvers */
    private FastMap<String, MetadataResolver> resolvers;

    /** Metadata cached for a particular resolver. */
    private FastMap<String, CacheEntryImpl> metadataCache;

    /**
     * Max number of failures that can occur while trying to load meteadata from a resolver before its removed from the
     * cache
     */
    private short maxFailedAttempts;

    /** Max time metadata may be cached */
    private long maxCacheDuration;

    /** Time between when a metadata resolver failed to resolve metadata and when it should be retried */
    private long resolveRetryInterval;

    /** Background thread that automatically updates the cache */
    private CacheUpdateThread cacheRefreshThread;

    /**
     * Constructor
     * 
     * @param maxCacheDuration max time, in seconds, metadata may be cached
     * @param maxFailedResolveAttempts number of failures that can occur while trying to load meteadata from a resolver
     *            before its removed from the cache
     * @param resolveRetryInterval time, in seconds, between when a metadata resolver failed to resolve metadata and
     *            when it should be retried
     */
    public SoftReferenceMetadataCache(long maxCacheDuration, short maxFailedResolveAttempts, long resolveRetryInterval) {
        this(2, maxCacheDuration, maxFailedResolveAttempts, resolveRetryInterval);
    }

    /**
     * Constructor.
     * 
     * If the number of resolvers to be loaded is known at construction time, this will help with memory because the
     * backling list of resolvers will never need to grow. Resolvers added beyond this initial capacity will be be
     * added, but will trigger the list to grow.
     * 
     * @param initialResolverCapacity number of resolvers that will be loaded into this provider
     * @param maxCacheDuration max time, in seconds, metadata may be cached
     * @param maxFailedResolveAttempts number of failures that can occur while trying to load meteadata from a resolver
     *            before its removed from the cache
     * @param resolveRetryInterval time, in seconds, between when a metadata resolver failed to resolve metadata and
     *            when it should be retried
     */
    public SoftReferenceMetadataCache(int initialResolverCapacity, long maxCacheDuration,
            short maxFailedResolveAttempts, long resolveRetryInterval) {
        observers = new FastList<MetadataCacheObserver>();
        resolvers = new FastMap<String, MetadataResolver>();
        metadataCache = new FastMap<String, CacheEntryImpl>();
        this.maxCacheDuration = maxCacheDuration * 1000;
        maxFailedAttempts = maxFailedResolveAttempts;
        this.resolveRetryInterval = resolveRetryInterval * 1000;
        cacheRefreshThread = new CacheUpdateThread("SoftReferenceMetadataCache updater");
        cacheRefreshThread.start();
    }

    /**
     * {@inheritDoc}
     */
    public SAMLObject getMetadata(String resolverID) {
        CacheEntry cacheEntry;
        if (resolvers.containsKey(resolverID)) {
            cacheEntry = metadataCache.get(resolverID);
            return cacheEntry.getMetadata();
        }

        return null;
    }
    
    /** {@inheritDoc} */
    public CacheEntry getCacheEntry(String resolverID){
        return metadataCache.get(resolverID);
    }
    
    /** {@inheritDoc} */
    public Collection<? extends CacheEntry> getCacheEntries(){
        return metadataCache.values();
    }

    /** {@inheritDoc} */
    public Collection<MetadataResolver> getMetadataResolvers() {
        return Collections.unmodifiableCollection(resolvers.values());
    }

    /** {@inheritDoc} */
    public void addMetadataResolver(MetadataResolver newResolver) {
        resolvers.put(newResolver.getID(), newResolver);
        notifyObservers(newResolver.getID(), ADD_RESOLVER);

    }

    /** {@inheritDoc} */
    public void removeMetadataResolver(MetadataResolver oldResolver) {
        String resolverID = oldResolver.getID();
        if (resolvers.containsKey(resolverID)) {
            resolvers.remove(resolverID);
            metadataCache.remove(resolverID);
            notifyObservers(resolverID, REMOVE_RESOLVER);
        }
    }

    /** {@inheritDoc} */
    public void clearCache() {
        FastMap.Entry<String, MetadataResolver> lastEntry = resolvers.tail();
        for (FastMap.Entry<String, MetadataResolver> n = resolvers.head(); (n = n.getNext()) != lastEntry;) {
            removeMetadataResolver(n.getValue());
        }
    }

    /** {@inheritDoc} */
    public void destroyCache() {
        clearCache();
        cacheRefreshThread.interrupt();
    }

    /** {@inheritDoc} */
    public List<MetadataCacheObserver> getCacheObservers() {
        return observers;
    }

    /**
     * Notifies all the registered obvservers of a change in the cache.
     * 
     * @param resolverID the ID or the resolver that changed
     * @param operation the operation that occured
     */
    private void notifyObservers(String resolverID, short operation) {
        FastList.Node<MetadataCacheObserver> endNode = observers.tail();
        MetadataCacheObserver observer;
        for (FastList.Node<MetadataCacheObserver> n = observers.head(); (n = n.getNext()) != endNode;) {
            observer = n.getValue();
            observer.notify(resolverID, operation);
        }
    }

    /**
     * A class that represents an entry within the metadata cache.
     */
    private class CacheEntryImpl implements MetadataCache.CacheEntry{

        /** Number of consecutive failed attempts to load metadata */
        private short failedAttempts;
        
        private Exception lastFailure;

        /** The resolver used to get the metadata */
        private MetadataResolver metadataResolver;

        /** The cached metadata */
        private SoftReference<SAMLObject> cachedMetadata;

        /** The time the cached metadata expires */
        private DateTime expirationDate;

        /**
         * Constructor
         * 
         * @param resolver the resolver used to fetch the metadata to be cached
         * @param maxCacheDuration the max duration, in seconds, for metadata to be cached
         */
        protected CacheEntryImpl(MetadataResolver resolver) {
            metadataResolver = resolver;
            reloadMetadata();
        }

        /** {@inheritDoc} */
        public MetadataResolver getMetadataResolver() {
            return metadataResolver;
        }

        /** {@inheritDoc} */
        public SAMLObject getMetadata() {
            if (cachedMetadata != null) {
                return cachedMetadata.get();
            }

            return null;
        }

        /** {@inheritDoc} */
        public boolean isExpired() {
            if (expirationDate != null) {
                return expirationDate.isBeforeNow();
            }
            return false;
        }

        /** {@inheritDoc} */
        public DateTime getExpirationTime() {
            return expirationDate;
        }
        
        /** {@inheritDoc} */
        public int getFailedResolveAttempts(){
            return failedAttempts;
        }
        
        /** {@inheritDoc} */
        public Exception getFailure(){
            return lastFailure;
        }

        /**
         * Triggers the metadata resolver to fetch a fresh version of the cached metadata.
         */
        private void reloadMetadata() {
            try {
                SAMLObject newMetadata = metadataResolver.resolve();
                DateTime now = new DateTime();
                expirationDate = getEarliestExpiration(newMetadata, now.plus(maxCacheDuration), now);
                cachedMetadata = new SoftReference<SAMLObject>(newMetadata);
                failedAttempts = 0;
                lastFailure = null;
            } catch (Exception e) {
                if (failedAttempts >= maxFailedAttempts) {
                    removeMetadataResolver(metadataResolver);
                }

                cachedMetadata = null;
                expirationDate = new DateTime().plus(resolveRetryInterval);
                lastFailure = e;
            }
        }

        /**
         * Gets the earliest expiration instant within a metadata tree.
         * 
         * @param metadata the metadata
         * @param earliestExpiration the earliest expiration instant
         * @param now when this method was called
         * 
         * @return the earliest expiration instant within a metadata tree
         */
        private DateTime getEarliestExpiration(XMLObject metadata, DateTime earliestExpiration, DateTime now) {
            // We only care about EntitiesDescriptor and EntityDescriptors
            if (!(metadata instanceof EntitiesDescriptor || metadata instanceof EntityDescriptor)) {
                return earliestExpiration;
            }

            // earliest time thus far
            DateTime expirationTime = earliestExpiration;

            // expiration time for a specific element
            DateTime elementExpirationTime;

            // Test duration based times
            if (metadata instanceof CacheableSAMLObject) {
                CacheableSAMLObject cacheInfo = (CacheableSAMLObject) metadata;

                if (cacheInfo.getCacheDuration() != null && cacheInfo.getCacheDuration().longValue() > 0) {
                    elementExpirationTime = now.plus(cacheInfo.getCacheDuration().longValue());
                    if (elementExpirationTime.isBefore(expirationTime)) {
                        expirationTime = elementExpirationTime;
                    }
                }
            }

            // Test instant based times
            if (metadata instanceof TimeBoundSAMLObject) {
                TimeBoundSAMLObject timeBoundObject = (TimeBoundSAMLObject) metadata;
                elementExpirationTime = timeBoundObject.getValidUntil();

                if (elementExpirationTime != null && elementExpirationTime.isBefore(earliestExpiration)) {
                    earliestExpiration = elementExpirationTime;
                }
            }

            // Inspect children
            for (XMLObject child : metadata.getOrderedChildren()) {
                earliestExpiration = getEarliestExpiration(child, earliestExpiration, now);
            }

            return earliestExpiration;
        }
    }

    /**
     * Background thread that updates expired cache entries.
     */
    private class CacheUpdateThread extends Thread {

        /** Whether this thread should continue to run */
        private boolean continueRunning;

        /**
         * Constructor
         * 
         * @param threadName name of this thread
         */
        public CacheUpdateThread(String threadName) {
            super(threadName);
            continueRunning = true;
        }

        /**
         * Interrupts this thread and stops it.
         */
        public void stopThread() {
            continueRunning = false;
            interrupt();
        }

        /**
         * {@inheritDoc}
         */
        public void run() {
            while (continueRunning) {
                CacheEntryImpl cacheEntry;
                DateTime nextRefresh = null;
                FastMap.Entry<String, CacheEntryImpl> lastEntry = metadataCache.tail();
                for (FastMap.Entry<String, CacheEntryImpl> entry = metadataCache.head(); entry != lastEntry; entry = entry
                        .getNext()) {
                    cacheEntry = entry.getValue();
                    if (cacheEntry.isExpired()) {
                        cacheEntry.reloadMetadata();
                        if (nextRefresh == null || cacheEntry.getExpirationTime().isBefore(nextRefresh)) {
                            nextRefresh = cacheEntry.getExpirationTime();
                        }
                    }
                }

                try {
                    sleep(new Duration(new DateTime(), nextRefresh).getMillis());
                } catch (InterruptedException e) {
                    // Do nothing
                }
            }
        }
    }
}