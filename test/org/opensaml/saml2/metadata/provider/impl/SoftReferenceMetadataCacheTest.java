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

import java.util.List;

import org.opensaml.common.SAMLObjectTestCaseConfigInitializer;
import org.opensaml.saml2.metadata.provider.MetadataCache.CacheEntry;
import org.opensaml.saml2.metadata.resolver.MetadataResolver;
import org.opensaml.saml2.metadata.resolver.impl.CountingURLResolver;

public class SoftReferenceMetadataCacheTest extends SAMLObjectTestCaseConfigInitializer {

    /** URL of InCommon metadata */
    private String incommonMDURL = "http://wayf.incommonfederation.org/InCommon/InCommon-metadata.xml";
    
    /** URL of InQueue metadata */
    private String inqueueMDURL = "http://wayf.incommonfederation.org/InCommon/InCommon-metadata.xml";
    
    /**
     * Tests fetching cached metadata.
     */
    public void testFetchFromCache(){
        CountingURLResolver icResolver = new CountingURLResolver("incommon", incommonMDURL);
        CountingURLResolver iqResolver = new CountingURLResolver("inqueue", inqueueMDURL);
        
        SoftReferenceMetadataCache cache = new SoftReferenceMetadataCache(3600L, 5, 300L);
        cache.addMetadataResolver(icResolver);
        cache.addMetadataResolver(iqResolver);
        
        assertNotNull("InCommon metadata was null", cache.getMetadata("incommon"));
        assertEquals("InCommon resolution count was not expected value", 1, icResolver.getNumberOfResolutions());
        assertNotNull("InCommon metadata was null", cache.getMetadata("incommon"));
        assertEquals("InCommon resolution count was not expected value", 1, icResolver.getNumberOfResolutions());
        
        assertNotNull("InQueue metadata was null", cache.getMetadata("inqueue"));
        assertEquals("InQueue resolution count was not expected value", 1, iqResolver.getNumberOfResolutions());
        assertNotNull("InQueue metadata was null", cache.getMetadata("inqueue"));
        assertEquals("InQueue resolution count was not expected value", 1, iqResolver.getNumberOfResolutions());
    }
    
    /**
     * Tests that the cache refreshes metadata.
     */
    public void testCacheRefresh(){
        CountingURLResolver icResolver = new CountingURLResolver("incommon", incommonMDURL);
        CountingURLResolver iqResolver = new CountingURLResolver("inqueue", inqueueMDURL);
        
        SoftReferenceMetadataCache cache = new SoftReferenceMetadataCache(5L, 5, 300L);
        cache.addMetadataResolver(icResolver);
        cache.addMetadataResolver(iqResolver);
        
        try{
            Thread.sleep(15*1000);
        }catch(InterruptedException e){
            
        }
        
        assertNotNull("InCommon metadata was null", cache.getMetadata("incommon"));
        assertTrue("InCommon resolution count was not expected value", icResolver.getNumberOfResolutions() > 1);
        assertNotNull("InQueue metadata was null", cache.getMetadata("inqueue"));
        assertTrue("InQueue resolution count was not expected value", iqResolver.getNumberOfResolutions() > 1);
        
        cache.destroyCache();
    }
    
    /**
     * Tests that loaded resolvers can be fetched.
     */
    public void testGetMetadataResolvers(){
        CountingURLResolver icResolver = new CountingURLResolver("incommon", incommonMDURL);
        CountingURLResolver iqResolver = new CountingURLResolver("inqueue", inqueueMDURL);
        
        SoftReferenceMetadataCache cache = new SoftReferenceMetadataCache(3600L, 5, 300L);
        cache.addMetadataResolver(icResolver);
        cache.addMetadataResolver(iqResolver);
        
        List<MetadataResolver> resolvers = cache.getMetadataResolvers();
        assertEquals("Resolver list size was unexepected", 2, resolvers.size());
    }
    
    /**
     * Tests clearing the cache
     */
    public void testClearCache(){
        CountingURLResolver icResolver = new CountingURLResolver("incommon", incommonMDURL);
        CountingURLResolver iqResolver = new CountingURLResolver("inqueue", inqueueMDURL);
        
        SoftReferenceMetadataCache cache = new SoftReferenceMetadataCache(3600L, 5, 300L);
        cache.addMetadataResolver(icResolver);
        cache.addMetadataResolver(iqResolver);
        
        cache.clearCache();
        List<? extends CacheEntry> entries = cache.getCacheEntries();
        assertEquals("Cache entry list size was unexepected", 0, entries.size());
    }
    
    /**
     * Tests that observers fire appropriately.
     */
    public void testCacheObservation(){
        CountingURLResolver icResolver = new CountingURLResolver("incommon", incommonMDURL);
        CountingURLResolver iqResolver = new CountingURLResolver("inqueue", inqueueMDURL);
        
        SoftReferenceMetadataCache cache = new SoftReferenceMetadataCache(3600L, 5, 300L);
        
        CountingObserver observer = new CountingObserver();
        cache.getCacheObservers().add(observer);
        
        cache.addMetadataResolver(icResolver);
        assertEquals("Observer did not have expected invocation count", 1, observer.getCount());
        
        cache.addMetadataResolver(iqResolver);
        assertEquals("Observer did not have expected invocation count", 2, observer.getCount());
        
        cache.removeMetadataResolver(iqResolver);
        assertEquals("Observer did not have expected invocation count", 3, observer.getCount());
    }
}