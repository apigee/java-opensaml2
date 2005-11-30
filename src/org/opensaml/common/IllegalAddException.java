/*
 * Copyright [2005] [University Corporation for Advanced Internet Development, Inc.]
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
package org.opensaml.common;

/**
 * Exception thrown when a SAMLElement, that is already a descendant of another 
 * element, is added to a second SAMLElement.
 */
public class IllegalAddException extends SAMLException {
    
    /**
     *  Serial version UID
     */
    private static final long serialVersionUID = 5223278190536687601L;

    /**
     * Constructor.
     */
    public IllegalAddException() {
        super();
    }
    
    /**
     * Constructor.
     * 
     * @param message exception message
     */
    public IllegalAddException(String message) {
        super(message);
    }
    
    /**
     * Constructor.
     * 
     * @param wrappedException exception to be wrapped by this one
     */
    public IllegalAddException(Exception wrappedException) {
        super(wrappedException);
    }
    
    /**
     * Constructor.
     * 
     * @param message exception message
     * @param wrappedException exception to be wrapped by this one
     */
    public IllegalAddException(String message, Exception wrappedException) {
        super(message, wrappedException);
    }
}
