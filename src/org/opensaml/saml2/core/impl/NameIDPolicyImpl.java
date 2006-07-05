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

import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.saml2.core.NameIDPolicy;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSBooleanValue;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.NameIDPolicy}
 */
public class NameIDPolicyImpl extends AbstractSAMLObject implements NameIDPolicy {

    /** NameID Format URI */
    private String format;

    /** NameID Format URI */
    private String spNameQualifier;

    /** NameID Format URI */
    private XSBooleanValue allowCreate;

    /**
     * Constructor
     * 
     * @param namespaceURI
     * @param elementLocalName
     * @param namespacePrefix
     */
    protected NameIDPolicyImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    /**
     * @see org.opensaml.saml2.core.NameIDPolicy#getFormat()
     */
    public String getFormat() {
        return this.format;
    }

    /**
     * @see org.opensaml.saml2.core.NameIDPolicy#setFormat(java.lang.String)
     */
    public void setFormat(String newFormat) {
        this.format = prepareForAssignment(this.format, newFormat);

    }

    /**
     * @see org.opensaml.saml2.core.NameIDPolicy#getSPNameQualifier()
     */
    public String getSPNameQualifier() {
        return this.spNameQualifier;
    }

    /**
     * @see org.opensaml.saml2.core.NameIDPolicy#setSPNameQualifier(java.lang.String)
     */
    public void setSPNameQualifier(String newSPNameQualifier) {
        this.spNameQualifier = prepareForAssignment(this.spNameQualifier, newSPNameQualifier);

    }

    /**
     * @see org.opensaml.saml2.core.NameIDPolicy#getAllowCreate()
     */
    public XSBooleanValue getAllowCreate() {
        return this.allowCreate;
    }

    /**
     * {@inheritDoc}
     */
    public void setAllowCreate(XSBooleanValue newAllowCreate) {
        this.allowCreate = prepareForAssignment(this.allowCreate, newAllowCreate);

    }

    /**
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        // no children
        return null;
    }
}