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

package org.opensaml.saml2.common.impl;

import java.io.Serializable;
import java.util.Set;

import javax.xml.namespace.QName;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.saml2.metadata.Extensions;

/**
 * A helper for SAMLElements that implement the {@link org.opensaml.saml2.common.ExtensionsExtensibleSAMLObject} interface.
 * This helper is <strong>NOT</strong> thread safe.
 */
public class ExtensionsSAMLObjectHelper implements Serializable{

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -2407257469201861011L;

    /**
     * The Extensions element
     */
    private Extensions extensions;
    
    /**
     * The SAMLELement that contains this helper
     */
    private AbstractSAMLObject containingElement;
    
    /**
     * 
     * Constructor
     *
     */
    public ExtensionsSAMLObjectHelper(AbstractSAMLObject containingElement){
        this.containingElement = containingElement;
    }
    
    /*
     * @see org.opensaml.saml2.common.ExtensionsExtensibleElement#getExtensions()
     */
    public Extensions getExtensions() {
        return extensions;
    }

    /*
     * @see org.opensaml.saml2.common.ExtensionsExtensibleElement#getExtensionElements()
     */
    public Set<SAMLObject> getExtensionElements() {
        if(extensions == null){
            return null;
        }
        
        return extensions.getExtensionElements();
    }

    /*
     * @see org.opensaml.saml2.common.ExtensionsExtensibleElement#getExtensionElements(javax.xml.namespace.QName)
     */
    public Set<SAMLObject> getExtensionElements(QName elementName) {
        if(extensions == null){
            return null;
        }
        
        return extensions.getExtensionElements(elementName);
    }

    /*
     * @see org.opensaml.saml2.common.ExtensionsExtensibleElement#getExtensionElement(javax.xml.namespace.QName)
     */
    public SAMLObject getExtensionElement(QName elementName) {
        if(extensions == null){
            return null;
        }
        
        return extensions.getExtensionElement(elementName);
    }

    /**
     * Sets the Extension element for the containing SAMLElement.
     * 
     * @param extensions the Extension element, MUST be a subtype of {@link AbstractSAMLObject}
     * 
     * @throws IllegalAddException thrown if the given Extensions is already a descendant of another SAMLElement
     */
    public void setExtensions(Extensions extensions) throws IllegalAddException{
        if(extensions.getParent() != null) {
            if(extensions.getParent() != containingElement) {
                throw new IllegalAddException("The given Extensions element is already a descendant of another SAMLElement");
            }
        }
        
        containingElement.releaseThisandParentDOM();
        this.extensions = extensions;
        (((AbstractSAMLObject)extensions)).setParent(containingElement);
    }
}
