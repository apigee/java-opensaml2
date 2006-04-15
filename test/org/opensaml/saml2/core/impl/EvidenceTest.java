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
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.AssertionIDRef;
import org.opensaml.saml2.core.AssertionURIRef;
import org.opensaml.saml2.core.Evidence;

/**
 * Test case for creating, marshalling, and unmarshalling {@link org.opensaml.saml2.core.impl.EvidenceImpl}.
 */
public class EvidenceTest extends SAMLObjectBaseTestCase {

    /** Count of AssertionIDRef subelements */
    protected int assertionIDRefCount = 3;

    /** Count of AssertionURIRef subelements */
    protected int assertionURIRefCount = 4;

    /** Count of Assertion subelements */
    protected int assertionCount = 2;

    /** Constructor */
    public EvidenceTest() {
        singleElementFile = "/data/org/opensaml/saml2/core/impl/Evidence.xml";
        childElementsFile = "/data/org/opensaml/saml2/core/impl/EvidenceChildElements.xml";
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
        Evidence evidence = (Evidence) unmarshallElement(singleElementFile);

        assertNotNull(evidence);
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
        QName qname = new QName(SAMLConstants.SAML20_NS, Evidence.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        Evidence evidence = (Evidence) buildXMLObject(qname);

        assertEquals(expectedDOM, evidence);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        // do nothing
    }

    public void testChildElementsUnmarshall() {
        Evidence evidence = (Evidence) unmarshallElement(childElementsFile);

        assertEquals("AssertionIDRef count not as expected", assertionIDRefCount, evidence.getAssertionIDReferences()
                .size());
        assertEquals("AssertionURIRef count not as expected", assertionURIRefCount, evidence
                .getAssertionURIReferences().size());
        assertEquals("Assertion count not as expected", assertionCount, evidence.getAssertions().size());
    }

    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML20_NS, Evidence.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        Evidence evidence = (Evidence) buildXMLObject(qname);

        QName assertionIDRefQName = new QName(SAMLConstants.SAML20_NS, AssertionIDRef.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        for (int i = 0; i < assertionIDRefCount; i++) {
            evidence.getAssertionIDReferences().add((AssertionIDRef) buildXMLObject(assertionIDRefQName));
        }
        
        QName assertionURIRefQName = new QName(SAMLConstants.SAML20_NS, AssertionURIRef.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        for (int i = 0; i < assertionURIRefCount; i++) {
            evidence.getAssertionURIReferences().add((AssertionURIRef) buildXMLObject(assertionURIRefQName));
        }
        
        QName assertionQName = new QName(SAMLConstants.SAML20_NS, Assertion.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        for (int i = 0; i < assertionCount; i++) {
            evidence.getAssertions().add((Assertion) buildXMLObject(assertionQName));
        }
        assertEquals(expectedChildElementsDOM, evidence);
    }
}