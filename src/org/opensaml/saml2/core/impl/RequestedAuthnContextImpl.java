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

import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnContextComparisonType;
import org.opensaml.saml2.core.AuthnContextDeclRef;
import org.opensaml.saml2.core.RequestedAuthnContext;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.RequestedAuthnContext}
 */
public class RequestedAuthnContextImpl extends AbstractSAMLObject implements RequestedAuthnContext {

    /** AuthnContextClassRef child elements */
    private final XMLObjectChildrenList<AuthnContextClassRef> authnContextClassRefs;

    /** AuthnContextDeclRef child elements */
    private final XMLObjectChildrenList<AuthnContextDeclRef> authnContextDeclRefs;

    /** Comparison attribute */
    private AuthnContextComparisonType comparison;

    /**
     * Constructor
     * 
     * @param namespaceURI
     * @param elementLocalName
     * @param namespacePrefix
     */
    protected RequestedAuthnContextImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        authnContextClassRefs = new XMLObjectChildrenList<AuthnContextClassRef>(this);
        authnContextDeclRefs = new XMLObjectChildrenList<AuthnContextDeclRef>(this);
    }

    /**
     * @see org.opensaml.saml2.core.RequestedAuthnContext#getComparison()
     */
    public AuthnContextComparisonType getComparison() {
        return this.comparison;
    }

    /**
     * @see org.opensaml.saml2.core.RequestedAuthnContext#setComparison(org.opensaml.saml2.core.AuthnContextComparisonType)
     */
    public void setComparison(AuthnContextComparisonType newComparison) {
        this.comparison = prepareForAssignment(this.comparison, newComparison);
    }

    /**
     * @see org.opensaml.saml2.core.RequestedAuthnContext#getAuthnContextClassRefs()
     */
    public List<AuthnContextClassRef> getAuthnContextClassRefs() {
        return this.authnContextClassRefs;
    }

    /**
     * @see org.opensaml.saml2.core.RequestedAuthnContext#getAuthnContextDeclRefs()
     */
    public List<AuthnContextDeclRef> getAuthnContextDeclRefs() {
        return this.authnContextDeclRefs;
    }

    /**
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

        children.addAll(authnContextClassRefs);
        children.addAll(authnContextDeclRefs);

        if (children.size() == 0)
            return null;

        return Collections.unmodifiableList(children);
    }
}