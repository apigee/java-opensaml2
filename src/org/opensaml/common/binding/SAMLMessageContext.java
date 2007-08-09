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
import org.opensaml.ws.message.MessageContext;
import org.opensaml.xml.security.credential.Credential;

/**
 * SAML specific extension to the more basic {@link MessageContext}.
 * 
 * @param <InboundMessage> type of inbound SAML message
 * @param <OutboundMessage> type of outbound SAML message
 */
public interface SAMLMessageContext<InboundMessage extends SAMLObject, OutboundMessage extends SAMLObject> extends
        MessageContext {

    /**
     * Gets the entity ID of the asserting party.
     * 
     * @return entity ID of the asserting party
     */
    public String getAssertingPartyEntityId();

    /**
     * Gets the asserting party metadata.
     * 
     * @return asserting party metadata
     */
    public EntityDescriptor getAssertingPartyMetadata();

    /**
     * Gets the role of the asserting party.
     * 
     * @return role of the asserting party
     */
    public QName getAssertingPartyRole();

    /**
     * Gets the role metadata of the asserting party.
     * 
     * @return role metadata of the asserting party
     */
    public RoleDescriptor getAssertingPartyRoleMetadata();

    /**
     * Gets the inbound SAML message. This may not be the same as the message returned from
     * {@link MessageContext#getInboundMessage()} if the SAML message was carried in another protocol (e.g. SOAP).
     * 
     * @return inbound SAML message
     */
    public InboundMessage getInboundSAMLMessage();

    /**
     * Gets the ID of the inbound SAML message.
     * 
     * @return ID of the inbound SAML message
     */
    public String getInboundSAMLMessageId();

    /**
     * Gets the issue instant of the incomming SAML message.
     * 
     * @return issue instant of the incomming SAML message
     */
    public DateTime getInboundSAMLMessageIssueInstant();

    /**
     * Gets the protocol used by the relying party to communicate with the asserting party.
     * 
     * @return protocol used by the relying party to communicate with the asserting party
     */
    public String getInboundSAMLProtcol();

    /**
     * Gets the metadata provider used to lookup information entity information.
     * 
     * @return metadata provider used to lookup information entity information
     */
    public MetadataProvider getMetadataProvider();

    /**
     * Gets the credential used to sign the outbound SAML message.
     * 
     * @return credential used to sign the outbound SAML message
     */
    public Credential getOuboundSAMLMessageSigningCredential();

    /**
     * Gets the outbound SAML message. This may not be the same as the message returned from
     * {@link MessageContext#getOutboundMessage()} if the SAML message was carried in another protocol (e.g. SOAP).
     * 
     * @return outbound SAML message
     */
    public OutboundMessage getOutboundSAMLMessage();

    /**
     * Gets the ID of the outbound SAML message.
     * 
     * @return ID of the outbound SAML message
     */
    public String getOutboundSAMLMessageId();

    /**
     * Gets the issue instant of the outbound SAML message.
     * 
     * @return issue instant of the outbound SAML message
     */
    public DateTime getOutboundSAMLMessageIssueInstant();

    /**
     * Gets the protocol used by the asserting party to communicate with the relying party.
     * 
     * @return protocol used by the asserting party to communicate with the relying party
     */
    public String getOutboundSAMLProtcol();

    /**
     * Gets the relay state associated with the message.
     * 
     * @return relay state associated with the message
     */
    public String getRelayState();

    /**
     * Gets whether the inbound SAML message has been authenticated.
     * 
     * @return whether the inbound SAML message has been authenticated
     */
    public boolean isInboundSAMLMessageAuthenticated();

    /**
     * Sets whether the inbound SAML message has been authenticated.
     * 
     * @param isAuthenticated whether the inbound SAML message has been authenticated
     */
    public void setInboundSAMLMessageAuthenticated(boolean isAuthenticated);

    /**
     * Gets the endpoint of for the relying party.
     * 
     * @return endpoint of for the relying party
     */
    public Endpoint getRelyingPartyEndpoint();

    /**
     * Gets the entity ID of the relying party.
     * 
     * @return entity ID of the relying party
     */
    public String getRelyingPartyEntityId();

    /**
     * Gets the relying party metadata.
     * 
     * @return relying party metadata
     */
    public EntityDescriptor getRelyingPartyMetadata();

    /**
     * Gets the role of the relying party.
     * 
     * @return role of the relying party
     */
    public QName getRelyingPartyRole();

    /**
     * Gets the role of the relying party.
     * 
     * @return role of the relying party
     */
    public RoleDescriptor getRelyingPartyRoleMetadata();

    /**
     * Sets the entity ID of the asserting party.
     * 
     * @param id entity ID of the asserting party
     */
    public void setAssertingPartyEntityId(String id);

    /**
     * Sets the role of the asserting party.
     * 
     * @param role role of the asserting party
     */
    public void setAssertingPartyRole(QName role);

    /**
     * Sets the inbound SAML message.
     * 
     * @param message inbound SAML message
     */
    public void setInboundSAMLMessage(InboundMessage message);

    /**
     * Sets the ID of the inbound SAML message.
     * 
     * @param id ID of the inbound SAML message
     */
    public void setInboundSAMLMessageId(String id);

    /**
     * Sets the issue instant of the incomming SAML message.
     * 
     * @param instant issue instant of the incomming SAML message
     */
    public void setInboundSAMLMessageIssueInstant(DateTime instant);

    /**
     * Sets the protocol used by the relying party to communicate with the asserting party.
     * 
     * @param protocol protocol used by the relying party to communicate with the asserting party
     */
    public void setInboundSAMLProtocol(String protocol);

    /**
     * Sets the metadata provider used to lookup information entity information.
     * 
     * @param provider metadata provider used to lookup information entity information
     */
    public void setMetadataProvider(MetadataProvider provider);

    /**
     * Sets the outbound SAML message.
     * 
     * @param message outbound SAML message
     */
    public void setOutboundSAMLMessage(OutboundMessage message);

    /**
     * Sets the ID of the outbound SAML message.
     * 
     * @param id ID of the outbound SAML message
     */
    public void setOutboundSAMLMessageId(String id);

    /**
     * Sets the issue instant of the outbound SAML message.
     * 
     * @param instant issue instant of the outbound SAML message
     */
    public void setOutboundSAMLMessageIssueInstant(DateTime instant);

    /**
     * Sets the credential used to sign the outbound SAML message.
     * 
     * @param credential credential used to sign the outbound SAML message
     */
    public void setOutboundSAMLMessageSigningCredential(Credential credential);

    /**
     * Sets the protocol used by the asserting party to communicate with the relying party.
     * 
     * @param protocol protocol used by the asserting party to communicate with the relying party
     */
    public void setOutboundSAMLProtocol(String protocol);

    /**
     * Sets the relay state associated with the message.
     * 
     * @param relayState relay state associated with the message
     */
    public void setRelayState(String relayState);

    /**
     * Sets the endpoint of for the relying party.
     * 
     * @param endpoint endpoint of for the relying party
     */
    public void setRelyingPartyEndpoint(Endpoint endpoint);

    /**
     * Sets the entity ID of the relying party.
     * 
     * @param id entity ID of the relying party
     */
    public void setRelyingPartyEntityId(String id);

    /**
     * Sets the role of the relying party.
     * 
     * @param role role of the relying party
     */
    public void setRelyingPartyRole(QName role);
}