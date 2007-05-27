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

package org.opensaml.common.binding.decoding;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory for producing new message decoders.
 */
public class MessageDecoderFactory {

    /** Registered decoder builders. */
    private HashMap<String, MessageDecoderBuilder> decoderBuilders;

    /** Constructor. */
    public MessageDecoderFactory() {
        decoderBuilders = new HashMap<String, MessageDecoderBuilder>();
    }

    /**
     * Creates a new message decoder for the given binding.
     * 
     * @param binding binding URI
     * 
     * @return newly created decoder or null if no builder was registered for the binding string
     */
    public MessageDecoder getMessageDecoder(String binding) {
        MessageDecoderBuilder builder = decoderBuilders.get(binding);

        if (builder != null) {
            return builder.buildDecoder();
        }

        return null;
    }

    /**
     * Gets the registered binding decoder.
     * 
     * @return registered binding decoder
     */
    public Map<String, MessageDecoderBuilder> getEncoderBuilders() {
        return decoderBuilders;
    }
    
    /**
     * Sets the registered binding decoders, replacing all currently registered builders.
     * 
     * @param builders registered binding decoders
     */
    public void setDecoderBuilders(Map<String, MessageDecoderBuilder> builders) {
        if (builders == null) {
            throw new IllegalArgumentException("Message decoder builders may not be null");
        }

        decoderBuilders.clear();
        decoderBuilders.putAll(builders);
    }
}