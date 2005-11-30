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

package org.opensaml.saml2.metadata.resolver;

/**
 * An implementation of a metadata resolver factory is responsible for 
 * creating concrete instances of {@link org.opensaml.saml2.metadata.resolver.MetadataResolver}s.
 */
public interface MetadataResolverFactory {

    /**
     * Creates a metadata resolver.
     * 
     * @return the resolver
     */
    public MetadataResolver createResolver();
    
    /**
     * Sets whether the resolver should perform schema validation on the received XML.
     * 
     * @param validate true to validate, false otherwise
     */
    public void setValidateXML(boolean validate);
    
    /**
     * Gets whether the resolver will perform schema validation on the received XML.
     * 
     * @return true to validate, false otherwise
     */
    public boolean validateXML();
    
    /**
     * Sets whether the resolver will validate digital signatures in the XML document.
     * 
     * @param validate true to validate, false otherwise
     */
    public void setValidateDigitalSignature(boolean validate);
    
    /**
     * Gets whether the resolver will validate digital signatures in the XML document.
     * 
     * @return true to validate, false otherwise
     */
    public boolean validateDigitalSignature();
}