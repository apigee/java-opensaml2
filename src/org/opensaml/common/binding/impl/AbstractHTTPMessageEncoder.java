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

package org.opensaml.common.binding.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opensaml.common.binding.HTTPMessageEncoder;

/**
 * Base class handling boilerplate code for HTTP message encoders.
 */
public abstract class AbstractHTTPMessageEncoder extends AbstractMessageEncoder implements HTTPMessageEncoder {

    /** HTTP servlet request to decode */
    private HttpServletRequest request;
    
    /** HTTP respones to use during decoding */
    private HttpServletResponse response;
    
    /** Relay state */
    private String relayState;
    
    /** {@inheritDoc} */
    public HttpServletRequest getRequest(){
        return request;
    }
    
    /** {@inheritDoc} */
    public void setRequest(HttpServletRequest request){
        this.request = request;
    }
    
    /** {@inheritDoc} */
    public HttpServletResponse getResponse(){
        return response;
    }
    
    /** {@inheritDoc} */
    public void setResponse(HttpServletResponse response){
        this.response = response;
    }
    
    /** {@inheritDoc} */
    public String getRelayState() {
        return relayState;
    }

    /** {@inheritDoc} */
    public void setRelayState(String relayState) {
        this.relayState = relayState;
    }
}