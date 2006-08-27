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
import org.opensaml.saml2.common.Extensions;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Request;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.UnmarshallingException;
import org.w3c.dom.Attr;

/**
 * A thread-safe Unmarshaller for {@link org.opensaml.saml2.core.Request} objects.
 */
public abstract class RequestUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /**
     * Constructor
     * 
     * @param targetNamespaceURI
     * @param targetLocalName
     * @throws IllegalArgumentException
     */
    protected RequestUnmarshaller(String targetNamespaceURI, String targetLocalName) throws IllegalArgumentException {
        super(targetNamespaceURI, targetLocalName);
    }

    /** {@inheritDoc} */
    protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
        Request req = (Request) samlObject;

        if (attribute.getLocalName().equals(Request.VERSION_ATTRIB_NAME)) {
            req.setVersion(SAMLVersion.valueOf(attribute.getValue()));
        } else if (attribute.getLocalName().equals(Request.ID_ATTRIB_NAME))
            req.setID(attribute.getValue());
        else if (attribute.getLocalName().equals(Request.ISSUE_INSTANT_ATTRIB_NAME))
            req.setIssueInstant(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
        else if (attribute.getLocalName().equals(Request.DESTINATION_ATTRIB_NAME))
            req.setDestination(attribute.getValue());
        else if (attribute.getLocalName().equals(Request.CONSENT_ATTRIB_NAME))
            req.setConsent(attribute.getValue());
        else
            super.processAttribute(samlObject, attribute);
    }

    /** {@inheritDoc} */
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject)
            throws UnmarshallingException {
        Request req = (Request) parentSAMLObject;

        if (childSAMLObject instanceof Issuer)
            req.setIssuer((Issuer) childSAMLObject);
        // TODO Signature
        else if (childSAMLObject instanceof Extensions)
            req.setExtensions((Extensions) childSAMLObject);
        else
            super.processChildElement(parentSAMLObject, childSAMLObject);
    }
}