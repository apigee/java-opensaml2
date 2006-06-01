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

import org.opensaml.common.binding.CommunicationException;
import org.opensaml.common.binding.MessageFilterException;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A client that waits for a response after a request has been sent.
 * 
 * @param <RequestType> the request message type
 * @param <ResponseType> the response message type
 */
public interface SynchronousClient<RequestType, ResponseType> extends BindingClient<RequestType, ResponseType> {

    /**
     * Send a request to the server and wait for a reply.
     * 
     * @param request the request to send
     * 
     * @return the reply
     */
    public ResponseType sendRequest(RequestType request) throws CommunicationException, MarshallingException, UnmarshallingException, MessageFilterException;
}