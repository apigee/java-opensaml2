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
 * XMLObject representing XML Digital Signature, version 20020212, PGPData element.
 */
public interface PGPData extends XMLObject {
    
    /** Element local name */
    public final static String LOCAL_NAME = "PGPData";
    
    /**
     * Gets the PGP key ID.
     * 
     * @return the PGP key ID
     */
    public PGPKeyID getKeyID();
    
    /**
     * Sets the PGP key ID.
     * 
     * @param newKeyID the PGP key ID
     */
    public void setKeyID(PGPKeyID newKeyID);
    
    /**
     * Gets the PGP key packet.
     * 
     * @return the PGP key packet
     */
    public PGPKeyPacket getKeyPacket();
    
    /**
     * Sets the PGP key packet.
     * 
     * @param newKeyPacket the PGP key packet
     */
    public void setKeyPacket(PGPKeyPacket newKeyPacket);
}