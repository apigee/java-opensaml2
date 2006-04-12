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

package org.opensaml.saml2.metadata.cache;

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.ParserPoolManager;
import org.opensaml.saml2.metadata.resolver.MetadataResolver;
import org.opensaml.saml2.metadata.resolver.MetadataResolverFactory;
import org.opensaml.saml2.metadata.resolver.ResolutionException;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.XMLParserException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Something to test the functioning of the MetadataCache.  
 * 
 * These tests are really not suitable for automation, rather we are borrowing 
 * the SAMLObjectBaseTestCase framework to set up some cache situations which
 * we can then walk through the code by hand to ensure that the right thing is being
 * done  
 */
public class MetadataCacheTest extends SAMLObjectBaseTestCase {

    private static String TIMEOUT_URI = "timeout"; 
    private MetadataCacheImpl cache;
    private final ResolverFactory factory;
    private Document document; 
    
    public MetadataCacheTest() {
        factory = new ResolverFactory();
        try {
            document = factory.resolve(null);
        } catch (ResolutionException e) {
            document = null;
        }
    }
    
    protected void setUp() throws Exception {
        super.setUp();
        cache = new MetadataCacheImpl(Configuration.getUnmarshallerFactory(), 3, 20*60);
        try {
            cache.loadMetadata("one", factory);
            cache.loadMetadata("two", factory);
        } catch (ResolutionException e) {
            fail(e.toString());
        } catch (UnmarshallingException e) {
            fail(e.toString());
        }
    }
    
    protected void tearDown() {
        cache.stopWorker();
    }

    public void testLookup() {
        assertEquals(document, cache.retrieveEntities("one"));
    }

    public void testLookupSleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            fail(e.toString());
        }
        assertEquals(document, cache.retrieveEntities("one"));
    }
    
    public void testLookupInvalidate() {
        cache.invalidateMetadata("one");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            fail(e.toString());
        }
        assertEquals(document, cache.retrieveEntities("one"));
    }
    
    public void testLookupRemove() {
        cache.retrieveEntities("two");
        cache.retrieveEntities("one");
        cache.removeMetadata("one");
        assertNull("data was removed", cache.retrieveEntities("one"));
    }
    
    public void testLookupGC() {
        
        try {
            cache.loadMetadata("three", factory);
            cache.loadMetadata("four", factory);
        } catch (ResolutionException e) {
            fail(e.getStackTrace().toString());
        } catch (UnmarshallingException e) {
            fail(e.getStackTrace().toString());
        }
        
        //
        // prime LRU
        // 
        cache.retrieveEntities("one");
        cache.retrieveEntities("two");
        cache.retrieveEntities("one");
        cache.retrieveEntities("three");
        cache.retrieveEntities("two");
        cache.retrieveEntities("four");
        //
        // Now, possibly in vain try and pushd 'one' out of memory
        //
        System.gc();
        assertEquals(document, cache.retrieveEntities("one"));
        
    }

    public void testLookupTimeOut() {
        
        try {
            cache.loadMetadata(TIMEOUT_URI, factory);
            // 
            // A thread with a 4 second timeout
            //
            Thread.sleep(10*1000);
            
            assertEquals(factory.resolve(TIMEOUT_URI), cache.retrieveEntities(TIMEOUT_URI));

            Thread.sleep(1000);

            cache.removeMetadata(TIMEOUT_URI);
            assertNull(cache.retrieveEntities(TIMEOUT_URI));

        } catch (ResolutionException e) {
            fail(e.getStackTrace().toString());
        } catch (UnmarshallingException e) {
            fail(e.getStackTrace().toString());
        } catch (InterruptedException e) {
            fail(e.getStackTrace().toString());
        }
    }
    
    
    public void testSingleElementUnmarshall() {
        // Noop - we are only borrowing the framework
    }

    public void testSingleElementMarshall() {
        // Noop - we are only borrowing the framework              
    }
    private static class ResolverFactory implements MetadataResolverFactory, MetadataResolver {

        private static String NORMAL_FILE = "/data/org/opensaml/saml2/metadata/impl/EntitiesDescriptorChildElements.xml";
        private static String TIMOUT_FILE = "/data/org/opensaml/saml2/metadata/impl/EntitiesDescriptorWithCacheDuration.xml";
        
        public MetadataResolver createResolver() {
            return this;
        }

        public void setValidateXML(boolean validate) {
            
        }

        public boolean validateXML() {
            return true;
        }

        public void setValidateDigitalSignature(boolean validate) {
            
        }

        public boolean validateDigitalSignature() {
            return true;
        }

        public Document resolve(String metadataURI) throws ResolutionException {
            ParserPoolManager ppMgr = ParserPoolManager.getInstance();
            
            try {
                if (TIMEOUT_URI.equals(metadataURI)) {
                    return ppMgr.parse(new InputSource(SAMLObjectBaseTestCase.class
                            .getResourceAsStream(TIMOUT_FILE)));
                        
                }
                return ppMgr.parse(new InputSource(SAMLObjectBaseTestCase.class
                        .getResourceAsStream(NORMAL_FILE)));
            } catch (XMLParserException e) {
             throw new ResolutionException(e);
            }
        }
        
    }
    
}
