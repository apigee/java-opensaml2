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

import org.joda.time.DateTime;
import org.opensaml.ws.security.SecurityPolicyContext;
import org.opensaml.ws.security.SecurityPolicyException;
import org.opensaml.ws.security.SecurityPolicyRule;
import org.opensaml.ws.security.StaticIssuerRuleFactory;
import org.opensaml.xml.XMLObject;

/**
 * Factory for static rule for testing, to set issuer, message ID and issue instant in SAML security policy context.
 */
public class StaticProtocolMessageRuleFactory extends StaticIssuerRuleFactory {
    
    /** Static issue instant to set. */
    private DateTime issueInstant;
    
    /** Static message ID to set. */
    private String messageID;

    /**
     * Get the issue instant.
     * 
     * @return Returns the issueInstant.
     */
    public DateTime getIssueInstant() {
        return issueInstant;
    }

    /**
     * Set the issue instant.
     * 
     * @param newIssueInstant The issueInstant to set.
     */
    public void setIssueInstant(DateTime newIssueInstant) {
        this.issueInstant = newIssueInstant;
    }

    /**
     * Get the message ID.
     * 
     * @return Returns the messageID.
     */
    public String getMessageID() {
        return messageID;
    }

    /**
     * Set the message ID.
     * 
     * @param newMessageID The messageID to set.
     */
    public void setMessageID(String newMessageID) {
        this.messageID = newMessageID;
    }

    /** {@inheritDoc} */
    public SecurityPolicyRule<ServletRequest> createRuleInstance() {
        return new StaticProtocolMesageRule(getIssuer(), getIssuerAuthenticated(), getMessageID(), getIssueInstant());
    }

    /**
     * Static rule for testing, to set issuer, message ID and issue instant in SAML security policy context.
     */
    public class StaticProtocolMesageRule extends StaticIssuerRule {
        
        /** Static issue instant to set. */
        private DateTime issueInstant;
        
        /** Static message ID to set. */
        private String messageID;

        /**
         * Constructor.
         *
         * @param newIssuer the new issuer
         * @param newIssuerAuthenticated the new issuer authenticated state to set
         * @param newMessageID the new message ID
         * @param newIssueInstant the new issue instant
         */
        protected StaticProtocolMesageRule(String newIssuer, Boolean newIssuerAuthenticated, String newMessageID,
                DateTime newIssueInstant) {
            super(newIssuer, newIssuerAuthenticated);
            messageID = newMessageID;
            issueInstant = newIssueInstant;
        }

        /** {@inheritDoc} */
        public void evaluate(ServletRequest request, XMLObject message, SecurityPolicyContext context) 
                throws SecurityPolicyException {
            
            super.evaluate(request, message, context);

            SAMLSecurityPolicyContext samlContext = (SAMLSecurityPolicyContext) context;
            if (samlContext == null) {
                throw new IllegalArgumentException("Supplied context was not an instance of SAMLSecurityPolicyContext");
            }
            
            samlContext.setMessageID(messageID);
            samlContext.setIssueInstant(issueInstant);
        }
        
    }

}
