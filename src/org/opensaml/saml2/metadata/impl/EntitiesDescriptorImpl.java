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
import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.namespace.QName;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.util.StringHelper;
import org.opensaml.saml2.common.impl.ExtensionsSAMLObjectHelper;
import org.opensaml.saml2.common.impl.SignableTimeBoundCacheableSAMLObject;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.Extensions;

/**
 * Concrete implementation of {@link org.opensaml.saml2.metadata.EntitiesDescriptor}.
 */
public class EntitiesDescriptorImpl extends SignableTimeBoundCacheableSAMLObject implements EntitiesDescriptor {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -6728339304859005880L;

    /**
     * Name of this descriptor group
     */
    private String name;

    /**
     * Ordered set of child Entity/Entities Descriptors
     */
    private Set<SAMLObject> orderedDescriptors = new LinkedHashSet<SAMLObject>();
    
    /**
     * Ordered set of EntitiesDescriptors
     */
    private Set<EntitiesDescriptor> entitiesDescriptors = new LinkedHashSet<EntitiesDescriptor>();
    
    /**
     * Ordered set of EntityDescriptors
     */
    private Set<EntityDescriptor> entityDescriptors = new LinkedHashSet<EntityDescriptor>();
    
    /**
     * Helper for dealing ExtensionsExtensibleElement interface methods
     */
    private ExtensionsSAMLObjectHelper extensionHelper;
    
    /**
     * Constructor
     */
    public EntitiesDescriptorImpl() {
        super();
        setQName(EntitiesDescriptor.QNAME);
        extensionHelper = new ExtensionsSAMLObjectHelper(this);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#getName()
     */
    public String getName() {
        return name;
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#setName(java.lang.String)
     */
    public void setName(String newName) {
        String newGivenName = StringHelper.safeTrimOrNullString(newName);
        if(!StringHelper.safeEquals(newGivenName, this.name)){
            releaseThisandParentDOM();
            this.name = newGivenName;
        }
        
        //Name didn't really change, don't do anything
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#getEntitiesDescriptors()
     */
    public Set<EntitiesDescriptor> getEntitiesDescriptors() {
        return Collections.unmodifiableSet(entitiesDescriptors);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#addEntitiesDescriptor(org.opensaml.saml2.metadata.EntitiesDescriptor)
     */
    public void addEntitiesDescriptor(EntitiesDescriptor descriptor) throws IllegalAddException{
        if(!entitiesDescriptors.contains(descriptor)){
            if(descriptor.hasParent()) {
                throw new IllegalAddException("The given EntitiesDescriptor element is already a descendant of another SAMLElement");
            }
            releaseThisandParentDOM();
            descriptor.setParent(this);
            orderedDescriptors.add(descriptor);
            entitiesDescriptors.add(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#removeEntitiesDescriptor(org.opensaml.saml2.metadata.EntitiesDescriptor)
     */
    public void removeEntitiesDescriptor(EntitiesDescriptor descriptor) {
        if(entitiesDescriptors.contains(descriptor)){
            releaseThisandParentDOM();
            descriptor.setParent(null);
            orderedDescriptors.remove(descriptor);
            entitiesDescriptors.remove(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#removeEntitiesDescriptors(java.util.List)
     */
    public void removeEntitiesDescriptors(Set<EntitiesDescriptor> desciptors) {
        for(EntitiesDescriptor descriptor : desciptors) {
            removeEntitiesDescriptor(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#removeAllEntitiesDescriptors()
     */
    public void removeAllEntitiesDescriptors() {
        for(EntitiesDescriptor descriptor : entitiesDescriptors) {
            removeEntitiesDescriptor(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#getEntityDescriptors()
     */
    public Set<EntityDescriptor> getEntityDescriptors() {
        return Collections.unmodifiableSet(entityDescriptors);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#addEntityDescriptor(org.opensaml.saml2.metadata.EntityDescriptor)
     */
    public void addEntityDescriptor(EntityDescriptor descriptor) throws IllegalAddException{
        if(!entityDescriptors.contains(descriptor)){
            if(descriptor.hasParent()) {
                throw new IllegalAddException("The given EntityDescriptor element is already a descendant of another SAMLElement");
            }
            releaseThisandParentDOM();
            descriptor.setParent(this);
            orderedDescriptors.add(descriptor);
            entityDescriptors.add(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#removeEntityDescriptor(org.opensaml.saml2.metadata.EntityDescriptor)
     */
    public void removeEntityDescriptor(EntityDescriptor descriptor){
        if(entityDescriptors.contains(descriptor)){
            releaseThisandParentDOM();
            descriptor.setParent(null);
            orderedDescriptors.remove(descriptor);
            entityDescriptors.remove(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#removeEntityDescriptors(java.util.List)
     */
    public void removeEntityDescriptors(Set<EntityDescriptor> descriptors) {
        for(EntityDescriptor descriptor : descriptors) {
            removeEntityDescriptor(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#removeAllEntityDescriptors()
     */
    public void removeAllEntityDescriptors() {
        for(EntityDescriptor descriptor : entityDescriptors) {
            removeEntityDescriptor(descriptor);
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
    public void setExtensions(Extensions extensions) throws IllegalAddException{
        if(!(extensions.equals(extensionHelper.getExtensions()))){
            extensionHelper.setExtensions(extensions);
        }
    }
    
    /*
     * @see org.opensaml.saml2.common.impl.AbstractSAMLElement#getOrderedChildren()
     */
    public Set<SAMLObject> getOrderedChildren(){
        Set<SAMLObject> children = new LinkedHashSet<SAMLObject>();
        
        if(getExtensions() != null){
            children.add(getExtensions());
        }
        
        children.addAll(getOrderedChildDescriptors());
        
        return children;
    }

    /**
     * Checks if this element is equal to the given element.  The elements are equal if:
     * <ul>
     *   <li>They are both {@link EntitiesDescriptor}s</li>
     *   <li>They have the same Name attribute</li>
     * </ul>
     */
    public boolean equals(SAMLObject element) {
        if (element instanceof EntitiesDescriptor){
            EntitiesDescriptor other = (EntitiesDescriptor)element;
            
            return getName().equals(other.getName());
        }
        
        return false;
    }
    
    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#getOrderedChildDescriptors()
     */
    public Set<SAMLObject> getOrderedChildDescriptors(){
        return Collections.unmodifiableSet(orderedDescriptors);
    }
}