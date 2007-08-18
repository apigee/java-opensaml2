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
import org.opensaml.common.binding.security.BaseSAMLSecurityPolicyRuleTest;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.NameIdentifier;
import org.opensaml.saml1.core.Request;
import org.opensaml.saml1.core.Response;
import org.opensaml.ws.message.BaseMessageContext;
import org.opensaml.ws.security.SecurityPolicyException;


/**
 * Test the protocol message rule for request types.
 */
public class ResponseProtocolMessageRuleTest extends BaseSAMLSecurityPolicyRuleTest<Response, Request, NameIdentifier> {
    
    private String issuer;
    private String messageID;
    private DateTime issueInstant;
    
    /** Constructor. */
    public ResponseProtocolMessageRuleTest() {
        issuer = "SomeIssuerID";
        messageID = "abc123";
        issueInstant = new DateTime();
    }

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        
        rule = new SAML1ProtocolMessageRule();
    }

    /** {@inheritDoc} */
    protected Response buildInboundSAMLMessage() {
        Response response = (Response) buildXMLObject(Response.DEFAULT_ELEMENT_NAME);
        response.setID(messageID);
        response.setIssueInstant(issueInstant);
        return response;
    }
    
    /**
     * Test basic message information extraction.
     */
    public void testRule() {
        assertRuleSuccess("Request protocol message rule");
        assertEquals("Unexpected value for extracted message ID", messageID, messageContext.getInboundSAMLMessageId());
        assertTrue("Unexpected value for extracted message issue instant", 
                issueInstant.isEqual(messageContext.getInboundSAMLMessageIssueInstant()) );
        assertNull("Non-null value for Issuer found", messageContext.getInboundMessageIssuer());
    }
    
    /**
     * Test message information extraction, with one Assertion containing Issuer.
     */
    public void testRuleWithAssertion() {
        Response response = messageContext.getInboundSAMLMessage();
        
        Assertion assertion = (Assertion) buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
        assertion.setIssuer(issuer);
        response.getAssertions().add(assertion);
        
        assertRuleSuccess("Request protocol message rule, with 1 assertion");
        assertEquals("Unexpected value for extracted message ID", messageID, messageContext.getInboundSAMLMessageId());
        assertTrue("Unexpected value for extracted message issue instant", 
                issueInstant.isEqual(messageContext.getInboundSAMLMessageIssueInstant()) );
        assertEquals("Unexpected value for extracted message issuer", issuer, messageContext.getInboundMessageIssuer());
    }

    /**
     * Test message information extraction, with two Assertions containing the same Issuer.
     */
    public void testRuleWithAssertions() {
        Response response = messageContext.getInboundSAMLMessage();
        
        Assertion assertion = (Assertion) buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
        assertion.setIssuer(issuer);
        response.getAssertions().add(assertion);
        
        assertion = (Assertion) buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
        assertion.setIssuer(issuer);
        response.getAssertions().add(assertion);
        
        assertRuleSuccess("Request protocol message rule, with 2 assertions, same issuer");
        assertEquals("Unexpected value for extracted message ID", messageID, messageContext.getInboundSAMLMessageId());
        assertTrue("Unexpected value for extracted message issue instant", 
                issueInstant.isEqual(messageContext.getInboundSAMLMessageIssueInstant()) );
        assertEquals("Unexpected value for extracted message issuer", issuer, messageContext.getInboundMessageIssuer());
    }

    /**
     * Test message information extraction, with two Assertions containing different Issuers.
     */
    public void testRuleWithAssertionsDifferentIssuers() {
        Response response = messageContext.getInboundSAMLMessage();
        
        Assertion assertion = (Assertion) buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
        assertion.setIssuer(issuer);
        response.getAssertions().add(assertion);
        
        assertion = (Assertion) buildXMLObject(Assertion.DEFAULT_ELEMENT_NAME);
        assertion.setIssuer("someOther" + issuer);
        response.getAssertions().add(assertion);
        
        assertRuleFailure("Request protocol message rule, with 2 assertions, different issuer");
    }
    
    /**
     * A non-SAMLMessageContext results in rule not being evaluated.
     * @throws SecurityPolicyException 
     * 
     */
    public void testNotEvaluated() throws SecurityPolicyException {
        assertFalse("Rule should not have been evaluated, non-SAMLMessageContext",
                rule.evaluate(new BaseMessageContext()));
    }

}
