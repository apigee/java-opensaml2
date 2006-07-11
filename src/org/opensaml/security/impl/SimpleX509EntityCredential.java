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
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;

import javolution.util.FastList;

import org.opensaml.security.CredentialUsageTypeEnumeration;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * A basic implementation of {@link org.opensaml.security.X509EntityCredential}.
 */
public class SimpleX509EntityCredential extends AbstractX509EntityCredential {

    /**
     * Constructor. Uses the entity certificate's (the 0th cert in the chain) principal common name as the entity ID and
     * the certificate's public key as this credential's public key.
     * 
     * @param entityCertificateChain the entity's certificate chain
     */
    public SimpleX509EntityCredential(List<X509Certificate> entityCertificateChain) {
        if (entityCertificateChain == null || entityCertificateChain.size() < 1) {
            throw new IllegalArgumentException("Entity certificate chain may not be null or empty");
        }
        this.entityCertificate = entityCertificateChain.get(0);

        if (entityCertificateChain != null) {
            certificateChain = new FastList<X509Certificate>();
        }
        certificateChain.addAll(entityCertificateChain);

        entityID = entityCertificate.getSubjectX500Principal().getName();
        publicKey = entityCertificate.getPublicKey();

    }

    /**
     * Constructor. Uses the public key in the entity certificate as this credential's public key.
     * 
     * @param entityID the ID of the entity this credential belongs to, may not be null
     * @param privateKey the entity's private key
     * @param entityCertificateChain the certificate chain for this entity, the entity cert must be the first element in
     *            the list
     * 
     * @throws IllegalArgumentException thrown if the entityID or entity certificate is null
     */
    public SimpleX509EntityCredential(String entityID, PrivateKey privateKey,
            List<X509Certificate> entityCertificateChain) throws IllegalArgumentException {

        setEntityID(entityID);

        if (entityCertificateChain == null || entityCertificateChain.size() < 1) {
            throw new IllegalArgumentException("Entity certificate chain may not be null or empty");
        }
        this.entityCertificate = entityCertificateChain.get(0);

        if (entityCertificateChain != null) {
            certificateChain = new FastList<X509Certificate>();
        }
        certificateChain.addAll(entityCertificateChain);

        publicKey = entityCertificate.getPublicKey();
        this.privateKey = privateKey;
    }

    /**
     * Constructor
     * 
     * @param entityID the ID of the entity this credential is for, may not be null
     * @param privateKey the entity's private key
     * @param publicKey the entity's public key, may not be null
     * 
     * @throws IllegalArgumentException thrown if the entity ID or public key is null or empty
     */
    public SimpleX509EntityCredential(String entityID, PrivateKey privateKey, PublicKey publicKey)
            throws IllegalArgumentException {
        setEntityID(entityID);

        if (publicKey == null) {
            throw new IllegalArgumentException("Public key may not be null");
        }
        this.publicKey = publicKey;
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

    /**
     * Sets the entityID.
     * 
     * @param newEntityID the new entity id
     * 
     * @throws IllegalArgumentException thrown if the entityID is null or empty
     */
    protected void setEntityID(String newEntityID) throws IllegalArgumentException {
        if (DatatypeHelper.isEmpty(entityID)) {
            entityID = new String(newEntityID);
        } else {
            throw new IllegalArgumentException("Entity ID may not be null or empty");
        }
    }
}