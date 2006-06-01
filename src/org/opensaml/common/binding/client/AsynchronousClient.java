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

import org.opensaml.common.binding.Callback;

/**
 * A non-blocking binding client.
 * 
 * @param <RequestType> the request message type
 * @param <ResponseType> the response message type
 */
public interface AsynchronousClient<RequestType, ResponseType> extends BindingClient<RequestType, ResponseType> {

    /**
     * Sends a request to the server and does not block on the reply.
     * 
     * @param request the request to send
     * @param callback the callback that will process the reply when it comes in
     */
    public void sendRequest(RequestType request, Callback<ResponseType> callback);
}