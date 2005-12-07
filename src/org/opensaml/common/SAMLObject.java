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
package org.opensaml.common;

import java.io.Serializable;
import java.util.Set;

import javax.xml.namespace.QName;

import org.opensaml.common.util.xml.Namespace;

/**
 * A base interface for all SAML elements.
 */
public interface SAMLObject extends Serializable {

    /**
     * Gets the SAML version for this object.
     * 
     * @return the SAML version for this object
     */
    public SAMLVersion getVersion();
    
	/**
	 * Gets the QName for this element.  This QName <strong>MUST</strong> 
	 * contain the namespace URI, namespace prefix, and local element name.
     * Changes made to the returned QName are not reflected by the QName held 
     * by this element, that is, the returned QName is a copy of the internal 
     * QName member of this class.
	 * 
	 * @return the QName for this attribute
	 */
    public QName getElementQName();
    
    /**
     * Sets the namespace URI and namespace prefix for this element.
     * 
     * @param namespaceURI the namespace URI for this element
     * @param prefix the prefix for this element
     */
    public void setElementNamespaceAndPrefix(String namespaceURI, String prefix);
    
    /**
     * Gets the namespaces, represented by QNames, that are scoped to this element
     * 
     * @return the namespaces that are scoped to this element
     */
    public Set<Namespace> getNamespaces();
    
    /**
     * Adds a namespace to the ones already scoped to this element
     * 
     * @param namespace the namespace to add
     */
    public void addNamespace(Namespace namespace);
    
    /**
     * Removes a namespace from this element
     * 
     * @param namespace the namespace to remove
     */
    public void removeNamespace(Namespace namespace);
    
    /**
     * Gets the XML schema type of this element.  This translates to contents the xsi:type
     * attribute for the element.
     * 
     * @return XML schema type of this element
     */
    public QName getSchemaType();
    
    /**
     * Sets the XML schema type of this element.  This translates to contents the xsi:type
     * attribute for the element.
     * 
     * @param type XML schema type of this element
     */
    public void setSchemaType(QName type);
    
    /**
     * Checks to see if this object has a parent.
     * 
     * @return true if the object has a parent, false if not
     */
    public boolean hasParent();
    
    /**
     * Gets the parent of this element or null if there is no parent.
     * 
     * @return the parent of this element or null
     */
    public SAMLObject getParent();
    
    /**
     * Sets the parent of this element.
     * 
     * @param parent the parent of this element
     */
    public void setParent(SAMLObject parent);
    
    /**
     * Gets a list of child elements in the order that they will appear in the DOM.
     * 
     * @return ordered list of child elements
     */
    public Set<SAMLObject> getOrderedChildren();
    
    /**
     * Checks to see if the given element is equal to this one.  What it means for 
     * one element to be equal to another is left up to the implementor and will vary
     * from element to element.
     *  
     * @param element the other element compared to this one
     * 
     * @return true if the given element is the same as this one, false if not
     */
    public boolean equals(SAMLObject element);
}
