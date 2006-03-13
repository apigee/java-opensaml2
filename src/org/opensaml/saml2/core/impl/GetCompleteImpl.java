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

import org.opensaml.saml2.core.GetComplete;
import org.opensaml.xml.XMLObject;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.GetComplete}
 */
public class GetCompleteImpl extends AbstractProtocolSAMLObject implements GetComplete {

    /** URI element content */
    private String getComplete;

    /**
     * Constructor
     */
    protected GetCompleteImpl() {
        super(GetComplete.LOCAL_NAME);
    }

    /**
     * Constructor
     * 
     * @param namespaceURI
     * @param elementLocalName
     * @param namespacePrefix
     */
    protected GetCompleteImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
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
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        // No children
        return null;
    }
}