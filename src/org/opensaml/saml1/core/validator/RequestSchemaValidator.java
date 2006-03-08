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

import org.opensaml.saml1.core.Request;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;

/**
 * Checks {@link org.opensaml.saml1.core.AssertionIDReference} for Schema compliance.
 */
public class RequestSchemaValidator extends RequestAbstractTypeSchemaValidator  {

    /*
     * @see org.opensaml.xml.validation.Validator#validate(org.opensaml.xml.XMLObject)
     */
    public void validate(XMLObject xmlObject) throws ValidationException {
       
        super.validate(xmlObject);
        
        Request request = (Request) xmlObject;
        
        if (request.getQuery() != null) {
            if (request.getAssertionArtifacts().size() != 0) {
                throw new ValidationException("Both Query and one or more AssertionAtrifacts present");
            }
            if (request.getAssertionIDReferences().size() != 0) {
                throw new ValidationException("Both  Query and one ore more AsertionIDReferences present");
            }
        } else if (request.getAssertionArtifacts().size() != 0) {
            if (request.getAssertionIDReferences().size() != 0) {
                throw new ValidationException("Both one or more AssertionAtrifacts and one ore more AsertionIDReferences present");
            }
        } else if (request.getAssertionIDReferences().size() == 0) {
            throw new ValidationException("No AssertionAtrifacts, No Query, and No AsertionIDReferences present");
        }
    }
}