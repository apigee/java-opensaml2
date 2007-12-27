/*
 * Copyright [2007] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.saml2.metadata.provider;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.SignableXMLObject;
import org.opensaml.xml.signature.SignatureTrustEngine;

/**
 * 
 */
public class SignatureValidationFilter implements MetadataFilter {

    /** Trust engine used to validate a signature. */
    private SignatureTrustEngine signatureTrustEngine;

    /** Indicates whether signed metadata is required. */
    private boolean requireSignature;

    /**
     * Constructor.
     * 
     * @param engine the trust engine used to validate signatures on incoming metadata.
     */
    public SignatureValidationFilter(SignatureTrustEngine engine) {
        if (engine == null) {
            throw new IllegalArgumentException("Signature trust engine may not be null");
        }

        signatureTrustEngine = engine;
    }

    /**
     * Gets the trust engine used to validate signatures on incoming metadata.
     * 
     * @return trust engine used to validate signatures on incoming metadata
     */
    public SignatureTrustEngine getSignatureTrustEngine() {
        return signatureTrustEngine;
    }

    /**
     * Gets whether incoming metadata is required to be signed.
     * 
     * @return whether incoming metadata is required to be signed
     */
    public boolean getRequireSignature() {
        return requireSignature;
    }

    /**
     * Sets whether incoming metadata is required to be signed.
     * 
     * @param require whether incoming metadata is required to be signed
     */
    public void setRequireSignature(boolean require) {
        requireSignature = require;
    }

    /** {@inheritDoc} */
    public void doFilter(XMLObject metadata) throws FilterException {
        SignableXMLObject signedMetadata = (SignableXMLObject) metadata;

        if (!signedMetadata.isSigned() && requireSignature) {
            throw new FilterException("Metadata was unsignd and signatures are required.");
        }

        //TODO validate signature
    }
}