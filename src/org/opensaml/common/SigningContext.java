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

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Set;

import org.apache.xml.security.algorithms.MessageDigestAlgorithm;
import org.apache.xml.security.signature.XMLSignature;

/**
 * A data construct for information needed to digitally sign an XML Node. By default the HMAC-SHA1 will be used to
 * create the message digest and RSA-SHA1 will be used to create the signature.
 */
public class SigningContext {

    /** ID attribute used as reference by digital signature */
    private String id;

    /**
     * The signature algorithim
     */
    private String signatureAlgorithim = XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA1;

    /**
     * The digest algorithim
     */
    private String digestAlgorithim = MessageDigestAlgorithm.ALGO_ID_DIGEST_SHA1;

    /**
     * The signing key
     */
    private PrivateKey signingKey;

    /**
     * Public key to validate signature with
     */
    private PublicKey publicKey;

    /**
     * Certificate containing the public key to validate signature with
     */
    private X509Certificate publicKeyCert;

    /**
     * The certificates to be included with the signature
     */
    private Set<X509Certificate> certs;

    /**
     * Constructor
     * 
     * @param id ID attribute value used a base reference for the signature
     */
    public SigningContext(String id) {
        this.id = id;
    }

    /**
     * Gets the ID attribute value used for the digital signature.
     * 
     * @return the ID attribute value used for the digital signature
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the certificates to be included with the signature.
     * 
     * @return the certificates to be included with the signature
     */
    public Set<X509Certificate> getCerts() {
        return certs;
    }

    /**
     * Sets the certificates to be included with the signature.
     * 
     * @param certs the certificates to be included with the signature
     */
    public void setCerts(Set<X509Certificate> certs) {
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
    public PrivateKey getSigningKey() {
        return signingKey;
    }

    /**
     * Sets the signing key used to create the signature.
     * 
     * @param key the signing key used to create the signature
     */
    public void setSigningKey(PrivateKey key) {
        signingKey = key;
    }

    /**
     * Gets the public key used to validate the signature.
     * 
     * @return the public key used to validate the signature
     */
    public PublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * Sets the public key used to validate the signature.
     * 
     * @param key the public key used to validate the signature
     */
    public void setPublicKey(PublicKey key) {
        publicKey = key;
    }

    /**
     * Gets the certificate containing the public key used to validate the signature.
     * 
     * @return certificate containing the public key used to validate the signature
     */
    public X509Certificate getPublicKeyCertificate() {
        return publicKeyCert;
    }

    /**
     * Sets the certificate containing the public key used to validate the signature.
     * 
     * @param cert the certificate containing the public key used to validate the signature
     */
    public void setPublicKeyCertificate(X509Certificate cert) {
        publicKeyCert = cert;
    }
}