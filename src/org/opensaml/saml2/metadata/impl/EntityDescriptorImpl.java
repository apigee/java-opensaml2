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

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.util.StringHelper;
import org.opensaml.saml2.common.impl.ExtensionsSAMLObjectHelper;
import org.opensaml.saml2.common.impl.SignableTimeBoundCacheableSAMLObject;
import org.opensaml.saml2.metadata.AdditionalMetadataLocation;
import org.opensaml.saml2.metadata.AffiliationDescriptor;
import org.opensaml.saml2.metadata.ContactPerson;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.Extensions;
import org.opensaml.saml2.metadata.Organization;
import org.opensaml.saml2.metadata.RoleDescriptor;

/**
 * Concretate implementation of {@link org.opensaml.saml2.metadata.EntitiesDescriptor}
 */
public class EntityDescriptorImpl extends SignableTimeBoundCacheableSAMLObject implements EntityDescriptor {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1378835031535483784L;

    /** Entity ID of this Entity */
    private String entityID;

    /** Role descriptors for this entity */
    private Set<RoleDescriptor> roleDescriptors = new LinkedHashSet<RoleDescriptor>();

    /** Index of role descriptors by type */
    private Map<QName, Set<RoleDescriptor>> typeIndexedRoleDescriptors;

    /** Affiliatition descriptor for this entity */
    private AffiliationDescriptor affiliationDescriptor;

    /** Organization the administers this entity */
    private Organization organization;

    /** Contact persons for this entity */
    private Set<ContactPerson> contactPersons = new LinkedHashSet<ContactPerson>();

    /** Additional metadata locations for this entity */
    private Set<AdditionalMetadataLocation> additionalMetadata = new LinkedHashSet<AdditionalMetadataLocation>();

    /**
     * Helper for dealing ExtensionsExtensibleElement interface methods
     */
    private ExtensionsSAMLObjectHelper extensionHelper;

