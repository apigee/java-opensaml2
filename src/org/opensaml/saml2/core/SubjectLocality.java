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
 * SAML 2.0 Core SubjectLocality
 */
public interface SubjectLocality extends SAMLObject {
    
    /** Element local name */
    public final static String LOCAL_NAME = "SubjectLocality";
    
    /** Address attribute name */
    public final static String ADDRESS_ATTRIB_NAME = "Address";
    
    /** DNSName attribute name */
    public final static String DNS_NAME_ATTRIB_NAME = "DNSName";

    /**
     * Gets the IP address of the system from which the subject was authenticated.
     * 
     * @return the IP address of the system from which the subject was authenticated
     */
    public String getAddress();
    
    /**
     * Sets the IP address of the system from which the subject was authenticated.
     * 
     * @param newAddress the IP address of the system from which the subject was authenticated
     */
    public void setAddress(String newAddress);
    
    /**
     * Gets the DNSName of the system from which the subject was authenticated.
     * 
     * @return the DNSName of the system from which the subject was authenticated
     */
    public String getDNSName();
    
    /**
     * Sets the DNSName of the system from which the subject was authenticated.
     * 
     * @param newDNSName the DNSName of the system from which the subject was authenticated
     */
    public void setDNSName(String newDNSName);
}