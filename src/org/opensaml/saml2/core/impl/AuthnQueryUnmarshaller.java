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

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.AuthnQuery;
import org.opensaml.saml2.core.RequestedAuthnContext;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread-safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.core.AuthnQuery} objects.
 */
public class AuthnQueryUnmarshaller extends SubjectQueryUnmarshaller {

    /**
     * Constructor
     *
     */
    public AuthnQueryUnmarshaller() {
        super(SAMLConstants.SAML20P_NS, AuthnQuery.LOCAL_NAME);
    }

    /**
     * @see org.opensaml.saml2.core.impl.SubjectQueryUnmarshaller#processAttribute(org.opensaml.common.SAMLObject, java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlObject, String attributeName, String attributeValue) throws UnmarshallingException, UnknownAttributeException {
        AuthnQuery query = (AuthnQuery) samlObject;
        
        if (attributeName.equals(AuthnQuery.SESSION_INDEX_ATTRIB_NAME))
            query.setSessionIndex(attributeValue);
        else
            super.processAttribute(samlObject, attributeName, attributeValue);
    }

    /**
     * @see org.opensaml.saml2.core.impl.SubjectQueryUnmarshaller#processChildElement(org.opensaml.common.SAMLObject, org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(SAMLObject parentSAMLObject, SAMLObject childSAMLObject) throws UnmarshallingException, UnknownElementException {
        AuthnQuery query = (AuthnQuery) parentSAMLObject;
        
        if (childSAMLObject instanceof RequestedAuthnContext)
            query.setRequestedAuthnContext((RequestedAuthnContext) childSAMLObject);
        else
            super.processChildElement(parentSAMLObject, childSAMLObject);
    }
}