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

package org.opensaml.xml.signature;

import org.opensaml.xml.XMLObject;

/**
 * XMLObject representing XML Digital Signature, version 20020212, X509Data element.
 */
public interface X509Data extends XMLObject {

    /** Element local name */
    public final static String LOCAL_NAME = "X509Data";

    /**
     * Gets the information about the issuer of this certificate.
     * 
     * @return the information about the issuer of this certificate
     */
    public X509IssuerSerial getIssuerSerial();

    /**
     * Sets the information about the issuer of this certificate.
     * 
     * @param newSerial the information about the issuer of this certificate
     */
    public void setIssuerSerial(X509IssuerSerial newSerial);

    /**
     * Gets the subject key identifier for this certificate.
     * 
     * @return the subject key identifier for this certificate
     */
    public X509SKI getSubjectKeyIdentifier();

    /**
     * Sets the subject key identifier for this certificate.
     * 
     * @param newSKI the subject key identifier for this certificate
     */
    public void setSubjectKeyIdentifier(X509SKI newSKI);

    /**
     * Gets the name of the subject for this certificate.
     * 
     * @return the name of the subject for this certificate
     */
    public X509SubjectName getSubjectName();

    /**
     * Sets the name of the subject for this certificate.
     * 
     * @param newSubjectName the name of the subject for this certificate
     */
    public void setSubjectName(X509SubjectName newSubjectName);

    /**
     * Gets the X.509 certficate.
     * 
     * @return the X.509 certficate
     */
    public X509Certificate getCertificate();

    /**
     * Sets the X.509 certficate.
     * 
     * @param newCertificate the X.509 certficate
     */
    public void setCertificate(X509Certificate newCertificate);

    /**
     * Gets the certificate revocation list information.
     * 
     * @return the certificate revocation list information
     */
    public X509CRL getCRL();

    /**
     * Sets the certificate revocation list information.
     * 
     * @param newCRL the certificate revocation list information
     */
    public void setCRL(X509CRL newCRL);
}