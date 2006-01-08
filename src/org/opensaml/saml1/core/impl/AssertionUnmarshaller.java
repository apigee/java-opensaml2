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
import org.opensaml.common.SAMLConfig;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Advice;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.Conditions;
import org.opensaml.saml1.core.Statement;
import org.opensaml.xml.IllegalAddException;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * A thread-safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml1.core.Assertion} objects.
 */
public class AssertionUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /**
     * Constructor
     */
    public AssertionUnmarshaller() {
        super(SAMLConstants.SAML1_NS, Assertion.LOCAL_NAME);
    }

    /** Logger */
    private static Logger log = Logger.getLogger(AssertionUnmarshaller.class);


    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processChildElement(org.opensaml.common.SAMLObject,
     *      org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(SAMLObject parentElement, SAMLObject childElement)
            throws UnmarshallingException, UnknownElementException {

        Assertion assertion = (Assertion) parentElement;

        try {
            if (childElement instanceof Conditions) {
                assertion.setConditions((Conditions) childElement);
            } else if (childElement instanceof Advice) {
                assertion.setAdvice((Advice) childElement);
            } else if (childElement instanceof Statement) {
                assertion.addStatement((Statement) childElement);
            } else {
                log.error(childElement.getElementQName() + " is not a supported element for Assertion objects");
                if (!SAMLConfig.ignoreUnknownElements()) {
                    throw new UnknownElementException(childElement.getElementQName()
                            + " is not a supported element for Assertion objects");
                }
            }
        } catch (IllegalAddException e) {
            log.error("Couldn't add " + childElement + " to Assertion", e);
            throw new UnmarshallingException(e);
        }
    }

    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processAttribute(org.opensaml.common.SAMLObject,
     *      java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlElement, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {

        Assertion assertion = (Assertion) samlElement;

        if (Assertion.ISSUER_ATTRIB_NAME.equals(attributeName)) {
            assertion.setIssuer(attributeValue);
        } else if (Assertion.ISSUEINSTANT_ATTRIB_NAME.equals(attributeName)) {
            assertion.setIssueInstant(DatatypeHelper.stringToCalendar(attributeValue, 0));
        } else if (Assertion.MAJORVERSION_ATTRIB_NAME.endsWith(attributeName)) {
            try {
                if (Integer.parseInt(attributeValue) != 1) {
                    log.error("SAML version must be 1");
                    throw new UnmarshallingException("SAML version must be 1");
                }
            } catch (NumberFormatException n) {
                log.error("Error when checking MajorVersion attribute", n);
                throw new UnmarshallingException(n);
            }
        } else if (Assertion.MINORVERSION_ATTRIB_NAME.equals(attributeName)) {
            try {
                assertion.setMinorVersion(Integer.parseInt(attributeValue));
            } catch (NumberFormatException n) {
                log.error("Error when checking MinorVersion attribute", n);
                throw new UnmarshallingException(n);
            }
        } else {
            log.error(attributeName + " is not a supported attributed for Assertion objects");
            if (!SAMLConfig.ignoreUnknownAttributes()) {
                throw new UnknownAttributeException(attributeName
                        + " is not a supported attributed for Assertion objects");
            }
        }
    }
}