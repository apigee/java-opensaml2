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

package org.opensaml.xml.signature;

import org.opensaml.xml.XMLObject;

/**
 * XMLObject representing XML Digital Signature, version 20020212, X509IssuerSerial element.
 */
public interface X509IssuerSerial extends XMLObject {

    /** Element local name */
    public final static String LOCAL_NAME = "X509IssuerSerial";
    
    /**
     * Gets the name of the issuer of this cert.
     * 
     * @return the name of the issuer of this cert
     */
    public X509IssuerName getIssuerName();
    
    /**
     * Sets the name of the issuer of this cert.
     * 
     * @param newName the name of the issuer of this cert
     */
    public void setIssuerName(X509IssuerName newName);
    
    /**
     * Gets the serial number of this cert.
     * 
     * @return the serial number of this cert
     */
    public X509SerialNumber getSerialNumber();
    
    /**
     * Sets the serial number of this cert.
     * 
     * @param newNumber the serial number of this cert
     */
    public void setSerialNumber(X509SerialNumber newNumber);
}