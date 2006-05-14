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

import java.security.Key;

/**
 * A credential for an entity, such as an Identity provider or Service provider. A credential must contain a public
 * key, and in the event that symmetric keys are used the public and private key may be identical.
 */
public interface EntityCredential {

    /**
     * The unique ID of the entity this credential is for.
     * 
     * @return unique ID of the entity this credential is for
     */
    public String getEntityID();
    
    /**
     * Gets usage type of this credential.
     * 
     * @return usage type of this credential
     */
    public CredentialUsageTypeEnumeration getUsageType();
    
    /**
     * Gets the algorithim used to generate the key, some examples would be DSA or RSA.
     * 
     * @return the algorithim used to generate the key
     */
    public String getKeyAlgorithm();

    /**
     * Gets the public key for the entity.
     * 
     * @return the public key for the entity
     */
    public Key getPublicKey();

    /**
     * Gets the private key for the entity if there is one.
     * 
     * @return the private key for the entity
     */
    public Key getPrivateKey();
}