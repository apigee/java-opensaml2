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
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Identifier;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.LogoutRequest;
import org.opensaml.saml2.core.SessionIndex;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.UnmarshallingException;
import org.w3c.dom.Attr;

/**
 * A thread-safe Unmarshaller for {@link org.opensaml.saml2.core.LogoutRequest} objects.
 */
public class LogoutRequestUnmarshaller extends RequestUnmarshaller {

    /**
     * Constructor
     *
     */
    public LogoutRequestUnmarshaller() {
        super(SAMLConstants.SAML20P_NS, LogoutRequest.LOCAL_NAME);
    }

    /**
     * @see org.opensaml.xml.io.AbstractXMLObjectUnmarshaller#processAttribute(org.opensaml.xml.XMLObject, org.w3c.dom.Attr)
     */
    protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
        LogoutRequest req = (LogoutRequest) samlObject;
        
        if (attribute.getLocalName().equals(LogoutRequest.REASON_ATTRIB_NAME))
            req.setReason(attribute.getValue());
        else if (attribute.getLocalName().equals(LogoutRequest.NOT_ON_OR_AFTER_ATTRIB_NAME))
            req.setNotOnOrAfter(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
        else
            super.processAttribute(samlObject, attribute);
    }

    /**
     * @see org.opensaml.xml.io.AbstractXMLObjectUnmarshaller#processChildElement(org.opensaml.xml.XMLObject, org.opensaml.xml.XMLObject)
     */
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
        LogoutRequest req = (LogoutRequest) parentSAMLObject;
        
        // NOTE: Issuer in superclass is also an instance of Identifier, so have to be careful
        if (childSAMLObject instanceof Identifier && !(childSAMLObject instanceof Issuer))
            req.setIdentifier((Identifier) childSAMLObject);
        else if (childSAMLObject instanceof SessionIndex)
            req.getSessionIndexes().add((SessionIndex) childSAMLObject);
        else
            super.processChildElement(parentSAMLObject, childSAMLObject);
    }

}
