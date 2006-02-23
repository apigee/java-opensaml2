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
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.SubjectConfirmationData;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread-safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.core.SubjectConfirmationData}
 * objects.
 */
public class SubjectConfirmationDataUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /** Constructor */
    public SubjectConfirmationDataUnmarshaller() {
        super(SAMLConstants.SAML20_NS, SubjectConfirmationData.LOCAL_NAME);
    }

    /**
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processAttribute(org.opensaml.common.SAMLObject,
     *      java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlObject, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {
        SubjectConfirmationData subjectCD = (SubjectConfirmationData) samlObject;

        if (attributeName.equals(SubjectConfirmationData.NOT_BEFORE_ATTRIB_NAME)) {
            subjectCD.setNotBefore(new DateTime(attributeValue, ISOChronology.getInstanceUTC()));
        } else if (attributeName.equals(SubjectConfirmationData.NOT_ON_OR_AFTER_ATTRIB_NAME)) {
            subjectCD.setNotOnOrAfter(new DateTime(attributeValue, ISOChronology.getInstanceUTC()));
        } else if (attributeName.equals(SubjectConfirmationData.RECIPIENT_ATTRIB_NAME)) {
            subjectCD.setRecipient(attributeValue);
        } else if (attributeName.equals(SubjectConfirmationData.IN_RESPONSE_TO_ATTRIB_NAME)) {
            subjectCD.setInResponseTo(attributeValue);
        } else if (attributeName.equals(SubjectConfirmationData.ADDRESS_ATTRIB_NAME)) {
            subjectCD.setAddress(attributeValue);
        } else {
            super.processAttribute(samlObject, attributeName, attributeValue);
        }
    }
}