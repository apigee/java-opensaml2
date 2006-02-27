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

import org.opensaml.saml2.core.AttributeValue;
import org.opensaml.xml.XMLObject;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.AttributeValue}
 */
public class AttributeValueImpl extends AbstractAssertionSAMLObject implements AttributeValue {

    /** Name of the attribute */
    private String attributeValue;

    /** Constructor */
    protected AttributeValueImpl() {
        super(AttributeValue.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.saml2.core.AttributeValue#getValue()
     */
    public String getValue() {
        return attributeValue;
    }

    /*
     * @see org.opensaml.saml2.core.AttributeValue#setValue(java.lang.String)
     */
    public void setValue(String newValue) {
        this.attributeValue = prepareForAssignment(this.attributeValue, newValue);
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}