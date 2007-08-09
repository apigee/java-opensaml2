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
import org.opensaml.saml1.core.Response;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Test case for SAML 1 HTTP POST decoding.
 */
public class HTTPPostDecoderTest extends BaseTestCase {

    /** Test decoding message. */
//    public void testDecode() throws Exception {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        request.setParameter("TARGET", "relay");
//        request.setParameter("SAMLResponse", "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPHNhbWxwOlJlc3Bvbn"
//                + "NlIElzc3VlSW5zdGFudD0iMTk3MC0wMS0wMVQwMDowMDowMC4wMDBaIiBNYWpvclZlcnNpb249IjEiIE1pbm9yVmVyc2lvbj0i"
//                + "MSIgUmVzcG9uc2VJRD0iZm9vIiB4bWxuczpzYW1scD0idXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6MS4wOnByb3RvY29sIi8+");
//
//        HTTPPostDecoder decoder = new HTTPPostDecoder();
//        decoder.decode();
//
//        assertTrue(decoder.getSAMLMessage() instanceof Response);
//        assertEquals("relay", decoder.getRelayState());
//    }
}