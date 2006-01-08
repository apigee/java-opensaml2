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

import org.apache.log4j.Logger;
import org.opensaml.common.SAMLConfig;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.AuthenticationStatement;
import org.opensaml.saml1.core.AuthorityBinding;
import org.opensaml.saml1.core.Subject;
import org.opensaml.saml1.core.SubjectLocality;
import org.opensaml.xml.IllegalAddException;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.util.DatatypeHelper;

/**
 *A thread-safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml1.core.AuthenticationStatement} objects.
 */
public class AuthenticationStatementUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /**
     * Constructor
     */
    public AuthenticationStatementUnmarshaller() {
        super(SAMLConstants.SAML1_NS, AuthenticationStatement.LOCAL_NAME);
    }

    /** Logger */
    private static Logger log = Logger.getLogger(AuthenticationStatementUnmarshaller.class);

    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processChildElement(org.opensaml.common.SAMLObject, org.opensaml.common.SAMLObject)
     */
@Override
    protected void processChildElement(SAMLObject parentElement, SAMLObject childElement)
            throws UnmarshallingException, UnknownElementException {

        AuthenticationStatement authenticationStatement = (AuthenticationStatement) parentElement;
        
        try {

            if (childElement instanceof Subject) {
                authenticationStatement.setSubject((Subject) childElement);
            } else if (childElement instanceof SubjectLocality) {
                authenticationStatement.setSubjectLocality((SubjectLocality) childElement);
            } else if (childElement instanceof AuthorityBinding) {
                authenticationStatement.addAuthorityBinding((AuthorityBinding) childElement);
            } else {
                log.error(childElement.getElementQName() + " is not a supported element for AuthenticationStatement objects");
                if (!SAMLConfig.ignoreUnknownElements()) {
                    throw new UnknownElementException(childElement.getElementQName()
                            + " is not a supported element for AuthorityBinding objects");
                }
            } 
        } catch (IllegalAddException e) {
            log.error("Couldn't add " + childElement + " to Assertion", e);
            throw new UnmarshallingException(e);
        }
    }
    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processAttribute(org.opensaml.common.SAMLObject, java.lang.String, java.lang.String)
     */
    @Override
    protected void processAttribute(SAMLObject samlElement, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {
        AuthenticationStatement authenticationStatement = (AuthenticationStatement) samlElement;
        
        if (AuthenticationStatement.AUTHENTICATIONINSTANT_ATTRIB_NAME.equals(attributeName)) {
            GregorianCalendar value = DatatypeHelper.stringToCalendar(attributeValue, 0);
            authenticationStatement.setAuthenticationInstant(value);
        } else if (AuthenticationStatement.AUTHENTICATIONMETHOD_ATTRIB_NAME.equals(attributeName)) {
            authenticationStatement.setAuthenticationMethod(attributeValue);
        } else {
            log.error(attributeName + "is not a supported attribute for AuthenticationStatement");
            if (!SAMLConfig.ignoreUnknownAttributes()) {
                throw new UnknownAttributeException(attributeName
                        + " is not a supported attributed for AuthenticationStatement objects");
            }
        }
    }
}