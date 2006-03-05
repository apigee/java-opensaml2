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

/**
 * 
 */

package org.opensaml.saml1.core.validator;

import org.opensaml.saml1.core.AuthorizationDecisionStatement;
import org.opensaml.saml1.core.DecisionType;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;

/**
 * Checks {@link org.opensaml.saml1.core.AudienceRestrictionCondition} for Schema compliance.
 */
public class AuthorizationDecisionStatementValidator extends SubjectStatementValidator {

    /*
     * @see org.opensaml.xml.validation.Validator#validate(org.opensaml.xml.XMLObject)
     */
    public void validate(XMLObject xmlObject) throws ValidationException {
        
        super.validate(xmlObject);
        
        AuthorizationDecisionStatement authorizationDecisionStatement;
        authorizationDecisionStatement = (AuthorizationDecisionStatement) xmlObject;
        
        String resource = authorizationDecisionStatement.getResource();
        if (resource == null || resource.length() == 0) {
            throw new ValidationException("No Resource attribute present");
        }

        DecisionType decision = authorizationDecisionStatement.getDecision();
        if (decision == null) {
            throw new ValidationException("No Decision attribute present");
        }
        
        if (authorizationDecisionStatement.getActions().size() == 0) {
            throw new ValidationException("No Action elements present");
        }
    }
}