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

package org.opensaml.saml1.core.impl;

import org.joda.time.format.ISODateTimeFormat;
import org.opensaml.common.impl.AbstractSAMLObjectMarshaller;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Response;
import org.opensaml.saml1.core.ResponseAbstractType;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Element;

/**
 * A thread safe Marshaller for {@link org.opensaml.saml1.core.Response} objects.
 */
public class ResponseMarshaller extends AbstractSAMLObjectMarshaller {

    /**
     * Constructor
     */
    public ResponseMarshaller() {
        super(SAMLConstants.SAML1P_NS, Response.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.xml.io.AbstractXMLObjectMarshaller#marshallAttributes(org.opensaml.xml.XMLObject,
     *      org.w3c.dom.Element)
     */
    protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {

        Response response = (Response) samlElement;

        if (response.getID() != null) {
            domElement.setAttributeNS(null, ResponseAbstractType.ID_ATTRIB_NAME, response.getID());
        }
        
        if (response.getInResponseTo() != null) {
            domElement.setAttributeNS(null, ResponseAbstractType.INRESPONSETO_ATTRIB_NAME, response.getInResponseTo());
        }

        if (response.getIssueInstant() != null) {
            String date = ISODateTimeFormat.dateTime().print(response.getIssueInstant());
            domElement.setAttributeNS(null, ResponseAbstractType.ISSUEINSTANT_ATTRIB_NAME, date);
        }

        if (response.getMinorVersion() != 0) {
            String minorVersion = Integer.toString(response.getMinorVersion());
            domElement.setAttributeNS(null, ResponseAbstractType.MINORVERSION_ATTRIB_NAME, minorVersion);
            domElement.setAttributeNS(null, ResponseAbstractType.MAJORVERSION_ATTRIB_NAME, "1");
        }

        if (response.getRecipient() != null) {
            domElement.setAttributeNS(null, ResponseAbstractType.RECIPIENT_ATTRIB_NAME, response.getRecipient());
        }
    }
}