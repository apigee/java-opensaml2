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

import java.util.List;
import java.util.Vector;

import org.opensaml.xml.XMLObject;

/**
 * A filter that allows the composition of {@link org.opensaml.saml2.metadata.cache.MetadataFilter}s.
 * Filters will be executed on the given metadata document in the order they were added to the chain.
 */
public class MetadataFilterChain implements MetadataFilter {

    private Vector<MetadataFilter> filters;
    
    public MetadataFilterChain() {
        filters = new Vector<MetadataFilter>();
    }
    
    /**
     * Evaluates the filters in this chain in the order they were added.
     */
    public final void doFilter(XMLObject object) throws FilterException{
        MetadataFilter filter;
        
        for(int i = 0; i < filters.size(); i++) {
            filter = filters.get(i);
            filter.doFilter(object);
        }
    }
    
    /**
     * Gets the list of {@link MetadataFilter}s that make up this chain.
     * 
     * @return the filters that make up this chain
     */
    public List<MetadataFilter> getFilters() {
        return filters;
    }
}