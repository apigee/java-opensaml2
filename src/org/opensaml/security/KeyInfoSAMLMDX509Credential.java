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

import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.xml.security.x509.KeyInfoX509Credential;
import org.opensaml.xml.signature.KeyInfo;

/**
 * A credential class which represents a {@link KeyInfoX509Credential} which was resolved based on a 
 * {@link KeyInfo} that was found in SAML 2 metadata.
 */
public class KeyInfoSAMLMDX509Credential extends KeyInfoX509Credential implements SAMLMDCredential {

    /** Role containing the KeyInfo resolution context. */
    private RoleDescriptor role;
    
    /** {@inheritDoc} */
    public void setKeyInfo(KeyInfo info) {
        super.setKeyInfo(info);
        // KeyInfo -> KeyDescriptor -> RoleDescriptor
        role = (RoleDescriptor) info.getParent().getParent();
        if (role != null) {
            EntityDescriptor entityDescriptor = (EntityDescriptor) role.getParent();
            if (entityDescriptor != null) {
                setEntityId(entityDescriptor.getEntityID());
            }
        }
    }
    
    /** {@inheritDoc} */
    public RoleDescriptor getRoleDescriptor() {
        return role;
    }
}