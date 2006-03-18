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
import org.opensaml.saml1.core.Subject;

/**
 * Test for {@link org.opensaml.saml1.core.impl.Subject}
 */
public class SubjectTest extends SAMLObjectBaseTestCase {

    /**
     * Constructor
     */
    public SubjectTest() {
        super();

        singleElementFile = "/data/org/opensaml/saml1/singleSubject.xml";
        childElementsFile = "/data/org/opensaml/saml1/SubjectWithChildren.xml";
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        Subject subject = (Subject) unmarshallElement(singleElementFile);

        assertNull("Non zero number of child NameIdentifier elements", subject.getNameIdentifier());
        assertNull("Non zero number of child SubjectConfirmation elements", subject.getSubjectConfirmation());
    }

    /**
     * Test an XML file with children
     */
    public void testChildElementsUnmarshall() {
        Subject subject = (Subject) unmarshallElement(childElementsFile);

        assertNotNull("Zero child NameIdentifier elements", subject.getNameIdentifier());
        assertNotNull("Zero child SubjectConfirmation elements", subject.getSubjectConfirmation());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        Subject subject = new SubjectImpl(null);

        assertEquals(expectedDOM, subject);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    @Override
    public void testChildElementsMarshall() {
        Subject subject = new SubjectImpl(null);

        subject.setNameIdentifier(new NameIdentifierImpl(null));
        subject.setSubjectConfirmation(new SubjectConfirmationImpl(null));

        assertEquals(expectedChildElementsDOM, subject);
    }
}
