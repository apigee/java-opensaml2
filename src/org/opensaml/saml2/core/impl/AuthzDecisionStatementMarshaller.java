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
import org.opensaml.common.impl.AbstractSAMLObjectMarshaller;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.AuthzDecisionStatement;
import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Element;

/**
 * A thread-safe {@link org.opensaml.common.io.Marshaller} for {@link org.opensaml.saml2.core.AuthzDecisionStatement}.
 */
public class AuthzDecisionStatementMarshaller extends AbstractSAMLObjectMarshaller {

    /** Constructor */
    public AuthzDecisionStatementMarshaller() {
        super(SAMLConstants.SAML20_NS, AuthzDecisionStatement.LOCAL_NAME);
    }

    /**
     * @see org.opensaml.common.impl.AbstractSAMLObjectMarshaller#marshallAttributes(org.opensaml.common.SAMLObject,
     *      org.w3c.dom.Element)
     */
    protected void marshallAttributes(SAMLObject samlObject, Element domElement) throws MarshallingException {
        AuthzDecisionStatement authzDS = (AuthzDecisionStatement) samlObject;

        if (authzDS.getResource() != null) {
            domElement.setAttributeNS(null, AuthzDecisionStatement.RESOURCE_ATTRIB_NAME, authzDS.getResource());
        }

        if (authzDS.getDecision() != null) {
            domElement.setAttributeNS(null, AuthzDecisionStatement.DECISION_ATTRIB_NAME, authzDS.getDecision()
                    .toString());
        }
    }
}