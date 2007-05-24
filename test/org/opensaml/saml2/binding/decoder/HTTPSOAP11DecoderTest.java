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

package org.opensaml.saml2.binding.decoder;

import org.opensaml.common.BaseTestCase;
import org.opensaml.common.binding.decoding.HTTPMessageDecoder;
import org.opensaml.saml2.binding.decoding.HTTPSOAP11DecoderBuilder;
import org.opensaml.saml2.core.Response;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Test case for HTTP SOAP 1.1 decoder.
 */
public class HTTPSOAP11DecoderTest extends BaseTestCase {

    /**
     * Tests decoding a SOAP 1.1 message.
     */
    public void testDecoding() throws Exception {
        String requestContent = "<soap11:Envelope xmlns:soap11=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap11:Body><samlp:Response ID=\"foo\" IssueInstant=\"1970-01-01T00:00:00.000Z\" Version=\"2.0\" "
                + "xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\"><samlp:Status><samlp:StatusCode "
                + "Value=\"urn:oasis:names:tc:SAML:2.0:status:Success\"/></samlp:Status></samlp:Response>"
                + "</soap11:Body></soap11:Envelope>";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContent(requestContent.getBytes());

        HTTPSOAP11DecoderBuilder decoderBuilder = new HTTPSOAP11DecoderBuilder();
        decoderBuilder.setParser(parser);

        HTTPMessageDecoder decoder = decoderBuilder.buildDecoder();
        decoder.setRequest(request);
        decoder.decode();

        assertTrue(decoder.getSAMLMessage() instanceof Response);
    }
}