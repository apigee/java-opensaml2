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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.saml2.metadata.AttributeConsumingService;
import org.opensaml.saml2.metadata.RequestedAttribute;
import org.opensaml.saml2.metadata.ServiceDescription;
import org.opensaml.saml2.metadata.ServiceName;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSBooleanValue;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of {@link org.opensaml.saml2.metadata.AttributeConsumingService}.
 */
public class AttributeConsumingServiceImpl extends AbstractSAMLObject implements AttributeConsumingService {

    /** Index of this service */
    private int index;

    /** isDefault attribute of this service */
    private XSBooleanValue isDefault;

    /** ServiceName children */
    private final XMLObjectChildrenList<ServiceName> serviceNames;

    /** ServiceDescription children */
    private final XMLObjectChildrenList<ServiceDescription> serviceDescriptions;

    /** RequestedAttribute children */
    private final XMLObjectChildrenList<RequestedAttribute> requestAttributes;
    
    /**
     * Constructor
     * 
     * @param namespaceURI
     * @param elementLocalName
     * @param namespacePrefix
     */
    protected AttributeConsumingServiceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        serviceNames = new XMLObjectChildrenList<ServiceName>(this);
        serviceDescriptions = new XMLObjectChildrenList<ServiceDescription>(this);
        requestAttributes = new XMLObjectChildrenList<RequestedAttribute>(this);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeConsumingService#getIndex()
     */
    public int getIndex() {
        return index;
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeConsumingService#setIndex(int)
     */
    public void setIndex(int index) {
        if (this.index != index) {
            releaseThisandParentDOM();
            this.index = index;
        }
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeConsumingService#isDefault()
     */
    public XSBooleanValue isDefault() {
        return isDefault;
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeConsumingService#setIsDefault(boolean)
     */
    public void setIsDefault(XSBooleanValue isDefault) {
        this.isDefault = prepareForAssignment(this.isDefault, isDefault);
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeConsumingService#getNames()
     */
    public List<ServiceName> getNames() {
        return serviceNames;
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeConsumingService#getDescriptions()
     */
    public List<ServiceDescription> getDescriptions() {
        return serviceDescriptions;
    }

    /*
     * @see org.opensaml.saml2.metadata.AttributeConsumingService#getRequestAttributes()
     */
    public List<RequestedAttribute> getRequestAttributes() {
        return requestAttributes;
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

        children.addAll(serviceNames);
        children.addAll(serviceDescriptions);
        children.addAll(requestAttributes);

        return Collections.unmodifiableList(children);
    }
}