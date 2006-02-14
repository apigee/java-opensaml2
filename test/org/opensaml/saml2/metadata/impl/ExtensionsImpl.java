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

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Extensions;

/**
 * Test implementation of Extensions for the prupooises 
 */
public class ExtensionsImpl extends AbstractSAMLObject implements Extensions {

    /**
     * Constructor
     */
    public ExtensionsImpl() {
        super(SAMLConstants.SAML20MD_NS, Extensions.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20MD_PREFIX);
    }

    /*
     * @see org.opensaml.saml2.core.Extensions#getExtensionElements()
     */
    public List<SAMLObject> getExtensionElements() {
        return new ArrayList<SAMLObject>();
    }

    /*
     * @see org.opensaml.saml2.core.Extensions#getExtensionElements(javax.xml.namespace.QName)
     */
    public List<SAMLObject> getExtensionElements(QName elementName) {
        return new ArrayList<SAMLObject>();
    }

    /*
     * @see org.opensaml.saml2.core.Extensions#getExtensionElement(javax.xml.namespace.QName)
     */
    public SAMLObject getExtensionElement(QName elementName) {
        return null;
    }

    /*
     * @see org.opensaml.saml2.core.Extensions#addExtensionElement(org.opensaml.common.SAMLObject)
     */
    public void addExtensionElement(SAMLObject elment) {
    }

    /*
     * @see org.opensaml.saml2.core.Extensions#replaceExtensionElement(org.opensaml.common.SAMLObject)
     */
    public void replaceExtensionElement(SAMLObject element) {
    }

    /*
     * @see org.opensaml.saml2.core.Extensions#removeExtensionElements()
     */
    public void removeExtensionElements() {
    }

    /*
     * @see org.opensaml.saml2.core.Extensions#removeExtensionElements(javax.xml.namespace.QName)
     */
    public void removeExtensionElements(QName elementName) {
    }

    /*
     * @see org.opensaml.saml2.core.Extensions#removeExtensionElement(org.opensaml.common.SAMLObject)
     */
    public void removeExtensionElement(SAMLObject element) {
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        return new ArrayList<SAMLObject>();
    }
}
