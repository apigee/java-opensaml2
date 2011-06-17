/*
 * Licensed to the University Corporation for Advanced Internet Development, 
 * Inc. (UCAID) under one or more contributor license agreements.  See the 
 * NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The UCAID licenses this file to You under the Apache 
 * License, Version 2.0 (the "License"); you may not use this file except in 
 * compliance with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.saml2.binding.security;

import org.opensaml.common.binding.security.BaseSAMLSecurityPolicyRuleTestCase;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.metadata.provider.DOMMetadataProvider;
import org.opensaml.ws.transport.InTransport;
import org.opensaml.ws.transport.http.HTTPInTransport;
import org.opensaml.ws.transport.http.HttpServletRequestAdapter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.w3c.dom.Document;


/**
 * Test SAML 2 AuthnRequetsSigned rule.
 */
public class SAML2AuthnRequestsSignedSecurityPolicyRuleTest 
    extends BaseSAMLSecurityPolicyRuleTestCase<AuthnRequest, Response, NameID> {
 
    /** Issuer for signing required case. */
    private final String issuerSigningRequired = "urn:test:issuer:required";
    
    /** Issuer for signing not required case. */
    private final String issuerSigningNotRequired = "urn:test:issuer:notrequired";

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        
        String mdfile = "/data/org/opensaml/saml2/binding/Metadata-AuthnRequestsSigned.xml";
        Document mdDoc = parser.parse(SAML2AuthnRequestsSignedSecurityPolicyRuleTest.class.getResourceAsStream(mdfile));
        DOMMetadataProvider metadataProvider = new DOMMetadataProvider(mdDoc.getDocumentElement());
        metadataProvider.initialize();
        
        messageContext.setMetadataProvider(metadataProvider);
        
        rule = new SAML2AuthnRequestsSignedRule();
    }
    
    /**
     * Test message not signed, signing not required.
     */
    public void testNotSignedAndNotRequired() {
        AuthnRequest authnRequest = 
            (AuthnRequest) unmarshallElement("/data/org/opensaml/saml2/binding/AuthnRequest.xml");
        messageContext.setInboundSAMLMessage(authnRequest);
        messageContext.setInboundMessageIssuer(issuerSigningNotRequired);
        
        assertRuleSuccess("Protocol message was not signed and was not required to be signed");
    }
    
    
    /**
     * Test message not signed, signing required.
     */
    public void testNotSignedAndRequired() {
        AuthnRequest authnRequest = 
            (AuthnRequest) unmarshallElement("/data/org/opensaml/saml2/binding/AuthnRequest.xml");
        messageContext.setInboundSAMLMessage(authnRequest);
        messageContext.setInboundMessageIssuer(issuerSigningRequired);
        
        assertRuleFailure("Protocol message signature was not signed but was required to be signed");
    }
    
    /**
     * Test message XML signed, signing not required.
     */
    public void testSignedAndNotRequired() {
        AuthnRequest authnRequest = 
            (AuthnRequest) unmarshallElement("/data/org/opensaml/saml2/binding/AuthnRequest-Signed.xml");
        messageContext.setInboundSAMLMessage(authnRequest);
        messageContext.setInboundMessageIssuer(issuerSigningNotRequired);
        
        assertRuleSuccess("Protocol message was signed and was not required to be signed");
    }
 
    /**
     * Test message XML signed, signing required.
     */
    public void testSignedAndRequired() {
        AuthnRequest authnRequest = 
            (AuthnRequest) unmarshallElement("/data/org/opensaml/saml2/binding/AuthnRequest-Signed.xml");
        messageContext.setInboundSAMLMessage(authnRequest);
        messageContext.setInboundMessageIssuer(issuerSigningRequired);
        
        assertRuleSuccess("Protocol message signature was signed but was required to be signed");
    }
    
    /**
     * Test message simple signed, signing not required.
     */
    public void testSimpleSignedAndRequired() {
        AuthnRequest authnRequest = 
            (AuthnRequest) unmarshallElement("/data/org/opensaml/saml2/binding/AuthnRequest.xml");
        messageContext.setInboundSAMLMessage(authnRequest);
        messageContext.setInboundMessageIssuer(issuerSigningRequired);
        
        HttpServletRequestAdapter inTransport = (HttpServletRequestAdapter) messageContext.getInboundMessageTransport();
        MockHttpServletRequest request  = (MockHttpServletRequest) inTransport.getWrappedRequest();
        request.setParameter("Signature", "some-signature-value");
        
        assertRuleSuccess("Protocol message was simple signed and was required to be signed");
    }

    /** {@inheritDoc} */
    protected InTransport buildInTransport() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        HTTPInTransport inTransport = new HttpServletRequestAdapter(request);
        
        request.setMethod("POST");
        
        return inTransport; 
    }
    
}


