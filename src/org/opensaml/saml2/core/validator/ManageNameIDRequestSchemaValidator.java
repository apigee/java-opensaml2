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

import org.opensaml.saml2.core.ManageNameIDRequest;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.Validator;

/**
 * Checks {@link org.opensaml.saml2.core.ManageNameIDRequest} for Schema compliance.
 */
public class ManageNameIDRequestSchemaValidator extends RequestSchemaValidator implements Validator {

    /**
     * Constructor
     *
     */
    public ManageNameIDRequestSchemaValidator() {
        super();
    }

    /*
     * @see org.opensaml.saml2.core.validator.RequestSchemaValidator#validate(org.opensaml.xml.XMLObject)
     */
    public void validate(XMLObject xmlObject) throws ValidationException {
        super.validate(xmlObject);
        ManageNameIDRequest request = (ManageNameIDRequest) xmlObject;
        
        validateNameID(request);
        validateNewIDAndTerminate(request);
    }

    /**
     * Validates NameID/EncryptedID child element
     * 
     * @param request
     * @throws ValidationException 
     */
    protected void validateNameID(ManageNameIDRequest request) throws ValidationException {
        if (request.getNameID() == null) {
            throw new ValidationException("NameID is required");
        }
        
        // TODO EncryptedID pending encryption implementation
        
    }
    
    /**
     * Validates NewID/NewEncryptedID child element
     * 
     * @param request
     * @throws ValidationException 
     */
    protected void validateNewIDAndTerminate(ManageNameIDRequest request) throws ValidationException {
        if (request.getNewID() == null && request.getTerminate() == null) {
            throw new ValidationException("Either NewID or Terminate is required");
        }
        
        if (request.getNewID() != null && request.getTerminate() != null) {
            throw new ValidationException("NewID and Terminate are mutually exclusive");
        }
        
        // TODO NewEncryptedID pending encryption implementation
    }
    

}
