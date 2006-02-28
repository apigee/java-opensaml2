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

package org.opensaml.saml1.core.impl;

import java.util.List;

import org.opensaml.saml1.core.ConfirmationMethod;
import org.opensaml.xml.XMLObject;

/**
 * Concrete Implementation of the {@link org.opensaml.saml1.core.ConfirmationMethod} interface
 */
public class ConfirmationMethodImpl extends AbstractAssertionSAMLObject implements ConfirmationMethod {

    /** Contains the content string */
    private String confirmationMethod;

    /**
     * Constructor
     */
    protected ConfirmationMethodImpl() {
        super(ConfirmationMethod.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.saml1.core.ConfirmationMethod#getConfirmationMethod()
     */
    public String getConfirmationMethod() {
        return confirmationMethod;
    }

    /*
     * @see org.opensaml.saml1.core.ConfirmationMethod#setConfirmationMethod(java.lang.String)
     */
    public void setConfirmationMethod(String confirmationMethod) {
        this.confirmationMethod = prepareForAssignment(this.confirmationMethod, confirmationMethod);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}