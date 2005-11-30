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

import org.w3c.dom.Document;

/**
 * A resolver that takes a URI and resolves it to a DOM, level 3, 
 * document containing SAML2 Metadata.
 */
public interface MetadataResolver {

    /**
     * Resolves a URI into a DOM document containing SAML2 metadata.
     * 
     * @param metadataURI the URI
     * 
     * @return the DOM document
     * 
     * @throws ResolutionException thrown if a problem creating the DOM document occurs
     */
    public Document resolve(String metadataURI) throws ResolutionException;
    
    /**
     * Gets whether the resolver will perform schema validation on the received XML.
     * 
     * @return true to validate, false otherwise
     */
    public boolean validateXML();
    
    /**
     * Gets whether the resolver will validate digital signatures in the XML document.
     * 
     * @return true to validate, false otherwise
     */
    public boolean validateDigitalSignature();
}
