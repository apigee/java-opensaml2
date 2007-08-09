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
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.ws.security.SecurityPolicyException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.trust.TrustEngine;
import org.opensaml.xml.signature.Signature;

/**
 * 
 * SAML security policy rule which validates the signature (if present) on the {@link SAMLObject} which represents the
 * SAML protocol message being processed. If the message is not an instance of {@link SignableSAMLObject}, then no
 * processing is performed. If signature validation is successful, and the context issuer was not previously
 * authenticated, then the context's issuer authentication state will be set to <code>true</code>.
 */
public class SAMLProtocolMessageXMLSignatureSecurityPolicyRule {

//    /** Logger. */
//    private Logger log = Logger.getLogger(SAMLProtocolMessageXMLSignatureSecurityPolicyRule.class);
//
//    /**
//     * Constructor.
//     * 
//     * @param engine Trust engine used to verify the signature
//     */
//    public SAMLProtocolMessageXMLSignatureSecurityPolicyRule(TrustEngine<Signature> engine) {
//        super(engine);
//    }
//
//    /** {@inheritDoc} */
//    public void evaluate(ServletRequest request, XMLObject message, SecurityPolicyContext context)
//            throws SecurityPolicyException {
//
//        SAMLObject samlMsg = SAMLSecurityPolicyHelper.getSAMLMessage(message);
//        if (samlMsg == null) {
//            log.debug("Could not extract SAML message");
//            return;
//        }
//        if (!(samlMsg instanceof SignableSAMLObject)) {
//            log.debug("Extracted SAML message was not a SignableSAMLObject, can not process signature");
//            return;
//        }
//        SignableSAMLObject signableObject = (SignableSAMLObject) samlMsg;
//        if (!signableObject.isSigned()) {
//            log.info("SAML protocol message was not signed, skipping XML signature processing");
//            return;
//        }
//        Signature signature = signableObject.getSignature();
//
//        String contextIssuer = context.getIssuer();
//        if (contextIssuer != null) {
//            String msgType = signableObject.getElementQName().toString();
//            if (log.isDebugEnabled()) {
//                log.debug("Attempting to verify signature on signed SAML protocol message using context issuer,"
//                        + " message type: " + msgType);
//            }
//
//            CriteriaSet criteriaSet = buildCriteriaSet(context.getIssuer(), request, message, context);
//            if (evaluate(signature, criteriaSet)) {
//                log.info("Validation of protocol message signature succeeded, message type: " + msgType);
//                if (context.isIssuerAuthenticated() != Boolean.TRUE) {
//                    log.info("Authentication via protocol message signature succeeded for "
//                            + "context issuer entity ID '" + contextIssuer + "'");
//                    context.setIssuerAuthenticated(true);
//                }
//            } else {
//                log.error("Validation of protocol message signature failed for context issuer '" + contextIssuer
//                        + "', message type: " + msgType);
//                throw new SecurityPolicyException("Validation of protocol message signature failed");
//            }
//        } else {
//            // TODO should attempt to get valid signing creds from Signature/KeyInfo
//            // and attempt to infer issuer, as in the client cert rule?
//            // Need KeyInfo resolver + Credential or X509Credential TrustEngine
//            log.error("Context issuer unavailable, can not attempt SAML protocol message signature validation");
//            throw new SecurityPolicyException("Context issuer unavailable, can not validate signature");
//        }
//
//    }
}