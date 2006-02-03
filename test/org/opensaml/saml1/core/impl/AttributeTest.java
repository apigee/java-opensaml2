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
import org.opensaml.saml1.core.Attribute;

/**
 * 
 */
public class AttributeTest extends SAMLObjectBaseTestCase {

    /** Value from test file */
    private final String expectedAttributeName;

    /** Value from test file */
    private final String expectedAttributeNamespace;

    /**
     * Constructor
     */
    public AttributeTest() {
        super();
        singleElementFile = "/data/org/opensaml/saml1/singleAttribute.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/singleAttributeAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml1/AttributeWithChildren.xml";
        expectedAttributeName = "AttributeName";
        expectedAttributeNamespace = "namespace";
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    @Override
    public void testSingleElementUnmarshall() {
        Attribute attribute = (Attribute) unmarshallElement(singleElementFile);

        assertNull("AttributeName", attribute.getAttributeName());
        assertNull("AttributeNamespace", attribute.getAttributeNamespace());
        assertEquals("<AttributeValue> subelement found", 0, attribute.getAttributeValues().size());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesUnmarshall() {
        Attribute attribute = (Attribute) unmarshallElement(singleElementOptionalAttributesFile);

        assertEquals("AttributeName", expectedAttributeName, attribute.getAttributeName());
        assertEquals("AttributeNamespace", expectedAttributeNamespace, attribute.getAttributeNamespace());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    @Override
    public void testChildElementsUnmarshall() {
        Attribute attribute = (Attribute) unmarshallElement(childElementsFile);

        assertNotNull("<AttributeValue> subelement not found", attribute.getAttributeValues());
        assertEquals("Number of <AttributeValue> subelement not found", 4, attribute.getAttributeValues().size());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    @Override
    public void testSingleElementMarshall() {
        assertEquals(expectedDOM, new AttributeImpl());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesMarshall() {
        Attribute attribute = new AttributeImpl();

        attribute.setAttributeName(expectedAttributeName);
        attribute.setAttributeNamespace(expectedAttributeNamespace);
        assertEquals(expectedOptionalAttributesDOM, attribute);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    @Override
    public void testChildElementsMarshall() {
        Attribute attribute = new AttributeImpl();

        attribute.getAttributeValues().add(new AttributeValueImpl());
        attribute.getAttributeValues().add(new AttributeValueImpl());
        attribute.getAttributeValues().add(new AttributeValueImpl());
        attribute.getAttributeValues().add(new AttributeValueImpl());

        assertEquals(expectedChildElementsDOM, attribute);
    }

}
