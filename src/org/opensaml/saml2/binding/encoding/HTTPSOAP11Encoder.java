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
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.encoding.impl.AbstractSOAPHTTPEncoder;
import org.opensaml.ws.soap.soap11.Envelope;

/**
 * SAML 2.0 SOAP 1.1 over HTTP binding encoder.
 */
public class HTTPSOAP11Encoder extends AbstractSOAPHTTPEncoder {

    /** URI for this binding. */
    public static final String BINDING_URI = "urn:oasis:names:tc:SAML:2.0:bindings:SOAP";

    /** Class logger. */
    private final Logger log = Logger.getLogger(HTTPSOAP11Encoder.class);

    /** {@inheritDoc} */
    public String getBindingURI() {
        return BINDING_URI;
    }

    /** {@inheritDoc} */
    public void encode() throws BindingException {
        if (log.isDebugEnabled()) {
            log.debug("Beginning SAML 2 HTTP SOAP 1.1 encoding");
        }

        Envelope envelope = buildSOAPMessage();
        setSOAPMessage(envelope);

        String soapMessage = marshallMessage(envelope);

        try {
            if (log.isDebugEnabled()) {
                log.debug("Writting SOAP message to response");
            }
            HttpServletResponse response = getResponse();
            response.setContentType("text/xml");
            addNoCacheResponseHeaders();
            PrintWriter responseWriter = response.getWriter();
            responseWriter.write(soapMessage);
        } catch (IOException e) {
            log.error("Unable to write response", e);
            throw new BindingException("Unable to write response", e);
        }
    }
}