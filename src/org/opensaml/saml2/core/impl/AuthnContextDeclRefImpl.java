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
import org.opensaml.saml2.core.AuthnContextDeclRef;

/**
 * A concrete implementation of {@link org.opensaml.saml2.core.AuthnContextDeclRef}.
 */
public class AuthnContextDeclRefImpl extends AbstractSAMLObject implements AuthnContextDeclRef {

    /** URI of the Authentication Context Declaration */
    private String authnContextDeclRef;

    /** Constructor */
    public AuthnContextDeclRefImpl() {
        super(SAMLConstants.SAML20_NS, AuthnContextDeclRef.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20_PREFIX);
    }

    /*
     * @see org.opensaml.saml2.core.AuthnContextDeclRef#getURI()
     */
    public String getAuthnContextDeclRef() {
        return authnContextDeclRef;
    }

    /*
     * @see org.opensaml.saml2.core.AuthnContextDeclRef#setURI(java.lang.String)
     */
    public void setAuthnContextDeclRef(String newAuthnContextDeclRef) {
        this.authnContextDeclRef = prepareForAssignment(this.authnContextDeclRef, newAuthnContextDeclRef);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        return null;
    }
}