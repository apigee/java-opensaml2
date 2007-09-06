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
import org.opensaml.xml.util.DatatypeHelper;

/**
 * Security policy rule implementation that which checks for replay of SAML messages.
 */
public class MessageReplayRule implements SecurityPolicyRule {

    /** Logger. */
    private static Logger log = Logger.getLogger(MessageReplayRule.class);

    /** Messge replay cache instance to use. */
    private ReplayCache replayCache;

    /**
     * Constructor.
     * 
     * @param newReplayCache the new replay cache instance
     */
    public MessageReplayRule(ReplayCache newReplayCache) {
        replayCache = newReplayCache;
    }

    /** {@inheritDoc} */
    public void evaluate(MessageContext messageContext) throws SecurityPolicyException {
        if (!(messageContext instanceof SAMLMessageContext)) {
            log.debug("Invalid message context type, this policy rule only supports SAMLMessageContext");
            return;
        }

        SAMLMessageContext samlMsgCtx = (SAMLMessageContext) messageContext;

        String messageIsuer = samlMsgCtx.getInboundMessageIssuer();
        if (DatatypeHelper.isEmpty(messageIsuer)) {
            log.error("Message contained no Issuer ID, replay check not possible");
            return;
        }

        String messageId = samlMsgCtx.getInboundSAMLMessageId();
        if (DatatypeHelper.isEmpty(messageId)) {
            log.error("Message contained no ID, replay check not possible");
            return;
        }

        if (replayCache.isReplay(messageIsuer, messageId)) {
            log.error("Replay detected of message '" + messageId + "' from issuer " + messageIsuer);
            throw new SecurityPolicyException("Rejecting replayed message ID '" + messageId + "' from issuer "
                    + messageIsuer);
        }

    }
}