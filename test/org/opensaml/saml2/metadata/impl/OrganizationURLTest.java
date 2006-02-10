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

/**
 * 
 */
package org.opensaml.saml2.metadata.impl;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.OrganizationURL;

/**
 * Test case for creating, marshalling, and unmarshalling
 * {@link org.opensaml.saml2.metadata.OrganizationURL}.
 */
public class OrganizationURLTest extends SAMLObjectBaseTestCase {
    
    /** Expected URL */
    protected String expectURL;
    
    /**
     * Constructor
     */
    public OrganizationURLTest() {
        singleElementFile = "/data/org/opensaml/saml2/metadata/impl/OrganizationURL.xml";
    }
    
    /*
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        expectURL = "http://example.org";
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        OrganizationURL url = (OrganizationURL) unmarshallElement(singleElementFile);
        
        assertEquals("URL was not expected value", expectURL, url.getURL());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, OrganizationURL.LOCAL_NAME);
        OrganizationURL url = (OrganizationURL) buildSAMLObject(qname);
        
        url.setURL(expectURL);

        assertEquals(expectedDOM, url);
    }

}