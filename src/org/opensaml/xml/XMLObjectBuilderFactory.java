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

package org.opensaml.xml;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * A factory for {@link org.opensaml.xml.XMLObjectBuilder}s.
 *
 * @param <KeyType> the object type that will be used as a key to retrieve builders
 */
public class XMLObjectBuilderFactory<KeyType, BuilderType extends XMLObjectBuilder> {

    /** Logger */
    private final static Logger log = Logger.getLogger(XMLObjectBuilderFactory.class);

    /** Registered builders */
    private Map<KeyType, BuilderType> builders;

    /** Constructor */
    public XMLObjectBuilderFactory() {
        builders = new HashMap<KeyType, BuilderType>();
    }

    /**
     * Retrieves an {@link XMLObjectBuilder} using the key it was registered with.
     * 
     * @param key the key used to register the builder
     * 
     * @return the builder
     */
    public BuilderType getBuilder(KeyType key) {
        return builders.get(key);
    }

    /**
     * Gets an immutable list of all the builders currently registered.
     * 
     * @return list of all the builders currently registered
     */
    public Map<KeyType, BuilderType> getBuilders() {
        return Collections.unmodifiableMap(builders);
    }

    /**
     * Registers a new builder for the given name.
     * 
     * @param builderKey the key used to retrieve this builder later
     * @param builder the builder
     */
    public void registerBuilder(KeyType builderKey, BuilderType builder) {
        if (log.isDebugEnabled()) {
            log.debug("Registering builder, " + builder.getClass().getCanonicalName() + " under key "
                    + builderKey);
        }
        synchronized (builders) {
            builders.put(builderKey, builder);
        }
    }

    /**
     * Deregisters a builder.
     * 
     * @param builderKey the key for the builder to be deregistered
     */
    public XMLObjectBuilder deregisterBuilder(KeyType builderKey) {
        if (log.isDebugEnabled()) {
            log.debug("Deregistering builder for object type " + builderKey);
        }
        synchronized (builders) {
            return builders.remove(builders.get(builderKey));
        }
    }
}