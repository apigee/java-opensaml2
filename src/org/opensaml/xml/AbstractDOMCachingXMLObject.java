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

package org.opensaml.xml;

import org.apache.log4j.Logger;
import org.opensaml.xml.util.DatatypeHelper;
import org.w3c.dom.Element;

/**
 * Extension of {@link org.opensaml.xml.AbstractXMLObject} that implements {@link org.opensaml.xml.DOMCachingXMLObject}.
 */
public abstract class AbstractDOMCachingXMLObject extends AbstractXMLObject implements DOMCachingXMLObject {
    
    /** Logger */
    private final Logger log = Logger.getLogger(AbstractDOMCachingXMLObject.class);

    /** DOM Element representation of this object */
    private Element dom;

    /**
     * Constructor
     * 
     * @param namespaceURI the namespace the element is in
     * @param elementLocalName the local name of the XML element this Object represents
     */
    protected AbstractDOMCachingXMLObject(String namespaceURI, String elementLocalName) {
        super(namespaceURI, elementLocalName);
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
        if(log.isDebugEnabled()){
            log.debug("Releasing cached DOM reprsentation for " + getElementQName());
        }
        
        setDOM(null);
    }

    /*
     * @see org.opensaml.common.SAMLElement#releaseParentDOM(boolean)
     */
    public void releaseParentDOM(boolean propagateRelease) {
        if(log.isDebugEnabled()){
            log.debug("Releasing cached DOM reprsentation for parent of " + getElementQName() + " with propagation set to " + propagateRelease);
        }

        XMLObject parentElement = getParent();
        if (parentElement != null && parentElement instanceof DOMCachingXMLObject) {
            DOMCachingXMLObject domCachingParent = (DOMCachingXMLObject) parentElement;
            domCachingParent.releaseDOM();
            if (propagateRelease) {
                domCachingParent.releaseParentDOM(propagateRelease);
            }
        }
    }

    /*
     * @see org.opensaml.common.SAMLElement#releaseChildrenDOM(boolean)
     */
    public void releaseChildrenDOM(boolean propagateRelease) {
        if(log.isDebugEnabled()){
            log.debug("Releasing cached DOM reprsentation for children of " + getElementQName() + " with propagation set to " + propagateRelease);
        }
        
        if (getOrderedChildren() != null) {
            for (XMLObject childElement : getOrderedChildren()) {
                if (childElement instanceof DOMCachingXMLObject) {
                    DOMCachingXMLObject domCachingChild = (DOMCachingXMLObject) childElement;
                    domCachingChild.releaseDOM();
                    if (propagateRelease) {
                        domCachingChild.releaseChildrenDOM(propagateRelease);
                    }
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
        String newString = DatatypeHelper.safeTrimOrNullString(newValue);

        if (!DatatypeHelper.safeEquals(oldValue, newString)) {
            releaseThisandParentDOM();
        }

        return newString;
    }

    /**
     * A helper function for derived classes that checks to see if the old and new value are equal and if so releases
     * the cached dom. Derived classes are expected to use this thus: <code>
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
    protected <T extends Object> T prepareForAssignment(T oldValue, T newValue) {
        if (oldValue == null) {
            if (newValue != null) {
                releaseThisandParentDOM();
                return newValue;
            } else {
                return null;
            }
        }

        if (!oldValue.equals(newValue)) {
            releaseThisandParentDOM();
        }

        return newValue;
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
     * @throws IllegalArgumentException if the child already has a parent.
     */
    protected <T extends XMLObject> T prepareForAssignment(T oldValue, T newValue) throws IllegalArgumentException {

        if (newValue != null && newValue.hasParent()) {
            throw new IllegalArgumentException(newValue.getClass().getName()
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
}