/*
 * Copyright [2005] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.common;

import java.security.Key;
import java.util.Collection;

import javax.security.cert.X509Certificate;

/**
 * A data construct for information needed to digitally sign an XML Node.
 */
public class SigningContext {
    /**
     * The signature algorithim
     */
    private String signatureAlgorithim;

    /**
     * The digest algorithim
     */
    private String digestAlgorithim;

    /**
     * The signing key
     */
    private Key signingKey;

    /**
     * The certificates to be included with the signature
     */
    private Collection <X509Certificate> certs;

    /**
     * 
     * Constructor
     *
     */
    public SigningContext() {

    }

    /**
     * 
     * Constructor
     *
     * @param signatureAlgo the signature algorithim
     * @param signingKey the signing key
     * @param certificates the certificates to be included with the signature
     */
    public SigningContext(String signatureAlgo, Key signingKey, Collection<X509Certificate> certificates) {

    }

    /**
     * 
     * Constructor
     *
     * @param signatureAlgo the signature algorithim
     * @param digestAlgo the digest algorithim
     * @param signingKey the signing key
     * @param certificates the certificates to be included with the signature
     */
    public SigningContext(String signatureAlgo, String digestAlgo, Key signingKey,
            Collection<X509Certificate> certificates) {

    }

    /**
     * Gets the certificates to be included with the signature.
     * 
     * @return the certificates to be included with the signature
     */
    public Collection getCerts() {
        return certs;
    }

    /**
     * Sets the certificates to be included with the signature.
     * 
     * @param certs the certificates to be included with the signature
     */
    public void setCerts(Collection<X509Certificate> certs) {
        this.certs = certs;
    }

    /**
     * Gets the digest algorithim used in creating the signature.
     * 
     * @return the digest algorithim used in creating the signature
     */
    public String getDigestAlgorithim() {
        return digestAlgorithim;
    }

    /**
     * Sets the digest algorithim used in creating the signature.
     * 
     * @param digestAlgorithim the digest algorithim used in creating the signature
     */
    public void setDigestAlgorithim(String digestAlgorithim) {
        this.digestAlgorithim = digestAlgorithim;
    }

    /**
     * Gets the signature algorithim used in creating the signature.
     * 
     * @return the signature algorithim used in creating the signature
     */
    public String getSignatureAlgorithim() {
        return signatureAlgorithim;
    }

    /**
     * Sets the signature algorithim used in creating the signature.
     * 
     * @param signatureAlgorithim the signature algorithim used in creating the signature
     */
    public void setSignatureAlgorithim(String signatureAlgorithim) {
        this.signatureAlgorithim = signatureAlgorithim;
    }

    /**
     * Gets the signing key used to create the signature.
     * 
     * @return the signing key used to create the signature
     */
    public Key getSigningKey() {
        return signingKey;
    }

    /**
     * Sets the signing key used to create the signature.
     * 
     * @param signingKey the signing key used to create the signature
     */
    public void setSigningKey(Key signingKey) {
        this.signingKey = signingKey;
    }
}
