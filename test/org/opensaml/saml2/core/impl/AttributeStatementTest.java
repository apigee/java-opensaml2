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

package org.opensaml.saml2.core.impl;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;

/**
 * Test case for creating, marshalling, and unmarshalling {@link org.opensaml.saml2.core.impl.AttributeStatementImpl}.
 */
public class AttributeStatementTest extends SAMLObjectBaseTestCase {

    /** Count of Attribute subelements */
    protected int expectedAttributeCount = 3;

    /** Constructor */
    public AttributeStatementTest() {
        singleElementFile = "/data/org/opensaml/saml2/core/impl/AttributeStatement.xml";
        childElementsFile = "/data/org/opensaml/saml2/core/impl/AttributeStatementChildElements.xml";
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        AttributeStatement attributeStatement = (AttributeStatement) unmarshallElement(singleElementFile);

        assertNotNull(attributeStatement);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        // do nothing
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20_NS, AttributeStatement.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        AttributeStatement attributeStatement = (AttributeStatement) buildXMLObject(qname);

        assertEquals(expectedDOM, attributeStatement);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        // do nothing
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    public void testChildElementsUnmarshall() {
        AttributeStatement attributeStatement = (AttributeStatement) unmarshallElement(childElementsFile);
        assertEquals("Attribute Count", expectedAttributeCount, attributeStatement.getAttributes().size());
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML20_NS, AttributeStatement.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        AttributeStatement attributeStatement = (AttributeStatement) buildXMLObject(qname);

        QName attributeQName = new QName(SAMLConstants.SAML20_NS, Attribute.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        for (int i = 0; i < expectedAttributeCount; i++) {
            attributeStatement.getAttributes().add((Attribute) buildXMLObject(attributeQName));
        }

        assertEquals(expectedChildElementsDOM, attributeStatement);
    }
}