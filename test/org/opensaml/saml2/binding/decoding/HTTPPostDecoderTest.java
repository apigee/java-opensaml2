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

package org.opensaml.saml2.binding.decoding;

import org.opensaml.common.BaseTestCase;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.saml2.core.RequestAbstractType;
import org.opensaml.saml2.core.Response;
import org.opensaml.ws.transport.http.HttpServletRequestAdapter;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Test case for HTTP POST decoders.
 */
public class HTTPPostDecoderTest extends BaseTestCase {

    /**
     * Test decoding a SAML request.
     */
    public void testRequestDecoding() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("RelayState", "relay");
        request.setParameter("SAMLRequest", "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPHNhbWxwOkF1dGhuUm"
                + "VxdWVzdCBJRD0iZm9vIiBJc3N1ZUluc3RhbnQ9IjE5NzAtMDEtMDFUMDA6MDA6MDAuMDAwWiIgVmVyc2lvbj0iMi4wIiB4bW"
                + "xuczpzYW1scD0idXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6Mi4wOnByb3RvY29sIi8+");

        BasicSAMLMessageContext messageContext = new BasicSAMLMessageContext();
        messageContext.setMessageInTransport(new HttpServletRequestAdapter(request));
        
        HTTPPostDecoder decoder = new HTTPPostDecoder();
        decoder.decode(messageContext);

        assertTrue(messageContext.getInboundMessage() instanceof RequestAbstractType);
        assertTrue(messageContext.getInboundMessage() instanceof RequestAbstractType);
        assertEquals("relay", messageContext.getRelayState());
    }

    /**
     * Test decoding a SAML response.
     */
    public void testResponseDecoding() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("RelayState", "relay");
        request.setParameter("SAMLResponse", "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPHNhbWxwOlJlc3Bvbn"
                + "NlIElEPSJmb28iIElzc3VlSW5zdGFudD0iMTk3MC0wMS0wMVQwMDowMDowMC4wMDBaIiBWZXJzaW9uPSIyLjAiIHhtbG5zOnN"
                + "hbWxwPSJ1cm46b2FzaXM6bmFtZXM6dGM6U0FNTDoyLjA6cHJvdG9jb2wiPjxzYW1scDpTdGF0dXM+PHNhbWxwOlN0YXR1c0Nv"
                + "ZGUgVmFsdWU9InVybjpvYXNpczpuYW1lczp0YzpTQU1MOjIuMDpzdGF0dXM6U3VjY2VzcyIvPjwvc2FtbHA6U3RhdHVzPjwvc"
                + "2FtbHA6UmVzcG9uc2U+");

        BasicSAMLMessageContext messageContext = new BasicSAMLMessageContext();
        messageContext.setMessageInTransport(new HttpServletRequestAdapter(request));
        
        HTTPPostDecoder decoder = new HTTPPostDecoder();
        decoder.decode(messageContext);

        assertTrue(messageContext.getInboundSAMLMessage() instanceof Response);
        assertTrue(messageContext.getInboundSAMLMessage() instanceof Response);
        assertEquals("relay", messageContext.getRelayState());
    }
}