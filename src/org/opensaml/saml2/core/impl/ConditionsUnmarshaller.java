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
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.AudienceRestriction;
import org.opensaml.saml2.core.Condition;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.OneTimeUse;
import org.opensaml.saml2.core.ProxyRestriction;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread-safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.core.Conditions} objects.
 */
public class ConditionsUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /** Constructor */
    public ConditionsUnmarshaller() {
        super(SAMLConstants.SAML20_NS, Conditions.LOCAL_NAME);
    }

    /**
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processChildElement(org.opensaml.common.SAMLObject,
     *      org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(SAMLObject parentObject, SAMLObject childObject) throws UnmarshallingException,
            UnknownElementException {
        Conditions conditions = (Conditions) parentObject;

        if (childObject instanceof Condition) {
            conditions.getConditions().add((Condition) childObject);
        } else if (childObject instanceof AudienceRestriction) {
            conditions.getAudienceRestrictions().add((AudienceRestriction) childObject);
        } else if (childObject instanceof OneTimeUse) {
            conditions.setOneTimeUse((OneTimeUse) childObject);
        } else if (childObject instanceof ProxyRestriction) {
            conditions.getProxyRestrictions().add((ProxyRestriction) childObject);
        } else {
            super.processChildElement(parentObject, childObject);
        }
    }

    /**
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processAttribute(org.opensaml.common.SAMLObject,
     *      java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlObject, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {
        Conditions conditions = (Conditions) samlObject;

        if (attributeName.equals(Conditions.NOT_BEFORE_ATTRIB_NAME)) {
            conditions.setNotBefore(new DateTime(attributeValue, ISOChronology.getInstanceUTC()));
        } else if (attributeName.equals(Conditions.NOT_ON_OR_AFTER_ATTRIB_NAME)) {
            conditions.setNotOnOrAfter(new DateTime(attributeValue, ISOChronology.getInstanceUTC()));
        } else {
            super.processAttribute(samlObject, attributeName, attributeValue);
        }
    }
}