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

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Condition;
import org.opensaml.saml1.core.Conditions;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * 
 */
public class ConditionsUnmarshaller extends AbstractSAMLObjectUnmarshaller {

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
    protected void processChildElement(SAMLObject parentSAMLObject, SAMLObject childSAMLObject)
            throws UnmarshallingException, UnknownElementException {
        Conditions conditions = (Conditions) parentSAMLObject;
        
        if (childSAMLObject instanceof Condition) {
            conditions.getConditions().add((Condition) childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#processAttribute(org.opensaml.common.SAMLObject,
     *      java.lang.String, java.lang.String)
     */

    protected void processAttribute(SAMLObject samlObject, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {

        Conditions conditions = (Conditions) samlObject;

        if (Conditions.NOTBEFORE_ATTRIB_NAME.equals(attributeName)) {
            conditions.setNotBefore(new DateTime(attributeValue, ISOChronology.getInstanceUTC()));
        } else if (Conditions.NOTONORAFTER_ATTRIB_NAME.equals(attributeName)) {
            conditions.setNotOnOrAfter(new DateTime(attributeValue, ISOChronology.getInstanceUTC()));
        } else {
            processAttribute(samlObject, attributeName, attributeValue);
        }
    }
}