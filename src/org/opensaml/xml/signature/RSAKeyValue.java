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
 * XMLObject representing XML Digital Signature, version 20020212, RSAKeyValue element.
 */
public interface RSAKeyValue extends XMLObject {

    /** Element local name */
    public final static String LOCAL_NAME = "RSAKeyValue";

    /**
     * Gets the modulus of the RSA key.
     * 
     * @return the modulus of the RSA key
     */
    public RSAKeyModulus getModulus();

    /**
     * Sets the modulus of the RSA key.
     * 
     * @param newModulus the modulus of the RSA key
     */
    public void setModulus(RSAKeyModulus newModulus);

    /**
     * Gets the exponent of the RSA key.
     * 
     * @return the exponent of the RSA key
     */
    public RSAKeyExponent getExponent();

    /**
     * Sets the exponent of the RSA key.
     * 
     * @param newExponent the exponent of the RSA key
     */
    public void setExponent(RSAKeyExponent newExponent);
}