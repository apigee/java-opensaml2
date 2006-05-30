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
     * Constructor
     * 
     * @param targetNamespaceURI the namespace URI of either the schema type QName or element QName of the elements this
     *            unmarshaller operates on
     * @param targetLocalName the local name of either the schema type QName or element QName of the elements this
     *            unmarshaller operates on
     * 
     * @throws NullPointerException if any of the arguments are null (or empty in the case of String parameters)
     */
    protected AbstractSAMLObjectUnmarshaller(String targetNamespaceURI, String targetLocalName)
            throws IllegalArgumentException {
        super(targetNamespaceURI, targetLocalName);
    }

    /**
     * {@inheritDoc}
     */
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject)
            throws UnmarshallingException {
        if (log.isDebugEnabled()) {
            log.debug("Ignoring unknown element " + childSAMLObject.getElementQName());
        }
    }

    /**
     * {@inheritDoc}
     */
    protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
        if (log.isDebugEnabled()) {
            log.debug("Ignorning unknown attribute " + attribute.getLocalName());
        }
    }

    /**
     * {@inheritDoc}
     */
    protected void processElementContent(XMLObject samlObject, String elementContent) {
        if (log.isDebugEnabled()) {
            log.debug("Ignoring element content " + elementContent);
        }
    }
}