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
import org.opensaml.saml2.core.SessionIndex;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.SessionIndex}
 */
public class SessionIndexImpl extends AbstractSAMLObject implements SessionIndex {
    
    /** The session index value */
    private String sessionIndex;

    /**
     * Constructor
     *
     */
    public SessionIndexImpl() {
        super(SAMLConstants.SAML20P_NS, SessionIndex.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20P_PREFIX);
    }

    /**
     * @see org.opensaml.saml2.core.SessionIndex#getSessionIndex()
     */
    public String getSessionIndex() {
        return this.sessionIndex;
    }

    /**
     * @see org.opensaml.saml2.core.SessionIndex#setSessionIndex(java.lang.String)
     */
    public void setSessionIndex(String newSessionIndex) {
        this.sessionIndex = prepareForAssignment(this.sessionIndex, newSessionIndex);
    }

    /**
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        // no children
        return null;
    }
}