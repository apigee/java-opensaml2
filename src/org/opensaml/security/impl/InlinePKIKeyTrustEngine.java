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

import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.security.CredentialUsageTypeEnumeration;
import org.opensaml.security.TrustEngine;
import org.opensaml.security.X509EntityCredential;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.signature.KeyInfoHelper;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureValidator;
import org.opensaml.xml.validation.ValidationException;

/**
 * A trust engine that uses cryptographic keys located within a role descriptor's key descriptor.
 */
public class InlinePKIKeyTrustEngine implements TrustEngine<X509EntityCredential> {

    /** Logger */
    private static Logger log = Logger.getLogger(InlinePKIKeyTrustEngine.class);

    /**
     * Constructor
     */
    public InlinePKIKeyTrustEngine() {

    }

    /**
     * Validates that the end-entity certificate within the credential is identical to one of the certificates within
     * the role descriptor's key descriptor.
     * 
     * @param entityCredential the credentials to validate
     * @param descriptor the role descriptor to validate the credentials against
     * 
     * @return true if the given credentials identical to one of the credentials listed for the role descriptor, false
     *         if not
     */
    public boolean validate(X509EntityCredential entityCredential, RoleDescriptor descriptor) {
        if (entityCredential == null) {
            log.error("Unable to validate, entity credential was null");
            return false;
        }

        if (descriptor == null) {
            log.error("Unable to validate, role descriptor was null");
            return false;
        }

        List<KeyDescriptor> keyDescriptors = descriptor.getKeyDescriptors();
        if (keyDescriptors == null || keyDescriptors.size() == 0) {
            if (log.isDebugEnabled()) {
                log.debug("Unable to validate entity credential, role descriptor does not contain any key descriptors");
            }
            return false;
        }

        if (log.isDebugEnabled()) {
            log.debug("Attempting to match key information within role descriptor with end-entity certificate");
        }
        KeyInfo keyInfo;
        List<X509Certificate> keyInfoCertificates;
        for (KeyDescriptor keyDescriptor : keyDescriptors) {
            if (keyDescriptor.getUse() != CredentialUsageTypeEnumeration.SIGNING) {
                if (log.isDebugEnabled()) {
                    log.debug("Key descriptor is not for signing, skipping it");
                }
                continue;
            }
            if (log.isDebugEnabled()) {
                log.debug("Located a key descriptor used for signing");
            }

            keyInfo = keyDescriptor.getKeyInfo();
            keyInfoCertificates = null;
            try {
                keyInfoCertificates = KeyInfoHelper.getCertificates(keyInfo);
            } catch (CertificateException e) {
                log.error("Error extracting certificates from KeyInfo: " + e);
                continue;
            }
            
            if (keyInfoCertificates == null || keyInfoCertificates.size() == 0) {
                if (log.isDebugEnabled()) {
                    log.debug("Key descriptor does not contain any certificates, skipping this key descriptor");
                    continue;
                }
            }

            if (log.isDebugEnabled()) {
                log.debug("Checking if certificates contained within match end-entity certificate");
            }
            for (X509Certificate roleCertificate : keyInfoCertificates) {
                try {
                    if (Arrays.equals(roleCertificate.getEncoded(), entityCredential.getEntityCertificate()
                            .getEncoded())) {
                        if (log.isDebugEnabled()) {
                            log.debug("End-entity certificate matches a role descriptor certificate, success");
                        }
                        return true;
                    }
                } catch (CertificateEncodingException e) {
                    log.error("Error encoding certificate during matching process", e);
                }
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("No certificates within this role descriptor matched the given end-entity certificate");
        }
        return false;
    }

    /**
     * Validates that the given signed SAMLObject was signed using a Signing key for the given descriptor.
     * 
     * @return the credential that validated the SAMLObject or null if the signature did not validate or the SAMLObject
     *         was not signedf
     */
    public X509EntityCredential validate(SignableSAMLObject samlObject, RoleDescriptor descriptor) {
        if (samlObject.getSignature() == null) {
            if (log.isDebugEnabled()) {
                log.debug("Signature validation requested on unsigned object, returning");
            }
            return null;
        }

        EntityDescriptor owningEntity = (EntityDescriptor) descriptor.getParent();

        List<KeyDescriptor> keyDescriptors = descriptor.getKeyDescriptors();
        if (keyDescriptors == null || keyDescriptors.size() == 0) {
            log.warn("Unable to validate signature, entity " + owningEntity.getEntityID()
                    + " does not contain any keying information for role this role");
            return null;
        }

        if (log.isDebugEnabled()) {
            log.debug("Attempting to validate signature with the keying information for entity "
                    + owningEntity.getEntityID());
        }
        Signature signature = samlObject.getSignature();
        KeyInfo keyInfo;
        PublicKey publicKey;
        SignatureValidator signatureValidator;
        for (KeyDescriptor keyDescriptor : keyDescriptors) {
            if (keyDescriptor.getUse() == CredentialUsageTypeEnumeration.SIGNING) {
                keyInfo = keyDescriptor.getKeyInfo();
                //TODO this is broken until helper is finished.
                //TODO KeyInfo can in theory have multiple public key reps, so what to do ?
                publicKey = KeyInfoHelper.getPublicKeys(keyInfo).get(0);
                
                if (publicKey != null) {
                    if(log.isDebugEnabled()){
                        log.debug("Attempting to validate signature with public key");
                    }
                    signatureValidator = new SignatureValidator(publicKey);
                    try {
                        signatureValidator.validate(signature);
                        if(log.isDebugEnabled()){
                            log.debug("Signature validated with public key");
                        }
                        SimpleX509EntityCredential validatingCredential;
                        validatingCredential = new SimpleX509EntityCredential(owningEntity.getEntityID(), null,
                                publicKey);
                        return validatingCredential;
                    } catch (ValidationException e) {
                        if(log.isDebugEnabled()){
                            log.debug("Public key did not validate signature");
                        }
                    }
                }else{
                    if(log.isDebugEnabled()){
                        log.debug("Signing key information does not contain a public key, skipping it");
                    }
                }
            }else{
                if(log.isDebugEnabled()){
                    log.debug("Found keying information, but was not for signing, skipping it");
                }
            }
        }

        if(log.isDebugEnabled()){
            log.debug("No keying information validated the signature");
        }
        return null;
    }
}