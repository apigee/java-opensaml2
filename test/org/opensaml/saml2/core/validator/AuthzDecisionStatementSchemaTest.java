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

public class AuthzDecisionStatementSchemaTest extends SAMLObjectValidatorBaseTestCase {

    private QName qname;
    private QName actionQName;
    private Action action;
    private AuthzDecisionStatementSchemaValidator authzDecisionStatementValidator;
    
    /**Constructor*/
    public AuthzDecisionStatementSchemaTest() {
        qname = new QName(SAMLConstants.SAML20_NS, AuthzDecisionStatement.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        actionQName = new QName(SAMLConstants.SAML20_NS, Action.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        action = (Action) buildXMLObject(actionQName);
        authzDecisionStatementValidator = new AuthzDecisionStatementSchemaValidator();
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
        AuthzDecisionStatement authzDecisionStatement = (AuthzDecisionStatement) buildXMLObject(qname);

        authzDecisionStatement.setResource("resource");
        authzDecisionStatement.setDecision(DecisionType.DENY);
        authzDecisionStatement.getActions().add(action);
        
        authzDecisionStatementValidator.validate(authzDecisionStatement);
    }

    /**
     * Tests absent Resource failure.
     * 
     * @throws ValidationException
     */
    public void testResourceFailure() throws ValidationException {
        AuthzDecisionStatement authzDecisionStatement = (AuthzDecisionStatement) buildXMLObject(qname);

        authzDecisionStatement.setDecision(DecisionType.DENY);
        authzDecisionStatement.getActions().add(action);
        
        try {
            authzDecisionStatementValidator.validate(authzDecisionStatement);
            fail("Resource missing, should raise a Validation Exception");
        } catch (ValidationException success) {
        }
    }

    /**
     * Tests absent Decision failure.
     * 
     * @throws ValidationException
     */
    public void testDecisionFailure() throws ValidationException {
        AuthzDecisionStatement authzDecisionStatement = (AuthzDecisionStatement) buildXMLObject(qname);

        authzDecisionStatement.setResource("resource");
        authzDecisionStatement.getActions().add(action);
        
        try {
            authzDecisionStatementValidator.validate(authzDecisionStatement);
            fail("Decision missing, should raise a Validation Exception");
        } catch (ValidationException success) {
        }
    }
    
    /**
     * Tests absent Action failure.
     * 
     * @throws ValidationException
     */
    public void testActionFailure() throws ValidationException {
        AuthzDecisionStatement authzDecisionStatement = (AuthzDecisionStatement) buildXMLObject(qname);

        authzDecisionStatement.setResource("resource");
        authzDecisionStatement.setDecision(DecisionType.DENY);

        try {
            authzDecisionStatementValidator.validate(authzDecisionStatement);
            fail("Action missing, should raise a Validation Exception");
        } catch (ValidationException success) {
        }
    }
}