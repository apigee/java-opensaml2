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

import org.joda.time.format.ISODateTimeFormat;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectMarshaller;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.SubjectConfirmationData;
import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Element;

/**
 * A thread safe {@link org.opensaml.common.io.Marshaller} for {@link org.opensaml.saml2.core.SubjectConfirmationData}
 * objects.
 */
public class SubjectConfirmationDataMarshaller extends AbstractSAMLObjectMarshaller {

    /** Constructor */
    public SubjectConfirmationDataMarshaller() {
        super(SAMLConstants.SAML20_NS, SubjectConfirmationData.LOCAL_NAME);
    }

    /**
     * @see org.opensaml.common.impl.AbstractSAMLObjectMarshaller#marshallAttributes(org.opensaml.common.SAMLObject,
     *      org.w3c.dom.Element)
     */
    protected void marshallAttributes(SAMLObject samlObject, Element domElement) throws MarshallingException {
        SubjectConfirmationData subjectCD = (SubjectConfirmationData) samlObject;

        if (subjectCD.getNotBefore() != null) {
            String notBeforeStr = ISODateTimeFormat.dateTime().print(subjectCD.getNotBefore());
            domElement.setAttributeNS(null, SubjectConfirmationData.NOT_BEFORE_ATTRIB_NAME, notBeforeStr);            
        }

        if (subjectCD.getNotOnOrAfter() != null) {
            String notOnOrAfterStr = ISODateTimeFormat.dateTime().print(subjectCD.getNotOnOrAfter());
            domElement.setAttributeNS(null, SubjectConfirmationData.NOT_ON_OR_AFTER_ATTRIB_NAME, notOnOrAfterStr);
        }

        if (subjectCD.getRecipient() != null) {
            domElement.setAttributeNS(null, SubjectConfirmationData.RECIPIENT_ATTRIB_NAME, subjectCD.getRecipient());
        }

        if (subjectCD.getInResponseTo() != null) {
            domElement.setAttributeNS(null, SubjectConfirmationData.IN_RESPONSE_TO_ATTRIB_NAME, subjectCD
                    .getInResponseTo());
        }

        if (subjectCD.getAddress() != null) {
            domElement.setAttributeNS(null, SubjectConfirmationData.ADDRESS_ATTRIB_NAME, subjectCD.getAddress());
        }
    }
}