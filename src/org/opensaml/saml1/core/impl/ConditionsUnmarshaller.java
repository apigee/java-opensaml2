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

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLConfig;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.io.UnknownAttributeException;
import org.opensaml.common.io.UnknownElementException;
import org.opensaml.common.io.Unmarshaller;
import org.opensaml.common.io.UnmarshallingException;
import org.opensaml.common.io.impl.AbstractUnmarshaller;
import org.opensaml.saml1.core.AudienceRestrictionCondition;
import org.opensaml.saml1.core.Condition;
import org.opensaml.saml1.core.Conditions;
import org.opensaml.saml1.core.DoNotCacheCondition;
import org.opensaml.saml2.common.impl.TimeBoundSAMLObjectHelper;

/**
 * 
 */
public class ConditionsUnmarshaller extends AbstractUnmarshaller implements Unmarshaller {

    /**
     * Constructor
     */
    public ConditionsUnmarshaller() {
        super(Conditions.QNAME);
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#processChildElement(org.opensaml.common.SAMLObject,
     *      org.opensaml.common.SAMLObject)
     */
    @Override
    protected void processChildElement(SAMLObject parentElement, SAMLObject childElement)
            throws UnmarshallingException, UnknownElementException {

        Conditions conditions = (Conditions) parentElement;

        try {

            if (childElement instanceof AudienceRestrictionCondition) {

                conditions.addAudienceRestrictionCondition((AudienceRestrictionCondition) childElement);

            } else if (childElement instanceof DoNotCacheCondition) {

                conditions.addDoNotCacheCondition((DoNotCacheCondition) childElement);
                
            } else if (childElement instanceof Condition) {
                
                conditions.addCondition((Condition)childElement);
            } else if (!SAMLConfig.ignoreUnknownElements()) {
                    throw new UnknownElementException(childElement.getElementQName()
                            + " is not a supported element for Response objects");
            }
        } catch (IllegalAddException e) {

            throw new UnmarshallingException(e);
        }
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#processAttribute(org.opensaml.common.SAMLObject,
     *      java.lang.String, java.lang.String)
     */
    @Override
    protected void processAttribute(SAMLObject samlElement, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {

        Conditions conditions = (Conditions) samlElement;
        
        if (Conditions.NOTBEFORE_ATTRIB_NAME.equals(attributeName)) {

            conditions.setNotBefore(TimeBoundSAMLObjectHelper.stringToCalendar(attributeValue));
            
        } else if (Conditions.NOTONORAFTER_ATTRIB_NAME.equals(attributeName)) {
           
            conditions.setNotOnOrAfter(TimeBoundSAMLObjectHelper.stringToCalendar(attributeValue));
            
        } else if (!SAMLConfig.ignoreUnknownAttributes()) {
                throw new UnknownAttributeException(attributeName
                        + " is not a supported attributed for Response objects");
        }
    }
}
