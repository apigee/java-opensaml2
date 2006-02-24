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
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Response;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.Response}
 */
public class ResponseImpl extends StatusResponseImpl implements Response {
    
    //TODO may need more for EncryptedAssertion pending Chad's encryption implementation
    
    /** Assertion child elements */
    private XMLObjectChildrenList<Assertion> assertions;

    /**
     * Constructor
     *
     */
    public ResponseImpl() {
        super(SAMLConstants.SAML20P_NS, Response.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20P_PREFIX);
        
        assertions = new XMLObjectChildrenList<Assertion>(this);
    }

    /**
     * @see org.opensaml.saml2.core.Response#getAssertions()
     */
    public List<Assertion> getAssertions() {
        return this.assertions;
    }

    /**
     * @see org.opensaml.saml2.core.impl.StatusResponseImpl#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        
        if (super.getOrderedChildren() != null)
            children.addAll(super.getOrderedChildren());
        
        children.addAll(assertions);
        
        if (children.size() == 0)
            return null;
        
        return Collections.unmodifiableList(children);
    }

}
