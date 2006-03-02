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

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.common.SAMLObjectValidatorBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.AuthnContext;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.xml.validation.ValidationException;

public class AuthnStatementSchemaTest extends SAMLObjectValidatorBaseTestCase {

    private QName qname;
    private QName authnQName;
    private AuthnContext authnContext;
    private AuthnStatementSchemaValidator assertionValidator;
    
    /**Constructor*/
    public AuthnStatementSchemaTest() {
        qname = new QName(SAMLConstants.SAML20_NS, AuthnStatement.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        authnQName = new QName(SAMLConstants.SAML20_NS, AuthnContext.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        authnContext = (AuthnContext) buildXMLObject(authnQName);
        assertionValidator = new AuthnStatementSchemaValidator();

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
        AuthnStatement assertion = (AuthnStatement) buildXMLObject(qname);

        assertion.setAuthnInstant(new DateTime(1984, 8, 26, 10, 01, 30, 43, ISOChronology.getInstanceUTC()));
        assertion.setAuthnContext(authnContext);
        
        assertionValidator.validate(assertion);
    }

    /**
     * Tests absent AuthnInstant failure.
     * 
     * @throws ValidationException
     */
    public void testIssuerFailure() throws ValidationException {
        AuthnStatement assertion = (AuthnStatement) buildXMLObject(qname);

        assertion.setAuthnContext(authnContext);
        
        try {
            assertionValidator.validate(assertion);
            fail("AuthnInstant missing, should raise a Validation Exception");
        } catch (ValidationException success) {
        }
    }

    /**
     * Tests absent AuthnContext failure.
     * 
     * @throws ValidationException
     */
    public void testIDFailure() throws ValidationException {
        AuthnStatement assertion = (AuthnStatement) buildXMLObject(qname);

        assertion.setAuthnInstant(new DateTime(1984, 8, 26, 10, 01, 30, 43, ISOChronology.getInstanceUTC()));
        
        try {
            assertionValidator.validate(assertion);
            fail("AuthnContext missing, should raise a Validation Exception");
        } catch (ValidationException success) {
        }
    }
}