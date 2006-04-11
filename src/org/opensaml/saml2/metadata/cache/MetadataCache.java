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

package org.opensaml.saml2.metadata.cache;

import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.resolver.MetadataResolverFactory;
import org.opensaml.saml2.metadata.resolver.ResolutionException;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A metadata respository is used to cache and maintain metadata 
 * that has been fetched before.  A respository implementation 
 * SHOULD follow the stipulations for caching described in the SAML2 
 * Metadata specification.
 */
public interface MetadataCache {

    /**
     * Loads a metadata document, pointed to by the given URI and resolved by a resolver 
     * instance created from the given factory.
     * 
     * @param metadataURI the metadata URI
     * @param resolverFactory the factory that can create a resolver to resolve the metadata URI
     * 
     * @throws ResolutionException thrown if the URI can not be resolved into an W3C Document
     * @throws UnmarshallingException thrown if the W3C Document can not be unmarshalled into an {@link EntitiesDescriptor}
     */
    public void loadMetadata(String metadataURI, MetadataResolverFactory resolverFactory) throws ResolutionException, UnmarshallingException;
    
    /**
     * Loads a metadata document, pointed to by the URI and resolved by a resolver instance created from 
     * the given factory.  Before the metadata is stored in the repository it is first run through the given 
     * filter. 
     * 
     * @param metadataURI the metadata URI
     * @param resolverFactory the factory that can create a resolver to resolve the metadata URI
     * @param filter a filter applied to the metadata before it is stored in the repository
     * 
     * @throws ResolutionException thrown if the URI can not be resolved into an W3C Document
     * @throws UnmarshallingException thrown if the W3C Document can not be unmarshalled into an {@link EntitiesDescriptor}
     * @throws FilterException thrown if an error occurs during the filtering process
     */
    public void loadMetadata(String metadataURI, MetadataResolverFactory resolverFactory, MetadataFilter filter)throws ResolutionException, UnmarshallingException, FilterException;
    
    /**
     * Retrieves metadata corrsponding to the URI iff the top level of the metadata
     * is an EntitiesDescriptor
     * 
     * @param URI URI of the metadata
     * 
     * @return the metadata
     */
    public EntitiesDescriptor retrieveEntities(String URI);
    
    /**
     * Retrieves metadata corrsponding to the URI iff the top level of the metadata
     * is an EntityDescriptor
     * 
     * @param URI URI of the metadata
     * 
     * @return the metadata  Returns null i
     */
    public EntityDescriptor retrieveEntity(String URI);
    
    /**
     * Removes a metadata document from the repository.
     * 
     * @param metadataURI the URI of the metadata document
     */
    public void removeMetadata(String metadataURI);
    
    /**
     * Invalidate the metadata.  This would be called, for instance when a locally stored file with
     * metadata in it changes.
     */
    public void invalidateMetadata(String metadataURI);
}