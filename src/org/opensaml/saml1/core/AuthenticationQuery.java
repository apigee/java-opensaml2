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
package org.opensaml.saml1.core;

/**
 * Description of the behaviour of the <code> AuthenticationQuery </code> element
 */
public interface AuthenticationQuery extends SubjectQuery {

    /** Element name, no namespace. */
    public final static String LOCAL_NAME = "AuthenticationQuery";
    
    /** AuthenticationMethod attribute name */
    public final static String AUTHENTICATIONMETHOD_ATTRIB_NAME = "AuthenticationMethod"; 

    /** Get AuthenticationMethod attribute */
    public String getAuthenticationMethod();
    
    /** Set AuthenticationMethod attribute */
    public void setAuthenticationMethod(String authenticationMethod);

}
