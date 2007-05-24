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

/**
 * Simple builder interface for {@link MessageEncoder}s.
 * 
 * Builders must be thread-safe and reusable.
 * 
 * @param <MessageEncoderType> type of message encoder built
 */
public interface MessageEncoderBuilder<MessageEncoderType extends MessageEncoder> {

    /**
     * Creates a new instance of the message encoder.
     * 
     * @return new instance of the message encoder
     */
    public MessageEncoderType buildEncoder();
}