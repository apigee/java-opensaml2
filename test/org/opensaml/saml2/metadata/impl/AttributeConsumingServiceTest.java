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

package org.opensaml.saml2.metadata.impl;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.AttributeConsumingService;

/**
 * Test case for creating, marshalling, and unmarshalling
 * {@link org.opensaml.saml2.metadata.impl.AssertionConsumerServiceImpl}.
 */
public class AttributeConsumingServiceTest extends SAMLObjectBaseTestCase {
    
    protected int expectedIndex;
    protected Boolean expectedIsDefault;

    /**
     * Constructor
     */
    public AttributeConsumingServiceTest() {
        singleElementFile = "/data/org/opensaml/saml2/metadata/impl/AttributeConsumingService.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/metadata/impl/AttributeConsumingServiceOptionalAttributes.xml";
    }
    
    /*
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        expectedIndex = 1;
        expectedIsDefault = Boolean.TRUE;
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        AttributeConsumingService service = (AttributeConsumingService) unmarshallElement(singleElementFile);
        
        assertEquals("Index was not expected value", expectedIndex, service.getIndex());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        AttributeConsumingService service = (AttributeConsumingService) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertEquals("Index was not expected value", expectedIndex, service.getIndex());
        assertEquals("isDefault was not expected value", expectedIsDefault, service.isDefault());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, AttributeConsumingService.LOCAL_NAME);
        AttributeConsumingService service = (AttributeConsumingService) buildSAMLObject(qname);
        
        service.setIndex(expectedIndex);

        assertEquals(expectedDOM, service);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, AttributeConsumingService.LOCAL_NAME);
        AttributeConsumingService service = (AttributeConsumingService) buildSAMLObject(qname);
        
        service.setIndex(expectedIndex);
        service.setIsDefault(expectedIsDefault);

        assertEquals(expectedOptionalAttributesDOM, service);
    }
}