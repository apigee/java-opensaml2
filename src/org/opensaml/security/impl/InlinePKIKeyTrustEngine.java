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

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.security.CredentialUsageTypeEnumeration;
import org.opensaml.security.TrustEngine;
import org.opensaml.security.X509EntityCredential;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.signature.SignatureValidator;
import org.opensaml.xml.validation.ValidationException;

/**
 * A trust engine that uses cryptographic keys located within a role descriptor's key descriptor.
 */
public class InlinePKIKeyTrustEngine implements TrustEngine<X509EntityCredential> {

    /** Logger */
    private static Logger log = Logger.getLogger(InlinePKIKeyTrustEngine.class.getName());
    
    /**
     * Constructor
     */
    public InlinePKIKeyTrustEngine(){
        
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
            keyInfoCertificates = keyInfo.getCertificates();
            if (keyInfoCertificates == null || keyInfoCertificates.size() == 0) {
                if (log.isDebugEnabled()) {
                    log.debug("Key descriptor does not contain any certificates, skipping this key descriptor");
                    continue;
                }
            }

            if (log.isDebugEnabled()) {
                log.debug("Checking if certificates contained within match end-entity certificate");
            }
            for (X509Certificate roleCertificate : keyInfo.getCertificates()) {
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
     * @return false if SAMLObject is not signed or the descriptor does not have any signing keys that validate the
     *         signature
     */
    public boolean validate(SignableSAMLObject samlObject, RoleDescriptor descriptor) {
        if (samlObject.getSignature() == null) {
            return false;
        }

        List<KeyDescriptor> keyDescriptors = descriptor.getKeyDescriptors();
        if (keyDescriptors == null || keyDescriptors.size() == 0) {
            return false;
        }

        KeyInfo keyInfo;
        SignatureValidator signatureValidator;
        for (KeyDescriptor keyDescriptor : keyDescriptors) {
            if (keyDescriptor.getUse() == CredentialUsageTypeEnumeration.SIGNING) {
                keyInfo = keyDescriptor.getKeyInfo();
                if (keyInfo.getPublicKey() != null) {
                    signatureValidator = new SignatureValidator(keyInfo.getPublicKey());
                    try {
                        signatureValidator.validate(samlObject.getSignature());
                        return true;
                    } catch (ValidationException e) {
                        // Don't do anything, another key may work
                    }
                }
            }
        }

        return false;
    }
}