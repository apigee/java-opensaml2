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
import org.opensaml.saml2.core.AuthnContextDeclRef;

/**
 * Test case for creating, marshalling, and unmarshalling {@link org.opensaml.saml2.core.impl.AuthnContextDeclRefImpl}.
 */
public class AuthnContextDeclRefTest extends SAMLObjectBaseTestCase {

    /** Expected Declaration Reference value */
    protected String expectedDeclRef;

    /** Constructor */
    public AuthnContextDeclRefTest() {
        singleElementFile = "/data/org/opensaml/saml2/core/impl/AuthnContextDeclRef.xml";
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        expectedDeclRef = "declaration reference";
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        AuthnContextDeclRef authnContextDeclRef = (AuthnContextDeclRef) unmarshallElement(singleElementFile);

        String declRef = authnContextDeclRef.getAuthnContextDeclRef();
        assertEquals("Declartion Reference was " + declRef + ", expected " + expectedDeclRef, expectedDeclRef, declRef);
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
        QName qname = new QName(SAMLConstants.SAML20_NS, AuthnContextDeclRef.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        AuthnContextDeclRef authnContextDeclRef = (AuthnContextDeclRef) buildXMLObject(qname);

        authnContextDeclRef.setAuthnContextDeclRef(expectedDeclRef);
        assertEquals(expectedDOM, authnContextDeclRef);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        // do nothing
    }
}