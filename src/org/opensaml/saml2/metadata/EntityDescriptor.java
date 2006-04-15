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
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.common.CacheableSAMLObject;
import org.opensaml.saml2.common.Extensions;
import org.opensaml.saml2.common.TimeBoundSAMLObject;
import org.opensaml.xml.AttributeExtensibleXMLObject;
import org.opensaml.xml.signature.SignableXMLObject;

/**
 * SAML 2.0 Metadata EntityDescriptor
 */
public interface EntityDescriptor extends SAMLObject, TimeBoundSAMLObject, CacheableSAMLObject, SignableXMLObject,
        AttributeExtensibleXMLObject {

    /** Element name, no namespace */
    public final static String DEFAULT_ELEMENT_LOCAL_NAME = "EntityDescriptor";
    
    /** Default element name */
    public final static QName DEFUALT_ELEMENT_NAME = new QName(SAMLConstants.SAML20MD_NS, DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
    
    /** Local name of the XSI type */
    public final static String TYPE_LOCAL_NAME = "EntityDescriptorType"; 
        
    /** QName of the XSI type */
    public final static QName TYPE_NAME = new QName(SAMLConstants.SAML20MD_NS, TYPE_LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);

    /** Element QName, no prefix */
    public final static QName ELEMENT_QNAME = new QName(SAMLConstants.SAML20MD_NS, DEFAULT_ELEMENT_LOCAL_NAME);

    /** "ID" attribute name */
    public final static String ID_ATTRIB_NAME = "ID";

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
     * Gets the ID for this entity descriptor.
     * 
     * @return the ID for this entity descriptor
     */
    public String getID();

    /**
     * Sets the ID for this entity descriptor.
     * 
     * @param newID the ID for this entity descriptor
     */
    public void setID(String newID);

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
     * @throws IllegalArgumentException thrown if the given extensions Object is already a child of another SAMLObject
     */
    public void setExtensions(Extensions extensions) throws IllegalArgumentException;

    /**
     * Gets all the role descriptors for this entity descriptor.
     * 
     * @return the role descriptors for this entity descriptor
     */
    public List<RoleDescriptor> getRoleDescriptors();

    /**
     * Gets all the role descriptors for this entity descriptor that match the supplied QName parameter.
     * 
     * @return the role descriptors for this entity descriptor
     */
    public List<RoleDescriptor> getRoleDescriptors(QName typeOrName);

    /**
     * Gets all the {@link IDPSSODescriptor}s role descriptor for this entity.
     * 
     * @return the {@link IDPSSODescriptor}s role descriptor for this entity
     */
    public List<IDPSSODescriptor> getIDPSSODescriptor();

    /**
     * Gets all the {@link SPSSODescriptor}s role descriptor for this entity.
     * 
     * @return the {@link SPSSODescriptor}s role descriptor for this entity
     */
    public List<SPSSODescriptor> getSPSSODescriptor();

    /**
     * Gets all the {@link AuthnAuthorityDescriptor}s role descriptor for this entity.
     * 
     * @return the {@link AuthnAuthorityDescriptor}s role descriptor for this entity
     */
    public List<AuthnAuthorityDescriptor> getAuthnAuthorityDescriptor();

    /**
     * Gets all the {@link AttributeAuthorityDescriptor}s role descriptor for this entity.
     * 
     * @return the {@link AttributeAuthorityDescriptor}s role descriptor for this entity
     */
    public List<AttributeAuthorityDescriptor> getAttributeAuthorityDescriptor();

    /**
     * Gets all the {@link PDPDescriptor}s role descriptor for this entity.
     * 
     * @return the {@link PDPDescriptor}s role descriptor for this entity
     */
    public List<PDPDescriptor> getPDPDescriptor();

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
     * @throws IllegalArgumentException thrown if the descriptor is owned by another entity or if this entity already
     *             has one or more role descriptors associated with it
     */
    public void setAffiliationDescriptor(AffiliationDescriptor descriptor) throws IllegalArgumentException;

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
     * @throws IllegalArgumentException thrown if this organization belongs to another entity
     */
    public void setOrganization(Organization organization) throws IllegalArgumentException;

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