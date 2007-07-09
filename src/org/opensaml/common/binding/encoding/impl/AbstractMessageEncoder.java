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

package org.opensaml.common.binding.encoding.impl;

import java.io.StringWriter;

import javax.servlet.ServletResponse;

import org.opensaml.Configuration;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.encoding.MessageEncoder;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Element;

/**
 * Base class handling boilerplate code for message encoders.
 * 
 * @param <ResponseType> the type of response the message is encoded to
 */
public abstract class AbstractMessageEncoder<ResponseType extends ServletResponse> implements
        MessageEncoder<ResponseType> {

    /** Issuer of the message. */
    private String issuer;

    /** Metadata provider used to look up information about the relying party. */
    private MetadataProvider metadataProvider;
    
    /** Relay state associated with outgoing message. */
    private String relayState;

    /** Party the message is being sent to. */
    private EntityDescriptor relyingParty;

    /** Endpoint of the relying party. */
    private Endpoint relyingPartyEndpoint;

    /** Role of the relying party. */
    private RoleDescriptor relyingPartyRole;

    /** Response to pack the message into. */
    private ResponseType response;

    /** SAML message to encode. */
    private SAMLObject samlMessage;

    /** Credential that should be used to sign the message. */
    private Credential signingCredential;

    /** {@inheritDoc} */
    public String getIssuer() {
        return issuer;
    }

    /** {@inheritDoc} */
    public MetadataProvider getMetadataProvider() {
        return metadataProvider;
    }
    
    /** {@inheritDoc} */
    public String getRelayState() {
        return relayState;
    }
    
    /** {@inheritDoc} */
    public EntityDescriptor getRelyingParty() {
        return relyingParty;
    }

    /** {@inheritDoc} */
    public Endpoint getRelyingPartyEndpoint() {
        return relyingPartyEndpoint;
    }

    /** {@inheritDoc} */
    public RoleDescriptor getRelyingPartyRole() {
        return relyingPartyRole;
    }

    /** {@inheritDoc} */
    public ResponseType getResponse() {
        return response;
    }

    /** {@inheritDoc} */
    public SAMLObject getSamlMessage() {
        return samlMessage;
    }

    /** {@inheritDoc} */
    public Credential getSigningCredential() {
        return signingCredential;
    }

    /**
     * Marshalls an XML message and writes the element to a string using UTF-8 encoding.
     * 
     * @param message the message to marshall
     * 
     * @return the marshalled message
     * 
     * @throws BindingException thrown if the message can not be marshalled
     */
    protected String marshallMessage(XMLObject message) throws BindingException {
        try {
            Marshaller marshaller = Configuration.getMarshallerFactory().getMarshaller(message);
            Element messageDOM = marshaller.marshall(message);
            StringWriter writer = new StringWriter();
            XMLHelper.writeNode(messageDOM, writer);
            return writer.toString();
        } catch (MarshallingException e) {
            throw new BindingException("Unable to marshall XML message", e);
        }
    }

    /** {@inheritDoc} */
    public void setIssuer(String id) {
        issuer = id;
    }

    /** {@inheritDoc} */
    public void setMetadataProvider(MetadataProvider provider) {
        metadataProvider = provider;
    }

    /** {@inheritDoc} */
    public void setRelayState(String state) {
        relayState = DatatypeHelper.safeTrimOrNullString(state);
    }

    /** {@inheritDoc} */
    public void setRelyingParty(EntityDescriptor entity) {
        relyingParty = entity;
    }

    /** {@inheritDoc} */
    public void setRelyingPartyEndpoint(Endpoint endpoint) {
        relyingPartyEndpoint = endpoint;
    }

    /** {@inheritDoc} */
    public void setRelyingPartyRole(RoleDescriptor role) {
        relyingPartyRole = role;
    }

    /** {@inheritDoc} */
    public void setResponse(ResponseType rpResponse) {
        response = rpResponse;
    }

    /** {@inheritDoc} */
    public void setSamlMessage(SAMLObject message) {
        samlMessage = message;
    }

    /** {@inheritDoc} */
    public void setSigningCredential(Credential credential) {
        signingCredential = credential;
    }
}