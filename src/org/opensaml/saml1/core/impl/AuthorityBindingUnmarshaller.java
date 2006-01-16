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
package org.opensaml.saml1.core.impl;

import org.apache.log4j.Logger;
import org.opensaml.common.SAMLConfig;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.AuthorityBinding;
import org.opensaml.xml.io.UnmarshallingException;

/**
 *  A thread-safe {@link org.opensaml.xml.io.Unmarshaller} for {@link org.opensaml.saml1.core.AuthorityBinding} objects.
 */
public class AuthorityBindingUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /**
     * Constructor
     */
    public AuthorityBindingUnmarshaller() {
        super(SAMLConstants.SAML1_NS, AuthorityBinding.LOCAL_NAME);
    }

    /** Logger */
    private static Logger log = Logger.getLogger(AuthorityBindingUnmarshaller.class);

    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processChildElement(org.opensaml.common.SAMLObject, org.opensaml.common.SAMLObject)
     */
    @Override
    protected void processChildElement(SAMLObject parentElement, SAMLObject childElement)
            throws UnmarshallingException, UnknownElementException {

        // No Children
        
        log.error(childElement.getElementQName() + " is not a supported element for AuthorityBinding objects");
        if (!SAMLConfig.ignoreUnknownElements()) {
            throw new UnknownElementException(childElement.getElementQName()
                    + " is not a supported element for AuthorityBinding objects");
        }

    }

    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processAttribute(org.opensaml.common.SAMLObject, java.lang.String, java.lang.String)
     */
    @Override
    protected void processAttribute(SAMLObject samlElement, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {
        
        AuthorityBinding authorityBinding = (AuthorityBinding) samlElement;
        
        if (AuthorityBinding.AUTHORITYKIND_ATTRIB_NAME.equals(attributeName)) {
            authorityBinding.setAuthorityKind(attributeValue);
        } else if (AuthorityBinding.LOCATION_ATTRIB_NAME.equals(attributeName)) {
            authorityBinding.setLocation(attributeValue);
        } else if (AuthorityBinding.BINDING_ATTRIB_NAME.equals(attributeName)) { 
            authorityBinding.setBinding(attributeValue);
        } else {
            log.error(attributeName + " is not supported attribute for AuthorityBinding");
            if (!SAMLConfig.ignoreUnknownAttributes()) {
                throw new UnknownAttributeException(attributeName
                        + " is not a supported attribute for AuthorityBinding objects");
            }
        }
    }

}
