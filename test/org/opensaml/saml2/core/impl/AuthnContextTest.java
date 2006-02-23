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
import org.opensaml.saml2.core.AuthnContext;

/**
 * Test case for creating, marshalling, and unmarshalling {@link org.opensaml.saml2.core.impl.AuthnContextImpl}.
 */
public class AuthnContextTest extends SAMLObjectBaseTestCase {

    /** Count of AuthenticatingAuthority subelements */
    protected int expectedAuthenticatingAuthorityCount = 2;

    /** Constructor */
    public AuthnContextTest() {
        singleElementFile = "/data/org/opensaml/saml2/core/impl/AuthnContext.xml";
        childElementsFile = "/data/org/opensaml/saml2/core/impl/AuthnContextChildElements.xml";
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        AuthnContext authnContext = (AuthnContext) unmarshallElement(singleElementFile);

        assertNotNull(authnContext);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        // do nothing
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20_NS, AuthnContext.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        AuthnContext authnContext = (AuthnContext) buildSAMLObject(qname);

        assertEquals(expectedDOM, authnContext);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        // do nothing
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    public void testChildElementsUnmarshall() {
        AuthnContext authnContext = (AuthnContext) unmarshallElement(childElementsFile);

        assertNotNull("AuthnContextClassRef element not present", authnContext.getAuthnContextClassRef());
        assertNotNull("AuthnContextDecl element not present", authnContext.getAuthContextDecl());
        assertNotNull("AuthnContextDeclRef element not present", authnContext.getAuthnContextDeclRef());
        assertEquals("AuthenticatingAuthorityCount Count", expectedAuthenticatingAuthorityCount, authnContext
                .getAuthenticatingAuthorities().size());
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML20_NS, AuthnContext.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        AuthnContext authnContext = (AuthnContext) buildSAMLObject(qname);

        authnContext.setAuthnContextClassRef(new AuthnContextClassRefImpl());
        authnContext.setAuthnContextDecl(new AuthnContextDeclImpl());
        authnContext.setAuthnContextDeclRef(new AuthnContextDeclRefImpl());
        for (int i = 0; i < expectedAuthenticatingAuthorityCount; i++) {
            authnContext.getAuthenticatingAuthorities().add(new AuthenticatingAuthorityImpl());
        }

        assertEquals(expectedChildElementsDOM, authnContext);
    }
}