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
import org.opensaml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml2.metadata.AttributeConsumingService;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml2.metadata.SPSSODescriptor}
 * objects.
 */
public class SPSSODescriptorUnmarshaller extends SSODescriptorUnmarshaller {

    /**
     * Constructor
     */
    public SPSSODescriptorUnmarshaller() {
        super(SAMLConstants.SAML20MD_NS, SPSSODescriptor.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#addChildElement(org.opensaml.saml2.common.impl.AbstractSAMLElement,
     *      org.opensaml.saml2.common.impl.AbstractSAMLElement)
     */
    protected void processChildElement(SAMLObject parentSAMLObject, SAMLObject childSAMLObject)
            throws UnmarshallingException {
        SPSSODescriptor descriptor = (SPSSODescriptor) parentSAMLObject;

        if (childSAMLObject instanceof AssertionConsumerService) {
            descriptor.getAssertionConsumerServices().add((AssertionConsumerService) childSAMLObject);
        } else if (childSAMLObject instanceof AttributeConsumingService) {
            descriptor.getAttributeConsumingServices().add((AttributeConsumingService) childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#addAttribute(org.opensaml.saml2.common.impl.AbstractSAMLElement,
     *      java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlObject, String attributeName, String attributeValue)
            throws UnmarshallingException {
        SPSSODescriptor descriptor = (SPSSODescriptor) samlObject;

        if (attributeName.equals(SPSSODescriptor.AUTH_REQUETS_SIGNED_ATTRIB_NAME)) {
            descriptor.setAuthnRequestsSigned(Boolean.valueOf(attributeValue));
        } else if (attributeName.equals(SPSSODescriptor.WANT_ASSERTIONS_SIGNED_ATTRIB_NAME)) {
            descriptor.setWantAssertionsSigned(Boolean.valueOf(attributeValue));
        } else {
            super.processAttribute(samlObject, attributeName, attributeValue);
        }
    }
}