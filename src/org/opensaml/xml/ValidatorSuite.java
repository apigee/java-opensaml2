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

package org.opensaml.xml;

import java.util.List;

import javax.xml.namespace.QName;

/**
 * A collection of validators that can be applied to an XMLObject and it's children. These collections can represent
 * usage specific checks, such as those outlined in things like profiles for specific XML specification.
 * 
 * Implementations of ValidatorSuite and the registered {@link org.opensaml.xml.Validator}s must be stateless. The
 * xmlObjectTarget used in many methods must, at least, be evaluated against the XMLObject's element QName {i.e.
 * {@link org.opensaml.xml.XMLObject#getElementQName()}).
 */
public interface ValidatorSuite extends Validator {

    /**
     * Gets a unique ID for this suite.
     * 
     * @return a unique ID for this suite
     */
    public String getId();

    /**
     * Gets the validators for a given XMLObject target.
     * 
     * @param xmlObjectTarget the XMLObject the returned list of validators operates on
     * 
     * @return the list of validators for the XMLObject
     */
    public List<Validator> getValidators(QName xmlObjectTarget);

    /**
     * Registers a new validator in the suite.
     * 
     * @param validator the validator
     * @param xmlObjectTarget the XMLObject the validator should operate on
     */
    public void registerValidator(Validator validator, QName xmlObjectTarget);

    /**
     * Removes a validator from this suite.
     * 
     * @param validator the validator to remove
     */
    public void deregisterValidator(Validator validator);
}