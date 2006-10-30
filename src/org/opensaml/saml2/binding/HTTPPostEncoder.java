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

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.bouncycastle.util.encoders.Base64;
import org.opensaml.Configuration;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.impl.AbstractHTTPMessageEncoder;
import org.opensaml.saml2.core.Request;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.util.DatatypeHelper;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Element;

/**
 * SAML 2.0 HTTP Post binding message encoder
 * 
 * TODO Consider adding attributes that might be released
 */
public class HTTPPostEncoder extends AbstractHTTPMessageEncoder {

    /** Location of the velocity template */
    public final static String VELOCITY_TEMPLATE = "/templates/saml2-post-binding.vm";

    /** Class logger */
    private final static Logger log = Logger.getLogger(HTTPPostEncoder.class);

    /** URL for the form action field */
    private String actionURL;

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

    /** {@inheritDoc} */
    public void encode() throws BindingException {
        HttpServletResponse response = getResponse();
        response.setCharacterEncoding("UTF-8");

        if (log.isDebugEnabled()) {
            log.debug("Adding cache headers to response");
        }
        response.addHeader("Cache-control", "no-cache, no-store");
        response.addHeader("Pragma", "no-cache");

        try {
            if (log.isDebugEnabled()) {
                log.debug("Marshalling SAML message");
            }
            Marshaller marshaller = Configuration.getMarshallerFactory().getMarshaller(getSAMLMessage());
            Element messageElement = marshaller.marshall(getSAMLMessage());

            if (log.isDebugEnabled()) {
                log.debug("Writting message DOM Element to a string");
            }
            String messageXML = XMLHelper.nodeToString(messageElement);

            if (log.isDebugEnabled()) {
                log.debug("Base64 encoding message");
            }
            String base64Message = new String(Base64.encode(messageXML.getBytes()));

            if (log.isDebugEnabled()) {
                log.debug("Creating velocity context");
            }
            VelocityContext context = new VelocityContext();
            context.put("action", getActionURL());

            if (getSAMLMessage() instanceof Request) {
                context.put("SAMLRequest", base64Message);
            } else {
                context.put("SAMLResponse", base64Message);
            }

            if (!DatatypeHelper.isEmpty(getRelayState())) {
                context.put("RelayState", getRelayState());
            }

            if (log.isDebugEnabled()) {
                log.debug("Invoking velocity template");
            }
            Velocity.mergeTemplate(VELOCITY_TEMPLATE, "UTF-8", context, response.getWriter());
        } catch (MarshallingException e) {
            log.error("Unable to marshall SAML message", e);
            throw new BindingException("Unable to marshall SAML message", e);
        } catch (Exception e) {
            log.error("Error invoking velocity template", e);
            throw new BindingException("Error creating output document", e);
        }
    }
}