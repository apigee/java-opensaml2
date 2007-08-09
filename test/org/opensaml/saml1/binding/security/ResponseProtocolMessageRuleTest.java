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

package org.opensaml.saml1.binding.security;

import org.joda.time.DateTime;
import org.opensaml.common.binding.security.BaseSAMLSecurityPolicyTest;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.Response;
import org.opensaml.xml.XMLObject;


/**
 * Test the protocol message rule for request types.
 */
public class ResponseProtocolMessageRuleTest extends BaseSAMLSecurityPolicyTest {
//    
//    private String issuer;
//    private String messageID;
//    private DateTime issueInstant;
//    
//    private SAML1ProtocolMessageRuleFactory protocolMessageRuleFactory;
//    
//    /** Constructor. */
//    public ResponseProtocolMessageRuleTest() {
//        issuer = "SomeIssuerID";
//        messageID = "abc123";
//        issueInstant = new DateTime();
//    }
//
//    /** {@inheritDoc} */
//    protected void setUp() throws Exception {
//        super.setUp();
//        
//        protocolMessageRuleFactory = new SAML1ProtocolMessageRuleFactory();
//        getPolicyRuleFactories().add(protocolMessageRuleFactory);
//        
//        policyFactory.setRequiredAuthenticatedIssuer(false);
//    }
//
//    /** {@inheritDoc} */
//    protected XMLObject buildMessage() {
//        Response response = (Response) buildXMLObject(Response.DEFAULT_ELEMENT_NAME);
//        response.setID(messageID);
//        response.setIssueInstant(issueInstant);
//        return response;
//    }
//    
//    /**
//     * Test basic message information extraction.
//     */
//    public void testRule() {
//        assertPolicySuccess("Request protocol message rule");
//        SAMLSecurityPolicyContext samlContext = (SAMLSecurityPolicyContext) policy.getSecurityPolicyContext();
//        assertEquals("Unexpected value for extracted message ID", messageID, samlContext.getMessageID());
//        assertTrue("Unexpected value for extracted message issue instant", 
//                issueInstant.isEqual(samlContext.getIssueInstant()));
//        assertNull("Non-null value for Issuer found", samlContext.getIssuer());
//    }
//    
//    /**
//     * Test message information extraction, with one Assertion containing Issuer.
//     */
//    public void testRuleWithAssertion() {
//        Assertion assertion = (Assertion) buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
//        assertion.setIssuer(issuer);
//        ((Response) message).getAssertions().add(assertion);
//        
//        assertPolicySuccess("Request protocol message rule, with 1 assertion");
//        SAMLSecurityPolicyContext samlContext = (SAMLSecurityPolicyContext) policy.getSecurityPolicyContext();
//        assertEquals("Unexpected value for extracted message ID", messageID, samlContext.getMessageID());
//        assertTrue("Unexpected value for extracted message issue instant", 
//                issueInstant.isEqual(samlContext.getIssueInstant()));
//        assertEquals("Unexpected value for extracted message issuer", issuer, samlContext.getIssuer());
//    }
//
//    /**
//     * Test message information extraction, with two Assertions containing the same Issuer.
//     */
//    public void testRuleWithAssertions() {
//        Assertion assertion = (Assertion) buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
//        assertion.setIssuer(issuer);
//        ((Response) message).getAssertions().add(assertion);
//        assertion = (Assertion) buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
//        assertion.setIssuer(issuer);
//        ((Response) message).getAssertions().add(assertion);
//        
//        assertPolicySuccess("Request protocol message rule, with 2 assertions, same issuer");
//        SAMLSecurityPolicyContext samlContext = (SAMLSecurityPolicyContext) policy.getSecurityPolicyContext();
//        assertEquals("Unexpected value for extracted message ID", messageID, samlContext.getMessageID());
//        assertTrue("Unexpected value for extracted message issue instant", 
//                issueInstant.isEqual(samlContext.getIssueInstant()));
//        assertEquals("Unexpected value for extracted message issuer", issuer, samlContext.getIssuer());
//    }
//
//    /**
//     * Test message information extraction, with two Assertions containing different Issuers.
//     */
//    public void testRuleWithAssertionsDifferentIssuers() {
//        Assertion assertion = (Assertion) buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
//        assertion.setIssuer(issuer);
//        ((Response) message).getAssertions().add(assertion);
//        assertion = (Assertion) buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
//        assertion.setIssuer("NotTheSame" + issuer);
//        ((Response) message).getAssertions().add(assertion);
//        
//        assertPolicyFail("Request protocol message rule, with 2 assertions, different issuer");
//    }

}
