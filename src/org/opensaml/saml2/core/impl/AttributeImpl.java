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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeValue;
import org.opensaml.xml.IllegalAddException;

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
    private List<AttributeValue> attributeValues = new ArrayList<AttributeValue>();

    /**
     * Constructor
     */
    public AttributeImpl() {
        super(SAMLConstants.SAML20_NS, Attribute.LOCAL_NAME);
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
    public List<AttributeValue> getAttributeValues() {
        return Collections.unmodifiableList(attributeValues);
    }

    /*
     * @see org.opensaml.saml2.core.Attribute#addAttributeValue(org.opensaml.saml2.core.AttributeValue)
     */
    public void addAttributeValue(AttributeValue value) throws IllegalAddException {
        addXMLObject(attributeValues, value);

    }

    /*
     * @see org.opensaml.saml2.core.Attribute#removeAttributeValue(org.opensaml.saml2.core.AttributeValue)
     */
    public void removeAttributeValue(AttributeValue value) {
        removeXMLObject(attributeValues, value);

    }

    /*
     * @see org.opensaml.saml2.core.Attribute#removeAttributeValues(java.util.Collection)
     */
    public void removeAttributeValues(Collection<AttributeValue> values) {
        removeXMLObjects(attributeValues, values);

    }

    /*
     * @see org.opensaml.saml2.core.Attribute#removeAllAttributeValues()
     */
    public void removeAllAttributeValues() {
        for (AttributeValue value : attributeValues) {
            removeAttributeValue(value);
        }
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();

        children.addAll(attributeValues);

        return Collections.unmodifiableList(children);
    }
}