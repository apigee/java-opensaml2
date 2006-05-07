/*
 * Copyright [2006] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.saml2.metadata.provider;

/**
 * Observer that can be registered with a {@link org.opensaml.saml2.metadata.provider.MetadataCache} 
 * to be notified of changes that occur within the cache.
 */
public interface MetadataCacheObserver {

    /**
     * Inidicates that a change has occured within a cache.
     * 
     * @param resolverID the ID of the resolver, or it's cached metadata, that was changed
     * @param operation the change
     */
    public void notify(String resolverID, short operation);
}