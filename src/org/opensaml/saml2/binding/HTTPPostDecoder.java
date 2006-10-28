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

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;
import org.opensaml.Configuration;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.SecurityPolicy;
import org.opensaml.common.binding.impl.AbstractHTTPMessageDecoder;
import org.opensaml.common.xml.ParserPoolManager;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.XMLParserException;
import org.opensaml.xml.util.DatatypeHelper;
import org.w3c.dom.Document;

/**
 * Message decoder implementing the SAML 2.0 HTTP POST profile.
 */
public class HTTPPostDecoder extends AbstractHTTPMessageDecoder {

    /** Class logger */
    private final static Logger log = Logger.getLogger(HTTPPostDecoder.class);

    /** HTTP request param name for SAML request */
    public final static String REQUEST_PARAM = "SAMLRequest";

    /** HTTP request param name for SAML response */
    public final static String RESPONSE_PARAM = "SAMLResponse";

    /** HTTP request param name for relay state */
    public final static String RELAY_STATE_PARAM = "RelayState";

    /** {@inheritDoc} */
    public void decode() throws BindingException {
        if (log.isDebugEnabled()) {
            log.debug("Beginning decode of request using HTTP POST binding");
        }
        HttpServletRequest request = getRequest();

        if (log.isDebugEnabled()) {
            log.debug("Getting Base64 encoded message from request");
        }
        String encodedMessage = request.getParameter(REQUEST_PARAM);
        if (DatatypeHelper.isEmpty(encodedMessage)) {
            encodedMessage = request.getParameter(RESPONSE_PARAM);
        }

        if (DatatypeHelper.isEmpty(encodedMessage)) {
            log.error("Request did not contain either a " + REQUEST_PARAM + " or " + RESPONSE_PARAM
                    + " paramter in request.  Invalid request for SAML 2 HTTP POST binding.");
            throw new BindingException("No SAML message present in request");
        }

        if (log.isDebugEnabled()) {
            log.debug("Base64 decoding SAML message");
        }
        byte[] decodedMessage = Base64.decode(encodedMessage);

        try {
            if (log.isDebugEnabled()) {
                log.debug("Parsing message XML into a DOM");
            }
            Document domMessage = ParserPoolManager.getInstance().parse(new ByteArrayInputStream(decodedMessage));
            
            if(log.isDebugEnabled()){
                log.debug("Unmarshalling DOM into SAMLObject");
            }
            Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(
                    domMessage.getDocumentElement());
            SAMLObject message = (SAMLObject) unmarshaller.unmarshall(domMessage.getDocumentElement());

            if(log.isDebugEnabled()){
                log.debug("Evaluating request and SAML message against security policy");
            }
            SecurityPolicy<HttpServletRequest> securityPolicy = getSecurityPolicy();
            securityPolicy.evaluate(getRequest(), message);

            if(log.isDebugEnabled()){
                log.debug("HTTP request successfully decoded using SAML 2 HTTP POST binding");
            }
            setIssuer(securityPolicy.getIssuer());
            setIssuerMetadata(securityPolicy.getIssuerMetadata());
            setRelayState(request.getParameter(RELAY_STATE_PARAM));
            setSAMLMessage(message);
        } catch (XMLParserException e) {
            log.error("Unable to parse SAML message XML", e);
            throw new BindingException("Unable to parse SAML message XML", e);
        } catch (UnmarshallingException e) {
            log.error("Unable to unmarshall SAML message DOM", e);
            throw new BindingException("Unable to unmarshaller SAML message DOM", e);
        }
    }
}