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

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.SAMLObjectBuilderFactory;
import org.opensaml.common.util.OrderedSet;
import org.opensaml.common.util.UnmodifiableOrderedSet;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml2.metadata.AttributeAuthorityDescriptor;
import org.opensaml.saml2.metadata.AttributeProfile;
import org.opensaml.saml2.metadata.AttributeService;
import org.opensaml.saml2.metadata.NameIDFormat;

/**
 * A concrete implementation of {@link org.opensaml.saml2.metadata.AttributeAuthorityDescriptor}.
 */
public class AttributeAuthorityDescriptorImpl extends RoleDescriptorImpl implements AttributeAuthorityDescriptor {

    /** Attribte query endpoints */
    private OrderedSet<AttributeService> attributeServices = new OrderedSet<AttributeService>();
    
    /** Assertion request endpoints */
    private OrderedSet<AssertionIDRequestService> assertionIDRequestServices = new OrderedSet<AssertionIDRequestService>();
    
    /** Supported NameID formats */
    private OrderedSet<NameIDFormat> nameFormats = new OrderedSet<NameIDFormat>();
    
    /** Supported attribute profiles */
    private OrderedSet<AttributeProfile> attributeProfiles = new OrderedSet<AttributeProfile>();
    
    /** Supported attribute */
    private OrderedSet<Attribute> attributes = new OrderedSet<Attribute>();
    
    /**
     * Constructor
     */
    public AttributeAuthorityDescriptorImpl(){
        super();
        setQName(AttributeAuthorityDescriptor.QNAME);
    }
    
