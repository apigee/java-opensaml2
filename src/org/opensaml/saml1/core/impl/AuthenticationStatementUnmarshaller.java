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
import org.opensaml.saml1.core.AuthenticationStatement;
import org.opensaml.saml1.core.AuthorityBinding;
import org.opensaml.saml1.core.Subject;
import org.opensaml.saml1.core.SubjectLocality;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * A thread-safe {@link org.opensaml.xml.io.Unmarshaller} for {@link org.opensaml.saml1.core.AuthenticationStatement}
 * objects.
 */
public class AuthenticationStatementUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /**
     * Constructor
     */
    public AuthenticationStatementUnmarshaller() {
        super(SAMLConstants.SAML1_NS, AuthenticationStatement.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processChildElement(org.opensaml.common.SAMLObject,
     *      org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(SAMLObject parentSAMLObject, SAMLObject childSAMLObject)
            throws UnmarshallingException, UnknownElementException {

        AuthenticationStatement authenticationStatement = (AuthenticationStatement) parentSAMLObject;

        if (childSAMLObject instanceof Subject) {
            authenticationStatement.setSubject((Subject) childSAMLObject);
        } else if (childSAMLObject instanceof SubjectLocality) {
            authenticationStatement.setSubjectLocality((SubjectLocality) childSAMLObject);
        } else if (childSAMLObject instanceof AuthorityBinding) {
            authenticationStatement.getAuthorityBindings().add((AuthorityBinding) childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }

    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processAttribute(org.opensaml.common.SAMLObject,
     *      java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlObject, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {
        AuthenticationStatement authenticationStatement = (AuthenticationStatement) samlObject;

        if (AuthenticationStatement.AUTHENTICATIONINSTANT_ATTRIB_NAME.equals(attributeName)) {
            GregorianCalendar value = DatatypeHelper.stringToCalendar(attributeValue, 0);
            authenticationStatement.setAuthenticationInstant(value);
        } else if (AuthenticationStatement.AUTHENTICATIONMETHOD_ATTRIB_NAME.equals(attributeName)) {
            authenticationStatement.setAuthenticationMethod(attributeValue);
        } else {
            super.processAttribute(samlObject, attributeName, attributeValue);
        }
    }
}