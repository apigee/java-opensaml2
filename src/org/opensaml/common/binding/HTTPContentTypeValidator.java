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
 * Validates that the requests content type matches the given content type.
 * 
 * When validating the request content type is checked to see if it contains the provided content type, not if 
 * it exactly matches.
 */
public class HTTPContentTypeValidator implements TransportValidator<HttpServletRequest> {

    /** Expected content type */
    private String contentType;
    
    /**
     * Gets the expected content type.
     * 
     * @return expected content type
     */
    public String getContentType(){
        return contentType;
    }
    
    /**
     * Sets the expected content type.
     *
     * @param type expected content type
     */
    public void setContentType(String type){
        contentType = type;
    }
    
    /** {@inheritDoc} */
    public void validate(HttpServletRequest request) throws BindingException {
        if(!request.getContentType().contains(getContentType())){
            throw new BindingException("Illegal content type");
        }
    }
}