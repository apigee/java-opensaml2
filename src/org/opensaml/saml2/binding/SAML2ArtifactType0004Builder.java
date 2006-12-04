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

package org.opensaml.saml2.binding;

import org.opensaml.common.binding.SAMLArtifactBuilder;

/**
 * SAML 2, type 0x0004, artifact builder.
 */
public class SAML2ArtifactType0004Builder implements SAMLArtifactBuilder<SAML2ArtifactType0004> {

    /** {@inheritDoc} */
    public SAML2ArtifactType0004 buildArtifact(String relyingParty) {
        SAML2ArtifactType0004 artifact = new SAML2ArtifactType0004();
        artifact.setSourceID(relyingParty.getBytes());

        return artifact;
    }
    
    /** {@inheritDoc} */
    public SAML2ArtifactType0004 buildArtifact(byte[] artifact){
        return SAML2ArtifactType0004.parseArtifact(artifact);
    }
}