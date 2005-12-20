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

import javax.xml.namespace.QName;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.util.OrderedSet;
import org.opensaml.common.util.UnmodifiableOrderedSet;
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
    private OrderedSet<SAMLObject> orderedDescriptors = new OrderedSet<SAMLObject>();
    
    /**
     * Ordered set of EntitiesDescriptors
     */
    private OrderedSet<EntitiesDescriptor> entitiesDescriptors = new OrderedSet<EntitiesDescriptor>();
    
    /**
     * Ordered set of EntityDescriptors
     */
    private OrderedSet<EntityDescriptor> entityDescriptors = new OrderedSet<EntityDescriptor>();
    
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
        this.name = prepareForAssignment(this.name, newName);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#getEntitiesDescriptors()
     */
    public UnmodifiableOrderedSet<EntitiesDescriptor> getEntitiesDescriptors() {
        return new UnmodifiableOrderedSet<EntitiesDescriptor>(entitiesDescriptors);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#addEntitiesDescriptor(org.opensaml.saml2.metadata.EntitiesDescriptor)
     */
    public void addEntitiesDescriptor(EntitiesDescriptor descriptor) throws IllegalAddException{
        if(addSAMLObject(entitiesDescriptors, descriptor)) {
            orderedDescriptors.add(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#removeEntitiesDescriptor(org.opensaml.saml2.metadata.EntitiesDescriptor)
     */
    public void removeEntitiesDescriptor(EntitiesDescriptor descriptor) {
        if(removeSAMLObject(entitiesDescriptors, descriptor)){
            orderedDescriptors.remove(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#removeEntitiesDescriptors(java.util.List)
     */
    public void removeEntitiesDescriptors(Collection<EntitiesDescriptor> desciptors) {
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
    public UnmodifiableOrderedSet<EntityDescriptor> getEntityDescriptors() {
        return new UnmodifiableOrderedSet<EntityDescriptor>(entityDescriptors);
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#addEntityDescriptor(org.opensaml.saml2.metadata.EntityDescriptor)
     */
    public void addEntityDescriptor(EntityDescriptor descriptor) throws IllegalAddException{
        if(addSAMLObject(entityDescriptors, descriptor)) {
            orderedDescriptors.add(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#removeEntityDescriptor(org.opensaml.saml2.metadata.EntityDescriptor)
     */
    public void removeEntityDescriptor(EntityDescriptor descriptor){
        if(removeSAMLObject(entityDescriptors, descriptor)) {
            orderedDescriptors.remove(descriptor);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.EntitiesDescriptor#removeEntityDescriptors(java.util.List)
     */
    public void removeEntityDescriptors(Collection<EntityDescriptor> descriptors) {
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
    public void setExtensions(Extensions extensions) throws IllegalAddException{
        if(!(extensions.equals(extensionHelper.getExtensions()))){
            extensionHelper.setExtensions(extensions);
        }
    }
    
    /*
     * @see org.opensaml.saml2.common.impl.AbstractSAMLElement#getOrderedChildren()
     */
    public UnmodifiableOrderedSet<SAMLObject> getOrderedChildren(){
        OrderedSet<SAMLObject> children = new OrderedSet<SAMLObject>();
        
        if(getExtensions() != null){
            children.add(getExtensions());
        }
        
        children.addAll(getOrderedChildDescriptors());
        
        return new UnmodifiableOrderedSet<SAMLObject>(children);
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
    public UnmodifiableOrderedSet<SAMLObject> getOrderedChildDescriptors(){
        return new UnmodifiableOrderedSet<SAMLObject>(orderedDescriptors);
    }
}