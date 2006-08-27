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

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Advice;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.AttributeStatement;
import org.opensaml.saml1.core.AuthenticationStatement;
import org.opensaml.saml1.core.AuthorizationDecisionStatement;
import org.opensaml.saml1.core.Conditions;
import org.opensaml.saml1.core.Statement;

/**
 * Test for {@link org.opensaml.saml1.core.impl.Assertion}
 */
public class AssertionTest extends SAMLObjectBaseTestCase {

    /** name used to generate objects */
    private final QName qname;

    private final int expectedMinorVersion;

    private final String expectedIssuer;

    private final DateTime expectedIssueInstant;

    private final String expectedID;
    /**
     * Constructor
     */
    public AssertionTest() {
        super();
        expectedID = "ident";
        expectedMinorVersion = 1;
        expectedIssuer = "issuer";
        //
        // IssueInstant="1970-01-02T01:01:02.100Z"
        //
        expectedIssueInstant = new DateTime(1970, 1, 2, 1, 1, 2, 100, ISOChronology.getInstanceUTC());

        singleElementFile = "/data/org/opensaml/saml1/impl/singleAssertion.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/impl/singleAssertionAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml1/impl/AssertionWithChildren.xml";
        qname = new QName(SAMLConstants.SAML1_NS, Assertion.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
    }

    /** {@inheritDoc} */

    public void testSingleElementUnmarshall() {

        Assertion assertion = (Assertion) unmarshallElement(singleElementFile);

        assertNull("Issuer attribute", assertion.getIssuer());
        assertNull("IssueInstant attribute", assertion.getIssueInstant());
        assertNull("ID attribute", assertion.getID());

        assertNull("Conditions element", assertion.getConditions());
        assertNull("Advice element", assertion.getAdvice());

        assertEquals("Statement element count", 0, assertion.getStatements().size());
        assertEquals("AttributeStatements element count", 0, assertion.getAttributeStatements().size());
        assertEquals("SubjectStatements element count", 0, assertion.getSubjectStatements().size());
        assertEquals("AuthenticationStatements element count", 0, assertion.getAuthenticationStatements().size());
        assertEquals("AuthorizationDecisionStatements element count", 0, assertion.getAuthorizationDecisionStatements().size());
    }

    /** {@inheritDoc} */

    public void testSingleElementOptionalAttributesUnmarshall() {
        Assertion assertion = (Assertion) unmarshallElement(singleElementOptionalAttributesFile);

        assertEquals("Issuer attribute", expectedIssuer, assertion.getIssuer());
        assertEquals("IssueInstant attribute", expectedIssueInstant, assertion.getIssueInstant());
        assertEquals("ID attribute", expectedID, assertion.getID());
        assertEquals("Issuer expectedMinorVersion", expectedMinorVersion, assertion.getMinorVersion());

        assertNull("Conditions element", assertion.getConditions());
        assertNull("Advice element", assertion.getAdvice());

        assertEquals("Statement element count", 0, assertion.getStatements().size());
        assertEquals("AttributeStatements element count", 0, assertion.getAttributeStatements().size());
        assertEquals("SubjectStatements element count", 0, assertion.getSubjectStatements().size());
        assertEquals("AuthenticationStatements element count", 0, assertion.getAuthenticationStatements().size());
        assertEquals("AuthorizationDecisionStatements element count", 0, assertion.getAuthorizationDecisionStatements().size());
    }

    /**
     * Test an XML file with children
     */

    public void testChildElementsUnmarshall() {
        Assertion assertion = (Assertion) unmarshallElement(childElementsFile);

        assertNull("Issuer attribute", assertion.getIssuer());
        assertNull("ID attribute", assertion.getID());
        assertNull("IssueInstant attribute", assertion.getIssueInstant());

        assertNotNull("Conditions element null", assertion.getConditions());
        assertNotNull("Advice element null", assertion.getAdvice());

        assertNotNull("No Authentication Statements", assertion.getAuthenticationStatements());
        assertEquals("AuthenticationStatements element count", 2, assertion.getAuthenticationStatements().size());

        assertNotNull("No Attribute Statements", assertion.getAttributeStatements());
        assertEquals("AttributeStatements element count", 3, assertion.getAttributeStatements().size());

        assertNotNull("No AuthorizationDecisionStatements ", assertion.getAuthorizationDecisionStatements());
        assertEquals("AuthorizationDecisionStatements element count", 3, assertion.getAuthorizationDecisionStatements()
                .size());
    }

    /** {@inheritDoc} */

    public void testSingleElementMarshall() {
        assertEquals(expectedDOM, buildXMLObject(qname));
    }

    /** {@inheritDoc} */

    public void testSingleElementOptionalAttributesMarshall() {
        Assertion assertion = (Assertion) buildXMLObject(qname);

        assertion.setIssueInstant(expectedIssueInstant);
        assertion.setID(expectedID);
        assertion.setIssuer(expectedIssuer);
        assertEquals(expectedOptionalAttributesDOM, assertion);
    }

    /**
     * Test an XML file with Children
     */

    public void testChildElementsMarshall() {
        Assertion assertion = (Assertion) buildXMLObject(qname);

        assertion.setConditions((Conditions) buildXMLObject(new QName(SAMLConstants.SAML1_NS, Conditions.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML1_PREFIX)));
        assertion.setAdvice((Advice) buildXMLObject(new QName(SAMLConstants.SAML1_NS, Advice.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML1_PREFIX)));

        QName authenticationQname = new QName(SAMLConstants.SAML1_NS, AuthenticationStatement.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
        QName authorizationQname = new QName(SAMLConstants.SAML1_NS, AuthorizationDecisionStatement.LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
        QName attributeQname = new QName(SAMLConstants.SAML1_NS, AttributeStatement.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
        
        assertion.getStatements().add((Statement) buildXMLObject(authenticationQname));
        assertion.getStatements().add((Statement) buildXMLObject(authorizationQname));
        assertion.getStatements().add((Statement) buildXMLObject(attributeQname));
        assertion.getStatements().add((Statement) buildXMLObject(authenticationQname));
        assertion.getStatements().add((Statement) buildXMLObject(authorizationQname));
        assertion.getStatements().add((Statement) buildXMLObject(attributeQname));
        assertion.getStatements().add((Statement) buildXMLObject(authorizationQname));
        assertion.getStatements().add((Statement) buildXMLObject(attributeQname));

        assertEquals(expectedChildElementsDOM, assertion);
    }
}
