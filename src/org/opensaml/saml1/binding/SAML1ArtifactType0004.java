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

/**
 * SAML 1.X Type 0x0004 Artifact
 */
public class SAML1ArtifactType0004 extends BaseSAML1Artifact {

    /** SAML 2 artifact type code (0x0004) */
    public final static byte[] TYPE_CODE = { 0, 4 };

    /**
     * Constructor
     * 
     * @param sourceID 20 byte source ID of the artifact
     * @param messageHandle 20 byte assertion handle of the artifact
     * 
     * @throws IllegalArgumentException thrown if the given source ID or message handle are not of the current length
     *             (20 bytes)
     */
    public SAML1ArtifactType0004(byte[] sourceID, byte[] messageHandle) throws IllegalArgumentException {
        super(TYPE_CODE, sourceID, messageHandle);
    }

    /**
     * Constructs a SAML 1 artifact from it's byte array representation.
     * 
     * @param artifact the byte array representing the artifact
     * 
     * @throws IllegalArgumentException thrown if the artifact is not the right type or lenght (42 bytes) or is not of
     *             the correct type (0x0004)
     */
    public SAML1ArtifactType0004(byte[] artifact) throws IllegalArgumentException {
        super(TYPE_CODE, artifact);
    }
}