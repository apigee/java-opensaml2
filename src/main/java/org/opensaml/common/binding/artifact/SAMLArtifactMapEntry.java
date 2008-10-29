package org.opensaml.common.binding.artifact;

import org.opensaml.common.SAMLObject;
import org.opensaml.util.storage.ExpiringObject;

/**
 * Represents a mapping between an artifact a SAML message with some associated metadata. 
 */
public interface SAMLArtifactMapEntry extends ExpiringObject {

    /**
     * Gets the artifact that maps to the SAML message.
     * 
     * @return artifact that maps to the SAML message
     */
    public String getArtifact();

    /**
     * Gets the ID of the issuer of the artifact.
     * 
     * @return ID of the issuer of the artifact
     */
    public String getIssuerId();

    /**
     * Gets the ID of the relying party the artifact was sent to.
     * 
     * @return ID of the relying party the artifact was sent to
     */
    public String getRelyingPartyId();

    /**
     * Gets SAML message the artifact maps to.
     * 
     * @return SAML message the artifact maps to
     */
    public SAMLObject getSamlMessage();
}