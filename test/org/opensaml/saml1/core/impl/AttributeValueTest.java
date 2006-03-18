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
package org.opensaml.saml1.core.impl;

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.saml1.core.AttributeValue;

/**
 * Test case for {@link org.opensaml.saml1.core.impl.AttributeValue}
 */
public class AttributeValueTest extends SAMLObjectBaseTestCase {

    private final String expectedAttributeValue;
    
    /**
     * Constructor
     */
    public AttributeValueTest() {
        super();
        expectedAttributeValue = "Test Attribute Value";
        singleElementFile = "/data/org/opensaml/saml1/singleAttributeValue.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/singleAttributeValueContents.xml";
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    @Override
    public void testSingleElementUnmarshall() {
        AttributeValue attributeValue = (AttributeValue) unmarshallElement(singleElementFile);
        
        assertNull("Contents of <AttributeValue>", attributeValue.getAttributeValue());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesUnmarshall() {
        AttributeValue attributeValue = (AttributeValue) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertEquals("Contents of <AttributeValue>", expectedAttributeValue, attributeValue.getAttributeValue());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    @Override
    public void testSingleElementMarshall() {
        assertEquals(expectedDOM, new AttributeValueImpl(null));
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesMarshall() {
        AttributeValue attributeValue = new AttributeValueImpl(null);
        
        attributeValue.setAttributeValue(expectedAttributeValue);
        assertEquals(expectedOptionalAttributesDOM, attributeValue);
    }

}
