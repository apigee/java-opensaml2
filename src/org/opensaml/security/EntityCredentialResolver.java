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

package org.opensaml.security;

import java.security.GeneralSecurityException;
import java.util.List;

/**
 * EntityCredentialResolvers are responsible for reading credentials in different formats (files, SAML2 metadata, java keystores)
 * and constructing {@link org.opensaml.security.EntityCredential}s from the information.
 */
public interface EntityCredentialResolver<CredentialType extends EntityCredential> {

    /**
     * Fetches all the credentials in the underlying store.
     * 
     * @return the credentials found in the store
     * 
     * @throws GeneralSecurityException thrown if there is a problem fetching the credentials
     */
    public List<CredentialType> resolveCredential() throws GeneralSecurityException;
}