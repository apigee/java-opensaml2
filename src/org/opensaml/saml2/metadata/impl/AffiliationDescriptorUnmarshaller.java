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

package org.opensaml.saml2.metadata.impl;

import org.opensaml.common.SAMLConfig;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml2.common.TimeBoundSAMLObject;
import org.opensaml.saml2.core.Extensions;
import org.opensaml.saml2.metadata.AffiliateMember;
import org.opensaml.saml2.metadata.AffiliationDescriptor;
import org.opensaml.saml2.metadata.KeyDescriptor;
import org.opensaml.xml.IllegalAddException;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * A thread safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.metadata.AffiliationDescriptor}s.
 */
public class AffiliationDescriptorUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /**
     * Constructor
     */
    public AffiliationDescriptorUnmarshaller() {
        super(SAMLConstants.SAML20MD_NS, AffiliationDescriptor.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#processChildElement(org.opensaml.common.SAMLObject,
     *      org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(SAMLObject parentElement, SAMLObject childElement)
            throws UnmarshallingException, UnknownElementException {
        AffiliationDescriptor descriptor = (AffiliationDescriptor) parentElement;

        try {
            if (childElement instanceof Extensions) {
                descriptor.setExtensions((Extensions) childElement);
            } else if (childElement instanceof AffiliateMember) {
                descriptor.addMember((AffiliateMember) childElement);
            } else if (childElement instanceof KeyDescriptor) {
                descriptor.addKeyDescriptor((KeyDescriptor) childElement);
            } else {
                if (!SAMLConfig.ignoreUnknownElements()) {
                    throw new UnknownElementException(childElement.getElementQName()
                            + " is not a supported element for EntityDescriptor objects");
                }
            }
        } catch (IllegalAddException e) {
            // This should never happen, but just in case
            throw new UnmarshallingException(e);
        }
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#processAttribute(org.opensaml.common.SAMLObject,
     *      java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlElement, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {
        AffiliationDescriptor descriptor = (AffiliationDescriptor) samlElement;

        if (attributeName.equals(AffiliationDescriptor.OWNER_ID_ATTRIB_NAME)) {
            descriptor.setOwnerID(attributeValue);
        } else if (attributeName.equals(TimeBoundSAMLObject.VALID_UNTIL_ATTRIB_NAME)) {
            descriptor.setValidUntil(DatatypeHelper.stringToCalendar(attributeValue, 0));
        } else if (attributeName.equals(CacheableSAMLObject.CACHE_DURATION_ATTRIB_NAME)) {
            descriptor.setCacheDuration(DatatypeHelper.durationToLong(attributeValue));
        } else {
            if (!SAMLConfig.ignoreUnknownAttributes()) {
                throw new UnknownAttributeException(attributeName
                        + " is not a supported attributed for EntityDescriptor objects");
            }
        }
    }
}