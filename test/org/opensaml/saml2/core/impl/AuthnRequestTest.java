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

/**
 * 
 */
package org.opensaml.saml2.core.impl;

import javax.xml.namespace.QName;

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.AuthnRequest;

/**
 *
 */
public class AuthnRequestTest extends RequestTest {
    
            
        /** Expected ForceAuthn attribute */    
        private Boolean expectedForceAuthn;
        
        /** Expected IsPassive attribute */    
        private Boolean expectedIsPassive;
        
        /** Expected ProtocolBinding attribute */    
        private String expectedProtocolBinding;
        
        /** Expected AssertionConsumerServiceIndex attribute */    
        private Integer expectedAssertionConsumerServiceIndex;
        
        /** Expected AssertionConsumerServiceURL attribute */    
        private String expectedAssertionConsumerServiceURL;
        
        /** Expected AttributeConsumingServiceIndex attribute */    
        private Integer expectedAttributeConsumingServiceIndex;
        
        /** Expected ProviderName attribute */    
        private String expectedProviderName;

    /**
     * Constructor
     *
     */
    public AuthnRequestTest() {
        super();
        
        singleElementFile = "/data/org/opensaml/saml2/core/impl/AuthnRequest.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/core/impl/AuthnRequestOptionalAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml2/core/impl/AuthnRequestChildElements.xml";
    }
    

    /**
     * @see org.opensaml.saml2.core.impl.RequestTest#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        expectedForceAuthn = new Boolean(true);
        expectedIsPassive = new Boolean(true);
        expectedProtocolBinding = "urn:string:protocol-binding";
        expectedAssertionConsumerServiceIndex = new Integer(3);
        expectedAssertionConsumerServiceURL = "http://sp.example.org/acs";
        expectedAttributeConsumingServiceIndex = new Integer(2);
        expectedProviderName = "Example Org";
    }



    /**
     * @see org.opensaml.saml2.core.impl.RequestTest#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, AuthnRequest.LOCAL_NAME);
        AuthnRequest req = (AuthnRequest) buildSAMLObject(qname);
        
        super.populateRequiredAttributes(req);
        
        assertEquals(expectedDOM, req);

    }
    
    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, AuthnRequest.LOCAL_NAME);
        AuthnRequest req = (AuthnRequest) buildSAMLObject(qname);
        
        super.populateRequiredAttributes(req);
        super.populateOptionalAttributes(req);
        
        req.setForceAuthn(expectedForceAuthn);
        req.setIsPassive(expectedIsPassive);
        req.setProtocolBinding(expectedProtocolBinding);
        req.setAssertionConsumerServiceIndex(expectedAssertionConsumerServiceIndex);
        req.setAssertionConsumerServiceURL(expectedAssertionConsumerServiceURL);
        req.setAttributeConsumingServiceIndex(expectedAttributeConsumingServiceIndex);
        req.setProviderName(expectedProviderName);
        
        assertEquals(expectedOptionalAttributesDOM, req);
    }
    
    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, AuthnRequest.LOCAL_NAME);
        AuthnRequest req = (AuthnRequest) buildSAMLObject(qname);
        
        super.populateChildElements(req);
        
        req.setSubject(new SubjectImpl());
        req.setNameIDPolicy(new NameIDPolicyImpl());
        req.setConditions(new ConditionsImpl());
        req.setRequestedAuthnContext(new RequestedAuthnContextImpl());
        req.setScoping(new ScopingImpl());
        
        assertEquals(expectedChildElementsDOM, req);
    }

    /**
     * @see org.opensaml.saml2.core.impl.RequestTest#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        AuthnRequest req = (AuthnRequest) unmarshallElement(singleElementFile);
        
        assertNotNull("AuthnRequest was null", req);
        assertNull("ForceAuthn was not null", req.getForceAuthn());
        assertNull("IsPassive was not null", req.getIsPassive());
        assertNull("ProtocolBinding was not null", req.getProtocolBinding());
        assertNull("AssertionConsumerServiceIndex was not null", req.getAssertionConsumerServiceIndex());
        assertNull("AssertionConsumerServiceURL was not null", req.getAssertionConsumerServiceURL());
        assertNull("AttributeConsumingServiceIndex was not null", req.getAttributeConsumingServiceIndex());
        assertNull("ProviderName was not null", req.getProviderName());
        
        super.helperTestSingleElementUnmarshall(req);

    }
 
    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        AuthnRequest req = (AuthnRequest) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertEquals("Unmarshalled ForceAuthn was not the expected value", expectedForceAuthn, req.getForceAuthn());
        assertEquals("Unmarshalled IsPassive was not the expected value", expectedIsPassive, req.getIsPassive());
        assertEquals("Unmarshalled ProtocolBinding was not the expected value", expectedProtocolBinding, req.getProtocolBinding());
        assertEquals("Unmarshalled AssertionConsumerServiceIndex was not the expected value", expectedAssertionConsumerServiceIndex, req.getAssertionConsumerServiceIndex());
        assertEquals("Unmarshalled AssertionConsumerServiceURL was not the expected value", expectedAssertionConsumerServiceURL, req.getAssertionConsumerServiceURL());
        assertEquals("Unmarshalled AttributeConsumingServiceIndex was not the expected value", expectedAttributeConsumingServiceIndex, req.getAttributeConsumingServiceIndex());
        assertEquals("Unmarshalled ProviderName was not the expected value", expectedProviderName, req.getProviderName());
        
        super.helperTestSingleElementOptionalAttributesUnmarshall(req);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    public void testChildElementsUnmarshall() {
        AuthnRequest req = (AuthnRequest) unmarshallElement(childElementsFile);
        
        assertNotNull("Subject was null", req.getSubject());
        assertNotNull("NameIDPolicy was null", req.getNameIDPolicy());
        assertNotNull("Conditions was null", req.getConditions());
        assertNotNull("RequestedAuthnContext was null", req.getRequestedAuthnContext());
        assertNotNull("Scoping was null", req.getScoping());
        
        super.helperTestChildElementsUnmarshall(req);
    }
}