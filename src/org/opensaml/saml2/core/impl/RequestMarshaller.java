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
import org.opensaml.saml2.core.Request;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Element;

/**
 * A thread safe Marshaller for {@link org.opensaml.saml2.core.Request} objects.
 */
public abstract class RequestMarshaller extends AbstractSAMLObjectMarshaller {

    /**
     * Constructor
     * 
     * @param targetNamespaceURI
     * @param targetLocalName
     * @throws IllegalArgumentException
     */
    protected RequestMarshaller(String targetNamespaceURI, String targetLocalName) throws IllegalArgumentException {
        super(targetNamespaceURI, targetLocalName);
    }

    /** {@inheritDoc} */
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        Request req = (Request) samlObject;

        if (req.getVersion() != null) {
            domElement.setAttributeNS(null, Request.VERSION_ATTRIB_NAME, req.getVersion().toString());
        }

        if (req.getID() != null) {
            domElement.setAttributeNS(null, Request.ID_ATTRIB_NAME, req.getID());
            domElement.setIdAttributeNS(null, Request.ID_ATTRIB_NAME, true);
        }

        if (req.getVersion() != null)
            domElement.setAttributeNS(null, Request.VERSION_ATTRIB_NAME, req.getVersion().toString());

        if (req.getIssueInstant() != null) {
            String iiStr = ISODateTimeFormat.dateTime().print(req.getIssueInstant());
            domElement.setAttributeNS(null, Request.ISSUE_INSTANT_ATTRIB_NAME, iiStr);
        }

        if (req.getDestination() != null)
            domElement.setAttributeNS(null, Request.DESTINATION_ATTRIB_NAME, req.getDestination());

        if (req.getConsent() != null)
            domElement.setAttributeNS(null, Request.CONSENT_ATTRIB_NAME, req.getConsent());
    }
}