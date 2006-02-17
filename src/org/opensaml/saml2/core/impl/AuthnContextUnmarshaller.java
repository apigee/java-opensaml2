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

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.AuthenticatingAuthority;
import org.opensaml.saml2.core.AuthnContext;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnContextDecl;
import org.opensaml.saml2.core.AuthnContextDeclRef;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread-safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.core.AuthnContext}.
 */
public class AuthnContextUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /** Constructor */
    public AuthnContextUnmarshaller() {
        super(SAMLConstants.SAML20_NS, AuthnContext.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processChildElement(org.opensaml.common.SAMLObject,
     *      org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(SAMLObject parentObject, SAMLObject childObject) throws UnmarshallingException,
            UnknownElementException {
        AuthnContext authnContext = (AuthnContext) parentObject;
        if (childObject instanceof AuthnContextClassRef) {
            authnContext.setAuthnContextClassRef((AuthnContextClassRef) childObject);
        } else if (childObject instanceof AuthnContextDecl) {
            authnContext.setAuthnContextDecl((AuthnContextDecl) childObject);
        } else if (childObject instanceof AuthnContextDeclRef) {
            authnContext.setAuthnContextDeclRef((AuthnContextDeclRef) childObject);
        } else if (childObject instanceof AuthenticatingAuthority) {
            authnContext.getAuthenticatingAuthorities().add((AuthenticatingAuthority) childObject);
        } else {
            super.processChildElement(parentObject, childObject);
        }
    }
}