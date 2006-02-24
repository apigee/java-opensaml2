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
import org.opensaml.saml2.core.NameIDMappingResponse;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.NameIDMappingResponse}
 */
public class NameIDMappingResponseImpl extends StatusResponseImpl implements NameIDMappingResponse {
    
    /** Identifier child element */
    private Identifier identifier;

    /**
     * Constructor
     *
     */
    public NameIDMappingResponseImpl() {
        super(SAMLConstants.SAML20P_NS, NameIDMappingResponse.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20P_PREFIX);
    }

    /**
     * @see org.opensaml.saml2.core.NameIDMappingResponse#getIdentifier()
     */
    public Identifier getIdentifier() {
        return this.identifier;
    }

    /**
     * @see org.opensaml.saml2.core.NameIDMappingResponse#setIdentifier(org.opensaml.saml2.core.Identifier)
     */
    public void setIdentifier(Identifier newIdentifier) {
        this.identifier = prepareForAssignment(this.identifier, newIdentifier);
    }

    /**
     * @see org.opensaml.saml2.core.impl.StatusResponseImpl#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        
        if (super.getOrderedChildren() != null)
            children.addAll(super.getOrderedChildren());
        
        if (identifier != null)
            children.add(identifier);
        
        if (children.size() == 0)
            return null;
        
        return Collections.unmodifiableList(children);
    }
    
    

}
