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

package org.opensaml.common.binding;

import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.metadata.provider.MetadataProvider;

/**
 * Encodes a SAML message in a binding specific manner.  A given encoder instance need not be thread-safe or 
 * reusable.  The process of encoding a message may change some of the properties set on this encoder.  For example, a 
 * message may be required to be signed in a specific manner, so prior to the encoding the message may not be signed 
 * while afterwords it may be.
 */
public interface MessageEncoder {
    
    /**
     * Gets the relying party the message will be encoded for.
     * 
     * @return relying party the message will be encoded for
     */
    public String getRelyingParty();
    
    /**
     * Sets relying party the message will be encoded for.
     * 
     * @param relyingParty relying party the message will be encoded for, may not be null
     */
    public void setRelyingParty(String relyingParty);
    
    /**
     * Gets the metadata provider that can be used to look up information about the relying party.
     * 
     * @return the metadata provider that can be used to look up information about the relying party
     */
    public MetadataProvider getMetadataProvider();
    
    /**
     * Sets the metadata provider that can be used to look up information about the relying party.
     * 
     * @param metadatProvider provider that can be used to look up information about the relying party, may not be null
     */
    public void setMetadataProvider(MetadataProvider metadatProvider);
    
    /**
     * Gets the SAML message that will be encoded and sent to the relying party.
     * 
     * @return the SAML message that will be encoded and sent to the relying party
     */
    public SAMLObject getSAMLMessage();
    
    /**
     * Sets the SAML message that will be encoded and sent to the relying party.
     * 
     * @param samlMessage the SAML message to encode, may not be null
     */
    public void setSAMLMessage(SAMLObject samlMessage);

    /**
     * Encode the SAML message in the binding specific manner.
     *  
     * @throws BindingException thrown if the problem can not be encoded
     */
    public void encode() throws BindingException;
}