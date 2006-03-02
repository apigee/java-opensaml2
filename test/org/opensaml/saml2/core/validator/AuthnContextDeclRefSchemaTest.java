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

package org.opensaml.saml2.core.validator;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObjectValidatorBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.AuthnContextDeclRef;
import org.opensaml.xml.validation.ValidationException;

public class AuthnContextDeclRefSchemaTest extends SAMLObjectValidatorBaseTestCase {

    private QName qname;

    private AuthnContextDeclRefSchemaValidator authnContextDeclRefValidator;

    /** Constructor */
    public AuthnContextDeclRefSchemaTest() {
        qname = new QName(SAMLConstants.SAML20_NS, AuthnContextDeclRef.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        authnContextDeclRefValidator = new AuthnContextDeclRefSchemaValidator();
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Tests the correct case.
     * 
     * @throws ValidationException
     */
    public void testProper() throws ValidationException {
        AuthnContextDeclRef authnContextDeclRef = (AuthnContextDeclRef) buildXMLObject(qname);

        authnContextDeclRef.setAuthnContextDeclRef("ref");

        authnContextDeclRefValidator.validate(authnContextDeclRef);
    }

    /**
     * Tests absent Declaration Reference failure.
     * 
     * @throws ValidationException
     */
    public void testURIFailure() throws ValidationException {
        AuthnContextDeclRef authnContextDeclRef = (AuthnContextDeclRef) buildXMLObject(qname);

        try {
            authnContextDeclRefValidator.validate(authnContextDeclRef);
            fail("DeclRef missing, should raise a Validation Exception");
        } catch (ValidationException success) {
        }
    }
}