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

package org.opensaml.saml1.core.impl;

import javax.xml.namespace.QName;

import org.opensaml.common.impl.AbstractSAMLObjectMarshaller;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.AuthorityBinding;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Element;

/**
 * A thread safe Marshaller for {@link org.opensaml.saml1.core.AuthorityBinding} objects.
 */
public class AuthorityBindingMarshaller extends AbstractSAMLObjectMarshaller {

    /**
     * Constructor
     */
    public AuthorityBindingMarshaller() {
        super(SAMLConstants.SAML1_NS, AuthorityBinding.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.xml.io.AbstractXMLObjectMarshaller#marshallAttributes(org.opensaml.xml.XMLObject,
     *      org.w3c.dom.Element)
     */
    public void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
        AuthorityBinding authorityBinding = (AuthorityBinding) samlElement;

        if (authorityBinding.getAuthorityKind() != null) {
            QName authKind = authorityBinding.getAuthorityKind();
            // TODO may want to factor this code out for reuse, ie get "QName string"
            // from the QName in a particular Element context
            StringBuffer buf = new StringBuffer();
            if (authKind.getNamespaceURI() != null) {
                String prefix = domElement.lookupPrefix(authKind.getNamespaceURI());
                if (prefix != null) {
                    buf.append(prefix + ":");
                }
            }
            buf.append(authKind.getLocalPart());
            domElement.setAttributeNS(null, AuthorityBinding.AUTHORITYKIND_ATTRIB_NAME, buf.toString());
        }

        if (authorityBinding.getBinding() != null) {
            domElement.setAttributeNS(null, AuthorityBinding.BINDING_ATTRIB_NAME, authorityBinding.getBinding());
        }

        if (authorityBinding.getLocation() != null) {
            domElement.setAttributeNS(null, AuthorityBinding.LOCATION_ATTRIB_NAME, authorityBinding.getLocation());
        }
    }
}