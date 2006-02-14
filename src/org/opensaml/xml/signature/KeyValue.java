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
 * XMLObject representing XML Digital Signature, version 20020212, KeyValue element.
 */
public interface KeyValue extends XMLObject {
    
    /** Element local name */
    public final static String LOCAL_NAME = "KeyValue";
    
    /**
     * Gets the DSA key value.
     * 
     * @return the DSA key value
     */
    public DSAKeyValue getDSAKeyValue();
    
    /**
     * Sets the DSA key value.
     * 
     * @param newKeyValue the DSA key value
     */
    public void setDSAKeyValue(DSAKeyValue newKeyValue);
    
    /**
     * Gets the RSA key value.
     * 
     * @return the RSA key value
     */
    public RSAKeyValue getRSAKeyValue();
    
    /**
     * Sets the RSA key value.
     * 
     * @param newKeyValue the RSA key value
     */
    public void setRSAKeyValue(RSAKeyValue newKeyValue);
}