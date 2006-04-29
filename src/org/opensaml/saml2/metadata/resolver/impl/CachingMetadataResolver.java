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

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.opensaml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml2.common.TimeBoundSAMLObject;
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
 * can be set and retrieved through this resolvers methods.
 */
public class CachingMetadataResolver implements MetadataResolver {

    /** The wrapped resolver */
    private MetadataResolver wrappedResolver;
    
    /** Maximum amount of time, in seconds, metadata will be cached */
    private Duration maxCacheDuration;

    /** The cached metadata */
    private XMLObject cachedMetadata;

    /** The date/time after which the cache is expired */
    private DateTime cacheExpiration;

    /**
     * Constructor
     * 
     * @param resolver the resolver whose returned metadata will be cached
     */
    public CachingMetadataResolver(MetadataResolver resolver) {
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
        if(maxCacheDuration > 0){
            this.maxCacheDuration = new Duration(maxCacheDuration * 1000);
        }
    }

    /**
     * {@inheritDoc}
     */
    public XMLObject resolve() throws ResolutionException, FilterException {
        try {
            if (cachedMetadata == null) {
                synchronized (wrappedResolver) {
                    cachedMetadata = wrappedResolver.resolve();
                }
            } else {
                if (cacheExpiration.isBeforeNow()) {
                    synchronized (wrappedResolver) {
                        cachedMetadata = wrappedResolver.resolve();
                        DateTime now = new DateTime();
                        cacheExpiration = getEarliestExpiration(cachedMetadata, now.plus(maxCacheDuration), now);
                    }
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
     * Gets the earliest expiration instant within a metadata tree.
     * 
     * @param metadata the metadata
     * @param earliestExpiration the earliest expiration instant
     * @param now when this method was called
     * 
     * @return the earliest expiration instant within a metadata tree
     */
    private DateTime getEarliestExpiration(XMLObject metadata, DateTime earliestExpiration, DateTime now) {
        // earliest time thus far
        DateTime expirationTime = earliestExpiration;
        
        // expiration time for a specific element
        DateTime elementExpirationTime;
        
        // Test duration based times
        if (metadata instanceof CacheableSAMLObject) {
            CacheableSAMLObject cacheInfo = (CacheableSAMLObject) metadata;
            
            if (cacheInfo.getCacheDuration() != null && cacheInfo.getCacheDuration().longValue() > 0) {
                elementExpirationTime = now.plus(cacheInfo.getCacheDuration().longValue());
                if(elementExpirationTime.isBefore(expirationTime)){
                    expirationTime = elementExpirationTime;
                }
            }
        }

        // Test instant based times
        if (metadata instanceof TimeBoundSAMLObject) {
            TimeBoundSAMLObject timeBoundObject = (TimeBoundSAMLObject) metadata;
            elementExpirationTime = timeBoundObject.getValidUntil();
            
            if(elementExpirationTime != null && elementExpirationTime.isBefore(earliestExpiration)){
                earliestExpiration = elementExpirationTime;
            }
        }

        for (XMLObject child : metadata.getOrderedChildren()) {
            earliestExpiration = getEarliestExpiration(child, earliestExpiration, now);
        }
        return earliestExpiration;
    }
}