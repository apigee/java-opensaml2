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

import org.opensaml.saml2.core.NameIDMappingResponse;
import org.opensaml.xml.validation.ValidationException;

/**
 * Checks {@link org.opensaml.saml2.core.NameIDMappingResponse} for Schema compliance.
 */
public class NameIDMappingResponseSchemaValidator extends StatusResponseSchemaValidator<NameIDMappingResponse> {

    /**
     * Constructor
     *
     */
    public NameIDMappingResponseSchemaValidator() {
        super();
    }

    /** {@inheritDoc} */
    public void validate(NameIDMappingResponse response) throws ValidationException {
        super.validate(response);
        validateIdentifiers(response);
    }

    /**
     * Validate the identifier child elements (NameID, EncryptedID).
     * 
     * @param resp
     * @throws ValidationException 
     */
    protected void validateIdentifiers(NameIDMappingResponse resp) throws ValidationException {
        if (resp.getNameID() == null) {
            throw new ValidationException("NameID is required");
        }
        
        // TODO EncryptedID pending encryption implementation.
    }

}
