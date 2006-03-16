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

package org.opensaml.saml2.metadata.impl;

import java.util.List;

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.impl.AttributeImpl;
import org.opensaml.saml2.metadata.RequestedAttribute;
import org.opensaml.xml.XMLObject;

/**
 * Concrete implementation of {@link org.opensaml.saml2.metadata.RequestedAttribute}
 */
public class RequestedAttributeImpl extends AttributeImpl implements RequestedAttribute {

    /** isRequired attribute */
    private boolean isRequired;

    /**
     * Constructor
     */
    protected RequestedAttributeImpl() {
        super(SAMLConstants.SAML20MD_NS, RequestedAttribute.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setIsRequired(boolean isRequired) {
        this.isRequired = prepareForAssignment(this.isRequired, isRequired);

    }

    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}