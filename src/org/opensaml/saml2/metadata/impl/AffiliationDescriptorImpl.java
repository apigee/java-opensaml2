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

/**
 * 
 */
package org.opensaml.saml2.metadata.impl;

import java.util.Collection;

import javax.xml.namespace.QName;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.SAMLObjectBuilderFactory;
import org.opensaml.common.util.OrderedSet;
import org.opensaml.common.util.UnmodifiableOrderedSet;
import org.opensaml.saml2.common.impl.ExtensionsSAMLObjectHelper;
import org.opensaml.saml2.common.impl.SignableTimeBoundCacheableSAMLObject;
import org.opensaml.saml2.metadata.AffiliateMember;
import org.opensaml.saml2.metadata.AffiliationDescriptor;
import org.opensaml.saml2.metadata.Extensions;
import org.opensaml.saml2.metadata.KeyDescriptor;

/**
 * Concrete implementation of {@link org.opensaml.saml2.metadata.AffiliationDescriptor}.
 */
public class AffiliationDescriptorImpl extends SignableTimeBoundCacheableSAMLObject implements AffiliationDescriptor {
    
    /** ID of the owner of this affiliation */
    private String ownerID;
    
    /** Members of this affiliation */
    private OrderedSet<AffiliateMember> members = new OrderedSet<AffiliateMember>();
    
    /** Key descriptors for this role */
    private OrderedSet<KeyDescriptor> keyDescriptors = new OrderedSet<KeyDescriptor>();
    
    /**
     * Helper for dealing ExtensionsExtensibleElement interface methods
     */
    private ExtensionsSAMLObjectHelper extensionHelper;
    
    /**
     * Constructor
     */
    public AffiliationDescriptorImpl(){
        super();
        setQName(AffiliationDescriptor.QNAME);
        
        extensionHelper = new ExtensionsSAMLObjectHelper(this);
    }

    /*
     * @see org.opensaml.saml2.metadata.AffiliationDescriptor#getOwnerID()
     */
    public String getOwnerID() {
        return ownerID;
    }

    /*
     * @see org.opensaml.saml2.metadata.AffiliationDescriptor#setOwnerID(java.lang.String)
     */
    public void setOwnerID(String newOwnerID) {
        ownerID = prepareForAssignment(ownerID, newOwnerID);
    }

    /*
     * @see org.opensaml.saml2.metadata.AffiliationDescriptor#isMember(java.lang.String)
     */
    public boolean isMember(String id) {
        return members.contains(id);
    }

    /*
     * @see org.opensaml.saml2.metadata.AffiliationDescriptor#getMembers()
     */
    public UnmodifiableOrderedSet<AffiliateMember> getMembers() {
        return new UnmodifiableOrderedSet<AffiliateMember>(members);
    }
    
    /*
     * @see org.opensaml.saml2.metadata.AffiliationDescriptor#addMember(java.lang.String)
     */
    public void addMember(AffiliateMember member) throws IllegalAddException {
        addSAMLObject(members, member);
    }

    /*
     * @see org.opensaml.saml2.metadata.AffiliationDescriptor#addMember(java.lang.String)
     */
    public void addMember(String memberID) throws IllegalArgumentException {
        SAMLObjectBuilder builder = SAMLObjectBuilderFactory.getInstance().getBuilder(AffiliateMember.QNAME);
        AffiliateMember member = (AffiliateMember) builder.buildObject();
        member.setID(memberID);
        
        try{
            addMember(member);
        }catch(SAMLException e){
            //DO NOTHING, unreachable at this point
        }
    }
    
    /*
     * @see org.opensaml.saml2.metadata.AffiliationDescriptor#removeMember(org.opensaml.saml2.metadata.AffiliateMember)
     */
    public void removeMember(AffiliateMember member) {
        removeSAMLObject(members, member);
    }

    /*
     * @see org.opensaml.saml2.metadata.AffiliationDescriptor#removeMember(java.lang.String)
     */
    public void removeMember(String memberID) {
        SAMLObjectBuilder builder = SAMLObjectBuilderFactory.getInstance().getBuilder(AffiliateMember.QNAME);
        AffiliateMember member = (AffiliateMember) builder.buildObject();
        member.setID(memberID);
        removeMember(member);
    }

    /*
     * @see org.opensaml.saml2.metadata.AffiliationDescriptor#removeMemebers(java.util.Collection)
     */
    public void removeMemebers(Collection<AffiliateMember> members) {
        if(members != null){
            for(AffiliateMember member : members){
                removeMember(member);
            }
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.AffiliationDescriptor#removeAllMembers()
     */
    public void removeAllMembers() {
        for(AffiliateMember member : members){
            removeMember(member);
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
     * @see org.opensaml.saml2.metadata.KeyDescriptorDescriptorComp#getKeyDescriptors()
     */
    public UnmodifiableOrderedSet<KeyDescriptor> getKeyDescriptors() {
        return new UnmodifiableOrderedSet<KeyDescriptor>(keyDescriptors);
    }

    /*
     * @see org.opensaml.saml2.metadata.KeyDescriptorDescriptorComp#addKeyDescriptor(org.opensaml.saml2.metadata.KeyDescriptor)
     */
    public void addKeyDescriptor(KeyDescriptor keyDescriptor) throws IllegalAddException {
        addSAMLObject(keyDescriptors, keyDescriptor);
    }

    /*
     * @see org.opensaml.saml2.metadata.KeyDescriptorDescriptorComp#removeKeyDescriptor(org.opensaml.saml2.metadata.KeyDescriptor)
     */
    public void removeKeyDescriptor(KeyDescriptor keyDescriptor) {
        removeSAMLObject(keyDescriptors, keyDescriptor);
    }

    /*
     * @see org.opensaml.saml2.metadata.KeyDescriptorDescriptorComp#removeKeyDescriptors(java.util.Set)
     */
    public void removeKeyDescriptors(Collection<KeyDescriptor> keyDescriptors) {
        removeSAMLObjects(this.keyDescriptors, keyDescriptors);
    }

    /*
     * @see org.opensaml.saml2.metadata.KeyDescriptorDescriptorComp#removeAllKeyDescriptors()
     */
    public void removeAllKeyDescriptors() {
        for(KeyDescriptor keyDescriptor : keyDescriptors) {
            removeKeyDescriptor(keyDescriptor);
        }
    }
    
    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public UnmodifiableOrderedSet<SAMLObject> getOrderedChildren() {
        OrderedSet<SAMLObject> children = new OrderedSet<SAMLObject>();
        
        children.add(getExtensions());

        children.addAll(getMembers());
        
        children.addAll(getKeyDescriptors());
        
        return new UnmodifiableOrderedSet<SAMLObject>(children);
    }

    /*
     * @see org.opensaml.common.SAMLObject#equals(org.opensaml.common.SAMLObject)
     */
    public boolean equals(SAMLObject element) {
        // TODO Auto-generated method stub
        return false;
    }
}