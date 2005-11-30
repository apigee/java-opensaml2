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

import org.opensaml.saml2.metadata.resolver.MetadataResolverFactory;

/**
 * Creates {@link org.opensaml.saml2.metadata.resolver.provider.DNSNAPTRResolver}s.
 */
public class DNSNAPTRResolverFactory extends AbstractMetadataResolverFactory implements MetadataResolverFactory {

    /**
     * Constructor.
     */
    public DNSNAPTRResolverFactory() {
        
    }
    
    /**
     * Creates a {@link org.opensaml.saml2.metadata.resolver.provider.DNSNAPTRResolver}.
     * 
     * @return the resolver instance
     */
    public AbstractMetadataResolver doCreateResolver() {
        // TODO Auto-generated method stub
        return null;
    }

}
