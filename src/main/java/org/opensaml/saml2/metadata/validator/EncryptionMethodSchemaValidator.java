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

package org.opensaml.saml2.metadata.validator;

import org.opensaml.saml2.metadata.EncryptionMethod;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.Validator;

/**
 * Checks {@link org.opensaml.saml2.metadata.EncryptionMethod} for Schema compliance.
 */
public class EncryptionMethodSchemaValidator implements Validator<EncryptionMethod> {

    /** Constructor. */
    public EncryptionMethodSchemaValidator() {

    }

    /** {@inheritDoc} */
    public void validate(EncryptionMethod encMethod) throws ValidationException {
        validateAlgorithm(encMethod);
    }

    /**
     * Checks that Algorithm URI attribute is present and not empty.
     * 
     * @param encMethod the encryption method to validate
     * @throws ValidationException thrown if Algorithm attribute is missing or empty
     */
    protected void validateAlgorithm(EncryptionMethod encMethod) throws ValidationException {
        if (DatatypeHelper.isEmpty(encMethod.getAlgorithm())) {
            throw new ValidationException("Algorithm URI attribute is required");
        }
    }
}