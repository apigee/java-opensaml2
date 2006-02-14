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
 * XMLObject representing XML Encryption, version 20021210, AgreementMethod element.
 */
public interface AgreementMethod extends XMLObject {

    /** Element local name */
    public final static String LOCAL_NAME = "AgreementMethod";

    /** Algorithm attribute name */
    public final static String ALGORITHM_ATTRIB_NAME = "Algorithm";

    /**
     * Gets the algorithm used for key agreement/derivation.
     * 
     * @return the algorithm used for key agreement/derivation
     */
    public String getAlgortihm();

    /**
     * Sets the algorithm used for key agreement/derivation.
     * 
     * @param newAlgorithm the algorithm used for key agreement/derivation
     */
    public void setAlgorithm(String newAlgorithm);

    /**
     * Gets the Nonce for this key agreement.
     * 
     * @return the Nonce for this key agreement
     */
    public KANonce getNonce();

    /**
     * Sets the Nonce for this key agreement.
     * 
     * @param newNonce the Nonce for this key agreement
     */
    public void setNonce(KANonce newNonce);

    /**
     * Gets the key info for the originator.
     * 
     * @return the key info for the originator
     */
    public OriginatorKeyInfo getOrigniatorKeyInfo();

    /**
     * Sets the key info for the originator.
     * 
     * @param newInfo the key info for the originator
     */
    public void setOriginatorKeyInfo(OriginatorKeyInfo newInfo);

    /**
     * Gets the key info for the recipient.
     * 
     * @return the key info for the recipient
     */
    public RecipientKeyInfo getRecipientKeyInfo();

    /**
     * Sets the key info for the recipient.
     * 
     * @param newInfo the key info for the recipient
     */
    public void setRecipientKeyInfo(RecipientKeyInfo newInfo);
}