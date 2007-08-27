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
import org.opensaml.common.binding.security.BaseSAMLSecurityPolicyRuleTest;
import org.opensaml.saml2.core.AttributeQuery;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.saml2.core.Response;


/**
 * Test the protocol message rule for request types.
 */
public class RequestProtocolMessageRuleTest extends BaseSAMLSecurityPolicyRuleTest<AttributeQuery, Response, NameID> {
    
    private String issuer;
    private String messageID;
    private DateTime issueInstant;
    
    /** Constructor. */
    public RequestProtocolMessageRuleTest() {
        issuer = "SomeIssuerID";
        messageID = "abc123";
        issueInstant = new DateTime();
    }

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        
        rule = new SAML2ProtocolMessageRule();
    }

    /** {@inheritDoc} */
    protected AttributeQuery buildInboundSAMLMessage() {
        AttributeQuery query = (AttributeQuery) buildXMLObject(AttributeQuery.DEFAULT_ELEMENT_NAME);
        query.setID(messageID);
        query.setIssueInstant(issueInstant);
        query.setIssuer(buildIssuer());
        return query;
    }
    
    /**
     * Test basic message information extraction.
     */
    public void testRule() {
        assertRuleSuccess("Request protocol message rule");
        assertEquals("Unexpected value for extracted message ID", messageID, messageContext.getInboundSAMLMessageId());
        assertTrue("Unexpected value for extracted message issue instant", 
                issueInstant.isEqual(messageContext.getInboundSAMLMessageIssueInstant()));
        assertEquals("Unexpected value for Issuer found", issuer, messageContext.getInboundMessageIssuer());
    }
    
    /**
     * Test basic message information extraction, null Issuer.
     */
    public void testRuleNoIssuer() {
        messageContext.getInboundSAMLMessage().setIssuer(null);
        assertRuleSuccess("Request protocol message rule, null issuer");
        assertEquals("Unexpected value for extracted message ID", messageID, messageContext.getInboundSAMLMessageId());
        assertTrue("Unexpected value for extracted message issue instant", 
                issueInstant.isEqual(messageContext.getInboundSAMLMessageIssueInstant()));
        assertNull("Unexpected non-null value for Issuer found", messageContext.getInboundMessageIssuer());
    }

    /**
     * Test non-entity Issuer format.
     */
    public void testNonEntityIssuer() {
        Issuer issuerXO = messageContext.getInboundSAMLMessage().getIssuer();
        issuerXO.setFormat(NameIDType.EMAIL);
        assertRuleFailure("Request protocol message rule, non-entity Issuer NameID format");
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
