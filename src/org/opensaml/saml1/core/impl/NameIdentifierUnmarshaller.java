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
import org.opensaml.saml1.core.NameIdentifier;
import org.opensaml.xml.io.UnmarshallingException;

/**
 *
 */
public class NameIdentifierUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /** Logger */
    private static Logger log = Logger.getLogger(NameIdentifierUnmarshaller.class);


    /**
     * Constructor
     */
    public NameIdentifierUnmarshaller() {
        super(SAMLConstants.SAML1_NS, NameIdentifier.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processChildElement(org.opensaml.common.SAMLObject, org.opensaml.common.SAMLObject)
     */
    @Override
    protected void processChildElement(SAMLObject parentElement, SAMLObject childElement) throws UnmarshallingException, UnknownElementException {
        // 
        // No child elements
        //

        log.error(childElement.getElementQName() + " is not a supported element for NameIdentifier objects");
        if (!SAMLConfig.ignoreUnknownElements()) {
            throw new UnknownElementException(childElement.getElementQName()
                    + " is not a supported element for NameIdentifier objects");
        }
    }

    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processAttribute(org.opensaml.common.SAMLObject, java.lang.String, java.lang.String)
     */
    @Override
    protected void processAttribute(SAMLObject samlElement, String attributeName, String attributeValue) throws UnmarshallingException, UnknownAttributeException {
        NameIdentifier nameIdentifier = (NameIdentifier) samlElement;

        if (NameIdentifier.FORMAT_ATTRIB_NAME.equals(attributeName)) {
           nameIdentifier.setFormat(attributeValue);
        } else if (NameIdentifier.NAMEQUALIFIER_ATTRIB_NAME.equals(attributeName)) {
            nameIdentifier.setNameQualifier(attributeValue);
        } else {
            log.error(attributeName
                    + " is not a supported attributed for NameIdentifier objects");
            if (!SAMLConfig.ignoreUnknownAttributes()) {
                throw new UnknownAttributeException(attributeName
                        + " is not a supported attributed for NameIdentifier objects");
            }
        }
    }
    
    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#unmarshallElementContent(org.opensaml.common.SAMLObject,
     *      java.lang.String)
     */
    protected void unmarshallElementContent(SAMLObject samlElement, String elementContent) {
        NameIdentifier nameIdentifier = (NameIdentifier) samlElement;
        
        nameIdentifier.setNameIdentifier(elementContent);
    }
}
