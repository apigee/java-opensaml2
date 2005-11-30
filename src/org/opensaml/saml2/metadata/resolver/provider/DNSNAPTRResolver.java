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
 * This metadata resolver obtains a URL from the resolution of DNS NAPTR records
 * and then uses the {@link org.opensaml.saml2.metadata.resolver.provider.URLResolver} to
 * resolve the URL in to a metadata document.
 */
public class DNSNAPTRResolver extends AbstractMetadataResolver implements MetadataResolver {

    /**
     * Resolves the metadata URI, via DNS, to a URL and then resolves the URL 
     * to a metadata document.
     * 
     * @param metadataURI the metadata URI
     * 
     * @return the SAML2 metadata document
     * 
     * @throws ResolutionException thrown if there is a problem looking up the 
     * metadata document URL via DNS or resolving the URL
     */
    public Document doResolve(String metadataURI) throws ResolutionException {
        // TODO Auto-generated method stub
        return null;
    }

}
