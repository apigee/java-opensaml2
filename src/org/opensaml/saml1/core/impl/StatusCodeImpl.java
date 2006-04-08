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

import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.saml1.core.StatusCode;
import org.opensaml.xml.XMLObject;

/**
 * Concrete implementation of {@link org.opensaml.saml1.core.StatusCode} Object
 */
public class StatusCodeImpl extends AbstractSAMLObject implements StatusCode {

    /** Contents of the Value attribute */
    private String value;

    /** The child StatusCode sub element */
    private StatusCode childStatusCode;

    /**
     * Constructor
     * 
     * @param namespaceURI the namespace the element is in
     * @param elementLocalName the local name of the XML element this Object represents
     * @param namespacePrefix the prefix for the given namespace
     */
    protected StatusCodeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    /*
     * @see org.opensaml.saml1.core.StatusCode#getValue()
     */
    public String getValue() {
        return value;
    }

    /*
     * @see org.opensaml.saml1.core.StatusCode#setValue(java.lang.String)
     */
    public void setValue(String value) {
        this.value = prepareForAssignment(this.value, value);
    }

    /*
     * @see org.opensaml.saml1.core.StatusCode#getStatusCode()
     */
    public StatusCode getStatusCode() {
        return childStatusCode;
    }

    /*
     * @see org.opensaml.saml1.core.StatusCode#setStatusCode(org.opensaml.saml1.core.StatusCode)
     */
    public void setStatusCode(StatusCode statusCode) throws IllegalArgumentException {
        childStatusCode = prepareForAssignment(childStatusCode, statusCode);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        if (childStatusCode != null) {
            ArrayList<XMLObject> contents = new ArrayList<XMLObject>(1);
            contents.add(childStatusCode);
            return Collections.unmodifiableList(contents);
        } else {
            return null;
        }
    }
}