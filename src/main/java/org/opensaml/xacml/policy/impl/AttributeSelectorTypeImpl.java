/*
 * Copyright 2008 University Corporation for Advanced Internet Development, Inc.
 * Copyright 2008 Members of the EGEE Collaboration.
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

package org.opensaml.xacml.policy.impl;

import java.util.List;

import org.opensaml.xacml.policy.AttributeSelectorType;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSBooleanValue;
import org.opensaml.xml.validation.AbstractValidatingXMLObject;

/**
 * Implementation {@link AttributeSelectorType}.
 */
public class AttributeSelectorTypeImpl extends AbstractValidatingXMLObject implements AttributeSelectorType {

    /**Datatype.*/
    private String dataType;
    
    /**Issuer.*/
    private String requestContextPath;
    
    /**Must be present.*/
    private boolean mustBePresent;
    
    /**Must be present.*/
    private XSBooleanValue mustBePresentXS;
    
    /**
     * Constructor.
     * 
     * @param namespaceURI the namespace the element is in
     * @param elementLocalName the local name of the XML element this Object represents
     * @param namespacePrefix the prefix for the given namespace
     */
    protected AttributeSelectorTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);     
    }
    
    /** {@inheritDoc} */
    public String getDataType() {
        return dataType;
    }

    /** {@inheritDoc} */
    public boolean getMustBePresent() {
        return false;
    }

    /** {@inheritDoc} */
    public XSBooleanValue getMustBePresentXSBoolean() {
        return mustBePresentXS;
    }

    /** {@inheritDoc} */
    public String getRequestContextPath() {
        return requestContextPath;
    }

    /** {@inheritDoc} */
    public void setDataType(String type) {
       this.dataType = prepareForAssignment(this.dataType,type);
    }

    /** {@inheritDoc} */
    public void setMustBePresent(XSBooleanValue present) {
        mustBePresentXS = prepareForAssignment(this.mustBePresentXS,present);

    }

    /** {@inheritDoc} */
    public void setRequestContextPath(String path) {
        requestContextPath = prepareForAssignment(this.requestContextPath,path);
    }

    /** {@inheritDoc} */
    public List<XMLObject> getOrderedChildren() {
        // TODO Auto-generated method stub
        return null;
    }

}
