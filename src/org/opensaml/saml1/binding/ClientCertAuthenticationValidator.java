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

package org.opensaml.saml1.binding;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import javolution.util.FastList;

import org.apache.log4j.Logger;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.TransportValidator;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.security.TrustEngine;
import org.opensaml.security.X509EntityCredential;
import org.opensaml.security.X509Util;
import org.opensaml.security.impl.HttpX509EntityCredential;

/**
 * Validates the client certificate used in a SAML 1 connection.
 */
public class ClientCertAuthenticationValidator implements TransportValidator<HttpServletRequest> {
    
    /** Class logger */
    private static Logger log = Logger.getLogger(ClientCertAuthenticationValidator.class);
    
    /** Metadata provider to lookup issuer information */
    private MetadataProvider metadataProvider;
    
    /** Trust engine used to verify metadata */
    private TrustEngine<X509EntityCredential> trustEngine;
    
    /**
     * Gets the metadata provider used to lookup issuer data.
     * 
     * @return metadata provider used to lookup issuer data
     */
    public MetadataProvider getMetadataProvider(){
        return metadataProvider;
    }

    /**
     * Sets the metadata provider used to lookup issuer data.
     * 
     * @param provider metadata provider used to lookup issuer data
     */
    public void setMetadataProvider(MetadataProvider provider){
        metadataProvider = provider;
    }
    
    /**
     * Gets the engine used to validate the trustworthiness of digital certificates.
     * 
     * @return  engine used to validate the trustworthiness of digital certificates
     */
    public TrustEngine<X509EntityCredential> getTrustEngine(){
        return trustEngine;
    }
    
    /**
     * Sets the engine used to validate the trustworthiness of digital certificates.
     * 
     * @param engine engine used to validate the trustworthiness of digital certificates
     */
    public void setTrustEngine(TrustEngine<X509EntityCredential> engine){
        trustEngine = engine;
    }

    /** {@inheritDoc} */
    public void validate(HttpServletRequest request) throws BindingException {

        HttpX509EntityCredential credential;
        try {
            credential = new HttpX509EntityCredential(request);
        } catch (IllegalArgumentException e) {
            log.info("SOAP message not accompanied by SSL transport credentials.");
            return;
        }

        // Try and authenticate the requester as any of the potentially relevant identifiers we know.
        POSSIBLE_ISSUERS: for (String issuerName : getIssuerNames(credential)) {

            // TODO role?
            // TODO protocol, hardcode to SAML
            QName foo = null;
            String bar = null;

            try {
                RoleDescriptor roleDescriptor = getMetadataProvider().getRole(issuerName, foo, bar);
                if (roleDescriptor == null) {
                    log.info("No metadata found for provider (" + issuerName + ").");
                    continue POSSIBLE_ISSUERS;
                }

                if (getTrustEngine().validate(credential, roleDescriptor)) {
                    //issuerMetadata = roleDescriptor;
                    break POSSIBLE_ISSUERS;
                } else {
                    log.error("Authentication failed for provider (" + issuerName + ").");
                    throw new BindingException("Authentication failed.");
                }

            } catch (MetadataProviderException e) {
                log.error("Error performing metadata lookup: " + e);
                throw new BindingException("Unable to perform metadata lookup.");
            }
        }
    }
    
    /**
     * Gets the list of possible issuer names, pulled from the credential's entity certificate subject and subject alt names.
     * 
     * @param credential the credental of the entity that issued the request
     * 
     * @return  possible issuer names
     */
    private List<String> getIssuerNames(HttpX509EntityCredential credential) {
        FastList<String> issuerNames = new FastList<String>();

        List<String> entityCertCNs = X509Util.getCommonNames(credential.getEntityCertificate().getSubjectX500Principal());
        issuerNames.add(entityCertCNs.get(0));
        
        Integer[] altNameTypes = {X509Util.DNS_ALT_NAME, X509Util.URI_ALT_NAME};
        issuerNames.addAll(X509Util.getAltNames(credential.getEntityCertificate(), altNameTypes));

        return issuerNames;
    }
}