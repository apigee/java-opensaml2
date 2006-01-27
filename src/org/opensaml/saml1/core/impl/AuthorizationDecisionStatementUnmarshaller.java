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

import org.apache.log4j.Logger;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Action;
import org.opensaml.saml1.core.AuthorizationDecisionStatement;
import org.opensaml.saml1.core.DecisionType;
import org.opensaml.saml1.core.Evidence;
import org.opensaml.saml1.core.Subject;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread-safe {@link org.opensaml.xml.io.Unmarshaller} for
 * {@link org.opensaml.saml1.core.impl.AuthorizationDecisionStatementImpl} objects.
 */
public class AuthorizationDecisionStatementUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /** Logger */
    private static Logger log = Logger.getLogger(AuthorizationDecisionStatementUnmarshaller.class);

    /**
     * Constructor
     */
    public AuthorizationDecisionStatementUnmarshaller() {
        super(SAMLConstants.SAML1_NS, AuthorizationDecisionStatement.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processChildElement(org.opensaml.common.SAMLObject,
     *      org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(SAMLObject parentSAMLObject, SAMLObject childSAMLObject)
            throws UnmarshallingException, UnknownElementException {

        AuthorizationDecisionStatement authorizationDecisionStatement;
        authorizationDecisionStatement = (AuthorizationDecisionStatement) parentSAMLObject;

        if (childSAMLObject instanceof Action) {
            authorizationDecisionStatement.getActions().add((Action) childSAMLObject);
        } else if (childSAMLObject instanceof Evidence) {
            authorizationDecisionStatement.setEvidence((Evidence) childSAMLObject);
        } else if (childSAMLObject instanceof Subject) {
            authorizationDecisionStatement.setSubject((Subject) childSAMLObject);
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

        AuthorizationDecisionStatement authorizationDecisionStatement;
        authorizationDecisionStatement = (AuthorizationDecisionStatement) samlObject;

        if (AuthorizationDecisionStatement.DECISION_ATTRIB_NAME.equals(attributeName)) {
            DecisionType decision;

            try {
                decision = Enum.valueOf(DecisionType.class, attributeValue);
            } catch (IllegalArgumentException e) {
                log.error("Unknown type for DecisionType " + attributeValue);
                throw new UnmarshallingException("Unknown type for DecisionType " + attributeValue, e);
            }
            authorizationDecisionStatement.setDecision(decision);

        } else if (AuthorizationDecisionStatement.RESOURCE_ATTRIB_NAME.equals(attributeName)) {
            authorizationDecisionStatement.setResource(attributeValue);
        } else {
            super.processAttribute(samlObject, attributeName, attributeValue);
        }
    }
}