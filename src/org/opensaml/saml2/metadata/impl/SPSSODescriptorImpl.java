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
import org.opensaml.xml.schema.XSBooleanValue;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of {@link org.opensaml.saml2.metadata.SPSSODescriptor}
 */
public class SPSSODescriptorImpl extends SSODescriptorImpl implements SPSSODescriptor {

    /** value for isAuthnRequestSigned attribute */
    private XSBooleanValue authnRequestSigned;

    /** value for the want assertion signed attribute */
    private XSBooleanValue assertionSigned;

    /** AssertionConsumerService children */
    private final XMLObjectChildrenList<AssertionConsumerService> assertionConsumerServices;

    /** AttributeConsumingService children */
    private final XMLObjectChildrenList<AttributeConsumingService> attributeConsumingServices;

    /**
     * Constructor
     * 
     * @param namespaceURI
     * @param elementLocalName
     * @param namespacePrefix
     */
    protected SPSSODescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        assertionConsumerServices = new XMLObjectChildrenList<AssertionConsumerService>(this);
        attributeConsumingServices = new XMLObjectChildrenList<AttributeConsumingService>(this);
    }
    
    /** {@inheritDoc} */
    public Boolean isAuthnRequestSigned(){
        if(authnRequestSigned != null){
            return authnRequestSigned.getValue();
        }
        
        return null;
    }

    /** {@inheritDoc} */
    public XSBooleanValue isAuthnRequestsSignedXSBoolean() {
        return authnRequestSigned;
    }
    
    /** {@inheritDoc} */
    public void setAuthnRequestsSigned(Boolean newIsSigned) {
        if(newIsSigned == null){
            authnRequestSigned = prepareForAssignment(authnRequestSigned, new XSBooleanValue(newIsSigned, false));
        }else{
            authnRequestSigned = prepareForAssignment(authnRequestSigned, null);
        }
    }

    /** {@inheritDoc} */
    public void setAuthnRequestsSigned(XSBooleanValue isSigned) {
        authnRequestSigned = prepareForAssignment(authnRequestSigned, isSigned);
    }
    
    /** {@inheritDoc} */
    public Boolean getWantAssertionsSigned(){
        if(assertionSigned != null){
            return assertionSigned.getValue();
        }
        
        return null;
    }

    /** {@inheritDoc} */
    public XSBooleanValue getWantAssertionsSignedXSBoolean() {
        return assertionSigned;
    }
    
    /** {@inheritDoc} */
    public void setWantAssertionsSigned(Boolean wantAssestionSigned) {
        if(wantAssestionSigned == null){
            assertionSigned = prepareForAssignment(assertionSigned, new XSBooleanValue(wantAssestionSigned, false));
        }else{
            assertionSigned = prepareForAssignment(assertionSigned, null);
        }
    }

    /** {@inheritDoc} */
    public void setWantAssertionsSigned(XSBooleanValue wantAssestionSigned) {
        this.assertionSigned = prepareForAssignment(this.assertionSigned, wantAssestionSigned);
    }

    /** {@inheritDoc} */
    public List<AssertionConsumerService> getAssertionConsumerServices() {
        return assertionConsumerServices;
    }
    
    /** {@inheritDoc} */
    public AssertionConsumerService getDefaultAssertionConsumerService(){
        for(AssertionConsumerService service : assertionConsumerServices){
            if(service.isDefault()){
                return service;
            }
        }
        
        return null;
    }

    /** {@inheritDoc} */
    public List<AttributeConsumingService> getAttributeConsumingServices() {
        return attributeConsumingServices;
    }
    
    /** {@inheritDoc} */
    public AttributeConsumingService getDefaultAttributeConsumingService(){
        for(AttributeConsumingService service : attributeConsumingServices){
            if(service.isDefault()){
                return service;
            }
        }
        
        return null;
    }

    /** {@inheritDoc} */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

        children.addAll(super.getOrderedChildren());
        children.addAll(assertionConsumerServices);
        children.addAll(attributeConsumingServices);

        return Collections.unmodifiableList(children);
    }
}