    /*
     * @see org.opensaml.saml2.metadata.AttributeAuthorityDescriptor#getAttributeServices()
     */
    public UnmodifiableOrderedSet<AttributeService> getAttributeServices() {
        return new UnmodifiableOrderedSet<AttributeService>(attributeServices);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeAuthorityDescriptor#addAttributeService(org.opensaml.saml2.metadata.Endpoint)
     */
    public void addAttributeService(AttributeService service) throws IllegalAddException{
        addSAMLObject(attributeServices, service);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeAuthorityDescriptor#removeAttributeService(org.opensaml.saml2.metadata.Endpoint)
     */
    public void removeAttributeService(AttributeService service) {
        removeSAMLObject(attributeServices, service);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeAuthorityDescriptor#removeAttributeServices(java.util.Collection)
     */
    public void removeAttributeServices(Collection<AttributeService> services) {
        removeSAMLObjects(attributeServices, services);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeAuthorityDescriptor#removeAllAttributeServices()
     */
    public void removeAllAttributeServices() {
        for(AttributeService service : attributeServices){
            removeAttributeService(service);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.AssertionIDRequestDescriptorComp#getAssertionIDRequestServices()
     */
    public UnmodifiableOrderedSet<AssertionIDRequestService> getAssertionIDRequestServices() {
        return new UnmodifiableOrderedSet<AssertionIDRequestService>(assertionIDRequestServices);
    }

    /*
     * @see org.opensaml.saml2.metadata.AssertionIDRequestDescriptorComp#addAssertionIDRequestService(org.opensaml.saml2.metadata.Endpoint)
     */
    public void addAssertionIDRequestService(AssertionIDRequestService service) throws IllegalAddException {
        addSAMLObject(assertionIDRequestServices, service);
    }

    /*
     * @see org.opensaml.saml2.metadata.AssertionIDRequestDescriptorComp#removeAssertionIDRequestService(org.opensaml.saml2.metadata.Endpoint)
     */
    public void removeAssertionIDRequestService(AssertionIDRequestService service) {
        removeSAMLObject(assertionIDRequestServices, service);
    }

    /*
     * @see org.opensaml.saml2.metadata.AssertionIDRequestDescriptorComp#removeAssertionIDRequestServices(java.util.Collection)
     */
    public void removeAssertionIDRequestServices(Collection<AssertionIDRequestService> services) {
        removeSAMLObjects(assertionIDRequestServices, services);
    }

    /*
     * @see org.opensaml.saml2.metadata.AssertionIDRequestDescriptorComp#removeAllAssertionIDRequestServices()
     */
    public void removeAllAssertionIDRequestServices() {
        for(AssertionIDRequestService service : assertionIDRequestServices){
            removeAssertionIDRequestService(service);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#isSupportedNameIDFormat(java.lang.String)
     */
    public boolean isSupportedNameIDFormat(String format) {
        return isSupportedNameIDFormat(buildNameIDFormat(format));
    }
    
    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#isSupportedNameIDFormat(org.opensaml.saml2.metadata.NameIDFormat)
     */
    public boolean isSupportedNameIDFormat(NameIDFormat format){
        return nameFormats.contains(format);
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#getNameIDFormats()
     */
    public UnmodifiableOrderedSet<NameIDFormat> getNameIDFormats() {
        return new UnmodifiableOrderedSet<NameIDFormat>(nameFormats);
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#addNameIDFormat(org.opensaml.saml2.metadata.NameIDFormat)
     */
    public void addNameIDFormat(NameIDFormat format) throws IllegalAddException {
        addSAMLObject(nameFormats, format);
    }
    
    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#addNameIDFormat(java.lang.String)
     */
    public void addNameIDFormat(String format){
        try {
            addNameIDFormat(buildNameIDFormat(format));
        } catch (IllegalAddException e) {
            // unreachable
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#removeNameIDFormat(org.opensaml.saml2.metadata.NameIDFormat)
     */
    public void removeNameIDFormat(NameIDFormat format) {
        removeSAMLObject(nameFormats, format);
    }
    
    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#removeNameIDFormat(java.lang.String)
     */
    public void removeNameIDFormat(String format){
        removeNameIDFormat(buildNameIDFormat(format));
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#removeNameIDFormats(java.util.Collection)
     */
    public void removeNameIDFormats(Collection<NameIDFormat> formats) {
        removeSAMLObjects(nameFormats, formats);
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#removeAllNameIDFormats()
     */
    public void removeAllNameIDFormats() {
        for(NameIDFormat format : nameFormats){
            removeNameIDFormat(format);
        }
    }
    
    /*
     * @see org.opensaml.saml2.metadata.AttributeProfileDescriptorComp#isSupportedAttributeProfile(org.opensaml.saml2.metadata.AttributeProfile)
     */
    public boolean isSupportedAttributeProfile(AttributeProfile profile) {
        return attributeProfiles.contains(profile);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeProfileDescriptorComp#isSupportedAttributeProfile(java.lang.String)
     */
    public boolean isSupportedAttributeProfile(String profileURI) {
        return isSupportedAttributeProfile(buildAttributeProfile(profileURI));
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeProfileDescriptorComp#getAttributeProfiles()
     */
    public UnmodifiableOrderedSet<AttributeProfile> getAttributeProfiles() {
        return new UnmodifiableOrderedSet<AttributeProfile>(attributeProfiles);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeProfileDescriptorComp#addAttributeProfile(org.opensaml.saml2.metadata.AttributeProfile)
     */
    public void addAttributeProfile(AttributeProfile profile) throws IllegalAddException{
        addSAMLObject(attributeProfiles, profile);
    }
    
    /*
     * @see org.opensaml.saml2.metadata.AttributeProfileDescriptorComp#addAttributeProfile(java.lang.String)
     */
    public void addAttributeProfile(String profileURI) {
        try {
            addAttributeProfile(buildAttributeProfile(profileURI));
        } catch (IllegalAddException e) {
            //unreachable
        }
    }
    
    /*
     * @see org.opensaml.saml2.metadata.AttributeProfileDescriptorComp#removeAttributeProfile(org.opensaml.saml2.metadata.AttributeProfile)
     */
    public void removeAttributeProfile(AttributeProfile profile) {
        removeSAMLObject(attributeProfiles, profile);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeProfileDescriptorComp#removeAttributeProfile(java.lang.String)
     */
    public void removeAttributeProfile(String profileURI) {
        removeAttributeProfile(buildAttributeProfile(profileURI));
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeProfileDescriptorComp#removeAttributeProfiles(java.util.Collection)
     */
    public void removeAttributeProfiles(Collection<AttributeProfile> profiles) {
        removeSAMLObjects(attributeProfiles, profiles);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeProfileDescriptorComp#removeAllAttributeProfiles()
     */
    public void removeAllAttributeProfiles() {
        for(AttributeProfile profile : attributeProfiles){
            removeAttributeProfile(profile);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeDescriptorComp#isSupportedAttribute(org.opensaml.saml2.core.Attribute)
     */
    public boolean isSupportedAttribute(Attribute attribute) {
        return attributes.contains(attribute);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeDescriptorComp#getAttributes()
     */
    public UnmodifiableOrderedSet<Attribute> getAttributes() {
        return new UnmodifiableOrderedSet<Attribute>(attributes);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeDescriptorComp#addAttribute(org.opensaml.saml2.core.Attribute)
     */
    public void addAttribute(Attribute attribute) throws IllegalAddException{
        addSAMLObject(attributes, attribute);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeDescriptorComp#removeAttributes(org.opensaml.saml2.core.Attribute)
     */
    public void removeAttributes(Attribute attribute) {
        removeSAMLObject(attributes, attribute);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeDescriptorComp#removeAttributes(java.util.Collection)
     */
    public void removeAttributes(Collection<Attribute> attributes) {
        removeSAMLObjects(this.attributes, attributes);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeDescriptorComp#removeAllAttributes()
     */
    public void removeAllAttributes() {
        for(Attribute attribute : attributes){
            removeAttributes(attribute);
        }
    }
    
    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public UnmodifiableOrderedSet<SAMLObject> getOrderedChildren() {
        OrderedSet<SAMLObject> children = new OrderedSet<SAMLObject>();
        
        children.addAll(super.getOrderedChildren());
        children.addAll(attributeServices);
        children.addAll(assertionIDRequestServices);
        children.addAll(nameFormats);
        children.addAll(attributeProfiles);
        children.addAll(attributes);
        
        return new UnmodifiableOrderedSet<SAMLObject>(children);
    }

    /*
     * @see org.opensaml.common.SAMLObject#equals(org.opensaml.common.SAMLObject)
     */
    public boolean equals(SAMLObject element) {
        // TODO Auto-generated method stub
        return false;
    }
    
    /**
     * Convience method for creating NameIDFormat objects with a given format.
     * 
     * @param format the format
     * 
     * @return the NameIDFormat object with the given format
     */
    protected NameIDFormat buildNameIDFormat(String format){
        SAMLObjectBuilder builder = SAMLObjectBuilderFactory.getInstance().getBuilder(NameIDFormat.QNAME);
        NameIDFormat nameFormat = (NameIDFormat) builder.buildObject();
        nameFormat.setFormat(format);
        
        return nameFormat;
    }
    
    /**
     * Convience method for creating AttributeProfile objects with a given profile URI.
     * 
     * @param profileURI the profile URI
     * 
     * @return the AttributeProfile object with the given profile URI
     */
    protected AttributeProfile buildAttributeProfile(String profileURI){
        SAMLObjectBuilder builder = SAMLObjectBuilderFactory.getInstance().getBuilder(AttributeProfile.QNAME);
        AttributeProfile attributeProfile = (AttributeProfile) builder.buildObject();
        attributeProfile.setProfileURI(profileURI);
        
        return attributeProfile;
    }
}