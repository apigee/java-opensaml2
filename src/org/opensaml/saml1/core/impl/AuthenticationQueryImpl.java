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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.common.SAMLVersion;
import org.opensaml.saml1.core.AuthenticationQuery;
import org.opensaml.xml.XMLObject;

/**
 * Concrete implementation of the {@link org.opensaml.saml1.core.AuthenticationQuery} interface
 */
public class AuthenticationQueryImpl extends SubjectQueryImpl implements AuthenticationQuery {

    /** The method used to do the authentication */
    private String authenticationMethod;

    /**
     * Constructor
     * @deprecated
     */
    private AuthenticationQueryImpl() {
        super(AuthenticationQuery.LOCAL_NAME, null);
    }
    
    /**
     * Constructor
     *
     * @param version the version to set the object to
     */
    protected AuthenticationQueryImpl(SAMLVersion version) {
        super(AuthenticationQuery.LOCAL_NAME, version);
    }
    /*
     * @see org.opensaml.saml1.core.AuthenticationQuery#getAuthenticationMethod()
     */
    public String getAuthenticationMethod() {
        return authenticationMethod;
    }

    /*
     * @see org.opensaml.saml1.core.AuthenticationQuery#setAuthenticationMethod(java.lang.String)
     */
    public void setAuthenticationMethod(String authenticationMethod) {
        this.authenticationMethod = prepareForAssignment(this.authenticationMethod, authenticationMethod);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        List<XMLObject> list = new ArrayList<XMLObject>();
        
        if (super.getOrderedChildren() != null) {
            list.addAll(super.getOrderedChildren());
        }
        
        if (list.size() == 0) {
            return null;
        }
        
        return Collections.unmodifiableList(list);
    }
}