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

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Action;
import org.opensaml.saml1.core.AuthorizationDecisionStatement;
import org.opensaml.saml1.core.DecisionType;

/**
 * Test case for {@link org.opensaml.saml1.core.validator.AuthorizationDecisionStatementValidator}.
 */
public class AuthorizationDecisionStatementSchemaTest extends SubjectStatementSchemaTest {

    /** Constructor */
    public AuthorizationDecisionStatementSchemaTest() {
        super();
        targetQName = new QName(SAMLConstants.SAML1_NS, AuthorizationDecisionStatement.LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
        validator = new AuthorizationDecisionStatementValidator();

    }

    /*
     * @see org.opensaml.common.SAMLObjectValidatorBaseTestCase#populateRequiredData()
     */
    protected void populateRequiredData() {
        super.populateRequiredData();

        AuthorizationDecisionStatement authorizationDecisionStatement = (AuthorizationDecisionStatement) target;
 
        authorizationDecisionStatement.setResource("resource");
        authorizationDecisionStatement.setDecision(DecisionType.DENY);
        
        QName qname = new QName(SAMLConstants.SAML1_NS, Action.LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
        authorizationDecisionStatement.getActions().add((Action)buildXMLObject(qname));
    }
    
    public void testMissingResource() {
        AuthorizationDecisionStatement authorizationDecisionStatement = (AuthorizationDecisionStatement) target;

        authorizationDecisionStatement.setResource("");
        assertValidationFail("No Resource attribute - should fail");
    }

    public void testMissingDecision() {
        AuthorizationDecisionStatement authorizationDecisionStatement = (AuthorizationDecisionStatement) target;

        authorizationDecisionStatement.setDecision(null);
        assertValidationFail("No Decision attribute - should fail");
    }

    public void testEmptyActionList() {
        AuthorizationDecisionStatement authorizationDecisionStatement = (AuthorizationDecisionStatement) target;

        authorizationDecisionStatement.getActions().clear();
        assertValidationFail("No Action elements - should fail");
    }
}