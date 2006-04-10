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

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.SubjectConfirmationData;

/**
 * Test for {@link org.opensaml.saml1.core.impl.SubjectConfirmationData}
 */
public class SubjectConfirmationDataTest extends SAMLObjectBaseTestCase {

    /** name used to generate objects */
    private final QName qname;

    private String expectedSubjectConfirmationData;
    /**
     * Constructor
     *
     */
    public SubjectConfirmationDataTest() {
        super();
        singleElementFile = "/data/org/opensaml/saml1/singleSubjectConfirmationData.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/singleSubjectConfirmationDataAttributes.xml";
        expectedSubjectConfirmationData = "subjectconfirmation";
        qname = new QName(SAMLConstants.SAML1_NS, SubjectConfirmationData.LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    @Override
    public void testSingleElementUnmarshall() {
        SubjectConfirmationData subjectConfirmationData;
        
        subjectConfirmationData = (SubjectConfirmationData) unmarshallElement(singleElementFile);
        
        assertNull("contents of SubjectConfirmationData", subjectConfirmationData.getConfirmationData());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesUnmarshall() {
        SubjectConfirmationData subjectConfirmationData;
        
        subjectConfirmationData = (SubjectConfirmationData) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertEquals("contents of SubjectConfirmationData", expectedSubjectConfirmationData, subjectConfirmationData.getConfirmationData());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    @Override
    public void testSingleElementMarshall() {
        assertEquals(expectedDOM, buildXMLObject(qname));
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesMarshall() {
        SubjectConfirmationData subjectConfirmationData = (SubjectConfirmationData)  buildXMLObject(qname);
        
        subjectConfirmationData.setConfirmationData(expectedSubjectConfirmationData);

        assertEquals(expectedOptionalAttributesDOM, subjectConfirmationData);
    }

}
