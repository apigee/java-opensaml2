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
import org.opensaml.saml2.core.GetComplete;
import org.opensaml.saml2.core.IDPEntry;
import org.opensaml.saml2.core.IDPList;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.IDPList}
 */
public class IDPListImpl extends AbstractSAMLObject implements IDPList {

    /** List of IDPEntry's */
    private final XMLObjectChildrenList<IDPEntry> idpEntries;

    /** GetComplete child element */
    private GetComplete getComplete;

    /**
     * Constructor
     * 
     * @param namespaceURI
     * @param elementLocalName
     * @param namespacePrefix
     */
    protected IDPListImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        idpEntries = new XMLObjectChildrenList<IDPEntry>(this);
    }

    /**
     * @see org.opensaml.saml2.core.IDPList#getIDPEntrys()
     */
    public List<IDPEntry> getIDPEntrys() {
        return idpEntries;
    }

    /**
     * @see org.opensaml.saml2.core.IDPList#getGetComplete()
     */
    public GetComplete getGetComplete() {
        return getComplete;
    }

    /**
     * @see org.opensaml.saml2.core.IDPList#setGetComplete(org.opensaml.saml2.core.GetComplete)
     */
    public void setGetComplete(GetComplete newGetComplete) {
        this.getComplete = prepareForAssignment(this.getComplete, newGetComplete);
    }

    /**
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(idpEntries);
        children.add(getComplete);
        if (children.size() > 0)
            return Collections.unmodifiableList(children);
        else
            return null;
    }
}