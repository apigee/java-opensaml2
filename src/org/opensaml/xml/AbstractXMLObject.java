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

package org.opensaml.xml;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

/**
 * An abstract implementation of XMLObject.
 */
public abstract class AbstractXMLObject implements XMLObject {

    /** Parent of this element */
    private XMLObject parent;

    /** The name of this element with namespace and prefix information */
    private QName elementQname;

    /** The schema type of this element with namespace and prefix information */
    private QName typeQname;

    /** Namespaces declared on this element */
    private HashSet<Namespace> namespaces = new HashSet<Namespace>();

    /**
     * Constructor
     * 
     * @param namespaceURI the namespace the element is in
     * @param elementLocalName the local name of the XML element this Object represents
     */
    protected AbstractXMLObject(String namespaceURI, String elementLocalName) {
        elementQname = new QName(namespaceURI, elementLocalName);
    }
    
    /*
     * @see org.opensaml.common.SAMLObject#getQName()
     */
    public QName getElementQName() {
        return new QName(elementQname.getNamespaceURI(), elementQname.getLocalPart(), elementQname.getPrefix());
    }
    
    /*
     * @see org.opensaml.common.SAMLObject#setNamespaceAndPrefix(java.lang.String, java.lang.String)
     */
    public void setElementNamespacePrefix(String prefix) {
        if(prefix == null){
            elementQname = new QName(elementQname.getNamespaceURI(), elementQname.getLocalPart());
        }else{
            elementQname = new QName(elementQname.getNamespaceURI(), elementQname.getLocalPart(), prefix);
        }
    }
    
    /*
     * @see org.opensaml.common.SAMLObject#getNamespaces()
     */
    public Set<Namespace> getNamespaces() {
        return Collections.unmodifiableSet(namespaces);
    }

    /*
     * @see org.opensaml.common.SAMLObject#addNamespace(javax.xml.namespace.QName)
     */
    public void addNamespace(Namespace namespace) {
        if (namespace != null) {
            namespaces.add(namespace);
        }
    }

    /*
     * @see org.opensaml.common.SAMLObject#removeNamespace(javax.xml.namespace.QName)
     */
    public void removeNamespace(Namespace namespace) {
        namespaces.remove(namespace);
    }
    
    /*
     * @see org.opensaml.common.SAMLObject#getType()
     */
    public QName getSchemaType() {
        return typeQname;
    }

    /*
     * @see org.opensaml.common.SAMLObject#setType(javax.xml.namespace.QName)
     */
    public void setSchemaType(QName type) {
        if(type == null){
            if(typeQname != null){
                removeNamespace(new Namespace(typeQname.getNamespaceURI(), typeQname.getPrefix()));
            }
            typeQname = null;
        }else{
            typeQname = type;
            addNamespace(new Namespace(type.getNamespaceURI(), type.getPrefix()));
        }
    }

    /**
     * Gets the parent of this element
     * 
     * @return the parent of this element
     */
    public XMLObject getParent() {
        return parent;
    }

    /*
     * @see org.opensaml.common.SAMLObject#setParent(org.opensaml.common.SAMLObject)
     */
    public void setParent(XMLObject parent) {
        this.parent = parent;
    }

    /*
     * @see org.opensaml.common.SAMLObject#hasParent()
     */
    public boolean hasParent() {
        return getParent() != null;
    }
    
    /*
     * @see org.opensaml.xml.XMLObject#hasChildren()
     */
    public boolean hasChildren(){
        List<? extends XMLObject> children = getOrderedChildren();
        return (children != null && children.size() > 0);
    }
}