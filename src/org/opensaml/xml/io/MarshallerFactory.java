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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * This thread-safe factory creates {@link org.opensaml.xml.io.Marshaller}s that can be used to convert
 * {@link org.opensaml.xml.XMLObject}s into W3C DOM elements.
 * 
 * @param <KeyType> the object type of keys used to reference marshallers
 * @param <MarshallerType> the object type of the marshallers registered
 */
public class MarshallerFactory<KeyType, MarshallerType extends Marshaller> {

    /** Logger */
    private final static Logger log = Logger.getLogger(MarshallerFactory.class);

    /** Map of marshallers to the elements they are for */
    private Map<KeyType, MarshallerType> marshallers;

    /**
     * Constructor
     */
    public MarshallerFactory() {
        marshallers = new HashMap<KeyType, MarshallerType>();
    }

    /**
     * Gets the Marshaller for a particular element or null if no marshaller is registered for an element.
     * 
     * @param qname the qname of the schema type or element
     * 
     * @return the Marshaller or null
     */
    public MarshallerType getMarshaller(KeyType key) {
        return marshallers.get(key);
    }

    /**
     * Gets an immutable listing of all the Marshallers currently registered.
     * 
     * @return a listing of all the Marshallers currently registered
     */
    public Map<KeyType, MarshallerType> getMarshallers() {
        return Collections.unmodifiableMap(marshallers);
    }

    /**
     * Registers a Marshaller with this factory. If a Marshaller exist for the element name given it is replaced with
     * the given marshaller.
     * 
     * @param qname the type or element the Marshaller operates on
     * @param marshaller the Marshaller
     */
    public void registerMarshaller(KeyType key, MarshallerType marshaller) {
        if (log.isDebugEnabled()) {
            log.debug("Registering marshaller, " + marshaller.getClass().getCanonicalName() + ", for object type "
                    + key);
        }
        synchronized (marshallers) {
            marshallers.put(key, marshaller);
        }
    }

    /**
     * Deregisters the marshaller for the given element.
     * 
     * @param qname the type or element name
     * 
     * @return the Marshaller previously registered or null
     */
    public MarshallerType deregisterMarshaller(KeyType key) {
        if (log.isDebugEnabled()) {
            log.debug("Deregistering marshaller for object type " + key);
        }
        synchronized (marshallers) {
            return marshallers.remove(key);
        }
    }
}