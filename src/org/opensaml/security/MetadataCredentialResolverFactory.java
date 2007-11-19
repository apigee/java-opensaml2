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

package org.opensaml.security;

import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.xml.util.AbstractSingletonFactory;

/**
 * Singleton factory for producing instances of {@link MetadataCredentialResolver}
 * based on a given instance of {@link MetadataProvider}.  Only once instance of 
 * a metadata credential resolver will exist for each metadata provider instance.
 */
public class MetadataCredentialResolverFactory 
    extends AbstractSingletonFactory<MetadataProvider, MetadataCredentialResolver> {

    /** {@inheritDoc} */
    protected MetadataCredentialResolver createNewInstance(MetadataProvider metadataProvider) {
        return new MetadataCredentialResolver(metadataProvider);
    }
    
}
