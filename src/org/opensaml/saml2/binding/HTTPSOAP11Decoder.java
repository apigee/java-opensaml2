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
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.impl.AbstractSOAPHTTPDecoder;
import org.opensaml.ws.soap.soap11.Envelope;
import org.opensaml.xml.XMLObject;

/**
 * SAML 2.0 SOAP 1.1 over HTTP binding decoder.
 */
public class HTTPSOAP11Decoder extends AbstractSOAPHTTPDecoder {

    /** Class logger */
    private final static Logger log = Logger.getLogger(HTTPSOAP11Decoder.class);

    /** {@inheritDoc} */
    public void decode() throws BindingException {
        if (log.isDebugEnabled()) {
            log.debug("Beginning SAML 2 HTTP SOAP 1.1 decoding");
        }

        HttpServletRequest request = getRequest();
        setHttpMethod("POST");

        try {
            Envelope soapMessage = (Envelope) unmarshallMessage(request.getInputStream());
            setSOAPMessage(soapMessage);

            List<XMLObject> soapBodyChildren = soapMessage.getBody().getUnknownXMLObjects();
            if (soapBodyChildren.size() < 1 || soapBodyChildren.size() > 1) {
                log.error("Unexpected number of children in the SOAP body, " + soapBodyChildren.size()
                        + ".  Unable to extract SAML message");
                throw new BindingException(
                        "Unexpected number of children in the SOAP body, unable to extract SAML message");
            }

            setSAMLMessage((SAMLObject) soapBodyChildren.get(0));

            evaluateSecurityPolicy(soapMessage);

        } catch (IOException e) {
            log.error("Unable to read SOAP message from request", e);
            throw new BindingException("Unable to read SOAP message from request", e);
        }
    }
}