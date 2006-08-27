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

import org.joda.time.format.ISODateTimeFormat;
import org.opensaml.common.impl.AbstractSAMLObjectMarshaller;
import org.opensaml.saml2.core.StatusResponse;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Element;

/**
 * A thread safe Marshaller for {@link org.opensaml.saml2.core.StatusResponse} objects.
 */
public abstract class StatusResponseMarshaller extends AbstractSAMLObjectMarshaller {

    /**
     * Constructor
     *
     * @param targetNamespaceURI
     * @param targetLocalName
     * @throws IllegalArgumentException
     */
     protected StatusResponseMarshaller(String targetNamespaceURI, String targetLocalName) throws IllegalArgumentException {
        super(targetNamespaceURI, targetLocalName);
    }

    /** {@inheritDoc} */
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        StatusResponse sr = (StatusResponse) samlObject;
        
        if (sr.getVersion() != null) {
            domElement.setAttributeNS(null, StatusResponse.VERSION_ATTRIB_NAME, sr.getVersion().toString());
        }
        
       if (sr.getID() != null)  {
           domElement.setAttributeNS(null, StatusResponse.ID_ATTRIB_NAME, sr.getID());
           domElement.setIdAttributeNS(null, StatusResponse.ID_ATTRIB_NAME, true);
       }
       
       if (sr.getInResponseTo() != null) 
           domElement.setAttributeNS(null, StatusResponse.IN_RESPONSE_TO_ATTRIB_NAME, sr.getInResponseTo());
       
       if (sr.getVersion() != null)
           domElement.setAttributeNS(null, StatusResponse.VERSION_ATTRIB_NAME, sr.getVersion().toString());
       
       if (sr.getIssueInstant() != null) {
           String iiStr = ISODateTimeFormat.dateTime().print(sr.getIssueInstant());
           domElement.setAttributeNS(null, StatusResponse.ISSUE_INSTANT_ATTRIB_NAME, iiStr);
       }
       
       if (sr.getDestination() != null)
           domElement.setAttributeNS(null, StatusResponse.DESTINATION_ATTRIB_NAME, sr.getDestination());
       
       if (sr.getConsent() != null)
           domElement.setAttributeNS(null, StatusResponse.CONSENT_ATTRIB_NAME, sr.getConsent());
    }
}