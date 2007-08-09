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
import org.opensaml.util.storage.MapBasedStorageService;
import org.opensaml.util.storage.ReplayCache;
import org.opensaml.util.storage.ReplayCache.ReplayCacheEntry;

/**
 * Testing SAML message replay security policy rule.
 */
public class ReplayRuleTest extends BaseSAMLSecurityPolicyTest {
//    
//    private StaticProtocolMessageRuleFactory staticProtocolMessageFactory;
//    private ReplayRuleFactory replayRuleFactory;
//    
//    private int clockSkew;
//    private int expires;
//    
//    private DateTime now;
//    private String messageID;
//    
//    private ReplayCache replayCache;
//    
//
//    /** {@inheritDoc} */
//    protected void setUp() throws Exception {
//        super.setUp();
//        
//        messageID = "abc123";
//        now = new DateTime();
//        clockSkew = 60*5;
//        expires = 60*10;
//        
//        staticProtocolMessageFactory = new StaticProtocolMessageRuleFactory();
//        staticProtocolMessageFactory.setIssueInstant(now);
//        staticProtocolMessageFactory.setMessageID(messageID);
//        
//        replayRuleFactory = new ReplayRuleFactory();
//        replayRuleFactory.setClockSkew(clockSkew);
//        replayRuleFactory.setExpires(expires);
//        MapBasedStorageService<String, ReplayCacheEntry> storage = 
//            new MapBasedStorageService<String, ReplayCacheEntry>();
//        replayCache = new ReplayCache(storage, 60*10*1000);
//        replayRuleFactory.setReplayCache(replayCache);
//        
//        getPolicyRuleFactories().add(staticProtocolMessageFactory);
//        getPolicyRuleFactories().add(replayRuleFactory);
//    }
//    
//    /**
//     *  Test valid message ID.
//     */
//    public void testNoReplay() {
//        assertPolicySuccess("Message ID was valid");
//        checkPolicyContext();
//    }
//    
//    /**
//     *  Test valid message ID, distinct ID.
//     */
//    public void testNoReplayDistinctIDs() {
//        assertPolicySuccess("Message ID was valid");
//        checkPolicyContext();
//        
//        staticProtocolMessageFactory.setMessageID("someOtherID" + messageID);
//        assertPolicySuccess("Message ID was valid, distinct message ID");
//        checkPolicyContext();
//        
//    }
//    
//    /**
//     *  Test invalid replay of message ID.
//     */
//    public void testReplay() {
//        assertPolicySuccess("Message ID was valid");
//        checkPolicyContext();
//        
//        assertPolicyFail("Message ID was a replay");
//        checkPolicyContext();
//    }
//    
//    /**
//     *  Test valid replay of message ID due to replay cache expiration.
//     */
//    public void testReplayValidWithExpiration() {
//        // Set issue instant for first message to time in past so know will expire by 'now'.
//        staticProtocolMessageFactory.setIssueInstant( now.minusSeconds((clockSkew + expires) * 2));
//        assertPolicySuccess("Message ID was valid");
//        checkPolicyContext();
//        
//        // Set issue instant for second message to now
//        staticProtocolMessageFactory.setIssueInstant(now);
//        assertPolicySuccess("Message ID was valid, no replay due to expiration");
//        checkPolicyContext();
//        
//    }
//    
//    /**
//     * Just a sanity check to call after assertPolicy*().  Checks that expected things actually were
//     * in the policy context, so catches bad test setup.
//     */
//    private void checkPolicyContext() {
//        assertNotNull("Test setup error, message ID wasn't set in policy context",
//                ((SAMLSecurityPolicyContext)policy.getSecurityPolicyContext()).getMessageID());
//        assertNotNull("Test setup error, issue instant wasn't set in policy context",
//                ((SAMLSecurityPolicyContext)policy.getSecurityPolicyContext()).getIssueInstant());
//    }

}
