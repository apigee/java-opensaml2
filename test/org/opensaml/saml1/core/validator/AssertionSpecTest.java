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

package org.opensaml.saml1.core.validator;

import javax.xml.namespace.QName;

import org.joda.time.DateTime;
import org.opensaml.common.SAMLObjectValidatorBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.AttributeStatement;

/**
 * Test case for {@link org.opensaml.saml1.core.validator.AssertionSchemaValidator}.
 */
public class AssertionSpecTest extends SAMLObjectValidatorBaseTestCase {

    /** Constructor */
    public AssertionSpecTest() {
        super();
        targetQName = new QName(SAMLConstants.SAML1_NS, Assertion.LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
        validator = new AssertionSpecValidator();
    }

    /*
     * @see org.opensaml.common.SAMLObjectValidatorBaseTestCase#populateRequiredData()
     */
    protected void populateRequiredData() {
        super.populateRequiredData();
        Assertion assertion = (Assertion) target;
        assertion.setIssuer("Issuer");
        assertion.setID("ident");
        assertion.setIssueInstant(new DateTime());
        QName name = new QName(SAMLConstants.SAML1_NS, AttributeStatement.LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
        assertion.getStatements().add((AttributeStatement)buildXMLObject(name));
    }
    
    public void testWrongSubVersion() {

        QName name = new QName(SAMLConstants.SAML1_NS, AttributeStatement.LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
        Assertion assertion = (Assertion) target;
        assertion.getStatements().add((AttributeStatement)buildXMLObject(name));
        
        assertValidationFail("Invalid child version, should raise a Validation Exception");
    }
}