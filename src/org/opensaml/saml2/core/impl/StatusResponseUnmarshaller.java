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
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.saml2.core.Extensions;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusResponse;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread-safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.core.StatusResponse}
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
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processAttribute(org.opensaml.common.SAMLObject, java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlObject, String attributeName, String attributeValue) throws UnmarshallingException, UnknownAttributeException {
        StatusResponse sr = (StatusResponse) samlObject;
        
        if (attributeName.equals(StatusResponse.ID_ATTRIB_NAME))
            sr.setID(attributeValue);
        else if (attributeName.equals(StatusResponse.IN_RESPONSE_TO_ATTRIB_NAME))
            sr.setInResponseTo(attributeValue);
        // TODO how to handle SAMLVersion
        //else if (attributeName.equals(StatusResponse.VERSION_ATTRIB_NAME))
        //    sr.setVersion();
        else if (attributeName.equals(StatusResponse.ISSUE_INSTANT_ATTRIB_NAME))
            sr.setIssueInstant( new DateTime(attributeValue, ISOChronology.getInstanceUTC()) );
        else if (attributeName.equals(StatusResponse.DESTINATION_ATTRIB_NAME))
            sr.setDestination(attributeValue);
        else if (attributeName.equals(StatusResponse.CONSENT_ATTRIB_NAME))
            sr.setConsent(attributeValue);
        else
            super.processAttribute(samlObject, attributeName, attributeValue);
    }

    /**
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processChildElement(org.opensaml.common.SAMLObject, org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(SAMLObject parentSAMLObject, SAMLObject childSAMLObject) throws UnmarshallingException, UnknownElementException {
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
