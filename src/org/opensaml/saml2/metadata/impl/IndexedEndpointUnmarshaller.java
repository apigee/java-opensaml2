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

import org.opensaml.common.SAMLObject;
import org.opensaml.common.SAMLObjectUnmarshaller;
import org.opensaml.saml2.metadata.IndexedEndpoint;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread-safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.metadata.IndexedEndpoint}
 * objects.
 */
public class IndexedEndpointUnmarshaller extends EndpointUnmarshaller implements SAMLObjectUnmarshaller {

    /**
     * Constructor
     * 
     * @param targetNamespaceURI
     * @param targetLocalName
     */
    public IndexedEndpointUnmarshaller(String targetNamespaceURI, String targetLocalName) {
        super(targetNamespaceURI, targetLocalName);
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#addAttribute(org.opensaml.common.SAMLObject,
     *      java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlObject, String attributeName, String attributeValue)
            throws UnmarshallingException {
        IndexedEndpoint iEndpoint = (IndexedEndpoint) samlObject;

        if (attributeName.equals(IndexedEndpoint.INDEX_ATTRIB_NAME)) {
            iEndpoint.setIndex(Integer.valueOf(attributeValue));
        } else if (attributeName.equals(IndexedEndpoint.IS_DEFAULT_ATTRIB_NAME)) {
            iEndpoint.setDefault(Boolean.valueOf(attributeValue));
        } else {
            super.processAttribute(samlObject, attributeName, attributeValue);
        }
    }
}