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
 * A container for loaded credentials.
 */
public interface EntityCredentialStore {
    
    /**
     * Gets the credentials for a given entity.
     * 
     * @param entityID the ID of the entity
     * 
     * @return the credentials for that entity
     */
    public List<EntityCredential> getCredential(String entityID);

    /**
     * Gets the credentials for the given entity.
     * 
     * @param entityID the ID of the entity
     * 
     * @return the credentials for that entity
     */
    public List<EntityCredential> getCredential(String entityID, CredentialUsageTypeEnumeration usageType);
    
    /**
     * Gets all the credentials loaded into this store.
     * 
     * @return the credentials loaded into this store
     */
    public List<EntityCredential> getCredentials();
    
    /**
     * Loads the given credentials into the store.
     * 
     * @param credentials the credentials to load
     */
    public void loadCredentials(List<EntityCredential> credentials);
    
    /**
     * Loads the credentials fetched by the resolver into the store.
     * 
     * @param credentialResolver the resolver used to fetch credentials
     */
    public void loadCredentials(EntityCredentialResolver credentialResolver) throws GeneralSecurityException;
    
    /**
     * Removes the given credential from the store.
     * 
     * @param credential the credential to remove.
     */
    public void removeCredential(EntityCredential credential);
}