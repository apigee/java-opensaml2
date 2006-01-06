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
package org.opensaml.saml1.core.impl;

import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.ConfirmationMethod;

/**
 * Concrete Implementation of the {@link org.opensaml.saml1.core.ConfirmationMethod} interface
 */
public class ConfirmationMethodImpl extends AbstractSAMLObject implements ConfirmationMethod {

    /** Contains the content string */
    private String confirmationMethod;
    
    /**
     * Constructor
     */
    public ConfirmationMethodImpl() {
        super(SAMLConstants.SAML1_NS, ConfirmationMethod.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML1_PREFIX);
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
    public List<SAMLObject> getOrderedChildren() {
        return null;
    }

}
