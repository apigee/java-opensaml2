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
import org.opensaml.saml1.core.Audience;
import org.opensaml.saml1.core.AudienceRestrictionCondition;
import org.opensaml.xml.IllegalAddException;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread-safe {@link org.opensaml.common.io.Unmarshaller} for
 * {@link org.opensaml.saml1.core.AudienceRestrictionCondition} objects.
 */
public class AudienceRestrictionConditionUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /** Logger */
    private static Logger log = Logger.getLogger(AudienceRestrictionConditionUnmarshaller.class);

    /**
     * Constructor
     */
    public AudienceRestrictionConditionUnmarshaller() {
        super(SAMLConstants.SAML1_NS, AudienceRestrictionCondition.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#processChildElement(org.opensaml.common.SAMLObject,
     *      org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(SAMLObject parentElement, SAMLObject childElement)
            throws UnmarshallingException, UnknownElementException {

        AudienceRestrictionCondition audienceRestrictionCondition;

        audienceRestrictionCondition = (AudienceRestrictionCondition) parentElement;

        if (childElement instanceof Audience) {
            try {
                audienceRestrictionCondition.addAudience((Audience) childElement);
            } catch (IllegalAddException e) {
                log.error("couldnt add elements", e);
                throw new UnmarshallingException(e);
            }
        } else {
            log.error(childElement.getElementQName()
                        + " is not a supported element for AudienceRestrictionCondition objects");
            if (!SAMLConfig.ignoreUnknownElements()) {
                throw new UnknownElementException(childElement.getElementQName()
                        + " is not a supported element for AudienceRestrictionCondition objects");
            }
        }
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#processAttribute(org.opensaml.common.SAMLObject,
     *      java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlElement, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {

        log.error(attributeName
                + " is not a supported attributed for AudienceRestrictionCondition objects");
        if (!SAMLConfig.ignoreUnknownAttributes()) {
            throw new UnknownAttributeException(attributeName
                    + " is not a supported attributed for AudienceRestrictionCondition objects");
        }
    }
}