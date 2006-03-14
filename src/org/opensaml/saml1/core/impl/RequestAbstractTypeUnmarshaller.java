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

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml1.core.RequestAbstractType;
import org.opensaml.saml1.core.RespondWith;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.UnmarshallingException;
import org.w3c.dom.Attr;

/**
 * A thread safe Unmarshaller for {@link org.opensaml.saml1.core.RequestAbstractType} objects.
 */
public abstract class RequestAbstractTypeUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /**
     * Constructor
     *
     * @param targetNamespaceURI
     * @param targetLocalName
     * @throws IllegalArgumentException
     */
    protected RequestAbstractTypeUnmarshaller(String targetNamespaceURI, String targetLocalName)
            throws IllegalArgumentException {
        super(targetNamespaceURI, targetLocalName);
    }
    
    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processChildElement(org.opensaml.xml.XMLObject, org.opensaml.xml.XMLObject)
     */
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
        RequestAbstractType request = (RequestAbstractType) parentSAMLObject;
        
        if (childSAMLObject instanceof RespondWith) {
            request.getRespondWiths().add((RespondWith) childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }

    /*
     * @see org.opensaml.xml.io.AbstractXMLObjectUnmarshaller#processAttribute(org.opensaml.xml.XMLObject, org.w3c.dom.Attr)
     */
    protected void processAttribute(XMLObject samlElement, Attr attribute) throws UnmarshallingException {
        RequestAbstractType request = (RequestAbstractType) samlElement;

        if (RequestAbstractType.ID_ATTRIB_NAME.equals(attribute.getLocalName())) {
            request.setID(attribute.getValue());
        } else if (RequestAbstractType.ISSUEINSTANT_ATTRIB_NAME.equals(attribute.getLocalName())) {
            DateTime cal = new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC());
            request.setIssueInstant(cal);
        } else if (RequestAbstractType.MINORVERSION_ATTRIB_NAME.equals(attribute.getLocalName())) {
            request.setMinorVersion(Integer.parseInt(attribute.getValue()));
        } else if (RequestAbstractType.MAJORVERSION_ATTRIB_NAME.equals(attribute.getLocalName())) {
            if (Integer.parseInt(attribute.getValue()) != 1) {
                throw new UnmarshallingException(attribute.getValue() + " is invalid valued for "
                        + RequestAbstractType.MAJORVERSION_ATTRIB_NAME + ": 1 expected");
            }
        } else {
            super.processAttribute(samlElement, attribute);
        }
    }

}
