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

import org.opensaml.saml1.core.AuthorityBinding;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.Validator;

/**
 * Checks {@link org.opensaml.saml1.core.AuthorityBinding} for Schema compliance.
 */
public class AuthorityBindingValidator implements Validator {

    /*
     * @see org.opensaml.xml.validation.Validator#validate(org.opensaml.xml.XMLObject)
     */
    public void validate(XMLObject xmlObject) throws ValidationException {
        
        AuthorityBinding authorityBinding = (AuthorityBinding) xmlObject;
         
        String authorityKind = authorityBinding.getAuthorityKind();    
        if (authorityKind == null || authorityKind.length() == 0) {
             throw new ValidationException("No AuthorityKind attribute present");
         }
        
        String location = authorityBinding.getLocation();
        if (location == null || location.length() == 0) {
            throw new ValidationException("No Location attribute present");
        }
        
        String binding = authorityBinding.getBinding();
        if (binding == null || binding.length() == 0) {
            throw new ValidationException("No Bining attribute present");
        }
    }
}