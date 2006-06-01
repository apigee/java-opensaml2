/*
 * Copyright [2006] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.common.binding.client;

import java.util.List;

import org.opensaml.common.binding.MessageFilter;

/**
 * Base interface for all binding clients.
 * 
 * @param <RequestType> the request message type
 * @param <ResponseType> the response message type
 */
public interface BindingClient<RequestType, ResponseType> {

    /**
     * Gets the list of message filters that will be applied to messages before they are transmitted. Filters will be
     * applied in the order they are registered in the list.
     * 
     * @return list of message filters that will be applied to messages before they are transmitted
     */
    public List<MessageFilter<RequestType>> getOutgoingMessageFilters();

    /**
     * Gets the list of message filters that will be applied to messages as they arrive, before they are returned.
     * Filters will be applied in the order they are registered in the list.
     * 
     * @return list of message filters that will be applied to messages before they are transmitted
     */
    public List<MessageFilter<ResponseType>> getIncomingMessageFilters();
}