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
import org.opensaml.saml1.core.Subject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Test for {@link org.opensaml.saml1.core.impl.Subject}
 */
public class SubjectTest extends SAMLObjectBaseTestCase {

    private String fullElementsFile;
    private Document expectedFullDOM;
      
    /**
     * Constructor
     */
    public SubjectTest() {
        super();

        singleElementFile = "/data/org/opensaml/saml1/singleSubject.xml";
        singleElementOptionalAttributesFile  = "/data/org/opensaml/saml1/singleSubject.xml";
        fullElementsFile = "/data/org/opensaml/saml1/SubjectWithChildren.xml";
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
        Subject subject = (Subject) unmarshallElement(singleElementFile);
        
        assertNull("Non zero number of child NameIdentifier elements", subject.getNameIdentifier());
        assertNull("Non zero number of child SubjectConfirmation elements", subject.getSubjectConfirmation());
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
        // TODO Add in when NameIdentifier &  SubjectConfirmation done
        /*
        Subject subject = (Subject) unmarshallElement(fullElementsFile);
        
        assertNotNull("Zero child NameIdentifier elements", subject.getNameIdentifier());
        assertEquals("Zero child SubjectConfirmation elements", subject.getSubjectConfirmation());
        */
    }
    
    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        Subject subject = new SubjectImpl();
        
        assertEquals(expectedDOM, subject);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        // No attributes
    }

    /*
     * Generate an subject with contents
     */
    
    public void testFullElementsMarshall() {
        // TODO Add in when NameIdentifier &  SubjectConfirmation done
        /*
        Subject subject = new SubjectImpl();
              
        try {
            subject.setNameIdentifier(new NameIdentifierImpl());
            subject.setSubjectConfirmation(new SubjectConfirmationImpl());
        } catch (IllegalAddException e) {
            fail("Threw a IllegalAddException ");
        }
        assertEquals(expectedFullDOM, subject);
        */
    }
}
