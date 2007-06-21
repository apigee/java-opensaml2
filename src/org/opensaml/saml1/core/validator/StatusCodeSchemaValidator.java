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

import javax.xml.namespace.QName;

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.StatusCode;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.Validator;

/**
 * Checks {@link org.opensaml.saml1.core.StatusCode} for Schema compliance.
 */
public class StatusCodeSchemaValidator implements Validator<StatusCode> {

    private final static String[] allowedCodes = { "Success", "VersionMismatch", "Requester", "Responder",
            "RequestVersionTooHigh", "RequestVersionTooLow", "RequestVersionDeprecated", "TooManyResponses",
            "RequestDenied", "ResourceNotRecognized" };

    /** {@inheritDoc} */
    public void validate(StatusCode statusCode) throws ValidationException {

        validateValue(statusCode);
        validateValueQNameNamespace(statusCode);
        validateValueContent(statusCode);
    }

    /**
     * Validates that the status code has a value.
     * 
     * @param statusCode status code to validate
     * 
     * @throws ValidationException thrown if the status code does not have a value
     */
    protected void validateValue(StatusCode statusCode) throws ValidationException {
        String value = statusCode.getValue();
        if (DatatypeHelper.isEmpty(value)) {
            throw new ValidationException("No Value attribute present");
        }
    }

    /**
     * Validates that the status code value is not in the SAML 1 namespace.
     * 
     * @param statusCode status code to validate
     * 
     * @throws ValidationException thrown if the status code is in the SAML 1 namespace
     */
    protected void validateValueQNameNamespace(StatusCode statusCode) throws ValidationException {
        String value = statusCode.getValue();
        QName qname = QName.valueOf(value);

        if (SAMLConstants.SAML1_NS.equals(qname.getNamespaceURI())) {
            throw new ValidationException("value Qname cannot be in the SAML1 Assertion namespace");
        }
    }

    /**
     * Validates that the status code local name is one of the allowabled values.
     * 
     * @param statusCode the status code to validate
     * 
     * @throws ValidationException thrown if the status code local name is not an allowed value
     */
    protected void validateValueContent(StatusCode statusCode) throws ValidationException {
        String value = statusCode.getValue();
        QName qname = QName.valueOf(value);

        if (SAMLConstants.SAML10P_NS.equals(qname.getNamespaceURI())) {
            String localName = qname.getLocalPart();
            boolean allowedName = false;
            for (int i = 0; i < allowedCodes.length; i++) {
                if (allowedCodes[i].equals(localName)) {
                    allowedName = true;
                    break;
                }
            }
            if (!allowedName) {
                throw new ValidationException(localName + " is not a valid local name");
            }
        }
    }
}