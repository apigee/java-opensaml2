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

package org.opensaml.saml2.metadata.impl;

import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.AdditionalMetadataLocation;

/**
 * Concreate implementation of {@link org.opensaml.saml2.metadata.AdditionalMetadataLocation}
 */
public class AdditionalMetadataLocationImpl extends AbstractSAMLObject implements AdditionalMetadataLocation {

    /** The metadata location */
    private String location;

    /** Namespace scope of the root metadata element at the location */
    private String namespace;

    /**
     * Constructor
     */
    public AdditionalMetadataLocationImpl() {
        super(SAMLConstants.SAML20MD_NS, AdditionalMetadataLocation.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20MD_PREFIX);
    }

    /*
     * @see org.opensaml.saml2.metadata.AdditionalMetadataLocation#getLocationURI()
     */
    public String getLocationURI() {
        return location;
    }

    /*
     * @see org.opensaml.saml2.metadata.AdditionalMetadataLocation#setLocationURI(java.lang.String)
     */
    public void setLocationURI(String locationURI) {
        location = prepareForAssignment(location, locationURI);
    }

    /*
     * @see org.opensaml.saml2.metadata.AdditionalMetadataLocation#getNamespaceURI()
     */
    public String getNamespaceURI() {
        return namespace;
    }

    /*
     * @see org.opensaml.saml2.metadata.AdditionalMetadataLocation#setNamespaceURI(java.lang.String)
     */
    public void setNamespaceURI(String namespaceURI) {
        namespace = prepareForAssignment(namespace, namespaceURI);
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        // No children for this element
        return null;
    }
}