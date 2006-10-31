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

import java.util.Map;

import javolution.util.FastMap;

import org.opensaml.saml1.binding.AbstractSAML1Artifact;
import org.opensaml.saml1.binding.SAML1ArtifactType0001;
import org.opensaml.saml1.binding.SAML1ArtifactType0001Builder;
import org.opensaml.saml1.binding.SAML1ArtifactType0002;
import org.opensaml.saml1.binding.SAML1ArtifactType0002Builder;
import org.opensaml.saml2.binding.AbstractSAML2Artifact;

/**
 * Factory for producing SAML artifacts.
 */
public class SAMLArtifactFactory {

    /** SAML 1 Artifact Builders */
    private FastMap<String, SAMLArtifactBuilder<? extends AbstractSAML1Artifact>> saml1Builders;

    /** SAML 2 Artifact Builders */
    private FastMap<String, SAMLArtifactBuilder<? extends AbstractSAML2Artifact>> saml2Builders;

    /** Constructor */
    public SAMLArtifactFactory() {
        saml1Builders = new FastMap<String, SAMLArtifactBuilder<? extends AbstractSAML1Artifact>>();
        saml1Builders.put(new String(SAML1ArtifactType0001.TYPE_CODE), new SAML1ArtifactType0001Builder());
        saml1Builders.put(new String(SAML1ArtifactType0002.TYPE_CODE), new SAML1ArtifactType0002Builder());

        saml2Builders = new FastMap<String, SAMLArtifactBuilder<? extends AbstractSAML2Artifact>>();
    }

    /**
     * Builds a SAML 1 artifact of the given type for the given SAML message destined to the given relying party
     * operating in the given role.
     * 
     * @param artifactType the type of artifact to generate
     * @param relyingParty the relying party the SAML message is destined for
     * @param relyingPartyRole the role the relying party is operating in during this request
     * 
     * @return the SAML 1 artifact for the given message
     * 
     * @throws BindingException thrown if no builder is registered for the given type
     */
    public AbstractSAML1Artifact buildSAML1Artifact(byte[] artifactType, String relyingParty) throws BindingException {
        SAMLArtifactBuilder<? extends AbstractSAML1Artifact> artifactBuilder = saml1Builders.get(new String(
                artifactType));
        if (artifactBuilder == null) {
            throw new BindingException("No SAML 1 artifact builder registered for type " + artifactType);
        }

        return artifactBuilder.buildArtifact(relyingParty);
    }

    /**
     * Gets the registered SAML 1 artifact builders. The map key is the string representation of the artifact type
     * (using the default encoding) and the value is the builder. This map is mutable and new builders my be registered
     * through it. Existing builders may be replaced by registering a new builder with the same type.
     * 
     * @return registered SAML 1 artifact builders
     */
    public Map<String, SAMLArtifactBuilder<? extends AbstractSAML1Artifact>> getSAML1ArtifactBuilders() {
        return saml1Builders;
    }

    /**
     * Builds a SAML 2 artifact of the given type for the given SAML message destined to the given relying party
     * operating in the given role.
     * 
     * @param artifactType the type of artifact to generate
     * @param relyingParty the relying party the SAML message is destined for
     * @param relyingPartyRole the role the relying party is operating in during this request
     * 
     * @return the SAML 2 artifact for the given message
     * 
     * @throws BindingException thrown if no builder is registered for the given type
     */
    public AbstractSAML2Artifact buildSAML2Artifact(byte[] artifactType, String relyingParty) throws BindingException {
        SAMLArtifactBuilder<? extends AbstractSAML2Artifact> artifactBuilder = saml2Builders.get(new String(
                artifactType));
        if (artifactBuilder == null) {
            throw new BindingException("No SAML 2 artifact builder registered for type " + artifactType);
        }

        return artifactBuilder.buildArtifact(relyingParty);
    }

    /**
     * Gets the registered SAML 2 artifact builders. The map key is the string representation of the artifact type
     * (using the default encoding) and the value is the builder. This map is mutable and new builders my be registered
     * through it. Existing builders may be replaced by registering a new builder with the same type.
     * 
     * @return registered SAML 2 artifact builders
     */
    public Map<String, SAMLArtifactBuilder<? extends AbstractSAML2Artifact>> getSAML2ArtifactBuilders() {
        return saml2Builders;
    }
}