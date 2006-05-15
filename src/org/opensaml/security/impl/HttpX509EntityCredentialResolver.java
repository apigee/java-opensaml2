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
import java.security.cert.X509Certificate;
import java.util.List;

import javax.security.auth.x500.X500Principal;
import javax.servlet.http.HttpServletRequest;

import javolution.util.FastList;

import org.opensaml.security.CredentialUsageTypeEnumeration;
import org.opensaml.security.EntityCredentialResolver;
import org.opensaml.security.X509EntityCredential;

/**
 * An entity credential built from the X.509 certificates contained in the HTTP request header
 * <code>javax.servlet.request.X509Certificate</code>. The entity certificate is considered to be the first
 * certificate in the certs list returned by the request. The entity ID is the RFC2253 Subject DN of the entity
 * certificate, the public key is the public key contained within the same certificate.
 */
public class HttpX509EntityCredentialResolver implements EntityCredentialResolver<X509EntityCredential> {

    /** HTTP header to pull certificate info from */
    public final static String X509_HEADER = "javax.servlet.request.X509Certificate";

    /** Resolved credential */
    private List<X509EntityCredential> credentials;

    /**
     * Constructor
     * 
     * @param httpRequest the HTTP request
     */
    public HttpX509EntityCredentialResolver(HttpServletRequest httpRequest) {
        X509Certificate[] chain = (X509Certificate[]) httpRequest.getAttribute(X509_HEADER);
        if (chain == null | chain.length == 0) {
            throw new IllegalArgumentException(
                    "HTTP Request does not contain X.509 certificates in header javax.servlet.request.X509Certificate");
        }
        X509Certificate entityCertificate = chain[0];

        List<X509Certificate> certificateChain = new FastList<X509Certificate>();
        for (int i = 0; i < chain.length; i++) {
            certificateChain.add(chain[i]);
        }

        String entityID = entityCertificate.getSubjectX500Principal().getName(X500Principal.RFC2253);
        if (entityID != null) {
            entityID = entityID.trim();
        }

        if (entityID == null || entityID.length() == 0) {
            throw new IllegalArgumentException("End-entity certificate does not contain a Subject DN");
        }

        SimpleX509EntityCredential credential = new SimpleX509EntityCredential(entityID, null, entityCertificate,
                certificateChain);
        credential.setCredentialUsageType(CredentialUsageTypeEnumeration.SIGNING);

        credentials = new FastList<X509EntityCredential>(2);
        credentials.add(credential);
    }

    /** {@inheritDoc} */
    public List<X509EntityCredential> resolveCredential() throws GeneralSecurityException {
        return credentials;
    }
}