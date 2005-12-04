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

package org.opensaml.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;

/**
 * Factory that provides access and management of the {@link org.opensaml.common.SAMLObjectBuilder} singleton registered
 * for a SAMLObject type.
 */
public class SAMLObjectBuilderFactory {

    /** Logger */
    private final static Logger log = Logger.getLogger(SAMLObjectBuilderFactory.class);

    /** Singleton instance of this factory */
    private static SAMLObjectBuilderFactory instance = new SAMLObjectBuilderFactory();

    /** Registered builders */
    private Map<QName, SAMLObjectBuilder> builders;

    /** Constructor */
    private SAMLObjectBuilderFactory() {
        builders = new HashMap<QName, SAMLObjectBuilder>();
    }

    /**
     * Gets the singleton instance of this factory.
     * 
     * @return the singleton instance of this factory
     */
    public static SAMLObjectBuilderFactory getInstance() {
        return instance;
    }

    /**
     * Gets the builder for the given object QName. This name may correspond to either a a namespace qualified element
     * name or a XML schema type for an element.
     * 
     * @param objectQName object's QName
     * 
     * @return the builder for the given object
     */
    public SAMLObjectBuilder getBuilder(QName objectQName) {
        return builders.get(objectQName);
    }

    /**
     * Gets the builder for the given object namespace and local name. These may correspond to either a a namespace
     * qualified element name or a XML schema type for an element.
     * 
     * @param namespaceURI the namespace of the local name
     * @param localName the localname of the element or XML schema type
     * 
     * @return the builder for the given object
     */
    public SAMLObjectBuilder getBuilder(String namespaceURI, String localName) {
        QName elementName = new QName(namespaceURI, localName);
        return getBuilder(elementName);
    }

    /**
     * Gets an immutable list of all the builders currently registered.
     * 
     * @return list of all the builders currently registered
     */
    public Map<QName, SAMLObjectBuilder> getBuilders() {
        return Collections.unmodifiableMap(builders);
    }

    /**
     * Registers a new builder for the given name.
     * 
     * @param objectQName the element or schema type name
     * @param builder the builder for this type
     */
    public void registerBuilder(QName objectQName, SAMLObjectBuilder builder) {
        if (log.isDebugEnabled()) {
            log.debug("Registering builder, " + builder.getClass().getCanonicalName() + ", for object type "
                    + objectQName);
        }
        synchronized (builders) {
            builders.put(objectQName, builder);
        }
    }

    /**
     * Deregisters the builder for the given type.
     * 
     * @param objectQName the element or schema type name
     */
    public void deregisterBuilder(QName objectQName) {
        if (log.isDebugEnabled()) {
            log.debug("Deregistering builder for object type " + objectQName);
        }
        synchronized (builders) {
            builders.remove(builders.get(objectQName));
        }
    }
}