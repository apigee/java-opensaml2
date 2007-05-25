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

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.joda.time.DateTime;
import org.opensaml.common.BaseTestCase;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.binding.encoding.HTTPMessageEncoder;
import org.opensaml.saml1.core.Response;
import org.opensaml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml2.metadata.Endpoint;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * Test class for SAML 1 HTTP Post encoding.
 */
public class HTTPPostEncoderTest extends BaseTestCase {

    /** Velocity template engine. */
    private VelocityEngine velocityEngine;

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        super.setUp();

        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.ENCODING_DEFAULT, "UTF-8");
        velocityEngine.setProperty(RuntimeConstants.OUTPUT_ENCODING, "UTF-8");
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.init();
    }

    @SuppressWarnings("unchecked")
    public void testEncoding() throws Exception {
        SAMLObjectBuilder<Response> requestBuilder = (SAMLObjectBuilder<Response>) builderFactory
                .getBuilder(Response.DEFAULT_ELEMENT_NAME);
        Response samlMessage = requestBuilder.buildObject();
        samlMessage.setID("foo");
        samlMessage.setIssueInstant(new DateTime(0));
        samlMessage.setVersion(SAMLVersion.VERSION_11);

        SAMLObjectBuilder<Endpoint> endpointBuilder = (SAMLObjectBuilder<Endpoint>) builderFactory
                .getBuilder(AssertionConsumerService.DEFAULT_ELEMENT_NAME);
        Endpoint samlEndpoint = endpointBuilder.buildObject();
        samlEndpoint.setBinding(HTTPPostEncoder.BINDING_URI);
        samlEndpoint.setLocation("http://example.org");
        samlEndpoint.setResponseLocation("http://example.org/response");

        HTTPPostEncoderBuilder encoderBuilder = new HTTPPostEncoderBuilder(velocityEngine,
                "/templates/saml1-post-binding.vm");
        HTTPMessageEncoder encoder = encoderBuilder.buildEncoder();

        MockHttpServletResponse response = new MockHttpServletResponse();
        encoder.setRelyingPartyEndpoint(samlEndpoint);
        encoder.setSamlMessage(samlMessage);
        encoder.setRelayState("relay");
        encoder.setResponse(response);
        encoder.encode();

        assertEquals("Unexpected content type", "application/xhtml+xml", response.getContentType());
        assertEquals("Unexpected character encoding", response.getCharacterEncoding(), "UTF-8");
        assertEquals("Unexpected cache controls", "no-cache, no-store", response.getHeader("Cache-control"));
        assertEquals(-921869005, response.getContentAsString().hashCode());
    }
}