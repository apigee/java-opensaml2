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
        // Note, Spring's Mock objects don't do URL encoding/decoding, so this is the URL decoded form
        request.setParameter("SAMLResponse", "fZAxa8NADIX3/opDe3yXLG2F7VASCoF2qdMM3Y6LkhrOp8PSlfz8uqYdvBTeIMHT08ert7chmi8apefUwLpyYCgFPvfp2sD78Xn1ANv2rhY/xIxvJJmTkNmTaJ+8zkefqhmtpZsfcqSKxyuYw76BC/M0iBQ6JFGfdMp/vHcrt550dA5nVc65DzCnP4TND8IElQTnpw2UMSF76QWTH0hQA3ZPry84OTGPrBw4QvuL2KnXIsttx2cyJx8L/R8msxu7EgKJgG1ruwy1yxrabw==");
        
        BasicSAMLMessageContext messageContext = new BasicSAMLMessageContext();
        messageContext.setMessageInTransport(new HttpServletRequestAdapter(request));

        HTTPRedirectDeflateDecoder decoder = new HTTPRedirectDeflateDecoder();
        decoder.decode(messageContext);

        assertTrue(messageContext.getInboundMessage() instanceof Response);
        assertTrue(messageContext.getInboundSAMLMessage() instanceof Response);
        assertEquals("relay", messageContext.getRelayState());
    }
}