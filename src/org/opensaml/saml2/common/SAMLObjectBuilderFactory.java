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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.impl.EntitiesDescriptorImplBuilder;

public class SAMLObjectBuilderFactory {

	private static SAMLObjectBuilderFactory instance;

	private Map<QName, SAMLObjectBuilder> builders;

	private SAMLObjectBuilderFactory() {
		builders = new HashMap<QName, SAMLObjectBuilder>();
        
        //Temporary
        builders.put(EntitiesDescriptor.QNAME, new EntitiesDescriptorImplBuilder());
	}

	public static SAMLObjectBuilderFactory getInstance() {
		if (instance == null) {
			instance = new SAMLObjectBuilderFactory();
		}
		return instance;
	}

	public SAMLObjectBuilder newBuilder(QName elementName) {
		return builders.get(elementName);
	}

	public SAMLObjectBuilder newBuilder(String namespaceURI, String localName) {
		QName elementName = new QName(namespaceURI, localName);
		return newBuilder(elementName);
	}

	public List listBuilders() {
		return null;
	}

	public void registerBuilder(QName elementName, SAMLObjectBuilder builder) {
	    builders.put(elementName, builder);
	}

	public void registerBuilder(String namespaceURI, String localName,
			SAMLObjectBuilder builder) {
		QName elementName = new QName(namespaceURI, localName);
		registerBuilder(elementName, builder);
	}

	public void deregisterBuilder(QName elementName) {
	    builders.remove(builders.get(elementName));
	}

	public void deregisterBuilder(String namespaceURI, String localName) {
		QName elementName = new QName(namespaceURI, localName);
		deregisterBuilder(elementName);
	}
}
