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

import java.security.cert.CertificateException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Set;

import javolution.util.FastList;

import org.apache.log4j.Logger;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.security.TrustEngine;
import org.opensaml.security.X509EntityCredential;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureValidator;
import org.opensaml.xml.validation.ValidationException;

/**
 * A trust engine that uses the X509 certificate and CRLs associated with a role to perform PKIX validation on security
 * tokens.
 */
public abstract class AbstractPKIXTrustEngine implements TrustEngine<X509EntityCredential> {

    /** Logger */
    private static Logger log = Logger.getLogger(AbstractPKIXTrustEngine.class);

    /** {@inheritDoc} */
    public X509EntityCredential validate(SignableSAMLObject samlObject, RoleDescriptor descriptor) {
        if (samlObject.getSignature() == null) {
            if (log.isDebugEnabled()) {
                log.debug("Signature validation requested on unsigned object, returning");
            }
            return null;
        }

        EntityDescriptor owningEntity = (EntityDescriptor) descriptor.getParent();
        PKIXValidationInformation pkixInfo = getValidationInformation(descriptor);

        Set<X509Certificate> trustAnchors = pkixInfo.getTrustAnchors();
        Set<X509CRL> crls = pkixInfo.getCRLs();

        if (trustAnchors == null || trustAnchors.size() < 1) {
            if (log.isDebugEnabled()) {
                log
                        .debug("Unable to validate signature, no trust anchors found in the PKIX validation information provided for entity"
                                + owningEntity.getEntityID());
            }
            return null;
        }

        if (log.isDebugEnabled()) {
            log.debug("Attempting to validate signature with PKIX validation information provided for entity"
                    + owningEntity.getEntityID());
        }
        Signature signature = samlObject.getSignature();
        boolean signatureValidated = false;
        FastList<X509Certificate> certificateChain = new FastList<X509Certificate>();
        String subjectDN;
        SignatureValidator signatureValidator;
        for (X509Certificate cert : trustAnchors) {
            subjectDN = cert.getSubjectX500Principal().toString();

            if (!isCertificateValid(cert, crls)) {
                if (log.isDebugEnabled()) {
                    log.debug("Certificate with subject DN " + subjectDN + " is not valid.");
                }
                if (signatureValidated) {
                    log.warn("Signature had been validated but certificate with subject DN "
                                    + subjectDN
                                    + ", found in the cert chain, was invalid."
                                    + " Signature validation is invalid, attempting to re-validate signature with remaining certificates");
                }
                signatureValidated = false;
                certificateChain.clear();
                continue;
            }

            if (!signatureValidated) {
                if (log.isDebugEnabled()) {
                    log.debug("Attempting to validate siganture with public key associated with certifcate "
                            + subjectDN);
                }
                signatureValidator = new SignatureValidator(cert.getPublicKey());
                try {
                    signatureValidator.validate(signature);
                    if (log.isDebugEnabled()) {
                        log.debug("Signature validated with public key associated with certifcate " + subjectDN);
                    }
                    signatureValidated = true;
                    certificateChain.add(cert);
                } catch (ValidationException e) {
                    if (log.isDebugEnabled()) {
                        log.debug("Signature failed to validate with public key associated with certifcate "
                                + subjectDN);
                    }
                }
            } else {
                // Add the rest of the certs into the chain that validated the signature
                if (log.isDebugEnabled()) {
                    log.debug("Adding certificate to cert chain that validated signature");
                }
                certificateChain.add(cert);
                if (cert.getIssuerX500Principal().equals(cert.getSubjectX500Principal())) {
                    if (log.isDebugEnabled()) {
                        log.debug("Adding root certificate to cert chain that validated signature");
                    }
                    break;
                }
            }
        }

        if (signatureValidated) {
            SimpleX509EntityCredential validatingCredential;
            validatingCredential = new SimpleX509EntityCredential(owningEntity.getEntityID(), null, certificateChain);
            return validatingCredential;
        }

        if (log.isDebugEnabled()) {
            log.debug("No X509 information within the " + owningEntity.getEntityID()
                    + " entity's metadata validated the signature");
        }
        return null;
    }

    /**
     * Checks that a certificate is currently valid and that it is not on a revocation list.
     * 
     * @param certificate the cerificate to validate
     * @param crls the CRLs to check the certificate against
     * 
     * @return true if the certificate is valid, false if not
     */
    private boolean isCertificateValid(X509Certificate certificate, Set<X509CRL> crls) {
        try {
            certificate.checkValidity();
        } catch (CertificateException e) {
            return false;
        }

        if (crls != null && crls.size() > 0) {
            for (X509CRL crl : crls) {
                if (crl.isRevoked(certificate)) {
                    return false;
                }
            }
        }

        return true;
    }

    /** {@inheritDoc} */
    public boolean validate(X509EntityCredential entityCredential, RoleDescriptor descriptor) {
        PKIXValidationInformation pkixInfo = getValidationInformation(descriptor);

        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Gets the X509 certificates and CRLs for the given role that should be used during the PKIX validation.
     * 
     * @return the X509 certificates and CRLs for the given role that should be used during the PKIX validation
     */
    protected abstract PKIXValidationInformation getValidationInformation(RoleDescriptor descriptor);

    /**
     * Collection of X509 certificates to be used as trust anchors and CRLs that will be used during PKIX validation.
     */
    protected final class PKIXValidationInformation {

        /** X509 Certificates to be used as trust anchors */
        private Set<X509Certificate> trustAnchors;

        /** CRLs used during PKIX validation */
        private Set<X509CRL> crls;

        /**
         * Constructor
         * 
         * @param trustAnchors trust anchors used during PKIX validation
         * @param crls CRLs used during PKIX validation
         */
        public PKIXValidationInformation(Set<X509Certificate> trustAnchors, Set<X509CRL> crls) {
            this.trustAnchors = trustAnchors;
            this.crls = crls;
        }

        /**
         * Gets the trust anchors used during PKIX validation.
         * 
         * @return trust anchors used during PKIX validation
         */
        public Set<X509Certificate> getTrustAnchors() {
            return trustAnchors;
        }

        /**
         * Gets the CRLs used during PKIX validation.
         * 
         * @return CRLs used during PKIX validation
         */
        public Set<X509CRL> getCRLs() {
            return crls;
        }
    }
}