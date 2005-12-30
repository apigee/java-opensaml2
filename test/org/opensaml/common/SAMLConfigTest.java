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

import javax.xml.namespace.QName;

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.EntitiesDescriptor;

/**
 * Test case for OpenSAML library initialization and property access.
 */
public class SAMLConfigTest extends BaseTestCase {

    /*
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test that the library initialization worked
     */
    public void testInitializeOpenSAML() {
        assertTrue("Library should be initialized but isInitialized() returned false", SAMLConfig.isInitialized());

        assertFalse("ignoreUnknownAttributes was set to false but configuration reports true", SAMLConfig
                .ignoreUnknownAttributes());
        assertTrue("ingoreUnknownElements was set to true but configuration reports false", SAMLConfig
                .ignoreUnknownElements());

        QName entitiesDescriptorQName = new QName(SAMLConstants.SAML20MD_NS, EntitiesDescriptor.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        assertNotNull(
                "Object provider configuration for md:EntitiesDescriptor was provided in configuration file but was not available though getObjectProviderConfiguration(QName)",
                SAMLConfig.getObjectProviderConfiguration(entitiesDescriptorQName));
    }
}