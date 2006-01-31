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

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
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
    protected void processChildElement(SAMLObject parentSAMLObject, SAMLObject childSAMLObject)
            throws UnmarshallingException {
        EntityDescriptor entityDescriptor = (EntityDescriptor) parentSAMLObject;

        if (childSAMLObject instanceof Extensions) {
            entityDescriptor.setExtensions((Extensions) childSAMLObject);
        } else if (childSAMLObject instanceof RoleDescriptor) {
            entityDescriptor.getRoleDescriptors().add((RoleDescriptor) childSAMLObject);
        } else if (childSAMLObject instanceof AffiliationDescriptor) {
            entityDescriptor.setAffiliationDescriptor((AffiliationDescriptor) childSAMLObject);
        } else if (childSAMLObject instanceof Organization) {
            entityDescriptor.setOrganization((Organization) childSAMLObject);
        } else if (childSAMLObject instanceof ContactPerson) {
            entityDescriptor.getContactPersons().add((ContactPerson) childSAMLObject);
        } else if (childSAMLObject instanceof AdditionalMetadataLocation) {
            entityDescriptor.getAdditionalMetadataLocations().add((AdditionalMetadataLocation) childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#addAttribute(org.opensaml.saml2.common.impl.AbstractSAMLElement,
     *      java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlObject, String attributeName, String attributeValue)
            throws UnmarshallingException {
        EntityDescriptor entityDescriptor = (EntityDescriptor) samlObject;

        if (attributeName.equals(EntityDescriptor.ENTITY_ID_ATTRIB_NAME)) {
            entityDescriptor.setEntityID(attributeValue);
        } else if (attributeName.equals(TimeBoundSAMLObject.VALID_UNTIL_ATTRIB_NAME)) {
            entityDescriptor.setValidUntil(new DateTime(attributeValue, ISOChronology.getInstanceUTC()));
        } else if (attributeName.equals(CacheableSAMLObject.CACHE_DURATION_ATTRIB_NAME)) {
            entityDescriptor.setCacheDuration(DatatypeHelper.durationToLong(attributeValue));
        } else {
            super.processAttribute(samlObject, attributeName, attributeValue);
        }
    }
}