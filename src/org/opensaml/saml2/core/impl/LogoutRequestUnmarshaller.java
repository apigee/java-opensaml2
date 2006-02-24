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
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Identifier;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.LogoutRequest;
import org.opensaml.saml2.core.SessionIndex;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread-safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.core.LogoutRequest} objects.
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
     * @see org.opensaml.saml2.core.impl.RequestUnmarshaller#processAttribute(org.opensaml.common.SAMLObject, java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlObject, String attributeName, String attributeValue) throws UnmarshallingException, UnknownAttributeException {
        LogoutRequest req = (LogoutRequest) samlObject;
        
        if (attributeName.equals(LogoutRequest.REASON_ATTRIB_NAME))
            req.setReason(attributeValue);
        else if (attributeName.equals(LogoutRequest.NOT_ON_OR_AFTER_ATTRIB_NAME))
            req.setNotOnOrAfter(new DateTime(attributeValue, ISOChronology.getInstanceUTC()));
        else
            super.processAttribute(samlObject, attributeName, attributeValue);
    }

    /**
     * @see org.opensaml.saml2.core.impl.RequestUnmarshaller#processChildElement(org.opensaml.common.SAMLObject, org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(SAMLObject parentSAMLObject, SAMLObject childSAMLObject) throws UnmarshallingException, UnknownElementException {
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
