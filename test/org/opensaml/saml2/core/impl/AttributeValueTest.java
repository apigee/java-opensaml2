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
import org.opensaml.saml2.core.AttributeValue;


/**
 * Test case for creating, marshalling, and unmarshalling {@link org.opensaml.saml2.core.impl.AttributeValueImpl}.
 */
public class AttributeValueTest extends SAMLObjectBaseTestCase {

    protected String expectedValue;

    /** Constructor */
    public AttributeValueTest() {
        singleElementFile = "/data/org/opensaml/saml2/core/impl/AttributeValue.xml";
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        expectedValue = "Something";
    }

    /** 
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        AttributeValue attributeValue = (AttributeValue) unmarshallElement(singleElementFile);

        String value = attributeValue.getValue();
        assertEquals("value was " + value + ", expected " + expectedValue, expectedValue, value);
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
        QName qname = new QName(SAMLConstants.SAML20_NS, AttributeValue.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        AttributeValue attributeValue = (AttributeValue) buildSAMLObject(qname);

        attributeValue.setValue(expectedValue);
        assertEquals(expectedDOM, attributeValue);
    }

    /** 
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        // do nothing
    }
}