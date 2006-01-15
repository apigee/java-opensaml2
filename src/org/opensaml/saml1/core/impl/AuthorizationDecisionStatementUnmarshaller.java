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
import org.opensaml.common.SAMLConfig;
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
import org.opensaml.xml.IllegalAddException;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread-safe {@link org.opensaml.common.io.Unmarshaller} for
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
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processChildElement(org.opensaml.common.SAMLObject, org.opensaml.common.SAMLObject)
     */
    @Override
    protected void processChildElement(SAMLObject parentElement, SAMLObject childElement)
            throws UnmarshallingException, UnknownElementException {
        
        AuthorizationDecisionStatement authorizationDecisionStatement;
        authorizationDecisionStatement = (AuthorizationDecisionStatement) parentElement;
        
        try {
            if (childElement instanceof Action) {
                authorizationDecisionStatement.addAction((Action) childElement);
            } else if (childElement instanceof Evidence) {
                authorizationDecisionStatement.setEvidence((Evidence) childElement);
            } else if (childElement instanceof Subject) {
                authorizationDecisionStatement.setSubject((Subject) childElement);
            } else {
                log.error(childElement.getElementQName()
                        + " is not a supported element for AuthorizationDecisionStatement");
                if (!SAMLConfig.ignoreUnknownElements()) {
                    throw new UnknownElementException(childElement.getElementQName()
                            + " is not a supported element for AuthorizationDecisionStatement");
                }
            }
        } catch (IllegalAddException e) {
            log.error("Couldn't add element " + childElement.getElementQName(), e);
            throw new UnmarshallingException(e);
        }
    }

    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processAttribute(org.opensaml.common.SAMLObject, java.lang.String, java.lang.String)
     */
    @Override
    protected void processAttribute(SAMLObject samlElement, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {

        AuthorizationDecisionStatement authorizationDecisionStatement;
        authorizationDecisionStatement = (AuthorizationDecisionStatement) samlElement;
        
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
            log.error(attributeName
                    + " is not a supported attributed for AuthorizationDecisionStatement objects");
            if (!SAMLConfig.ignoreUnknownAttributes()) {
                throw new UnknownAttributeException(attributeName
                        + " is not a supported attributed for AuthorizationDecisionStatement objects");
            }
        }
    }
}
