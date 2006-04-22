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

import java.util.List;

import javax.xml.namespace.QName;

import org.opensaml.common.xml.SAMLConstants;

/**
 * SAML 2.0 Metadata SPSSODescriptorType
 */
public interface SPSSODescriptor extends SSODescriptor {
    /** Element name, no namespace */
    public final static String DEFAULT_ELEMENT_LOCAL_NAME = "SPSSODescriptor";
    
    /** Default element name */
    public final static QName DEFAULT_ELEMENT_NAME = new QName(SAMLConstants.SAML20MD_NS, DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
    
    /** Local name of the XSI type */
    public final static String TYPE_LOCAL_NAME = "SPSSODescriptorType"; 
        
    /** QName of the XSI type */
    public final static QName TYPE_NAME = new QName(SAMLConstants.SAML20MD_NS, TYPE_LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
    
    /** "AuthnRequestsSigned" attribute's local name */
    public final static String AUTH_REQUETS_SIGNED_ATTRIB_NAME = "AuthnRequestsSigned";
    
    /** "WantAssertionsSigned" attribute's local name */
    public final static String WANT_ASSERTIONS_SIGNED_ATTRIB_NAME = "WantAssertionsSigned";
    
    /**
     * Gets whether this service signs AuthN requests.
     * 
     * @return true of this service signs requests, false if not
     */
	public boolean authnRequestsSigned();
    
    /**
     * Sets whether this service signs AuthN requests.
     * 
     * @param isSigned true of this service signs requests, false if not
     */
    public void setAuthnRequestsSigned(Boolean isSigned);

    /**
     * Gets whether this service wants assertions signed.
     * 
     * @return true if this service wants assertions signed, false if not
     */
	public boolean wantAssertionsSigned();
    
    /**
     * Sets whether this service wants assertions signed.
     * 
     * @param wantAssestionSigned true if this service wants assertions signed, false if not
     */
    public void setWantAssertionsSigned(Boolean wantAssestionSigned);
    
    /**
     * Gets an immutable list of assertion consumer service {@link Endpoint}s for this service.
     * 
     * @return list of assertion consumer service {@link Endpoint}s for this service
     */
    public List<AssertionConsumerService> getAssertionConsumerServices();
    
    /**
     * Gets an immutable list of attribute consuming service {@link Endpoint}s for this service.
     * 
     * @return list of attribute consuming service {@link Endpoint}s for this service
     */
    public List<AttributeConsumingService> getAttributeConsumingServices();
}
