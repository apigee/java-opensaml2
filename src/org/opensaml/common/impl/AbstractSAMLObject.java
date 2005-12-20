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

import java.util.Collection;
import java.util.List;

import javax.xml.namespace.QName;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.ValidatingObject;
import org.opensaml.common.ValidationException;
import org.opensaml.common.Validator;
import org.opensaml.common.util.OrderedSet;
import org.opensaml.common.util.StringHelper;
import org.opensaml.common.util.UnmodifiableOrderedSet;
import org.opensaml.common.util.xml.Namespace;
import org.w3c.dom.Element;

/**
 * An abstract implementation of SAMLElement.
 */
public abstract class AbstractSAMLObject implements DOMCachingSAMLObject, ValidatingObject {

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
    private OrderedSet<Namespace> namespaces = new OrderedSet<Namespace>();

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
    public SAMLObject getParent() {
        return parent;
    }

    /*
     * @see org.opensaml.common.SAMLObject#getQName()
     */
    public QName getElementQName() {
        return new QName(elementQname.getNamespaceURI(), elementQname.getLocalPart(), elementQname.getPrefix());
    }

    /**
     * Sets the QName for this element.
     * 
     * @param qname the qname for this element
     */
    protected void setQName(QName qname) {
        this.elementQname = qname;
    }

    /*
     * @see org.opensaml.common.SAMLObject#setNamespaceAndPrefix(java.lang.String, java.lang.String)
     */
    public void setElementNamespaceAndPrefix(String namespaceURI, String prefix) {
        if (StringHelper.isEmpty(prefix)) {
            elementQname = new QName(namespaceURI, elementQname.getLocalPart());
        } else {
            elementQname = new QName(namespaceURI, elementQname.getLocalPart(), prefix);
        }
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
        typeQname = type;
    }

