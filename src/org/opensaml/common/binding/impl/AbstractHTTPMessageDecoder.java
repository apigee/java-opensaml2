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

import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.HTTPMessageDecoder;

/**
 * Base class for HTTP message decoders handling much of boilerplate code.
 */
public abstract class AbstractHTTPMessageDecoder extends AbstractMessageDecoder<HttpServletRequest> implements HTTPMessageDecoder {

    /** Request relay state */
    private String relayState;
    
    /** {@inheritDoc} */
    public String getRelayState() {
        return relayState;
    }
    
    /**
     * Sets the relay state of the request.
     * 
     * @param relayState relay state of the request
     */
    public void setRelayState(String relayState){
        this.relayState = relayState;
    }

    /** {@inheritDoc} */
    abstract public void decode() throws BindingException;
}