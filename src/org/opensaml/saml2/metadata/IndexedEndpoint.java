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

package org.opensaml.saml2.metadata;


/**
 * SAML 2.0 Metadata IndexedEndpoint
 */
public interface IndexedEndpoint extends Endpoint {

    /** Local name, no namespace */
    public final static String LOCAL_NAME = "IndexedEndpoint";
    
    /** index attribute name */
    public final static String INDEX_ATTRIB_NAME = "index";
    
    /** isDeault attribute name */
    public final static String IS_DEFAULT_ATTRIB_NAME = "isDefault";
    
    /**
     * Gets the index of the endpoint.
     * 
     * @return index of the endpoint
     */
	public Integer getIndex();
    
    /**
     * Sets the index of the endpoint.
     * 
     * @param index index of the endpoint
     */
    public void setIndex(Integer index);
    
    /**
     * Gets whether this is the default endpoint in a list.
     * 
     * @return whether this is the default endpoint in a list
     */
    public Boolean isDefault();
    
    /**
     * Sets whether this is the default endpoint in a list.
     * 
     * @param isDefault whether this is the default endpoint in a list
     */
    public void setDefault(Boolean isDefault);
}
