/*
 * Copyright [2006] [University Corporation for Advanced Internet Development, Inc.]
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

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.impl.UnknownAttributeException;
import org.opensaml.common.impl.UnknownElementException;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.AttributeConsumingService;
import org.opensaml.saml2.metadata.RequestedAttribute;
import org.opensaml.saml2.metadata.ServiceDescription;
import org.opensaml.saml2.metadata.ServiceName;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * A thread safe {@link org.opensaml.common.io.Unmarshaller} for
 * {@link org.opensaml.saml2.metadata.AttributeConsumingService} objects.
 */
public class AttributeConsumingServiceUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /**
     * Constructor
     */
    public AttributeConsumingServiceUnmarshaller() {
        super(SAMLConstants.SAML20MD_NS, AttributeConsumingService.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processChildElement(org.opensaml.common.SAMLObject,
     *      org.opensaml.common.SAMLObject)
     */
    protected void processChildElement(SAMLObject parentSAMLObject, SAMLObject childSAMLObject)
            throws UnmarshallingException, UnknownElementException {
        AttributeConsumingService service = (AttributeConsumingService) parentSAMLObject;

        if (childSAMLObject instanceof ServiceName) {
            service.getNames().add((ServiceName) childSAMLObject);
        } else if (childSAMLObject instanceof ServiceDescription) {
            service.getDescriptions().add((ServiceDescription) childSAMLObject);
        } else if (childSAMLObject instanceof RequestedAttribute) {
            service.getRequestAttributes().add((RequestedAttribute) childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }

    /*
     * @see org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller#processAttribute(org.opensaml.common.SAMLObject,
     *      java.lang.String, java.lang.String)
     */
    protected void processAttribute(SAMLObject samlObject, String attributeName, String attributeValue)
            throws UnmarshallingException, UnknownAttributeException {
        AttributeConsumingService service = (AttributeConsumingService) samlObject;

        if (attributeName.equals(AttributeConsumingService.INDEX_ATTRIB_NAME)) {
            service.setIndex(Integer.valueOf(attributeValue));
        } else if (attributeName.equals(AttributeConsumingService.IS_DEFAULT_ATTRIB_NAME)) {
            service.setIsDefault(Boolean.valueOf(attributeValue));
        } else {
            super.processAttribute(samlObject, attributeName, attributeValue);
        }
    }
}