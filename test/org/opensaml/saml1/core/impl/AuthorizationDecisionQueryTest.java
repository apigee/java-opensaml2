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

import java.util.List;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.ParserPoolManager;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Action;
import org.opensaml.saml1.core.AuthorizationDecisionQuery;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Test class for org.opensaml.saml1.core.AttributeQuery
 */
public class AuthorizationDecisionQueryTest extends SAMLObjectBaseTestCase {

    /** A file with a AuthenticationQuery with kids */

    private final String fullElementsFile;

    /** The expected result of a marshalled multiple element */

    private Document expectedFullDOM;

    private final String expectedResource;

    /**
     * Constructor
     */
    public AuthorizationDecisionQueryTest() {
        singleElementFile = "/data/org/opensaml/saml1/singleAuthorizationDecisionQuery.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/singleAuthorizationDecisionQueryAttributes.xml";
        fullElementsFile = "/data/org/opensaml/saml1/AuthorizationDecisionQueryWithChildren.xml";

        expectedResource = "resource";
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
    public void testSingleElementUnmarshall() {

        AuthorizationDecisionQuery authorizationDecisionQuery;
        authorizationDecisionQuery = (AuthorizationDecisionQuery) unmarshallElement(singleElementFile);

        assertNull("Resource attribute present", authorizationDecisionQuery.getResource());
        assertNull("Subject element present", authorizationDecisionQuery.getSubject());
        assertEquals("Count of AttributeDesignator elements", 0, authorizationDecisionQuery.getActions().size());
        assertNull("Evidence element present", authorizationDecisionQuery.getEvidence());
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        AuthorizationDecisionQuery authorizationDecisionQuery;
        authorizationDecisionQuery = (AuthorizationDecisionQuery) unmarshallElement(singleElementOptionalAttributesFile);

        assertEquals("Resource attribute", expectedResource, authorizationDecisionQuery.getResource());
        assertNull("Subject element present", authorizationDecisionQuery.getSubject());
        assertEquals("Count of AttributeDesignator elements", 0, authorizationDecisionQuery.getActions().size());
        assertNull("Evidence element present", authorizationDecisionQuery.getEvidence());
    }

    /**
     * Test an Response file with children
     */
    public void testFullElementsUnmarshall() {
        AuthorizationDecisionQuery authorizationDecisionQuery;
        authorizationDecisionQuery = (AuthorizationDecisionQuery) unmarshallElement(fullElementsFile);

        assertNotNull("Subject element present", authorizationDecisionQuery.getSubject());
        assertEquals("Count of Action elements", 3, authorizationDecisionQuery.getActions().size());
        assertNotNull("Evidence element present", authorizationDecisionQuery.getEvidence());
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML1P_NS, AuthorizationDecisionQuery.LOCAL_NAME);
        
        assertEquals(expectedDOM, buildXMLObject(qname));
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML1P_NS, AuthorizationDecisionQuery.LOCAL_NAME);
        
        AuthorizationDecisionQuery authorizationDecisionQuery;
        authorizationDecisionQuery = (AuthorizationDecisionQuery) buildXMLObject(qname);

        authorizationDecisionQuery.setResource(expectedResource);
        assertEquals(expectedOptionalAttributesDOM, authorizationDecisionQuery);
    }

    /**
     * Test Marshalling up a file with children
     * 
     */
    public void testFullElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML1P_NS, AuthorizationDecisionQuery.LOCAL_NAME);
        AuthorizationDecisionQuery authorizationDecisionQuery;
        authorizationDecisionQuery = (AuthorizationDecisionQuery) buildXMLObject(qname);

        authorizationDecisionQuery.setSubject(new SubjectImpl());
        List <Action> list = authorizationDecisionQuery.getActions();
        list.add(new ActionImpl());
        list.add(new ActionImpl());
        list.add(new ActionImpl());
        authorizationDecisionQuery.setEvidence(new EvidenceImpl());
        assertEquals(expectedFullDOM, authorizationDecisionQuery);

    }

}
