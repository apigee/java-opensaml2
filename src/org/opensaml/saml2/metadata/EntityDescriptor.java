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

package org.opensaml.saml2.metadata;

import java.util.Set;

import javax.xml.namespace.QName;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SignableObject;
import org.opensaml.common.util.xml.XMLConstants;
import org.opensaml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml2.common.ExtensionsExtensibleSAMLObject;
import org.opensaml.saml2.common.TimeBoundSAMLObject;

/**
 * SAML 2.0 Metadata EntityDescriptor
 */
public interface EntityDescriptor extends SAMLObject, TimeBoundSAMLObject, CacheableSAMLObject, SignableObject, ExtensionsExtensibleSAMLObject{
	
	/** Element name, no namespace */
	public final static String LOCAL_NAME = "EntityDescriptor";
	
	/** QName for this element */
	public final static QName QNAME = new QName(XMLConstants.SAML20MD_NS, LOCAL_NAME, XMLConstants.SAML20MD_PREFIX);

    /**
     * Gets the entity ID for this entity descriptor.
     * 
     * @return the entity ID for this entity descriptor
     */
    public String getEntityID();
    
    /**
     * Sets the entity ID for this entity descriptor.
     * 
     * @param id the entity ID for this entity descriptor
     */
    public void setEntityID(String id);
    
    /**
     * Gets all the role descriptors for this entity descriptor.
     * 
     * @return the role descriptors for this entity descriptor
     */
	public Set<RoleDescriptor> getRoleDescriptors();

    /**
     * Gets all the role descriptors of a certain type.
     * 
     * @param type the xsi:type of role descriptor
     * 
     * @return the role descriptors of a certain type
     */
	public Set<RoleDescriptor> getRoleDescriptors(QName type);
    
    /**
     * Gets the role descriptors of a certain type that support the given protocol.
     * 
     * @param type the xsi:type of role descriptor
     * @param protocol the protocol that must be supported
     * 
     * @return the role descriptor or null
     */
    public RoleDescriptor getRoleDescriptors(QName type, String protocol);
    
    /**
     * Adds a role descriptor
     * 
     * @param descriptor the descriptor
     * 
     * @throws IllegalAddException thrown if the given descriptor is owned by another EntityDescriptor, if 
     * its supported protocols overlap with an existing descriptor, or if this entity descriptor has an 
     * AffiliationDescriptor
     */
    public void addRoleDescriptor(RoleDescriptor descriptor) throws IllegalAddException;
    
    /**
     * Removes a role descriptor.  If the given role descriptor is not 
     * currently a member of this entity descriptor this operation simply returns.
     * 
     * @param descriptor the role descriptor
     */
    public void removeRoleDescriptor(RoleDescriptor descriptor);
    
    /**
     * Removes a set of role descriptors.  If any of the given role descriptors are not 
     * currently a member of this entity descriptor they are simply skipped.
     * 
     * @param descriptors the descriptors
     */
    public void removeRoleDescriptors(Set<RoleDescriptor> descriptors);
    
    /**
     * Removes all the role descriptors.
     */
    public void removeAllRoleDescriptors();

    /**
     * Gets the affiliation descriptor for this entity.
     * 
     * @return the affiliation descriptor for this entity
     */
	public AffiliationDescriptor getAffiliationDescriptor();
    
    /**
     * Sets the affiliation descriptor for this entity.
     * 
     * @param descriptor the affiliation descriptor for this entity
     * 
     * @throws IllegalAddException thrown if the descriptor is owned by another entity or if this entity 
     * already has one or more role descriptors associated with it
     */
    public void setAffiliationDescriptor(AffiliationDescriptor descriptor) throws IllegalAddException;

    /**
     * Gets the organization for this entity.
     * 
     * @return the organization for this entity
     */
	public Organization getOrganization();
    
    /**
     * Sets the organization for this entity.
     * 
     * @param organization the organization for this entity
     * 
     * @throws IllegalAddException thrown if this organization belongs to another entity
     */
    public void setOrganization(Organization organization)  throws IllegalAddException;

    /**
     * Get the contact people for this entity.
     * 
     * @return the contact people for this entity
     */
	public Set<ContactPerson> getContactPersons();
    
    /**
     * Adds a contact person to this entity.
     * 
     * @param person the contact person
     * 
     * @throws IllegalAddException thrown if given contact person belongs to another entity
     */
    public void addContactPerson(ContactPerson person)  throws IllegalAddException;
    
    /**
     * Removes a contact person from this entity, if they are associated with it.
     * 
     * @param person the person to remove
     */
    public void removeContactPerson(ContactPerson person);
    
    /**
     * Removes a set of contact people from this entity, if a contact person is not 
     * associated with this entity it is simply skipped.
     * 
     * @param persons the contact people
     */
    public void removeContactPersons(Set<ContactPerson> persons);
    
    /**
     * Removes all the contact people from this entity.
     */
    public void removeAllContactPersons();

    /**
     * Gets the additional metadata locations for this entity.
     * 
     * @return the additional metadata locations for this entity
     */
	public Set<AdditionalMetadataLocation> getAdditionalMetadataLocations();
    
    /**
     * Adds an additional metadata locations for this entity.
     * 
     * @param location the location to add
     * 
     * @throws IllegalAddException thrown if this location is owned by another entity
     */
    public void addAdditionalMetadataLocation(AdditionalMetadataLocation location) throws IllegalAddException;
    
    /**
     * Removes an additional metadata location from this entity if it exist.
     * 
     * @param location the location to remove
     */
    public void removeAdditionalMetadataLocation(AdditionalMetadataLocation location);
    
    /**
     * Removes the additional metadata locations from this entity if they exist.
     * 
     * @param locations the location to remove
     */
    public void removeAdditionalMetadataLocations(Set<AdditionalMetadataLocation> locations);
    
    /**
     * Removes all the additional metadata locations from this entity.
     */
    public void removeAllAdditionalMetadataLocation();
}
