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

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.saml1.core.AuthenticationStatement;
import org.opensaml.saml1.core.AuthorityBinding;

/**
 * 
 */
public class AuthenticationStatementTest extends SAMLObjectBaseTestCase {

    /** Expected value of AuthenticationMethod */
    private String expectedAuthenticationMethod;

    /** Expected value of AuthenticationInstant */
    private DateTime expectedAuthenticationInstant;

    /**
     * Constructor
     */
    public AuthenticationStatementTest() {
        super();
        expectedAuthenticationMethod = "trustme";
        //
        // AuthenticationInstant="1970-01-02T01:01:02.123Z"
        //
        expectedAuthenticationInstant = new DateTime(1970, 1, 2, 1, 1, 2, 123, ISOChronology.getInstanceUTC());

        singleElementFile = "/data/org/opensaml/saml1/singleAuthenticationStatement.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/singleAuthenticationStatementAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml1/AuthenticationStatementWithChildren.xml";
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
        assertEquals("Non zero count of <AuthorityBinding> elements", 0, authenticationStatement.getAuthorityBindings().size());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesUnmarshall() {
        AuthenticationStatement authenticationStatement = (AuthenticationStatement) unmarshallElement(singleElementOptionalAttributesFile);

        assertEquals("AuthenticationMethod", expectedAuthenticationMethod, authenticationStatement
                .getAuthenticationMethod());
        assertEquals("AuthenticationInstant", expectedAuthenticationInstant, authenticationStatement.getAuthenticationInstant());
    }

    /**
     * Test an XML file with children
     */

    public void testChildElementsUnmarshall() {
        AuthenticationStatement authenticationStatement = (AuthenticationStatement) unmarshallElement(childElementsFile);

        assertNotNull("<Subject> element not present", authenticationStatement.getSubject());

        assertNotNull("<SubjectLocality> element not present", authenticationStatement.getSubjectLocality());
        assertNotNull("<AuthorityBinding> elements not present", authenticationStatement.getAuthorityBindings());
        assertEquals("count of <AuthorityBinding> elements", 2, authenticationStatement.getAuthorityBindings().size());

        AuthorityBinding authorityBinding = authenticationStatement.getAuthorityBindings().get(0);
        authenticationStatement.getAuthorityBindings().remove(authorityBinding);
        assertEquals("count of <AuthorityBinding> elements", 1, authenticationStatement.getAuthorityBindings().size());
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

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    @Override
    public void testChildElementsMarshall() {

        AuthenticationStatement authenticationStatement = new AuthenticationStatementImpl();

        authenticationStatement.setSubject(new SubjectImpl());

        authenticationStatement.setSubjectLocality(new SubjectLocalityImpl());
        authenticationStatement.getAuthorityBindings().add(new AuthorityBindingImpl());
        authenticationStatement.getAuthorityBindings().add(new AuthorityBindingImpl());

        assertEquals(expectedChildElementsDOM, authenticationStatement);
    }
}
