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
import org.joda.time.DateTimeComparator;
import org.opensaml.ws.security.SecurityPolicyContext;
import org.opensaml.ws.security.SecurityPolicyException;
import org.opensaml.ws.security.SecurityPolicyRule;
import org.opensaml.ws.security.SecurityPolicyRuleFactory;
import org.opensaml.xml.XMLObject;

/**
 * Security policy rule factory implementation that generates rules which check for validity of SAML message issue
 * instant date and time.
 */
public class IssueInstantRuleFactory implements SecurityPolicyRuleFactory<ServletRequest> {

    /**
     * Clock skew - the number of seconds before a lower time bound, or after an upper time bound, to consider still
     * acceptable.
     */
    private int clockSkew;

    /** Number of seconds after a message issue instant after which the message is considered expired. */
    private int expires;

    /**
     * Get the clock skew, in seconds.
     * 
     * @return the clock skew.
     */
    public int getClockSkew() {
        return clockSkew;
    }

    /**
     * Set the clock skew, in seconds.
     * 
     * @param newClockSkew the new clock skew to set.
     */
    public void setClockSkew(int newClockSkew) {
        this.clockSkew = newClockSkew;
    }

    /**
     * Get the issue instant expiration, in seconds from time of issuance.
     * 
     * @return the expiration in seconds
     */
    public int getExpires() {
        return expires;
    }

    /**
     * Set the issue instant expiration, in seconds from time of issuance.
     * 
     * @param newExpires the expiration in seconds
     */
    public void setExpires(int newExpires) {
        this.expires = newExpires;
    }

    /** {@inheritDoc} */
    public SecurityPolicyRule<ServletRequest> createRuleInstance() {
        return new IssueInstantRule(clockSkew, expires);
    }

    /**
     * Security policy rule implementation that which checks for validity of SAML message issue instant date and time.
     */
    public class IssueInstantRule implements SecurityPolicyRule<ServletRequest> {

        /**
         * Clock skew - the number of seconds before a lower time bound, or after an upper time bound, to consider still
         * acceptable.
         */
        private int clockSkew;

        /** Number of seconds after a message issue instant after which the message is considered expired. */
        private int expires;

        /**
         * Constructor.
         * 
         * @param newClockSkew the new clock skew value
         * @param newExpires the new expiration value
         */
        public IssueInstantRule(int newClockSkew, int newExpires) {
            clockSkew = newClockSkew;
            expires = newExpires;
        }

        /** {@inheritDoc} */
        public void evaluate(ServletRequest request, XMLObject message, SecurityPolicyContext context)
                throws SecurityPolicyException {

            Logger log = Logger.getLogger(IssueInstantRule.class);

            SAMLSecurityPolicyContext samlContext = (SAMLSecurityPolicyContext) context;
            if (samlContext == null) {
                log.error("Supplied context was not an instance of SAMLSecurityPolicyContext");
                throw new IllegalArgumentException("Supplied context was not an instance of SAMLSecurityPolicyContext");
            }

            if (samlContext.getIssueInstant() == null) {
                log.debug("Message did not contain an issue instant, skipping evaluation");
                return;
            }

            DateTime now = new DateTime();
            DateTime latestValid = now.plusSeconds(clockSkew);
            DateTime expiration = samlContext.getIssueInstant().plusSeconds(clockSkew + expires);

            DateTimeComparator dtCompare = DateTimeComparator.getInstance();

            // Check message wasn't issued in the future
            if (dtCompare.compare(latestValid, samlContext.getIssueInstant()) < 0) {
                log.error("Message was not yet valid: message time was '" + samlContext.getIssueInstant()
                        + "', latest valid is: '" + latestValid + "'");
                throw new SecurityPolicyException("Message was rejected because was issued in the future");
            }

            // Check message has not expired
            if (dtCompare.compare(expiration, now) < 0) {
                log.error("Message was expired: message issue time was '" + samlContext.getIssueInstant()
                        + "', message expired at: '" + expiration + "', current time: '" + now + "'");
                throw new SecurityPolicyException("Message was rejected due to issue instant expiration");
            }

        }
    }

}