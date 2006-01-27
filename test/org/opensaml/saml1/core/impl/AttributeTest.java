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
import org.opensaml.common.xml.ParserPoolManager;
import org.opensaml.saml1.core.Attribute;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * 
 */
public class AttributeTest extends SAMLObjectBaseTestCase {

    /** A file with sub elements */
    private String fullElementsFile;

    /** The DOM of an element with subelements */
    private Document expectedFullDOM;

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
        fullElementsFile = "/data/org/opensaml/saml1/AttributeWithChildren.xml";
        expectedAttributeName = "AttributeName";
        expectedAttributeNamespace = "namespace";
    }

    /*
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        ParserPoolManager ppMgr = ParserPoolManager.getInstance();

        expectedFullDOM = ppMgr.parse(new InputSource(SAMLObjectBaseTestCase.class
                .getResourceAsStream(fullElementsFile)));
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

    /**
     * Test an XML file with children
     */

    public void testFullElementsUnmarshall() {
        Attribute attribute = (Attribute) unmarshallElement(fullElementsFile);

        assertNotNull("<AttributeValue> subelement not found", attribute.getAttributeValues());
        assertEquals("Number of <AttributeValue> subelement not found", 4, attribute.getAttributeValues().size());
        // TODO RemoveAllXXX
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

    /**
     * Test an XML file with Children
     */

    public void testFullElementsMarshall() {
        Attribute attribute = new AttributeImpl();

        attribute.getAttributeValues().add(new AttributeValueImpl());
        attribute.getAttributeValues().add(new AttributeValueImpl());
        attribute.getAttributeValues().add(new AttributeValueImpl());
        attribute.getAttributeValues().add(new AttributeValueImpl());

        assertEquals(expectedFullDOM, attribute);
    }

}
