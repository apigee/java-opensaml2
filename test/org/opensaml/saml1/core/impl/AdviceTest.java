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
import org.opensaml.saml1.core.Advice;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.AssertionIDReference;

/**
 * Test for {@link org.opensaml.saml1.core.impl.Advice}
 */
public class AdviceTest extends SAMLObjectBaseTestCase {

    /** name used to generate objects */
    private final QName qname;

    /**
     * Constructor
     */
    public AdviceTest() {
        super();

        singleElementFile = "/data/org/opensaml/saml1/singleAdvice.xml";
        childElementsFile = "/data/org/opensaml/saml1/AdviceWithChildren.xml";
        qname = new QName(SAMLConstants.SAML1_NS, Advice.LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
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
        assertEquals(expectedDOM, buildXMLObject(qname));
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    @Override
    public void testChildElementsMarshall() {
        Advice advice = (Advice) buildXMLObject(qname);
        
        QName assertionIDRefQname = new QName(SAMLConstants.SAML1P_NS, AssertionIDReference.LOCAL_NAME, SAMLConstants.SAML1P_PREFIX);
        QName assertionQname = new QName(SAMLConstants.SAML1P_NS, Assertion.LOCAL_NAME, SAMLConstants.SAML1P_PREFIX);
        
        advice.getAssertionIDReferences().add((AssertionIDReference) buildXMLObject(assertionIDRefQname));
        advice.getAssertions().add((Assertion) buildXMLObject(assertionQname) );
        advice.getAssertionIDReferences().add((AssertionIDReference) buildXMLObject(assertionIDRefQname));

        assertEquals(expectedChildElementsDOM, advice);
    }
}
