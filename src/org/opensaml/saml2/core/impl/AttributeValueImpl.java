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

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.AttributeValue;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.AttributeValue}
 */
public class AttributeValueImpl extends AbstractSAMLObject implements AttributeValue {

    /** Name of the attribute */
    private String attributeValue;

    /** Constructor */
    public AttributeValueImpl() {
        super(SAMLConstants.SAML20_NS, AttributeValue.LOCAL_NAME);
    }

    /**
     * @see org.opensaml.saml2.core.AttributeValue#getValue()
     */
    public String getValue() {
        return attributeValue;
    }

    /**
     * @see org.opensaml.saml2.core.AttributeValue#setValue(java.lang.String)
     */
    public void setValue(String newValue) {
        this.attributeValue = prepareForAssignment(this.attributeValue, newValue);
    }

    /**
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        return null;
    }
}