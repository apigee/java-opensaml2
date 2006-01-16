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

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectMarshaller;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.NameIdentifier;
import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Element;

/**
 *
 */
public class NameIdentifierMarshaller extends AbstractSAMLObjectMarshaller {

    /**
     * Constructor
     * @throws IllegalArgumentException
     */
    public NameIdentifierMarshaller() throws IllegalArgumentException {
        super(SAMLConstants.SAML1_NS, NameIdentifier.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectMarshaller#marshallAttributes(org.opensaml.common.SAMLObject, org.w3c.dom.Element)
     */
    @Override
    protected void marshallAttributes(SAMLObject samlElement, Element domElement) throws MarshallingException {
        NameIdentifier nameIdentifier = (NameIdentifier) samlElement;
        
        if (nameIdentifier.getNameQualifier() != null) {
            domElement.setAttribute(NameIdentifier.NAMEQUALIFIER_ATTRIB_NAME, nameIdentifier.getNameQualifier());
        }
        
        if (nameIdentifier.getFormat() != null) {
            domElement.setAttribute(NameIdentifier.FORMAT_ATTRIB_NAME, nameIdentifier.getFormat());
        }
    }
    
    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectMarshaller#marshallElementContent(org.opensaml.common.SAMLObject,
     *      org.w3c.dom.Element)
     */
    protected void marshallElementContent(SAMLObject samlObject, Element domElement) throws MarshallingException {
        NameIdentifier nameIdentifier = (NameIdentifier) samlObject;
        
        if (nameIdentifier.getNameIdentifier() != null) {
            domElement.setTextContent(nameIdentifier.getNameIdentifier());
        }
    }
}
