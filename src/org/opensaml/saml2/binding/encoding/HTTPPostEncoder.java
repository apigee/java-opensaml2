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

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.opensaml.common.binding.BindingException;
import org.opensaml.saml2.core.RequestAbstractType;
import org.opensaml.saml2.core.StatusResponseType;

/**
 * SAML 2.0 HTTP Post binding message encoder.
 */
public class HTTPPostEncoder extends AbstractSAML2HTTPMessageEncoder {

    /** URI for this binding. */
    public static final String BINDING_URI = "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST";

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
        
        StatusResponseType samlResponse = (StatusResponseType) getSamlMessage();
        samlResponse.setDestination(getEndpointURL());

        HttpServletResponse response = getResponse();

        signMessage();

        if (log.isDebugEnabled()) {
            log.debug("Base64 encoding message");
        }
        String encodedMessage = getBase64EncodedMessage();

        try {
            initializeResponse();
            response.setContentType("application/xhtml+xml");
            
            StringWriter responseBodyWriter = new StringWriter();
            postEncode(responseBodyWriter, encodedMessage);
            String responseBody = responseBodyWriter.toString();
            
            if(log.isDebugEnabled()){
                log.debug("POST encoded body is:\n" + responseBody);
            }

            response.getWriter().write(responseBody);
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
            log.debug("Creating velocity context");
        }
        VelocityContext context = new VelocityContext();
        
        if(log.isDebugEnabled()){
            log.debug("Encoding action url of: " + getEndpointURL());
        }
        context.put("action", getEndpointURL());

        if(log.isDebugEnabled()){
            log.debug("Encoding SAML message of: " + message);
        }
        if (getSamlMessage() instanceof RequestAbstractType) {
            context.put("SAMLRequest", message);
        } else {
            context.put("SAMLResponse", message);
        }

        if (checkRelayState()) {
            if(log.isDebugEnabled()){
                log.debug("Encoding relay state of: " + getRelayState());
            }
            context.put("RelayState", getRelayState());
        }

        if (log.isDebugEnabled()) {
            log.debug("Invoking velocity template");
        }
        try {
            velocityEngine.mergeTemplate(velocityTemplateId, "UTF-8", context, responseWriter);
        } catch (Exception e) {
            log.error("Error invoking velocity template", e);
            throw new BindingException("Error creating output document", e);
        }
    }
}