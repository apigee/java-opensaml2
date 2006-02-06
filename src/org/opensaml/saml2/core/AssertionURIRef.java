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

package org.opensaml.saml2.core;

import org.opensaml.common.SAMLObject;

/**
 * SAML 2.0 Core AssertionURIRef
 */
public interface AssertionURIRef extends SAMLObject {
    
    /** Element local name */ 
    public final static String LOCAL_NAME = "AssertionURIRef";

    /**
     * Gets the URI of the assertion this references.
     * 
     * @return the URI of the assertion this references
     */
    public String getAssertionURI();
    
    /**
     * Sets the URI of the assertion this references.
     * 
     * @param newURI the URI of the assertion this references
     */
    public void setAssertionURI(String newURI);
}