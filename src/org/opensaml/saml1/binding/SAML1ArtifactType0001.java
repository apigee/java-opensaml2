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

package org.opensaml.saml1.binding;

import java.util.Arrays;

import org.opensaml.common.binding.SAMLArtifact;

/**
 * SAML 1.X Type 0x0001 Artifact. SAML 1, type 1, artifacts contains a 2 byte type code with a value of 1 followed by a
 * 20 byte source ID followed by a 20 byte assertion handle.
 */
public class SAML1ArtifactType0001 extends SAMLArtifact {

    /** Artifact type code (0x0001) */
    public final static byte[] TYPE_CODE = { 0, 1 };

    /** 20 byte artifact source ID */
    private byte[] sourceID;
    
    /** 20 byte assertion handle */
    private byte[] assertionHandle;

    /**
     * Constructor
     * 
     * @param sourceID 20 byte source ID of the artifact
     * @param messageHandle 20 byte assertion handle of the artifact
     * 
     * @throws IllegalArgumentException thrown if the given source ID or message handle are not of the current length
     *             (20 bytes)
     */
    public SAML1ArtifactType0001(byte[] sourceID, byte[] messageHandle, byte[] assertionHandle) throws IllegalArgumentException {
        setTypeCode(TYPE_CODE);        
        setSourceID(sourceID);
    }

    /**
     * Constructs a SAML 1 artifact from it's byte array representation.
     * 
     * @param artifact the byte array representing the artifact
     * 
     * @throws IllegalArgumentException thrown if the artifact is not the right type or lenght (42 bytes) or is not of
     *             the correct type (0x0001)
     */
    public SAML1ArtifactType0001(byte[] artifact) throws IllegalArgumentException {
        if (artifact.length != 42) {
            throw new IllegalArgumentException("Artifact length must be 42 bytes it was " + artifact.length + "bytes");
        }

        byte[] typeCode = { artifact[0], artifact[1] };
        if(Arrays.equals(typeCode, TYPE_CODE)){
            throw new IllegalArgumentException("Artifact is not of appropriate type.");
        }
        setTypeCode(typeCode);

        byte[] sourceID = new byte[20];
        System.arraycopy(artifact, 2, sourceID, 0, 20);
        setSourceID(sourceID);

        byte[] assertionHandle = new byte[20];
        System.arraycopy(artifact, 22, assertionHandle, 0, 20);
        setAssertionHandle(assertionHandle);
    }

    /**
     * Gets the 20 byte source ID of the artifact.
     * 
     * @return the source ID of the artifact
     */
    public byte[] getSourceID() {
        return sourceID;
    }
    
    /**
     * Sets the 20 byte source ID of the artifact.
     * 
     * @param newSourceID 20 byte source ID of the artifact
     * 
     * @throws IllegalArgumentException thrown if the given source ID is not 20 bytes
     */
    protected void setSourceID(byte[] newSourceID) throws IllegalArgumentException {
        if (newSourceID.length != 20) {
            throw new IllegalArgumentException("Artifact source ID must be 20 bytes long");
        }
        sourceID = newSourceID;
    }
    
    /**
     * Gets the artifiact's 20 byte assertion handle.
     * 
     * @return artifiact's 20 byte assertion handle
     */
    public byte[] getAssertionHandle(){
        return assertionHandle;
    }
    
    /**
     * Sets the artifiact's 20 byte assertion handle.
     * 
     * @param assertionHandle artifiact's 20 byte assertion handle
     */
    public void setAssertionHandle(byte[] assertionHandle){
        if(assertionHandle.length != 20){
            throw new IllegalArgumentException("Artifact assertion handle must be 20 bytes long");
        }
    }
    
    /** {@inheritDoc} */
    public byte[] getRemainingArtifact(){
        byte[] remainingArtifact = new byte[40];

        System.arraycopy(getSourceID(), 0, remainingArtifact, 0, 20);
        System.arraycopy(getAssertionHandle(), 0, remainingArtifact, 20, 20);
        
        return remainingArtifact;
    }
}