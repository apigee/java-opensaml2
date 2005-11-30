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

package org.opensaml.saml2.metadata.resolver.provider;

import org.opensaml.saml2.metadata.resolver.MetadataResolver;
import org.opensaml.saml2.metadata.resolver.ResolutionException;
import org.w3c.dom.Document;

/**
 * A base class for implementations of {@link org.opensaml.saml2.metadata.resolver.MetadataResolver}
 * that handles schema and digital signature validation.
 */
public abstract class AbstractMetadataResolver implements MetadataResolver {

    private boolean schemaValidate;
    private boolean dsigValidate;

    /**
     * Gets whether this resolver should validate the metadata 
     * document recieved against the SAML2 Metadata schema.
     * 
     * @return true if the document should be validated, false if not
     */
    public boolean validateXML() {
        return schemaValidate;
    }
    
    /**
     * Sets whether this resolver should validate the metadata 
     * document recieved against the SAML2 Metadata schema.
     * 
     * @param validate true if the document should be validated, false if not
     */
    public void setValidateXML(boolean validate) {
        schemaValidate = validate;
    }

    /**
     * Gets whether this resolver should validate digital signatures 
     * contained within a resolved metadata document.
     * 
     * @return true if the signatures should be validated, false if not
     */
    public boolean validateDigitalSignature() {
        return dsigValidate;
    }
    
    /**
     * Sets whether this resolver should validate digital signatures 
     * contained within a resolved metadata document.
     * 
     * @param validate true if the signatures should be validated, false if not
     */
    public void setValidateDigitalSignature(boolean validate) {
        dsigValidate = validate;
    }
    
    /**
     * Delegates the resolution of the given URI into a DOM document, via 
     * {@link #doResolve(String)}, and then performs schema and digital 
     * signature validation as necessary.
     * 
     * @return the metadata document
     * 
     * @throws ResolutionException thrown if the document can not be resolved, the 
     * metadata file did not contain well-formed or valid XML, or the digital 
     * signatures did not verify
     */
    public Document resolve(String metadataURI) throws ResolutionException {
        Document metadata = doResolve(metadataURI);
        
        if(validateXML()) {
            //TODO validate against schema
        }
        
        if(validateDigitalSignature()) {
            //TODO
        }
        
        return metadata;
    }

    /**
     * Resolves the given URI into a metadata DOM document.  XML Schema and digitial 
     * signature validation should <strong>NOT</strong> be handled by this method as
     * it is performed by {@link #resolve(String)} as necessary.
     * 
     * @param metadataURI the metadata URI
     * 
     * @return the metadata document
     * 
     * @throws ResolutionException thrown if the document can not be resolved or the 
     * metadata file did not contain well-formed XML
     */
    public abstract Document doResolve(String metadataURI) throws ResolutionException;
}
