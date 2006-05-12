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

package org.opensaml.saml2.metadata.resolver.impl;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml2.common.TimeBoundSAMLObject;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.resolver.FilterException;
import org.opensaml.saml2.metadata.resolver.MetadataFilter;
import org.opensaml.saml2.metadata.resolver.MetadataResolver;
import org.opensaml.saml2.metadata.resolver.ResolutionException;
import org.opensaml.xml.XMLObject;

/**
 * A metadata resolver that wraps another resolver and caches the returned metadata. The cached metadata is refreshed
 * during the invocation of {@link #resolve()} after it expired. Metadata is considered expired when either the shortest
 * cache duration, the earliest valid until time has been reached, or the max cache duration has been reached, whichever
 * occurs first.
 * 
 * This resolver does not maintain it's own Metadata filter but instead relies on the wrapped resolver's filter which
 * can be set and retrieved through this resolvers methods. Nor does it have its own ID.
 */
public class CachingMetadataResolver implements MetadataResolver {

    /** Logger */
    private final Logger log = Logger.getLogger(URLResolver.class);

    /** The wrapped resolver */
    private MetadataResolver wrappedResolver;

    /** Maximum amount of time, in seconds, metadata will be cached */
    private Duration maxCacheDuration;

    /** The cached metadata */
    private SAMLObject cachedMetadata;

    /** The date/time after which the cache is expired */
    private DateTime cacheExpiration;

    /**
     * Constructor
     * 
     * @param resolver the resolver whose returned metadata will be cached
     */
    public CachingMetadataResolver(MetadataResolver resolver) {
        if (log.isDebugEnabled()) {
            log.debug("Wrapping resolver " + resolver.getID() + " of type " + resolver.getClass().getName());
        }
        wrappedResolver = resolver;
    }

    /**
     * Constructor
     * 
     * @param resolver the resolver whose returned metadata will be cached
     * @param maxCacheDuration the max length of time, in seconds, metadata will be cached.
     */
    public CachingMetadataResolver(MetadataResolver resolver, long maxCacheDuration) {
        wrappedResolver = resolver;
        if (maxCacheDuration > 0) {
            this.maxCacheDuration = new Duration(maxCacheDuration * 1000);
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getID() {
        return wrappedResolver.getID();
    }

    /**
     * {@inheritDoc}
     */
    public SAMLObject resolve() throws ResolutionException, FilterException {
        if (log.isDebugEnabled()) {
            log.debug("Attempting to resolve metadata");
        }
        try {
            if (cachedMetadata == null) {
                if (log.isDebugEnabled()) {
                    log.debug("Metadata was not cached, attempting to fetch it through the wrapped resolver");
                }
                updateMetadata();
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Metadata currently cached, checking to see if it is expired");
                }
                if (cacheExpiration.isBeforeNow()) {
                    if (log.isDebugEnabled()) {
                        log.debug("Cached metadata is expired, refreshing it");
                    }
                    updateMetadata();
                }
            }
        } catch (ResolutionException e) {
            cachedMetadata = null;
            throw e;
        } catch (FilterException e) {
            cachedMetadata = null;
            throw e;
        }

        return cachedMetadata;
    }

    /**
     * {@inheritDoc}
     */
    public MetadataFilter getMetadataFilter() {
        return wrappedResolver.getMetadataFilter();
    }

    /**
     * {@inheritDoc}
     */
    public void setMetadataFilter(MetadataFilter newFilter) {
        wrappedResolver.setMetadataFilter(newFilter);
    }

    /**
     * Gets a new set of metadata from the wrapped resolver and caches it.
     */
    private void updateMetadata() throws ResolutionException, FilterException {
        try {
            synchronized (wrappedResolver) {
                if(log.isDebugEnabled()){
                    log.debug("Attempting to fetch metadata from wrapped resolver");
                }
                cachedMetadata = wrappedResolver.resolve();
                
                if(log.isDebugEnabled()){
                    log.debug("Metadata fetched, determining when it expires");
                }
                DateTime now = new DateTime();
                cacheExpiration = getEarliestExpiration(cachedMetadata, now.plus(maxCacheDuration), now);
                if(log.isDebugEnabled()){
                    log.debug("Cached metadata will expire on " + cacheExpiration.toString());
                }
            }
        } catch (ResolutionException e) {
            cachedMetadata = null;
            throw e;
        } catch (FilterException e) {
            cachedMetadata = null;
            throw e;
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