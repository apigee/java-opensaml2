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
import org.opensaml.xml.util.DatatypeHelper;

/**
 * SAML 1 Type 0x0002 Artifact. SAML 1, type 2, artifacts contains a 2 byte type code with a value of 1 followed by a 20
 * byte assertion handle followed by an unspecified number of bytes which are a UTF-8 encouded source location string.
 */
public class SAML1ArtifactType0002 extends SAMLArtifact {

    /** Artifact type code (0x0002) */
    public final static byte[] TYPE_CODE = { 0, 2 };

    /** Artifact source location component */
    private String sourceLocation;

    /**
     * Constructor
     * 
     * @param assertionHandle 20 byte assertion handle artifact component
     * @param sourceLocation source location artifact component
     * 
     * @throws IllegalArgumentException thrown if the given assertion handle is not 20 bytes or the source location is
     *             null or empty
     */
    public SAML1ArtifactType0002(byte[] assertionHandle, String sourceLocation) throws IllegalArgumentException {
        super(TYPE_CODE);
        setTypeCode(TYPE_CODE);
        setMessageHandle(assertionHandle);
        setSourceLocation(sourceLocation);
    }

    /**
     * Constructs a SAML 1 artifact from its byte representation.
     * 
     * @param artifact the byte array representing the artifact
     * 
     * @throws IllegalArgumentException thrown if the artifact type is not 0x0002
     */
    public SAML1ArtifactType0002(byte[] artifact) throws IllegalArgumentException {
        super(TYPE_CODE);

        byte[] typeCode = { artifact[0], artifact[1] };
        setTypeCode(typeCode);

        byte[] messageHandle = new byte[20];
        for (int i = 0, j = 2; j < 22; i++, j++) {
            messageHandle[i] = artifact[j];
        }
        setMessageHandle(messageHandle);

        byte[] sourceLocation = new byte[artifact.length - 22];
        for (int i = 0, j = 22; j < artifact.length; i++, j++) {
            sourceLocation[i] = artifact[j];
        }
        setSourceLocation(new String(sourceLocation));
    }

    /** {@inheritDoc} */
    public byte[] getArtifactBytes() {
        byte[] typeCode = getTypeCode();
        byte[] messageHandle = getMessageHandle();
        byte[] sourceLocation = getSourceLocation().getBytes();
        byte[] artifact = new byte[22 + sourceLocation.length];

        artifact[0] = typeCode[0];
        artifact[1] = typeCode[1];

        for (int i = 2, j = 0; i < 22; i++, j++) {
            artifact[i] = messageHandle[j];
        }

        for (int i = 22, j = 0; j < sourceLocation.length; i++, j++) {
            artifact[i] = sourceLocation[j];
        }

        return artifact;
    }

    /**
     * Gets the source location component of this artifact.
     * 
     * @return source location component of this artifact
     */
    public String getSourceLocation() {
        return sourceLocation;
    }

    /**
     * Sets source location component of this artifact.
     * 
     * @param newLocation source location component of this artifact
     * 
     * @throws IllegalArgumentException thrown if the given location is empty or null
     */
    protected void setSourceLocation(String newLocation) throws IllegalArgumentException {
        String location = DatatypeHelper.safeTrimOrNullString(newLocation);
        if (location == null) {
            throw new IllegalArgumentException("Artifact source location may not be a null or empty string");
        }

        sourceLocation = location;
    }
}