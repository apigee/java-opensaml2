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

package org.opensaml.common;

import junit.framework.TestCase;

public class SAMLConfigTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /*
     * Test method for 'org.opensaml.common.SAMLConfig.initializeOpenSAML()'
     */
    public void testInitializeOpenSAML() {
        SAMLConfig.initializeOpenSAML();
        
        assertTrue("Library should be initialized but isInitialized() returned false", SAMLConfig.isInitialized());
        assertFalse("ignoreUnknownAttributes was set to false but configuration reports true", SAMLConfig.ignoreUnknownAttributes());
        assertTrue("ingoreUnknownElements was set to true but configuration reports false", SAMLConfig.ignoreUnknownElements());
    }

    /*
     * Test method for 'org.opensaml.common.SAMLConfig.getObjectProviderConfiguration(QName)'
     */
    public void testGetObjectProviderConfiguration() {

    }

}
