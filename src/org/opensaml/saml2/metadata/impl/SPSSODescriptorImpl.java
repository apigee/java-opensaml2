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

    /*
     * @see org.opensaml.saml2.metadata.SPSSODescriptor#isAuthnRequestsSigned()
     */
    public XSBooleanValue authnRequestsSigned() {
        return authnRequestSigned;
    }

    /*
     * @see org.opensaml.saml2.metadata.SPSSODescriptor#setAuthnRequestsSigned(Boolean)
     */
    public void setAuthnRequestsSigned(XSBooleanValue isSigned) {
        authnRequestSigned = prepareForAssignment(authnRequestSigned, isSigned);
    }

    /*
     * @see org.opensaml.saml2.metadata.SPSSODescriptor#wantAssertionsSigned()
     */
    public XSBooleanValue wantAssertionsSigned() {
        return assertionSigned;
    }

    /*
     * @see org.opensaml.saml2.metadata.SPSSODescriptor#setWantAssertionsSigned(Boolean)
     */
    public void setWantAssertionsSigned(XSBooleanValue wantAssestionSigned) {
        this.assertionSigned = prepareForAssignment(this.assertionSigned, wantAssestionSigned);
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