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

import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.AttributeValue;

/**
 * Concrete Implementation of {@link org.opensaml.saml1.core.AttributeValue} Object
 */
public class AttributeValueImpl extends AbstractSAMLObject implements AttributeValue {

    /** String to contain the contents */
    String attributeValue;
    
    /**
     * Constructor
     */
    public AttributeValueImpl() {
        super(SAMLConstants.SAML1_NS, AttributeValue.LOCAL_NAME);
            setElementNamespacePrefix(SAMLConstants.SAML1_PREFIX);
    }

    /*
     * @see org.opensaml.saml1.core.AttributeValue#getAttributeValue()
     */
    public String getAttributeValue() {
        return attributeValue;
    }

    /*
     * @see org.opensaml.saml1.core.AttributeValue#setAttributeValue(java.lang.String)
     */
    public void setAttributeValue(String attributeValue) {
        this.attributeValue = prepareForAssignment(this.attributeValue, attributeValue);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        // No children
        return null;
    }
}
