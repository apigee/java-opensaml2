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

/**
 * XMLObject representing XML Encryption, version 20021210, EncryptedKey element.
 */
public interface EncryptedKey extends EncryptedType {

    /** Element local name */
    public final static String LOCAL_NAME = "EncryptedKey";
    
    /** Recipient attribute name */
    public final static String RECIPIENT_ATTRIB_NAME = "Recipient";
    
    /**
     * Gets information about who this encryption key is intended for.
     * 
     * @return information about who this encryption key is intended for
     */
    public String getRecipient();
    
    /**
     * Sets information about who this encryption key is intended for.
     * 
     * @param newRecipient information about who this encryption key is intended for
     */
    public void setRecipient(String newRecipient);
    
    /**
     * Gets list of references to content encrypted with this key.
     * 
     * @return list of references to content encrypted with this key
     */
    public ReferenceList getReferenceList();
    
    /**
     * Sets list of references to content encrypted with this key.
     * 
     * @param newList list of references to content encrypted with this key
     */
    public void setReferenceList(ReferenceList newList);
    
    /**
     * Gets a readable name of the key.
     * 
     * @return a readable name of the key
     */
    public CarriedKeyName getCarriedKeyName();
    
    /**
     * Sets a readable name of the key.
     * 
     * @param newName a readable name of the key
     */
    public void setCarriedKeyName(CarriedKeyName newName);
}