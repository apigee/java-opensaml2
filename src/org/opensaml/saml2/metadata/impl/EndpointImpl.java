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

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.util.UnmodifiableOrderedSet;
import org.opensaml.saml2.metadata.Endpoint;

/**
 * A concrete implementation of {@link org.opensaml.saml2.metadata.Endpoint}
 */
public class EndpointImpl extends AbstractSAMLObject implements Endpoint {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 4651342129482524863L;

    /** Binding URI */
    private String bindingId;

    /** Endpoint location URI */
    private String location;

    /** Response location URI */
    private String responseLocation;
    
    /**
     * Constructor
     */
    public EndpointImpl() {
        super();
        setQName(Endpoint.QNAME);
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
     * @see org.opensaml.saml2.common.impl.AbstractSAMLElement#getOrderedChildren()
     */
    public UnmodifiableOrderedSet<SAMLObject> getOrderedChildren(){
        return null; //Endpoints don't have child elements
    }
    
    /*
     * @see org.opensaml.common.SAMLElement#equals(org.opensaml.common.SAMLElement)
     */
    public boolean equals(SAMLObject element) {
        if (element != null && element instanceof Endpoint) {
            Endpoint otherEndpoint = (Endpoint) element;
            return getBinding().equals(otherEndpoint.getBinding()) && getLocation().equals(otherEndpoint.getLocation())
                    && getResponseLocation().equals(otherEndpoint.getResponseLocation());
        }

        return false;
    }
}
