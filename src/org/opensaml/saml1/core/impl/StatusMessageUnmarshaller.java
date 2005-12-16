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

import org.opensaml.common.SAMLConfig;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.io.UnknownAttributeException;
import org.opensaml.common.io.UnknownElementException;
import org.opensaml.common.io.Unmarshaller;
import org.opensaml.common.io.UnmarshallingException;
import org.opensaml.common.io.impl.AbstractUnmarshaller;
import org.opensaml.saml1.core.StatusMessage;

/**
 *
 */
public class StatusMessageUnmarshaller extends AbstractUnmarshaller implements Unmarshaller {

    /**
     * Constructor
     *
     * @param target
     */
    public StatusMessageUnmarshaller() {
        super(StatusMessage.QNAME);
        // TODO Auto-generated constructor stub
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#processChildElement(org.opensaml.common.SAMLObject, org.opensaml.common.SAMLObject)
     */
    @Override
    protected void processChildElement(SAMLObject parentElement, SAMLObject childElement)
            throws UnmarshallingException, UnknownElementException {

        if(!SAMLConfig.ignoreUnknownElements()){
            throw new UnknownElementException(childElement.getElementQName() + " is not a supported element for StatusCode objects");
        }
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#processAttribute(org.opensaml.common.SAMLObject, java.lang.String, java.lang.String)
     */
    @Override
    protected void processAttribute(SAMLObject samlElement, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {
        
        if(!SAMLConfig.ignoreUnknownAttributes()){
            throw new UnknownAttributeException(attributeName + " is not a supported attributed for Response objects");
        }
    }
    
    protected void unmarshallElementContent(SAMLObject samlElement, String elementContent)
    {
        StatusMessage statusMessage = (StatusMessage) samlElement;
        
        statusMessage.setMessage(elementContent);
    }

}
