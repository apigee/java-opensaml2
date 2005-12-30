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

import org.opensaml.common.SAMLObject;

/**
 * SAML 2.0 Metadata AdditionalMetadataLocation
 */
public interface AdditionalMetadataLocation extends SAMLObject {
    /** Element name, no namespace */
    public final static String LOCAL_NAME = "AdditionalMetadataLocation";
    
    /** "affiliationOwnerID" attribute's local name */
    public final static String NAMESPACE_ATTRIB_NAME = "namespace";
    
    /**
     * Gets the location URI.
     * 
     * @return the location URI
     */
    public String getLocationURI();
    
    /**
     * Sets the location URI.
     * 
     * @param locationURI the location URI
     */
    public void setLocationURI(String locationURI);
    
    /**
     * Gets the namespace URI.
     * 
     * @return the namespace URI
     */
    public String getNamespaceURI();
    
    /**
     * Sets the namespace URI.
     * 
     * @param namespaceURI the namespace URI
     */
    public void setNamespaceURI(String namespaceURI);
}
