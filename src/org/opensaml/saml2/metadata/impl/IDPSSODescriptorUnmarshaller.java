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

import org.opensaml.common.SAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml2.metadata.AttributeProfile;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.NameIDMappingService;
import org.opensaml.saml2.metadata.SingleSignOnService;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * 
 */
public class IDPSSODescriptorUnmarshaller extends SSODescriptorUnmarshaller {

    /**
     * Constructor
     */
    public IDPSSODescriptorUnmarshaller() {
        super(SAMLConstants.SAML20MD_NS, IDPSSODescriptor.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#addChildElement(org.opensaml.saml2.common.impl.AbstractSAMLElement,
     *      org.opensaml.saml2.common.impl.AbstractSAMLElement)
     */
    protected void processChildElement(SAMLObject parentObject, SAMLObject childObject) throws UnmarshallingException {
        IDPSSODescriptor descriptor = (IDPSSODescriptor) parentObject;

        if (childObject instanceof SingleSignOnService) {
            descriptor.getSingleSignOnServices().add((SingleSignOnService) childObject);
        } else if (childObject instanceof NameIDMappingService) {
            descriptor.getNameIDMappingServices().add((NameIDMappingService) childObject);
        } else if (childObject instanceof AssertionIDRequestService) {
            descriptor.getAssertionIDRequestServices().add((AssertionIDRequestService) childObject);
        } else if (childObject instanceof AttributeProfile) {
            descriptor.getAttributeProfiles().add((AttributeProfile) childObject);
        } else if (childObject instanceof Attribute) {
            descriptor.getAttributes().add((Attribute) childObject);
        } else {
            super.processChildElement(parentObject, childObject);
        }
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#addAttribute(org.opensaml.saml2.common.impl.AbstractSAMLElement,
     *      java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlObject, String attributeName, String attributeValue) throws UnmarshallingException{
        IDPSSODescriptor descriptor = (IDPSSODescriptor) samlObject;

        if (attributeName.equals(IDPSSODescriptor.WANT_AUTHN_REQ_SIGNED_ATTRIB_NAME)) {
            descriptor.setWantAuthnRequestSigned(new Boolean(Boolean.parseBoolean(attributeValue)));
        } else {
            super.processAttribute(samlObject, attributeName, attributeValue);
        }
    }
}