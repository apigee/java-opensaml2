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

package org.opensaml.saml2.metadata.impl;

import java.util.List;

import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.xml.XMLObject;

/**
 * A concrete implementation of {@link org.opensaml.saml2.metadata.Endpoint}
 */
public abstract class EndpointImpl extends AbstractSAMLObject implements Endpoint {

    /** Binding URI */
    private String bindingId;

    /** Endpoint location URI */
    private String location;

    /** Response location URI */
    private String responseLocation;

    /**
     * Constructor
     * 
     * @param namespaceURI the namespace the element is in
     * @param elementLocalName the local name of the XML element this Object represents
     * @param namespacePrefix the prefix for the given namespace
     */
    protected EndpointImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespacePrefix, elementLocalName, namespacePrefix);
    }

    /*
     * @see org.opensaml.saml2.metadata.Endpoint#getBinding()
     */
    public String getBinding() {
        return bindingId;
    }

    /*
     * @see org.opensaml.saml2.metadata.Endpoint#setBinding(java.net.URI)
     */
    public void setBinding(String binding) {
        bindingId = prepareForAssignment(bindingId, binding);
    }

    /*
     * @see org.opensaml.saml2.metadata.Endpoint#getLocation()
     */
    public String getLocation() {
        return location;
    }

    /*
     * @see org.opensaml.saml2.metadata.Endpoint#setLocation(java.net.URI)
     */
    public void setLocation(String location) {
        this.location = prepareForAssignment(this.location, location);
    }

    /*
     * @see org.opensaml.saml2.metadata.Endpoint#getResponseLocation()
     */
    public String getResponseLocation() {
        return responseLocation;
    }

    /*
     * @see org.opensaml.saml2.metadata.Endpoint#setResponseLocation(java.net.URI)
     */
    public void setResponseLocation(String location) {
        responseLocation = prepareForAssignment(responseLocation, location);
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        return null; // Endpoints don't have child elements
    }
}