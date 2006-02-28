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

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.Response;
import org.opensaml.saml1.core.ResponseAbstractType;
import org.opensaml.saml1.core.Status;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.UnmarshallingException;
import org.w3c.dom.Attr;

/**
 * A thread-safe {@link org.opensaml.xml.io.Unmarshaller} for {@link org.opensaml.saml1.core.Response} objects.
 */
public class ResponseUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /** Logger */
    private static Logger log = Logger.getLogger(ResponseUnmarshaller.class);

    /** Constructor */
    public ResponseUnmarshaller() {
        super(SAMLConstants.SAML1P_NS, Response.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.xml.io.AbstractXMLObjectUnmarshaller#processChildElement(org.opensaml.xml.XMLObject,
     *      org.opensaml.xml.XMLObject)
     */
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject)
            throws UnmarshallingException {

        Response response = (Response) parentSAMLObject;

        if (childSAMLObject instanceof Assertion) {
            response.getAssertions().add((Assertion) childSAMLObject);
        } else if (childSAMLObject instanceof Status) {
            response.setStatus((Status) childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }

    /*
     * @see org.opensaml.xml.io.AbstractXMLObjectUnmarshaller#processAttribute(org.opensaml.xml.XMLObject,
     *      org.w3c.dom.Attr)
     */
    protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {

        Response response = (Response) samlObject;

        if (attribute.getLocalName().equals(ResponseAbstractType.INRESPONSETO_ATTRIB_NAME)) {
            response.setInResponseTo(attribute.getValue());
        } else if (attribute.getLocalName().equals(ResponseAbstractType.ISSUEINSTANT_ATTRIB_NAME)) {
            response.setIssueInstant(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
        } else if (attribute.getLocalName().equals(ResponseAbstractType.MAJORVERSION_ATTRIB_NAME)) {
            try {
                if (Integer.parseInt(attribute.getValue()) != 1) {
                    log.error("SAML version must be 1");
                    throw new UnmarshallingException("SAML version must be 1");
                }
            } catch (NumberFormatException n) {
                log.error("Parsing major version ", n);
                throw new UnmarshallingException(n);
            }
        } else if (attribute.getLocalName().equals(ResponseAbstractType.MINORVERSION_ATTRIB_NAME)) {
            try {
                int newVersion = Integer.parseInt(attribute.getValue());
                response.setMinorVersion(newVersion);
            } catch (NumberFormatException n) {
                log.error("Parsing minor version ", n);
                throw new UnmarshallingException(n);
            }
        } else if (attribute.getLocalName().equals(ResponseAbstractType.RECIPIENT_ATTRIB_NAME)) {
            response.setRecipient(attribute.getValue());
        } else {
            super.processAttribute(samlObject, attribute);
        }
    }
}