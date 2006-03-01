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

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.validator.AssertionSchemaValidator;
import org.opensaml.xml.validation.ValidationException;

public class AssertionSchemaTest extends SAMLObjectBaseTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Tests the correct case.
     * 
     * @throws ValidationException
     */
    public void testProper() throws ValidationException {
        QName qname = new QName(SAMLConstants.SAML20_NS, Assertion.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        Assertion assertion = (Assertion) buildXMLObject(qname);

        assertion.setIssuer(new IssuerImpl());
        assertion.setID("id");
        assertion.setIssueInstant(new DateTime(1984, 8, 26, 10, 01, 30, 43, ISOChronology.getInstanceUTC()));
        assertion.setSubject(new SubjectImpl());
        
        AssertionSchemaValidator assertionValidator = new AssertionSchemaValidator();
        assertionValidator.validate(assertion);
    }

    /**
     * Tests absent Issuer failure.
     * 
     * @throws ValidationException
     */
    public void testIssuerFailure() throws ValidationException {
        QName qname = new QName(SAMLConstants.SAML20_NS, Assertion.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        Assertion assertion = (Assertion) buildXMLObject(qname);

        assertion.setID("id");
        assertion.setIssueInstant(new DateTime(1984, 8, 26, 10, 01, 30, 43, ISOChronology.getInstanceUTC()));
        assertion.setSubject(new SubjectImpl());
        
        AssertionSchemaValidator assertionValidator = new AssertionSchemaValidator();
        try {
            assertionValidator.validate(assertion);
            fail("Should raise a Validation Exception");
        } catch (ValidationException success) {
        }
    }

    /**
     * Tests absent ID failure.
     * 
     * @throws ValidationException
     */
    public void testIDFailure() throws ValidationException {
        QName qname = new QName(SAMLConstants.SAML20_NS, Assertion.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        Assertion assertion = (Assertion) buildXMLObject(qname);

        assertion.setIssuer(new IssuerImpl());
        assertion.setIssueInstant(new DateTime(1984, 8, 26, 10, 01, 30, 43, ISOChronology.getInstanceUTC()));
        assertion.setSubject(new SubjectImpl());
        
        AssertionSchemaValidator assertionValidator = new AssertionSchemaValidator();
        try {
            assertionValidator.validate(assertion);
            fail("Should raise a Validation Exception");
        } catch (ValidationException success) {
        }
    }
    
    /**
     * Tests absent IssueInstant failure.
     * 
     * @throws ValidationException
     */
    public void testIssueInstantFailure() throws ValidationException {
        QName qname = new QName(SAMLConstants.SAML20_NS, Assertion.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        Assertion assertion = (Assertion) buildXMLObject(qname);

        assertion.setIssuer(new IssuerImpl());
        assertion.setID("id");
        assertion.setSubject(new SubjectImpl());
        
        AssertionSchemaValidator assertionValidator = new AssertionSchemaValidator();
        try {
            assertionValidator.validate(assertion);
            fail("Should raise a Validation Exception");
        } catch (ValidationException success) {
        }
    }
    
    /**
     * Tests absent Subject failure.
     * 
     * @throws ValidationException
     */
    public void testSubjectFailure() throws ValidationException {
        QName qname = new QName(SAMLConstants.SAML20_NS, Assertion.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        Assertion assertion = (Assertion) buildXMLObject(qname);

        assertion.setIssuer(new IssuerImpl());
        assertion.setID("id");
        assertion.setIssueInstant(new DateTime(1984, 8, 26, 10, 01, 30, 43, ISOChronology.getInstanceUTC()));
        
        AssertionSchemaValidator assertionValidator = new AssertionSchemaValidator();
        try {
            assertionValidator.validate(assertion);
            fail("Should raise a Validation Exception");
        } catch (ValidationException success) {
        }
    }
    
    public void testSingleElementUnmarshall() {
        // TODO Auto-generated method stub
    }

    public void testSingleElementMarshall() {
        // TODO Auto-generated method stub
    }
}