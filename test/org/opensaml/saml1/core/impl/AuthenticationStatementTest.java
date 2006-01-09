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

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.ParserPoolManager;
import org.opensaml.saml1.core.AuthenticationStatement;
import org.opensaml.saml1.core.AuthorityBinding;
import org.opensaml.xml.IllegalAddException;
import org.opensaml.xml.util.DatatypeHelper;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 *
 */
public class AuthenticationStatementTest extends SAMLObjectBaseTestCase {

    /** Expected value of AuthenticationMethod */
    private String expectedAuthenticationMethod;
    
    /** Expected value of AuthenticationInstant */
    private GregorianCalendar expectedAuthenticationInstant;
    /** Expected value of AuthenticationInstant */
    private String expectedAuthenticationInstantAsString;

    /** File with the AuthenticationStatementMethod with children */ 
    private String fullElementsFile;
    
    /** The DOM to hold the AuthenticationStatementMethod with children */ 
    private Document expectedFullDOM;
    
    /**
     * Constructor
     */
    public AuthenticationStatementTest() {
        super();
        expectedAuthenticationMethod = "trustme";
        //
        // AuthenticationInstant="1970-01-02T01:01:02.123Z"
        //
        expectedAuthenticationInstant = new GregorianCalendar(1970, 0, 2, 1, 1, 2);
        expectedAuthenticationInstant.set(Calendar.MILLISECOND, 123);
        expectedAuthenticationInstantAsString = DatatypeHelper.calendarToString(expectedAuthenticationInstant, 0);
        
        singleElementFile = "/data/org/opensaml/saml1/singleAuthenticationStatement.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/singleAuthenticationStatementAttributes.xml";
        fullElementsFile = "/data/org/opensaml/saml1/AuthenticationStatementWithChildren.xml";
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
        AuthenticationStatement authenticationStatement = (AuthenticationStatement) unmarshallElement(singleElementFile);
        
        assertNull("AuthenticationMethod attribute present", authenticationStatement.getAuthenticationMethod());
        assertNull("AuthenticationInstant attribute present", authenticationStatement.getAuthenticationInstant());
        
        assertNull("<Subject> element present", authenticationStatement.getSubject());
        assertNull("<SubjectLocailty> element present", authenticationStatement.getSubjectLocality());
        assertNull("Non zero count of <AuthorityBinding> elements", authenticationStatement.getAuthorityBindings());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesUnmarshall() {
        AuthenticationStatement authenticationStatement = (AuthenticationStatement) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertEquals("AuthenticationMethod", expectedAuthenticationMethod, authenticationStatement.getAuthenticationMethod());
        String date = DatatypeHelper.calendarToString(authenticationStatement.getAuthenticationInstant(), 0);
        assertEquals("AuthenticationInstant", expectedAuthenticationInstantAsString, date);
    }

    /**
     * Test an XML file with children
     */

    public void testFullElementsUnmarshall() {
        AuthenticationStatement authenticationStatement = (AuthenticationStatement) unmarshallElement(fullElementsFile);
        
        assertNotNull("<Subject> element not present", authenticationStatement.getSubject());
        
        assertNotNull("<SubjectLocality> element not present", authenticationStatement.getSubjectLocality());
        assertNotNull("<AuthorityBinding> elements not present", authenticationStatement.getAuthorityBindings());
        assertEquals("count of <AuthorityBinding> elements", 2, authenticationStatement.getAuthorityBindings().size());
        
        AuthorityBinding authorityBinding = authenticationStatement.getAuthorityBindings().get(0);
        authenticationStatement.removeAuthorityBinding(authorityBinding);
        assertEquals("count of <AuthorityBinding> elements", 1, authenticationStatement.getAuthorityBindings().size());
        // TODO
        /*
        authenticationStatement.removeAllAuthorityBindings();
        assertNull("<AuthorityBinding> not all rmeoved", authenticationStatement.getAuthorityBindings());
        */
    }
    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    @Override
    public void testSingleElementMarshall() {
        assertEquals(expectedDOM, new AuthenticationStatementImpl());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesMarshall() {
        AuthenticationStatement authenticationStatement = new AuthenticationStatementImpl();
        
        authenticationStatement.setAuthenticationInstant(expectedAuthenticationInstant);
        authenticationStatement.setAuthenticationMethod(expectedAuthenticationMethod);
        assertEquals(expectedOptionalAttributesDOM, authenticationStatement);
    }
    
    /**
     * Test an XML file with Children
     */
    
    public void testFullElementsMarshall() {

        AuthenticationStatement authenticationStatement = new AuthenticationStatementImpl();
        
        try {
            authenticationStatement.setSubject(new SubjectImpl());
            
            authenticationStatement.setSubjectLocality(new SubjectLocalityImpl());
            authenticationStatement.addAuthorityBinding(new AuthorityBindingImpl());
            authenticationStatement.addAuthorityBinding(new AuthorityBindingImpl());
        } catch (IllegalAddException e) {
            fail("threw IllegalAddException");
        }
        assertEquals(expectedFullDOM, authenticationStatement);
    }
}

    