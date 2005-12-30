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

package org.opensaml.saml1.core.impl;

import org.opensaml.common.SAMLConfig;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.Response;
import org.opensaml.saml1.core.Status;
import org.opensaml.xml.IllegalAddException;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * A thread-safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml1.core.Response} objects.
 */
public class ResponseUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /**
     * Constructor
     */
    public ResponseUnmarshaller() {
        super(SAMLConstants.SAML1P_NS, Response.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#processChildElement(org.opensaml.common.SAMLObject,
     *      org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(SAMLObject parentElement, SAMLObject childElement)
            throws UnmarshallingException, UnknownElementException {

        Response response = (Response) parentElement;

        try {
            if (childElement instanceof Assertion) {
                response.setAssertion((Assertion) childElement);
            } else if (childElement instanceof Status) {
                response.setStatus((Status) childElement);
            } else {
                if (!SAMLConfig.ignoreUnknownElements()) {
                    throw new UnknownElementException(childElement.getElementQName()
                            + " is not a supported element for Response objects");
                }
            }
        } catch (IllegalAddException e) {
            throw new UnmarshallingException(e);
        }
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#processAttribute(org.opensaml.common.SAMLObject,
     *      java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlElement, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {

        Response response = (Response) samlElement;

        if (attributeName.equals(Response.INRESPONSETO_ATTRIB_NAME)) {
            response.setInResponseTo(attributeValue);
        } else if (attributeName.equals(Response.ISSUEINSTANT_ATTRIB_NAME)) {
            response.setIssueInstant(DatatypeHelper.stringToCalendar(attributeValue, 0));
        } else if (attributeName.equals(Response.MAJORVERSION_ATTRIB_NAME)) {
            try {
                if (Integer.parseInt(attributeValue) != 1) {
                    throw new UnmarshallingException("SAML version must be 1");
                }
            } catch (NumberFormatException n) {
                throw new UnmarshallingException(n);
            }
        } else if (attributeName.equals(Response.MINORVERSION_ATTRIB_NAME)) {
            try {
                int newVersion = Integer.parseInt(attributeValue);
                response.setMinorVersion(newVersion);
            } catch (NumberFormatException n) {
                throw new UnmarshallingException(n);
            }
        } else if (attributeName.equals(Response.RECIPIENT_ATTRIB_NAME)) {
            response.setRecipient(attributeValue);
        } else {
            if (!SAMLConfig.ignoreUnknownAttributes()) {
                throw new UnknownAttributeException(attributeName
                        + " is not a supported attributed for Response objects");
            }
        }
    }
}