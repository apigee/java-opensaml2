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

import org.apache.log4j.Logger;
import org.opensaml.common.SAMLObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This thread-safe factory creates {@link org.opensaml.common.io.Marshaller}s that can be used to convert
 * {@link org.opensaml.common.SAMLObject}s into W3C DOM elements. Marshallers are registered and retrived by a QName.
 * This QName may either refer to the schema type, as stipulated by the xsi:type attribute on the DOM element, or the
 * element name.
 */
public class MarshallerFactory {

    /** Logger */
    private final static Logger log = Logger.getLogger(MarshallerFactory.class);

    /** Singleton instance */
    private static MarshallerFactory instance = new MarshallerFactory();

    /** Map of marshallers to the elements they are for */
    private Map<QName, Marshaller> marshallers;

    /**
     * Constructor
     */
    private MarshallerFactory() {
        marshallers = new HashMap<QName, Marshaller>();
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
     * Convience method equivalent to getting an instance of this factory, getting 
     * a marshaller based on the given object and then invoking marshall().
     * 
     * @param samlObject the object to marshall
     * 
     * @return the DOM element from the marshalling
     * 
     * @throws MarshallingException thrown if there is a problem marshalling the SAMLObject
     */
    public static Element marshallSAMLObject(SAMLObject samlObject) throws MarshallingException {
        Marshaller marshaller = getInstance().getMarshaller(samlObject);
        return marshaller.marshall(samlObject);
    }
    
    /**
     * Convience method equivalent to getting an instance of this factory, getting 
     * a marshaller based on the given object and then invoking marshall().
     * 
     * @param samlObject the object to marshall
     * @param document the document the element will be rooted in
     * 
     * @return the DOM element from the marshalling
     * 
     * @throws MarshallingException thrown if there is a problem marshalling the SAMLObject
     */
    public static Element marshallSAMLObject(SAMLObject samlObject, Document document) throws MarshallingException {
        Marshaller marshaller = getInstance().getMarshaller(samlObject);
        return marshaller.marshall(samlObject, document);
    }

    /**
     * Gets the marshaller for a given SAML element. If the element has a specified type the type QName is used to
     * retrieve the marshaller if it does not have a specified type the elements QName is used.
     * 
     * @param samlElement the SAML element
     * 
     * @return the marshaller for the element
     */
    public Marshaller getMarshaller(SAMLObject samlElement) {
        Marshaller marshaller;
        
        if (samlElement.getSchemaType() != null) {
            marshaller = getMarshaller(samlElement.getSchemaType());
            if(marshaller != null) {
                return marshaller;
            }
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
        if (log.isDebugEnabled()) {
            log.debug("Registering marshaller, " + marshaller.getClass().getCanonicalName() + ", for object type "
                    + qname);
        }
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
        if (log.isDebugEnabled()) {
            log.debug("Deregistering marshaller for object type " + qname);
        }
        synchronized (marshallers) {
            return marshallers.remove(qname);
        }
    }
}