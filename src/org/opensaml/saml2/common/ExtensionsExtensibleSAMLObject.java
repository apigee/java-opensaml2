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

package org.opensaml.saml2.common;

import java.util.Set;

import javax.xml.namespace.QName;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.metadata.Extensions;

/**
 * A functional interface for SAMLElements that can be extended with 
 * Extensions elements.
 *
 */
public interface ExtensionsExtensibleSAMLObject extends SAMLObject{

	/**
	 * Gets the Extensions element of the element.
	 * 
	 * @return the Extensions element
	 */
	public Extensions getExtensions();

	/**
	 * A convience methods for getting the children of an Extensions element.  This is the 
	 * same as calling this.getExtensions().getExtensionElements().
	 * 
	 * @return a list of SAMLElements that are children of the Extensions element
	 */
	public Set<SAMLObject> getExtensionElements();
	
	/**
	 * A convience method for getting all the children, of a given name, of an Extensions element.
	 * This is the same as calling this.getExtensions().getExtensionElements(QName).
	 * 
	 * @param elementName the name of the children to retrieve
	 * 
	 * @return a list of SAMLElements, with the given name, that are children of the Extensions element
	 */
	public Set<SAMLObject> getExtensionElements(QName elementName);
	
	/**
	 * A convience method for getting the first child, of a given name, of an Extensions element.
	 * This is the same as calling this.getExtensions.getExtensionElement(QName).
	 * 
	 * @param elementName the name of the child element to retrieve
	 * 
	 * @return the child element, or null
	 */
	public SAMLObject getExtensionElement(QName elementName);
	
	/**
	 * Sets the Extension element for this SAMLElement.
	 * 
	 * @param extensions the Extension element
     * 
     * @throws IllegalAddException thrown if the given Extensions is already a descendant of another SAMLElement
	 */
	public void setExtensions(Extensions extensions) throws IllegalAddException;
}