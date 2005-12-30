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
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml2.metadata.AttributeAuthorityDescriptor;
import org.opensaml.saml2.metadata.AttributeProfile;
import org.opensaml.saml2.metadata.AttributeService;
import org.opensaml.saml2.metadata.NameIDFormat;
import org.opensaml.xml.IllegalAddException;

/**
 * A concrete implementation of {@link org.opensaml.saml2.metadata.AttributeAuthorityDescriptor}.
 */
public class AttributeAuthorityDescriptorImpl extends RoleDescriptorImpl implements AttributeAuthorityDescriptor {

    /** validUntil attribute */
    private GregorianCalendar validUntil;
    
    /** cacheDurection attribute */
    private Long cacheDuration;
    
    /** Attribte query endpoints */
    private ArrayList<AttributeService> attributeServices = new ArrayList<AttributeService>();
    
    /** Assertion request endpoints */
    private ArrayList<AssertionIDRequestService> assertionIDRequestServices = new ArrayList<AssertionIDRequestService>();
    
    /** Supported NameID formats */
    private ArrayList<NameIDFormat> nameFormats = new ArrayList<NameIDFormat>();
    
    /** Supported attribute profiles */
    private ArrayList<AttributeProfile> attributeProfiles = new ArrayList<AttributeProfile>();
    
    /** Supported attribute */
    private ArrayList<Attribute> attributes = new ArrayList<Attribute>();
    
    /**
     * Constructor
     */
    public AttributeAuthorityDescriptorImpl(){
        super(AttributeAuthorityDescriptor.LOCAL_NAME);
        setElementNamespaceAndPrefix(SAMLConstants.SAML20MD_NS, SAMLConstants.SAML20MD_PREFIX);
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
     * @see org.opensaml.saml2.metadata.AttributeAuthorityDescriptor#getAttributeServices()
     */
    public List<AttributeService> getAttributeServices() {
        return Collections.unmodifiableList(attributeServices);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeAuthorityDescriptor#addAttributeService(org.opensaml.saml2.metadata.Endpoint)
     */
    public void addAttributeService(AttributeService service) throws IllegalAddException{
        addXMLObject(attributeServices, service);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeAuthorityDescriptor#removeAttributeService(org.opensaml.saml2.metadata.Endpoint)
     */
    public void removeAttributeService(AttributeService service) {
        removeXMLObject(attributeServices, service);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeAuthorityDescriptor#removeAttributeServices(java.util.Collection)
     */
    public void removeAttributeServices(Collection<AttributeService> services) {
        removeXMLObjects(attributeServices, services);
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
    public List<AssertionIDRequestService> getAssertionIDRequestServices() {
        return Collections.unmodifiableList(assertionIDRequestServices);
    }

    /*
     * @see org.opensaml.saml2.metadata.AssertionIDRequestDescriptorComp#addAssertionIDRequestService(org.opensaml.saml2.metadata.Endpoint)
     */
    public void addAssertionIDRequestService(AssertionIDRequestService service) throws IllegalAddException {
        addXMLObject(assertionIDRequestServices, service);
    }

    /*
     * @see org.opensaml.saml2.metadata.AssertionIDRequestDescriptorComp#removeAssertionIDRequestService(org.opensaml.saml2.metadata.Endpoint)
     */
    public void removeAssertionIDRequestService(AssertionIDRequestService service) {
        removeXMLObject(assertionIDRequestServices, service);
    }

    /*
     * @see org.opensaml.saml2.metadata.AssertionIDRequestDescriptorComp#removeAssertionIDRequestServices(java.util.Collection)
     */
    public void removeAssertionIDRequestServices(Collection<AssertionIDRequestService> services) {
        removeXMLObjects(assertionIDRequestServices, services);
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
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#isSupportedNameIDFormat(org.opensaml.saml2.metadata.NameIDFormat)
     */
    public boolean isSupportedNameIDFormat(NameIDFormat format){
        return nameFormats.contains(format);
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#getNameIDFormats()
     */
    public List<NameIDFormat> getNameIDFormats() {
        return Collections.unmodifiableList(nameFormats);
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#addNameIDFormat(org.opensaml.saml2.metadata.NameIDFormat)
     */
    public void addNameIDFormat(NameIDFormat format) throws IllegalAddException {
        addXMLObject(nameFormats, format);
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#removeNameIDFormat(org.opensaml.saml2.metadata.NameIDFormat)
     */
    public void removeNameIDFormat(NameIDFormat format) {
        removeXMLObject(nameFormats, format);
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#removeNameIDFormats(java.util.Collection)
     */
    public void removeNameIDFormats(Collection<NameIDFormat> formats) {
        removeXMLObjects(nameFormats, formats);
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
     * @see org.opensaml.saml2.metadata.AttributeProfileDescriptorComp#getAttributeProfiles()
     */
    public List<AttributeProfile> getAttributeProfiles() {
        return Collections.unmodifiableList(attributeProfiles);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeProfileDescriptorComp#addAttributeProfile(org.opensaml.saml2.metadata.AttributeProfile)
     */
    public void addAttributeProfile(AttributeProfile profile) throws IllegalAddException{
        addXMLObject(attributeProfiles, profile);
    }
    
    /*
     * @see org.opensaml.saml2.metadata.AttributeProfileDescriptorComp#removeAttributeProfile(org.opensaml.saml2.metadata.AttributeProfile)
     */
    public void removeAttributeProfile(AttributeProfile profile) {
        removeXMLObject(attributeProfiles, profile);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeProfileDescriptorComp#removeAttributeProfiles(java.util.Collection)
     */
    public void removeAttributeProfiles(Collection<AttributeProfile> profiles) {
        removeXMLObjects(attributeProfiles, profiles);
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
    public List<Attribute> getAttributes() {
        return Collections.unmodifiableList(attributes);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeDescriptorComp#addAttribute(org.opensaml.saml2.core.Attribute)
     */
    public void addAttribute(Attribute attribute) throws IllegalAddException{
        addXMLObject(attributes, attribute);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeDescriptorComp#removeAttributes(org.opensaml.saml2.core.Attribute)
     */
    public void removeAttributes(Attribute attribute) {
        removeXMLObject(attributes, attribute);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeDescriptorComp#removeAttributes(java.util.Collection)
     */
    public void removeAttributes(Collection<Attribute> attributes) {
        removeXMLObjects(this.attributes, attributes);
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
    public List<SAMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        
        children.addAll(super.getOrderedChildren());
        children.addAll(attributeServices);
        children.addAll(assertionIDRequestServices);
        children.addAll(nameFormats);
        children.addAll(attributeProfiles);
        children.addAll(attributes);
        
        return Collections.unmodifiableList(children);
    }
}