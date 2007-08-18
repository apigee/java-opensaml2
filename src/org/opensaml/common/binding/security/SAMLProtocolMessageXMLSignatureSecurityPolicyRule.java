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
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.security.SecurityPolicyException;
import org.opensaml.xml.security.trust.TrustEngine;
import org.opensaml.xml.signature.Signature;

/**
 * 
 * SAML security policy rule which validates the signature (if present) on the {@link SAMLObject} which represents the
 * SAML protocol message being processed. If the message is not an instance of {@link SignableSAMLObject}, then no
 * processing is performed. If signature validation is successful, and the SAML message context issuer was not
 * previously authenticated, then the context's issuer authentication state will be set to <code>true</code>.
 */
public class SAMLProtocolMessageXMLSignatureSecurityPolicyRule extends BaseSAMLSignatureSecurityPolicyRule {

    /** Logger. */
    private Logger log = Logger.getLogger(SAMLProtocolMessageXMLSignatureSecurityPolicyRule.class);

    /**
     * Constructor.
     * 
     * @param engine Trust engine used to verify the signature
     */
    public SAMLProtocolMessageXMLSignatureSecurityPolicyRule(TrustEngine<Signature> engine) {
        super(engine);
    }

    /** {@inheritDoc} */
    public boolean evaluate(MessageContext messageContext) throws SecurityPolicyException {
        if (!(messageContext instanceof SAMLMessageContext)) {
            log.debug("Invalid message context type, this policy rule only supports SAMLMessageContext");
            return false;
        }

        SAMLMessageContext samlMsgCtx = (SAMLMessageContext) messageContext;
        
        SAMLObject samlMsg = samlMsgCtx.getInboundSAMLMessage();
        if (!(samlMsg instanceof SignableSAMLObject)) {
            log.debug("Extracted SAML message was not a SignableSAMLObject, can not process signature");
            return false;
        }
        SignableSAMLObject signableObject = (SignableSAMLObject) samlMsg;
        if (!signableObject.isSigned()) {
            log.info("SAML protocol message was not signed, skipping XML signature processing");
            return false;
        }
        Signature signature = signableObject.getSignature();

        String contextIssuer = samlMsgCtx.getInboundMessageIssuer();
        if (contextIssuer != null) {
            String msgType = signableObject.getElementQName().toString();
            if (log.isDebugEnabled()) {
                log.debug("Attempting to verify signature on signed SAML protocol message using context issuer,"
                        + " message type: " + msgType);
            }

            if (evaluate(signature, contextIssuer, messageContext)) {
                log.info("Validation of protocol message signature succeeded, message type: " + msgType);
                if (! samlMsgCtx.isInboundSAMLMessageAuthenticated() ) {
                    log.info("Authentication via protocol message signature succeeded for "
                            + "context issuer entity ID '" + contextIssuer + "'");
                    samlMsgCtx.setInboundSAMLMessageAuthenticated(true);
                }
            } else {
                log.error("Validation of protocol message signature failed for context issuer '" + contextIssuer
                        + "', message type: " + msgType);
                throw new SecurityPolicyException("Validation of protocol message signature failed");
            }
        } else {
            // TODO should attempt to get valid signing creds from Signature/KeyInfo
            // and attempt to infer issuer, as in the client cert rule?
            // Need KeyInfo resolver + Credential or X509Credential TrustEngine
            log.error("Context issuer unavailable, can not attempt SAML protocol message signature validation");
            throw new SecurityPolicyException("Context issuer unavailable, can not validate signature");
        }
        
        return true;
    }
}