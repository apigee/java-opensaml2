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
import org.opensaml.saml2.metadata.Organization;

/**
 * Test case for creating, marshalling, and unmarshalling
 * {@link org.opensaml.saml2.metadata.OrganizationName}.
 */
public class OrganizationTest extends SAMLObjectBaseTestCase {
    
    /**
     * Constructor
     */
    public OrganizationTest() {
        singleElementFile = "/data/org/opensaml/saml2/metadata/impl/Organization.xml";
        childElementsFile = "/data/org/opensaml/saml2/metadata/impl/OrganizationChildElements.xml";
    }
    
    /*
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        Organization org = (Organization) unmarshallElement(singleElementFile);
        assertEquals("Display names", 0, org.getDisplayNames().size());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    public void testChildElementsUnmarshall() {
        Organization org = (Organization) unmarshallElement(childElementsFile);
        
        assertNotNull("Extensions", org.getExtensions());
        assertEquals("OrganizationName count", 3, org.getOrganizationNames().size());
        assertEquals("DisplayNames count", 2, org.getDisplayNames().size());
        assertEquals("URL count", 1, org.getURLs().size());
    }
    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, Organization.LOCAL_NAME);
        Organization org = (Organization) buildXMLObject(qname);

        assertEquals(expectedDOM, org);
    }

    /**
     * 
     */
    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, Organization.LOCAL_NAME);
        Organization org = (Organization) buildXMLObject(qname);

        org.setExtensions(new ExtensionsImpl());
        for (int i = 0; i < 3; i++){
            org.getOrganizationNames().add(new OrganizationNameImpl());
        }
        for (int i = 0; i < 2; i++){
            org.getDisplayNames().add(new OrganizationDisplayNameImpl());
        }
        org.getURLs().add(new OrganizationURLImpl());
        assertEquals(expectedChildElementsDOM, org);
    }
 }