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

package org.opensaml.saml2.binding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import org.apache.log4j.Logger;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.impl.AbstractHTTPMessageEncoder;
import org.opensaml.saml2.core.RequestAbstractType;
import org.opensaml.saml2.core.Response;
import org.opensaml.ws.util.URLBuilder;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.util.Pair;

/**
 * SAML 2.0 HTTP Redirect encoder using the DEFLATE encoding method.
 * 
 * This encoder only supports DEFLATE compression and DSA-SHA1 and RSA-SHA1 signatures.
 */
public class HTTPRedirectDeflateEncoder extends AbstractHTTPMessageEncoder {

    /** DSA with SHA1 signature algorithm */
    public final static String DSA_SHA1_SIGNATURE = "http://www.w3.org/2000/09/xmldsig#dsa-sha1";

    /** RSA with SHA1 signature algorithm */
    public final static String RSA_SHA1_SIGNATURE = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
    
    /** Class logger */
    private final static Logger log = Logger.getLogger(HTTPRedirectDeflateEncoder.class);

    /** URL to send message to via HTTP redirect */
    private String redirectURL;

    /** Algorithm used to sign message */
    private String signatureAlgorithm;

    /** Keys used to sign message */
    private PrivateKey signingKey;

    /** Whether to sign URL params */
    private boolean signMessage = false;

    /**
     * Gets the endpoint to redirect the message to.
     * 
     * @return endpoint to redirect the message to
     */
    public String getEndpointURL() {
        return redirectURL;
    }

    /**
     * Sets the endpoint to redirect the message to.
     * 
     * @param url endpoint to redirect the message to
     */
    public void setEndpointURL(String url) {
        redirectURL = url;
    }

    /**
     * Gets the signature algorithm that will be used to sign the message.
     * 
     * @return signature algorithm that will be used to sign the message
     */
    public String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    /**
     * Sets the signature algorithm that will be used to sign the message.
     * 
     * @param algorithm signature algorithm that will be used to sign the message
     */
    public void setSignatureAlgorithm(String algorithm) {
        signatureAlgorithm = algorithm;
    }

    /**
     * Gets the key used to sign the message.
     * 
     * @return key used to sign the message
     */
    public PrivateKey getSigningKey() {
        return signingKey;
    }

    /**
     * Sets the key used to sign the message.
     * 
     * @param key key used to sign the message
     */
    public void setSigningKey(PrivateKey key) {
        signingKey = key;
    }

    /**
     * Gets whether the message was signed per the redirect binding specification.
     * 
     * @return whether the message was signed per the redirect binding specification
     */
    public boolean isSigned() {
        return signMessage;
    }

    /** {@inheritDoc} */
    public void encode() throws BindingException {
        if(log.isDebugEnabled()){
            log.debug("Beginning SAML 2 HTTP Redirect encoding");
        }
        
        removeSignature();
        
        byte[] encodedMessage = defalteAndBase64Encode(getSAMLMessage());
        
        String redirectURL = buildRedirectURL(new String(encodedMessage));

        try {
            if(log.isDebugEnabled()){
                log.debug("Redirect encoding complete, redirecting client to " + redirectURL);
            }
            getResponse().setCharacterEncoding("UTF-8");
            getResponse().addHeader("Cache-control", "no-cache, no-store");
            getResponse().addHeader("Pragma", "no-cache");
            getResponse().sendRedirect(redirectURL);
        } catch (IOException e) {
            log.error("Unable to redirect client to " + redirectURL, e);
            throw new BindingException("Unable to redirect client", e);
        }
    }

    /**
     * Removes the signature from the protocol message.
     */
    protected void removeSignature() {
        SignableSAMLObject message = (SignableSAMLObject) getSAMLMessage();
        if(message.isSigned()){
            if(log.isDebugEnabled()){
                log.debug("Removing SAML protocol message signature");
            }
            
            signMessage = true;
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
     * @throws BindingException thrown if there is a problem compressing the message
     */
    protected byte[] defalteAndBase64Encode(SAMLObject message) throws BindingException {
        if(log.isDebugEnabled()){
            log.debug("Deflating and Base64 encoding SAML message");
        }
        try {
            String messageStr = marshallMessage(message);

            ByteArrayOutputStream messageOut = new ByteArrayOutputStream();
            Base64.OutputStream b64Out = new Base64.OutputStream(messageOut);
            Deflater deflater = new Deflater(Deflater.DEFLATED, true);
            DeflaterOutputStream deflaterStream = new DeflaterOutputStream(b64Out, deflater);
            deflaterStream.write(messageStr.getBytes());
            deflaterStream.close();
            return messageOut.toByteArray();
        } catch (IOException e) {
            throw new BindingException("Unable to DEFLATE and Base64 encode SAML message", e);
        }
    }

    /**
     * Builds the URL to redirect the client to.
     * 
     * @param message base64 encoded SAML message
     * 
     * @return URL to redirect client to
     * 
     * @throws BindingException thrown if the SAML message is neither a RequestAbstractType or Response
     */
    protected String buildRedirectURL(String message) throws BindingException {
        if(log.isDebugEnabled()){
            log.debug("Building URL to redirect client to");
        }
        URLBuilder urlBuilder = new URLBuilder(getEndpointURL());

        List<Pair<String, String>> queryParams = urlBuilder.getQueryParams();
        queryParams.clear();

        if (getSAMLMessage() instanceof RequestAbstractType) {
            queryParams.add(new Pair<String, String>("SAMLRequest", message));
        } else if (getSAMLMessage() instanceof Response) {
            queryParams.add(new Pair<String, String>("SAMLResponse", message));
        } else {
            throw new BindingException("SAML message is neither a SAML RequestAbstractType or Response");
        }

        if (!DatatypeHelper.isEmpty(getRelayState())) {
            queryParams.add(new Pair<String, String>("RelayState", getRelayState()));
        }

        if (isSigned()) {
            Pair<String, String> sigAlg = new Pair<String, String>("SigAlg", getSignatureAlgorithm());
            queryParams.add(sigAlg);
            String sigMaterial = urlBuilder.buildQueryString();

            queryParams.add(new Pair<String, String>("Signature", generateSignature(sigMaterial)));
        }

        return urlBuilder.buildURL();
    }

    /**
     * Generates the signature over the query string.
     * 
     * @param queryString query string to be signed
     * 
     * @return base64 encoded signature of query string
     * @throws BindingException
     */
    protected String generateSignature(String queryString) throws BindingException {
        Signature signature;

        if(log.isDebugEnabled()){
            log.debug("Generating digital signature of query string using algorithm " + getSignatureAlgorithm());
        }
        
        try {
            if (DSA_SHA1_SIGNATURE.equals(getSignatureAlgorithm())) {
                signature = Signature.getInstance("SHA1withDSA");
            } else if (RSA_SHA1_SIGNATURE.equals(getSignatureAlgorithm())) {
                signature = Signature.getInstance("SHA1withRSA");
            } else {
                throw new BindingException(getSignatureAlgorithm() + " is not a supported signature algorithm");
            }
            
            if(getSigningKey() == null){
                log.error("No signing key given, can not computer signature");
                throw new BindingException("No signing key given, can not computer signature");
            }
            signature.initSign(getSigningKey());
            
            signature.update(queryString.getBytes());
            
            byte[] rawSignature = signature.sign();
            return Base64.encodeBytes(rawSignature);
        } catch (GeneralSecurityException e) {
            log.error("Error during URL signing process", e);
            throw new BindingException("Unable to sign URL query string", e);
        }
    }
}