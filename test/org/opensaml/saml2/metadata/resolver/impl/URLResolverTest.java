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

/**
 * Test that {@link org.opensaml.saml2.metadata.resolver.impl.URLResolver} can properly fetch and 
 * unmarshall a metadata document.
 */
public class URLResolverTest extends SAMLObjectTestCaseConfigInitializer {

    /** URL of the metadata */
    private String metadataURL = "http://wayf.incommonfederation.org/InCommon/InCommon-metadata.xml";
    
    /**
     * Tests resolving the metadata.
     * 
     * @throws ResolutionException thrown if the metadata can not be resolved
     * @throws FilterException should never be thrown
     */
    public void testResolve() throws ResolutionException, FilterException{
        URLResolver resolver = new URLResolver("incommonMD", metadataURL);
        
        SAMLObject metadata = resolver.resolve();
        
        assertNotNull("Metadata was null", metadata);
    }
}