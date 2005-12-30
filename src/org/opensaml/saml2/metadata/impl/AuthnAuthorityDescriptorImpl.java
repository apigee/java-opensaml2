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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml2.metadata.AuthnAuthorityDescriptor;
import org.opensaml.saml2.metadata.AuthnQueryService;
import org.opensaml.saml2.metadata.NameIDFormat;
import org.opensaml.xml.IllegalAddException;

/**
 * Concreate implementation of {@link org.opensaml.saml2.metadata.AuthnAuthorityDescriptor}
 */
public class AuthnAuthorityDescriptorImpl extends RoleDescriptorImpl implements AuthnAuthorityDescriptor {

    /** validUntil attribute */
    private GregorianCalendar validUntil;
    
    /** cacheDurection attribute */
    private Long cacheDuration;
    
    /** AuthnQueryService endpoints */
    private ArrayList<AuthnQueryService> authnQueryServices = new ArrayList<AuthnQueryService>();
    
    /** AuthnQueryService endpoints */
    private ArrayList<AssertionIDRequestService> assertionIDRequestServices = new ArrayList<AssertionIDRequestService>();
    
    /** NameID formats supported by this descriptor */
    private ArrayList<NameIDFormat> nameIDFormats= new ArrayList<NameIDFormat>();
    
    /**
     * Constrcutor
     */
    public AuthnAuthorityDescriptorImpl() {
        super(AuthnAuthorityDescriptor.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20MD_PREFIX);
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
     * @see org.opensaml.saml2.metadata.AuthnAuthorityDescriptor#getAuthnQueryServices()
     */
    public List<AuthnQueryService> getAuthnQueryServices() {
        return Collections.unmodifiableList(authnQueryServices);
    }

    /*
     * @see org.opensaml.saml2.metadata.AuthnAuthorityDescriptor#addAuthnQueryService(org.opensaml.saml2.metadata.Endpoint)
     */
    public void addAuthnQueryService(AuthnQueryService service) throws IllegalAddException {
        addXMLObject(authnQueryServices, service);
    }

    /*
     * @see org.opensaml.saml2.metadata.AuthnAuthorityDescriptor#removeAuthnQueryService(org.opensaml.saml2.metadata.Endpoint)
     */
    public void removeAuthnQueryService(AuthnQueryService service) {
        removeXMLObject(authnQueryServices, service);
    }

    /*
     * @see org.opensaml.saml2.metadata.AuthnAuthorityDescriptor#removeAuthnQueryServices(java.util.Set)
     */
    public void removeAuthnQueryServices(Collection<AuthnQueryService> services) {
        removeXMLObjects(authnQueryServices, services);
    }

    /*
     * @see org.opensaml.saml2.metadata.AuthnAuthorityDescriptor#removeAllAuthnQueryServices()
     */
    public void removeAllAuthnQueryServices() {
        for(AuthnQueryService service : authnQueryServices) {
            removeAuthnQueryService(service);
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
     * @see org.opensaml.saml2.metadata.AssertionIDRequestDescriptorComp#removeAssertionIDRequestServices(java.util.Set)
     */
    public void removeAssertionIDRequestServices(Collection<AssertionIDRequestService> services) {
        removeXMLObjects(assertionIDRequestServices, services);
    }

    /*
     * @see org.opensaml.saml2.metadata.AssertionIDRequestDescriptorComp#removeAllAssertionIDRequestServices()
     */
    public void removeAllAssertionIDRequestServices() {
        for(AssertionIDRequestService service : assertionIDRequestServices) {
            removeAssertionIDRequestService(service);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#isSupportedNameIDFormat(org.opensaml.saml2.metadata.NameIDFormat)
     */
    public boolean isSupportedNameIDFormat(NameIDFormat format) {
        return nameIDFormats.contains(format);
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#getNameIDFormats()
     */
    public List<NameIDFormat> getNameIDFormats() {
        return Collections.unmodifiableList(nameIDFormats);
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#addNameIDFormat(java.lang.String)
     */
    public void addNameIDFormat(NameIDFormat format) throws IllegalAddException{
        addXMLObject(nameIDFormats, format);
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#removeNameIDFormat(java.lang.String)
     */
    public void removeNameIDFormat(NameIDFormat format) {
        removeXMLObject(nameIDFormats, format);
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#removeNameIDFormats(java.util.Set)
     */
    public void removeNameIDFormats(Collection<NameIDFormat> formats) {
        removeXMLObjects(nameIDFormats, formats);
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#removeAllNameIDFormats()
     */
    public void removeAllNameIDFormats() {
        for(NameIDFormat format : nameIDFormats) {
            removeNameIDFormat(format);
        }
    }
    
    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren(){
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        
        children.addAll(super.getOrderedChildren());
        children.addAll(authnQueryServices);
        children.addAll(assertionIDRequestServices);
        children.addAll(nameIDFormats);
        
        return Collections.unmodifiableList(children);
    }
}