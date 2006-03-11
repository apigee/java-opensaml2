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

        assertEquals("AssertionIDReference or Assertion element was present", 0, evidence.getEvidence().size());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    @Override
    public void testChildElementsUnmarshall() {
        Evidence evidence = (Evidence) unmarshallElement(childElementsFile);

        assertEquals("Assertion and AssertionIDReference element count", 4, evidence.getEvidence().size());
        assertEquals("AssertionIDReference element count", 2, evidence.getAssertionIDReferences().size());
        assertEquals("Assertion element count", 2, evidence.getAssertions().size());
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

        evidence.getAssertionIDReferences().add(new AssertionIDReferenceImpl());
        evidence.getAssertions().add(new AssertionImpl());
        evidence.getAssertions().add(new AssertionImpl());
        evidence.getAssertionIDReferences().add(new AssertionIDReferenceImpl());

        assertEquals(expectedChildElementsDOM, evidence);
    }
}
