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
package org.opensaml.saml2.core.impl;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.SubjectQuery;

/**
 *
 */
public abstract class SubjectQueryTest extends RequestTest {

    /**
     * Constructor
     *
     */
    public SubjectQueryTest() {
        super();
    }
    

    /**
     * @see org.opensaml.saml2.core.impl.RequestTest#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }


    /**
     * @see org.opensaml.saml2.core.impl.RequestTest#testSingleElementUnmarshall()
     */
    public abstract void testSingleElementUnmarshall();

    /**
     * @see org.opensaml.saml2.core.impl.RequestTest#testSingleElementMarshall()
     */
    public abstract void testSingleElementMarshall();


    /**
     * @see org.opensaml.saml2.core.impl.RequestTest#populateChildElements(org.opensaml.common.SAMLObject)
     */
    protected void populateChildElements(SAMLObject samlObject) {
        SubjectQuery sq = (SubjectQuery) samlObject;
        
        super.populateChildElements(sq);
        
        QName subjectQName = new QName(SAMLConstants.SAML20_NS, Subject.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        sq.setSubject((Subject) buildXMLObject(subjectQName));
    }


    /**
     * @see org.opensaml.saml2.core.impl.RequestTest#populateOptionalAttributes(org.opensaml.common.SAMLObject)
     */
    protected void populateOptionalAttributes(SAMLObject samlObject) {
        super.populateOptionalAttributes(samlObject);
    }


    /**
     * @see org.opensaml.saml2.core.impl.RequestTest#populateRequiredAttributes(org.opensaml.common.SAMLObject)
     */
    protected void populateRequiredAttributes(SAMLObject samlObject) {
        super.populateRequiredAttributes(samlObject);
    }


    /**
     * @see org.opensaml.saml2.core.impl.RequestTest#helperTestChildElementsUnmarshall(org.opensaml.common.SAMLObject)
     */
    protected void helperTestChildElementsUnmarshall(SAMLObject samlObject) {
        SubjectQuery sq = (SubjectQuery) samlObject;
        super.helperTestChildElementsUnmarshall(sq);
        assertNotNull("Subject", sq.getSubject());
    }


    /**
     * @see org.opensaml.saml2.core.impl.RequestTest#helperTestSingleElementOptionalAttributesUnmarshall(org.opensaml.common.SAMLObject)
     */
    protected void helperTestSingleElementOptionalAttributesUnmarshall(SAMLObject samlObject) {
        super.helperTestSingleElementOptionalAttributesUnmarshall(samlObject);
    }


    /**
     * @see org.opensaml.saml2.core.impl.RequestTest#helperTestSingleElementUnmarshall(org.opensaml.common.SAMLObject)
     */
    protected void helperTestSingleElementUnmarshall(SAMLObject samlObject) {
        super.helperTestSingleElementUnmarshall(samlObject);
    }
}