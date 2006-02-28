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
import org.opensaml.saml1.core.AuthenticationQuery;

/**
 * Test class for org.opensaml.saml1.core.AuthenticationQuery
 */
public class AuthenticationQueryTest extends SAMLObjectBaseTestCase {

    private final String expectedAuthenticationMethod;

    /**
     * Constructor
     */
    public AuthenticationQueryTest() {
        singleElementFile = "/data/org/opensaml/saml1/singleAuthenticationQuery.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/singleAuthenticationQueryAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml1/AuthenticationQueryWithChildren.xml";

        expectedAuthenticationMethod = "Trust Me";
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {

        AuthenticationQuery authenticationQuery;
        
        authenticationQuery = (AuthenticationQuery) unmarshallElement(singleElementFile);

        assertNull("AuthenticationQuery attribute present", authenticationQuery.getAuthenticationMethod());;
        assertNull("Subject element present", authenticationQuery.getSubject());
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        AuthenticationQuery authenticationQuery;
        
        authenticationQuery = (AuthenticationQuery) unmarshallElement(singleElementOptionalAttributesFile);

        assertEquals("AuthenticationQuery attribute", expectedAuthenticationMethod, authenticationQuery.getAuthenticationMethod());;
        assertNull("Subject element present", authenticationQuery.getSubject());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    public void testChildElementsUnmarshall() {
        AuthenticationQuery authenticationQuery;
        
        authenticationQuery = (AuthenticationQuery) unmarshallElement(childElementsFile);

        assertNotNull("No Subject element found", authenticationQuery.getSubject());
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML1P_NS, AuthenticationQuery.LOCAL_NAME);
        
        assertEquals(expectedDOM, buildXMLObject(qname));
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML1P_NS, AuthenticationQuery.LOCAL_NAME);
        AuthenticationQuery authenticationQuery = (AuthenticationQuery) buildXMLObject(qname);

        authenticationQuery.setAuthenticationMethod(expectedAuthenticationMethod);
        assertEquals(expectedOptionalAttributesDOM, authenticationQuery);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML1P_NS, AuthenticationQuery.LOCAL_NAME);
        AuthenticationQuery authenticationQuery = (AuthenticationQuery) buildXMLObject(qname);

        authenticationQuery.setSubject(new SubjectImpl());
        assertEquals(expectedChildElementsDOM, authenticationQuery);

    }

}
