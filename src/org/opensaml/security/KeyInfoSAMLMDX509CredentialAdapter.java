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

package org.opensaml.security;

import java.security.GeneralSecurityException;

import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.xml.security.x509.KeyInfoX509CredentialAdapter;
import org.opensaml.xml.signature.KeyInfo;

/**
 * Adapts a {@link KeyInfo} owned by a {@link RoleDescriptor} an {@link SAMLMDX509Credential}.
 */
public class KeyInfoSAMLMDX509CredentialAdapter extends KeyInfoX509CredentialAdapter implements SAMLMDX509Credential {

    /** Role containing the adapted key info. */
    private RoleDescriptor role;
    
    /**
     * Constructor.
     * 
     * @param info key info to adapt
     * 
     * @throws GeneralSecurityException thrown if the key, certificate, or CRL information is represented in an
     *             unsupported format
     */
    public KeyInfoSAMLMDX509CredentialAdapter(KeyInfo info) throws GeneralSecurityException {
        
        // KeyInfo -> KeyDescriptor -> RoleDescriptor
        role = (RoleDescriptor) info.getParent().getParent();
        EntityDescriptor entityDescriptor = (EntityDescriptor) role.getParent();
        setEntityId(entityDescriptor.getEntityID());
        
        parseKeyInfo(info);
    }
    
    /** {@inheritDoc} */
    public RoleDescriptor getRoleDescriptor() {
        return role;
    }
}