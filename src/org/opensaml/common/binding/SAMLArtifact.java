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

import java.security.SecureRandom;

import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

/**
 * Base class for SAML artifacts.
 */
public abstract class SAMLArtifact {

    /** 2 byte artifact type code */
    private byte[] typeCode;

    /** Random number generator */
    protected SecureRandom randomGen;

    /**
     * Constructor
     * 
     * @param typeCode the artifact type code
     * 
     * @throws IllegalArgumentException thrown if the given type code is not two bytes in length
     */
    protected SAMLArtifact(byte[] typeCode) throws IllegalArgumentException {
        setTypeCode(typeCode);
        try {
            randomGen = SecureRandom.getInstance("SHA1PRNG");
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to initialize random number generator", e);
        }
    }

    /**
     * Gets the bytes for the artifact.
     * 
     * @return the bytes for the artifact
     */
    public byte[] getArtifactBytes() {
        byte[] remainingArtifact = getRemainingArtifact();
        byte[] artifact = new byte[2 + remainingArtifact.length];

        System.arraycopy(getTypeCode(), 0, artifact, 0, 2);
        System.arraycopy(remainingArtifact, 0, artifact, 2, artifact.length);

        return artifact;
    }

    /**
     * Gets the 2 byte type code for this artifact.
     * 
     * @return the type code for this artifact
     */
    public byte[] getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the 2 byte type code for this artifact.
     * 
     * @param newTypeCode 2 byte type code for this artifact
     * 
     * @throws IllegalArgumentException thrown if the given type code is not two bytes
     */
    protected void setTypeCode(byte[] newTypeCode) throws IllegalArgumentException {
        typeCode = newTypeCode;
    }

    /**
     * Gets the artifact bytes minus the type code.
     * 
     * @return artifact bytes minus the type code
     */
    abstract public byte[] getRemainingArtifact();

    /**
     * Gets the Base64 encoded artifact.
     * 
     * @return Base64 encoded artifact.
     */
    public String base64Encode() {
        return new String(Base64.encode(getArtifactBytes()));
    }

    /**
     * Gets the hex encoded artifact.
     * 
     * @return hex encoded artifact
     */
    public String hexEncode() {
        return new String(Hex.encode(getArtifactBytes()));
    }

    /** {@inheritDoc} */
    public boolean equals(Object o) {
        if (o instanceof SAMLArtifact) {
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