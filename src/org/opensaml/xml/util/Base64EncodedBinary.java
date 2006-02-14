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

package org.opensaml.xml.util;

/**
 * An interface for elements whose content is binary data that has been 
 * Base64 encoded.  This is NOT the same thing as the base64Binary or 
 * cryptoBinary schema data types it simply provides a uniform way to access 
 * them.
 */
public interface Base64EncodedBinary {

    /**
     * Gets the value as a Base64 encoded string.
     * 
     * @return the value as a Base64 encoded string
     */
    public String getBase64EncodedValue();
    
    /**
     * Gets the binary value, i.e. the Base64 decoded string.
     * 
     * @return the binary value
     */
    public byte[] getValue();
    
    /**
     * Sets the value as a Base64 encoded string.
     * 
     * @param newValue the value as a Base64 encoded string
     */
    public void setValue(String newValue);
    
    /**
     * Sets the value as byte array which will be Base64 encoded when written out.
     * 
     * @param newValue the value as byte array
     */
    public void setValue(byte[] newValue);
}