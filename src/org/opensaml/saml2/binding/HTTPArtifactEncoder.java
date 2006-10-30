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

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.impl.AbstractHTTPMessageEncoder;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * SAML 2 Artifact Binding encoder, support both HTTP GET and POST.
 * 
 * TODO creation of artifact
 */
public class HTTPArtifactEncoder extends AbstractHTTPMessageEncoder {

    /** Location of the velocity template */
    public final static String VELOCITY_TEMPLATE = "/templates/saml2-post-artifact-binding.vm";

    /** HTTP Method - POST */
    public final static String POST_METHOD = "POST";

    /** HTTP Method - GET */
    public final static String GET_METHOD = "GET";

    /** Class logger */
    private final static Logger log = Logger.getLogger(HTTPArtifactEncoder.class);

    /** HTTP submit method */
    private String method;

    /** URL for the form action field */
    private String actionURL;

    /** Artifact generated for the given SAML message */
    private String artifact;

    /**
     * Gets the HTTP submit method to use.
     * 
     * @return HTTP submit method to use
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the HTTP submit method to use.
     * 
     * @param method HTTP submit method to use
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Gets the URL for the form action field.
     * 
     * @return URL for the form action field
     */
    public String getActionURL() {
        return actionURL;
    }

    /**
     * Sets the URL for the form action field.
     * 
     * @param url URL for the form action field
     */
    public void setActionURL(String url) {
        actionURL = url;
    }

    /**
     * Gets the artifact created for the given SAML message.
     * 
     * @return artifact created for the given SAML message
     */
    public String getArtifact() {
        return artifact;
    }

    /** {@inheritDoc} */
    public void encode() throws BindingException {
        HttpServletResponse response = getResponse();
        response.setCharacterEncoding("UTF-8");

        if (log.isDebugEnabled()) {
            log.debug("Creating velocity context");
        }
        VelocityContext context = new VelocityContext();
        context.put("action", getActionURL());
        context.put("SAMLArt", getArtifact());

        if (!DatatypeHelper.isEmpty(getRelayState())) {
            context.put("RelayState", getRelayState());
        }

        if (method.equalsIgnoreCase(POST_METHOD)) {
            postEncode(context);
        } else {
            getEncode(context);
        }
    }

    /**
     * Performs HTTP POST based encoding.
     * 
     * @throws BindingException thrown if there is a problem invoking the velocity template to create the form
     */
    private void postEncode(VelocityContext context) throws BindingException {
        try {
            if (log.isDebugEnabled()) {
                log.debug("Performing HTTP GET SAML 2 artifact encoding");
            }

            if (log.isDebugEnabled()) {
                log.debug("Invoking velocity template");
            }
            Velocity.mergeTemplate(VELOCITY_TEMPLATE, "UTF-8", context, getResponse().getWriter());
        } catch (Exception e) {
            log.error("Error invoking velocity template to create POST form", e);
            throw new BindingException("Error creating output document", e);
        }
    }

    /**
     * Performs HTTP GET based econding.
     * 
     * @throws BindingException
     */
    private void getEncode(VelocityContext context) throws BindingException {
        if (log.isDebugEnabled()) {
            log.debug("Performing HTTP GET SAML 2 artifact encoding");
        }

        try {
            String redirectURLTemplate = "${action}?SAMLArt=${SAMLArt}#if($RelayState)&RelayState=${RelayState}#end";
            StringWriter writer = new StringWriter();
            Velocity.evaluate(context, writer, HTTPArtifactEncoder.class.getName(), redirectURLTemplate);
            getResponse().sendRedirect(writer.toString());
        } catch (IOException e) {
            log.error("Unable to send HTTP redirect", e);
            throw new BindingException("Unable to send HTTP redirect", e);
        } catch (Exception e) {
            log.error("Error invoking velocity template to create redirect URL", e);
            throw new BindingException("Error creating redirect URL", e);
        }
    }
}