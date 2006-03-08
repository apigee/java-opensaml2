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
import org.opensaml.saml1.core.Request;
import org.opensaml.saml1.core.RequestAbstractType;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Element;

/**
 * A thread safe Marshaller for {@link org.opensaml.saml1.core.Request} objects.
 */
public class RequestMarshaller extends AbstractSAMLObjectMarshaller {

    /**
     * Constructor
     */
    public RequestMarshaller() throws IllegalArgumentException {
        super(SAMLConstants.SAML1P_NS, Request.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.xml.io.AbstractXMLObjectMarshaller#marshallAttributes(org.opensaml.xml.XMLObject,
     *      org.w3c.dom.Element)
     */
    protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
        Request request = (Request) samlElement;

        if (request.getID() != null) {
            domElement.setAttributeNS(null, RequestAbstractType.ID_ATTRIB_NAME, request.getID());
        }
        
        if (request.getIssueInstant() != null) {
            String date = ISODateTimeFormat.dateTime().print(request.getIssueInstant());
            domElement.setAttributeNS(null, RequestAbstractType.ISSUEINSTANT_ATTRIB_NAME, date);
        }
        if (request.getMinorVersion() != 0) {
            domElement.setAttributeNS(null, RequestAbstractType.MAJORVERSION_ATTRIB_NAME, "1");
            domElement.setAttributeNS(null, RequestAbstractType.MINORVERSION_ATTRIB_NAME, Integer.toString(request
                    .getMinorVersion()));
        }
    }
}