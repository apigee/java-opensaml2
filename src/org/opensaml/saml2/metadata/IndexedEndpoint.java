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

import javax.xml.namespace.QName;

import org.opensaml.common.xml.SAMLConstants;


/**
 * SAML 2.0 Metadata IndexedEndpoint
 */
public interface IndexedEndpoint extends Endpoint {

    /** Local name, no namespace */
    public final static String DEFAULT_ELEMENT_LOCAL_NAME = "IndexedEndpoint";
    
    /** Default element name */
    public final static QName DEFUALT_ELEMENT_NAME = new QName(SAMLConstants.SAML20MD_NS, DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
    
    /** Local name of the XSI type */
    public final static String TYPE_LOCAL_NAME = "IndexedEndpointType"; 
        
    /** QName of the XSI type */
    public final static QName TYPE_NAME = new QName(SAMLConstants.SAML20MD_NS, TYPE_LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
    
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
