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
import org.opensaml.common.io.UnknownAttributeException;
import org.opensaml.common.io.UnknownElementException;
import org.opensaml.common.io.Unmarshaller;
import org.opensaml.common.io.UnmarshallingException;
import org.opensaml.common.io.impl.AbstractUnmarshaller;
import org.opensaml.saml1.core.StatusMessage;

/**
 * A thread-safe {@link org.opensaml.common.io.Unmarshaller} for {@link org.opensaml.saml1.core.StatusMessage} objects.
 */
public class StatusMessageUnmarshaller extends AbstractUnmarshaller implements Unmarshaller {

    /**
     * Logger
     */
    private static Logger log = Logger.getLogger(StatusMessageUnmarshaller.class);

    /**
     * Constructor
     */
    public StatusMessageUnmarshaller() {
        super(StatusMessage.QNAME);
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#processChildElement(org.opensaml.common.SAMLObject,
     *      org.opensaml.common.SAMLObject)
     */
    @Override
    protected void processChildElement(SAMLObject parentElement, SAMLObject childElement)
            throws UnmarshallingException, UnknownElementException {

        log.error(childElement.getElementQName()
                + " is not a supported element for StatusMessage objects");
        if (!SAMLConfig.ignoreUnknownElements()) {
            throw new UnknownElementException(childElement.getElementQName()
                    + " is not a supported element for StatusMessage objects");
        }
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#processAttribute(org.opensaml.common.SAMLObject,
     *      java.lang.String, java.lang.String)
     */
    @Override
    protected void processAttribute(SAMLObject samlElement, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {

        log.error(attributeName + " is not a supported attributed for StatusMessage objects");
        if (!SAMLConfig.ignoreUnknownAttributes()) {
            
            throw new UnknownAttributeException(attributeName + " is not a supported attributed for StatusMessage objects");
        }
    }

    /*
     * @see org.opensaml.common.io.impl.AbstractUnmarshaller#unmarshallElementContent(org.opensaml.common.SAMLObject,
     *      java.lang.String)
     */
    protected void unmarshallElementContent(SAMLObject samlElement, String elementContent) {
        StatusMessage statusMessage = (StatusMessage) samlElement;

        statusMessage.setMessage(elementContent);
    }

}
