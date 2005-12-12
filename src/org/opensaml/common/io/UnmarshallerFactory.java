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
import org.opensaml.common.util.StringHelper;
import org.opensaml.common.util.xml.XMLConstants;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

/**
 * This thread-safe factory creates {@link org.opensaml.common.io.Unmarshaller}s that can be used to convert W3C DOM
 * elements into {@link org.opensaml.common.SAMLObject}s. Unmarshallers are registered and retrived by a QName. This
 * QName may either refer to the schema type, as stipulated by the xsi:type attribute on the DOM element, or the element
 * name.
 */
public class UnmarshallerFactory {

    /** Logger */
    private final static Logger log = Logger.getLogger(UnmarshallerFactory.class);
    
    /** Singleton instance */
    private static UnmarshallerFactory instance = new UnmarshallerFactory();

    /** Map of unmarshallers to the elements they are for */
    private Map<QName, Unmarshaller> unmarshallers;

    /**
     * Constructor
     */
    private UnmarshallerFactory() {
        unmarshallers = new HashMap<QName, Unmarshaller>();
    }

    /**
     * Gets the singleton instance of this factory.
     * 
     * @return the singleton instance of this factory
     */
    public static UnmarshallerFactory getInstance() {
        return instance;
    }

    /**
     * Gets the unmarshaller for a given COM element.  If the element 
     * has a specified xsi:type the type QName is used to retrieve the unmarshaller
     * if it does not have a specified type the elements QName is used.
     * 
     * @param domElement the DOM element
     * 
     * @return the unmarshaller for the element
     */
    public Unmarshaller getUnmarshaller(Element domElement) {
        Unmarshaller unmarshaller;
        
        Attr type = domElement.getAttributeNodeNS(XMLConstants.XSI_NS, "type");
        if(type != null) {
            unmarshaller = getUnmarshaller(new QName(type.getNamespaceURI(), type.getLocalName(), type.getPrefix()));
            if(unmarshaller != null) {
                return unmarshaller;
            }
        }
        
        if(StringHelper.isEmpty(domElement.getPrefix())){
            return getUnmarshaller(new QName(domElement.getNamespaceURI(), domElement.getLocalName()));
        }else{
            return getUnmarshaller(new QName(domElement.getNamespaceURI(), domElement.getLocalName(), domElement.getPrefix()));
        }
    }
    
    /**
     * Gets the Unmarshaller for a particular element or null if no unmarshaller is registered for an element.
     * 
     * @param qname the name of the type or element
     * 
     * @return the Unmarshaller
     */
    public Unmarshaller getUnmarshaller(QName qname) {
        return unmarshallers.get(qname);
    }

    /**
     * Gets the Unmarshaller for a particular element or null if no unmarshaller is registered for an element.
     * 
     * @param namespaceURI the namespace URI of the type or element
     * @param localName the local name of the type or element
     * 
     * @return the Unmarshaller
     */
    public Unmarshaller getUnmarshaller(String namespaceURI, String localName) {
        QName qname = new QName(namespaceURI, localName);
        return getUnmarshaller(qname);
    }

    /**
     * Gets an immutable listing of all the Unarshallers currently registered.
     * 
     * @return a listing of all the Unmarshallers currently registered
     */
    public Map<QName, Unmarshaller> getUnmarshallers() {
        return Collections.unmodifiableMap(unmarshallers);
    }

    /**
     * Registers an Unmarshaller with this factory. If an Unmarshaller exist for the Qname given it is replaced with the
     * given unmarshaller.
     * 
     * @param qname the type or element the Unmarshaller operates on
     * @param unmarshaller the Unmarshaller
     */
    public void registerUnmarshaller(QName qname, Unmarshaller unmarshaller) {
        if (log.isDebugEnabled()) {
            log.debug("Registering unmarshaller, " + unmarshaller.getClass().getCanonicalName() + ", for object type "
                    + qname);
        }
        synchronized (unmarshallers) {
            unmarshallers.put(qname, unmarshaller);
        }
    }

    /**
     * Deregisters the unmarshaller for the given element.
     * 
     * @param qname the type or element's name
     * 
     * @return the Unmarshaller previously registered or null
     */
    public Unmarshaller deregisterUnmarshaller(QName qname) {
        if (log.isDebugEnabled()) {
            log.debug("Deregistering marshaller for object type " + qname);
        }
        synchronized (unmarshallers) {
            return unmarshallers.remove(qname);
        }
    }
}
