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
import org.opensaml.saml2.core.Response;
import org.opensaml.ws.transport.http.HttpServletRequestAdapter;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 *
 */
public class HTTPRedirectDeflateDecoderTest extends BaseTestCase {

    public void testDecoding() throws Exception{
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("RelayState", "relay");
        request.setParameter("SAMLResponse", "fY89a8NADIb3/opDu+NzlrTCH4SUgqFd6jRDt+OsBIMtGesu5OfHNcngpfAuglfPI+XVbejNlSbthAvINhYMsZe240sBP8eP5BWq8iVXN/QjfpOOwkqmfi/gLAKmVo1UswbHYV5/29nEZnOO1uKSjbX2F8zpadj+GWYnKy7MAuLEKE47RXYDKQaPzf7rE+cmjpME8dJD+bigCS5EXU8HacmcXB/pf5gubWyi96QKaZmna2i6/rK8Aw==");
        
        BasicSAMLMessageContext messageContext = new BasicSAMLMessageContext();
        messageContext.setMessageInTransport(new HttpServletRequestAdapter(request));

        HTTPRedirectDeflateDecoder decoder = new HTTPRedirectDeflateDecoder();
        decoder.decode(messageContext);

        assertTrue(messageContext.getInboundMessage() instanceof Response);
        assertTrue(messageContext.getInboundSAMLMessage() instanceof Response);
        assertEquals("relay", messageContext.getRelayState());
    }
}