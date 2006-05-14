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

/**
 * A basic implementation of {@link org.opensaml.security.X509EntityCredential}.
 */
public class SimpleX509EntityCredential extends AbstractX509EntityCredential {

    /**
     * Constructor. If the given entityID is null the subject DN of the entity certificate will be used as the entityID.
     * If that is null as well an exception will be raised.  If the given entity certificate is not within the given certificate 
     * chain it will be added as the 0th element.  If a certificate chain does not exist and an entity certificate is given a list 
     * will be created and the entity certificate will be added to it.
     * 
     * @param entityID the ID of the entity this credential belongs to
     * @param privateKey the entity's public key
     * @param publicKey the entity's private key
     * @param entityCertificate the public certificate for the entity
     * @param entityCertificateChain the certificate chain for this entity
     * 
     * @throws IllegalArgumentException thrown if the public key is null or the entity ID can not be determined
     */
    public SimpleX509EntityCredential(String entityID, PrivateKey privateKey, PublicKey publicKey,
            X509Certificate entityCertificate, List<X509Certificate> entityCertificateChain)
            throws IllegalArgumentException {

        if (entityID != null && entityID.trim().length() > 0) {
            this.entityID = new String(entityID.trim());
        }

        if (publicKey == null) {
            throw new IllegalArgumentException("Public key may not be null");
        }
        
        if(entityCertificate != null){
            this.entityCertificate = entityCertificate;
            
            if(this.entityID == null){
                this.entityID = new String(this.entityCertificate.getSubjectX500Principal().getName());
            }
            
            if(entityCertificateChain == null){
                certificateChain = new FastList<X509Certificate>();
            }
            
            certificateChain.addAll(entityCertificateChain);
            
            if (!certificateChain.contains(entityCertificate)) {
                certificateChain.add(0, entityCertificate);
            }
        }
        
        if(this.entityID == null){
            throw new IllegalArgumentException("Unable to determine entity ID");
        }

        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }
}