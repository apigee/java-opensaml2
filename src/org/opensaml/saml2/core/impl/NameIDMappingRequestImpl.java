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

package org.opensaml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.saml2.core.BaseID;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.NameIDMappingRequest;
import org.opensaml.saml2.core.NameIDPolicy;
import org.opensaml.xml.XMLObject;

/**
 * A concrete implementation of {@link org.opensaml.saml2.core.NameIDMappingRequest}
 */
public class NameIDMappingRequestImpl extends RequestImpl implements NameIDMappingRequest {

    /** BaseID child element */
    private BaseID baseID;

    /** NameID child element */
    private NameID nameID;

    /** NameIDPolicy child element */
    private NameIDPolicy nameIDPolicy;

    /**
     * Constructor
     * 
     * @param namespaceURI
     * @param elementLocalName
     * @param namespacePrefix
     */
    protected NameIDMappingRequestImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    /*
     * @see org.opensaml.saml2.core.LogoutRequest#getBaseID()
     */
    public BaseID getBaseID() {
        return baseID;
    }

    /*
     * @see org.opensaml.saml2.core.LogoutRequest#setBaseID(org.opensaml.saml2.core.BaseID)
     */
    public void setBaseID(BaseID newBaseID) {
        baseID = prepareForAssignment(baseID, newBaseID);
    }

    /*
     * @see org.opensaml.saml2.core.LogoutRequest#getNameID()
     */
    public NameID getNameID() {
        return nameID;
    }

    /*
     * @see org.opensaml.saml2.core.LogoutRequest#setNameID(org.opensaml.saml2.core.NameID)
     */
    public void setNameID(NameID newNameID) {
        nameID = prepareForAssignment(nameID, newNameID);
    }

    /**
     * @see org.opensaml.saml2.core.NameIDMappingRequest#getNameIDPolicy()
     */
    public NameIDPolicy getNameIDPolicy() {
        return this.nameIDPolicy;
    }

    /**
     * @see org.opensaml.saml2.core.NameIDMappingRequest#setNameIDPolicy(org.opensaml.saml2.core.NameIDPolicy)
     */
    public void setNameIDPolicy(NameIDPolicy newNameIDPolicy) {
        this.nameIDPolicy = prepareForAssignment(this.nameIDPolicy, newNameIDPolicy);
    }

    /**
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

        if (super.getOrderedChildren() != null)
            children.addAll(super.getOrderedChildren());

        if (baseID != null) {
            children.add(baseID);
        }

        if (nameID != null) {
            children.add(nameID);
        }

        if (nameIDPolicy != null)
            children.add(nameIDPolicy);

        if (children.size() == 0)
            return null;

        return Collections.unmodifiableList(children);
    }
}