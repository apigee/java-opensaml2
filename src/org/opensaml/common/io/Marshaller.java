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

package org.opensaml.common.io;

import org.opensaml.common.SAMLObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Marshallers are used to marshall a {@link org.opensaml.common.SAMLObject} into 
 * a W3C DOM element.  Any Element in the chain of type {@link org.opensaml.common.SignableObject} 
 * that also contains a {@link org.opensaml.common.SigningContext} will be signed using the information
 * in the singing context.
 */
public interface Marshaller {

    /**
     * Marshall this element, and its children, into a W3C DOM element.
     * 
     * @param samlElement the SAML element to marshall
     * 
     * @return the W3C DOM element representing this SAML element
     * 
     * @throws MarshallingException thrown if a Document can not be created to root this element in
     * or the given SAML element can not be marshalled, or if child elements can not be signed
     */
	public Element marshall(SAMLObject samlElement) throws MarshallingException;
	
	/**
	 * Marshall this element, and its children, into a W3C DOM element rooted in the given document.
	 * 
	 * @param samlElement the SAML element to marshall
	 * @param document the DOM document
	 * 
	 * @return the W3C DOM element representing this SAML element
	 * 
	 * @throws MarshallingException thrown if the given SAML element can not be marshalled, or if 
     * child elements can not be signed
	 */
	public Element marshall(SAMLObject samlElement, Document document) throws MarshallingException;
}