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

import java.util.List;

import org.opensaml.common.SAMLVersion;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.Statement;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.Validator;

/**
 * Checks {@link org.opensaml.saml1.core.AssertionIDReference} for Schema compliance.
 */
public class AssertionSchemaValidator implements Validator {

    /*
     * @see org.opensaml.xml.validation.Validator#validate(org.opensaml.xml.XMLObject)
     */
    public void validate(XMLObject xmlObject) throws ValidationException {
        
         Assertion assertion= (Assertion) xmlObject;
         
         if ((assertion.getVersion() != SAMLVersion.VERSION_10) &&
             (assertion.getVersion() != SAMLVersion.VERSION_11)) {
             throw new ValidationException("Invalid Version");
         }
         
         String issuer = assertion.getIssuer();
         if ((issuer == null) || (issuer.length() == 0)) {
             throw new ValidationException("Issuer not present");
         }

         if (assertion.getIssueInstant() == null) {
             throw new ValidationException("IssueInstant not present");
         }
         
         List <Statement> list = assertion.getStatements();
         if (list == null || list.size() == 0) {
             throw new ValidationException("No Statements present");
         }
    }
}