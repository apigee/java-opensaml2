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

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.AuthzDecisionStatement;
import org.opensaml.saml2.core.DecisionType;

/**
 * Test case for creating, marshalling, and unmarshalling
 * {@link org.opensaml.saml2.core.impl.AuthzDecisionStatementImpl}.
 */
public class AuthzDecisionStatementTest extends SAMLObjectBaseTestCase {

    /** Expected Resource value */
    protected String expectedResource;

    /** Expected Decision value */
    protected DecisionType expectedDecision;

    /** Count of Action subelements */
    protected int expectedActionCount = 3;

    /** Constructor */
    public AuthzDecisionStatementTest() {
        singleElementFile = "/data/org/opensaml/saml2/core/impl/AuthzDecisionStatement.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/core/impl/AuthzDecisionStatementOptionalAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml2/core/impl/AuthzDecisionStatementChildElements.xml";
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        expectedResource = "resource name";
        expectedDecision = DecisionType.DENY;
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        AuthzDecisionStatement authzDecisionStatement = (AuthzDecisionStatement) unmarshallElement(singleElementFile);

        String resource = authzDecisionStatement.getResource();
        assertEquals("Resource not as expected", expectedResource, resource);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        AuthzDecisionStatement authzDecisionStatement = (AuthzDecisionStatement) unmarshallElement(singleElementOptionalAttributesFile);

        String resource = authzDecisionStatement.getResource();
        assertEquals("Resource not as expected", expectedResource, resource);

        DecisionType decision = authzDecisionStatement.getDecision();
        assertEquals("Decision not as expected", expectedDecision.toString(), decision.toString());
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20_NS, AuthzDecisionStatement.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        AuthzDecisionStatement authzDecisionStatement = (AuthzDecisionStatement) buildSAMLObject(qname);

        authzDecisionStatement.setResource(expectedResource);
        assertEquals(expectedDOM, authzDecisionStatement);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML20_NS, AuthzDecisionStatement.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        AuthzDecisionStatement authzDecisionStatement = (AuthzDecisionStatement) buildSAMLObject(qname);

        authzDecisionStatement.setResource(expectedResource);
        authzDecisionStatement.setDecision(expectedDecision);

        assertEquals(expectedOptionalAttributesDOM, authzDecisionStatement);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    public void testChildElementsUnmarshall() {
        AuthzDecisionStatement authzDecisionStatement = (AuthzDecisionStatement) unmarshallElement(childElementsFile);
        assertEquals("Action Count", expectedActionCount, authzDecisionStatement.getActions().size());
        assertNotNull("Evidence element not present", authzDecisionStatement.getEvidence());
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML20_NS, AuthzDecisionStatement.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        AuthzDecisionStatement authzDecisionStatement = (AuthzDecisionStatement) buildSAMLObject(qname);

        for (int i = 0; i < expectedActionCount; i++) {
            authzDecisionStatement.getActions().add(new ActionImpl());
        }
        authzDecisionStatement.setEvidence(new EvidenceImpl());
        assertEquals(expectedChildElementsDOM, authzDecisionStatement);
    }
}