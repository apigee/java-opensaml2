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

import java.util.GregorianCalendar;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.AssertionArtifact;
import org.opensaml.saml1.core.AssertionIDReference;
import org.opensaml.saml1.core.Query;
import org.opensaml.saml1.core.Request;
import org.opensaml.saml1.core.RequestAbstractType;
import org.opensaml.saml1.core.RespondWith;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * A thread safe {@link org.opensaml.xml.io.Unmarshaller} for {@link org.opensaml.saml1.core.Request} objects.
 */
public class RequestUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /**
     * Constructor
     */
    public RequestUnmarshaller() throws IllegalArgumentException {
        super(SAMLConstants.SAML1P_NS, Request.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processChildElement(org.opensaml.common.SAMLObject, org.opensaml.common.SAMLObject)
     */
    @Override
    protected void processChildElement(SAMLObject parentElement, SAMLObject childElement)
            throws UnmarshallingException, UnknownElementException {
        Request request = (Request) parentElement;
        
        try {
            if (childElement instanceof RespondWith) {
                request.getRespondWiths().add((RespondWith) childElement);
            } else if (childElement instanceof Query) {
                request.setQuery((Query) childElement);
            } else if (childElement instanceof AssertionIDReference) {
                request.getAssertionIDReferences().add((AssertionIDReference) childElement);
            } else if (childElement instanceof AssertionArtifact) {
                request.getAssertionArtifacts().add((AssertionArtifact) childElement);
            } else {
               super.processChildElement(parentElement, childElement);
            }
        } catch (IllegalArgumentException e) {
            throw new UnmarshallingException(e);
        }
    }

    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processAttribute(org.opensaml.common.SAMLObject, java.lang.String, java.lang.String)
     */
    @Override
    protected void processAttribute(SAMLObject samlElement, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {
        
        Request request = (Request) samlElement;
        
        if (RequestAbstractType.ISSUEINSTANT_ATTRIB_NAME.equals(attributeName)) {
            GregorianCalendar cal = DatatypeHelper.stringToCalendar(attributeValue, DatatypeHelper.UTC_TIMEZONE);
            request.setIssueInstant(cal);
        } else if (RequestAbstractType.MINORVERSION_ATTRIB_NAME.equals(attributeName)) {
            request.setMinorVersion(Integer.parseInt(attributeValue));
        } else if (RequestAbstractType.MAJORVERSION_ATTRIB_NAME.equals(attributeName)) {
            if (Integer.parseInt(attributeValue) != 1) {
                throw new UnmarshallingException(attributeValue + " is invalid valued for " +
                          RequestAbstractType.MAJORVERSION_ATTRIB_NAME + ": 1 expected");
            }
        } else {
            super.processAttribute(samlElement, attributeName, attributeValue);
        }
    }

}
