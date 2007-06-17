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

package org.opensaml.common.binding.security;

import org.joda.time.DateTime;

/**
 * Testing SAML issue instant security policy rule.
 */
public class IssueInstantRuleTest extends BaseSAMLSecurityPolicyTest {
    
    private StaticProtocolMessageRuleFactory staticProtocolMessageFactory;
    
    private IssueInstantRuleFactory issueInstantRuleFactory;
    
    private int clockSkew;
    private int expires;
    
    private DateTime now;

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        
        now = new DateTime();
        clockSkew = 60*5;
        expires = 60*10;
        
        staticProtocolMessageFactory = new StaticProtocolMessageRuleFactory();
        staticProtocolMessageFactory.setIssueInstant(now);
        
        issueInstantRuleFactory = new IssueInstantRuleFactory();
        issueInstantRuleFactory.setClockSkew(clockSkew);
        issueInstantRuleFactory.setExpires(expires);
        
        getPolicyRuleFactories().add(staticProtocolMessageFactory);
        getPolicyRuleFactories().add(issueInstantRuleFactory);
    }
    
    /**
     *  Test valid issue instant.
     */
    public void testValid() {
        assertPolicySuccess("Message issue instant was valid");
        
        assertNotNull("Test setup error, issue instant wasn't set in policy context",
                ((SAMLSecurityPolicyContext)policy.getSecurityPolicyContext()).getIssueInstant());
    }
    
    /**
     * Test invalid when issued in future, beyond allowed clock skew.
     */
    public void testInvalidIssuedInFuture() {
        staticProtocolMessageFactory.setIssueInstant(now.plusSeconds(clockSkew + 5));
        assertPolicyFail("Message issue instant was in the future");
        
        assertNotNull("Test setup error, issue instant wasn't set in policy context",
                ((SAMLSecurityPolicyContext)policy.getSecurityPolicyContext()).getIssueInstant());
    }
    
    /**
     *  Test valid when issued in future, but within allowed clock skew.
     */
    public void testValidIssuedInFutureWithinClockSkew() {
        staticProtocolMessageFactory.setIssueInstant(now.plusSeconds(clockSkew - 5));
        assertPolicySuccess("Message issue instant was in the future but within clock skew");
        
        assertNotNull("Test setup error, issue instant wasn't set in policy context",
                ((SAMLSecurityPolicyContext)policy.getSecurityPolicyContext()).getIssueInstant());
    }
    
    /**
     * Test invalid when expired, beyond allowed clock skew.
     */
    public void testInvalidExpired() {
        staticProtocolMessageFactory.setIssueInstant(now.minusSeconds(expires + (clockSkew + 5)));
        assertPolicyFail("Message issue instant was expired");
        
        assertNotNull("Test setup error, issue instant wasn't set in policy context",
                ((SAMLSecurityPolicyContext)policy.getSecurityPolicyContext()).getIssueInstant());
    }
    
    /**
     *  Test valid when expired, but within allowed clock skew.
     */
    public void testValidExpiredWithinClockSkew() {
        staticProtocolMessageFactory.setIssueInstant(now.minusSeconds(expires + (clockSkew - 5)));
        assertPolicySuccess("Message issue instant was expired but within clock skew");
        
        assertNotNull("Test setup error, issue instant wasn't set in policy context",
                ((SAMLSecurityPolicyContext)policy.getSecurityPolicyContext()).getIssueInstant());
    }

}
