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

package org.opensaml.common;

import java.util.List;

/**
 * A functional interface for SAML Elements that offer the ability
 * to evaluate validation rules.
 */
public interface ValidatingObject extends SAMLObject{

    /**
     * Gets the list of validators for this element or null if there is no list.
     * 
     * @return the list of validators for this element
     */
    public List<Validator> getValidators();
    
    /**
     * Registers a validator for this element.
     * 
     * @param validator the validator
     */
    public void registerValidator(Validator validator);
    
    /**
     * Deregisters a validator for this element.
     * 
     * @param validator the validator
     */
    public void deregisterValidator(Validator validator);
    
    /**
     * Validates the element against all registered validators.
     * 
     * @param validateChildren true if all the children of this element should 
     * be validated as well, false if not
     * 
     * @throws ValidationException thrown if the element is not valid
     */
    public void validateElement(boolean validateChildren) throws ValidationException;

}
