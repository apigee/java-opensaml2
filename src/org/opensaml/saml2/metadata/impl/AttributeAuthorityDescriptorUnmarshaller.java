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

import org.opensaml.common.SAMLConfig;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml2.metadata.AttributeAuthorityDescriptor;
import org.opensaml.saml2.metadata.AttributeProfile;
import org.opensaml.saml2.metadata.AttributeService;
import org.opensaml.saml2.metadata.NameIDFormat;
import org.opensaml.xml.IllegalAddException;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread safe {@link org.opensaml.common.io.Unmarshaller} for
 * {@link org.opensaml.saml2.metadata.AttributeAuthorityDescriptor}s.
 */
public class AttributeAuthorityDescriptorUnmarshaller extends RoleDescriptorUnmarshaller {

    /**
     * Constructor
     */
    public AttributeAuthorityDescriptorUnmarshaller() {
        super(SAMLConstants.SAML20MD_NS, AttributeAuthorityDescriptor.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#processChildElement(org.opensaml.common.SAMLObject,
     *      org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(SAMLObject parentElement, SAMLObject childElement)
            throws UnmarshallingException, UnknownElementException {
        super.processChildElement(parentElement, childElement);

        AttributeAuthorityDescriptor descriptor = (AttributeAuthorityDescriptor) parentElement;

        try {
            if (childElement instanceof AttributeService) {
                descriptor.addAttributeService((AttributeService) childElement);
            } else if (childElement instanceof AssertionIDRequestService) {
                descriptor.addAssertionIDRequestService((AssertionIDRequestService) childElement);
            } else if (childElement instanceof NameIDFormat) {
                descriptor.addNameIDFormat((NameIDFormat) childElement);
            } else if (childElement instanceof AttributeProfile) {
                descriptor.addAttributeProfile((AttributeProfile) childElement);
            } else if (childElement instanceof Attribute) {
                descriptor.addAttribute((Attribute) childElement);
            } else {
                if (!SAMLConfig.ignoreUnknownElements()) {
                    throw new UnknownElementException(childElement.getElementQName()
                            + " is an unknown child element of " + parentElement.getElementQName());
                }
            }
        } catch (IllegalAddException e) {
            // should never get here, but just in case
            throw new UnmarshallingException(e);
        }
    }
}