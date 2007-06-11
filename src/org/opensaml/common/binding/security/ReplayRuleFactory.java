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

import javax.servlet.ServletRequest;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.opensaml.util.storage.ReplayCache;
import org.opensaml.ws.security.SecurityPolicyContext;
import org.opensaml.ws.security.SecurityPolicyException;
import org.opensaml.ws.security.SecurityPolicyRule;
import org.opensaml.ws.security.SecurityPolicyRuleFactory;
import org.opensaml.xml.XMLObject;

/**
 * Security policy rule factory implementation that generates rules which check for replay of SAML messages.
 */
public class ReplayRuleFactory implements SecurityPolicyRuleFactory<ServletRequest> {

    /** Clock skew to use, in seconds. */
    private int clockSkew;

    /** Expiration value for messages inserted into replay cache, in seconds. */
    private int expires;

    /** Messge replay cache instance to use. */
    private ReplayCache replayCache;

    /**
     * Get the clock skew.
     * 
     * @return the clock skew
     */
    public int getClockSkew() {
        return clockSkew;
    }

    /**
     * Set the clock skew.
     * 
     * @param newClockSkew the clock skew
     */
    public void setClockSkew(int newClockSkew) {
        this.clockSkew = newClockSkew;
    }

    /**
     * Get the message replay cache expiration, in seconds.
     * 
     * @return the expiration value
     */
    public int getExpires() {
        return expires;
    }

    /**
     * Set the message replay cache expiration, in seconds.
     * 
     * @param newExpires the new expiration value
     */
    public void setExpires(int newExpires) {
        this.expires = newExpires;
    }

    /**
     * Get the message replay cache instance.
     * 
     * @return the message replay cache instance
     */
    public ReplayCache getReplayCache() {
        return replayCache;
    }

    /**
     * Set the message replay cache instance.
     * 
     * @param newReplayCache The replayCache to set.
     */
    public void setReplayCache(ReplayCache newReplayCache) {
        this.replayCache = newReplayCache;
    }

    /** {@inheritDoc} */
    public SecurityPolicyRule<ServletRequest> createRuleInstance() {
        return new ReplayRule(clockSkew, expires, replayCache);
    }

    /**
     * Security policy rule implementation which checks for replay of SAML messages.
     */
    public class ReplayRule implements SecurityPolicyRule<ServletRequest> {

        /** Clock skew to use, in seconds. */
        private int clockSkew;

        /** Expiration value for messages inserted into replay cache, in seconds. */
        private int expires;

        /** Messge replay cache instance to use. */
        private ReplayCache replayCache;

        /**
         * Constructor.
         * 
         * @param newClockSkew the new clock skew
         * @param newExpires the new expiration value
         * @param newReplayCache the new replay cache instance
         */
        public ReplayRule(int newClockSkew, int newExpires, ReplayCache newReplayCache) {
            clockSkew = newClockSkew;
            expires = newExpires;
            replayCache = newReplayCache;
        }

        /** {@inheritDoc} */
        public void evaluate(ServletRequest request, XMLObject message, SecurityPolicyContext context)
                throws SecurityPolicyException {

            Logger log = Logger.getLogger(ReplayRule.class);

            if (replayCache == null) {
                log.warn("No replay cache configured, skipping message replay check");
                return;
            }

            SAMLSecurityPolicyContext samlContext = (SAMLSecurityPolicyContext) context;
            if (samlContext == null) {
                log.error("Supplied context was not an instance of SAMLSecurityPolicyContext");
                throw new IllegalArgumentException("Supplied context was not an instance of SAMLSecurityPolicyContext");
            }

            if (samlContext.getMessageID() == null) {
                log.debug("Message contained no ID, replay check not possible");
                return;
            }

            DateTime issueInstant = samlContext.getIssueInstant();

            if (issueInstant == null) {
                log.debug("Message did not contain issue instant, using current time for replay checking");
                issueInstant = new DateTime();
            }

            if (!replayCache.isReplay(samlContext.getMessageID(), samlContext.getIssueInstant().plusSeconds(
                    clockSkew + expires))) {

                log.error("Replay detected of message '" + samlContext.getMessageID() + "'");
                throw new SecurityPolicyException("Rejecting replayed message ID '" + samlContext.getMessageID() + "'");
            }

        }
    }
}