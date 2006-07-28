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
import java.security.cert.CertificateParsingException;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.security.auth.x500.X500Principal;

import javolution.util.FastList;
import javolution.util.FastSet;

import org.apache.log4j.Logger;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.security.CredentialUsageTypeEnumeration;
import org.opensaml.security.TrustEngine;
import org.opensaml.security.X509EntityCredential;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * A trust engine that uses the X509 certificate and CRLs associated with a role to perform PKIX validation on security
 * tokens.
 */
public abstract class AbstractPKIXTrustEngine implements TrustEngine<X509EntityCredential> {

    /** Logger */
    private static Logger log = Logger.getLogger(AbstractPKIXTrustEngine.class);

    /** {@inheritDoc} */
    public X509EntityCredential validate(SignableSAMLObject samlObject, RoleDescriptor roleDescriptor) {
        if (samlObject.isSigned()) {
            Signature signature = samlObject.getSignature();
            KeyInfo keyInfo = signature.getKeyInfo();
            List<X509Certificate> entityCerts = keyInfo.getCertificates();

            if (entityCerts == null || entityCerts.size() == 0) {
                if (log.isDebugEnabled()) {
                    log
                            .debug("Requested validation on signed SAML object however no certificate information was included with the signature.  Unable to perform PKIX validation.");
                }
                return null;
            }

            if (log.isDebugEnabled()) {
                log.debug("Validating signed SAML object using PKIX validation.");
            }
            X509EntityCredential entityCredential = new SimpleX509EntityCredential(entityCerts);

            if (validate(entityCredential, roleDescriptor)) {
                return entityCredential;
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("Requested validation on unsigned SAML object, no validation performed.");
        }

        return null;
    }

    /** {@inheritDoc} */
    public boolean validate(X509EntityCredential entityCredential, RoleDescriptor roleDescriptor) {
        if (log.isDebugEnabled()) {
            log.debug("Attempting to validate X.509 credential against role descriptor");
        }

        if (entityCredential == null) {
            log.error("X.509 credential was null, unable to perform validation");
            return false;
        }

        if (roleDescriptor == null) {
            log.error("Role descriptor was null, unable to perform validation");
            return false;
        }
        
        if(log.isDebugEnabled()){
            log.debug("Attempting to match entity credential information with role credential information");
        }
        EntityDescriptor entityDescriptor = (EntityDescriptor) roleDescriptor.getParent();
        X509Certificate entityCerficate = entityCredential.getEntityCertificate();
        
        // NAME MATCHING:
        // First, check to see if the entity's ID is in the certificate
        if(!matchId(entityDescriptor.getEntityID(), entityCerficate)){
            
            // If not, try matching <KeyName/> elsements from the metadata
            boolean keyNameMatched = false;
            //
            KEYNAMECHECK: for(KeyDescriptor keyDescriptor : roleDescriptor.getKeyDescriptors()){
         
                // If it's not applicable for signing, skip it and move on
                if(keyDescriptor.getUse() != CredentialUsageTypeEnumeration.SIGNING){
                    if (log.isDebugEnabled()){
                        log.debug("Key descriptor is not for signing, skipping it");
                    }
                    continue;
                    
                } else {
                    List<String> keyNames = keyDescriptor.getKeyInfo().getKeyNames();
                    for(String keyName : keyNames){
                        if(matchKeyName(keyName, entityCerficate)){
                            keyNameMatched = true;
                            break KEYNAMECHECK;
                        }
                    }
                }
            }
            
            if (!keyNameMatched){
                log.error("Entity credentials are not valid for the given role descriptor");
                return false;
            }
        }
        
        Iterator<PKIXValidationInformation> pkixInfo = getValidationInformation(roleDescriptor);
        while(pkixInfo.hasNext()){
            if(pkixValidate(entityCredential, pkixInfo.next())){
                return true;
            }
        }
        
        return false;
    }

    /**
     * Attempts to validate the given entity credential using the PKIX information provided.
     * 
     * @param entityCredential the entity credential to validate
     * @param pkixInfo the PKIX information to validate the credential against
     * 
     * @return true if the given credential is valid, false if not
     */
    public boolean pkixValidate(X509EntityCredential entityCredential, PKIXValidationInformation pkixInfo) {
        Set<X509Certificate> trustChain = pkixInfo.getTrustChain();
        Set<X509CRL> crls = pkixInfo.getCRLs();

        if (trustChain == null || trustChain.size() < 1) {
            if (log.isDebugEnabled()) {
                log
                        .debug("Unable to validate signature, no trust anchors found in the PKIX validation information");
            }
            return false;
        }

        if (log.isDebugEnabled()) {
            log.debug("Attempting PKIX path validation on entity credential");
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
     * Gets a set of information necessary, for the given role, for PKIX validation of credentials. Each set of
     * validation information will be tried, in turn, until one succeeds or no more remain.
     * 
     * @return a set of information necessary, for the given role, for PKIX validation of credentials
     */
    protected abstract Iterator<PKIXValidationInformation> getValidationInformation(RoleDescriptor descriptor);

    /**
     * Checks to see if the given ID matches either the first CN component on the certificate's subject DN or any
     * of the certificate's DNS or URI subject alternate names. Matching is case-insensitive.
     * 
     * @param id the entity ID to match
     * @param certificate the certificate to match against
     * 
     * @return true if the entity ID matches the subject DN or subject alternate names of the certificate
     */
    private boolean matchId(String id, X509Certificate certificate) {
        if(log.isDebugEnabled()){
            log.debug("Attempting to match entity ID " + id + " with certificate subject information");
        }
        if (DatatypeHelper.isEmpty(id)) {
            log.error("Entity ID was empty or null");
            return false;
        }

        if (certificate == null) {
            log.error("X509 certificate null");
            return false;
        }

        String loweredEntityId = id.trim().toLowerCase();

        if(log.isDebugEnabled()){
            log.debug("Attempting to match entity ID " + id + " with the first CN component of the certificate's subject DN");
        }
        String firstCNComponent = getFirstCN(certificate.getSubjectX500Principal());
        if (!DatatypeHelper.isEmpty(firstCNComponent)) {
            if (loweredEntityId.equals(firstCNComponent.toLowerCase())) {
                if(log.isDebugEnabled()){
                    log.debug("Entity ID matched the first CN component of the certificate's subject DN");
                }
                return true;
            }
        }

        if(log.isDebugEnabled()){
            log.debug("Attempting to match entity ID with certificate's DNS and URI subject alt names");
        }
        try {
            Collection<List<?>> altNames = certificate.getSubjectAlternativeNames();
            if (altNames != null && altNames.size() > 0) {
                for (List altName : altNames) {
                    // 0th position represents the data type; 2 = DNS, 6 = URI
                    // 1st position contains the actual data to match
                    if (altName.get(0).equals(new Integer(2)) || altName.get(0).equals(new Integer(6))) {
                        if (altName.get(1).equals(id)) {
                            if(log.isDebugEnabled()){
                                log.debug("ID matched against subject alt name");
                            }
                            return true;
                        }
                    }
                }
            }
        } catch (CertificateParsingException e) {
            log.error("Unable to extract subject alt names from certificate", e);
        }

        if(log.isDebugEnabled()){
            log.debug("Unable to match ID against subject alt names");
        }
        return false;
    }
    
    /**
     * Gets the value of the first CN component in the given distinguished name.
     * 
     * @param principal the distinguished name
     * 
     * @return the value of the fist CN attribute in the DN or null if there are no CN components
     */
    private String getFirstCN(X500Principal principal) {
        if (principal == null) {
            return null;
        }

        String canonicalDN = principal.getName(X500Principal.CANONICAL);
        if(log.isDebugEnabled()){
            log.debug("Extracting first CN component from DN " + canonicalDN);
        }
        StringTokenizer dnTokens = new StringTokenizer(canonicalDN, ",");
        String dnToken;

        while (dnTokens.hasMoreTokens()) {
            dnToken = dnTokens.nextToken();
            dnToken = dnToken.trim();
            if (dnToken.startsWith("cn=")) {
                return dnToken.substring(dnToken.indexOf("=") + 1);
            }
        }

        return null;
    }

    /**
     * Checks if the given key name matches the certificate's Subject DN, the first CN component 
     * of the subject DN, or any DNS or URI subject alt names.
     * 
     * @param keyName the key name to check
     * @param certificate the certificate to check the key name against
     * 
     * @return true if the key name matches, false if not
     */
    private boolean matchKeyName(String keyName, X509Certificate certificate){
        if(log.isDebugEnabled()){
            log.debug("Attempting to match key name " + keyName + " against certificate information");
        }
        
        if(DatatypeHelper.isEmpty(keyName)){
            log.error("Key name is null or empty");
            return false;
        }
        
        if(certificate == null){
            log.error("Certificate is null");
            return false;
        }
        
        if(log.isDebugEnabled()){
            log.debug("Attempting to match key name against certificate's subject DN");
        }
        X500Principal subjectPrincipal = certificate.getSubjectX500Principal();
        try{
            if(subjectPrincipal.equals(new X500Principal(keyName))){
                if(log.isDebugEnabled()){
                    log.debug("Key name matched certificate's subject DN");
                }
                return true;
            }
        }catch(IllegalArgumentException e){
            // ingore this exception, this occurs if the key name
            // is not a valid DN, which is okay
        }
        
        return matchId(keyName, certificate);
    }

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