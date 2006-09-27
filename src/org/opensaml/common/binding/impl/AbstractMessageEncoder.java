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

import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.MessageEncoder;
import org.opensaml.saml2.metadata.provider.MetadataProvider;

/**
 * Base class handling boilerplate code for message encoders.
 */
public abstract class AbstractMessageEncoder implements MessageEncoder {
    
    private MetadataProvider metadataProvider;
    private String relyingParty;
    private SAMLObject samlMessage;

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
        if(metadatProvider != null){
            this.metadataProvider = metadatProvider;
        }else{
            throw new NullPointerException("MetadataProvider may not be null");
        }
    }

    /** {@inheritDoc} */
    public void setRelyingParty(String relyingParty) {
        if(relyingParty != null){
            this.relyingParty = relyingParty;
        }else{
            throw new NullPointerException("Relying party may not be null");
        }
    }

    /** {@inheritDoc} */
    public void setSAMLMessage(SAMLObject samlMessage) {
        if(samlMessage != null){
            this.samlMessage = samlMessage;
        }else{
            throw new NullPointerException("SAML message may not be null");
        }
    }
}