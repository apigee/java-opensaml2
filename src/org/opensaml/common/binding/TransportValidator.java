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

import javax.servlet.ServletRequest;

/**
 * A validator used to evaluate an incoming request and determine if it meets the necessary security requirements.
 * 
 * Validators must be thread-safe and stateless such that multiple threads may call the validate method one or more 
 * times and recieve a proper answer.
 */
public interface TransportValidator<RequestType extends ServletRequest> {
    
    //TODO issuer/relying party

    /**
     * Valdiates that an incoming request meets necesary security requirements.
     * 
     * @param request the request to validate
     * 
     * @throws BindingException thrown if the request does not meet the necessary security requirements
     */
    public void validate(RequestType request) throws BindingException;
}