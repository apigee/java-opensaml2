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
import org.opensaml.saml1.core.Evidence;

/**
 * Test for {@link EvidenceImpl}
 */
public class EvidenceTest extends SAMLObjectBaseTestCase {

    /**
     * Constructor
     */

    public EvidenceTest() {
        super();
        singleElementFile = "/data/org/opensaml/saml1/singleEvidence.xml";
        childElementsFile = "/data/org/opensaml/saml1/EvidenceWithChildren.xml";
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    @Override
    public void testSingleElementUnmarshall() {
        Evidence evidence = (Evidence) unmarshallElement(singleElementFile);

        assertNull("AssertionIDReference element present", evidence.getAssertionIDReference());
        assertNull("Assertion element present", evidence.getAssertion());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    @Override
    public void testChildElementsUnmarshall() {
        Evidence evidence = (Evidence) unmarshallElement(childElementsFile);

        assertNotNull("AssertionIDReference element not present", evidence.getAssertionIDReference());
        assertNotNull("Assertion element not present", evidence.getAssertion());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    @Override
    public void testSingleElementMarshall() {
        assertEquals(expectedDOM, new EvidenceImpl());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    @Override
    public void testChildElementsMarshall() {
        Evidence evidence = new EvidenceImpl();

        evidence.setAssertion(new AssertionImpl());
        evidence.setAssertionIDReference(new AssertionIDReferenceImpl());

        assertEquals(expectedChildElementsDOM, evidence);
    }
}
