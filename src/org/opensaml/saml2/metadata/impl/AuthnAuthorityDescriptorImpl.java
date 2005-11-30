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

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.metadata.AuthnAuthorityDescriptor;
import org.opensaml.saml2.metadata.Endpoint;
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
    private Set<Endpoint> authnQueryEndpoints = new LinkedHashSet<Endpoint>();
    
    /** AuthnQueryService endpoints */
    private Set<Endpoint> assertionIDRequestEndpoints = new LinkedHashSet<Endpoint>();
    
    /** NameID formats supported by this descriptor */
    private Set<NameIDFormat> nameIDFormats= new HashSet<NameIDFormat>();
    
    /**
     * Constrcutor
     */
    public AuthnAuthorityDescriptorImpl() {
        super();
    }
    
    /*
     * @see org.opensaml.saml2.metadata.AuthnAuthorityDescriptor#getAuthnQueryServices()
     */
    public Set<Endpoint> getAuthnQueryServices() {
        return Collections.unmodifiableSet(authnQueryEndpoints);
    }

    /*
     * @see org.opensaml.saml2.metadata.AuthnAuthorityDescriptor#addAuthnQueryService(org.opensaml.saml2.metadata.Endpoint)
     */
    public void addAuthnQueryService(Endpoint service) throws IllegalAddException {
        if(service != null && !authnQueryEndpoints.contains(service)) {
            if(service.hasParent()) {
                throw new IllegalAddException("Can not add an endpoint owned by another element");
            }

            releaseThisandParentDOM();
            service.setParent(this);
            authnQueryEndpoints.add(service);                
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.AuthnAuthorityDescriptor#removeAuthnQueryService(org.opensaml.saml2.metadata.Endpoint)
     */
    public void removeAuthnQueryService(Endpoint service) {
        if(service != null && authnQueryEndpoints.contains(service)) {
            releaseThisandParentDOM();
            authnQueryEndpoints.remove(service);
            service.setParent(null);
    }
    }

    /*
     * @see org.opensaml.saml2.metadata.AuthnAuthorityDescriptor#removeAuthnQueryServices(java.util.Set)
     */
    public void removeAuthnQueryServices(Set<Endpoint> services) {
        if(services != null) {
            for(Endpoint service : services) {
                removeAuthnQueryService(service);
            }
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.AuthnAuthorityDescriptor#removeAllAuthnQueryServices()
     */
    public void removeAllAuthnQueryServices() {
        for(Endpoint service : authnQueryEndpoints) {
            removeAuthnQueryService(service);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.AssertionIDRequestDescriptorComp#getAssertionIDRequestServices()
     */
    public Set<Endpoint> getAssertionIDRequestServices() {
        return Collections.unmodifiableSet(assertionIDRequestEndpoints);
    }

    /*
     * @see org.opensaml.saml2.metadata.AssertionIDRequestDescriptorComp#addAssertionIDRequestService(org.opensaml.saml2.metadata.Endpoint)
     */
    public void addAssertionIDRequestService(Endpoint service) throws IllegalAddException {
        if(service != null && !assertionIDRequestEndpoints.contains(service)) {
            if(service.hasParent()) {
                throw new IllegalAddException("Can not add an endpoint owned by another element");
            }
        
            releaseThisandParentDOM();
            service.setParent(this);
            assertionIDRequestEndpoints.add(service);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.AssertionIDRequestDescriptorComp#removeAssertionIDRequestService(org.opensaml.saml2.metadata.Endpoint)
     */
    public void removeAssertionIDRequestService(Endpoint service) {
        if(service != null && assertionIDRequestEndpoints.contains(service)) {
            releaseThisandParentDOM();
            assertionIDRequestEndpoints.remove(service);
            service.setParent(null);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.AssertionIDRequestDescriptorComp#removeAssertionIDRequestServices(java.util.Set)
     */
    public void removeAssertionIDRequestServices(Set<Endpoint> services) {
        if(services != null) {
            for(Endpoint service : services) {
                removeAssertionIDRequestService(service);
            }
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.AssertionIDRequestDescriptorComp#removeAllAssertionIDRequestServices()
     */
    public void removeAllAssertionIDRequestServices() {
        for(Endpoint service : assertionIDRequestEndpoints) {
            removeAssertionIDRequestService(service);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#isSupportedNameIDFormat(java.lang.String)
     */
    public boolean isSupportedNameIDFormat(String format) {
        return nameIDFormats.contains(format);
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#getNameIDFormats()
     */
    public Set<NameIDFormat> getNameIDFormats() {
        return Collections.unmodifiableSet(nameIDFormats);
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#addNameIDFormat(java.lang.String)
     */
    public void addNameIDFormat(NameIDFormat format) {
        if(!nameIDFormats.contains(format)) {
            releaseThisandParentDOM();
            format.setParent(this);
            nameIDFormats.add(format);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#removeNameIDFormat(java.lang.String)
     */
    public void removeNameIDFormat(NameIDFormat format) {
        if(format != null && nameIDFormats.contains(format)) {
            releaseThisandParentDOM();
            nameIDFormats.remove(format);
            format.setParent(null);
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.NameIDFormatDescriptorComp#removeNameIDFormats(java.util.Set)
     */
    public void removeNameIDFormats(Set<NameIDFormat> formats) {
        if(formats != null) {
            for(NameIDFormat format : formats) {
                removeNameIDFormat(format);
            }
        }
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
    public Set<SAMLObject> getOrderedChildren(){
        Set<SAMLObject> children = super.getOrderedChildren();
        
        for(Endpoint service : authnQueryEndpoints) {
            children.add(service);
        }
        
        for(Endpoint service : assertionIDRequestEndpoints) {
            children.add(service);
        }
        
        for(NameIDFormat nameIDFormat : nameIDFormats) {
            children.add(nameIDFormat);
        }
        
        return children;
    }
    
    /*
     * @see org.opensaml.common.SAMLObject#equals(org.opensaml.common.SAMLObject)
     */
    public boolean equals(SAMLObject obj) {
        //TODO
        return false;
    }
}