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

import org.apache.log4j.Logger;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.util.storage.ReplayCache;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.security.SecurityPolicyException;
import org.opensaml.ws.security.SecurityPolicyRule;

/**
 * Security policy rule factory implementation that generates rules which check for replay of SAML messages.
 */
public class MessageReplayRule implements SecurityPolicyRule {

    /** Logger. */
    private static Logger log = Logger.getLogger(MessageReplayRule.class);

    /** Expiration value for messages inserted into replay cache, in seconds. */
    private int expires;

    /** Messge replay cache instance to use. */
    private ReplayCache replayCache;

    /**
     * Constructor.
     * 
     * @param newClockSkew allowed clock skew in seconds
     * @param newExpires replay message expiration duration in seconds
     * @param newReplayCache the new replay cache instance
     */
    public MessageReplayRule(int newClockSkew, int newExpires, ReplayCache newReplayCache) {
        expires = (newExpires + newClockSkew) * 1000;
        replayCache = newReplayCache;
    }

    /** {@inheritDoc} */
    public boolean evaluate(MessageContext messageContext) throws SecurityPolicyException {
        if (!(messageContext instanceof MessageContext)) {
            log.debug("Invalid message context type, this policy rule only support SAMLMessageContext");
            return false;
        }

        SAMLMessageContext samlMsgCtx = (SAMLMessageContext) messageContext;

        if (samlMsgCtx.getInboundSAMLMessageId() == null) {
            log.error("Message contained no ID, replay check not possible");
            return false;
        }

        if (replayCache.isReplay(samlMsgCtx.getInboundSAMLMessageId(), expires)) {
            log.error("Replay detected of message '" + samlMsgCtx.getInboundSAMLMessageId() + "'");
            throw new SecurityPolicyException("Rejecting replayed message ID '" + samlMsgCtx.getInboundSAMLMessageId()
                    + "'");
        }

        return true;
    }
}