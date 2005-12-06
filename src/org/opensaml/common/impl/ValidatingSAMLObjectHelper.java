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

package org.opensaml.common.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.ValidationException;
import org.opensaml.common.Validator;

/**
 * A helper for SAMLElements that implement the {@link org.opensaml.common.ValidatingObject} interface.
 * This helper is <strong>NOT</strong> thread safe.
 *
 */
public class ValidatingSAMLObjectHelper implements Serializable{

	/**
     * Serial version UID
     */
    private static final long serialVersionUID = 4754449824327104581L;
    
    /** List of registered validators */
	private List<Validator> validators;
	
	/* 
	 * @see org.opensaml.common.ValidatingElement#getValidators()
	 */
	public List<Validator> getValidators() {
		return validators;
	}

	/* 
	 * @see org.opensaml.common.ValidatingElement#registerValidator(org.opensaml.common.Validator)
	 */
	public void registerValidator(Validator validator) {
		if(validators == null){
			validators = new ArrayList<Validator>();
		}

		validators.add(validator);
	}

	/* 
	 * @see org.opensaml.common.ValidatingElement#deregisterValidator(org.opensaml.common.Validator)
	 */
	public void deregisterValidator(Validator validator) {
		validators.remove(validator);
	}

	/* 
	 * @see org.opensaml.common.ValidatingElement#validateElement(boolean)
	 */
	public void validateElement(SAMLObject samlElement, boolean validateChildren)
			throws ValidationException {
		Validator currentRule;
		Iterator itr = validators.iterator();
		while(itr.hasNext()){
			currentRule = (Validator) itr.next();
			currentRule.validate(samlElement);
		}
	}
}