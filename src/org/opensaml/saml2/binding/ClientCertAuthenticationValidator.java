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

package org.opensaml.saml2.binding;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.TransportValidator;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.security.TrustEngine;
import org.opensaml.security.X509EntityCredential;
import org.opensaml.security.impl.HttpX509EntityCredential;

public class ClientCertAuthenticationValidator implements TransportValidator {
    
    private static Logger log = Logger.getLogger(ClientCertAuthenticationValidator.class);
    
    private MetadataProvider metadataProvider;
    
    private TrustEngine<X509EntityCredential> trustEngine;
    
    public MetadataProvider getMetadataProvider(){
        return metadataProvider;
    }

    public void setMetadataProvider(MetadataProvider provider){
        metadataProvider = provider;
    }
    
    public TrustEngine<X509EntityCredential> getTrustEngine(){
        return trustEngine;
    }
    
    public void setTrustEngine(TrustEngine<X509EntityCredential> engine){
        trustEngine = engine;
    }

    public void valdate(ServletRequest request) throws BindingException {
        String issuerName = getSAML2IssuerName();

        // TODO handle no credential

        // TODO role?
        // TODO protocol?
        QName foo = null;
        String bar = null;

        try {
            RoleDescriptor roleDescriptor = metadataProvider.getRole(issuerName, foo, bar);
            if (roleDescriptor == null) {
                log.info("No metadata found for provider (" + issuerName + ").");
                return;
            }

            if (trustEngine.validate(new HttpX509EntityCredential((HttpServletRequest) request), roleDescriptor)) {
                issuerMetadata = roleDescriptor;
            } else {
                log.error("Authentication failed for provider (" + issuerName + ").");
                throw new BindingException("Authentication failed.");
            }

        } catch (MetadataProviderException e) {
            log.error("Error performing metadata lookup: " + e);
            throw new BindingException("Unable to perform metadata lookup.");
        }
    }

    private String getSAML2IssuerName() throws BindingException {

        if (message instanceof org.opensaml.saml2.core.Request) {
            org.opensaml.saml2.core.Request request = (org.opensaml.saml2.core.Request) message;
            if (request.getIssuer() == null || request.getIssuer().getValue() == null
                    || request.getIssuer().getValue().equals("")) {
                log.error("Expected SAML2 Request to contain a valid Issuer.");
                throw new BindingException("Expected SAML2 Request to contain a valid Issuer.");
            }
            return request.getIssuer().getValue();

        } else if (message instanceof org.opensaml.saml2.core.Response) {
            org.opensaml.saml2.core.Response response = (org.opensaml.saml2.core.Response) message;
            if (response.getIssuer() == null || response.getIssuer().getValue() == null
                    || response.getIssuer().getValue().equals("")) {
                log.error("Expected SAML2 Response to contain a valid Issuer.");
                throw new BindingException("Expected SAML2 Response to contain a valid Issuer.");
            }
            return response.getIssuer().getValue();
        }
        throw new IllegalStateException(
                "Could not determine SAML2 issuer name from message.  Excpected SAML2 Request or Response.");
    }
}
