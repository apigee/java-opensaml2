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

import org.opensaml.common.SAMLArtifact;

/**
 * Base type for SAML 1 Artifacts. SAML 1 artifacts contains a 2 byte type code followed by a 20 byte source ID followed
 * by a 20 byte message handle.
 */
public abstract class BaseSAML1Artifact extends SAMLArtifact {
    
    /** Type code required for this artifact */
    private byte[] requiredTypeCode;

    /**
     * Constructor
     * 
     * @param typeCode 2 byte type code of the artifact
     * @param sourceID 20 byte source ID of the artifact
     * @param messageHandle 20 byte assertion handle of the artifact
     * 
     * @throws IllegalArgumentException thrown if the endpoint index, source ID, or message handle arrays are not of the
     *             right size
     */
    protected BaseSAML1Artifact(byte[] typeCode, byte[] sourceID, byte[] messageHandle) throws IllegalArgumentException {
        this.requiredTypeCode = typeCode;
        setTypeCode(typeCode);
        setSourceID(sourceID);
        setMessageHandle(messageHandle);
    }

    /**
     * Constructs a SAML 1 artifact from it's byte array representation.
     * 
     * @param requiredTypeCode the type code required for this artifact
     * @param artifact the byte array representing the artifact
     * 
     * @throws IllegalArgumentException thrown if the artifact is not the right type or lenght (42 bytes)
     */
    protected BaseSAML1Artifact(byte[] requiredTypeCode, byte[] artifact) throws IllegalArgumentException {
        if (artifact.length != 42) {
            throw new IllegalArgumentException("Artifact length must be 42 bytes it was " + artifact.length + "bytes");
        }
        
        this.requiredTypeCode = requiredTypeCode;

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
    
    /** {@inheritDoc} */
    protected void setTypeCode(byte[] newTypeCode) throws IllegalArgumentException {
        if (newTypeCode != requiredTypeCode) {
            throw new IllegalArgumentException("Incorrect type code for this artifact");
        }

        super.setTypeCode(newTypeCode);
    }
}