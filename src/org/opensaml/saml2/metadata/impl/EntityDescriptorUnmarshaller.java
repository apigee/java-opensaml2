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

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLConfig;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.io.UnknownAttributeException;
import org.opensaml.common.io.UnknownElementException;
import org.opensaml.common.io.Unmarshaller;
import org.opensaml.common.io.UnmarshallingException;
import org.opensaml.common.io.impl.AbstractUnmarshaller;
import org.opensaml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml2.common.TimeBoundSAMLObject;
import org.opensaml.saml2.common.impl.TimeBoundSAMLObjectHelper;
import org.opensaml.saml2.metadata.AdditionalMetadataLocation;
import org.opensaml.saml2.metadata.AffiliationDescriptor;
import org.opensaml.saml2.metadata.ContactPerson;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.Extensions;
import org.opensaml.saml2.metadata.Organization;
import org.opensaml.saml2.metadata.RoleDescriptor;

/**
 * A thread safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.metadata.EntityDescriptor}
 * objects. <strong>NOTE</strong> this Unmarshaller will only work with objects that are derived from
 * {@link org.opensaml.saml2.common.impl.AbstractSAMLObject}.
 */
public class EntityDescriptorUnmarshaller extends AbstractUnmarshaller implements Unmarshaller {
    /**
     * Constructor
     */
    public EntityDescriptorUnmarshaller() {
        super(EntityDescriptor.QNAME);
    }
    
    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#addChildElement(org.opensaml.saml2.common.impl.AbstractSAMLElement, org.opensaml.saml2.common.impl.AbstractSAMLElement)
     */
    protected void processChildElement(SAMLObject parentElement, SAMLObject childElement)
            throws UnmarshallingException {
        EntityDescriptor entityDescriptor = (EntityDescriptor)parentElement;
        
        try {
            if(childElement instanceof Extensions) {
                entityDescriptor.setExtensions((Extensions) childElement);
            }else if(childElement instanceof RoleDescriptor) {
                entityDescriptor.addRoleDescriptor((RoleDescriptor)childElement);
            }else if(childElement instanceof AffiliationDescriptor) {
                entityDescriptor.setAffiliationDescriptor((AffiliationDescriptor) childElement);
            }else if(childElement instanceof Organization) {
                entityDescriptor.setOrganization((Organization) childElement);
            }else if(childElement instanceof ContactPerson) {
                entityDescriptor.addContactPerson((ContactPerson) childElement);
            }else if(childElement instanceof AdditionalMetadataLocation) {
                entityDescriptor.addAdditionalMetadataLocation((AdditionalMetadataLocation) childElement);
            }else {
                if(!SAMLConfig.ignoreUnknownElements()){
                    throw new UnknownElementException(childElement.getElementQName() + " is not a supported element for EntityDescriptor objects");
                }
            }
        }catch(IllegalAddException e){
            //This should never happen, but just in case
            throw new UnmarshallingException(e);
        }
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#addAttribute(org.opensaml.saml2.common.impl.AbstractSAMLElement, java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlElement, String attributeName, String attributeValue)
            throws UnmarshallingException {
        EntityDescriptor entityDescriptor = (EntityDescriptor)samlElement;
        
        if(attributeName.equals(EntityDescriptor.ENTITY_ID_ATTRIB_NAME)) {
            entityDescriptor.setEntityID(attributeValue);
        }else if(attributeName.equals(TimeBoundSAMLObject.VALID_UNTIL_ATTRIB_NAME)) {
            entityDescriptor.setValidUntil(TimeBoundSAMLObjectHelper.stringToCalendar(attributeValue));
        }else if(attributeName.equals(CacheableSAMLObject.CACHE_DURATION_ATTRIB_NAME)) {
            entityDescriptor.setCacheDuration(new Long(Long.parseLong(attributeValue)));
        }else {
            if(!SAMLConfig.ignoreUnknownAttributes()){
                throw new UnknownAttributeException(attributeName + " is not a supported attributed for EntityDescriptor objects");
            }
        }
    }

}
