/*
 * Copyright [2007] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.saml2.binding.security;

import org.joda.time.DateTime;
import org.opensaml.common.binding.security.BaseSAMLSecurityPolicyTest;
import org.opensaml.common.binding.security.SAMLSecurityPolicyContext;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.saml2.core.Response;
import org.opensaml.xml.XMLObject;


/**
 * Test the protocol message rule for request types.
 */
public class ResponseProtocolMessageRuleTest extends BaseSAMLSecurityPolicyTest {
    
    private String issuer;
    private String messageID;
    private DateTime issueInstant;
    
    private SAML2ProtocolMessageRuleFactory protocolMessageRuleFactory;
    
    /** Constructor. */
    public ResponseProtocolMessageRuleTest() {
        issuer = "SomeIssuerID";
        messageID = "abc123";
        issueInstant = new DateTime();
    }

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        
        protocolMessageRuleFactory = new SAML2ProtocolMessageRuleFactory();
        getPolicyRuleFactories().add(protocolMessageRuleFactory);
        
        policyFactory.setRequiredAuthenticatedIssuer(false);
    }

    /** {@inheritDoc} */
    protected XMLObject buildMessage() {
        Response response = (Response) buildXMLObject(Response.DEFAULT_ELEMENT_NAME);
        response.setID(messageID);
        response.setIssueInstant(issueInstant);
        response.setIssuer(buildIssuer());
        return response;
    }
    
    /**
     * Test basic message information extraction.
     */
    public void testRule() {
        assertPolicySuccess("Request protocol message rule");
        SAMLSecurityPolicyContext samlContext = (SAMLSecurityPolicyContext) policy.getSecurityPolicyContext();
        assertEquals("Unexpected value for extracted message ID", messageID, samlContext.getMessageID());
        assertTrue("Unexpected value for extracted message issue instant", 
                issueInstant.isEqual(samlContext.getIssueInstant()));
        assertEquals("Unexpected value for Issuer found", issuer, samlContext.getIssuer());
    }
    
    /**
     * Test basic message information extraction, null Issuer.
     */
    public void testRuleNoIssuer() {
        Response response = (Response) message;
        response.setIssuer(null);
        
        assertPolicySuccess("Request protocol message rule");
        SAMLSecurityPolicyContext samlContext = (SAMLSecurityPolicyContext) policy.getSecurityPolicyContext();
        assertEquals("Unexpected value for extracted message ID", messageID, samlContext.getMessageID());
        assertTrue("Unexpected value for extracted message issue instant", 
                issueInstant.isEqual(samlContext.getIssueInstant()));
        assertNull("Non-null value for Issuer found", samlContext.getIssuer());
    }
    
    /**
     * Test non-entity Issuer format.
     */
    public void testRuleNonEntityIssuer() {
        Issuer issuerXO = ((Response) message).getIssuer();
        issuerXO.setFormat(NameIDType.EMAIL);
        assertPolicyFail("Response protocol message rule, non-entity Issuer NameID format");
    }
    
    /**
     * Test message information extraction, with one Assertion containing Issuer.
     */
    public void testRuleWithAssertion() {
        Response response = (Response) message;
        response.setIssuer(null);
        
        Assertion assertion = (Assertion) buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
        Issuer issuerXO = buildIssuer();
        assertion.setIssuer(issuerXO);
        response.getAssertions().add(assertion);
        
        assertPolicySuccess("Request protocol message rule, with 1 assertion");
        SAMLSecurityPolicyContext samlContext = (SAMLSecurityPolicyContext) policy.getSecurityPolicyContext();
        assertEquals("Unexpected value for extracted message ID", messageID, samlContext.getMessageID());
        assertTrue("Unexpected value for extracted message issue instant", 
                issueInstant.isEqual(samlContext.getIssueInstant()));
        assertEquals("Unexpected value for extracted message issuer", issuer, samlContext.getIssuer());
    }

    /**
     * Test message information extraction, with two Assertions containing the same Issuer.
     */
    public void testRuleWithAssertions() {
        Response response = (Response) message;
        response.setIssuer(null);
        
        Assertion assertion = (Assertion) buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
        Issuer issuerXO = buildIssuer();
        assertion.setIssuer(issuerXO);
        response.getAssertions().add(assertion);
        
        assertion = (Assertion) buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
        issuerXO = buildIssuer();
        assertion.setIssuer(issuerXO);
        response.getAssertions().add(assertion);
        
        assertPolicySuccess("Request protocol message rule, with 2 assertions, same issuer");
        SAMLSecurityPolicyContext samlContext = (SAMLSecurityPolicyContext) policy.getSecurityPolicyContext();
        assertEquals("Unexpected value for extracted message ID", messageID, samlContext.getMessageID());
        assertTrue("Unexpected value for extracted message issue instant", 
                issueInstant.isEqual(samlContext.getIssueInstant()));
        assertEquals("Unexpected value for extracted message issuer", issuer, samlContext.getIssuer());
    }

    /**
     * Test message information extraction, with two Assertions containing different Issuers.
     */
    public void testRuleWithAssertionsDifferentIssuers() {
        Response response = (Response) message;
        response.setIssuer(null);
        
        Assertion assertion = (Assertion) buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
        Issuer issuerXO = buildIssuer();
        assertion.setIssuer(issuerXO);
        response.getAssertions().add(assertion);
        
        assertion = (Assertion) buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
        issuerXO = buildIssuer();
        issuerXO.setValue("NotTheSame" + issuer);
        assertion.setIssuer(issuerXO);
        response.getAssertions().add(assertion);
        
        assertPolicyFail("Request protocol message rule, with 2 assertions, different issuer");
    }
    
    /**
     * Build an Issuer with entity format.
     * 
     * @return a new Issuer
     */
    private Issuer buildIssuer() {
        Issuer issuerXO = (Issuer) buildXMLObject(Issuer.DEFAULT_ELEMENT_NAME);
        issuerXO.setValue(issuer);
        issuerXO.setFormat(NameIDType.ENTITY);
        return  issuerXO;
    }

}
