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

package org.opensaml.saml2.metadata.resolver;

/**
 * An exception thrown during the resolution of a metadata URI by a {@link org.opensaml.saml2.metadata.resolver.MetadataResolver}.
 */
public class ResolutionException extends Exception {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 6952599085295798685L;

    /**
     * Constructor.
     */
    public ResolutionException() {
        
    }
    
    /**
     * Constructor.
     * 
     * @param message exception message
     */
    public ResolutionException(String message) {
        super(message);
    }
    
    /**
     * Constructor.
     * 
     * @param wrappedException exception to be wrapped by this one
     */
    public ResolutionException(Exception wrappedException) {
        super(wrappedException);
    }
    
    /**
     * Constructor.
     * 
     * @param message exception message
     * @param wrappedException exception to be wrapped by this one
     */
    public ResolutionException(String message, Exception wrappedException) {
        super(message, wrappedException);
    }
}
