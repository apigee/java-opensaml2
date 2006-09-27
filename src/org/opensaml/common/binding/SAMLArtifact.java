/*
 * Copyright [2006] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.common.binding;

import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.encoders.Base64;

/**
 * Base class for SAML artifacts.
 */
public abstract class SAMLArtifact {

    /** 2 byte artifact type code expected for the artifact */
    private byte[] expectedTypeCode;
    
    /** 2 byte artifact type code */
    private byte[] typeCode;
    
    /** 20 byte artifact message handle */
    private byte[] messageHandle;
    
    /**
     * Constructor
     *
     * @param requiredTypeCode the artifact type code required for this artifact
     * 
     * @throws IllegalArgumentException thrown if the given type code is not two bytes in length
     */
    protected SAMLArtifact(byte[] requiredTypeCode) throws IllegalArgumentException{
        if(requiredTypeCode.length != 2){
            throw new IllegalArgumentException("Artifact type code must be two bytes in length");
        }
        this.expectedTypeCode = requiredTypeCode;
    }
    
    /**
     * Gets the bytes for the artifact.
     * 
     * @return the bytes for the artifact
     */
    abstract public byte[] getArtifactBytes();
    
    /**
     * Gets the 2 byte type code for this artifact.
     * 
     * @return the type code for this artifact
     */
    public byte[] getTypeCode(){
        return typeCode;
    }
    
    /**
     * Sets the 2 byte type code for this artifact.
     * 
     * @param newTypeCode 2 byte type code for this artifact
     * 
     * @throws IllegalArgumentException thrown if the given type code is not two bytes
     */
    protected void setTypeCode(byte[] newTypeCode) throws IllegalArgumentException{
        if(newTypeCode != expectedTypeCode){
            throw new IllegalArgumentException("Unexpected artifact type code");
        }
        typeCode = newTypeCode;
    }
    
    /**
     * Gets the 20 byte message/assertion handle of the artifact.
     * 
     * @return the source ID of the artifact
     */
    public byte[] getMessageHandle(){
        return messageHandle;
    }
    
    /**
     * Sets the 20 byte message/assertion handle of the artifact.
     * 
     * @param newHandle 20 byte message/assertion handle of the artifact
     * 
     * @throws IllegalArgumentException thrown if the given message/assertion handle is not 20 bytes
     */
    protected void setMessageHandle(byte[] newHandle) throws IllegalArgumentException{
        if(newHandle.length != 20){
            throw new IllegalArgumentException("Artifact message/assertion handle must be 20 bytes long");
        }
        messageHandle = newHandle;
    }
    
    /**
     * Gets the Base64 encoded artifact.
     * 
     * @return Base64 encoded artifact.
     */
    public String base64Encode(){
        return new String(Base64.encode(getArtifactBytes()));
    }
    
    /**
     * Gets the hex encoded artifact.
     * 
     * @return hex encoded artifact
     */
    public String hexEncode(){
        return new String(Hex.encode(getArtifactBytes()));
    }
    
    /** {@inheritDoc} */
    public boolean equals( Object o ) {
        if(o instanceof SAMLArtifact){
            SAMLArtifact otherArtifact = (SAMLArtifact) o;
            return getArtifactBytes() == otherArtifact.getArtifactBytes();
        }
        
        return false;
    }
    
    /** {@inheritDoc} */
    public int hashCode() {
        return base64Encode().hashCode();
    }
}