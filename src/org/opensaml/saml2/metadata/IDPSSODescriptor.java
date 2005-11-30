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

package org.opensaml.saml2.metadata;

import java.net.URI;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.opensaml.common.util.xml.XMLConstants;

/**
 * SAML 2.0 Metadata IDPSSODescriptorType
 */
public interface IDPSSODescriptor extends SSODescriptor, AssertionIDRequestDescriptorComp, AttributeDescriptorComp, NameIDFormatDescriptorComp, AttributeProfileDescriptorComp {

    /** Local name, no namespace */
    public final static String LOCAL_NAME = "IDPSSODescriptor";
    
    /** QName for element */
    public final static QName QNAME = new QName(XMLConstants.SAML20MD_NS, LOCAL_NAME, XMLConstants.SAML20MD_PREFIX);
    
    /** "Name" attribute name */
    public final static String WANT_AUTHN_REQ_SIGNED_ATTRIB_NAME = "WantAuthnRequestSigned";
    
    /** "Name" attribute's QName */
    public final static QName WANT_AUTHN_REQ_SIGNED_QNAME = new QName(XMLConstants.SAML20MD_NS, WANT_AUTHN_REQ_SIGNED_ATTRIB_NAME, XMLConstants.SAML20MD_PREFIX);
    
    /**
     * Checks if the IDP SSO service wants authentication requests signed.
     * 
     * @return true is signing is desired, false if not
     */
	public boolean wantAuthnRequestsSigned();
    
    /**
     * Sets whether the IDP SSO service wants authentication requests signed.
     * 
     * @param wantSigned true if request should be signed, false if not
     */
    public void setWantAuthnRequestSigned(boolean wantSigned);

    
    /**
     * Gets the list of single sign on service {@link Endpoint}s for this IDP.
     * 
     * @return list of single sign on service {@link Endpoint}s
     */
	public Set /*<Endpoint>*/ getSingleSignOnServices();
    
    /**
     * Gets list of single sign on service {@link Endpoint}s for a particular binding for this IDP.
     * 
     * @param binding the binding
     * 
     * @return list of single sign on service {@link Endpoint}s
     */
    public Set /*<Endpoint>*/ getSignleSignOnServicesByBinding(URI binding);
    
    /**
     * Adds an endpoint to the list of single sign on service {@link Endpoint}s.
     * 
     * @param endpoint the endpoint
     */
    public void addSingleSignOnService(Endpoint endpoint);
    
    /**
     * Adds a list of endpoints to the list of single sign on service {@link Endpoint}s.
     * 
     * @param endpoints the endpoints
     */
    public void addSingleSignOnServices(Set /*<Endpoint>*/ endpoints);
    
    /**
     * Removes an endpoint from the list of single sign on service {@link Endpoint}s.
     * 
     * @param endpoint the endpoint
     */
    public void removeSingleSignOnService(Endpoint endpoint);
    
    /**
     * Removes a list of endpoint from the list of single sign on service {@link Endpoint}s.
     * 
     * @param endpoints the endpoints
     */
    public void removeSingleSignOnServices(Set /*<Endpoint>*/ endpoints);
    
    /**
     * Removes all the single sign on endpoints.
     */
    public void removeAllSingleSignOnServices();

    /**
     * Gets the list of NameID mapping service {@link Endpoint}s for this service.
     *  
     * @return the list of NameID mapping service {@link Endpoint}s for this service
     */
	public Set /*<Endpoint>*/ getNameIDMappingServices();
    
    /**
     * Gets list of NameID mapping service {@link Endpoint}s for a particular binding for this IDP.
     * 
     * @param binding the binding
     * 
     * @return list of single sign on service {@link Endpoint}s
     */
    public List /*<Endpoint>*/ getNameIdMappingServicesByBinding(URI binding);
    
    /**
     * Adds an endpoint to the list of NameID mapping service {@link Endpoint}s.
     * 
     * @param endpoint the endpoint
     */
	public void addNameIDMappingService(Endpoint endpoint);
    
    /**
     * Adds a list of endpoint to the list of NameID mapping service {@link Endpoint}s.
     * 
     * @param endpoints the endpoints
     */
    public void addNameIDMappingServices(Set /*<Endpoint>*/ endpoints);
    
    /**
     * Removes an endpoint from the list of NameID mapping service {@link Endpoint}s.
     * 
     * @param endpoint the endpoint
     */
    public void removeNameIDMappingService(Endpoint endpoint);
    
    /**
     * Removes a list of endpoints from the list of NameID mapping service {@link Endpoint}s.
     * 
     * @param endpoints the endpoints
     */
    public void removeNameIDMappingServices(Set /*<Endpoint>*/ endpoints);
    
    /**
     * Removes all the NameID mapping endpoints.
     *
     */
    public void removeAllNameIDMappingServices();
}