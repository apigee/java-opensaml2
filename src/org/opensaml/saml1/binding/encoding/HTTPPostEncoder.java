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

package org.opensaml.saml1.binding.encoding;

import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.opensaml.Configuration;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.encoding.impl.AbstractHTTPMessageEncoder;
import org.opensaml.common.impl.SAMLObjectContentReference;
import org.opensaml.saml1.core.Response;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.Signer;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * SAML 1.X HTTP POST message encoder.
 */
public class HTTPPostEncoder extends AbstractHTTPMessageEncoder {

    /** Binding URI. */
    public static final String BINDING_URI = "urn:oasis:names:tc:SAML:1.0:profiles:browser-post";

    /** Class logger. */
    private final Logger log = Logger.getLogger(HTTPPostEncoder.class);

    /** Velocity engine used to evaluate the template when performing POST encoding. */
    private VelocityEngine velocityEngine;

    /** ID of the velocity template used when performing POST encoding. */
    private String velocityTemplateId;

    /** {@inheritDoc} */
    public String getBindingURI() {
        return BINDING_URI;
    }

    /**
     * Gets the velocity engine used to evaluate the template when performing POST encoding.
     * 
     * @return velocity engine used to evaluate the template when performing POST encoding
     */
    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    /**
     * Sets the velocity engine used to evaluate the template when performing POST encoding.
     * 
     * @param engine velocity engine used to evaluate the template when performing POST encoding
     */
    public void setVelocityEngine(VelocityEngine engine) {
        velocityEngine = engine;
    }

    /**
     * Gets the ID of the velocity template used for POST encoding.
     * 
     * @return ID of the velocity template used for POST encoding
     */
    public String getVelocityTemplateId() {
        return velocityTemplateId;
    }

    /**
     * Sets the ID of the velocity template used for POST encoding.
     * 
     * @param id ID of the velocity template used for POST encoding
     */
    public void setVelocityTemplateId(String id) {
        velocityTemplateId = id;
    }

    /** {@inheritDoc} */
    public void encode() throws BindingException {
        if (log.isDebugEnabled()) {
            log.debug("Beginning SAML 2 HTTP POST encoding");
        }

        HttpServletResponse response = getResponse();

        signMessage();

        if (log.isDebugEnabled()) {
            log.debug("Marshalling SAML message");
        }
        String messageXML = marshallMessage(getSamlMessage());

        if (log.isDebugEnabled()) {
            log.debug("Base64 encoding message");
        }
        String encodedMessage = new String(Base64.encodeBytes(messageXML.getBytes(), Base64.DONT_BREAK_LINES));
        
        if(log.isDebugEnabled()){
            log.debug("Base64 encoded SAML message is: " + encodedMessage);
        }

        try {
            if (log.isDebugEnabled()) {
                log.debug("Adding cache headers to response");
            }
            response.setContentType("application/xhtml+xml");
            initializeResponse();

            postEncode(response.getWriter(), encodedMessage);
        } catch (IOException e) {
            log.error("Unable to access HttpServletResponse output writer", e);
            throw new BindingException("Unable to access HttpServletResponse output writer", e);
        }
    }

    /**
     * POST encodes the SAML message.
     * 
     * @param responseWriter writer to write the encoded message to
     * 
     * @param message base64 encoded SAML message
     * 
     * @throws BindingException thrown if the message can not be written to the writer
     */
    protected void postEncode(Writer responseWriter, String message) throws BindingException {
        if (log.isDebugEnabled()) {
            log.debug("Performing SAML 1 HTTP POST encoding");
        }

        if (log.isDebugEnabled()) {
            log.debug("Invoking velocity template");
        }
        try {
            VelocityContext context = new VelocityContext();
            context.put("action", getEndpointURL());
            context.put("SAMLResponse", message);
            context.put("Target", URLEncoder.encode(getRelayState(), "UTF-8"));
            velocityEngine.mergeTemplate(velocityTemplateId, "UTF-8", context, responseWriter);
        } catch (Exception e) {
            log.error("Error invoking velocity template", e);
            throw new BindingException("Error creating output document", e);
        }
    }

    /**
     * Gets the response URL from the relying party endpoint. If the SAML message is a {@link Response} and the relying
     * party endpoint contains a response location then that location is returned otherwise the normal endpoint location
     * is returned.
     * 
     * @return response URL from the relying party endpoint
     * 
     * @throws BindingException throw if no relying party endpoint is available
     */
    protected String getEndpointURL() throws BindingException {
        Endpoint endpoint = getRelyingPartyEndpoint();
        if (endpoint == null) {
            throw new BindingException("Relying party endpoint provided we null.");
        }

        if (getSamlMessage() instanceof Response && !DatatypeHelper.isEmpty(endpoint.getResponseLocation())) {
            return endpoint.getResponseLocation();
        } else {
            if (DatatypeHelper.isEmpty(endpoint.getLocation())) {
                throw new BindingException("Relying party endpoint location was null or empty.");
            }
            return endpoint.getLocation();
        }
    }

    /**
     * Signs the given SAML message if it a {@link SignableSAMLObject} and this encoder has signing credentials.
     */
    @SuppressWarnings("unchecked")
    protected void signMessage() {
        if (getSamlMessage() instanceof SignableSAMLObject && getSigningCredential() != null) {
            if (log.isDebugEnabled()) {
                log.debug("Signing SAML message.");
            }
            SignableSAMLObject signableMessage = (SignableSAMLObject) getSamlMessage();

            SAMLObjectContentReference contentRef = new SAMLObjectContentReference(signableMessage);
            XMLObjectBuilder<Signature> signatureBuilder = Configuration.getBuilderFactory().getBuilder(
                    Signature.DEFAULT_ELEMENT_NAME);
            Signature signature = signatureBuilder.buildObject(Signature.DEFAULT_ELEMENT_NAME);
            signature.getContentReferences().add(contentRef);
            signableMessage.setSignature(signature);

            Signer.signObject(signature);
        }
    }
}