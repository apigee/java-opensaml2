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

import org.opensaml.saml2.core.NameIDMappingRequest;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.Validator;

/**
 * Checks {@link org.opensaml.saml2.core.NameIDMappingRequest} for Schema compliance.
 */
public class NameIDMappingRequestSchemaValidator extends RequestSchemaValidator implements Validator {

    /**
     * Constructor
     *
     */
    public NameIDMappingRequestSchemaValidator() {
        super();
    }

    /*
     * @see org.opensaml.saml2.core.validator.RequestSchemaValidator#validate(org.opensaml.xml.XMLObject)
     */
    public void validate(XMLObject xmlObject) throws ValidationException {
        super.validate(xmlObject);
        NameIDMappingRequest request = (NameIDMappingRequest) xmlObject;
        
        validateIdentifiers(request);
        validateNameIDPolicy(request);
    }

    /**
     * Validates the identifier child types (BaseID, NameID, EncryptedID).
     * 
     * @param request
     * @throws ValidationException 
     */
    protected void validateIdentifiers(NameIDMappingRequest request) throws ValidationException {
        if (request.getBaseID() == null && request.getNameID() == null) {
            throw new ValidationException("Either NameID or BaseID child is required");
        }
        
        if (request.getBaseID() != null && request.getNameID() != null) {
            throw new ValidationException("NameID and BaseID are mutually exclusive");
        }
        
        // TODO EncryptedID pending encryption implementation
    }
    
    /**
     * Validates the NameIDPolicy child element.
     * 
     * @param request
     * @throws ValidationException 
     */
    private void validateNameIDPolicy(NameIDMappingRequest request) throws ValidationException {
        if(request.getNameIDPolicy() == null) {
            throw new ValidationException("NameIDPolicy is required");
        }
    }
}
