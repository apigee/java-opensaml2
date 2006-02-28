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
import org.opensaml.saml2.core.Advice;

/**
 * Test case for creating, marshalling, and unmarshalling {@link org.opensaml.saml2.core.impl.AdviceImpl}.
 */
public class AdviceTest extends SAMLObjectBaseTestCase {

    /** Count of AssertionIDRef subelements */
    protected int assertionIDRefCount = 3;

    /** Count of AssertionURIRef subelements */
    protected int assertionURIRefCount = 2;

    /** Count of Assertion subelements */
    protected int assertionCount = 2;

    /** Constructor */
    public AdviceTest() {
        singleElementFile = "/data/org/opensaml/saml2/core/impl/Advice.xml";
        childElementsFile = "/data/org/opensaml/saml2/core/impl/AdviceChildElements.xml";
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
        Advice advice = (Advice) unmarshallElement(singleElementFile);

        assertNotNull(advice);
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
        QName qname = new QName(SAMLConstants.SAML20_NS, Advice.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        Advice advice = (Advice) buildXMLObject(qname);

        assertEquals(expectedDOM, advice);
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
        Advice advice = (Advice) unmarshallElement(childElementsFile);

        assertEquals("AssertionIDRef count not as expected", assertionIDRefCount, advice.getAssertionIDReferences()
                .size());
        assertEquals("AssertionURIRef count not as expected", assertionURIRefCount, advice.getAssertionURIReferences()
                .size());
        assertEquals("Assertion count not as expected", assertionCount, advice.getAssertions().size());
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML20_NS, Advice.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        Advice advice = (Advice) buildXMLObject(qname);

        for (int i = 0; i < assertionIDRefCount; i++) {
            advice.getAssertionIDReferences().add(new AssertionIDRefImpl());
        }
        for (int i = 0; i < assertionURIRefCount; i++) {
            advice.getAssertionURIReferences().add(new AssertionURIRefImpl());
        }
        for (int i = 0; i < assertionCount; i++) {
            advice.getAssertions().add(new AssertionImpl());
        }
        assertEquals(expectedChildElementsDOM, advice);
    }
}