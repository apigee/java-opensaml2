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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSignableSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Extensions;
import org.opensaml.saml2.metadata.AffiliateMember;
import org.opensaml.saml2.metadata.AffiliationDescriptor;
import org.opensaml.saml2.metadata.KeyDescriptor;
import org.opensaml.xml.IllegalAddException;

/**
 * Concrete implementation of {@link org.opensaml.saml2.metadata.AffiliationDescriptor}.
 */
public class AffiliationDescriptorImpl extends AbstractSignableSAMLObject implements AffiliationDescriptor {

    /** ID of the owner of this affiliation */
    private String ownerID;
    
    /** validUntil attribute */
    private GregorianCalendar validUntil;
    
    /** cacheDurection attribute */
    private Long cacheDuration;
    
    /** Extensions child */
    private Extensions extensions;
    
    /** Members of this affiliation */
    private ArrayList<AffiliateMember> members = new ArrayList<AffiliateMember>();
    
    /** Key descriptors for this role */
    private ArrayList<KeyDescriptor> keyDescriptors = new ArrayList<KeyDescriptor>();
    
    /**
     * Constructor
     */
    public AffiliationDescriptorImpl(){
        super(SAMLConstants.SAML20MD_NS, AffiliationDescriptor.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20MD_PREFIX);
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
     * @see org.opensaml.saml2.common.TimeBoundSAMLObject#isValid()
     */
    public boolean isValid() {
        return validUntil.before(GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC")));
    }
    
    /*
     * @see org.opensaml.saml2.common.TimeBoundSAMLObject#getValidUntil()
     */
    public GregorianCalendar getValidUntil() {
        return validUntil;
    }

    /*
     * @see org.opensaml.saml2.common.TimeBoundSAMLObject#setValidUntil(java.util.GregorianCalendar)
     */
    public void setValidUntil(GregorianCalendar validUntil) {
        this.validUntil = prepareForAssignment(this.validUntil, validUntil);
    }

    /*
     * @see org.opensaml.saml2.common.CacheableSAMLObject#getCacheDuration()
     */
    public Long getCacheDuration() {
        return cacheDuration;
    }

    /*
     * @see org.opensaml.saml2.common.CacheableSAMLObject#setCacheDuration(java.lang.Long)
     */
    public void setCacheDuration(Long duration) {
        cacheDuration = prepareForAssignment(cacheDuration, duration);
    }
    
    /*
     * @see org.opensaml.saml2.metadata.AffiliationDescriptor#getExtensions()
     */
    public Extensions getExtensions() {
        return extensions;
    }

    /*
     * @see org.opensaml.saml2.metadata.AffiliationDescriptor#setExtensions(org.opensaml.saml2.core.Extensions)
     */
    public void setExtensions(Extensions extensions) throws IllegalAddException {
        this.extensions = prepareForAssignment(this.extensions, extensions);
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
    public List<AffiliateMember> getMembers() {
        return Collections.unmodifiableList(members);
    }
    
    /*
     * @see org.opensaml.saml2.metadata.AffiliationDescriptor#addMember(java.lang.String)
     */
    public void addMember(AffiliateMember member) throws IllegalAddException {
        addXMLObject(members, member);
    }
    
    /*
     * @see org.opensaml.saml2.metadata.AffiliationDescriptor#removeMember(org.opensaml.saml2.metadata.AffiliateMember)
     */
    public void removeMember(AffiliateMember member) {
        removeXMLObject(members, member);
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
     * @see org.opensaml.saml2.metadata.KeyDescriptorDescriptorComp#getKeyDescriptors()
     */
    public List<KeyDescriptor> getKeyDescriptors() {
        return Collections.unmodifiableList(keyDescriptors);
    }

    /*
     * @see org.opensaml.saml2.metadata.KeyDescriptorDescriptorComp#addKeyDescriptor(org.opensaml.saml2.metadata.KeyDescriptor)
     */
    public void addKeyDescriptor(KeyDescriptor keyDescriptor) throws IllegalAddException {
        addXMLObject(keyDescriptors, keyDescriptor);
    }

    /*
     * @see org.opensaml.saml2.metadata.KeyDescriptorDescriptorComp#removeKeyDescriptor(org.opensaml.saml2.metadata.KeyDescriptor)
     */
    public void removeKeyDescriptor(KeyDescriptor keyDescriptor) {
        removeXMLObject(keyDescriptors, keyDescriptor);
    }

    /*
     * @see org.opensaml.saml2.metadata.KeyDescriptorDescriptorComp#removeKeyDescriptors(java.util.Set)
     */
    public void removeKeyDescriptors(Collection<KeyDescriptor> keyDescriptors) {
        removeXMLObjects(this.keyDescriptors, keyDescriptors);
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
    public List<SAMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        
        children.add(getExtensions());

        children.addAll(getMembers());
        
        children.addAll(getKeyDescriptors());
        
        return Collections.unmodifiableList(children);
    }
}