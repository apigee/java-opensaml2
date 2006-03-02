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
import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.SAMLObjectValidatorBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Subject;
import org.opensaml.xml.validation.ValidationException;

public class AssertionSchemaTest extends SAMLObjectValidatorBaseTestCase {

    private QName qname;
    private QName isqname;
    private QName subqname;
    private Issuer issuer;
    private Subject subject;
    private AssertionSchemaValidator assertionValidator;
    
    /**Constructor*/
    public AssertionSchemaTest() {
        qname = new QName(SAMLConstants.SAML20_NS, Assertion.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        isqname = new QName(SAMLConstants.SAML20_NS, Issuer.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        subqname = new QName(SAMLConstants.SAML20_NS, Subject.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        issuer = (Issuer) buildXMLObject(isqname);
        subject = (Subject) buildXMLObject(subqname);
        assertionValidator = new AssertionSchemaValidator();

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
        Assertion assertion = (Assertion) buildXMLObject(qname);

        assertion.setIssuer(issuer);
        assertion.setID("id");
        assertion.setIssueInstant(new DateTime(1984, 8, 26, 10, 01, 30, 43, ISOChronology.getInstanceUTC()));
        assertion.setSubject(subject);
        
        assertionValidator.validate(assertion);
    }

    /**
     * Tests absent Issuer failure.
     * 
     * @throws ValidationException
     */
    public void testIssuerFailure() throws ValidationException {
        Assertion assertion = (Assertion) buildXMLObject(qname);

        assertion.setID("id");
        assertion.setIssueInstant(new DateTime(1984, 8, 26, 10, 01, 30, 43, ISOChronology.getInstanceUTC()));
        assertion.setSubject(subject);
        
        try {
            assertionValidator.validate(assertion);
            fail("Issuer missing, should raise a Validation Exception");
        } catch (ValidationException success) {
        }
    }

    /**
     * Tests absent ID failure.
     * 
     * @throws ValidationException
     */
    public void testIDFailure() throws ValidationException {
        Assertion assertion = (Assertion) buildXMLObject(qname);

        assertion.setIssuer(issuer);
        assertion.setIssueInstant(new DateTime(1984, 8, 26, 10, 01, 30, 43, ISOChronology.getInstanceUTC()));
        assertion.setSubject(subject);
        
        try {
            assertionValidator.validate(assertion);
            fail("ID missing, should raise a Validation Exception");
        } catch (ValidationException success) {
        }
    }
    
    /**
     * Tests absent IssueInstant failure.
     * 
     * @throws ValidationException
     */
    public void testIssueInstantFailure() throws ValidationException {
        Assertion assertion = (Assertion) buildXMLObject(qname);

        assertion.setIssuer(issuer);
        assertion.setID("id");
        assertion.setSubject(subject);
        
        try {
            assertionValidator.validate(assertion);
            fail("IssueInstant missing, should raise a Validation Exception");
        } catch (ValidationException success) {
        }
    }
    
    /**
     * Tests absent Subject failure.
     * 
     * @throws ValidationException
     */
    public void testSubjectFailure() throws ValidationException {
        Assertion assertion = (Assertion) buildXMLObject(qname);

        assertion.setIssuer(issuer);
        assertion.setID("id");
        assertion.setIssueInstant(new DateTime(1984, 8, 26, 10, 01, 30, 43, ISOChronology.getInstanceUTC()));
        
        try {
            assertionValidator.validate(assertion);
            fail("Subject missing, should raise a Validation Exception");
        } catch (ValidationException success) {
        }
    }
}