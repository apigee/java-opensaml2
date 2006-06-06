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

import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.List;

import javolution.util.FastList;

import org.opensaml.security.CredentialUsageTypeEnumeration;

/**
 * A basic implementation of {@link org.opensaml.security.X509EntityCredential}.
 */
public class SimpleX509EntityCredential extends AbstractX509EntityCredential {

    /**
     * Constructor.  Uses the public key in the entity certificate as this credential's public key.
     * 
     * @param entityID the ID of the entity this credential belongs to, may not be null
     * @param privateKey the entity's private key
     * @param entityCertificate the public certificate for the entity, may not be null
     * @param entityCertificateChain the certificate chain for this entity
     * 
     * @throws IllegalArgumentException thrown if the entityID or entity certificate is null
     */
    public SimpleX509EntityCredential(String entityID, PrivateKey privateKey, X509Certificate entityCertificate,
            List<X509Certificate> entityCertificateChain) throws IllegalArgumentException {

        if (entityID != null && entityID.length() > 0) {
            this.entityID = new String(entityID);
        } else {
            throw new IllegalArgumentException("Entity ID may not be null or empty");
        }

        if (entityCertificate == null) {
            throw new IllegalArgumentException("Entity certificate may not be null");
        }
        this.entityCertificate = entityCertificate;

        if (entityCertificateChain != null) {
            certificateChain = new FastList<X509Certificate>();
        }
        certificateChain.addAll(entityCertificateChain);

        if (!certificateChain.contains(entityCertificate)) {
            certificateChain.add(0, entityCertificate);
        }

        publicKey = entityCertificate.getPublicKey();
        this.privateKey = privateKey;
    }

    /**
     * Sets the usage type for this credential.
     * 
     * @param usageType usage type for this credential
     */
    public void setCredentialUsageType(CredentialUsageTypeEnumeration usageType) {
        this.usageType = usageType;
    }
}