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

import org.joda.time.DateTime;
import org.opensaml.common.BaseTestCase;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.binding.encoding.HTTPMessageEncoder;
import org.opensaml.saml1.binding.encoding.HTTPSOAP11Encoder;
import org.opensaml.saml1.binding.encoding.HTTPSOAP11EncoderBuilder;
import org.opensaml.saml1.core.Request;
import org.opensaml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml2.metadata.Endpoint;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * Test case for SAML 1.X HTTP SOAP 1.1 binding encoding.
 */
public class HTTPSOAP11EncoderTest extends BaseTestCase {

    /** Tests encoding a simple SAML message. */
    @SuppressWarnings("unchecked")
    public void testEncoding() throws Exception {
        SAMLObjectBuilder<Request> requestBuilder = (SAMLObjectBuilder<Request>) builderFactory
                .getBuilder(Request.DEFAULT_ELEMENT_NAME);
        Request request = requestBuilder.buildObject();
        request.setID("foo");
        request.setIssueInstant(new DateTime(0));
        request.setVersion(SAMLVersion.VERSION_11);

        SAMLObjectBuilder<Endpoint> endpointBuilder = (SAMLObjectBuilder<Endpoint>) builderFactory
                .getBuilder(AssertionConsumerService.DEFAULT_ELEMENT_NAME);
        Endpoint samlEndpoint = endpointBuilder.buildObject();
        samlEndpoint.setBinding(HTTPSOAP11Encoder.BINDING_URI);
        samlEndpoint.setLocation("http://example.org");
        samlEndpoint.setResponseLocation("http://example.org/response");

        HTTPSOAP11EncoderBuilder encoderBuilder = new HTTPSOAP11EncoderBuilder();
        HTTPMessageEncoder encoder = encoderBuilder.buildEncoder();

        MockHttpServletResponse response = new MockHttpServletResponse();
        encoder.setRelyingPartyEndpoint(samlEndpoint);
        encoder.setSamlMessage(request);
        encoder.setRelayState("relay");
        encoder.setResponse(response);
        encoder.encode();

        assertEquals("Unexpected content type", "text/xml", response.getContentType());
        assertEquals("Unexpected character encoding", response.getCharacterEncoding(), "UTF-8");
        assertEquals("Unexpected cache controls", "no-cache, no-store", response.getHeader("Cache-control"));
        assertEquals("http://www.oasis-open.org/committees/security", response.getHeader("SOAPAction"));
        assertEquals(-1600998768, response.getContentAsString().hashCode());
    }
}