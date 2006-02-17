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

package org.opensaml.saml2.core.impl;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.NameID;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread-safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.core.NameID} objects.
 */
public class NameIDUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /** Constructor */
    public NameIDUnmarshaller() {
        super(SAMLConstants.SAML20_NS, NameID.LOCAL_NAME);
    }

    protected NameIDUnmarshaller(String targetNamespaceURI, String targetLocalName) {
        super(targetNamespaceURI, targetLocalName);
    }

    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processAttribute(org.opensaml.common.SAMLObject,
     *      java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlObject, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {
        NameID nameID = (NameID) samlObject;
        if (attributeName.equals(NameID.NAME_QUALIFIER_ATTRIB_NAME)) {
            nameID.setNameQualifier(attributeValue);
        } else if (attributeName.equals(NameID.SP_NAME_QUALIFIER_ATTRIB_NAME)) {
            nameID.setSPNameQualifier(attributeValue);
        } else if (attributeName.equals(NameID.FORMAT_ATTRIB_NAME)) {
            nameID.setFormat(attributeValue);
        } else if (attributeName.equals(NameID.SPPROVIDER_ID_ATTRIB_NAME)) {
            nameID.setSPProviderID(attributeValue);
        } else {
            super.processAttribute(samlObject, attributeName, attributeValue);
        }
    }
}