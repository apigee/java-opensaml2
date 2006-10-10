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
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.security.TrustEngine;

/**
 * 
 * Decodes a SAML message in a binding specific mannger. The decode() method should be run before any accessor methods
 * are called.
 * 
 * @author Walter Hoehn
 */
public interface MessageDecoder {

    /**
     * Sets the metadata provider that can be used to look up information regarding message issuers
     */
    public void setMetadataProvider(MetadataProvider metadatProvider);

    /**
     * Sets the trust engine that can be used to authenticate messages
     */
    public void setTrustEngine(TrustEngine trustEngine);

    /**
     * Decodes a SAML message in a binding specific manner.
     * 
     * @throws BindingException thrown if the message can not be decoded
     */
    public void decode() throws BindingException;

    /**
     * Gets the SAML message that was received and decoded.
     */
    public SAMLObject getSAMLMessage();

    /**
     * Gets the <code>RoleDescriptor</code> for the issuer of the decoded message.
     */
    public RoleDescriptor getIssuerMetadata();

    /**
     * Boolean indication of whether or not the decoded message was authenticated
     */
    public boolean issuerWasAuthenticated();

}
