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

/**
 * 
 */

package org.opensaml.saml2.core;

import org.opensaml.common.SAMLObject;

/**
 * SAML 2.0 Core AuthenticatingAuthority.
 */
public interface AuthenticatingAuthority extends SAMLObject {

    /** Local Name of AuthenticatingAuthority */
    public final static String LOCAL_NAME = "AuthenticatingAuthority";

    /**
     * Gets the URI of this Authenticating Authority
     * 
     * @return AuthenticatingAuthority URI
     */
    public String getURI();

    /**
     * Sets the URI of this Authenticating Authority.
     * 
     * @param newURI the URI of this Authenticating Authority
     */
    public void setURI(String newURI);
}