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

import org.opensaml.common.SAMLObjectTestCaseConfigInitializer;
import org.opensaml.saml2.metadata.resolver.impl.CountingURLResolver;

public class SoftReferenceMetadataCacheTest extends SAMLObjectTestCaseConfigInitializer {

    /** URL of InCommon metadata */
    private String incommonMDURL = "http://wayf.incommonfederation.org/InCommon/InCommon-metadata.xml";
    
    /** URL of InQueue metadata */
    private String inqueueMDURL = "http://wayf.incommonfederation.org/InCommon/InCommon-metadata.xml";
    
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
}