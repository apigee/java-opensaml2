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
import org.opensaml.saml1.core.Advice;

/**
 * Test for {@link org.opensaml.saml1.core.impl.Advice}
 */
public class AdviceTest extends SAMLObjectBaseTestCase {

    /**
     * Constructor
     */
    public AdviceTest() {
        super();

        singleElementFile = "/data/org/opensaml/saml1/singleAdvice.xml";
        childElementsFile = "/data/org/opensaml/saml1/AdviceWithChildren.xml";
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        Advice advice = (Advice) unmarshallElement(singleElementFile);

        assertEquals("Number of child AssertIDReference elements", 0, advice.getAssertionIDReferences().size());
        assertEquals("Number of child Assertion elements", 0, advice.getAssertions().size());
    }

    /**
     * Test an XML file with children
     */
    public void testChildElementsUnmarshall() {
        Advice advice = (Advice) unmarshallElement(childElementsFile);

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
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    @Override
    public void testChildElementsMarshall() {
        Advice advice = new AdviceImpl();

        advice.getAssertionIDReferences().add(new AssertionIDReferenceImpl());
        advice.getAssertions().add(new AssertionImpl());
        advice.getAssertionIDReferences().add(new AssertionIDReferenceImpl());

        assertEquals(expectedChildElementsDOM, advice);
    }
}
