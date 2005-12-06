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

package org.opensaml.common.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.ValidatingObject;
import org.opensaml.common.ValidationException;
import org.opensaml.common.Validator;
import org.opensaml.common.util.NamespaceComparator;
import org.w3c.dom.Element;

/**
 * An abstract implementation of SAMLElement.
 */
public abstract class AbstractSAMLObject implements DOMCachingSAMLObject, ValidatingObject{

    /** SAML version of this object */
    private SAMLVersion version;
    
    /** DOM Element representation of this object */
    private Element dom;
    
    /** Parent of this element */
    private SAMLObject parent;
    
    /** The name of this element with namespace and prefix information */
    private QName elementQname;
    
    /** The schema type of this element with namespace and prefix information */
    private QName typeQname;
    
    /** Namespaces declared on this element */
    private Set<QName> namespaces = new TreeSet<QName>(new NamespaceComparator());
    
    private ValidatingSAMLObjectHelper validationHelper;
    
    /**
     * Constructor
     */
    protected AbstractSAMLObject() {
        validationHelper = new ValidatingSAMLObjectHelper();
    }
    
    /*
     * @see org.opensaml.common.SAMLObject#getVersion()
     */
    public SAMLVersion getVersion() {
        return version;
    }
    
    /**
     * Sets the SAML version for this object.
     * 
     * @param version the SAML version for this object
     */
    protected void setSAMLVersion(SAMLVersion version) {
        this.version = version;
    }
    
    /**
     * Gets the parent of this element
     * 
     * @return the parent of this element
     */
    public SAMLObject getParent(){
        return parent;
    }
    
    /*
     * @see org.opensaml.common.SAMLObject#getQName()
     */
    public QName getElementQName(){
        return new QName(elementQname.getNamespaceURI(), elementQname.getLocalPart(), elementQname.getPrefix());
    }
    
    /**
     * Sets the QName for this element.
     * 
     * @param qname the qname for this element
     */
    protected void setQName(QName qname){
        this.elementQname = qname;
    }
    
    /*
     * @see org.opensaml.common.SAMLObject#setNamespaceAndPrefix(java.lang.String, java.lang.String)
     */
    public void setElementNamespaceAndPrefix(String namespaceURI, String prefix){
        elementQname = new QName(namespaceURI, elementQname.getLocalPart(), prefix);
    }
    
    /*
     * @see org.opensaml.common.SAMLObject#getType()
     */
    public QName getSchemaType(){
        return typeQname;
    }
    
    /*
     * @see org.opensaml.common.SAMLObject#setType(javax.xml.namespace.QName)
     */
    public void setSchemaType(QName type){
        typeQname = type;
    }
    
    /*
     * @see org.opensaml.common.SAMLObject#getNamespaces()
     */
    public Set<QName> getNamespaces() {
        return Collections.unmodifiableSet(namespaces);
    }
    
    /*
     * @see org.opensaml.common.SAMLObject#addNamespace(javax.xml.namespace.QName)
     */
    public void addNamespace(QName namespace) {
        namespaces.add(namespace);
    }
    
    /*
     * @see org.opensaml.common.SAMLObject#removeNamespace(javax.xml.namespace.QName)
     */
    public void removeNamespace(QName namespace) {
        namespaces.remove(namespace);
    }
    
    /*
     * @see org.opensaml.common.SAMLObject#setParent(org.opensaml.common.SAMLObject)
     */
    public void setParent(SAMLObject parent){
        this.parent = parent;
    }

    /*
     * @see org.opensaml.common.impl.DOMBackedSAMLObject#getDOM()
     */
    public Element getDOM() {
        return dom;
    }
    
    /*
     * @see org.opensaml.common.impl.DOMBackedSAMLObject#setDOM(org.w3c.dom.Element)
     */
    public void setDOM(Element dom){
        this.dom = dom;
    }
    
    /*
     * @see org.opensaml.common.SAMLElement#releaseDOM()
     */
    public void releaseDOM(){
        setDOM(null);
    }
    
    /*
     * @see org.opensaml.common.SAMLElement#releaseParentDOM(boolean)
     */
    public void releaseParentDOM(boolean propagateRelease) {
        SAMLObject parentElement = getParent();
        if(parentElement != null && parentElement instanceof DOMCachingSAMLObject) {
            ((DOMCachingSAMLObject)parentElement).releaseDOM();
            if(propagateRelease) {
                ((DOMCachingSAMLObject)parentElement).releaseParentDOM(propagateRelease);
            }
        }
    }
    
    /*
     * @see org.opensaml.common.SAMLElement#releaseChildrenDOM(boolean)
     */
    public void releaseChildrenDOM(boolean propagateRelease) {
        for(SAMLObject childElement : getOrderedChildren()) {
            ((DOMCachingSAMLObject)childElement).releaseDOM();
            if(propagateRelease) {
                ((DOMCachingSAMLObject)childElement).releaseChildrenDOM(propagateRelease);
            }
        }
    }
    
    /**
     * A convience method that is equal to calling {@link #releaseDOM()} then 
     * {@link #releaseParentDOM(boolean)} with the release being propogated.
     */
    public void releaseThisandParentDOM() {
        if(getDOM() != null) {
            releaseDOM();
            releaseParentDOM(true);
        }
    }
    
    /**
     * A convience method that is equal to calling {@link #releaseDOM()} then 
     * {@link #releaseChildrenDOM(boolean)} with the release being propogated.
     */
    public void releaseThisAndChildreanDOM(){
        if(getDOM() != null){
            releaseDOM();
            releaseChildrenDOM(true);
        }
    }
    
    /*
     * @see org.opensaml.common.SAMLObject#hasParent()
     */
    public boolean hasParent(){
        return getParent() != null;
    }
    
    /*
     * @see org.opensaml.common.ValidatingElement#getValidators()
     */
    public List<Validator> getValidators(){
        return validationHelper.getValidators();
    }
    
    /*
     * @see org.opensaml.common.ValidatingElement#registerValidator(Validator)
     */
    public void registerValidator(Validator validator){
        validationHelper.registerValidator(validator);
    }
    
    /*
     * @see org.opensaml.common.ValidatingElement#deregisterValidator(Validator)
     */
    public void deregisterValidator(Validator validator){
        validationHelper.deregisterValidator(validator);
    }
    
    /*
     * @see org.opensaml.common.ValidatingElement#validateElement(boolean)
     */
    public void validateElement(boolean validateChildren) throws ValidationException{
        validationHelper.validateElement(this, validateChildren);
    }
}