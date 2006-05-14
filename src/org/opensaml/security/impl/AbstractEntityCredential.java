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

package org.opensaml.security.impl;

import java.security.Key;

import org.opensaml.security.CredentialUsageTypeEnumeration;
import org.opensaml.security.EntityCredential;

/**
 * Base class for {@link org.opensaml.security.EntityCredential} implementations.
 */
public abstract class AbstractEntityCredential implements EntityCredential {

    /** ID of the entity owning this credential */
    protected String entityID;
    
    /** Usage type of this credential */
    protected CredentialUsageTypeEnumeration usageType;
    
    /** Public key of the entity */
    protected Key publicKey;
    
    /** Private key of the entity */
    protected Key privateKey;
    
    /** {@inheritDoc  */
    public String getEntityID() {
        return entityID;
    }

    /** {@inheritDoc  */
    public CredentialUsageTypeEnumeration getUsageType() {
        return usageType;
    }
    
    /** {@inheritDoc  */
    public String getKeyAlgorithm() {
        return publicKey.getAlgorithm();
    }

    /** {@inheritDoc  */
    public Key getPublicKey() {
        return publicKey;
    }

    /** {@inheritDoc  */
    public Key getPrivateKey() {
        return privateKey;
    }
}