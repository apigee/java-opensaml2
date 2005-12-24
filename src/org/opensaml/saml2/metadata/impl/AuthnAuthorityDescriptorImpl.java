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

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.SAMLObjectBuilderFactory;
import org.opensaml.common.util.OrderedSet;
import org.opensaml.common.util.UnmodifiableOrderedSet;
import org.opensaml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml2.metadata.AuthnAuthorityDescriptor;
import org.opensaml.saml2.metadata.AuthnQueryService;
import org.opensaml.saml2.metadata.NameIDFormat;

/**
 * Concreate implementation of {@link org.opensaml.saml2.metadata.AuthnAuthorityDescriptor}
 */
public class AuthnAuthorityDescriptorImpl extends RoleDescriptorImpl implements AuthnAuthorityDescriptor {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 422459752889774280L;

    /** AuthnQueryService endpoints */
    private OrderedSet<AuthnQueryService> authnQueryServices = new OrderedSet<AuthnQueryService>();
    
    /** AuthnQueryService endpoints */
    private OrderedSet<AssertionIDRequestService> assertionIDRequestServices = new OrderedSet<AssertionIDRequestService>();
    
    /** NameID formats supported by this descriptor */
    private OrderedSet<NameIDFormat> nameIDFormats= new OrderedSet<NameIDFormat>();
    
    /**
     * Constrcutor
     */
    public AuthnAuthorityDescriptorImpl() {
        super();
        setQName(AuthnAuthorityDescriptor.QNAME);
    }
    
    /*
     * @see org.opensaml.saml2.metadata.AuthnAuthorityDescriptor#getAuthnQueryServices()
     */
    public UnmodifiableOrderedSet<AuthnQueryService> getAuthnQueryServices() {
        return new UnmodifiableOrderedSet<AuthnQueryService>(authnQueryServices);
    }

    /*
     * @see org.opensaml.saml2.metadata.AuthnAuthorityDescriptor#addAuthnQueryService(org.opensaml.saml2.metadata.Endpoint)
     */
    public void addAuthnQueryService(AuthnQueryService service) throws IllegalAddException {
        addSAMLObject(authnQueryServices, service);
    }

    /*
     * @see org.opensaml.saml2.metadata.AuthnAuthorityDescriptor#removeAuthnQueryService(org.opensaml.saml2.metadata.Endpoint)
     */
    public void removeAuthnQueryService(AuthnQueryService service) {
        removeSAMLObject(authnQueryServices, service);
    }

    /*
     * @see org.opensaml.saml2.metadata.AuthnAuthorityDescriptor#removeAuthnQueryServices(java.util.Set)
     */
    public void removeAuthnQueryServices(Collection<AuthnQueryService> services) {
        removeSAMLObjects(authnQueryServices, services);
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
     * @see org.opensaml.saml2.metadata.AssertionIDRequestDescriptorComp#removeAssertionIDRequestServices(java.util.Set)
     */
    public void removeAssertionIDRequestServices(Collection<AssertionIDRequestService> services) {
        removeSAMLObjects(assertionIDRequestServices, services);
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
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#isSupportedNameIDFormat(java.lang.String)
     */
    public boolean isSupportedNameIDFormat(String format) {
        return isSupportedNameIDFormat(buildNameIDFormat(format));
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#getNameIDFormats()
     */
    public UnmodifiableOrderedSet<NameIDFormat> getNameIDFormats() {
        return new UnmodifiableOrderedSet<NameIDFormat>(nameIDFormats);
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#addNameIDFormat(java.lang.String)
     */
    public void addNameIDFormat(NameIDFormat format) throws IllegalAddException{
        addSAMLObject(nameIDFormats, format);
    }
    
    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#addNameIDFormat(java.lang.String)
     */
    public void addNameIDFormat(String format){
        try {
            addNameIDFormat(buildNameIDFormat(format));
        } catch (IllegalAddException e) {
            //unreachable
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#removeNameIDFormat(java.lang.String)
     */
    public void removeNameIDFormat(NameIDFormat format) {
        removeSAMLObject(nameIDFormats, format);
    }
    
    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#removeNameIDFormat(java.lang.String)
     */
    public void removeNameIDFormat(String format) {
        removeNameIDFormat(buildNameIDFormat(format));
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#removeNameIDFormats(java.util.Set)
     */
    public void removeNameIDFormats(Collection<NameIDFormat> formats) {
        removeSAMLObjects(nameIDFormats, formats);
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
    public UnmodifiableOrderedSet<SAMLObject> getOrderedChildren(){
        OrderedSet<SAMLObject> children = new OrderedSet<SAMLObject>();
        
        children.addAll(super.getOrderedChildren());
        children.addAll(authnQueryServices);
        children.addAll(assertionIDRequestServices);
        children.addAll(nameIDFormats);
        
        return new UnmodifiableOrderedSet<SAMLObject>(children);
    }
    
    /*
     * @see org.opensaml.common.SAMLObject#equals(org.opensaml.common.SAMLObject)
     */
    public boolean equals(SAMLObject obj) {
        //TODO
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
}