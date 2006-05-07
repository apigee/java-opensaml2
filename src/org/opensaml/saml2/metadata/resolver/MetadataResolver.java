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

import org.opensaml.common.SAMLObject;

/**
 * Resolves a metadata location into metadata.
 */
public interface MetadataResolver {
    
    /**
     * Gets a unique ID for an instance of this resolver.
     * 
     * @return a unique ID for an instance of this resolver
     */
    public String getID();

    /**
     * Performs the resolution, fetching the metadata and creating a DOM document from it.
     * 
     * @return the DOM document
     * 
     * @throws ResolutionException thrown if there is a problem creating the DOM document, or unmarshalling it
     * @throws FilterException thrown if there is a problem applying the metadata filter
     */
    public SAMLObject resolve() throws ResolutionException, FilterException;

    /**
     * Gets the metadata filter applied to the resolved metadata.
     * 
     * @return the metadata filter applied to the resolved metadata
     */
    public MetadataFilter getMetadataFilter();

    /**
     * Sets the metadata filter applied to the resolved metadata.
     * 
     * @param newFilter the metadata filter applied to the resolved metadata
     */
    public void setMetadataFilter(MetadataFilter newFilter);
}