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

package org.opensaml.common.binding.artifact;

import java.util.HashMap;
import java.util.Map;

import org.opensaml.common.SAMLVersion;
import org.opensaml.common.binding.BindingException;
import org.opensaml.saml1.binding.AbstractSAML1Artifact;
import org.opensaml.saml1.binding.SAML1ArtifactType0001;
import org.opensaml.saml1.binding.SAML1ArtifactType0001Builder;
import org.opensaml.saml1.binding.SAML1ArtifactType0002;
import org.opensaml.saml1.binding.SAML1ArtifactType0002Builder;
import org.opensaml.saml2.binding.artifact.AbstractSAML2Artifact;
import org.opensaml.saml2.binding.artifact.SAML2ArtifactType0004;
import org.opensaml.saml2.binding.artifact.SAML2ArtifactType0004Builder;

/**
 * Factory for producing SAML artifacts.
 */
public class SAMLArtifactFactory {

    /** SAML 1 Artifact Builders. */
    private HashMap<String, SAMLArtifactBuilder<? extends AbstractSAML1Artifact>> saml1Builders;

    /** SAML 2 Artifact Builders. */
    private HashMap<String, SAMLArtifactBuilder<? extends AbstractSAML2Artifact>> saml2Builders;

    /** Constructor. */
    public SAMLArtifactFactory() {
        saml1Builders = new HashMap<String, SAMLArtifactBuilder<? extends AbstractSAML1Artifact>>();
        saml1Builders.put(new String(SAML1ArtifactType0001.TYPE_CODE), new SAML1ArtifactType0001Builder());
        saml1Builders.put(new String(SAML1ArtifactType0002.TYPE_CODE), new SAML1ArtifactType0002Builder());

        saml2Builders = new HashMap<String, SAMLArtifactBuilder<? extends AbstractSAML2Artifact>>();
        saml2Builders.put(new String(SAML2ArtifactType0004.TYPE_CODE), new SAML2ArtifactType0004Builder());
    }

    /**
     * Builds the the artifact for a specific version of SAML and artifact type. Build artifacts are "empty" and must
     * have their appropriate data set before they can be used.
     * 
     * @param samlVersion SAML version
     * @param artifactType the type code of the artifact
     * @param relyingParty the party the artifact is meant for
     * 
     * @return the empty artifact
     * 
     * @throws BindingException thrown if the artifact can not be created usually because no builder was registered for
     *             the given version/type
     */
    public SAMLArtifact buildArtifact(SAMLVersion samlVersion, byte[] artifactType, String relyingParty)
            throws BindingException {
        SAMLArtifactBuilder builder = getBuilder(samlVersion, artifactType);

        if (builder == null) {
            throw new BindingException("No SAML " + samlVersion.toString() + " artifact builder registered for type "
                    + artifactType);
        }

        return builder.buildArtifact(relyingParty);
    }

    /**
     * Builds a populated artifact from its byte representation.
     * 
     * @param samlVersion SAML version
     * @param artifact byte representation of the artifact
     * 
     * @return artifact representation of the artifact
     * 
     * @throws BindingException thrown if the given artifact can not be built, usually because there is not builder for
     *             the given SAML version/artifact type
     */
    public SAMLArtifact buildArtifact(SAMLVersion samlVersion, byte[] artifact) throws BindingException {
        byte[] artifactType = { artifact[0], artifact[1] };

        SAMLArtifactBuilder builder = getBuilder(samlVersion, artifactType);

        if (builder == null) {
            throw new BindingException("No SAML " + samlVersion.toString() + " artifact builder registered for type "
                    + artifactType);
        }

        return builder.buildArtifact(artifact);
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
     * Gets the registered SAML 2 artifact builders. The map key is the string representation of the artifact type
     * (using the default encoding) and the value is the builder. This map is mutable and new builders my be registered
     * through it. Existing builders may be replaced by registering a new builder with the same type.
     * 
     * @return registered SAML 2 artifact builders
     */
    public Map<String, SAMLArtifactBuilder<? extends AbstractSAML2Artifact>> getSAML2ArtifactBuilders() {
        return saml2Builders;
    }

    /**
     * Gets the artifact builder for a particular version of SAML and artifact type.
     * 
     * @param samlVersion the SAML version
     * @param artifactType the artifact type
     * 
     * @return artifact builder for the given SAML version/artifact type or null if no builder is registered
     */
    protected SAMLArtifactBuilder getBuilder(SAMLVersion samlVersion, byte[] artifactType) {
        String type = new String(artifactType);
        if (samlVersion == SAMLVersion.VERSION_10 || samlVersion == SAMLVersion.VERSION_11) {
            return saml1Builders.get(type);
        } else if (samlVersion == SAMLVersion.VERSION_20) {
            return saml2Builders.get(type);
        }

        return null;
    }
}