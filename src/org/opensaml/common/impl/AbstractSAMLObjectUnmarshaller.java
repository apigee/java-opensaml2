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

package org.opensaml.common.impl;

import org.apache.log4j.Logger;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.AbstractXMLObjectUnmarshaller;
import org.opensaml.xml.io.UnmarshallingException;
import org.w3c.dom.Attr;

/**
 * An thread safe abstract unmarshaller. This abstract marshaller only works with
 * {@link org.opensaml.common.impl.AbstractSAMLObject}.
 */
public abstract class AbstractSAMLObjectUnmarshaller extends AbstractXMLObjectUnmarshaller {

    /**
     * Logger
     */
    private static Logger log = Logger.getLogger(AbstractSAMLObjectUnmarshaller.class);

    /**
     * 
     * Constructor
     * 
     * @param target the QName of the type or elment this unmarshaller operates on
     */
    protected AbstractSAMLObjectUnmarshaller(String targetNamespaceURI, String targetLocalName)
            throws IllegalArgumentException {
        super(targetNamespaceURI, targetLocalName);
    }

    /**
     * Called after this unmarshaller has unmarshalled a child element in order to add that child to the parent element.
     * 
     * @param parentSAMLObject the parent element
     * @param childSAMLObject the child element
     * 
     * @throws UnmarshallingException thrown if the child element is not a valid child of the parent or if an element
     *             that the unmarshaller does not understand is encountered
     */
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject)
            throws UnmarshallingException {
        if (Configuration.ignoreUnknownElements()) {
            if (log.isDebugEnabled()) {
                log.debug("Ignoring unknown element " + childSAMLObject.getElementQName());
            }
        } else {
            throw new UnmarshallingException(childSAMLObject.getElementQName()
                    + " is not an element supported by this unmarshaller");
        }
    }

    /**
     * Called after this unmarshaller has unmarshalled an attribute in order to add it to the SAML element
     * 
     * @param samlObject the SAML element
     * @param attributeName the attributes name
     * @param attributeValue the attributes value
     * 
     * @throws UnmarshallingException thrown if the given attribute is not a valid attribute for this SAML element or if
     *             an attribute that the unmarshaller does not understand is encountered
     */
    protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
        if (Configuration.ignoreUnknownAttributes()) {
            if (log.isDebugEnabled()) {
                log.debug("Ignorning unknown attribute " + attribute.getLocalName());
            }
        } else {
            throw new UnmarshallingException(attribute.getLocalName()
                    + " is not an attribute supported by this attribute");
        }
    }

    /**
     * Called to process the content of a DOM element
     * 
     * @param samlObject SAML object the content will be given to
     * @param elementContent the DOM element content
     */
    protected void processElementContent(XMLObject samlObject, String elementContent) {
        if (log.isDebugEnabled()) {
            log.debug("Ignoring element content " + elementContent);
        }
    }
}