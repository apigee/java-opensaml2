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

import org.opensaml.saml2.core.AttributeQuery;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.Response;
import org.opensaml.util.storage.MapBasedStorageService;
import org.opensaml.util.storage.ReplayCache;
import org.opensaml.util.storage.ReplayCache.ReplayCacheEntry;
import org.opensaml.ws.message.BaseMessageContext;
import org.opensaml.ws.security.SecurityPolicyException;

/**
 * Testing SAML message replay security policy rule.
 */
public class MessageReplayRuleTest extends BaseSAMLSecurityPolicyRuleTest<AttributeQuery, Response, NameID> {
    
    private int clockSkew;
    private int expires;
    
    private String messageID;
    
    private ReplayCache replayCache;
    

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        
        messageID = "abc123";
        clockSkew = 60*5;
        expires = 60*10;
        
        messageContext.setInboundSAMLMessageId(messageID);
        
        MapBasedStorageService<String, ReplayCacheEntry> storage = 
            new MapBasedStorageService<String, ReplayCacheEntry>();
        replayCache = new ReplayCache(storage, 60*10*1000);
        rule = new MessageReplayRule(clockSkew, expires, replayCache);
    }
    
    /**
     *  Test valid message ID.
     */
    public void testNoReplay() {
        assertRuleSuccess("Message ID was valid");
    }
    
    /**
     *  Test valid message ID, distinct ID.
     */
    public void testNoReplayDistinctIDs() {
        assertRuleSuccess("Message ID was valid");
        
        messageContext.setInboundSAMLMessageId("someOther" + messageID);
        assertRuleSuccess("Message ID was valid, distinct message ID");
        
    }
    
    /**
     *  Test invalid replay of message ID.
     */
    public void testReplay() {
        assertRuleSuccess("Message ID was valid");
        
        assertRuleFailure("Message ID was a replay");
    }
    
    /**
     *  Test valid replay of message ID due to replay cache expiration.
     * @throws InterruptedException 
     */
    public void testReplayValidWithExpiration() throws InterruptedException {
        //This isn't perfect, but is the only way to test since
        //expiration is determined internally in the rule, as offset 
        //from current time...
        
        //Set rule with 3 second expiration, with no clock skew
        rule = new MessageReplayRule(0, 3, replayCache);
        assertRuleSuccess("Message ID was valid");
        
        // Now sleep for 5 seconds to be sure has expired, and retry same message id
        Thread.sleep(5 * 1000);
        assertRuleSuccess("Message ID was valid, no replay due to expiration");
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
