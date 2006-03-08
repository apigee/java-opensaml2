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
import org.opensaml.saml1.core.impl.AttributeStatementImpl;

/**
 * Test case for {@link org.opensaml.saml1.core.validator.ActionSchemaValidator}.
 */
public class AssertionSchemaTest extends SAMLObjectValidatorBaseTestCase {

    /** Constructor */
    public AssertionSchemaTest() {
        super();
        targetQName = new QName(SAMLConstants.SAML1_NS, Assertion.LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
        validator = new AssertionSchemaValidator();
    }

    /*
     * @see org.opensaml.common.SAMLObjectValidatorBaseTestCase#populateRequiredData()
     */
    protected void populateRequiredData() {
        super.populateRequiredData();
        
        Assertion assertion = (Assertion) target;
        assertion.setIssuer("Issuer");
        assertion.setIssueInstant(new DateTime());
        assertion.getStatements().add(new AttributeStatementImpl());
    }
    
    public void testMissingIssuer(){
        Assertion assertion = (Assertion) target;
        assertion.setIssuer("");
        assertValidationFail("Issuer was empty, should raise a Validation Exception");
    }

    public void testMissingIssueInstant(){
        Assertion assertion = (Assertion) target;
        assertion.setIssueInstant(null);
        assertValidationFail("IssueInstant was empty, should raise a Validation Exception");
    }

    public void testMissingStatement(){
        Assertion assertion = (Assertion) target;
        assertion.getStatements().clear();
        assertValidationFail("No statements, should raise a Validation Exception");
        
    }
}