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

package org.opensaml.common.transport.http;

import java.util.List;
import java.util.Map;

import org.opensaml.common.transport.Response;

/**
 * A response to a request made through a {@link org.opensaml.common.transport.http.HTTPTransport}.
 */
public interface HTTPResponse extends Response {

    /**
     * Gets the HTTP response code for this response.
     * 
     * @return the HTTP response code for this response
     */
    public int getResponseCode();

    /**
     * Gets the HTTP headers that this transport will use.
     * 
     * @return HTTP headers that this transport will use
     */
    public Map<String, List<String>> getHeaders();

    /**
     * Gets the body of the response.
     * 
     * @return the body of the response
     */
    public String getBody();
}