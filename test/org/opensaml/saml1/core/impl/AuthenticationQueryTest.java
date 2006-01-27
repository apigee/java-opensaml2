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
import org.opensaml.common.xml.ParserPoolManager;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.AuthenticationQuery;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Test class for org.opensaml.saml1.core.AuthenticationQuery
 */
public class AuthenticationQueryTest extends SAMLObjectBaseTestCase {

    /** A file with a AuthenticationQuery with kids */

    private final String fullElementsFile;

    /** The expected result of a marshalled multiple element */

    private Document expectedFullDOM;

    private final String expectedAuthenticationMethod;

    /**
     * Constructor
     */
    public AuthenticationQueryTest() {
        singleElementFile = "/data/org/opensaml/saml1/singleAuthenticationQuery.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/singleAuthenticationQueryAttributes.xml";
        fullElementsFile = "/data/org/opensaml/saml1/AuthenticationQueryWithChildren.xml";

        expectedAuthenticationMethod = "Trust Me";
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        ParserPoolManager ppMgr = ParserPoolManager.getInstance();

        expectedFullDOM = ppMgr.parse(new InputSource(SAMLObjectBaseTestCase.class
                .getResourceAsStream(fullElementsFile)));
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    @Override
    public void testSingleElementUnmarshall() {

        AuthenticationQuery authenticationQuery;
        
        authenticationQuery = (AuthenticationQuery) unmarshallElement(singleElementFile);

        assertNull("AuthenticationQuery attribute present", authenticationQuery.getAuthenticationMethod());;
        assertNull("Subject element present", authenticationQuery.getSubject());
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesUnmarshall() {
        AuthenticationQuery authenticationQuery;
        
        authenticationQuery = (AuthenticationQuery) unmarshallElement(singleElementOptionalAttributesFile);

        assertEquals("AuthenticationQuery attribute", expectedAuthenticationMethod, authenticationQuery.getAuthenticationMethod());;
        assertNull("Subject element present", authenticationQuery.getSubject());
    }

    /**
     * Test an Response file with children
     */

    public void testFullElementsUnmarshall() {
        AuthenticationQuery authenticationQuery;
        
        authenticationQuery = (AuthenticationQuery) unmarshallElement(fullElementsFile);

        assertNotNull("No Subject element found", authenticationQuery.getSubject());
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    @Override
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML1P_NS, AuthenticationQuery.LOCAL_NAME);
        
        assertEquals(expectedDOM, buildSAMLObject(qname));
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML1P_NS, AuthenticationQuery.LOCAL_NAME);
        AuthenticationQuery authenticationQuery = (AuthenticationQuery) buildSAMLObject(qname);

        authenticationQuery.setAuthenticationMethod(expectedAuthenticationMethod);
        assertEquals(expectedOptionalAttributesDOM, authenticationQuery);
    }

    /**
     * Test Marshalling up a file with children
     * 
     */

    public void testFullElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML1P_NS, AuthenticationQuery.LOCAL_NAME);
        AuthenticationQuery authenticationQuery = (AuthenticationQuery) buildSAMLObject(qname);

        authenticationQuery.setSubject(new SubjectImpl());
        assertEquals(expectedFullDOM, authenticationQuery);

    }

}
