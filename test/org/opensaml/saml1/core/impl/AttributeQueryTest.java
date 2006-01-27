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

import java.util.List;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.ParserPoolManager;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.AttributeDesignator;
import org.opensaml.saml1.core.AttributeQuery;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Test class for org.opensaml.saml1.core.AttributeQuery
 */
public class AttributeQueryTest extends SAMLObjectBaseTestCase {

    /** A file with a AuthenticationQuery with kids */

    private final String fullElementsFile;

    /** The expected result of a marshalled multiple element */

    private Document expectedFullDOM;

    private final String expectedResource;

    /**
     * Constructor
     */
    public AttributeQueryTest() {
        singleElementFile = "/data/org/opensaml/saml1/singleAttributeQuery.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/singleAttributeQueryAttributes.xml";
        fullElementsFile = "/data/org/opensaml/saml1/AttributeQueryWithChildren.xml";

        expectedResource = "resource";
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        ParserPoolManager ppMgr = ParserPoolManager.getInstance();

        expectedFullDOM = ppMgr.parse(new InputSource(SAMLObjectBaseTestCase.class
                .getResourceAsStream(fullElementsFile)));
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    @Override
    public void testSingleElementUnmarshall() {

        AttributeQuery attributeQuery;
        attributeQuery = (AttributeQuery) unmarshallElement(singleElementFile);

        assertNull("Resource attribute present", attributeQuery.getResource());
        assertNull("Subject element present", attributeQuery.getSubject());
        assertEquals("Count of AttributeDesignator elements", 0, attributeQuery.getAttributeDesignators().size());
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesUnmarshall() {
        AttributeQuery attributeQuery;
        attributeQuery = (AttributeQuery) unmarshallElement(singleElementOptionalAttributesFile);

        assertEquals("Resource attribute", expectedResource, attributeQuery.getResource());
        assertNull("Subject element present", attributeQuery.getSubject());
        assertEquals("Count of AttributeDesignator elements", 0, attributeQuery.getAttributeDesignators().size());
    }

    /**
     * Test an Response file with children
     */

    public void testFullElementsUnmarshall() {
        AttributeQuery attributeQuery;
        attributeQuery = (AttributeQuery) unmarshallElement(fullElementsFile);

        assertNotNull("Subject element present", attributeQuery.getSubject());
        assertEquals("Count of AttributeDesignator elements", 4, attributeQuery.getAttributeDesignators().size());
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    @Override
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML1P_NS, AttributeQuery.LOCAL_NAME);
        
        assertEquals(expectedDOM, buildSAMLObject(qname));
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML1P_NS, AttributeQuery.LOCAL_NAME);
        AttributeQuery attributeQuery;
        attributeQuery = (AttributeQuery) buildSAMLObject(qname);

        attributeQuery.setResource(expectedResource);
        assertEquals(expectedOptionalAttributesDOM, attributeQuery);
    }

    /**
     * Test Marshalling up a file with children
     * 
     */

    public void testFullElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML1P_NS, AttributeQuery.LOCAL_NAME);
        AttributeQuery attributeQuery;
        attributeQuery = (AttributeQuery) buildSAMLObject(qname);

        attributeQuery.setSubject(new SubjectImpl());
        List <AttributeDesignator> list = attributeQuery.getAttributeDesignators();
        list.add(new AttributeImpl());
        list.add(new AttributeImpl());
        list.add(new AttributeImpl());
        list.add(new AttributeImpl());
        assertEquals(expectedFullDOM, attributeQuery);

    }

}
