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
import org.opensaml.saml2.core.Subject;

/**
 * Test case for creating, marshalling, and unmarshalling {@link org.opensaml.saml2.core.impl.SubjectImpl}.
 */
public class SubjectTest extends SAMLObjectBaseTestCase {

    /** Count of SubjectConfirmation subelements */
    protected int expectedSubjectConfirmationCount = 2;

    /** Constructor */
    public SubjectTest() {
        singleElementFile = "/data/org/opensaml/saml2/core/impl/Subject.xml";
        childElementsFile = "/data/org/opensaml/saml2/core/impl/SubjectChildElements.xml";
    }

    /*
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        Subject subject = (Subject) unmarshallElement(singleElementFile);

        assertNotNull(subject);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        // do nothing
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20_NS, Subject.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        Subject subject = (Subject) buildSAMLObject(qname);

        assertEquals(expectedDOM, subject);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        // do nothing
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    public void testChildElementsUnmarshall() {
        Subject subject = (Subject) unmarshallElement(childElementsFile);
        assertNotNull("Identifier element not present", subject.getIdentifier());
        assertEquals("SubjectConfirmation Count not as expected", expectedSubjectConfirmationCount, subject
                .getSubjectConfirmations().size());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML20_NS, Subject.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        Subject subject = (Subject) buildSAMLObject(qname);

        subject.setIdentifier(new NameIDImpl());
        for (int i = 0; i < expectedSubjectConfirmationCount; i++) {
            subject.getSubjectConfirmations().add(new SubjectConfirmationImpl());
        }

        assertEquals(expectedChildElementsDOM, subject);
    }
}