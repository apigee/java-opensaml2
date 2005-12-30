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
package org.opensaml.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Extension of {@link org.opensaml.xml.AbstractSignableXMLObject} that implements {@link org.opensaml.xml.ValidatingXMLObject}
 */
public abstract class AbstractValidatingSignableXMLObject extends AbstractSignableXMLObject implements ValidatingXMLObject {

    /** Validators used to validate this XMLObject */
    private ArrayList<Validator> validators = new ArrayList<Validator>();
    
    /**
     * Constructor
     * 
     * @param elementLocalName the local name of the XML element this Object represents
     */
    protected AbstractValidatingSignableXMLObject(String elementLocalName) {
        super(elementLocalName);
    }

    /*
     * @see org.opensaml.xml.ValidatingXMLObject#getValidators()
     */
    public List<Validator> getValidators() {
        if (validators.size() > 0) {
            return Collections.unmodifiableList(validators);
        }

        return null;
    }

    /*
     * @see org.opensaml.xml.ValidatingXMLObject#registerValidator(org.opensaml.xml.Validator)
     */
    public void registerValidator(Validator validator) {
        if (validator != null) {
            validators.add(validator);
        }
    }

    /*
     * @see org.opensaml.xml.ValidatingXMLObject#deregisterValidator(org.opensaml.xml.Validator)
     */
    public void deregisterValidator(Validator validator) {
        validators.remove(validator);
    }

    /*
     * @see org.opensaml.xml.ValidatingXMLObject#validate(boolean)
     */
    public void validate(boolean validateDescendants) throws ValidationException {
        for (Validator validator : validators) {
            validator.validate(this);
        }
        
        validateChildren(this);
    }

    /**
     * Recursive method used to validate all the children of the given XMLObject that implement
     * {@link ValidatingXMLObject}. Note, this can be a very expensive operation.
     * 
     * @param xmlObject xmlObject whose descendants should be validated
     * 
     * @throws ValidationException
     */
    protected void validateChildren(XMLObject xmlObject) throws ValidationException {
        for (XMLObject childObject : xmlObject.getOrderedChildren()) {
            if (childObject instanceof ValidatingXMLObject) {
                ((ValidatingXMLObject) childObject).validate(false);
            }
            
            if(childObject.hasChildren()){
                validateChildren(childObject);
            }
        }
    }
}