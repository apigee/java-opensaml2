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
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.StatusCode;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.StatusCode}
 */
public class StatusCodeImpl extends AbstractSAMLObject implements StatusCode {
    
    /** Value attribute URI*/
    private String value;
    
    /** Nested secondary StatusCode child element*/
    private StatusCode childStatusCode;

    /**
     * Constructor
     *
     */
    public StatusCodeImpl() {
        super(SAMLConstants.SAML20P_NS, StatusCode.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20P_PREFIX);
    }

    /**
     * @see org.opensaml.saml2.core.StatusCode#getStatusCode()
     */
    public StatusCode getStatusCode() {
        return childStatusCode;
    }

    /**
     * @see org.opensaml.saml2.core.StatusCode#setStatusCode(org.opensaml.saml2.core.StatusCode)
     */
    public void setStatusCode(StatusCode newStatusCode) {
        this.childStatusCode = prepareForAssignment(this.childStatusCode, newStatusCode);
    }

    /**
     * @see org.opensaml.saml2.core.StatusCode#getValue()
     */
    public String getValue() {
        return value;
    }

    /**
     * @see org.opensaml.saml2.core.StatusCode#setValue(java.lang.String)
     */
    public void setValue(String newValue) {
        this.value = prepareForAssignment(this.value, newValue);
    }

    /**
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        if (childStatusCode != null) {
            ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
            children.add(childStatusCode);
            return Collections.unmodifiableList(children);
        } else {
            return null;
        }  
    }
}