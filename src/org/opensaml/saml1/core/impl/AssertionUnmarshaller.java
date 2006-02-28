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

package org.opensaml.saml1.core.impl;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Advice;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.Conditions;
import org.opensaml.saml1.core.Statement;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.UnmarshallingException;
import org.w3c.dom.Attr;

/**
 * A thread-safe Unmarshaller for {@link org.opensaml.saml1.core.Assertion} objects.
 */
public class AssertionUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /** Logger */
    private static Logger log = Logger.getLogger(AssertionUnmarshaller.class);

    /**
     * Constructor
     */
    public AssertionUnmarshaller() {
        super(SAMLConstants.SAML1_NS, Assertion.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.xml.io.AbstractXMLObjectUnmarshaller#processChildElement(org.opensaml.xml.XMLObject,
     *      org.opensaml.xml.XMLObject)
     */
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject)
            throws UnmarshallingException {

        Assertion assertion = (Assertion) parentSAMLObject;

        if (childSAMLObject instanceof Conditions) {
            assertion.setConditions((Conditions) childSAMLObject);
        } else if (childSAMLObject instanceof Advice) {
            assertion.setAdvice((Advice) childSAMLObject);
        } else if (childSAMLObject instanceof Statement) {
            assertion.getStatements().add((Statement) childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }

    /*
     * @see org.opensaml.xml.io.AbstractXMLObjectUnmarshaller#processAttribute(org.opensaml.xml.XMLObject,
     *      org.w3c.dom.Attr)
     */
    protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {

        Assertion assertion = (Assertion) samlObject;

        if (Assertion.ISSUER_ATTRIB_NAME.equals(attribute.getLocalName())) {
            assertion.setIssuer(attribute.getValue());
        } else if (Assertion.ISSUEINSTANT_ATTRIB_NAME.equals(attribute.getLocalName())) {
            assertion.setIssueInstant(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
        } else if (Assertion.MAJORVERSION_ATTRIB_NAME.endsWith(attribute.getLocalName())) {
            try {
                if (Integer.parseInt(attribute.getValue()) != 1) {
                    log.error("SAML version must be 1");
                    throw new UnmarshallingException("SAML version must be 1");
                }
            } catch (NumberFormatException n) {
                log.error("Error when checking MajorVersion attribute", n);
                throw new UnmarshallingException(n);
            }
        } else if (Assertion.MINORVERSION_ATTRIB_NAME.equals(attribute.getLocalName())) {
            try {
                assertion.setMinorVersion(Integer.parseInt(attribute.getValue()));
            } catch (NumberFormatException n) {
                log.error("Error when checking MinorVersion attribute", n);
                throw new UnmarshallingException(n);
            }
        } else {
            super.processAttribute(samlObject, attribute);
        }
    }
}