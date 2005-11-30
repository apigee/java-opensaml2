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

import org.opensaml.common.SAMLObject;
import org.w3c.dom.Element;

/**
 * SAML element implementations that cache the DOM representation of the element should 
 * implement this interface.
 */
public interface DOMCachingSAMLObject extends SAMLObject {
    
    /**
     * Gets the DOM object for this element, if this element has been modified
     * or if {@link #releaseDOM()} has been called this method returns null. To
     * get the DOM element for a modified SAML element use the appropriate
     * {@link org.opensaml.common.io.Marshaller}.
     * 
     * @return the DOM object for this element
     */
    public Element getDOM();
    
    /**
     * Sets the given DOM representation of this element.
     * 
     * @param dom the dom representation of this element
     */
    public void setDOM(Element dom);

    /**
     * If this SAML Element currently contains the W3C DOM object representation
     * this method will cause those object to be dereferenced, allowing them to
     * be garbage collected.
     */
    public void releaseDOM();
    
    /**
     * If the parent of this SAML Element currently contains a W3C DOM object representation
     * this method will cause those objects to be dereferenced.
     * 
     * @param propagateRelease true if all ancestors of this element should release thier DOM
     */
    public void releaseParentDOM(boolean propagateRelease);
    
    /**
     * If the childrean of this SAML Element currently contains a W3C DOM object representation
     * this method will cause those objects to be dereferenced.
     * 
     * @param propagateRelease true if all descendants of this element should release thier DOM
     */
    public void releaseChildrenDOM(boolean propagateRelease);
}
