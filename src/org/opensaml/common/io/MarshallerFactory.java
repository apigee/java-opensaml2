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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.impl.EntitiesDescriptorMarshaller;

/**
 * This thread-safe factory creates {@link org.opensaml.common.io.Marshaller}s that can be used to convert
 * {@link org.opensaml.common.SAMLObject}s into W3C DOM elements. Marshallers are registered and retrived by a QName.
 * This QName may either refer to the schema type, as stipulated by the xsi:type attribute on the DOM element, or the
 * element name. 
 */
public class MarshallerFactory {

    /** Singleton instance */
    private static MarshallerFactory instance = new MarshallerFactory();

    /** Map of marshallers to the elements they are for */
    private Map<QName, Marshaller> marshallers;

    /**
     * Constructor
     */
    private MarshallerFactory() {
        marshallers = new HashMap<QName, Marshaller>();

        // TODO replace with dynamic configuration
        try {
            marshallers.put(EntitiesDescriptor.QNAME, new EntitiesDescriptorMarshaller());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the singleton instance of this factory.
     * 
     * @return the singleton instance of this factory
     */
    public static MarshallerFactory getInstance() {
        return instance;
    }
    
    /**
     * Gets the marshaller for a given SAML element.  If the element 
     * has a specified type the type QName is used to retrieve the marshaller
     * if it does not have a specified type the elements QName is used.
     * 
     * @param samlElement the SAML element
     * 
     * @return the marshaller for the element
     */
    public Marshaller getMarshaller(SAMLObject samlElement) {
        if(samlElement.getSchemaType() != null) {
            return getMarshaller(samlElement.getSchemaType());
        }
        
        return getMarshaller(samlElement.getElementQName());
    }

    /**
     * Gets the Marshaller for a particular element or null if no marshaller is registered for an element.
     * 
     * @param qname the qname of the schema type or element
     * 
     * @return the Marshaller or null
     */
    public Marshaller getMarshaller(QName qname) {
        return marshallers.get(qname);
    }

    /**
     * Gets the Marshaller for a particular element or null if no marshaller is registered for an element.
     * 
     * @param namespaceURI the namespace URI of the type or element
     * @param localName the local name of the type or element
     * 
     * @return the Marshaller or null
     */
    public Marshaller getMarshaller(String namespaceURI, String localName) {
        QName elementName = new QName(namespaceURI, localName);
        return getMarshaller(elementName);
    }

    /**
     * Gets an immutable listing of all the Marshallers currently registered.
     * 
     * @return a listing of all the Marshallers currently registered
     */
    public Map<QName, Marshaller> getMarshallers() {
        return Collections.unmodifiableMap(marshallers);
    }

    /**
     * Registers a Marshaller with this factory. If a Marshaller exist for the element name given it is replaced with
     * the given marshaller.
     * 
     * @param qname the type or element the Marshaller operates on
     * @param marshaller the Marshaller
     */
    public void registerMarshaller(QName qname, Marshaller marshaller) {
        synchronized (marshallers) {
            marshallers.put(qname, marshaller);
        }
    }

    /**
     * Deregisters the marshaller for the given element.
     * 
     * @param qname the type or element name
     * 
     * @return the Marshaller previously registered or null
     */
    public Marshaller deregisterMarshaller(QName qname) {
        synchronized (marshallers) {
            return marshallers.remove(qname);
        }
    }
}