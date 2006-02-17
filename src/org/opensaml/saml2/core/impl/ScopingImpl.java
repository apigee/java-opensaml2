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

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.IDPList;
import org.opensaml.saml2.core.RequesterID;
import org.opensaml.saml2.core.Scoping;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.Scoping}
 */
public class ScopingImpl extends AbstractSAMLObject implements Scoping {
    
    /** IDPList child element */
    private IDPList idpList;
    
    /** List of RequesterID child elements */
    private XMLObjectChildrenList<RequesterID> requesterIDs;
    
    /** ProxyCount attribute */
    private Integer proxyCount;

    /**
     * Constructor
     */
    public ScopingImpl() {
        super(SAMLConstants.SAML20P_NS, Scoping.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20P_PREFIX);
        
        requesterIDs = new XMLObjectChildrenList<RequesterID>(this);
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
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        
        if (idpList != null)
            children.add(idpList);
        
        children.addAll(requesterIDs);
        
        if  (children.size() > 0)
            return Collections.unmodifiableList(children);
        else
            return null;
    }

}
