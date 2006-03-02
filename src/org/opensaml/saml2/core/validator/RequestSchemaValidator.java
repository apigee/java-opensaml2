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

import org.opensaml.saml2.core.Request;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.Validator;

/**
 * Checks {@link org.opensaml.saml2.core.Request} for Schema compliance.
 */
public abstract class RequestSchemaValidator implements Validator {

    /**
     * Constructor
     *
     */
    public RequestSchemaValidator() {
    }

    /*
     * @see org.opensaml.xml.validation.Validator#validate(org.opensaml.xml.XMLObject)
     */
    public void validate(XMLObject xmlObject) throws ValidationException {
        Request request = (Request) xmlObject;
        
        validateID(request);
        validateVersion(request);
        validateIssueInstant(request);

    }
    
    /**
     * Validates the ID attribute
     * 
     * @param request
     * @throws ValidationException
     */
    protected void validateID(Request request) throws ValidationException {
        if (DatatypeHelper.isEmpty(request.getID()))
            throw new ValidationException("ID attribute must not be empty");
    }

    /**
     * Validates the Version attribute
     * 
     * @param request
     * @throws ValidationException
     */
    protected void validateVersion(Request request) throws ValidationException {
        if (request.getVersion() == null)
            throw new ValidationException("Version attribute must not be null");
    }
    
    /**
     * Validates the IsssueInstant attribute
     * 
     * @param request
     * @throws ValidationException
     */
    protected void validateIssueInstant(Request request) throws ValidationException {
        if (request.getIssueInstant() == null)
            throw new ValidationException ("IssueInstant attribute must not be null");
    }
}
