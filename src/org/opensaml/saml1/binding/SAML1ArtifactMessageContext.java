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

package org.opensaml.saml1.binding;

import java.util.Collection;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml1.binding.artifact.AbstractSAML1Artifact;
import org.opensaml.saml1.core.Assertion;

/**
 * Extensions to the base SAML message context that carries artifact related information.
 * 
 * @param <InboundMessageType> type of inbound SAML message
 * @param <OutboundMessageType> type of outbound SAML message
 * @param <NameIdentifierType> type of name identifier used for subjects
 */
public interface SAML1ArtifactMessageContext<InboundMessageType extends SAMLObject, OutboundMessageType extends SAMLObject, NameIdentifierType extends SAMLObject>
        extends SAMLMessageContext<InboundMessageType, OutboundMessageType, NameIdentifierType> {

    /**
     * Gets the artifact type used for the issued artifacts.
     * 
     * @return artifact type used for the issued artifacts
     */
    public byte[] getArtifactType();

    /**
     * Sets the artifact type used for the issued artifacts.
     * 
     * @param type artifact type used for the issued artifacts
     */
    public void setArtifactType(byte[] type);

    /**
     * Gets the artifacts to be resolved.
     * 
     * @return artifacts to be resolved
     */
    public Collection<AbstractSAML1Artifact> getArtifacts();

    /**
     * Sets the artifacts to be resolved.
     * 
     * @param artifacts artifacts to be resolved
     */
    public void setArtifacts(Collection<AbstractSAML1Artifact> artifacts);

    /**
     * Gets the SAML assertions referenced by the artifact(s).
     * 
     * @return SAML assertions referenced by the artifact(s)
     */
    public Collection<Assertion> getReferencedAssertions();

    /**
     * Sets the SAML assertions referenced by the artifact(s).
     * 
     * @param assertions SAML assertions referenced by the artifact(s)
     */
    public void setReferencedAssertions(Collection<Assertion> assertions);
}
