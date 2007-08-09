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
import org.opensaml.saml2.core.AttributeQuery;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.xml.XMLObject;


/**
 * Test the protocol message rule for request types.
 */
public class RequestProtocolMessageRuleTest extends BaseSAMLSecurityPolicyTest {
    
//    private String issuer;
//    private String messageID;
//    private DateTime issueInstant;
//    
//    private SAML2ProtocolMessageRuleFactory protocolMessageRuleFactory;
//    
//    /** Constructor. */
//    public RequestProtocolMessageRuleTest() {
//        issuer = "SomeIssuerID";
//        messageID = "abc123";
//        issueInstant = new DateTime();
//    }
//
//    /** {@inheritDoc} */
//    protected void setUp() throws Exception {
//        super.setUp();
//        
//        protocolMessageRuleFactory = new SAML2ProtocolMessageRuleFactory();
//        getPolicyRuleFactories().add(protocolMessageRuleFactory);
//        
//        policyFactory.setRequiredAuthenticatedIssuer(false);
//    }
//
//    /** {@inheritDoc} */
//    protected XMLObject buildMessage() {
//        AttributeQuery query = (AttributeQuery) buildXMLObject(AttributeQuery.DEFAULT_ELEMENT_NAME);
//        query.setID(messageID);
//        query.setIssueInstant(issueInstant);
//        query.setIssuer(buildIssuer());
//        return query;
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
//        assertEquals("Unexpected value for Issuer found", issuer, samlContext.getIssuer());
//    }
//    
//    /**
//     * Test basic message information extraction, null Issuer.
//     */
//    public void testRuleNoIssuer() {
//        ((AttributeQuery)message).setIssuer(null);
//        assertPolicySuccess("Request protocol message rule");
//        SAMLSecurityPolicyContext samlContext = (SAMLSecurityPolicyContext) policy.getSecurityPolicyContext();
//        assertEquals("Unexpected value for extracted message ID", messageID, samlContext.getMessageID());
//        assertTrue("Unexpected value for extracted message issue instant", 
//                issueInstant.isEqual(samlContext.getIssueInstant()));
//        assertNull("Non-null value for Issuer found", samlContext.getIssuer());
//    }
//
//    /**
//     * Test non-entity Issuer format.
//     */
//    public void testNonEntityIssuer() {
//        Issuer issuerXO = ((AttributeQuery)message).getIssuer();
//        issuerXO.setFormat(NameIDType.EMAIL);
//        assertPolicyFail("Request protocol message rule, non-entity Issuer NameID format");
//    }
//    
//    /**
//     * Build an Issuer with entity format.
//     * 
//     * @return a new Issuer
//     */
//    private Issuer buildIssuer() {
//        Issuer issuerXO = (Issuer) buildXMLObject(Issuer.DEFAULT_ELEMENT_NAME);
//        issuerXO.setValue(issuer);
//        issuerXO.setFormat(NameIDType.ENTITY);
//        return  issuerXO;
//    }

}
