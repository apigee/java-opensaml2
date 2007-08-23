/*
 * Copyright [2007] [University Corporation for Advanced Internet Development, Inc.]
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

import org.joda.time.DateTime;
import org.opensaml.common.SAMLObject;
import org.opensaml.util.storage.StorageService;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * Basic artifact map implementation that uses a {@link StorageService} to store and retrieve artifacts.
 */
public class BasicSAMLArtifactMap implements SAMLArtifactMap {

    /** Artifact mapping storage. */
    private StorageService<byte[], SAMLArtifactMapEntry> artifactStore;

    /** Storage service partition used by this cache. default: artifact */
    private String partition;

    /** Lifetime of an artifact in milliseconds. */
    private long artifactLifetime;

    /**
     * Constructor.
     * 
     * @param storage artifact mapping storage
     * @param lifetime lifetime of an artifact in milliseconds
     */
    public BasicSAMLArtifactMap(StorageService<byte[], SAMLArtifactMapEntry> storage, long lifetime) {
        artifactStore = storage;
        partition = "artifact";
        artifactLifetime = lifetime;
    }

    /**
     * Constructor.
     * 
     * @param storage artifact mapping storage
     * @param storageParition name of storage service partition to use
     * @param lifetime lifetime of an artifact in milliseconds
     */
    public BasicSAMLArtifactMap(StorageService<byte[], SAMLArtifactMapEntry> storage, String storageParition,
            long lifetime) {
        artifactStore = storage;
        if (!DatatypeHelper.isEmpty(storageParition)) {
            partition = DatatypeHelper.safeTrim(storageParition);
        } else {
            partition = "artifact";
        }
        artifactLifetime = lifetime;
    }

    /** {@inheritDoc} */
    public boolean contains(byte[] artifact) {
        return artifactStore.contains(partition, artifact);
    }

    /** {@inheritDoc} */
    public SAMLArtifactMapEntry get(byte[] artifact) {
        SAMLArtifactMapEntry entry = artifactStore.get(partition, artifact);

        if (entry.isExpired()) {
            remove(artifact);
            return null;
        }

        return entry;
    }

    /** {@inheritDoc} */
    public void put(byte[] artifact, String relyingPartyId, String issuerId, SAMLObject samlMessage) {
        BasicSAMLArtifactMapEntry artifactEntry = new BasicSAMLArtifactMapEntry(artifact, issuerId, relyingPartyId,
                samlMessage, artifactLifetime);
        artifactStore.put(partition, artifact, artifactEntry);
    }

    /** {@inheritDoc} */
    public void remove(byte[] artifact) {
        artifactStore.remove(partition, artifact);
    }

    /** Basic implementation of {@link SAMLArtifactMapEntry}. */
    public class BasicSAMLArtifactMapEntry implements SAMLArtifactMapEntry {

        /** SAML artifact being mapped. */
        private byte[] artifact;

        /** Entity ID of the issuer of the artifact. */
        private String issuer;

        /** Entity ID of the receiver of the artifact. */
        private String relyingParty;

        /** SAML object mapped to the artifact. */
        private SAMLObject samlMessage;

        /** Time this artifact entry expires. */
        private DateTime expirationTime;

        public BasicSAMLArtifactMapEntry(byte[] artifact, String issuer, String relyingParty, SAMLObject saml,
                long lifetime) {
            this.artifact = artifact;
            this.issuer = issuer;
            this.relyingParty = relyingParty;
            samlMessage = saml;
            expirationTime = new DateTime().plus(lifetime);
        }

        /** {@inheritDoc} */
        public byte[] getArtifact() {
            return artifact;
        }

        /** {@inheritDoc} */
        public String getIssuerId() {
            return issuer;
        }

        /** {@inheritDoc} */
        public String getRelyingPartyId() {
            return relyingParty;
        }

        /** {@inheritDoc} */
        public SAMLObject getSamlMessage() {
            return samlMessage;
        }

        /** {@inheritDoc} */
        public DateTime getExpirationTime() {
            return expirationTime;
        }

        /** {@inheritDoc} */
        public boolean isExpired() {
            return expirationTime.isBeforeNow();
        }
        
        /** {@inheritDoc} */
        public void onExpire() {
            
        }
    }
}