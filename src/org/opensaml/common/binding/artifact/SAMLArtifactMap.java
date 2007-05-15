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

import org.opensaml.common.SAMLObject;
import org.opensaml.util.ExpiringObject;

/**
 * Maps an artifact to a SAML message and back again.
 * 
 * Artifacts must be thread safe.
 */
public interface SAMLArtifactMap {

    /**
     * Checks if a given artifact has a map entry.
     * 
     * @param artifact the artifact to check
     * 
     * @return true of this map has an entry for the given artifact, false it not
     */
    public boolean contains(byte[] artifact);

    /**
     * Creates a mapping between a given artifact and the SAML message to which it maps.
     * 
     * @param artifact the artifact
     * @param relyingPartyId ID of the party the artifact was sent to
     * @param issuerId ID of the issuer of the artifact
     * @param samlMessage the SAML message
     */
    public void put(byte[] artifact, String relyingPartyId, String issuerId, SAMLObject samlMessage);

    /**
     * Retrieves the entry for the given artifact without removing that entry from the map.
     * 
     * @param artifact artifact to retrieve the map entry for
     * 
     * @return the map entry for the artifact or null
     */
    public SAMLArtifactMapEntry peek(byte[] artifact);

    /**
     * Gets the artifact entry for the given artifact. This operation will remove the artifact entry in order to ensure
     * the one-time use semantics of artifacts.
     * 
     * @param artifact the artifact to retrive the entry for
     * 
     * @return the entry or null if the artifact has already expired or did not exist
     */
    public SAMLArtifactMapEntry get(byte[] artifact);

    /**
     * Represents a mapping between an artifact a SAML message with some associated metadata.
     */
    public interface SAMLArtifactMapEntry extends ExpiringObject {

        /**
         * Gets the artifact that maps to the SAML message.
         * 
         * @return artifact that maps to the SAML message
         */
        public byte[] getArtifact();

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
         * Gets the SAML message the artifact maps to.
         * 
         * @return SAML message the artifact maps to
         */
        public SAMLObject getSamlMessage();
    }
}