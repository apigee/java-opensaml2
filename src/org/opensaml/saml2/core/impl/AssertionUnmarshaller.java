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
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Advice;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.AuthzDecisionStatement;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Statement;
import org.opensaml.saml2.core.Subject;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.signature.Signature;
import org.w3c.dom.Attr;

/**
 * A thread-safe Unmarshaller for {@link org.opensaml.saml2.core.Assertion}.
 */
public class AssertionUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /** Constructor */
    public AssertionUnmarshaller() {
        super(SAMLConstants.SAML20_NS, Assertion.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.xml.io.AbstractXMLObjectUnmarshaller#processChildElement(org.opensaml.xml.XMLObject,
     *      org.opensaml.xml.XMLObject)
     */
    protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
        Assertion assertion = (Assertion) parentObject;

        if (childObject instanceof Issuer) {
            assertion.setIssuer((Issuer) childObject);
        } else if (childObject instanceof Signature) {
            assertion.setSignature((Signature) childObject);
        } else if (childObject instanceof Subject) {
            assertion.setSubject((Subject) childObject);
        } else if (childObject instanceof Conditions) {
            assertion.setConditions((Conditions) childObject);
        } else if (childObject instanceof Advice) {
            assertion.setAdvice((Advice) childObject);
        } else if (childObject instanceof Statement) {
            assertion.getStatements().add((Statement) childObject);
        } else if (childObject instanceof AuthnStatement) {
            assertion.getAuthnStatements().add((AuthnStatement) childObject);
        } else if (childObject instanceof AuthzDecisionStatement) {
            assertion.getAuthzDecisionStatements().add((AuthzDecisionStatement) childObject);
        } else if (childObject instanceof AttributeStatement) {
            assertion.getAttributeStatement().add((AttributeStatement) childObject);
        } else {
            super.processChildElement(parentObject, childObject);
        }
    }

    /*
     * @see org.opensaml.xml.io.AbstractXMLObjectUnmarshaller#processAttribute(org.opensaml.xml.XMLObject,
     *      org.w3c.dom.Attr)
     */
    protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
        Assertion assertion = (Assertion) samlObject;

        if (attribute.getLocalName().equals(Assertion.ISSUE_INSTANT_ATTRIB_NAME)) {
            assertion.setIssueInstant(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
        } else if (attribute.getLocalName().equals(Assertion.ID_ATTRIB_NAME)) {
            assertion.setID(attribute.getValue());
        } else {
            super.processAttribute(samlObject, attribute);
        }
    }
}