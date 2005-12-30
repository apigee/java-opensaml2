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
 * This thread-safe factory creates {@link org.opensaml.xml.io.Unmarshaller}s that can be used to convert W3C DOM
 * elements into {@link org.opensaml.xml.XMLObject}s.
 * 
 * @param <KeyType> the object type of keys used to reference marshallers
 * @param <UnmarshallerType> the object type of the unmarshallers registered
 */
public class UnmarshallerFactory<KeyType, UnmarshallerType extends Unmarshaller> {

    /** Logger */
    private final static Logger log = Logger.getLogger(UnmarshallerFactory.class);

    /** Map of unmarshallers to the elements they are for */
    private Map<KeyType, UnmarshallerType> unmarshallers;

    /**
     * Constructor
     */
    public UnmarshallerFactory() {
        unmarshallers = new HashMap<KeyType, UnmarshallerType>();
    }

    /**
     * Gets the Unmarshaller for a particular element or null if no unmarshaller is registered for an element.
     * 
     * @param qname the name of the type or element
     * 
     * @return the Unmarshaller
     */
    public UnmarshallerType getUnmarshaller(KeyType key) {
        return unmarshallers.get(key);
    }

    /**
     * Gets an immutable listing of all the Unarshallers currently registered.
     * 
     * @return a listing of all the Unmarshallers currently registered
     */
    public Map<KeyType, UnmarshallerType> getUnmarshallers() {
        return Collections.unmodifiableMap(unmarshallers);
    }

    /**
     * Registers an Unmarshaller with this factory. If an Unmarshaller exist for the Qname given it is replaced with the
     * given unmarshaller.
     * 
     * @param qname the type or element the Unmarshaller operates on
     * @param unmarshaller the Unmarshaller
     */
    public void registerUnmarshaller(KeyType key, UnmarshallerType unmarshaller) {
        if (log.isDebugEnabled()) {
            log.debug("Registering unmarshaller, " + unmarshaller.getClass().getCanonicalName() + ", for object type "
                    + key);
        }
        synchronized (unmarshallers) {
            unmarshallers.put(key, unmarshaller);
        }
    }

    /**
     * Deregisters the unmarshaller for the given element.
     * 
     * @param qname the type or element's name
     * 
     * @return the Unmarshaller previously registered or null
     */
    public UnmarshallerType deregisterUnmarshaller(KeyType key) {
        if (log.isDebugEnabled()) {
            log.debug("Deregistering marshaller for object type " + key);
        }
        synchronized (unmarshallers) {
            return unmarshallers.remove(key);
        }
    }
}