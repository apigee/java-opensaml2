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

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Attribute;
import org.opensaml.saml1.core.AttributeValue;
import org.opensaml.xml.schema.XSIString;
import org.opensaml.xml.schema.impl.XSIStringBuilder;

/**
 * 
 */
public class AttributeTest extends SAMLObjectBaseTestCase {

    /** name used to generate objects */
    private final QName qname;

    /** Value from test file */
    private final String expectedAttributeName;

    /** Value from test file */
    private final String expectedAttributeNamespace;

    /**
     * Constructor
     */
    public AttributeTest() {
        super();
        singleElementFile = "/data/org/opensaml/saml1/impl/singleAttribute.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/impl/singleAttributeAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml1/impl/AttributeWithChildren.xml";
        expectedAttributeName = "AttributeName";
        expectedAttributeNamespace = "namespace";
        qname = new QName(SAMLConstants.SAML1_NS, Attribute.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
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
        assertEquals(expectedDOM, buildXMLObject(qname));
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesMarshall() {
        Attribute attribute = (Attribute) buildXMLObject(qname);

        attribute.setAttributeName(expectedAttributeName);
        attribute.setAttributeNamespace(expectedAttributeNamespace);
        assertEquals(expectedOptionalAttributesDOM, attribute);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    @Override
    public void testChildElementsMarshall() {
        Attribute attribute = (Attribute) buildXMLObject(qname);

        XSIStringBuilder attributeValueBuilder = (XSIStringBuilder) builderFactory.getBuilder(XSIString.TYPE_NAME);
        
        attribute.getAttributeValues().add(attributeValueBuilder.buildObject(AttributeValue.DEFUALT_ELEMENT_NAME)); 
        attribute.getAttributeValues().add(attributeValueBuilder.buildObject(AttributeValue.DEFUALT_ELEMENT_NAME)); 
        attribute.getAttributeValues().add(attributeValueBuilder.buildObject(AttributeValue.DEFUALT_ELEMENT_NAME)); 
        attribute.getAttributeValues().add(attributeValueBuilder.buildObject(AttributeValue.DEFUALT_ELEMENT_NAME)); 

        assertEquals(expectedChildElementsDOM, attribute);
    }
}