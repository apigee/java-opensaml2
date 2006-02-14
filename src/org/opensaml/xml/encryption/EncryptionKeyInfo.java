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

import org.opensaml.xml.signature.KeyInfo;

/**
 * XMLObject representing XML Encryption, version 20021210, extension to KeyInfo element.
 */
public interface EncryptionKeyInfo extends KeyInfo {

    /**
     * Gets the key used to encrypt the parent object.
     * 
     * @return the key used to encrypt the parent object
     */
    public EncryptedKey getKey();

    /**
     * Sets the key used to encrypt the parent object.
     * 
     * @param newKey the key used to encrypt the parent object
     */
    public void setKey(EncryptedKey newKey);

    /**
     * Gets the method by which key derivation/agreement occurs.
     * 
     * @return the method by which key derivation/agreement occurs
     */
    public AgreementMethod getAgreementMethod();

    /**
     * Sets the method by which key derivation/agreement occurs.
     * 
     * @param newMethod the method by which key derivation/agreement occurs
     */
    public void setAgreementMethod(AgreementMethod newMethod);
}