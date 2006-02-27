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

package org.opensaml.saml2.core.impl;

import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeValue;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.UnmarshallingException;
import org.w3c.dom.Attr;

/**
 * A thread-safe Unmarshaller for {@link org.opensaml.saml2.core.AttributeValue} objects.
 */
public class AttributeUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /**
     * Constructor
     */
    public AttributeUnmarshaller() {
        super(SAMLConstants.SAML20_NS, Attribute.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.xml.io.AbstractXMLObjectUnmarshaller#processChildElement(org.opensaml.xml.XMLObject,
     *      org.opensaml.xml.XMLObject)
     */
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject)
            throws UnmarshallingException {

        Attribute attribute = (Attribute) parentSAMLObject;

        if (childSAMLObject instanceof AttributeValue) {
            attribute.getAttributeValues().add((AttributeValue) childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }

    /*
     * @see org.opensaml.xml.io.AbstractXMLObjectUnmarshaller#processAttribute(org.opensaml.xml.XMLObject,
     *      org.w3c.dom.Attr)
     */
    protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {

        Attribute attrib = (Attribute) samlObject;

        if (attribute.getLocalName().equals(Attribute.NAME_ATTTRIB_NAME)) {
            attrib.setName(attribute.getValue());
        } else if (attribute.getLocalName().equals(Attribute.NAME_FORMAT_ATTRIB_NAME)) {
            attrib.setNameFormat(attribute.getValue());
        } else if (attribute.getLocalName().equals(Attribute.FRIENDLY_NAME_ATTRIB_NAME)) {
            attrib.setFriendlyName(attribute.getValue());
        } else {
            super.processAttribute(samlObject, attribute);
        }
    }
}