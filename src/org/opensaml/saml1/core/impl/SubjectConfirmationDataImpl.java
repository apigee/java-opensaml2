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
import org.opensaml.saml1.core.SubjectConfirmationData;

/**
 * Concrete Implementation of the {@link org.opensaml.saml1.core.SubjectConfirmationData} interface
 */
public class SubjectConfirmationDataImpl extends AbstractSAMLObject implements SubjectConfirmationData {

    /** Contains the content string */
    private String confirmationData;
    
    /**
     * Constructor
     */
    public SubjectConfirmationDataImpl() {
        super(SAMLConstants.SAML1_NS, SubjectConfirmationData.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML1_PREFIX);
    }

    /*
     * @see org.opensaml.saml1.core.SubjectConfirmationData#getConfirmationData()
     */
    public String getConfirmationData() {
        return confirmationData;
    }

    /*
     * @see org.opensaml.saml1.core.SubjectConfirmationData#setConfirmationData(java.lang.String)
     */
    public void setConfirmationData(String confirmationData) {
        this.confirmationData = prepareForAssignment(this.confirmationData, confirmationData);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        return null;
    }

}
