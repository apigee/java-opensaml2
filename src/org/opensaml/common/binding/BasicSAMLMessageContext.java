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

package org.opensaml.common.binding;

import javax.xml.namespace.QName;

import org.joda.time.DateTime;
import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.ws.message.BaseMessageContext;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * Base implemention of {@link SAMLMessageContext}.
 * 
 * @param <InboundMessageType> type of inbound SAML message
 * @param <OutboundMessageType> type of outbound SAML message
 * @param <NameIdentifierType> type of name identifier used for subjects
 */
public class BasicSAMLMessageContext<InboundMessageType extends SAMLObject, OutboundMessageType extends SAMLObject, NameIdentifierType extends SAMLObject>
        extends BaseMessageContext implements SAMLMessageContext<InboundMessageType, OutboundMessageType, NameIdentifierType> {

    /** Asserting party's entity ID. */
    private String assertingPartyId;

    /** Asserting party's metadata. */
    private EntityDescriptor assertingPartyMetadata;

    /** Asserting party's role. */
    private QName assertingPartyRole;

    /** Asserting party's role metadata. */
    private RoleDescriptor assertingPartyRoleMetadata;

    /** Inbound SAML message. */
    private InboundMessageType inboundSAMLMessage;

    /** Whether the inbound SAML message has been authenticated. */
    private boolean inboundSAMLMessageAuthenticated;

    /** Inbound SAML message's ID. */
    private String inboundSAMLMessageId;

    /** Inbound SAML message's issue instant. */
    private DateTime inboundSAMLMessageIssueInstant;

    /** Inbound SAML protocol. */
    private String inboundSAMLProtocol;

    /** Metadata provider used to lookup entity information. */
    private MetadataProvider metdataProvider;

    /** Outbound SAML message. */
    private OutboundMessageType outboundSAMLMessage;

    /** Outbound SAML message's ID. */
    private String outboundSAMLMessageId;

    /** Outbound SAML message's issue instant. */
    private DateTime outboundSAMLMessageIssueInstant;

    /** Outboud SAML message signing credential. */
    private Credential outboundSAMLMessageSigningCredential;

    /** Outbound SAML procotol. */
    private String outboundSAMLProtocol;

    /** Message relay state. */
    private String relayState;

    /** Relying party endpoint. */
    private Endpoint relyingPartyEndpoint;

    /** Relying party entity ID. */
    private String relyingPartyEntityId;

    /** Relying party's metadata. */
    private EntityDescriptor relyingPartyMetadata;

    /** Relying party role. */
    private QName relyingPartyRole;

    /** Relying party role's metadata. */
    private RoleDescriptor relyingPartyRoleMetadata;

    /** {@inheritDoc} */
    public String getAssertingPartyEntityId() {
        return assertingPartyId;
    }

    /** {@inheritDoc} */
    public EntityDescriptor getLocalEntityMetadata() {
        return assertingPartyMetadata;
    }

    /** {@inheritDoc} */
    public QName getLocalEntityRole() {
        return assertingPartyRole;
    }

    /** {@inheritDoc} */
    public RoleDescriptor getLocalEntityRoleMetadata() {
        return assertingPartyRoleMetadata;
    }

    /** {@inheritDoc} */
    public InboundMessageType getInboundSAMLMessage() {
        return inboundSAMLMessage;
    }

    /** {@inheritDoc} */
    public String getInboundSAMLMessageId() {
        return inboundSAMLMessageId;
    }

    /** {@inheritDoc} */
    public DateTime getInboundSAMLMessageIssueInstant() {
        return inboundSAMLMessageIssueInstant;
    }

    /** {@inheritDoc} */
    public String getInboundSAMLProtcol() {
        return inboundSAMLProtocol;
    }

    /** {@inheritDoc} */
    public MetadataProvider getMetadataProvider() {
        return metdataProvider;
    }

    /** {@inheritDoc} */
    public Credential getOuboundSAMLMessageSigningCredential() {
        return outboundSAMLMessageSigningCredential;
    }

    /** {@inheritDoc} */
    public OutboundMessageType getOutboundSAMLMessage() {
        return outboundSAMLMessage;
    }

    /** {@inheritDoc} */
    public String getOutboundSAMLMessageId() {
        return outboundSAMLMessageId;
    }

    /** {@inheritDoc} */
    public DateTime getOutboundSAMLMessageIssueInstant() {
        return outboundSAMLMessageIssueInstant;
    }

    /** {@inheritDoc} */
    public String getOutboundSAMLProtcol() {
        return outboundSAMLProtocol;
    }

    /** {@inheritDoc} */
    public String getRelayState() {
        return relayState;
    }

    /** {@inheritDoc} */
    public Endpoint getPeerEntityEndpoint() {
        return relyingPartyEndpoint;
    }

    /** {@inheritDoc} */
    public String getRelyingPartyEntityId() {
        return relyingPartyEntityId;
    }

    /** {@inheritDoc} */
    public EntityDescriptor getPeerEntityMetadata() {
        return relyingPartyMetadata;
    }

    /** {@inheritDoc} */
    public QName getPeerEntityRole() {
        return relyingPartyRole;
    }

    /** {@inheritDoc} */
    public RoleDescriptor getPeerEntityRoleMetadata() {
        return relyingPartyRoleMetadata;
    }

    /** {@inheritDoc} */
    public boolean isInboundSAMLMessageAuthenticated() {
        return inboundSAMLMessageAuthenticated;
    }

    /** {@inheritDoc} */
    public void setAssertingPartyEntityId(String id) {
        assertingPartyId = DatatypeHelper.safeTrimOrNullString(id);
    }

    /** {@inheritDoc} */
    public void setLocalEntityMetadata(EntityDescriptor metadata) {
        assertingPartyMetadata = metadata;
    }

    /** {@inheritDoc} */
    public void setLocalEntityRole(QName role) {
        assertingPartyRole = role;
    }

    /** {@inheritDoc} */
    public void setLocalEntityRoleMetadata(RoleDescriptor role) {
        assertingPartyRoleMetadata = role;
    }

    /** {@inheritDoc} */
    public void setInboundSAMLMessage(InboundMessageType message) {
        inboundSAMLMessage = message;
    }

    /** {@inheritDoc} */
    public void setInboundSAMLMessageAuthenticated(boolean isAuthenticated) {
        inboundSAMLMessageAuthenticated = isAuthenticated;
    }

    /** {@inheritDoc} */
    public void setInboundSAMLMessageId(String id) {
        inboundSAMLMessageId = DatatypeHelper.safeTrimOrNullString(id);
    }

    /** {@inheritDoc} */
    public void setInboundSAMLMessageIssueInstant(DateTime instant) {
        inboundSAMLMessageIssueInstant = instant;
    }

    /** {@inheritDoc} */
    public void setInboundSAMLProtocol(String protocol) {
        inboundSAMLProtocol = DatatypeHelper.safeTrimOrNullString(protocol);
    }

    /** {@inheritDoc} */
    public void setMetadataProvider(MetadataProvider provider) {
        metdataProvider = provider;
    }

    /** {@inheritDoc} */
    public void setOutboundSAMLMessage(OutboundMessageType message) {
        outboundSAMLMessage = message;
    }

    /** {@inheritDoc} */
    public void setOutboundSAMLMessageId(String id) {
        outboundSAMLMessageId = DatatypeHelper.safeTrimOrNullString(id);
    }

    /** {@inheritDoc} */
    public void setOutboundSAMLMessageIssueInstant(DateTime instant) {
        outboundSAMLMessageIssueInstant = instant;
    }

    /** {@inheritDoc} */
    public void setOutboundSAMLMessageSigningCredential(Credential credential) {
        outboundSAMLMessageSigningCredential = credential;
    }

    /** {@inheritDoc} */
    public void setOutboundSAMLProtocol(String protocol) {
        outboundSAMLProtocol = DatatypeHelper.safeTrimOrNullString(protocol);
    }

    /** {@inheritDoc} */
    public void setRelayState(String state) {
        relayState = DatatypeHelper.safeTrimOrNullString(state);
    }

    /** {@inheritDoc} */
    public void setPeerEntityEndpoint(Endpoint endpoint) {
        relyingPartyEndpoint = endpoint;
    }

    /** {@inheritDoc} */
    public void setRelyingPartyEntityId(String id) {
        relyingPartyEntityId = DatatypeHelper.safeTrimOrNullString(id);
    }

    /** {@inheritDoc} */
    public void setPeerEntityMetadata(EntityDescriptor metadata) {
        relyingPartyMetadata = metadata;
    }

    /** {@inheritDoc} */
    public void setPeerEntityRole(QName role) {
        relyingPartyRole = role;
    }

    /** {@inheritDoc} */
    public void setPeerEntityRoleMetadata(RoleDescriptor role) {
        relyingPartyRoleMetadata = role;
    }

    /** {@inheritDoc} */
    public NameIdentifierType getSubjectNameIdentifier() {
        // TODO Auto-generated method stub
        return null;
    }

    /** {@inheritDoc} */
    public void setSubjectNameIdentifier(NameIdentifierType identifier) {
        // TODO Auto-generated method stub
        
    }
}