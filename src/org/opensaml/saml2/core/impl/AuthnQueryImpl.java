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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.AuthnQuery;
import org.opensaml.saml2.core.RequestedAuthnContext;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.AuthnQuery}
 */
public class AuthnQueryImpl extends SubjectQueryImpl implements AuthnQuery {
    
    /** SessionIndex attribute */
    private String sessionIndex;
    
    /** RequestedAuthnContext child element */
    private RequestedAuthnContext requestedAuthnContext;

    /**
     * Constructor
     *
     */
    public AuthnQueryImpl() {
        super(SAMLConstants.SAML20P_NS, AuthnQuery.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20P_PREFIX);
    }

    /**
     * @see org.opensaml.saml2.core.AuthnQuery#getSessionIndex()
     */
    public String getSessionIndex() {
        return this.sessionIndex;
    }

    /**
     * @see org.opensaml.saml2.core.AuthnQuery#setSessionIndex(java.lang.String)
     */
    public void setSessionIndex(String newSessionIndex) {
        this.sessionIndex = prepareForAssignment(this.sessionIndex, newSessionIndex);
    }

    /**
     * @see org.opensaml.saml2.core.AuthnQuery#getRequestedAuthnContext()
     */
    public RequestedAuthnContext getRequestedAuthnContext() {
        return this.requestedAuthnContext;
    }

    /**
     * @see org.opensaml.saml2.core.AuthnQuery#setRequestedAuthnContext(org.opensaml.saml2.core.RequestedAuthnContext)
     */
    public void setRequestedAuthnContext(RequestedAuthnContext newRequestedAuthnContext) {
        this.requestedAuthnContext = prepareForAssignment(this.requestedAuthnContext, newRequestedAuthnContext);
    }

    /**
     * @see org.opensaml.saml2.core.impl.SubjectQueryImpl#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        
        if (super.getOrderedChildren() != null)
            children.addAll(super.getOrderedChildren());
        
        if (requestedAuthnContext != null)
            children.add(requestedAuthnContext);
        
        if (children.size() == 0)
            return null;
        
        return Collections.unmodifiableList(children);
    }
}