/*
 * Copyright [2006] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.saml2.binding.encoding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Signature;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import org.apache.log4j.Logger;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml2.core.RequestAbstractType;
import org.opensaml.saml2.core.Response;
import org.opensaml.util.URLBuilder;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.ws.transport.http.HTTPOutTransport;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.util.Pair;
import org.opensaml.xml.util.XMLHelper;

/**
 * SAML 2.0 HTTP Redirect encoder using the DEFLATE encoding method.
 * 
 * This encoder only supports DEFLATE compression and DSA-SHA1 and RSA-SHA1 signatures.
 */
public class HTTPRedirectDeflateEncoder extends BaseSAML2MessageEncoder {

    /** Class logger. */
    private static Logger log = Logger.getLogger(HTTPRedirectDeflateEncoder.class);

    /** {@inheritDoc} */
    public String getBindingURI() {
        return "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect";
    }

    /** {@inheritDoc} */
    protected void doEncode(MessageContext messageContext) throws MessageEncodingException {
        if (!(messageContext instanceof SAMLMessageContext)) {
            log.error("Invalid message context type, this encoder only support SAMLMessageContext");
            throw new MessageEncodingException(
                    "Invalid message context type, this encoder only support SAMLMessageContext");
        }

        if (!(messageContext.getMessageOutTransport() instanceof HTTPOutTransport)) {
            log.error("Invalid inbound message transport type, this encoder only support HTTPInTransport");
            throw new MessageEncodingException(
                    "Invalid inbound message transport type, this encoder only support HTTPInTransport");
        }

        SAMLMessageContext samlMsgCtx = (SAMLMessageContext) messageContext;

        String endpointURL = getEndpointURL(samlMsgCtx);
        
        setResponseDestination(samlMsgCtx.getOutboundSAMLMessage(), endpointURL);
        
        removeSignature(samlMsgCtx);

        String encodedMessage = defalteAndBase64Encode(samlMsgCtx.getOutboundSAMLMessage());
        
        String redirectURL = buildRedirectURL(samlMsgCtx, endpointURL, encodedMessage);
        
        // getResponse().setCharacterEncoding("UTF-8");
        // getResponse().addHeader("Cache-control", "no-cache, no-store");
        // getResponse().addHeader("Pragma", "no-cache");
        
        //send redirect
    }

    /**
     * Removes the signature from the protocol message.
     * 
     * @param messageContext current message context
     */
    protected void removeSignature(SAMLMessageContext messageContext) {
        SignableSAMLObject message = (SignableSAMLObject) messageContext.getOutboundSAMLMessage();
        if (message.isSigned()) {
            if (log.isDebugEnabled()) {
                log.debug("Removing SAML protocol message signature");
            }
            message.setSignature(null);
        }
    }

    /**
     * DEFLATE (RFC1951) compresses the given SAML message.
     * 
     * @param message SAML message
     * 
     * @return DEFLATE compressed message
     * 
     * @throws MessageEncodingException thrown if there is a problem compressing the message
     */
    protected String defalteAndBase64Encode(SAMLObject message) throws MessageEncodingException {
        if (log.isDebugEnabled()) {
            log.debug("Deflating and Base64 encoding SAML message");
        }
        try {
            String messageStr = XMLHelper.nodeToString(marshallMessage(message));

            ByteArrayOutputStream messageOut = new ByteArrayOutputStream();
            
            Base64.OutputStream b64Out = new Base64.OutputStream(messageOut, Base64.ENCODE & Base64.DONT_BREAK_LINES);
            
            Deflater deflater = new Deflater(Deflater.DEFLATED, true);
            DeflaterOutputStream deflaterStream = new DeflaterOutputStream(b64Out, deflater);
            deflaterStream.write(messageStr.getBytes());
            deflaterStream.close();
            
            return new String(messageOut.toByteArray());
        } catch (IOException e) {
            throw new MessageEncodingException("Unable to DEFLATE and Base64 encode SAML message", e);
        }
    }

    /**
     * Builds the URL to redirect the client to.
     * 
     * @param messagesContext current message context
     * @param endpointURL endpoint URL to send encoded message to
     * @param message Deflated and Base64 encoded message
     * 
     * @return URL to redirect client to
     * 
     * @throws MessageEncodingException thrown if the SAML message is neither a RequestAbstractType or Response
     */
    protected String buildRedirectURL(SAMLMessageContext messagesContext, String endpointURL, String message)
            throws MessageEncodingException {
        if (log.isDebugEnabled()) {
            log.debug("Building URL to redirect client to");
        }
        URLBuilder urlBuilder = new URLBuilder(endpointURL);

        List<Pair<String, String>> queryParams = urlBuilder.getQueryParams();
        queryParams.clear();

        if (messagesContext.getOutboundSAMLMessage() instanceof RequestAbstractType) {
            queryParams.add(new Pair<String, String>("SAMLRequest", message));
        } else if (messagesContext.getOutboundSAMLMessage() instanceof Response) {
            queryParams.add(new Pair<String, String>("SAMLResponse", message));
        } else {
            throw new MessageEncodingException("SAML message is neither a SAML RequestAbstractType or Response");
        }

        String relayState = messagesContext.getRelayState();
        if (checkRelayState(relayState)) {
            queryParams.add(new Pair<String, String>("RelayState", relayState));
        }

        Credential signingCredential = messagesContext.getOuboundSAMLMessageSigningCredential();
        if (signingCredential != null) {
            Pair<String, String> sigAlg = new Pair<String, String>("SigAlg", getSignatureAlgorithm(signingCredential));
            queryParams.add(sigAlg);
            String sigMaterial = urlBuilder.buildQueryString();

            queryParams.add(new Pair<String, String>("Signature", generateSignature(signingCredential, sigMaterial)));
        }

        return urlBuilder.buildURL();
    }

    /**
     * Gets the signature algorithm to use with the given signing credential.
     * 
     * @param credential the credential that will be used to sign the message
     * 
     * @return signature algorithm to use with the given signing credential
     * 
     * @throws MessageEncodingException thrown if the provided credential's private key is not an RSA or DSA key
     */
    protected String getSignatureAlgorithm(Credential credential) throws MessageEncodingException {
        if (credential.getPrivateKey() instanceof RSAPrivateKey) {
            return "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
        } else if (credential.getPrivateKey() instanceof DSAPrivateKey) {
            return "http://www.w3.org/2000/09/xmldsig#dsa-sha1";
        } else {
            throw new MessageEncodingException("Encoder only supports signing with RSA or DSA keys.");
        }
    }

    /**
     * Generates the signature over the query string.
     * 
     * @param signingCredential credential that will be used to sign query string
     * @param queryString query string to be signed
     * 
     * @return base64 encoded signature of query string
     * 
     * @throws MessageEncodingException there is an error computing the signature
     */
    protected String generateSignature(Credential signingCredential, String queryString)
            throws MessageEncodingException {
        Signature signature;

        if (log.isDebugEnabled()) {
            log.debug("Generating digital signature of query string");
        }

        String signingAlgo;
        if (signingCredential.getPrivateKey() instanceof RSAPrivateKey) {
            signingAlgo = "SHA1withRSA";
        } else {
            signingAlgo = "SHA1withDSA";
        }

        try {
            signature = Signature.getInstance(signingAlgo);
            signature.initSign(signingCredential.getPrivateKey());
            signature.update(queryString.getBytes());

            byte[] rawSignature = signature.sign();
            return Base64.encodeBytes(rawSignature);
        } catch (GeneralSecurityException e) {
            log.error("Error during URL signing process", e);
            throw new MessageEncodingException("Unable to sign URL query string", e);
        }
    }
}