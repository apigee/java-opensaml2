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

import java.security.GeneralSecurityException;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathValidator;
import java.security.cert.CertStore;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Set;

import javolution.util.FastList;
import javolution.util.FastSet;

import org.apache.log4j.Logger;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.security.TrustEngine;
import org.opensaml.security.X509EntityCredential;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.signature.Signature;

/**
 * A trust engine that uses the X509 certificate and CRLs associated with a role to perform PKIX validation on security
 * tokens.
 */
public abstract class AbstractPKIXTrustEngine implements TrustEngine<X509EntityCredential> {

    /** Logger */
    private static Logger log = Logger.getLogger(AbstractPKIXTrustEngine.class);

    /** {@inheritDoc} */
    public X509EntityCredential validate(SignableSAMLObject samlObject, RoleDescriptor descriptor) {
        if (samlObject.isSigned()) {
            Signature signature = samlObject.getSignature();
            KeyInfo keyInfo = signature.getKeyInfo();
            List<X509Certificate> entityCerts = keyInfo.getCertificates();

            if (entityCerts == null || entityCerts.size() == 0) {
                if (log.isDebugEnabled()) {
                    log.debug("Requested validation on signed SAML object however no certificate information was included with the signature.  Unable to perform PKIX validation.");
                }
                return null;
            }

            if(log.isDebugEnabled()){
                log.debug("Validating signed SAML object using PKIX validation.");
            }
            X509EntityCredential entityCredential = new SimpleX509EntityCredential(entityCerts);

            if (validate(entityCredential, descriptor)) {
                return entityCredential;
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("Requested validation on unsigned SAML object, no validation performed.");
        }
        
        return null;
    }

    /** {@inheritDoc} */
    public boolean validate(X509EntityCredential entityCredential, RoleDescriptor descriptor) {
        EntityDescriptor owningEntity = (EntityDescriptor) descriptor.getParent();
        PKIXValidationInformation pkixInfo = getValidationInformation(descriptor);

        Set<X509Certificate> trustChain = pkixInfo.getTrustChain();
        Set<X509CRL> crls = pkixInfo.getCRLs();

        if (trustChain == null || trustChain.size() < 1) {
            if (log.isDebugEnabled()) {
                log
                        .debug("Unable to validate signature, no trust anchors found in the PKIX validation information provided for entity"
                                + owningEntity.getEntityID());
            }
            return false;
        }

        if (log.isDebugEnabled()) {
            log.debug("Attempting PKIX path validation on entity credential for role owned by entity "
                    + owningEntity.getEntityID());
        }
        try {
            if (log.isDebugEnabled()) {
                log.debug("Constructring trust anchors");
            }
            Set<TrustAnchor> trustAnchors = new FastSet<TrustAnchor>();
            for (X509Certificate cert : trustChain) {
                trustAnchors.add(new TrustAnchor(cert, null));
            }

            X509CertSelector selector = new X509CertSelector();
            selector.setCertificate(entityCredential.getEntityCertificate());

            if (log.isDebugEnabled()) {
                log.debug("Adding trust anchors to PKIX validator parameters");
            }
            PKIXBuilderParameters params = new PKIXBuilderParameters(trustAnchors, selector);

            if (log.isDebugEnabled()) {
                log.debug("Setting verification depth to " + pkixInfo.getVerificationDepth());
            }
            params.setMaxPathLength(pkixInfo.getVerificationDepth());

            if (log.isDebugEnabled()) {
                log.debug("Adding entity ceritifcate chain to certificate store");
            }
            List storeMaterial = new FastList(entityCredential.getEntityCertificateChain());

            if (crls.size() > 0) {
                if (log.isDebugEnabled()) {
                    log.debug("Enabling CRL support and adding CRLs to certificate store");
                }
                storeMaterial.addAll(crls);
                params.setRevocationEnabled(true);
            } else {
                params.setRevocationEnabled(false);
            }

            if (log.isDebugEnabled()) {
                log.debug("Adding certificate store to PKIX validator parameters");
            }
            CertStore store = CertStore.getInstance("Collection", new CollectionCertStoreParameters(storeMaterial));
            List<CertStore> stores = new FastList<CertStore>();
            stores.add(store);
            params.setCertStores(stores);

            if (log.isDebugEnabled()) {
                log.debug("Building certificate validation path");
            }
            CertPathBuilder builder = CertPathBuilder.getInstance("PKIX");
            PKIXCertPathBuilderResult buildResult = (PKIXCertPathBuilderResult) builder.build(params);
            CertPath certificatePath = buildResult.getCertPath();

            if (log.isDebugEnabled()) {
                log.debug("Validating given entity credentials using built PKIX validator");
            }
            CertPathValidator validator = CertPathValidator.getInstance("PKIX");
            validator.validate(certificatePath, params);

            if (log.isDebugEnabled()) {
                log
                        .debug("PKIX validation of credentials for entity " + entityCredential.getEntityID()
                                + " successful");
            }
            return true;

        } catch (GeneralSecurityException e) {
            if (log.isDebugEnabled()) {
                log.debug("PKIX validation of credentials for entity " + entityCredential + " failed.", e);
            }

            return false;
        }
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

        /** Maximum allowable trust chain verification depth */
        private int verificationDepth;

        /** X509 Certificates to be used as trust anchors */
        private Set<X509Certificate> trustChain;

        /** CRLs used during PKIX validation */
        private Set<X509CRL> crls;

        /**
         * Constructor
         * 
         * @param verificationDepth maximum allowable trust chain verification depth
         * @param trustAnchors trust anchors used during PKIX validation
         * @param crls CRLs used during PKIX validation
         */
        public PKIXValidationInformation(int verificationDepth, Set<X509Certificate> trustAnchors, Set<X509CRL> crls) {
            this.trustChain = trustAnchors;
            this.crls = crls;
        }

        /**
         * Gets the maximum allowable trust chain verification depth.
         * 
         * @return maximum allowable trust chain verification depth
         */
        public int getVerificationDepth() {
            return verificationDepth;
        }

        /**
         * Gets the trust anchors used during PKIX validation.
         * 
         * @return trust anchors used during PKIX validation
         */
        public Set<X509Certificate> getTrustChain() {
            return trustChain;
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