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

package org.opensaml.common.binding.impl;

import javax.servlet.ServletResponse;

import org.opensaml.Configuration;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.MessageEncoder;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Element;

/**
 * Base class handling boilerplate code for message encoders.
 */
public abstract class AbstractMessageEncoder<ResponseType extends ServletResponse> implements MessageEncoder<ResponseType> {

    /** Response to pack the message into */
    private ResponseType response;
    
    /** Metadata provider used to look up information about the relying party */
    private MetadataProvider metadataProvider;

    /** Party the emssage is being sent to */
    private String relyingParty;

    /** SAML message to encode */
    private SAMLObject samlMessage;
    
    /** {@inheritDoc} */
    public ResponseType getResponse(){
        return response;
    }
    
    /** {@inheritDoc} */
    public void setResponse(ResponseType response){
        this.response = response;
    }

    /** {@inheritDoc} */
    public MetadataProvider getMetadataProvider() {
        return metadataProvider;
    }

    /** {@inheritDoc} */
    public String getRelyingParty() {
        return relyingParty;
    }

    /** {@inheritDoc} */
    public SAMLObject getSAMLMessage() {
        return samlMessage;
    }

    /** {@inheritDoc} */
    public void setMetadataProvider(MetadataProvider metadatProvider) {
        if (metadatProvider != null) {
            this.metadataProvider = metadatProvider;
        } else {
            throw new NullPointerException("MetadataProvider may not be null");
        }
    }

    /** {@inheritDoc} */
    public void setRelyingParty(String relyingParty) {
        if (relyingParty != null) {
            this.relyingParty = relyingParty;
        } else {
            throw new NullPointerException("Relying party may not be null");
        }
    }

    /** {@inheritDoc} */
    public void setSAMLMessage(SAMLObject samlMessage) {
        if (samlMessage != null) {
            this.samlMessage = samlMessage;
        } else {
            throw new NullPointerException("SAML message may not be null");
        }
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
            return XMLHelper.nodeToString(messageDOM);
        } catch (MarshallingException e) {
            throw new BindingException("Unable to marshall XML message", e);
        }
    }
}