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
import org.opensaml.saml1.core.Action;
import org.opensaml.saml1.core.AuthorizationDecisionQuery;
import org.opensaml.saml1.core.Evidence;
import org.opensaml.saml1.core.Subject;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread-safe {@link org.opensaml.xml.io.Unmarshaller} for {@link org.opensaml.saml1.core.AuthorizationDecisionQuery} objects.
 */
public class AuthorizationDecisionQueryUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /** Constructor */
    public AuthorizationDecisionQueryUnmarshaller() {
        super(SAMLConstants.SAML1P_NS, AuthorizationDecisionQuery.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#processChildElement(org.opensaml.common.SAMLObject,
     *      org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(SAMLObject parentSAMLObject, SAMLObject childSAMLObject)
            throws UnmarshallingException, UnknownElementException {

        AuthorizationDecisionQuery authorizationDecisionQuery;
        authorizationDecisionQuery = (AuthorizationDecisionQuery) parentSAMLObject;

        if (childSAMLObject instanceof Subject) {
            authorizationDecisionQuery.setSubject((Subject) childSAMLObject);
        } else if (childSAMLObject instanceof Action) {
            authorizationDecisionQuery.getActions().add((Action) childSAMLObject);
        } else if (childSAMLObject instanceof Evidence) {
            authorizationDecisionQuery.setEvidence((Evidence) childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#processAttribute(org.opensaml.common.SAMLObject,
     *      java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlObject, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {

        AuthorizationDecisionQuery authorizationDecisionQuery;
        authorizationDecisionQuery = (AuthorizationDecisionQuery) samlObject;

        if (attributeName.equals(AuthorizationDecisionQuery.RESOURCE_ATTRIB_NAME)) {
            authorizationDecisionQuery.setResource(attributeValue);
        } else {
            super.processAttribute(samlObject, attributeName, attributeValue);
        }
    }
}