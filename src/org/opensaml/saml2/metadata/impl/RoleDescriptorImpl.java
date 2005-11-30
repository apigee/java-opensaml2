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
import java.util.Set;

import javax.xml.namespace.QName;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.util.StringHelper;
import org.opensaml.saml2.common.impl.ExtensionsSAMLObjectHelper;
import org.opensaml.saml2.common.impl.SignableTimeBoundCacheableSAMLObject;
import org.opensaml.saml2.metadata.ContactPerson;
import org.opensaml.saml2.metadata.Extensions;
import org.opensaml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml2.metadata.Organization;
import org.opensaml.saml2.metadata.RoleDescriptor;

/**
 * Concretate implementation of {@link org.opensaml.saml2.metadata.RoleDescriptor}
 */
public abstract class RoleDescriptorImpl extends SignableTimeBoundCacheableSAMLObject implements RoleDescriptor {

    /** Set of supported protocols */
    private Set<String> supportedProtocols = new HashSet<String>();
    
    /** Error URL */
    private String errorURL;
    
    /** Organization administering this role */
    private Organization organization;
    
    /** Contact persons for this role */
    private Set<ContactPerson> contactPersons = new LinkedHashSet<ContactPerson>();
    
    /**
     * Helper for dealing ExtensionsExtensibleElement interface methods
     */
    private ExtensionsSAMLObjectHelper extensionHelper;
    
    /**
     * Constructor
     */
    protected RoleDescriptorImpl() {
        super();
    }
    
    /*
     * @see org.opensaml.saml2.metadata.RoleDescriptor#getSupportedProtocols()
     */
    public Set<String> getSupportedProtocols() {
        return Collections.unmodifiableSet(supportedProtocols);
    }

    /*
     * @see org.opensaml.saml2.metadata.RoleDescriptor#isSupportedProtocol(java.lang.String)
     */
    public boolean isSupportedProtocol(String protocol) {
        return supportedProtocols.contains(protocol);
    }

    /*
     * @see org.opensaml.saml2.metadata.RoleDescriptor#addSupportedProtocol(java.lang.String)
     */
    public void addSupportedProtocol(String protocol) {
        protocol = StringHelper.safeTrimOrNullString(protocol);
        if(protocol != null && !supportedProtocols.contains(protocol)) {
            releaseThisandParentDOM();
            supportedProtocols.add(protocol);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.RoleDescriptor#removeProtocol(java.net.URI)
     */
    public void removeSupportedProtocol(String protocol) {
        protocol = StringHelper.safeTrimOrNullString(protocol);
        if(protocol != null && supportedProtocols.contains(protocol)) {
            releaseThisandParentDOM();
            supportedProtocols.remove(protocol);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.RoleDescriptor#removeProtocols(java.util.Set)
     */
    public void removeSupportedProtocols(Set<String> protocols) {
       for(String protocol : protocols) {
           removeSupportedProtocol(protocol);
       }
    }

    /*
     * @see org.opensaml.saml2.metadata.RoleDescriptor#removeAllProtocols()
     */
    public void removeAllSupportedProtocols() {
        for(String protocol : supportedProtocols) {
            removeSupportedProtocol(protocol);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.RoleDescriptor#getErrorURL()
     */
    public String getErrorURL() {
        return errorURL;
    }

    /*
     * @see org.opensaml.saml2.metadata.RoleDescriptor#setErrorURL(java.lang.String)
     */
    public void setErrorURL(String errorURL) {
        errorURL = StringHelper.safeTrimOrNullString(errorURL);
        if(!StringHelper.safeEquals(this.errorURL, errorURL)) {
            releaseThisandParentDOM();
            this.errorURL = errorURL;
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.RoleDescriptor#getOrganization()
     */
    public Organization getOrganization() {
        return organization;
    }

    /*
     * @see org.opensaml.saml2.metadata.RoleDescriptor#setOrganization(org.opensaml.saml2.metadata.Organization)
     */
    public void setOrganization(Organization organization) throws IllegalAddException{
        if (organization.hasParent()) {
            throw new IllegalAddException("Can not add an organization owned by another role.");
        }
        
        if(this.organization == null) {
            if(organization == null) {
                return;
            }
        }else {
            if(this.organization.equals(organization)) {
                return;
            }
        }
        
        releaseThisandParentDOM();
        organization.setParent(this);
        this.organization = organization;
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
        if(contactPersons != null && !contactPersons.contains(person)) {
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
            contactPersons.remove(person);
            person.setParent(null);
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
     * @see org.opensaml.saml2.metadata.KeyDescriptorDescriptorComp#getKeyDescriptors()
     */
    public Set<KeyDescriptor> getKeyDescriptors() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * @see org.opensaml.saml2.metadata.KeyDescriptorDescriptorComp#addKeyDescriptor(org.opensaml.saml2.metadata.KeyDescriptor)
     */
    public void addKeyDescriptor(KeyDescriptor keyDescriptor) {
        // TODO Auto-generated method stub

    }

    /*
     * @see org.opensaml.saml2.metadata.KeyDescriptorDescriptorComp#addKeyDescriptors(java.util.Set)
     */
    public void addKeyDescriptors(Set keyDescriptors) {
        // TODO Auto-generated method stub

    }

    /*
     * @see org.opensaml.saml2.metadata.KeyDescriptorDescriptorComp#removeKeyDescriptor(org.opensaml.saml2.metadata.KeyDescriptor)
     */
    public void removeKeyDescriptor(KeyDescriptor keyDescriptor) {
        // TODO Auto-generated method stub

    }

    /*
     * @see org.opensaml.saml2.metadata.KeyDescriptorDescriptorComp#removeKeyDescriptors(java.util.Set)
     */
    public void removeKeyDescriptors(Set keyDescriptors) {
        // TODO Auto-generated method stub

    }

    /*
     * @see org.opensaml.saml2.metadata.KeyDescriptorDescriptorComp#removeAllKeyDescriptors()
     */
    public void removeAllKeyDescriptors() {
        // TODO Auto-generated method stub

    }
    
    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public Set<SAMLObject> getOrderedChildren() {
        Set<SAMLObject> children = new LinkedHashSet<SAMLObject>();
        
        children.add(getExtensions());
        
        for(KeyDescriptor descriptor : getKeyDescriptors()) {
            children.add(descriptor);
        }

        
        children.add(getOrganization());
        
        for (ContactPerson person : getContactPersons()) {
            children.add(person);
        }
        
        return children;
    }
}