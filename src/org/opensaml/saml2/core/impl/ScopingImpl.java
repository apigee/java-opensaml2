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

import org.opensaml.saml2.core.IDPList;
import org.opensaml.saml2.core.RequesterID;
import org.opensaml.saml2.core.Scoping;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.Scoping}
 */
public class ScopingImpl extends AbstractProtocolSAMLObject implements Scoping {

    /** IDPList child element */
    private IDPList idpList;

    /** List of RequesterID child elements */
    private XMLObjectChildrenList<RequesterID> requesterIDs;

    /** ProxyCount attribute */
    private Integer proxyCount;

    /**
     * Constructor
     */
    protected ScopingImpl() {
        super(Scoping.LOCAL_NAME);

        requesterIDs = new XMLObjectChildrenList<RequesterID>(this);
    }

    /**
     * Constructor
     * 
     * @param namespaceURI
     * @param elementLocalName
     * @param namespacePrefix
     */
    protected ScopingImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    /**
     * @see org.opensaml.saml2.core.Scoping#getProxyCount()
     */
    public Integer getProxyCount() {
        return this.proxyCount;
    }

    /**
     * @see org.opensaml.saml2.core.Scoping#setProxyCount(java.lang.Integer)
     */
    public void setProxyCount(Integer newProxyCount) {
        this.proxyCount = prepareForAssignment(this.proxyCount, newProxyCount);
    }

    /**
     * @see org.opensaml.saml2.core.Scoping#getIDPList()
     */
    public IDPList getIDPList() {
        return idpList;
    }

    /**
     * @see org.opensaml.saml2.core.Scoping#setIDPList(org.opensaml.saml2.core.IDPList)
     */
    public void setIDPList(IDPList newIDPList) {
        this.idpList = prepareForAssignment(this.idpList, newIDPList);

    }

    /**
     * @see org.opensaml.saml2.core.Scoping#getRequesterIDs()
     */
    public List<RequesterID> getRequesterIDs() {
        return requesterIDs;
    }

    /**
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

        if (idpList != null)
            children.add(idpList);

        children.addAll(requesterIDs);

        if (children.size() > 0)
            return Collections.unmodifiableList(children);
        else
            return null;
    }
}