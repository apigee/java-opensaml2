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

package org.opensaml.xml.io;

import org.opensaml.xml.XMLObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Marshallers are used to marshall a {@link org.opensaml.xml.XMLObject} into 
 * a W3C DOM element.
 * 
 * @param <T> object type this marshaller operates on
 */
public interface Marshaller<T extends XMLObject> {
	
	/**
	 * Marshall this element, and its children, into a W3C DOM element rooted in the given document.
	 * 
	 * @param xmlObject the object to marshall
	 * @param document the DOM document the marshalled element will be rooted in
     * @param ignoreUnknownElements indicates whether unknown elements should be ignored when they are encountered
	 * 
	 * @return the W3C DOM element representing this SAML element
	 * 
	 * @throws MarshallingException thrown if there is a problem marshalling the given object
	 */
	public Element marshall(T xmlObject, Document document) throws MarshallingException;
}