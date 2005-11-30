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
import org.opensaml.saml2.metadata.resolver.MetadataResolverFactory;

/**
 * A base class for implementations of {@link org.opensaml.saml2.metadata.resolver.MetadataResolverFactory} 
 * that handles the management of schema and digital signature validation settings.
 */
public abstract class AbstractMetadataResolverFactory implements MetadataResolverFactory {

    private boolean schemaValidate;
    private boolean dsigValidate;
    
    /**
     * Gets whether resolvers created from this factory should validate the metadata 
     * document recieved against the SAML2 Metadata schema.
     * 
     * @return true if the document should be validated, false if not
     */
    public boolean validateXML() {
        return schemaValidate;
    }
    
    /**
     * Sets whether resolvers created from this factory should validate the metadata 
     * document recieved against the SAML2 Metadata schema.
     * 
     * @param validate true if the document should be validated, false if not
     */
    public void setValidateXML(boolean validate) {
        schemaValidate = validate;
    }

    /**
     * Gets whether resolvers created from this factory should validate digital signatures 
     * contained within a resolved metadata document.
     * 
     * @return true if the signatures should be validated, false if not
     */
    public boolean validateDigitalSignature() {
        return dsigValidate;
    }
    
    /**
     * Sets whether resolvers created from this factory should validate digital signatures 
     * contained within a resolved metadata document.
     * 
     * @param validate true if the signatures should be validated, false if not
     */
    public void setValidateDigitalSignature(boolean validate) {
        dsigValidate = validate;
    }

    /**
     * Delegates the creation of the resolver to {@link #doCreateResolver()} and 
     * then sets the appropriate validation properties.
     * 
     * @return the metadata resolver
     */
    public final MetadataResolver createResolver() {
        AbstractMetadataResolver resolver = doCreateResolver();
        resolver.setValidateXML(validateXML());
        resolver.setValidateDigitalSignature(validateDigitalSignature());
        return resolver;
    }
    
    /**
     * Creates a metadata resolver and prepares it for use.  The settings for 
     * schema and digital signature validation are handled by this abstract class 
     * and should <strong>NOT</strong> be dealt with here.
     * 
     * @return the metadata resolver
     */
    public abstract AbstractMetadataResolver doCreateResolver();
}
