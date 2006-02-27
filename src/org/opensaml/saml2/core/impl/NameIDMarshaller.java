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

import org.opensaml.common.impl.AbstractSAMLObjectMarshaller;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.NameID;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Element;

/**
 * A thread safe Marshaller for {@link org.opensaml.saml2.core.NameID} objects.
 */
public class NameIDMarshaller extends AbstractSAMLObjectMarshaller {

    /** Constructor */
    public NameIDMarshaller() {
        super(SAMLConstants.SAML20_NS, NameID.LOCAL_NAME);
    }
    
    protected NameIDMarshaller(String targetNamespaceURI, String targetLocalName) {
        super(targetNamespaceURI, targetLocalName);
    }

    /*
     * @see org.opensaml.xml.io.AbstractXMLObjectMarshaller#marshallAttributes(org.opensaml.xml.XMLObject, org.w3c.dom.Element)
     */
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        NameID nameID = (NameID) samlObject;

        if (nameID.getNameQualifier() != null) {
            domElement.setAttributeNS(null, NameID.NAME_QUALIFIER_ATTRIB_NAME, nameID.getNameQualifier());
        }
        
        if (nameID.getSPNameQualifier() != null) {
            domElement.setAttributeNS(null, NameID.SP_NAME_QUALIFIER_ATTRIB_NAME, nameID.getSPNameQualifier());
        }
        
        if (nameID.getFormat() != null) {
            domElement.setAttributeNS(null, NameID.FORMAT_ATTRIB_NAME, nameID.getFormat());
        }

        if (nameID.getSPProviderID() != null) {
            domElement.setAttributeNS(null, NameID.SPPROVIDER_ID_ATTRIB_NAME, nameID.getSPProviderID());
        }
    }
}