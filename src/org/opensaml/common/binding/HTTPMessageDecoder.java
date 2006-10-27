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

package org.opensaml.common.binding;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Base interface for HTTP specific SAML bindings.
 */
public interface HTTPMessageDecoder extends MessageDecoder {
    
    /**
     * Sets the HTTP request to decode.
     * 
     * @return the HTTP request to decode
     */
    public HttpServletRequest getRequest();
    
    /**
     * Sets the HTTP request to decode.
     * 
     * @param request the HTTP request to decode
     */
    public void setRequest(HttpServletRequest request);
    
    /**
     * Sets the HTTP response to use during the decoding process.
     * 
     * @return response  the HTTP response to use during decoding
     */
    public HttpServletResponse getResponse();
    
    /**
     * Sets the HTTP response to use during the decoding process.
     * 
     * @param response the HTTP response to use during decoding
     */
    public void setResponse(HttpServletResponse response);

    /**
     * Gets the relay state from the decoded message.
     */
    public String getRelayState();

}
