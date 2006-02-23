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
package org.opensaml.saml2.core.impl;

import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.GetComplete;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.GetComplete}
 */
public class GetCompleteImpl extends AbstractSAMLObject implements GetComplete {
    
    /** URI element content */
    private String getComplete;

    /**
     * Constructor
     */
    public GetCompleteImpl() {
        super(SAMLConstants.SAML20P_NS, GetComplete.LOCAL_NAME);
    }

    /**
     * @see org.opensaml.saml2.core.GetComplete#getGetComplete()
     */
    public String getGetComplete() {
        return this.getComplete;
    }

    /**
     * @see org.opensaml.saml2.core.GetComplete#setGetComplete(java.lang.String)
     */
    public void setGetComplete(String newGetComplete) {
        this.getComplete = prepareForAssignment(this.getComplete, newGetComplete);
    }

    /**
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        // No children
        return null;
    }
}