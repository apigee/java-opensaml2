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

package org.opensaml.saml1.binding.decoding;

import org.opensaml.common.BaseTestCase;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.saml1.core.Request;
import org.opensaml.ws.soap.soap11.Envelope;
import org.opensaml.ws.transport.http.HttpServletRequestAdapter;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Test case for SAML 1.X HTTP SOAP 1.1 message decoder.
 */
public class HTTPSOAP11DecoderTest extends BaseTestCase {

    /**
     * Tests decoding a SOAP 1.1 message.
     */
    public void testDecoding() throws Exception {
        String requestContent = "<soap11:Envelope xmlns:soap11=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap11:Body><saml:Request IssueInstant=\"1970-01-01T00:00:00.000Z\" MajorVersion=\"1\" "
                + "MinorVersion=\"1\" RequestID=\"foo\" xmlns:saml=\"urn:oasis:names:tc:SAML:1.0:protocol\"/>"
                + "</soap11:Body></soap11:Envelope>";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContent(requestContent.getBytes());

        BasicSAMLMessageContext messageContext = new BasicSAMLMessageContext();
        messageContext.setInboundMessageTransport(new HttpServletRequestAdapter(request));
        
        HTTPSOAP11Decoder decoder = new HTTPSOAP11Decoder(null);
        decoder.decode(messageContext);

        assertTrue(messageContext.getInboundMessage() instanceof Envelope);
        assertTrue(messageContext.getInboundSAMLMessage() instanceof Request);
    }
}