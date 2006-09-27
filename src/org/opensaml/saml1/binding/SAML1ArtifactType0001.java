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

    /**
     * Constructor
     * 
     * @param sourceID 20 byte source ID of the artifact
     * @param messageHandle 20 byte assertion handle of the artifact
     * 
     * @throws IllegalArgumentException thrown if the given source ID or message handle are not of the current length
     *             (20 bytes)
     */
    public SAML1ArtifactType0001(byte[] sourceID, byte[] messageHandle) throws IllegalArgumentException {
        super(TYPE_CODE);
        setTypeCode(TYPE_CODE);
        setSourceID(sourceID);
        setMessageHandle(messageHandle);
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
        super(TYPE_CODE);

        if (artifact.length != 42) {
            throw new IllegalArgumentException("Artifact length must be 42 bytes it was " + artifact.length + "bytes");
        }

        byte[] typeCode = { artifact[0], artifact[1] };
        if (typeCode.length != 2) {
            throw new IllegalArgumentException("Artifact type code must be two bytes");
        }
        setTypeCode(typeCode);

        byte[] sourceID = new byte[20];
        for (int i = 0, j = 2; j < 22; i++, j++) {
            sourceID[i] = artifact[j];
        }
        setSourceID(sourceID);

        byte[] messageHandle = new byte[20];
        for (int i = 0, j = 22; j < 42; i++, j++) {
            messageHandle[i] = artifact[j];
        }
        setMessageHandle(messageHandle);
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

    /** {@inheritDoc} */
    public byte[] getArtifactBytes() {
        byte[] artifact = new byte[42];

        byte[] typeCode = getTypeCode();
        byte[] sourceID = getSourceID();
        byte[] messageHandle = getMessageHandle();

        artifact[0] = typeCode[0];
        artifact[1] = typeCode[1];

        for (int i = 2, j = 0; i < 22; i++, j++) {
            artifact[i] = sourceID[j];
        }

        for (int i = 22, j = 0; i < 42; i++, j++) {
            artifact[i] = messageHandle[j];
        }

        return artifact;
    }
}