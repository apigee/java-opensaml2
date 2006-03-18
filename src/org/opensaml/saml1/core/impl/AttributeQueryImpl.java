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

package org.opensaml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.common.SAMLVersion;
import org.opensaml.saml1.core.AttributeDesignator;
import org.opensaml.saml1.core.AttributeQuery;
import org.opensaml.xml.XMLObject;
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
     * Hidden Constructor
     * @deprecated
     */
    private AttributeQueryImpl() {
        super(AttributeQuery.LOCAL_NAME, null);

        attributeDesignators = null;
    }

    protected AttributeQueryImpl(SAMLVersion version) {
        super(AttributeQuery.LOCAL_NAME, version);

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
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        List<XMLObject> list = new ArrayList<XMLObject>(attributeDesignators.size() + 1);
        
        if (super.getOrderedChildren() != null) {
            list.addAll(super.getOrderedChildren());
        }
        
        list.addAll(attributeDesignators);
        return Collections.unmodifiableList(list);
    }
}