    public EntityDescriptorImpl() {
        super();

        extensionHelper = new ExtensionsSAMLObjectHelper(this);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#getEntityID()
     */
    public String getEntityID() {
        return entityID;
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#setEntityID(java.lang.String)
     */
    public void setEntityID(String id) {
        
        entityID = assignString(entityID, id);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#getRoleDescriptors()
     */
    public Set<RoleDescriptor> getRoleDescriptors() {
        return Collections.unmodifiableSet(roleDescriptors);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#getRoleDescriptors(javax.xml.namespace.QName)
     */
    public Set<RoleDescriptor> getRoleDescriptors(QName type) {
        return Collections.unmodifiableSet(typeIndexedRoleDescriptors.get(type));
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#getRoleDescriptors(javax.xml.namespace.QName, java.lang.String)
     */
    public RoleDescriptor getRoleDescriptors(QName type, String protocol) {
        for (RoleDescriptor descriptor : getRoleDescriptors(type)) {
            if (descriptor.isSupportedProtocol(protocol)) {
                return descriptor;
            }
        }

        return null;
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#addRoleDescriptor(org.opensaml.saml2.metadata.RoleDescriptor)
     */
    public void addRoleDescriptor(RoleDescriptor descriptor) throws IllegalAddException {
        if (getAffiliationDescriptor() != null) {
            throw new IllegalAddException(
                    "Can not add a role descritptor to an entity that contains an affiliation descriptor");
        }

        if (descriptor != null && !roleDescriptors.contains(descriptor)) {
            if (descriptor.hasParent()) {
                throw new IllegalAddException("Can not add a role descriptor owned by another entity to this entity.");
            }
            releaseThisandParentDOM();
            descriptor.setParent(this);
            roleDescriptors.add(descriptor);
            Set<RoleDescriptor> typedRoleDescriptors = typeIndexedRoleDescriptors.get(descriptor.getSchemaType());
            if (typedRoleDescriptors == null) {
                typedRoleDescriptors = new HashSet<RoleDescriptor>();
                typeIndexedRoleDescriptors.put(descriptor.getSchemaType(), typedRoleDescriptors);
            }
            typedRoleDescriptors.add(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#removeRoleDescriptor(org.opensaml.saml2.metadata.RoleDescriptor)
     */
    public void removeRoleDescriptor(RoleDescriptor descriptor) {
        if (descriptor != null && roleDescriptors.contains(descriptor)) {
            releaseThisandParentDOM();
            roleDescriptors.remove(descriptor);
            typeIndexedRoleDescriptors.remove(descriptor.getSchemaType());
            descriptor.setParent(null);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#removeRoleDescriptors(java.util.Set)
     */
    public void removeRoleDescriptors(Set<RoleDescriptor> descriptors) {
        for (RoleDescriptor descriptor : descriptors) {
            removeRoleDescriptor(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#removeAllRoleDescriptors()
     */
    public void removeAllRoleDescriptors() {
        for (RoleDescriptor descriptor : roleDescriptors) {
            removeRoleDescriptor(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#getAffiliationDescriptor()
     */
    public AffiliationDescriptor getAffiliationDescriptor() {
        return affiliationDescriptor;
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#setAffiliationDescriptor(org.opensaml.saml2.metadata.AffiliationDescriptor)
     */
    public void setAffiliationDescriptor(AffiliationDescriptor descriptor) throws IllegalAddException {
        if(descriptor == null) {
            return;
        }
        
        if (descriptor.hasParent()) {
            throw new IllegalAddException(
                    "Can not add an affiliation descriptor owned by another entity to this entity.");
        }

        if (getRoleDescriptors() != null) {
            throw new IllegalAddException(
                    "Can not add an affiliation descritptor to an entity that contains role descriptors");
        }

        releaseThisandParentDOM();
        descriptor.setParent(this);
        affiliationDescriptor = descriptor;
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#getOrganization()
     */
    public Organization getOrganization() {
        return organization;
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#setOrganization(org.opensaml.saml2.metadata.Organization)
     */
    public void setOrganization(Organization organization) throws IllegalAddException {
        if(organization == null) {
            if(this.organization == null) {
                return;
            }
            // Removing current organization
            this.organization.setParent(null);
            this.organization = null;
            releaseThisandParentDOM();
        }else {
            if(this.organization.equals(organization)) {
                return;
            }
            
            if (organization.hasParent()) {
                throw new IllegalAddException("Can not add an organization owned by another entity.");
            }
            
            //Replace existing organization
            this.organization.setParent(null);
            organization.setParent(this);
            this.organization = organization;
            releaseThisandParentDOM();
        }        
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#getContactPersons()
     */
    public Set<ContactPerson> getContactPersons() {
        return Collections.unmodifiableSet(contactPersons);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#addContactPerson(org.opensaml.saml2.metadata.ContactPerson)
     */
    public void addContactPerson(ContactPerson person) throws IllegalAddException {
        if (person != null && !contactPersons.contains(person)) {
            if (person.hasParent()) {
                throw new IllegalAddException("Can not add a contact person owned by another entity to this entity.");
            }

            releaseThisandParentDOM();
            person.setParent(this);
            contactPersons.add(person);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#removeContactPerson(org.opensaml.saml2.metadata.ContactPerson)
     */
    public void removeContactPerson(ContactPerson person) {
        if (person != null && contactPersons.contains(person)) {
            releaseThisandParentDOM();
            person.setParent(null);
            contactPersons.remove(person);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#removeContactPersons(java.util.Set)
     */
    public void removeContactPersons(Set<ContactPerson> persons) {
        for (ContactPerson person : persons) {
            removeContactPerson(person);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#removeAllContactPersons()
     */
    public void removeAllContactPersons() {
        for (ContactPerson person : contactPersons) {
            removeContactPerson(person);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#getAdditionalMetadataLocations()
     */
    public Set<AdditionalMetadataLocation> getAdditionalMetadataLocations() {
        return Collections.unmodifiableSet(additionalMetadata);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#addAdditionalMetadataLocation(org.opensaml.saml2.metadata.AdditionalMetadataLocation)
     */
    public void addAdditionalMetadataLocation(AdditionalMetadataLocation location) throws IllegalAddException {
        if (location != null && !additionalMetadata.contains(location)) {
            if (location.hasParent()) {
                throw new IllegalAddException(
                        "Can not add an additional metadata location owned by another entity to this entity.");
            }

            releaseThisandParentDOM();
            location.setParent(this);
            additionalMetadata.add(location);
        }

    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#removeAdditionalMetadataLocation(org.opensaml.saml2.metadata.AdditionalMetadataLocation)
     */
    public void removeAdditionalMetadataLocation(AdditionalMetadataLocation location) {
        if (location != null && additionalMetadata.contains(location)) {
            releaseThisandParentDOM();
            location.setParent(null);
            additionalMetadata.remove(location);
        }

    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#removeAdditionalMetadataLocations(java.util.Set)
     */
    public void removeAdditionalMetadataLocations(Set<AdditionalMetadataLocation> locations) {
        for (AdditionalMetadataLocation location : locations) {
            removeAdditionalMetadataLocation(location);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#removeAllAdditionalMetadataLocation()
     */
    public void removeAllAdditionalMetadataLocation() {
        for (AdditionalMetadataLocation location : additionalMetadata) {
            removeAdditionalMetadataLocation(location);
        }
    }

    /*
     * @see org.opensaml.saml2.common.ExtensionsExtensibleElement#getExtensions()
     */
    public Extensions getExtensions() {
        return extensionHelper.getExtensions();
    }

    /*
     * @see org.opensaml.saml2.common.ExtensionsExtensibleElement#getExtensionElements()
     */
    public Set<SAMLObject> getExtensionElements() {
        return extensionHelper.getExtensionElements();
    }

    /*
     * @see org.opensaml.saml2.common.ExtensionsExtensibleElement#getExtensionElements(javax.xml.namespace.QName)
     */
    public Set<SAMLObject> getExtensionElements(QName elementName) {
        return extensionHelper.getExtensionElements(elementName);
    }

    /*
     * @see org.opensaml.saml2.common.ExtensionsExtensibleElement#getExtensionElement(javax.xml.namespace.QName)
     */
    public SAMLObject getExtensionElement(QName elementName) {
        return extensionHelper.getExtensionElement(elementName);
    }

    /*
     * @see org.opensaml.saml2.common.ExtensionsExtensibleElement#setExtensions(org.opensaml.saml2.metadata.Extensions)
     */
    public void setExtensions(Extensions extensions) throws IllegalAddException {
        if (!(extensions.equals(extensionHelper.getExtensions()))) {
            extensionHelper.setExtensions(extensions);
        }
    }

    /*
     * @see org.opensaml.saml2.common.impl.AbstractSAMLElement#getOrderedChildren()
     */
    public Set<SAMLObject> getOrderedChildren() {
        Set<SAMLObject> children = new LinkedHashSet<SAMLObject>();
        
        children.add(getExtensions());
        
        for (RoleDescriptor descriptor : roleDescriptors) {
            children.add(descriptor);
        }
        
        children.add(getAffiliationDescriptor());
        
        children.add(getOrganization());
        
        for (ContactPerson person : contactPersons) {
            children.add(person);
        }
        
        for (AdditionalMetadataLocation location : additionalMetadata) {
            children.add(location);
        }

        return children;
    }

    /**
     * Checks to see if a given SAML object is equal to this object. The given object is equal to this one if it is of
     * type {@link EntityDescriptor} and has the same entity ID.
     */
    public boolean equals(SAMLObject element) {
        if (element instanceof EntityDescriptor) {
            return StringHelper.safeEquals(entityID, ((EntityDescriptor) element).getEntityID());
        }

        return false;
    }
}