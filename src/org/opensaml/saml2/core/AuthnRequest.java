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

package org.opensaml.saml2.core;

import javax.xml.namespace.QName;

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.xml.schema.XSBooleanValue;


/**
 * SAML 2.0 Core AuthnRequest
 */
public interface AuthnRequest extends Request {

    /** Element local name */
    public final static String DEFAULT_ELEMENT_LOCAL_NAME = "AuthnRequest";
    
    /** Default element name */
    public final static QName DEFAULT_ELEMENT_NAME = new QName(SAMLConstants.SAML20P_NS, DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20P_PREFIX);
    
    /** Local name of the XSI type */
    public final static String TYPE_LOCAL_NAME = "AuthnRequestType"; 
        
    /** QName of the XSI type */
    public final static QName TYPE_NAME = new QName(SAMLConstants.SAML20P_NS, TYPE_LOCAL_NAME, SAMLConstants.SAML20P_PREFIX);
    
    /** ForceAuthn attribute name */
    public final static String FORCE_AUTHN_ATTRIB_NAME = "ForceAuthn";
    
    /** IsPassive attribute name */
    public final static String IS_PASSIVE_ATTRIB_NAME = "IsPassive";
    
    /** ProtocolBinding attribute name */
    public final static String PROTOCOL_BINDING_ATTRIB_NAME = "ProtocolBinding";
    
    /** AssertionConsumerServiceIndex attribute name */
    public final static String ASSERTION_CONSUMER_SERVICE_INDEX_ATTRIB_NAME = "AssertionConsumerServiceIndex";
    
    /** AssertionConsumerServiceURL attribute name */
    public final static String ASSERTION_CONSUMER_SERVICE_URL_ATTRIB_NAME = "AssertionConsumerServiceURL";
    
    /** AttributeConsumingServiceIndex attribute name */
    public final static String ATTRIBUTE_CONSUMING_SERVICE_INDEX_ATTRIB_NAME = "AttributeConsumingServiceIndex";
    
    /** ProviderName attribute name */
    public final static String PROVIDER_NAME_ATTRIB_NAME = "ProviderName";
    
    /**
     * Gets whether the IdP should force the user to reauthenticate
     * 
     * @return the value of the ForceAuthn attribute
     */
    public XSBooleanValue getForceAuthn();
    
    /**
     * Sets whether the IdP should force the user to reauthenticate
     * 
     * @param newForceAuthn the new value of the ForceAuthn attribute
     */
    public void setForceAuthn(XSBooleanValue newForceAuthn);
    
    /**
     * Gets whether the IdP should refrain from interacting with the
     * user during the authentication process
     * 
     * @return the value of the ForceAuthn attribute
     */
    public XSBooleanValue getIsPassive();
    
    /**
     * Sets whether the IdP should refrain from interacting with the
     * user during the authentication process
     * 
     * @param newIsPassive the new value of the ForceAuthn attribute
     */
    public void setIsPassive(XSBooleanValue newIsPassive);
    
    /**
     * Gets the protocol binding URI for the request 
     * 
     * @return the value of the ProtocolBinding attribute
     */
    public String getProtocolBinding();
    
    /**
     * Sets the protocol binding URI for the request 
     * 
     * @param newProtocolBinding the new value of the ProtocolBinding attribute
     */
    public void setProtocolBinding(String newProtocolBinding);
    
    /**
     * Gets the index of the particular Assertion Consumer Service 
     * to which the response to this request should be delivered
     * 
     * @return the value of the AssertionConsumerServiceIndex attribute
     */
    public Integer getAssertionConsumerServiceIndex();
    
    /**
     * Sets the index of the particular Assertion Consumer Service
     * to which the response to this request should be delivered
     * 
     * @param newAssertionConsumerServiceIndex the new value of the AssertionConsumerServiceIndex attribute
     */
    public void setAssertionConsumerServiceIndex(Integer newAssertionConsumerServiceIndex);
    
    /**
     * Gets the URL of the particular Assertion Consumer Service
     * to which the response to this request should be delivered
     * 
     * @return the value of the AssertionConsumerServiceURL attribute
     */
    public String getAssertionConsumerServiceURL();
    
    /**
     * Sets the URL of the particular Assertion Consumer Service
     * to which the response to this request should be delivered
     * 
     * @param newAssertionConsumerServiceURL the new value of the AssertionConsumerServiceURL attribute
     */
    public void setAssertionConsumerServiceURL(String newAssertionConsumerServiceURL);
    
    /**
     * Gets the index of the Attribute Consuming Service which describes 
     * the SAML attributes the requester desires or requires to be supplied
     * in the <code>Response</code> message.
     * 
     * 
     * @return the value of the AssertionConsumerServiceIndex attribute
     */
    public Integer getAttributeConsumingServiceIndex();
    
    /**
     * 
     * Sets the index of the Attribute Consuming Service which describes 
     * the SAML attributes the requester desires or requires to be supplied
     * in the <code>Response</code> message.
     * 
     * @param newAttributeConsumingServiceIndex the new value of the AttributeConsumingServiceIndex attribute
     */
    public void setAttributeConsumingServiceIndex(Integer newAttributeConsumingServiceIndex);
    
    /**
     * Gets the human-readable name of the requester for use by the
     * presenter's user agent or the identity provider
     * 
     * @return the value of the ProviderName attribute
     */
    public String getProviderName();
    
    /**
     * Sets the human-readable name of the requester for use by the
     * presenter's user agent or the identity provider
     * 
     * @param newProviderName the new value of the ProviderName attribute
     */
    public void setProviderName(String newProviderName);
    
    /**
     * Gets the Subject of the request
     * 
     * @return the Subject of the request
     */
    public Subject getSubject();
    
    /**
     * Sets the Subject of the request
     * 
     * @param newSubject the new value of the Subject of the request
     */
    public void setSubject(Subject newSubject);
    
    /**
     * Gets the NameIDPolicy of the request
     * 
     * @return the NameIDPolicy of the request
     */
    public NameIDPolicy getNameIDPolicy();
    
    /**
     * Sets the NameIDPolicy of the request
     * 
     * @param newNameIDPolicy the new value of the NameIDPolicy of the request
     */
    public void setNameIDPolicy(NameIDPolicy newNameIDPolicy);
    
    /**
     * Gets the Conditions of the request
     * 
     * @return the Conditions of the request
     */
    public Conditions getConditions();
    
    /**
     * Sets the Conditions of the request
     * 
     * @param newConditions the new value of the Conditions of the request
     */
    public void setConditions(Conditions newConditions);
    
    /**
     * Gets the RequestedAuthnContext of the request
     * 
     * @return the RequestedAuthnContext of the request
     */
    public RequestedAuthnContext getRequestedAuthnContext();
    
    /**
     * Sets the RequestedAuthnContext of the request
     * 
     * @param newRequestedAuthnContext the new value of the RequestedAuthnContext of the request
     */
    public void setRequestedAuthnContext(RequestedAuthnContext newRequestedAuthnContext);
    
    /**
     * Gets the Scoping of the request
     * 
     * @return the Scoping of the request
     */
    public Scoping getScoping();
    
    /**
     * Sets the Scoping of the request
     * 
     * @param newScoping the new value of the Scoping of the request
     */
    public void setScoping(Scoping newScoping);
    
}