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

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Assertion;

/**
 * Test case for creating, marshalling, and unmarshalling {@link org.opensaml.saml2.core.impl.ConditionsImpl}.
 */
public class AssertionTest extends SAMLObjectBaseTestCase {

    /** Expected IssueInstant value */
    private DateTime expectedIssueInstant;

    /** Expected ID value */
    private String expectedID;

    /** Count of Statement subelements */
    private int statementCount = 7;

    /** Count of AuthnStatement subelements */
    private int authnStatementCount = 2;

    /** Count of AuthzDecisionStatement submelements */
    private int authzDecisionStatementCount = 2;

    /** Count of AttributeStatement subelements */
    private int attributeStatementCount = 3;

    /** Constructor */
    public AssertionTest() {
        singleElementFile = "/data/org/opensaml/saml2/core/impl/Assertion.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/core/impl/AssertionOptionalAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml2/core/impl/AssertionChildElements.xml";
    }

    /*
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        expectedIssueInstant = new DateTime(1984, 8, 26, 10, 01, 30, 43, ISOChronology.getInstanceUTC());
        expectedID = "id";
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        Assertion assertion = (Assertion) unmarshallElement(singleElementFile);

        DateTime notBefore = assertion.getIssueInstant();
        assertEquals("IssueInstant was " + notBefore + ", expected " + expectedIssueInstant, expectedIssueInstant,
                notBefore);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        Assertion assertion = (Assertion) unmarshallElement(singleElementOptionalAttributesFile);

        DateTime issueInstant = assertion.getIssueInstant();
        assertEquals("IssueInstant was " + issueInstant + ", expected " + expectedIssueInstant, expectedIssueInstant,
                issueInstant);

        String id = assertion.getID();
        assertEquals("ID was " + id + ", expected " + expectedID, expectedID, id);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20_NS, Assertion.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        Assertion assertion = (Assertion) buildSAMLObject(qname);

        assertion.setIssueInstant(expectedIssueInstant);
        assertEquals(expectedDOM, assertion);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML20_NS, Assertion.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        Assertion assertion = (Assertion) buildSAMLObject(qname);

        assertion.setIssueInstant(expectedIssueInstant);
        assertion.setID(expectedID);

        assertEquals(expectedOptionalAttributesDOM, assertion);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    public void testChildElementsUnmarshall() {
        Assertion assertion = (Assertion) unmarshallElement(childElementsFile);

        assertNotNull("Issuer element not present", assertion.getIssuer());
        assertNotNull("Subject element not present", assertion.getSubject());
        assertNotNull("Conditions element not present", assertion.getConditions());
        assertNotNull("Advice element not present", assertion.getAdvice());
        assertEquals("Statement count not as expected", statementCount, assertion.getStatements().size());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML20_NS, Assertion.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        Assertion assertion = (Assertion) buildSAMLObject(qname);

        assertion.setIssuer(new IssuerImpl());
        assertion.setSubject(new SubjectImpl());
        assertion.setConditions(new ConditionsImpl());
        assertion.setAdvice(new AdviceImpl());

        for (int i = 0; i < authnStatementCount; i++) {
            assertion.getAuthnStatements().add(new AuthnStatementImpl());
        }
        for (int i = 0; i < authzDecisionStatementCount; i++) {
            assertion.getAuthzDecisionStatements().add(new AuthzDecisionStatementImpl());
        }
        for (int i = 0; i < attributeStatementCount; i++) {
            assertion.getAttributeStatement().add(new AttributeStatementImpl());
        }
        assertEquals(expectedChildElementsDOM, assertion);
    }
}