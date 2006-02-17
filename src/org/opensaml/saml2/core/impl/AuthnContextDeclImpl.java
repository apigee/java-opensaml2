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
import org.opensaml.saml2.core.AuthnContextDecl;

/**
 * A concrete implementation of {@link org.opensaml.saml2.core.AuthnContextDeclImpl}.
 */
public class AuthnContextDeclImpl extends AbstractSAMLObject implements AuthnContextDecl {

    /** Declaration of the Authentication Context */
    private String declaration;

    /** Constructor */
    public AuthnContextDeclImpl() {
        super(SAMLConstants.SAML20_NS, AuthnContextDecl.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20_PREFIX);
    }

    /*
     * @see org.opensaml.saml2.core.AuthnContextDecl#getDeclartion()
     */
    public String getDeclartion() {
        return declaration;
    }

    /*
     * @see org.opensaml.saml2.core.AuthnContextDecl#setDeclaration(java.lang.String)
     */
    public void setDeclaration(String newDeclaration) {
        this.declaration = prepareForAssignment(this.declaration, newDeclaration);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        return null;
    }
}