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
package org.opensaml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.AttributeDesignator;
import org.opensaml.saml1.core.AttributeQuery;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of the {@link org.opensaml.saml1.core.AttributeQuery} interface
 */
public class AttributeQueryImpl extends SubjectQueryImpl implements AttributeQuery {

    /** Contains the resource attribute */
    private String resource;
    
    /** Contains all the child AttributeDesignators */ 
    private final List<AttributeDesignator> attributeDesignators;
    
    /**
     * Constructor
     */
    public AttributeQueryImpl() {
        super(SAMLConstants.SAML1P_NS, AttributeQuery.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML1P_PREFIX);
        attributeDesignators = new XMLObjectChildrenList<AttributeDesignator>(this); 
    }

    /*
     * @see org.opensaml.saml1.core.AttributeQuery#getResource()
     */
    public String getResource() {
        return resource;
    }

    /*
     * @see org.opensaml.saml1.core.AttributeQuery#setResource(java.lang.String)
     */
    public void setResource(String resource) {
        this.resource = prepareForAssignment(this.resource, resource);
    }

    /*
     * @see org.opensaml.saml1.core.AttributeQuery#getAttributeDesignators()
     */
    public List<AttributeDesignator> getAttributeDesignators() {
        return attributeDesignators;
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        List<SAMLObject> list = new ArrayList<SAMLObject>(attributeDesignators.size()+1);
        
        if (getSubject() != null) {
            list.add(getSubject());
        }
        list.addAll(attributeDesignators);
        return Collections.unmodifiableList(list);
    }
}
