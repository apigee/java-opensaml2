/*
 * Copyright [2007] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.common.binding.encoding;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory for producing new message encoders.
 */
public class MessageEncoderFactory {

    /** Registered encoder builders. */
    private HashMap<String, MessageEncoderBuilder> encoderBuilders;

    /** Constructor. */
    public MessageEncoderFactory() {
        encoderBuilders = new HashMap<String, MessageEncoderBuilder>();
    }

    /**
     * Creates a new message encoder for the given binding.
     * 
     * @param binding binding URI
     * 
     * @return newly created encoder or null if no builder was registered for the binding string
     */
    public MessageEncoder getMessageEncoder(String binding) {
        MessageEncoderBuilder builder = encoderBuilders.get(binding);

        if (builder != null) {
            return builder.buildEncoder();
        }

        return null;
    }

    /**
     * Gets the registered binding encoders.
     * 
     * @return registered binding encoders
     */
    public Map<String, MessageEncoderBuilder> getEncoderBuilders() {
        return encoderBuilders;
    }

    /**
     * Sets the registered binding encoders, replacing all currently registered builders.
     * 
     * @param builders registered binding encoders
     */
    public void setEncoderBuilders(Map<String, MessageEncoderBuilder> builders) {
        if (builders == null) {
            throw new IllegalArgumentException("Message encoder builders may not be null");
        }

        encoderBuilders.clear();
        encoderBuilders.putAll(builders);
    }
}