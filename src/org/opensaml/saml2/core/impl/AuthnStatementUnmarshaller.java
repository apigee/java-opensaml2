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
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.AuthnContext;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.SubjectLocality;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread-safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.core.AuthnStatement}.
 */
public class AuthnStatementUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /** Constructor */
    public AuthnStatementUnmarshaller() {
        super(SAMLConstants.SAML20_NS, AuthnStatement.LOCAL_NAME);
    }

    /**
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processChildElement(org.opensaml.common.SAMLObject,
     *      org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(SAMLObject parentObject, SAMLObject childObject) throws UnmarshallingException,
            UnknownElementException {
        AuthnStatement authnStatement = (AuthnStatement) parentObject;
        if (childObject instanceof SubjectLocality) {
            authnStatement.setSubjectLocality((SubjectLocality) childObject);
        } else if (childObject instanceof AuthnContext) {
            authnStatement.setAuthnContext((AuthnContext) childObject);
        } else {
            super.processChildElement(parentObject, childObject);
        }
    }

    /**
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processAttribute(org.opensaml.common.SAMLObject,
     *      java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlObject, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {
        AuthnStatement authnStatement = (AuthnStatement) samlObject;
        if (attributeName.equals(AuthnStatement.AUTHN_INSTANT_ATTRIB_NAME)) {
            authnStatement.setAuthnInstant(new DateTime(attributeValue, ISOChronology.getInstanceUTC()));
        } else if (attributeName.equals(AuthnStatement.SESSION_INDEX_ATTRIB_NAME)) {
            authnStatement.setSessionIndex(attributeValue);
        } else if (attributeName.equals(AuthnStatement.SESSION_NOT_ON_OR_AFTER_ATTRIB_NAME)) {
            authnStatement.setSessionNotOnOrAfter(new DateTime(attributeValue, ISOChronology.getInstanceUTC()));
        } else {
            super.processAttribute(samlObject, attributeName, attributeValue);
        }
    }
}