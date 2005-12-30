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

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectMarshaller;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.util.DatatypeHelper;
import org.w3c.dom.Element;

/**
 * A thread safe {@link org.opensaml.common.io.Marshaller} for {@link org.opensaml.saml1.core.Assertion} objects.
 */
public class AssertionMarshaller extends AbstractSAMLObjectMarshaller {

    /**
     * Constructor
     */
    public AssertionMarshaller() {
        super(SAMLConstants.SAML1_NS, Assertion.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractMarshaller#marshallAttributes(org.opensaml.common.SAMLObject,
     *      org.w3c.dom.Element)
     */
    @Override
    protected void marshallAttributes(SAMLObject samlElement, Element domElement) throws MarshallingException {

        Assertion assertion = (Assertion) samlElement;

        if (assertion.getIssuer() != null) {

            domElement.setAttribute(Assertion.ISSUER_ATTRIB_NAME, assertion.getIssuer());
        }

        if (assertion.getIssueInstant() != null) {

            String date = DatatypeHelper.calendarToString(assertion.getIssueInstant(), 0);

            domElement.setAttribute(Assertion.ISSUEINSTANT_ATTRIB_NAME, date);
        }

        if (assertion.getMinorVersion() != 0) {
            String minorVersion = Integer.toString(assertion.getMinorVersion());

            domElement.setAttribute(Assertion.MINORVERSION_ATTRIB_NAME, minorVersion);
            domElement.setAttribute(Assertion.MAJORVERSION_ATTRIB_NAME, "1");
        }
    }
}
