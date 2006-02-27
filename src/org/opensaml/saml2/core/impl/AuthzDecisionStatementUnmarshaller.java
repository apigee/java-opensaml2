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

import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Action;
import org.opensaml.saml2.core.AuthzDecisionStatement;
import org.opensaml.saml2.core.DecisionType;
import org.opensaml.saml2.core.Evidence;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.UnmarshallingException;
import org.w3c.dom.Attr;

/**
 * A thread-safe Unmarshaller for {@link org.opensaml.saml2.core.AuthzDecisionStatement}.
 */
public class AuthzDecisionStatementUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /** Constructor */
    public AuthzDecisionStatementUnmarshaller() {
        super(SAMLConstants.SAML20_NS, AuthzDecisionStatement.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.xml.io.AbstractXMLObjectUnmarshaller#processChildElement(org.opensaml.xml.XMLObject,
     *      org.opensaml.xml.XMLObject)
     */
    protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
        AuthzDecisionStatement authzDS = (AuthzDecisionStatement) parentObject;

        if (childObject instanceof Action) {
            authzDS.getActions().add((Action) childObject);
        } else if (childObject instanceof Evidence) {
            authzDS.setEvidence((Evidence) childObject);
        } else {
            super.processChildElement(parentObject, childObject);
        }
    }

    /*
     * @see org.opensaml.xml.io.AbstractXMLObjectUnmarshaller#processAttribute(org.opensaml.xml.XMLObject,
     *      org.w3c.dom.Attr)
     */
    protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
        AuthzDecisionStatement authzDS = (AuthzDecisionStatement) samlObject;

        if (attribute.getLocalName().equals(AuthzDecisionStatement.RESOURCE_ATTRIB_NAME)) {
            authzDS.setResource(attribute.getValue());
        } else if (attribute.getLocalName().equals(AuthzDecisionStatement.DECISION_ATTRIB_NAME)) {
            DecisionType decision = new DecisionType(attribute.getValue());
            authzDS.setDecision(decision);
        } else {
            super.processAttribute(samlObject, attribute);
        }
    }
}