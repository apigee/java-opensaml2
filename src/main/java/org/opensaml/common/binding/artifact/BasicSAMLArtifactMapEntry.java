/*
 * Copyright 2008 University Corporation for Advanced Internet Development, Inc.
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
import org.opensaml.common.binding.artifact.SAMLArtifactMap.SAMLArtifactMapEntry;
import org.opensaml.util.storage.AbstractExpiringObject;

/** Basic implementation of {@link SAMLArtifactMapEntry}. */
public class BasicSAMLArtifactMapEntry extends AbstractExpiringObject implements SAMLArtifactMapEntry {

    /** Serial version UID. */
    private static final long serialVersionUID = 4872905727625023964L;

    /** SAML artifact being mapped. */
    private String artifact;

    /** Entity ID of the issuer of the artifact. */
    private String issuer;

    /** Entity ID of the receiver of the artifact. */
    private String relyingParty;

    /** SAML message mapped to the artifact. */
    private transient SAMLObject message;

    /** Serialized SAML object mapped to the artifact. */
    private String serializedMessage;

    /**
     * Constructor.
     * 
     * @param artifact artifact associated with the message
     * @param issuer issuer of the artifact
     * @param relyingParty receiver of the artifact
     * @param saml serialized SAML message mapped to the artifact
     * @param lifetime lifetime of the artifact
     */
    public BasicSAMLArtifactMapEntry(String artifact, String issuer, String relyingParty, String saml, long lifetime) {
        super(new DateTime().plus(lifetime));
        this.artifact = artifact;
        this.issuer = issuer;
        this.relyingParty = relyingParty;
        serializedMessage = saml;
    }

    /** {@inheritDoc} */
    public String getArtifact() {
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
        return message;
    }

    /**
     * Sets the SAML message mapped to the artifact.
     * 
     * @param saml SAML message mapped to the artifact
     */
    void setSAMLMessage(SAMLObject saml) {
        message = saml;
    }

    /**
     * Gets the serialized form of the SAML message.
     * 
     * @deprecated replacement is: {@link #getSerializedMessage()}
     * 
     * @return serialized form of the SAML message
     * 
     */
    String getSeralizedMessage() {
        return getSerializedMessage();
    }
    
    /**
     * Gets the serialized form of the SAML message.
     * 
     * @return serialized form of the SAML message
     */
    String getSerializedMessage() {
        return serializedMessage;
    }
}