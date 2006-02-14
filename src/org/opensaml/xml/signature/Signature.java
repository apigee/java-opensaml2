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

import java.util.List;

import org.opensaml.xml.XMLObject;

/**
 * XMLObject representing XML Digital Signature, version 20020212, Signature element.
 */
public interface Signature extends XMLObject {

    /** Element local name */
    public final static String LOCAL_NAME = "Signature";
    
    /** Id attribute name */
    public final static String ID_ATTRIB_NAME = "Id";

    /**
     * Gets the XML ID of this signature.
     * 
     * @return the XML ID of this signature
     */
    public String getId();

    /**
     * Sets the XML ID of this signature.
     * 
     * @param newId the XML ID of this signature
     */
    public void setId(String newId);

    /**
     * Gets the information about what was signed and what algorithims were used.
     * 
     * @return the information about what was signed and what algorithims were used
     */
    public SignedInfo getSignedInfo();

    /**
     * Sets the information about what was signed and what algorithims were used.
     * 
     * @param newSignedInfo the information about what was signed and what algorithims were used
     */
    public void setSignedInfo(SignedInfo newSignedInfo);

    /**
     * Gets the value of the signature.
     * 
     * @return the value of the signature
     */
    public SignatureValue getSignatureValue();

    // No setSignatureValue as this is a computed value and must be handled by the underlying Digital Signature library

    /**
     * Gets the information about the key(s) used to compute the signature.
     * 
     * @return the information about the key(s) used to compute the signature
     */
    public KeyInfo getKeyInfo();

    /**
     * Sets the information about the key(s) used to compute the signature.
     * 
     * @param newKeyInfo the information about the key(s) used to compute the signature
     */
    public void setKeyInfo(KeyInfo newKeyInfo);

    /**
     * Gets the Object in the signature.
     * 
     * @return the Object in the signature
     */
    public List<SignatureObject> getObjects();    
}