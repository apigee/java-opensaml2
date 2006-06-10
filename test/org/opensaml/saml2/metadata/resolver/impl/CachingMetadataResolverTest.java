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

package org.opensaml.saml2.metadata.resolver.impl;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.SAMLObjectTestCaseConfigInitializer;
import org.opensaml.saml2.metadata.resolver.FilterException;
import org.opensaml.saml2.metadata.resolver.ResolutionException;
import org.w3c.dom.Document;

/**
 * Tests the {@link org.opensaml.saml2.metadata.resolver.impl.CachingMetadataResolver}
 */
public class CachingMetadataResolverTest extends SAMLObjectTestCaseConfigInitializer {
    
    /** URL of the metadata */
    private String metadataURL = "http://wayf.incommonfederation.org/InCommon/InCommon-metadata.xml";

    /**
     * Tests that fetch metadata is cached and that repeated calls do not hit wrapped resolver.
     * 
     * @throws ResolutionException thrown if the metadata can not be resolved
     * @throws FilterException should never be thrown
     */
    public void testFetchFromCache() throws ResolutionException, FilterException{
        CountingURLResolver urlResolver = new CountingURLResolver("incommonMD", metadataURL);
        CachingMetadataResolver cachingResolver = new CachingMetadataResolver(urlResolver, 60 * 60);
        
        SAMLObject metadata = cachingResolver.resolve();
        assertNotNull("Metadata object was null", metadata);
        assertEquals("Resolution count was not expected value", 1, urlResolver.getNumberOfResolutions());
        
        metadata = cachingResolver.resolve();
        assertNotNull("Metadata object was null", metadata);
        assertEquals("Resolution count was not expected value", 1, urlResolver.getNumberOfResolutions());
        
        metadata = cachingResolver.resolve();
        assertNotNull("Metadata object was null", metadata);
        assertEquals("Resolution count was not expected value", 1, urlResolver.getNumberOfResolutions());
    }
    
    /**
     * Tests that the metadata cache is refreshed automatically.
     * 
     * @throws ResolutionException thrown if the metadata can not be resolved
     * @throws FilterException should never be thrown
     */
    public void testCacheRefreshing() throws ResolutionException, FilterException, InterruptedException{
        CountingURLResolver urlResolver = new CountingURLResolver("incommonMD", metadataURL);
        CachingMetadataResolver cachingResolver = new CachingMetadataResolver(urlResolver, 3);
        
        SAMLObject metadata = cachingResolver.resolve();
        assertNotNull("Metadata object was null", metadata);
        assertEquals("Resolution count was not expected value", 1, urlResolver.getNumberOfResolutions());
        
        Thread.sleep(3 * 1000);
        
        metadata = cachingResolver.resolve();
        assertNotNull("Metadata object was null", metadata);
        assertEquals("Resolution count was not expected value", 2, urlResolver.getNumberOfResolutions());
    }
    
    /**
     * An extension to {@link URLResolver} that counts the number of times metadata was fetched from the URL.
     */
    private class CountingURLResolver extends URLResolver{
        
        /** Number of resolutions made */
        private int resolutions;
        
        /**
         * Constructor
         *
         * @param resolverID ID of this resolver
         * @param metadataURL the URL to the metadata
         */
        public CountingURLResolver(String resolverID, String metadataURL){
            super(resolverID, metadataURL);
            resolutions = 0;
        }
        
        /**
         * Gets the number of times metadata was fetched from the URL.
         * 
         * @return the number of times metadata was fetched from the URL
         */
        public int getNumberOfResolutions(){
            return resolutions;
        }
        
        /** {@inheritDoc} */
        public Document retrieveDOM() throws ResolutionException {
            Document retrievedDOM = super.retrieveDOM();
            resolutions++;
            return retrievedDOM;
        }
    }
}