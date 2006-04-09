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

import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.saml2.core.IDPEntry;
import org.opensaml.xml.XMLObject;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.IDPEntry}
 */
public class IDPEntryImpl extends AbstractSAMLObject implements IDPEntry {

    /** The unique identifier of the IdP */
    private String providerID;

    /** Human-readable name for the IdP */
    private String name;

    /**
     * URI reference representing the location of a profile-specific endpoint supporting the authentication request
     * protocol.
     */
    private String loc;

    /**
     * Constructor
     * 
     * @param namespaceURI
     * @param elementLocalName
     * @param namespacePrefix
     */
    protected IDPEntryImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    /**
     * @see org.opensaml.saml2.core.IDPEntry#getProviderID()
     */
    public String getProviderID() {
        return this.providerID;
    }

    /**
     * @see org.opensaml.saml2.core.IDPEntry#setProviderID(java.lang.String)
     */
    public void setProviderID(String newProviderID) {
        this.providerID = prepareForAssignment(this.providerID, newProviderID);

    }

    /**
     * @see org.opensaml.saml2.core.IDPEntry#getName()
     */
    public String getName() {
        return this.name;
    }

    /**
     * @see org.opensaml.saml2.core.IDPEntry#setName(java.lang.String)
     */
    public void setName(String newName) {
        this.name = prepareForAssignment(this.name, newName);

    }

    /**
     * @see org.opensaml.saml2.core.IDPEntry#getLoc()
     */
    public String getLoc() {
        return this.loc;
    }

    /**
     * @see org.opensaml.saml2.core.IDPEntry#setLoc(java.lang.String)
     */
    public void setLoc(String newLoc) {
        this.loc = prepareForAssignment(this.loc, newLoc);

    }

    /**
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        // no children
        return null;
    }
}