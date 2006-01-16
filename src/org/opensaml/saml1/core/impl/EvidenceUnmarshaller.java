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
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.AssertionIDReference;
import org.opensaml.saml1.core.Evidence;
import org.opensaml.xml.IllegalAddException;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread safe {@link org.opensaml.xml.io.Unmarshaller} for {@link org.opensaml.saml1.core.Evidence} objects.
 */
public class EvidenceUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /** Logger */
    private static Logger log = Logger.getLogger(EvidenceUnmarshaller.class);

    /**
     * Constructor
     */
    public EvidenceUnmarshaller() {
        super(SAMLConstants.SAML1_NS, Evidence.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processChildElement(org.opensaml.common.SAMLObject, org.opensaml.common.SAMLObject)
     */
    @Override
    protected void processChildElement(SAMLObject parentElement, SAMLObject childElement)
            throws UnmarshallingException, UnknownElementException {

        Evidence evidence = (Evidence) parentElement;
        try {
            if (childElement instanceof AssertionIDReference) {
                evidence.setAssertionIDReference((AssertionIDReference) childElement);
            } else if (childElement instanceof Assertion) {
                evidence.setAssertion((Assertion) childElement);
            } else {
                log.error(childElement.getElementQName() + " is not a supported element for Evidence objects");
                if (!SAMLConfig.ignoreUnknownElements()) {
                    throw new UnknownElementException(childElement.getElementQName()
                            + " is not a supported element for Evidence objects");
                }
            }
        } catch (IllegalAddException e) {
            log.error("Could not add " + childElement.getElementQName() + " to Evidence");
            throw new UnmarshallingException("Could not add " + childElement.getElementQName() + " to Evidence", e);
        }
    }

    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processAttribute(org.opensaml.common.SAMLObject, java.lang.String, java.lang.String)
     */
    @Override
    protected void processAttribute(SAMLObject samlElement, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {

        // No attributes

        log.error(attributeName + " is not a supported attribute for Evidence objects");
        if (!SAMLConfig.ignoreUnknownAttributes()) {
            throw new UnknownAttributeException(attributeName
                    + " is not a supported attributed for Evidence objects");
        }
    }
}
