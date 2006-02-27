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

package org.opensaml.saml2.metadata.impl;

import java.util.List;

import org.opensaml.saml2.metadata.TelephoneNumber;
import org.opensaml.xml.XMLObject;

/**
 * Concrete implementation of {@link org.opensaml.saml2.metadata.TelephoneNumber}
 */
public class TelephoneNumberImpl extends AbstractMetadataSAMLObject implements TelephoneNumber {

    /** Telephone number */
    private String number;

    /**
     * Constructor
     */
    protected TelephoneNumberImpl() {
        super(TelephoneNumber.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.saml2.metadata.TelephoneNumber#getNumber()
     */
    public String getNumber() {
        return number;
    }

    /*
     * @see org.opensaml.saml2.metadata.TelephoneNumber#setNumber(java.lang.String)
     */
    public void setNumber(String newNumber) {
        number = prepareForAssignment(number, newNumber);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}