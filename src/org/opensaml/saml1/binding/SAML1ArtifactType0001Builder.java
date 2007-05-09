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

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.opensaml.common.binding.artifact.SAMLArtifactBuilder;

/**
 * Builder of SAML 1, type 0x001, artifacts.
 */
public class SAML1ArtifactType0001Builder implements SAMLArtifactBuilder<SAML1ArtifactType0001> {

    /** Hash algorithm used to construct the source ID. */
    public static final String HASH_ALGORHTM = "SHA-1";

    /** {@inheritDoc} */
    public SAML1ArtifactType0001 buildArtifact(String relyingParty) {

        SAML1ArtifactType0001 artifact = new SAML1ArtifactType0001();

        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORHTM);
            return new SAML1ArtifactType0001(md.digest(relyingParty.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException ex) {

        } catch (UnsupportedEncodingException e) {

        }
        
        return artifact;
    }

    /** {@inheritDoc} */
    public SAML1ArtifactType0001 buildArtifact(byte[] artifact) {
        return SAML1ArtifactType0001.parseArtifact(artifact);
    }
}