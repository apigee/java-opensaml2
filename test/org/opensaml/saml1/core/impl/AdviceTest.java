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
import org.opensaml.saml1.core.Advice;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Test for {@link org.opensaml.saml1.core.impl.Advice}
 */
public class AdviceTest extends SAMLObjectBaseTestCase {

    private String fullElementsFile;

    private Document expectedFullDOM;

    /**
     * Constructor
     */
    public AdviceTest() {
        super();

        singleElementFile = "/data/org/opensaml/saml1/singleAdvice.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/singleAdvice.xml";
        fullElementsFile = "/data/org/opensaml/saml1/AdviceWithChildren.xml";
    }

    /*
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        ParserPoolManager ppMgr = ParserPoolManager.getInstance();

        expectedFullDOM = ppMgr.parse(new InputSource(SAMLObjectBaseTestCase.class
                .getResourceAsStream(fullElementsFile)));
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        Advice advice = (Advice) unmarshallElement(singleElementFile);

        assertNull("Non zero number of child AssertIDReference elements", advice.getAssertionIDReferences());
        assertNull("Non zero number of child Assertion elements", advice.getAssertions());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        // No attributes
    }

    /**
     * Test an XML file with children
     */
    public void testFullElementsUnmarshall() {
        Advice advice = (Advice) unmarshallElement(fullElementsFile);

        assertEquals("Number of child AssertIDReference elements", 2, advice.getAssertionIDReferences().size());
        assertEquals("Number of child Assertion elements", 1, advice.getAssertions().size());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        Advice advice = new AdviceImpl();

        assertEquals(expectedDOM, advice);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        // No attributes
    }

    /*
     * Generate an advice with contents
     */

    public void testFullElementsMarshall() {
        Advice advice = new AdviceImpl();

        advice.addAssertionIDReference(new AssertionIDReferenceImpl());
        advice.addAssertion(new AssertionImpl());
        advice.addAssertionIDReference(new AssertionIDReferenceImpl());

        assertEquals(expectedFullDOM, advice);
    }
}
