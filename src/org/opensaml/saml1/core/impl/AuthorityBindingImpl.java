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

import java.util.List;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLVersion;
import org.opensaml.saml1.core.AuthorityBinding;
import org.opensaml.xml.XMLObject;

/**
 * A concrete impementation of the {@link org.opensaml.saml1.core.SubjectLocality} interface
 */
public class AuthorityBindingImpl extends AbstractAssertionSAMLObject implements AuthorityBinding {

    /** The AuthorityKind */
    private QName authorityKind;

    /** The Location */
    private String location;

    /** The Binding */
    private String binding;

    /**
     * Hidden Constructor
     * @deprecated
     */
    private AuthorityBindingImpl() {
        super(AuthorityBinding.LOCAL_NAME, null);
    }

    /**
     * Constructor
     *
     * @param version the version to set
     */
    protected AuthorityBindingImpl(SAMLVersion version) {
        super(AuthorityBinding.LOCAL_NAME, version);
    }
    /*
     * @see org.opensaml.saml1.core.AuthorityBinding#getAuthorityKind()
     */
    public QName getAuthorityKind() {
        return authorityKind;
    }

    /*
     * @see org.opensaml.saml1.core.AuthorityBinding#setAuthorityKind(javax.xml.namespace.QName)
     */
    public void setAuthorityKind(QName authorityKind) {
        this.authorityKind = prepareForAssignment(this.authorityKind, authorityKind);
    }

    /*
     * @see org.opensaml.saml1.core.AuthorityBinding#getLocation()
     */
    public String getLocation() {
        return location;
    }

    /*
     * @see org.opensaml.saml1.core.AuthorityBinding#setLocation(java.lang.String)
     */
    public void setLocation(String location) {
        this.location = prepareForAssignment(this.location, location);
    }

    /*
     * @see org.opensaml.saml1.core.AuthorityBinding#getBinding()
     */
    public String getBinding() {
        return binding;
    }

    /*
     * @see org.opensaml.saml1.core.AuthorityBinding#setBinding(java.lang.String)
     */
    public void setBinding(String binding) {
        this.binding = prepareForAssignment(this.binding, binding);
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        // No children
        return null;
    }
}