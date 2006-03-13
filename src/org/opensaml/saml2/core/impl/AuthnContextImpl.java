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

import org.opensaml.saml2.core.AuthenticatingAuthority;
import org.opensaml.saml2.core.AuthnContext;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnContextDecl;
import org.opensaml.saml2.core.AuthnContextDeclRef;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * A concrete implemenation of {@link org.opensaml.saml2.core.AuthnContext}.
 */
public class AuthnContextImpl extends AbstractAssertionSAMLObject implements AuthnContext {

    /** URI of the Context Class */
    private AuthnContextClassRef authnContextClassRef;

    /** Declaration of the Authentication Context */
    private AuthnContextDecl authnContextDecl;

    /** URI of the Declaration of the Authentication Context */
    private AuthnContextDeclRef authnContextDeclRef;

    /** List of the Authenticating Authorities */
    private XMLObjectChildrenList<AuthenticatingAuthority> authenticatingAuthority;

    /** Constructor */
    protected AuthnContextImpl() {
        super(AuthnContext.LOCAL_NAME);

        authenticatingAuthority = new XMLObjectChildrenList<AuthenticatingAuthority>(this);
    }

    /**
     * Constructor
     * 
     * @param namespaceURI
     * @param elementLocalName
     * @param namespacePrefix
     */
    protected AuthnContextImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    /*
     * @see org.opensaml.saml2.core.AuthnContext#getAuthnContextClassRef()
     */
    public AuthnContextClassRef getAuthnContextClassRef() {
        return authnContextClassRef;
    }

    /*
     * @see org.opensaml.saml2.core.AuthnContext#setAuthnContextClassRef(org.opensaml.saml2.core.AuthnContextClassRef)
     */
    public void setAuthnContextClassRef(AuthnContextClassRef newAuthnContextClassRef) {
        this.authnContextClassRef = prepareForAssignment(this.authnContextClassRef, newAuthnContextClassRef);
    }

    /*
     * @see org.opensaml.saml2.core.AuthnContext#getAuthContextDecl()
     */
    public AuthnContextDecl getAuthContextDecl() {
        return authnContextDecl;
    }

    /*
     * @see org.opensaml.saml2.core.AuthnContext#setAuthnContextDecl(org.opensaml.saml2.core.AuthnContextDecl)
     */
    public void setAuthnContextDecl(AuthnContextDecl newAuthnContextDecl) {
        this.authnContextDecl = prepareForAssignment(this.authnContextDecl, newAuthnContextDecl);
    }

    /*
     * @see org.opensaml.saml2.core.AuthnContext#getAuthnContextDeclRef()
     */
    public AuthnContextDeclRef getAuthnContextDeclRef() {
        return authnContextDeclRef;
    }

    /*
     * @see org.opensaml.saml2.core.AuthnContext#setAuthnContextDeclRef(org.opensaml.saml2.core.AuthnContextDeclRef)
     */
    public void setAuthnContextDeclRef(AuthnContextDeclRef newAuthnContextDeclRef) {
        this.authnContextDeclRef = prepareForAssignment(this.authnContextDeclRef, newAuthnContextDeclRef);
    }

    /*
     * @see org.opensaml.saml2.core.AuthnContext#getAuthenticatingAuthorities()
     */
    public List<AuthenticatingAuthority> getAuthenticatingAuthorities() {
        return authenticatingAuthority;
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

        children.add(authnContextClassRef);
        children.add(authnContextDecl);
        children.add(authnContextDeclRef);
        children.addAll(authenticatingAuthority);

        return Collections.unmodifiableList(children);
    }
}