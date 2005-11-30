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

package org.opensaml.saml2.metadata;

import java.util.Set;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.ValidatingObject;
import org.opensaml.common.util.xml.XMLConstants;

/**
 * SAML 2.0 Metadata Extensions
 * 
 * @author Chad La Joie
 */
public interface Extensions extends ValidatingObject {

	/** Local name, no namespace */
	public final static String LOCAL_NAME = "Extensions";
	
	/** QName for element */
	public final static QName QNAME = new QName(XMLConstants.SAML20MD_NS, LOCAL_NAME, XMLConstants.SAML20MD_PREFIX);
	
	/**
	 * Gets an ordered list of the all the child element in this element.
	 *  
	 * @return an ordered list of the all the child element in this element
	 */
	public Set<SAMLObject> getExtensionElements();
	
	/**
	 * Gets all the child elements, with a given name, in this element.
	 * 
	 * @param elementName the elements name
	 * 
	 * @return all the child elements, with a given name, in this element
	 */
	public Set<SAMLObject> getExtensionElements(QName elementName);
	
	/**
	 * Gets the first child element, with a given name, in this element.
	 * 
	 * @param elementName the elements name
	 * 
	 * @return the first child element, with a given name, in this element or null
	 */
	public SAMLObject getExtensionElement(QName elementName);
	
	/**
	 * Adds a given element as a child.
	 * 
	 * @param elment the child element
	 */
	public void addExtensionElement(SAMLObject elment);
	
	/**
	 * Replaces any existing elements, with the same QName, with this element.
	 * 
	 * @param element the SAML element
	 */
	public void replaceExtensionElement(SAMLObject element);

	/**
	 * Removes all child elements.
	 */
	public void removeExtensionElements();

	/**
	 * Removes all the child elements with the given name.
	 * 
	 * @param elementName the element name
	 */
	public void removeExtensionElements(QName elementName);
	
	/**
	 * Removes the given child element.
	 * 
	 * @param element the child element
	 */
	public void removeExtensionElement(SAMLObject element);
	
}