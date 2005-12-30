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
import org.opensaml.saml2.metadata.AdditionalMetadataLocation;
import org.opensaml.saml2.metadata.AffiliationDescriptor;
import org.opensaml.saml2.metadata.ContactPerson;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.Organization;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.xml.IllegalAddException;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * A thread safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.metadata.EntityDescriptor}s.
 */
public class EntityDescriptorUnmarshaller extends AbstractSAMLObjectUnmarshaller {
    /**
     * Constructor
     */
    public EntityDescriptorUnmarshaller() {
        super(SAMLConstants.SAML20MD_NS, EntityDescriptor.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#addChildElement(org.opensaml.saml2.common.impl.AbstractSAMLElement,
     *      org.opensaml.saml2.common.impl.AbstractSAMLElement)
     */
    protected void processChildElement(SAMLObject parentElement, SAMLObject childElement) throws UnmarshallingException {
        EntityDescriptor entityDescriptor = (EntityDescriptor) parentElement;

        try {
            if (childElement instanceof Extensions) {
                entityDescriptor.setExtensions((Extensions) childElement);
            } else if (childElement instanceof RoleDescriptor) {
                entityDescriptor.addRoleDescriptor((RoleDescriptor) childElement);
            } else if (childElement instanceof AffiliationDescriptor) {
                entityDescriptor.setAffiliationDescriptor((AffiliationDescriptor) childElement);
            } else if (childElement instanceof Organization) {
                entityDescriptor.setOrganization((Organization) childElement);
            } else if (childElement instanceof ContactPerson) {
                entityDescriptor.addContactPerson((ContactPerson) childElement);
            } else if (childElement instanceof AdditionalMetadataLocation) {
                entityDescriptor.addAdditionalMetadataLocation((AdditionalMetadataLocation) childElement);
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
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#addAttribute(org.opensaml.saml2.common.impl.AbstractSAMLElement,
     *      java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlElement, String attributeName, String attributeValue)
            throws UnmarshallingException {
        EntityDescriptor entityDescriptor = (EntityDescriptor) samlElement;

        if (attributeName.equals(EntityDescriptor.ENTITY_ID_ATTRIB_NAME)) {
            entityDescriptor.setEntityID(attributeValue);
        } else if (attributeName.equals(TimeBoundSAMLObject.VALID_UNTIL_ATTRIB_NAME)) {
            entityDescriptor.setValidUntil(DatatypeHelper.stringToCalendar(attributeValue, 0));
        } else if (attributeName.equals(CacheableSAMLObject.CACHE_DURATION_ATTRIB_NAME)) {
            entityDescriptor.setCacheDuration(DatatypeHelper.durationToLong(attributeValue));
        } else {
            if (!SAMLConfig.ignoreUnknownAttributes()) {
                throw new UnknownAttributeException(attributeName
                        + " is not a supported attributed for EntityDescriptor objects");
            }
        }
    }

}