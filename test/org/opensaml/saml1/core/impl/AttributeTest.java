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
import org.opensaml.xml.IllegalAddException;
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
    
    /**
     * Constructor
     */
    public AttributeTest() {
        super();
        singleElementFile = "/data/org/opensaml/saml1/singleAttribute.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/singleAttribute.xml";
        fullElementsFile = "/data/org/opensaml/saml1/AttributeWithChildren.xml";
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
        
        assertNull("<Subject> subelement found", attribute.getSubject());
        assertNull("<AttributeValue> subelement found", attribute.getAttributeValues());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesUnmarshall() {
        // No attributes to test
    }

    /**
     * Test an XML file with children
     */

    public void testFullElementsUnmarshall() {
        Attribute attribute = (Attribute) unmarshallElement(fullElementsFile);
        
        assertNotNull("<Subject> subelement not found", attribute.getSubject());
        //TODO
        /*
        assertNotNull("<AttributeValue> subelement not found", attribute.getAttributeValues());
        assertEquals("Number of <AttributeValue> subelement not found", 4, attribute.getAttributeValues());
        */
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
        // No attributes
    }

    /**
     * Test an XML file with Children
     */
    
    public void testFullElementsMarshall() {
        Attribute attribute = new AttributeImpl();
        
        try {
            attribute.setSubject(new SubjectImpl());
            // TODO
            /*
            attribute.addAttributeValue(new AttributeValueImpl());
            attribute.addAttributeValue(new AttributeValueImpl());
            attribute.addAttributeValue(new AttributeValueImpl());
            attribute.addAttributeValue(new AttributeValueImpl());
            */
        } catch (IllegalAddException e) {
            fail("threw IllegalAddException");
        }

        // TODO assertEquals(expectedFullDOM, assertion);
    }

}
