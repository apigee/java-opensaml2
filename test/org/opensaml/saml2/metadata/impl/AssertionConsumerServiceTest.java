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
import org.opensaml.saml2.metadata.AssertionConsumerService;

/**
 * Test case for creating, marshalling, and unmarshalling
 * {@link org.opensaml.saml2.metadata.impl.AssertionConsumerServiceImpl}.
 */
public class AssertionConsumerServiceTest extends SAMLObjectBaseTestCase {
    
    protected String expectedBinding;
    protected String expectedLocation;
    protected String expectedResponseLocation;
    protected Integer expectedIndex;
    protected Boolean expectedIsDefault;
    
    /**
     * Constructor
     */
    public AssertionConsumerServiceTest() {
        singleElementFile = "/data/org/opensaml/saml2/metadata/impl/AssertionConsumerService.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/metadata/impl/AssertionConsumerServiceOptionalAttributes.xml";
    }
    
    /*
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        expectedBinding = "urn:binding:foo";
        expectedLocation = "example.org";
        expectedResponseLocation = "example.org/response";
        expectedIndex = new Integer(3);
        expectedIsDefault = Boolean.TRUE;
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        AssertionConsumerService service = (AssertionConsumerService) unmarshallElement(singleElementFile);
        
        assertEquals("Binding URI was not expected value", expectedBinding, service.getBinding());
        assertEquals("Location was not expected value", expectedLocation, service.getLocation());
        assertEquals("Index was not expected value", expectedIndex, service.getIndex());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        AssertionConsumerService service = (AssertionConsumerService) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertEquals("Binding URI was not expected value", expectedBinding, service.getBinding());
        assertEquals("Location was not expected value", expectedLocation, service.getLocation());
        assertEquals("Index was not expected value", expectedIndex, service.getIndex());
        assertEquals("ResponseLocation was not expected value", expectedResponseLocation, service.getResponseLocation());
        assertEquals("isDefault was not expected value", expectedIsDefault, service.isDefault());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, AssertionConsumerService.DEFAULT_ELEMENT_LOCAL_NAME);
        AssertionConsumerService service = (AssertionConsumerService) buildXMLObject(qname);
        
        service.setBinding(expectedBinding);
        service.setLocation(expectedLocation);
        service.setIndex(expectedIndex);

        assertEquals(expectedDOM, service);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, AssertionConsumerService.DEFAULT_ELEMENT_LOCAL_NAME);
        AssertionConsumerService service = (AssertionConsumerService) buildXMLObject(qname);
        
        service.setBinding(expectedBinding);
        service.setLocation(expectedLocation);
        service.setIndex(expectedIndex);
        service.setResponseLocation(expectedResponseLocation);
        service.setDefault(expectedIsDefault);

        assertEquals(expectedOptionalAttributesDOM, service);
    }
}