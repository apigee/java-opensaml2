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
import org.opensaml.saml2.core.Action;
import org.opensaml.saml2.core.AuthzDecisionStatement;
import org.opensaml.saml2.core.DecisionType;
import org.opensaml.xml.validation.ValidationException;

/**
 * Test case for {@link org.opensaml.saml2.core.validator.AuthzDecisionStatementSchemaValidator}.
 */
public class AuthzDecisionStatementSchemaTest extends SAMLObjectValidatorBaseTestCase {

    /** Constructor */
    public AuthzDecisionStatementSchemaTest() {
        targetQName = new QName(SAMLConstants.SAML20_NS, AuthzDecisionStatement.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        validator = new AuthzDecisionStatementSchemaValidator();
    }

    protected void populateRequiredData() {
        super.populateRequiredData();
        AuthzDecisionStatement authzDecisionStatement = (AuthzDecisionStatement) target;
        Action action = (Action) buildXMLObject(new QName(SAMLConstants.SAML20_NS, Action.LOCAL_NAME,
                SAMLConstants.SAML20_PREFIX));
        authzDecisionStatement.setResource("resource");
        authzDecisionStatement.setDecision(DecisionType.DENY);
        authzDecisionStatement.getActions().add(action);
    }

    /**
     * Tests absent Resource failure.
     * 
     * @throws ValidationException
     */
    public void testResourceFailure() throws ValidationException {
        AuthzDecisionStatement authzDecisionStatement = (AuthzDecisionStatement) target;

        authzDecisionStatement.setResource(null);
        try {
            validator.validate(authzDecisionStatement);
            fail("Resource was null, should raise a Validation Exception");
        } catch (ValidationException e) {
        }

        authzDecisionStatement.setResource("");
        try {
            validator.validate(authzDecisionStatement);
            fail("Resource was empty string, should raise a Validation Exception");
        } catch (ValidationException e) {
        }
    }

    /**
     * Tests absent Decision failure.
     * 
     * @throws ValidationException
     */
    public void testDecisionFailure() throws ValidationException {
        AuthzDecisionStatement authzDecisionStatement = (AuthzDecisionStatement) target;

        authzDecisionStatement.setDecision(null);
        try {
            validator.validate(authzDecisionStatement);
            fail("Decision was null, should raise a Validation Exception");
        } catch (ValidationException e) {
        }
    }

    /**
     * Tests absent Action failure.
     * 
     * @throws ValidationException
     */
    public void testActionFailure() throws ValidationException {
        AuthzDecisionStatement authzDecisionStatement = (AuthzDecisionStatement) target;

        authzDecisionStatement.getActions().clear();
        try {
            validator.validate(authzDecisionStatement);
            fail("Action list was empty, should raise a Validation Exception");
        } catch (ValidationException e) {
        }
    }
}