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

import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.metadata.resolver.FilterException;
import org.opensaml.saml2.metadata.resolver.MetadataFilter;
import org.opensaml.saml2.metadata.resolver.MetadataResolver;
import org.opensaml.saml2.metadata.resolver.ResolutionException;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.io.UnmarshallingException;
import org.w3c.dom.Document;

/**
 * A base class for implementations of {@link org.opensaml.saml2.metadata.resolver.MetadataResolver} that handles schema
 * and digital signature validation.
 */
public abstract class AbstractMetadataResolver implements MetadataResolver {

    /** Metadata filter to apply to resolved metadata */
    private MetadataFilter metadataFilter;

    /**
     * {@inheritDoc}
     */
    public MetadataFilter getMetadataFilter() {
        return metadataFilter;
    }

    /**
     * {@inheritDoc}
     */
    public void setMetadataFilter(MetadataFilter newFilter) {
        metadataFilter = newFilter;
    }

    /**
     * Unmarshalls the metadata document and filters it, if any filter is set. The metadata document is retrieved
     * through {@link #retrieveDOM()}.
     * 
     * @return {@inheritDoc}
     * 
     * @throws ResolutionException {@inheritDoc}
     * @throws FilterException {@inheritDoc}
     */
    public SAMLObject resolve() throws ResolutionException, FilterException {
        Document metadataDocument = retrieveDOM();

        UnmarshallerFactory unmarshallerFactory = Configuration.getUnmarshallerFactory();
        Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(metadataDocument.getDocumentElement());
        SAMLObject metadata;
        try {
            metadata = (SAMLObject) unmarshaller.unmarshall(metadataDocument.getDocumentElement());
        } catch (UnmarshallingException e) {
            throw new ResolutionException("Unable to unmarshall metadata document", e);
        }

        if (metadataFilter != null) {
            metadataFilter.doFilter(metadata);
        }

        return metadata;
    }

    /**
     * Retrieves the metadata DOM.
     * 
     * @return the metadata document
     * 
     * @throws ResolutionException thrown if the document can not be resolved or the metadata file did not contain
     *             well-formed XML
     */
    protected abstract Document retrieveDOM() throws ResolutionException;
}