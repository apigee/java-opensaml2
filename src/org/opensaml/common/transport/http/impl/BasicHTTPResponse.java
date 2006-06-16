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

package org.opensaml.common.transport.http.impl;

import java.util.List;
import java.util.Map;

import javolution.util.FastMap;

import org.opensaml.common.transport.http.HTTPResponse;

/**
 * Basic implementation of {@link org.opensaml.common.transport.http.HTTPResponse}.
 */
public class BasicHTTPResponse implements HTTPResponse{
    
    /** HTTP response code */
    private int responseCode;
    
    /** HTTP response headers */
    private FastMap<String, List<String>> headers;
    
    /** HTTP response body */
    private String body;
    
    /**
     * Constructor
     */
    public BasicHTTPResponse(int responseCode, FastMap<String, List<String>> headers, String body){
        this.responseCode = responseCode;
        this.headers = headers;
        this.body = body;
    }

    /** {@inheritDoc} */
    public int getResponseCode() {
       return responseCode;
    }

    /** {@inheritDoc} */
    public Map<String, List<String>> getHeaders() {
        return headers.unmodifiable();
    }

    /** {@inheritDoc} */
    public String getBody() {
        return body;
    }
}