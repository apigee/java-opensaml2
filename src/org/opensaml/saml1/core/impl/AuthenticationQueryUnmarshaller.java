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

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.AuthenticationQuery;
import org.opensaml.saml1.core.Subject;
import org.opensaml.xml.io.UnmarshallingException;

/**
 *
 */
public class AuthenticationQueryUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /**
     * Constructor
     */
    public AuthenticationQueryUnmarshaller() {
        super(SAMLConstants.SAML1P_NS, AuthenticationQuery.LOCAL_NAME);
   }
    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processChildElement(org.opensaml.common.SAMLObject, org.opensaml.common.SAMLObject)
     */
    @Override
    protected void processChildElement(SAMLObject parentSAMLObject, SAMLObject childSAMLObject)
        throws UnmarshallingException, UnknownElementException {
        AuthenticationQuery authenticationQuery = (AuthenticationQuery) parentSAMLObject;
        
        if (childSAMLObject instanceof Subject) {
            authenticationQuery.setSubject((Subject) childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }

    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processAttribute(org.opensaml.common.SAMLObject,
     *      java.lang.String, java.lang.String)
     */
    @Override
    protected void processAttribute(SAMLObject samlObject, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {
        
        AuthenticationQuery authenticationQuery = (AuthenticationQuery) samlObject;
        
        if (AuthenticationQuery.AUTHENTICATIONMETHOD_ATTRIB_NAME.equals(attributeName)) {
            authenticationQuery.setAuthenticationMethod(attributeValue);
        } else {
            super.processAttribute(samlObject, attributeName, attributeValue);
        }
    }
}
