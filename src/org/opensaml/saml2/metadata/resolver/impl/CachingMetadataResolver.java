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

package org.opensaml.saml2.metadata.resolver.impl;

import org.opensaml.saml2.metadata.resolver.FilterException;
import org.opensaml.saml2.metadata.resolver.MetadataFilter;
import org.opensaml.saml2.metadata.resolver.MetadataResolver;
import org.opensaml.saml2.metadata.resolver.ResolutionException;
import org.opensaml.xml.XMLObject;

/**
 * A metadata resolver that wraps another resolver and caches the returned metadata. Metadata is refreshed when either
 * the shortest cache duration or the earliest valid until time has been reached, whichever occurs first.
 */
public class CachingMetadataResolver implements MetadataResolver {

    /** The wrapped resolver */
    private MetadataResolver wrappedResolver;

    /**
     * Constructor
     * 
     * @param resolver the resolver whose returned metadata will be cached
     */
    public CachingMetadataResolver(MetadataResolver resolver) {
        wrappedResolver = resolver;
    }

    /**
     * {@inheritDoc}
     */
    public XMLObject resolve() throws ResolutionException, FilterException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public MetadataFilter getMetadataFilter() {
        return wrappedResolver.getMetadataFilter();
    }

    /**
     * {@inheritDoc}
     */
    public void setMetadataFilter(MetadataFilter newFilter) {
        wrappedResolver.setMetadataFilter(newFilter);
    }
}