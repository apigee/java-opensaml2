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
 * XMLObject representing XML Encryption, version 20021210, EncryptionMethod element.
 */
public interface EncryptionMethod extends XMLObject {

    /** Element local name */
    public final static String LOCAL_NAME = "EncryptionMethod";

    /** Algorithm attribute name */
    public final static String ALGORITHM_ATTRIB_NAME = "Algorithm";

    /**
     * Gets the algorithm used to perform the encryption.
     * 
     * @return the algorithm used to perform the encryption
     */
    public String getAlgorithm();

    /**
     * Sets the algorithm used to perform the encryption.
     * 
     * @param Algortihm the algorithm used to perform the encryption
     */
    public void setAlgorithm(String Algortihm);

    /**
     * Gets the size of the key used for encryption.
     * 
     * @return the size of the key used for encryption
     */
    public KeySize getKeySize();

    /**
     * Sets the size of the key used for encryption.
     * 
     * @param newKeySize the size of the key used for encryption
     */
    public void setKeySize(KeySize newKeySize);

    /**
     * Gets the OAE parameters used during the encryption.
     * 
     * @return the OAE parameters used during the encryption
     */
    public OAEParams getOAEParams();

    /**
     * Sets the OAE parameters used during the encryption.
     * 
     * @param newParams the OAE parameters used during the encryption
     */
    public void setOAEParams(OAEParams newParams);
}