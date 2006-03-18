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
import org.opensaml.saml1.core.Attribute;
import org.opensaml.saml1.core.AttributeValue;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * A Concrete implementation of the {@link org.opensaml.saml1.core.Attribute} Interface
 */
public class AttributeImpl extends AttributeDesignatorImpl implements Attribute {

    /** Contains the AttributeValues */
    private final List<AttributeValue> attributeValues;

    /**
     * Constructor
     * @deprecated
     */
    private AttributeImpl() {
        super(Attribute.LOCAL_NAME, null);

        attributeValues = null;
    }
   
    /**
     * Constructor
     *
     * @param version the version it is being build
     */
    protected AttributeImpl(SAMLVersion version) {
        super(Attribute.LOCAL_NAME, version);

        attributeValues = new XMLObjectChildrenList<AttributeValue>(this);
    }
    

    /*
     * @see org.opensaml.saml1.core.Attribute#getAttributeValues()
     */
    public List<AttributeValue> getAttributeValues() {
        return attributeValues;
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {

        if (attributeValues.size() == 0) {
            return null;
        }

        List<XMLObject> list = new ArrayList<XMLObject>(attributeValues.size() + 1);
        list.addAll(attributeValues);
        return Collections.unmodifiableList(list);
    }
}