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

package org.opensaml.xml.encryption;

import org.opensaml.xml.XMLObject;

/**
 * XMLObject representing XML Encryption, version 20021210, CipherData element.
 */
public interface CipherData extends XMLObject {

    /** Element local name */
    public final static String LOCAL_NAME = "CipherData";

    /**
     * Gets the value of the cipher.
     * 
     * @return the value of the cipher
     */
    public CipherValue getValue();

    /**
     * Sets the value of the cipher.
     * 
     * @param newValue the value of the cipher
     */
    public void setValue(CipherValue newValue);

    /**
     * Gets the reference to the cipher value.
     * 
     * @return the reference to the cipher value
     */
    public CipherReference getReference();

    /**
     * Sets the reference to the cipher value.
     * 
     * @param newReference the reference to the cipher value
     */
    public void setReference(CipherReference newReference);
}