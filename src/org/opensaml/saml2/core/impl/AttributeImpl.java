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

import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.Attribute}
 */
public class AttributeImpl extends AbstractSAMLObject implements Attribute {

    /** Name of the attribute */
    private String name;

    /** Format of the name of th attribute */
    private String nameFormat;

    /** Human readable name of the attribute */
    private String friendlyName;

    /** List of attribute values for this attribute */
    private final XMLObjectChildrenList<XMLObject> attributeValues;

    /**
     * Constructor
     * 
     * @param namespaceURI
     * @param elementLocalName
     * @param namespacePrefix
     */
    protected AttributeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        attributeValues = new XMLObjectChildrenList<XMLObject>(this);
    }

    /*
     * @see org.opensaml.saml2.core.Attribute#getName()
     */
    public String getName() {
        return name;
    }

    /*
     * @see org.opensaml.saml2.core.Attribute#setName(java.lang.String)
     */
    public void setName(String name) {
        this.name = prepareForAssignment(this.name, name);
    }

    /*
     * @see org.opensaml.saml2.core.Attribute#getNameFormat()
     */
    public String getNameFormat() {
        return nameFormat;
    }

    /*
     * @see org.opensaml.saml2.core.Attribute#setNameFormat(java.lang.String)
     */
    public void setNameFormat(String nameFormat) {
        this.nameFormat = prepareForAssignment(this.nameFormat, nameFormat);
    }

    /*
     * @see org.opensaml.saml2.core.Attribute#getFriendlyName()
     */
    public String getFriendlyName() {
        return friendlyName;
    }

    /*
     * @see org.opensaml.saml2.core.Attribute#setFriendlyName(java.lang.String)
     */
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = prepareForAssignment(this.friendlyName, friendlyName);
    }

    /*
     * @see org.opensaml.saml2.core.Attribute#getAttributeValues()
     */
    public List<XMLObject> getAttributeValues() {
        return attributeValues;
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

        children.addAll(attributeValues);

        return Collections.unmodifiableList(children);
    }
}