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
import org.opensaml.saml1.core.Evidence;
import org.opensaml.xml.IllegalAddException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Test for {@link EvidenceImpl}
 */
public class EvidenceTest extends SAMLObjectBaseTestCase {

    /**
     * Constructor
     */

    /** File to contain an Evidence element with children*/
    private final String fullElementsFile;
    
    /** Dom to contain an Evidence element with children*/
    private Document expectedFullDOM;

    
    public EvidenceTest() {
        super();
        singleElementFile = "/data/org/opensaml/saml1/singleEvidence.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/singleEvidence.xml";
        fullElementsFile = "/data/org/opensaml/saml1/EvidenceWithChildren.xml";
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
        Evidence evidence = (Evidence) unmarshallElement(singleElementFile);
        
        assertNull("AssertionIDReference element present", evidence.getAssertionIDReference());
        assertNull("Assertion element present", evidence.getAssertion());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesUnmarshall() {
        // No attributes, no test
    }
    
    /**
     * Test an XML file with children
     */

    public void testFullElementsUnmarshall() {
        Evidence evidence = (Evidence) unmarshallElement(fullElementsFile);
        
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
    public void testSingleElementOptionalAttributesMarshall() {
        Evidence evidence = new EvidenceImpl();
        try {
            evidence.setAssertion(new AssertionImpl());
            evidence.setAssertionIDReference(new AssertionIDReferenceImpl());
        } catch (IllegalAddException e) {
            fail("adding subelements threw " + e);
        }
        assertEquals(expectedFullDOM, evidence);
    }
}
