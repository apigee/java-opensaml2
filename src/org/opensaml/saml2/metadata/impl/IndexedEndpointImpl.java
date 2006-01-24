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

/**
 * 
 */

package org.opensaml.saml2.metadata.impl;

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.IndexedEndpoint;

/**
 * Concrete implementation of {@link org.opensaml.saml2.metadata.IndexedEndpoint}
 */
public abstract class IndexedEndpointImpl extends EndpointImpl implements IndexedEndpoint {

    /** Index of this endpoint */
    private Integer index;

    /** isDefault attribute */
    private Boolean isDefault;

    /**
     * Constructor
     * 
     * @param namespaceURI the namespace URI for the element this SAML object represents
     * @param localName the local name of the element this SAML object represents
     */
    public IndexedEndpointImpl(String namespaceURI, String localName) {
        super(namespaceURI, localName);
        setElementNamespacePrefix(SAMLConstants.SAML20MD_PREFIX);
    }

    /*
     * @see org.opensaml.saml2.metadata.IndexedEndpoint#getIndex()
     */
    public Integer getIndex() {
        return index;
    }

    /*
     * @see org.opensaml.saml2.metadata.IndexedEndpoint#setIndex(int)
     */
    public void setIndex(Integer index) {
        this.index = prepareForAssignment(this.index, index);
    }

    /*
     * @see org.opensaml.saml2.metadata.IndexedEndpoint#isDefault()
     */
    public Boolean isDefault() {
        return isDefault;
    }

    /*
     * @see org.opensaml.saml2.metadata.IndexedEndpoint#setDefault(java.lang.Boolean)
     */
    public void setDefault(Boolean isDefault) {
        this.isDefault = prepareForAssignment(this.isDefault, isDefault);
    }
}