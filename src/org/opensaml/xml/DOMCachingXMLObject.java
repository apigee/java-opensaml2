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

import org.w3c.dom.Element;

/**
 * An XMLObject that can cached a DOM representation of itself.
 *
 * @param <T> the object type of this object
 */
public interface DOMCachingXMLObject extends XMLObject {
    
    /**
     * Gets the DOM representation of this XMLObject, if one exists.
     * 
     * @return the DOM representation of this XMLObject
     */
    public Element getDOM();
    
    /**
     * Sets the DOM representation of this XMLObject.
     * 
     * @param dom DOM representation of this XMLObject
     */
    public void setDOM(Element dom);

    /**
     * Releases the DOM representation of this XMLObject, if there is one.
     */
    public void releaseDOM();
    
    /**
     * Releases the DOM representation of this XMLObject's parent.
     * 
     * @param propagateRelease true if all ancestors of this element should release thier DOM
     */
    public void releaseParentDOM(boolean propagateRelease);
    
    /**
     * Releases the DOM representation of this XMLObject's children.
     * 
     * @param propagateRelease true if all descendants of this element should release thier DOM
     */
    public void releaseChildrenDOM(boolean propagateRelease);
}