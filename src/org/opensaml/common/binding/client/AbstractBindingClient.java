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

import javolution.util.FastList;
import javolution.util.FastList.Node;

import org.apache.log4j.Logger;
import org.opensaml.common.binding.MessageFilter;
import org.opensaml.common.binding.MessageFilterException;

/**
 * A basic implementation of {@link org.opensaml.common.binding.client.BindingClient}.
 *
 * @param <RequestType> the request message type
 * @param <ResponseType> the response message type
 */
public abstract class AbstractBindingClient<RequestType, ResponseType> implements BindingClient<RequestType, ResponseType> {
    
    /** Logger */
    private final Logger log = Logger.getLogger(AbstractBindingClient.class);

    /** Message filters applied to outgoing methods */
    private FastList<MessageFilter<RequestType>> outgoingFilters;
    
    /** Message filters applied to incoming methods */
    private FastList<MessageFilter<ResponseType>> incomingFilters;
    
    protected AbstractBindingClient(){
        outgoingFilters = new FastList<MessageFilter<RequestType>>();
        incomingFilters = new FastList<MessageFilter<ResponseType>>();
    }
    
    /** {@inheritDoc} */
    public List<MessageFilter<RequestType>> getOutgoingMessageFilters() {
        return outgoingFilters;
    }

    /** {@inheritDoc} */
    public List<MessageFilter<ResponseType>> getIncomingMessageFilters() {
        return incomingFilters;
    }
    
    /**
     * Applies the outgoing message filters to the given request message.
     * 
     * @param request the request message
     */
    protected void applyOutgoingFilters(RequestType request) throws MessageFilterException{
        if(log.isDebugEnabled()){
            log.debug("Applying outgoing messages filters to response object " + request.getClass().getName());
        }
        if(outgoingFilters.size() > 0){
            MessageFilter<RequestType> filter;
            Node<MessageFilter<RequestType>> finalNode = outgoingFilters.tail();
            for(Node<MessageFilter<RequestType>> node = outgoingFilters.head(); node != finalNode; node = node.getNext()){
                filter = node.getValue();
                filter.doFilter(request);
            }
        }
    }
    
    /**
     * Applies the outgoing message filters to the given response message.
     * 
     * @param response the response message
     */
    protected void applyIncomingFilters(ResponseType response) throws MessageFilterException{
        if(log.isDebugEnabled()){
            log.debug("Applying incoming messages filters to response object " + response.getClass().getName());
        }
        if(incomingFilters.size() > 0){
            MessageFilter<ResponseType> filter;
            Node<MessageFilter<ResponseType>> finalNode = incomingFilters.tail();
            for(Node<MessageFilter<ResponseType>> node = incomingFilters.head(); node != finalNode; node = node.getNext()){
                filter = node.getValue();
                filter.doFilter(response);
            }
        }
    }
}