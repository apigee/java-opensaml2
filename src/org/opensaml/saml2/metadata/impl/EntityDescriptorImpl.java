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

import java.util.Collection;
import java.util.Map;

import javax.xml.namespace.QName;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.util.OrderedSet;
import org.opensaml.common.util.StringHelper;
import org.opensaml.common.util.UnmodifiableOrderedSet;
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
    private OrderedSet<RoleDescriptor> roleDescriptors = new OrderedSet<RoleDescriptor>();

    /** Index of role descriptors by type */
    private Map<QName, OrderedSet<RoleDescriptor>> typeIndexedRoleDescriptors;

    /** Affiliatition descriptor for this entity */
    private AffiliationDescriptor affiliationDescriptor;

    /** Organization the administers this entity */
    private Organization organization;

    /** Contact persons for this entity */
    private OrderedSet<ContactPerson> contactPersons = new OrderedSet<ContactPerson>();

    /** Additional metadata locations for this entity */
    private OrderedSet<AdditionalMetadataLocation> additionalMetadata = new OrderedSet<AdditionalMetadataLocation>();

    /**
     * Helper for dealing ExtensionsExtensibleElement interface methods
     */
    private ExtensionsSAMLObjectHelper extensionHelper;

    public EntityDescriptorImpl() {
        super();
        setQName(EntityDescriptor.QNAME);
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
        
        entityID = prepareForAssignment(entityID, id);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#getRoleDescriptors()
     */
    public UnmodifiableOrderedSet<RoleDescriptor> getRoleDescriptors() {
        return new UnmodifiableOrderedSet<RoleDescriptor>(roleDescriptors);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#getRoleDescriptors(javax.xml.namespace.QName)
     */
    public UnmodifiableOrderedSet<RoleDescriptor> getRoleDescriptors(QName type) {
        return new UnmodifiableOrderedSet<RoleDescriptor>(typeIndexedRoleDescriptors.get(type));
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
        if(addSAMLObject(roleDescriptors, descriptor)) {
            OrderedSet<RoleDescriptor> typedRoleDescriptors = typeIndexedRoleDescriptors.get(descriptor.getSchemaType());
            if (typedRoleDescriptors == null) {
                typedRoleDescriptors = new OrderedSet<RoleDescriptor>();
                typeIndexedRoleDescriptors.put(descriptor.getSchemaType(), typedRoleDescriptors);
            }
            typedRoleDescriptors.add(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#removeRoleDescriptor(org.opensaml.saml2.metadata.RoleDescriptor)
     */
    public void removeRoleDescriptor(RoleDescriptor descriptor) {
        if(removeSAMLObject(roleDescriptors, descriptor)) {
            typeIndexedRoleDescriptors.remove(descriptor.getSchemaType());
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#removeRoleDescriptors(java.util.Set)
     */
    public void removeRoleDescriptors(Collection<RoleDescriptor> descriptors) {
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
        affiliationDescriptor = prepareForAssignment(affiliationDescriptor, descriptor);
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
        organization = prepareForAssignment(this.organization, organization);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#getContactPersons()
     */
    public UnmodifiableOrderedSet<ContactPerson> getContactPersons() {
        return new UnmodifiableOrderedSet<ContactPerson>(contactPersons);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#addContactPerson(org.opensaml.saml2.metadata.ContactPerson)
     */
    public void addContactPerson(ContactPerson person) throws IllegalAddException {
        addSAMLObject(contactPersons, person);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#removeContactPerson(org.opensaml.saml2.metadata.ContactPerson)
     */
    public void removeContactPerson(ContactPerson person) {
        removeSAMLObject(contactPersons, person);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#removeContactPersons(java.util.Set)
     */
    public void removeContactPersons(Collection<ContactPerson> persons) {
        removeSAMLObjects(contactPersons, persons);
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
    public UnmodifiableOrderedSet<AdditionalMetadataLocation> getAdditionalMetadataLocations() {
        return new UnmodifiableOrderedSet<AdditionalMetadataLocation>(additionalMetadata);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#addAdditionalMetadataLocation(org.opensaml.saml2.metadata.AdditionalMetadataLocation)
     */
    public void addAdditionalMetadataLocation(AdditionalMetadataLocation location) throws IllegalAddException {
        addSAMLObject(additionalMetadata, location);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#removeAdditionalMetadataLocation(org.opensaml.saml2.metadata.AdditionalMetadataLocation)
     */
    public void removeAdditionalMetadataLocation(AdditionalMetadataLocation location) {
        removeSAMLObject(additionalMetadata, location);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntityDescriptor#removeAdditionalMetadataLocations(java.util.Set)
     */
    public void removeAdditionalMetadataLocations(Collection<AdditionalMetadataLocation> locations) {
        removeSAMLObjects(additionalMetadata, locations);
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
    public UnmodifiableOrderedSet<SAMLObject> getExtensionElements() {
        return extensionHelper.getExtensionElements();
    }

    /*
     * @see org.opensaml.saml2.common.ExtensionsExtensibleElement#getExtensionElements(javax.xml.namespace.QName)
     */
    public UnmodifiableOrderedSet<SAMLObject> getExtensionElements(QName elementName) {
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
    public UnmodifiableOrderedSet<SAMLObject> getOrderedChildren() {
        OrderedSet<SAMLObject> children = new OrderedSet<SAMLObject>();
        
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

        return new UnmodifiableOrderedSet<SAMLObject>(children);
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