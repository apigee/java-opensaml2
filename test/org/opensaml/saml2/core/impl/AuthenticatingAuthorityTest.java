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
import org.opensaml.saml2.core.AuthenticatingAuthority;

/**
 * Test case for creating, marshalling, and unmarshalling
 * {@link org.opensaml.saml2.core.impl.AuthenticatingAuthorityImpl}.
 */
public class AuthenticatingAuthorityTest extends SAMLObjectBaseTestCase {

    /** Expected URI value */
    protected String expectedURI;

    /** Constructor */
    public AuthenticatingAuthorityTest() {
        singleElementFile = "/data/org/opensaml/saml2/core/impl/AuthenticatingAuthority.xml";
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        expectedURI = "authenticating URI";
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        AuthenticatingAuthority authenticatingAuthority = (AuthenticatingAuthority) unmarshallElement(singleElementFile);

        String assertionURI = authenticatingAuthority.getURI();
        assertEquals("URI was " + assertionURI + ", expected " + expectedURI, expectedURI, assertionURI);
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
        QName qname = new QName(SAMLConstants.SAML20_NS, AuthenticatingAuthority.LOCAL_NAME,
                SAMLConstants.SAML20_PREFIX);
        AuthenticatingAuthority authenticatingAuthority = (AuthenticatingAuthority) buildSAMLObject(qname);

        authenticatingAuthority.setURI(expectedURI);
        assertEquals(expectedDOM, authenticatingAuthority);

    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        // do nothing
    }
}