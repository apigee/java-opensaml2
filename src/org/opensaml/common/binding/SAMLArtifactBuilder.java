
package org.opensaml.common.binding;


/**
 * Builder for typed SAML artifacts.
 * 
 * Builders must be thread safe and reusable.
 */
public interface SAMLArtifactBuilder<ArtifactType extends SAMLArtifact> {

    /**
     * Builds an empty artifact of a specific type.
     * 
     * @param relyingParty the relying party the artifact is for
     * 
     * @return the artifact
     */
    public ArtifactType buildArtifact(String relyingParty);
}