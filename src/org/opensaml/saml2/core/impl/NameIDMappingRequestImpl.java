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
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Identifier;
import org.opensaml.saml2.core.NameIDMappingRequest;
import org.opensaml.saml2.core.NameIDPolicy;

/**
 * A concrete implementation of {@link org.opensaml.saml2.core.NameIDMappingRequest}
 */
public class NameIDMappingRequestImpl extends RequestImpl implements NameIDMappingRequest {
    
    /** Identifier child element */
    private Identifier identifier;
    
    /** NameIDPolicy child element */
    private NameIDPolicy nameIDPolicy;

    /**
     * Constructor
     *
     */
    public NameIDMappingRequestImpl() {
        super(SAMLConstants.SAML20P_NS, NameIDMappingRequest.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20P_PREFIX);
    }

    /**
     * @see org.opensaml.saml2.core.NameIDMappingRequest#getIdentifier()
     */
    public Identifier getIdentifier() {
        return this.identifier;
    }

    /**
     * @see org.opensaml.saml2.core.NameIDMappingRequest#setIdentifier(org.opensaml.saml2.core.Identifier)
     */
    public void setIdentifier(Identifier newIdentifier) {
        this.identifier = prepareForAssignment(this.identifier, newIdentifier);
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
     * @see org.opensaml.saml2.core.impl.RequestImpl#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        
        if (super.getOrderedChildren() != null)
            children.addAll(super.getOrderedChildren());
        
        if (identifier != null)
            children.add(identifier);
        if (nameIDPolicy != null)
            children.add(nameIDPolicy);
        
        if (children.size() == 0)
            return null;
        
        return Collections.unmodifiableList(children);
    }

}