    /*
     * @see org.opensaml.common.SAMLObject#getNamespaces()
     */
    public UnmodifiableOrderedSet<Namespace> getNamespaces() {
        return new UnmodifiableOrderedSet<Namespace>(namespaces);
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
     * @see org.opensaml.common.SAMLObject#setParent(org.opensaml.common.SAMLObject)
     */
    public void setParent(SAMLObject parent) {
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
    public void setDOM(Element dom) {
        this.dom = dom;
    }

    /*
     * @see org.opensaml.common.SAMLElement#releaseDOM()
     */
    public void releaseDOM() {
        setDOM(null);
    }

    /*
     * @see org.opensaml.common.SAMLElement#releaseParentDOM(boolean)
     */
    public void releaseParentDOM(boolean propagateRelease) {
        SAMLObject parentElement = getParent();
        if (parentElement != null && parentElement instanceof DOMCachingSAMLObject) {
            ((DOMCachingSAMLObject) parentElement).releaseDOM();
            if (propagateRelease) {
                ((DOMCachingSAMLObject) parentElement).releaseParentDOM(propagateRelease);
            }
        }
    }

    /*
     * @see org.opensaml.common.SAMLElement#releaseChildrenDOM(boolean)
     */
    public void releaseChildrenDOM(boolean propagateRelease) {

        if (getOrderedChildren() != null) {
            for (SAMLObject childElement : getOrderedChildren()) {
                ((DOMCachingSAMLObject) childElement).releaseDOM();
                if (propagateRelease) {
                    ((DOMCachingSAMLObject) childElement).releaseChildrenDOM(propagateRelease);
                }
            }
        }
    }

    /**
     * A convience method that is equal to calling {@link #releaseDOM()} then {@link #releaseParentDOM(boolean)} with
     * the release being propogated.
     */
    public void releaseThisandParentDOM() {
        if (getDOM() != null) {
            releaseDOM();
            releaseParentDOM(true);
        }
    }

    /**
     * A convience method that is equal to calling {@link #releaseDOM()} then {@link #releaseChildrenDOM(boolean)} with
     * the release being propogated.
     */
    public void releaseThisAndChildrenDOM() {
        if (getDOM() != null) {
            releaseDOM();
            releaseChildrenDOM(true);
        }
    }

    /*
     * @see org.opensaml.common.SAMLObject#hasParent()
     */
    public boolean hasParent() {
        return getParent() != null;
    }

    /*
     * @see org.opensaml.common.ValidatingElement#getValidators()
     */
    public List<Validator> getValidators() {
        return validationHelper.getValidators();
    }

    /*
     * @see org.opensaml.common.ValidatingElement#registerValidator(Validator)
     */
    public void registerValidator(Validator validator) {
        validationHelper.registerValidator(validator);
    }

    /*
     * @see org.opensaml.common.ValidatingElement#deregisterValidator(Validator)
     */
    public void deregisterValidator(Validator validator) {
        validationHelper.deregisterValidator(validator);
    }

    /*
     * @see org.opensaml.common.ValidatingElement#validateElement(boolean)
     */
    public void validateElement(boolean validateChildren) throws ValidationException {
        validationHelper.validateElement(this, validateChildren);
    }

    /**
     * A helper function for derived classes. This 'nornmalizes' newString and then if it is different from oldString
     * invalidates the DOM. It returns the normalized value so subclasses just have to go. this.foo =
     * assignString(this.foo, foo);
     * 
     * @param oldValue - the current value
     * @param newString - the new value
     * @return
     */
    protected String prepareForAssignment(String oldValue, String newValue) {
        String newString = StringHelper.safeTrimOrNullString(newValue);

        if (!StringHelper.safeEquals(oldValue, newString)) {

            releaseThisandParentDOM();
        }

        return newString;
    }

    /**
     * A helper function for derived classes, similar to assignString, but for (singleton) SAML objects. It is
     * indifferent to whether either the old or the new version of the value is null. Derived classes are expected to
     * use this thus: <code>
     *   this.foo = prepareForAssignment(this.foo, foo);
     *   </code>
     * 
     * This method will do a (null) safe compare of the objects and will also invalidate the DOM if appropriate
     * 
     * @param oldvalue - current value
     * @param newValue - proposed new value
     * @return The value to assign to the saved Object.
     * 
     * @throws IllegalAddException if the child already has a parent.
     */
    protected <T extends SAMLObject> T prepareForAssignment(T oldValue, T newValue) throws IllegalAddException {

        if (newValue != null && newValue.hasParent()) {

            throw new IllegalAddException(newValue.getClass().getName()
                    + " cannot be added - it is already the child of another SAML Object");
        }

        if (oldValue == null) {

            if (newValue != null) {

                releaseThisandParentDOM();
                newValue.setParent(this);
                return newValue;

            } else {

                return null;
            }
        }

        if (!oldValue.equals(newValue)) {

            oldValue.setParent(null);
            releaseThisandParentDOM();
            newValue.setParent(this);
        }

        return newValue;
    }

    /**
     * This is a helper function for sets which support multiple sub elements. It does all the standard parameter
     * checking before adding the new member into the Set and invaldating the DOM (iff required)
     * 
     * @param <T> The type of the SAMLObject being added
     * @param set The set to which the SAMLObject is to be added.
     * @param samlObject The object to add
     * 
     * @throws IllegalAddException if the element already has a parent
     */
    protected <T extends SAMLObject> void addSAMLObject(OrderedSet<T> set, T samlObject) throws IllegalAddException {
        if (!set.contains(samlObject)) {

            if (samlObject.hasParent()) {
                throw new IllegalAddException(samlObject.getClass().getName()
                        + " cannot be added - it is already the child of another SAML Object");
            }
            samlObject.setParent(this);
            releaseThisandParentDOM();
            set.add(samlObject);
        }
    }

    /**
     * This helper function is the inverse of addSAMLObject. Again it does all the required checking processing of the
     * element being removed.
     * 
     * @param <T> The type of the SAMLObject being removed
     * @param set The set from which the SAMLObject is to be removed.
     * @param samlObject The object to remove
     */
    protected <T extends SAMLObject> void removeSAMLObject(OrderedSet<T> set, T samlObject) {
        if (samlObject != null && set.contains(samlObject)) {
            samlObject.setParent(null);
            releaseThisandParentDOM();
            set.remove(samlObject);
        }
    }

    /**
     * This helper function removes a series of SAMLObjects from the containing set. Again it is targetted at
     * SAMLObjects which multipl subelements.
     * 
     * @param <T> The type of the SAMLObject being removed
     * @param set The set from which the SAMLObject is to be removed.
     * @param samlObject The set of SAMLObject to be removed.
     * 
     * NOTE This class need not be copied into any superclasses so long as they implement removeSAMLObject if needed.
     * 
     */
    protected <T extends SAMLObject> void removeSAMLObjects(OrderedSet<T> containingSet, Collection<T> contentsSet) {
        for (T member : contentsSet) {
            removeSAMLObject(containingSet, member);
        }
    }
}