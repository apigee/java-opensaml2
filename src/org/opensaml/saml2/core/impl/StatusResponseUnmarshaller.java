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


import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml2.core.Extensions;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusResponse;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.UnmarshallingException;
import org.w3c.dom.Attr;

/**
 * A thread-safe Unmarshaller for {@link org.opensaml.saml2.core.StatusResponse}
 * objects.
 */
public abstract class StatusResponseUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /**
     * Constructor
     *
     * @param targetNamespaceURI
     * @param targetLocalName
     * @throws IllegalArgumentException
     */
     protected StatusResponseUnmarshaller(String targetNamespaceURI, String targetLocalName)
            throws IllegalArgumentException {
        super(targetNamespaceURI, targetLocalName);
    }

    /**
     * @see org.opensaml.xml.io.AbstractXMLObjectUnmarshaller#processAttribute(org.opensaml.xml.XMLObject, org.w3c.dom.Attr)
     */
    protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
        StatusResponse sr = (StatusResponse) samlObject;
        
        if (attribute.getLocalName().equals(StatusResponse.VERSION_ATTRIB_NAME)) {
            sr.setVersion(SAMLVersion.valueOf(attribute.getValue()));
        } else if (attribute.getLocalName().equals(StatusResponse.ID_ATTRIB_NAME))
            sr.setID(attribute.getValue());
        else if (attribute.getLocalName().equals(StatusResponse.IN_RESPONSE_TO_ATTRIB_NAME))
            sr.setInResponseTo(attribute.getValue());
        else if (attribute.getLocalName().equals(StatusResponse.ISSUE_INSTANT_ATTRIB_NAME))
            sr.setIssueInstant( new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()) );
        else if (attribute.getLocalName().equals(StatusResponse.DESTINATION_ATTRIB_NAME))
            sr.setDestination(attribute.getValue());
        else if (attribute.getLocalName().equals(StatusResponse.CONSENT_ATTRIB_NAME))
            sr.setConsent(attribute.getValue());
        else
            super.processAttribute(samlObject, attribute);
    }

    /**
     * @see org.opensaml.xml.io.AbstractXMLObjectUnmarshaller#processChildElement(org.opensaml.xml.XMLObject, org.opensaml.xml.XMLObject)
     */
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
        StatusResponse sr = (StatusResponse) parentSAMLObject;
        
        if (childSAMLObject instanceof Issuer)
            sr.setIssuer((Issuer) childSAMLObject);
        // TODO Signature
        else if (childSAMLObject instanceof Extensions)
            sr.setExtensions((Extensions) childSAMLObject);
        else if (childSAMLObject instanceof Status)
            sr.setStatus((Status) childSAMLObject);
        super.processChildElement(parentSAMLObject, childSAMLObject);
    }
}