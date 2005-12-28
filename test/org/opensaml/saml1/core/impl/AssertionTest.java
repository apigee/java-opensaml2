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

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.util.xml.ParserPoolManager;
import org.opensaml.common.util.xml.XMLHelper;
import org.opensaml.saml1.core.Assertion;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Test for {@link org.opensaml.saml1.core.impl.Assertion}
 */
public class AssertionTest extends SAMLObjectBaseTestCase {

    private final int minorVersion;
    private final String issuer;
    private final GregorianCalendar issueInstant;
    private String fullElementsFile;
    private Document expectedFullDOM;
    
    /**
     * Constructor
     *
     */
    public AssertionTest() {
        super();
        minorVersion = 1;
        issuer = "issuer";
        //
        // IssueInstant="1970-01-02T01:01:02.100Z"
        //
        issueInstant = new GregorianCalendar(1970, 0, 2, 1, 1, 2);
        issueInstant.set(Calendar.MILLISECOND, 100);
        
        singleElementFile = "/data/org/opensaml/saml1/singleAssertion.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/singleAssertionAttributes.xml";
        fullElementsFile = "/data/org/opensaml/saml1/AssertionWithChildren.xml";
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

        Assertion assertion = (Assertion) unmarshallElement(singleElementFile);
        
        assertNull("Issuer attribute", assertion.getIssuer());
        assertNull("IssueInstant attribute", assertion.getIssueInstant());
        
        assertNull("Conditions element", assertion.getConditions());
        assertNull("Advice element", assertion.getAdvice());
        
        assertEquals("Statement element count", 0, assertion.getStatements().size());
        assertEquals("AttributeStatements element count", 0, assertion.getAttributeStatements().size());
        assertEquals("SubjectStatements element count", 0, assertion.getSubjectStatements().size());
        assertEquals("AuthenticationStatements element count", 0, assertion.getAuthenticationStatements().size());
        assertEquals("AuthorizationDecisionStatements element count", 0, assertion.getAuthorizationDecisionStatements().size());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesUnmarshall() {
        Assertion assertion = (Assertion) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertEquals("Issuer attribute", issuer, assertion.getIssuer());
        assertEquals("IssueInstant attribute", XMLHelper.calendarToString(issueInstant), XMLHelper.calendarToString(assertion.getIssueInstant()));
        assertEquals("Issuer minorVersion", minorVersion, assertion.getMinorVersion());
        
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

    public void testFullElementsUnmarshall() {
        Assertion assertion = (Assertion) unmarshallElement(fullElementsFile);
        
        assertNull("Issuer attribut", assertion.getIssuer());
        assertNull("IssueInstant attribute", assertion.getIssueInstant());
        
        assertNotNull("Conditions element null", assertion.getConditions());
        assertNotNull("Advice element null", assertion.getAdvice());
        
        assertEquals("Statement element count", 1, assertion.getStatements().size());
        assertEquals("AttributeStatements element count", 3, assertion.getAttributeStatements().size());
        assertEquals("SubjectStatements element count", 2, assertion.getSubjectStatements().size());
        assertEquals("AuthenticationStatements element count", 2, assertion.getAuthenticationStatements().size());
        assertEquals("AuthorizationDecisionStatements element count", 3, assertion.getAuthorizationDecisionStatements().size());
    }
    
    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    @Override
    public void testSingleElementMarshall() {
        Assertion assertion = new AssertionImpl();

        assertEquals(expectedDOM, assertion);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesMarshall() {
        Assertion assertion = new AssertionImpl();
        
        assertion.setIssueInstant(issueInstant);
        assertion.setIssuer(issuer);
        assertion.setMinorVersion(minorVersion);

        assertEquals(expectedOptionalAttributesDOM, assertion);
    }

    /**
     * Test an XML file with Children
     */
    
    public void testFullElementsMarshall() {
        Assertion assertion = new AssertionImpl();

        try {
            assertion.setConditions(new ConditionsImpl());
            assertion.setAdvice(new AdviceImpl());
            //assertion.addStatement(new StatementImpl());
            //assertion.addSubjectStatement(new SubjectStatementImpl());
            //assertion.addAuthenticationStatement(new AuthenticationStatementImpl());
            //assertion.addAuthorizationDecisionStatement(new AuthorizationDecisionStatementImpl());
            //assertion.addAttributeStatement(new AttributeStatementImpl());
            //assertion.addSubjectStatement(new SubjectStatementImpl());
            //assertion.addAuthenticationStatement(new AuthenticationStatementImpl());
            //assertion.addAuthorizationDecisionStatement(new AuthorizationDecisionStatementImpl());
            //assertion.addAttributeStatement(new AttributeStatementImpl());
            //assertion.addAuthorizationDecisionStatement(new AuthorizationDecisionStatementImpl());
            //assertion.addAttributeStatement(new AttributeStatementImpl());
        } catch (IllegalAddException e) {
            fail("threw IllegalAddException");
        }

        assertEquals(expectedFullDOM, assertion);
    }   
}
