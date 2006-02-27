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
import java.util.Collections;
import java.util.List;

import org.opensaml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml2.metadata.AttributeConsumingService;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of {@link org.opensaml.saml2.metadata.SPSSODescriptor}
 */
public class SPSSODescriptorImpl extends SSODescriptorImpl implements SPSSODescriptor {

    /** value for isAuthnRequestSigned attribute */
    private Boolean authnRequestSigned;
    
    /** value for the want assertion signed attribute */
    private Boolean wantAssertionSigned;
    
    /** AssertionConsumerService children */
    private XMLObjectChildrenList<AssertionConsumerService> assertionConsumerServices;
    
    /** AttributeConsumingService children */
    private XMLObjectChildrenList<AttributeConsumingService> attributeConsumingServices;
    
    /**
     * Constructor
     */
    protected SPSSODescriptorImpl() {
        super(SPSSODescriptor.LOCAL_NAME);
        
        assertionConsumerServices = new XMLObjectChildrenList<AssertionConsumerService>(this);
        attributeConsumingServices = new XMLObjectChildrenList<AttributeConsumingService>(this);
    }
    
    /*
     * @see org.opensaml.saml2.metadata.SPSSODescriptor#isAuthnRequestsSigned()
     */
    public boolean authnRequestsSigned() {
        if(authnRequestSigned != null) {
            return authnRequestSigned;
        }else {
            return false;
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.SPSSODescriptor#setAuthnRequestsSigned(Boolean)
     */
    public void setAuthnRequestsSigned(Boolean isSigned) {
        authnRequestSigned = prepareForAssignment(authnRequestSigned, isSigned);
    }

    /*
     * @see org.opensaml.saml2.metadata.SPSSODescriptor#wantAssertionsSigned()
     */
    public boolean wantAssertionsSigned() {
        if(wantAssertionSigned != null) {
            return wantAssertionSigned;
        }else {
            return false;
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.SPSSODescriptor#setWantAssertionsSigned(Boolean)
     */
    public void setWantAssertionsSigned(Boolean wantAssestionSigned) {
        this.wantAssertionSigned = prepareForAssignment(this.wantAssertionSigned, wantAssestionSigned);
    }

    /*
     * @see org.opensaml.saml2.metadata.SPSSODescriptor#getAssertionConsumerServices()
     */
    public List<AssertionConsumerService> getAssertionConsumerServices() {
        return assertionConsumerServices;
    }

    /*
     * @see org.opensaml.saml2.metadata.SPSSODescriptor#getAttributeConsumingServices()
     */
    public List<AttributeConsumingService> getAttributeConsumingServices() {
        return attributeConsumingServices;
    }
    
    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        
        children.addAll(super.getOrderedChildren());
        children.addAll(assertionConsumerServices);
        children.addAll(attributeConsumingServices);
        
        return Collections.unmodifiableList(children);
    }
}