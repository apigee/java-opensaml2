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

package org.opensaml.saml2.core.validator;

import org.opensaml.saml2.core.Assertion;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.Validator;

/**
 * Checks {@link org.opensaml.saml2.core.Assertion} for Schema compliance.
 */
public class AssertionSchemaValidator implements Validator {

    /** Constructor */
    public AssertionSchemaValidator() {

    }

    /*
     * @see org.opensaml.xml.validation.Validator#validate(org.opensaml.xml.XMLObject)
     */
    public void validate(XMLObject xmlObject) throws ValidationException {
        Assertion assertion = (Assertion) xmlObject;

        validateIssuer(assertion);
        validateVersion(assertion);
        validateID(assertion);
        validateIssueInstant(assertion);
        validateSubject(assertion);
    }

    /**
     * Checks that Issuer element is present.
     * 
     * @param assertion
     * @throws ValidationException
     */
    protected void validateIssuer(Assertion assertion) throws ValidationException {
        if (assertion.getIssuer() == null) {
            throw new ValidationException("Issuer is required element");
        }
    }

    /**
     * Checks that the Version attribute is present.
     * 
     * @param assertion
     * @throws ValidationException
     */
    protected void validateVersion(Assertion assertion) throws ValidationException {
        if (assertion.getVersion() == null) {
            throw new ValidationException("Version is required attribute");
        }
    }

    /**
     * Checks that the ID attribute is present.
     * 
     * @param assertion
     * @throws ValidationException
     */
    protected void validateID(Assertion assertion) throws ValidationException {
        if (DatatypeHelper.isEmpty(assertion.getID())) {
            throw new ValidationException("ID is required attribute");
        }
    }

    /**
     * Checks that the IssueInstant attribute is present.
     * 
     * @param assertion
     * @throws ValidationException
     */
    protected void validateIssueInstant(Assertion assertion) throws ValidationException {
        if (assertion.getIssueInstant() == null) {
            throw new ValidationException("IssueInstant is required attribute");
        }
    }

    /**
     * Checks that the Subject element is present when required.
     * 
     * @param assertion
     * @throws ValidationException
     */
    protected void validateSubject(Assertion assertion) throws ValidationException {
        if ((assertion.getStatements() == null || assertion.getStatements().size() == 0)
                && (assertion.getAuthnStatements() == null || assertion.getAuthnStatements().size() == 0)
                && (assertion.getAttributeStatement() == null || assertion.getAttributeStatement().size() == 0)
                && (assertion.getAuthzDecisionStatements() == null || assertion.getAuthzDecisionStatements().size() == 0)
                && assertion.getSubject() == null) {
            throw new ValidationException("Subject is required when Statements are absent");
        }

        if (assertion.getAuthnStatements().size() > 0 && assertion.getSubject() == null) {
            throw new ValidationException("Assertions containing AuthnStatements require a Subject");
        }
        if (assertion.getAuthzDecisionStatements().size() > 0 && assertion.getSubject() == null) {
            throw new ValidationException("Assertions containing AuthzDecisionStatements require a Subject");
        }
        if (assertion.getAttributeStatement().size() > 0 && assertion.getSubject() == null) {
            throw new ValidationException("Assertions containing AttributeStatements require a Subject");
        }
    }
}