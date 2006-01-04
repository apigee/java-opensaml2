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
import org.opensaml.saml1.core.Condition;
import org.opensaml.saml1.core.Conditions;
import org.opensaml.xml.IllegalAddException;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * 
 */
public class ConditionsUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /** Logger */
    private static Logger log = Logger.getLogger(ConditionsUnmarshaller.class);
    
   /**
     * Constructor
     */
    public ConditionsUnmarshaller() {
        super(SAMLConstants.SAML1_NS, Conditions.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#processChildElement(org.opensaml.common.SAMLObject,
     *      org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(SAMLObject parentElement, SAMLObject childElement)
            throws UnmarshallingException, UnknownElementException {

        Conditions conditions = (Conditions) parentElement;

        try {

            if (childElement instanceof Condition) {
                conditions.addCondition((Condition) childElement);
            } else {
                log.error(childElement.getElementQName()
                        + " is not a supported element for Conditions objects");
                if (!SAMLConfig.ignoreUnknownElements()) {
                    throw new UnknownElementException(childElement.getElementQName()
                            + " is not a supported element for Conditions objects");
                }
            }
        } catch (IllegalAddException e) {
            throw new UnmarshallingException(e);
        }
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#processAttribute(org.opensaml.common.SAMLObject,
     *      java.lang.String, java.lang.String)
     */

    protected void processAttribute(SAMLObject samlElement, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {

        Conditions conditions = (Conditions) samlElement;
        
        if (Conditions.NOTBEFORE_ATTRIB_NAME.equals(attributeName)) {
            conditions.setNotBefore(DatatypeHelper.stringToCalendar(attributeValue, 0));
        } else if (Conditions.NOTONORAFTER_ATTRIB_NAME.equals(attributeName)) {
            conditions.setNotOnOrAfter(DatatypeHelper.stringToCalendar(attributeValue, 0));
        } else {
            log.error(attributeName
                    + " is not a supported attributed for Conditions objects");
            if (!SAMLConfig.ignoreUnknownAttributes()) {
                throw new UnknownAttributeException(attributeName
                        + " is not a supported attributed for Conditions objects");
            }
        }
    }
}