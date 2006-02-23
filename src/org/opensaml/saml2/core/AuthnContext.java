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

package org.opensaml.saml2.core;

import java.util.List;

import org.opensaml.common.SAMLObject;

/**
 * SAML 2.0 Core AuthnContext. 
 */
public interface AuthnContext extends SAMLObject {

    /**Local Name of AuthnContext*/
    public final static String LOCAL_NAME = "AuthnContext";
    
    /**
     * Gets the URI identifying the Context Class of this Authentication Context.
     * 
     * @return AuthnContext AuthnContextClassRef
     */
    public AuthnContextClassRef getAuthnContextClassRef();
    
    /**
     * Sets the URI identifying the Context Class of this Authentication Context.
     * 
     * @param newAuthnContextClassRef the URI of this Authentication Context's Class. 
     */
    public void setAuthnContextClassRef(AuthnContextClassRef newAuthnContextClassRef);
    
    /**
     * Gets Declaration of this Authentication Context.
     * 
     * @return AuthnContext AuthnContextDecl
     */
    public AuthnContextDecl getAuthContextDecl();
    
    /**
     * Sets the Declaration of this Authentication Context.
     * 
     * @param newAuthnContextDecl the Declaration of this Authentication Context
     */
    public void setAuthnContextDecl(AuthnContextDecl newAuthnContextDecl);
    
    /**
     * Gets the URI of the Declaration of this Authentication Context.
     * 
     * @return AuthnContext AuthnContextDeclRef
     */
    public AuthnContextDeclRef getAuthnContextDeclRef();
    
    /**
     * Sets the URI of the Declaration of this Authentication Context.
     * 
     * @param newAuthnContextDeclRef the URI of the Declaration of this Authentication Context
     */
    public void setAuthnContextDeclRef(AuthnContextDeclRef newAuthnContextDeclRef);
    
    /**
     * Gets the Authenticating Athorities of this Authentication Context.
     * 
     * @return AuthnContext AuthenticatingAuthorities
     */
    public List<AuthenticatingAuthority> getAuthenticatingAuthorities();
}