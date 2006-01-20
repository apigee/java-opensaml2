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

import java.util.List;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml2.common.TimeBoundSAMLObject;
import org.opensaml.saml2.core.Extensions;
import org.opensaml.xml.IllegalAddException;
import org.opensaml.xml.SignableXMLObject;

/**
 * SAML 2.0 Metadata EntityDescriptor
 */
public interface EntityDescriptor extends SAMLObject, TimeBoundSAMLObject, CacheableSAMLObject, SignableXMLObject{
	
	/** Element name, no namespace */
	public final static String LOCAL_NAME = "EntityDescriptor";
    
    /** "Name" attribute name */
    public final static String ENTITY_ID_ATTRIB_NAME = "entityID";

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
     * Gets the Extensions child of this object.
     * 
     * @return the Extensions child of this object
     */
    public Extensions getExtensions();
    
    /**
     * Sets the Extensions child of this object.
     * 
     * @param extensions the Extensions child of this object
     * 
     * @throws IllegalAddException thrown if the given extensions Object is already a child of another SAMLObject 
     */
    public void setExtensions(Extensions extensions) throws IllegalAddException;
    
    /**
     * Gets all the role descriptors for this entity descriptor.
     * 
     * @return the role descriptors for this entity descriptor
     */
	public List<RoleDescriptor> getRoleDescriptors();

    /**
     * Gets all the role descriptors of a certain type.
     * 
     * @param type the xsi:type of role descriptor
     * 
     * @return the role descriptors of a certain type
     */
	public List<RoleDescriptor> getRoleDescriptors(QName type);
    
    /**
     * Gets all the {@link IDPSSODescriptor}s role descriptor for this entity.
     * 
     * @return the {@link IDPSSODescriptor}s role descriptor for this entity
     */
    public List<RoleDescriptor> getIDPSSODescriptor();
    
    /**
     * Gets all the {@link SPSSODescriptor}s role descriptor for this entity.
     * 
     * @return the {@link SPSSODescriptor}s role descriptor for this entity
     */
    public List<RoleDescriptor> getSPSSODescriptor();
    
    /**
     * Gets all the {@link AuthnAuthorityDescriptor}s role descriptor for this entity.
     * 
     * @return the {@link AuthnAuthorityDescriptor}s role descriptor for this entity
     */
    public List<RoleDescriptor> getAuthnAuthorityDescriptor();
    
    /**
     * Gets all the {@link AttributeAuthorityDescriptor}s role descriptor for this entity.
     * 
     * @return the {@link AttributeAuthorityDescriptor}s role descriptor for this entity
     */
    public List<RoleDescriptor> getAttributeAuthorityDescriptor();
    
    /**
     * Gets all the {@link PDPDescriptor}s role descriptor for this entity.
     * 
     * @return the {@link PDPDescriptor}s role descriptor for this entity
     */
    public List<RoleDescriptor> getPDPDescriptor();
    
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
	public List<ContactPerson> getContactPersons();

    /**
     * Gets the additional metadata locations for this entity.
     * 
     * @return the additional metadata locations for this entity
     */
	public List<AdditionalMetadataLocation> getAdditionalMetadataLocations();
}