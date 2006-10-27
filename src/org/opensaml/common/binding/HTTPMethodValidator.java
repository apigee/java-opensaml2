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

/**
 * Validates that the given request method is a particular method type.
 */
public class HTTPMethodValidator implements TransportValidator<HttpServletRequest> {

    /** Acceptable HTTP method */
    private String httpMethod;
    
    /**
     * Gets the HTTP method expected by a request.
     * 
     * @return HTTP method expected by a request
     */
    public String getHTTPMethod(){
        return httpMethod;
    }
    
    /**
     * Sets the HTTP method expected by a request.
     * 
     * @param method HTTP method expected by a request
     */
    public void setHTTPMethod(String method){
        httpMethod = method;
    }
    
    /** {@inheritDoc} */
    public void valdate(HttpServletRequest request) throws BindingException {
        if(!request.getMethod().equals(getHTTPMethod())){
            throw new BindingException("Illegal HTTP method");
        }
    }
}