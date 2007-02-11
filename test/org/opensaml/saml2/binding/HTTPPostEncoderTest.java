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

import java.io.InputStream;

import org.joda.time.DateTime;
import org.opensaml.common.BaseTestCase;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.SAMLVersion;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusCode;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * Test case for {@link HTTPPostEncoder}.
 */
public class HTTPPostEncoderTest extends BaseTestCase {

    /** Message to encode. */
    private Response samlMessage;

    /** URL used as the encoder's ActionURL. */
    private String postActionURL;

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        SAMLObjectBuilder<StatusCode> statusCodeBuilder = (SAMLObjectBuilder<StatusCode>) builderFactory
                .getBuilder(StatusCode.DEFAULT_ELEMENT_NAME);
        StatusCode statusCode = statusCodeBuilder.buildObject();
        statusCode.setValue(StatusCode.SUCCESS_URI);

        SAMLObjectBuilder<Status> statusBuilder = (SAMLObjectBuilder<Status>) builderFactory
                .getBuilder(Status.DEFAULT_ELEMENT_NAME);
        Status responseStatus = statusBuilder.buildObject();
        responseStatus.setStatusCode(statusCode);

        SAMLObjectBuilder<Response> responseBuilder = (SAMLObjectBuilder<Response>) builderFactory
                .getBuilder(Response.DEFAULT_ELEMENT_NAME);
        samlMessage = responseBuilder.buildObject();
        samlMessage.setID("foo");
        samlMessage.setVersion(SAMLVersion.VERSION_20);
        samlMessage.setIssueInstant(new DateTime());
        samlMessage.setStatus(responseStatus);

        postActionURL = "http://example.org/sp/saml2/httppost";
    }

    /**
     * Tests encoding a SAML message to an servlet response.
     * 
     * @throws Exception
     */
    public void testEncoding() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();
        HTTPPostEncoder encoder = new HTTPPostEncoder();

        encoder.setActionURL(postActionURL);
        encoder.setSAMLMessage(samlMessage);
        encoder.setRelayState("relay");
        encoder.setResponse(response);
        encoder.encode();
        
        assertEquals("Unexpected content type", "application/xhtml+xml", response.getContentType());
        assertEquals("Unexpected character encoding", response.getCharacterEncoding(), "UTF-8");
        assertEquals("Unexpected cache controls", "no-cache, no-store", response.getHeader("Cache-control"));
        
        //TODO check response content
        InputStream input = getClass().getResourceAsStream("/data/org/opensaml/saml2/binding/http-post-binding.xml");
        byte[] buffer = new byte[input.available()];
        input.read(buffer);
        
        
        assertXMLEqual(new String(buffer,"UTF-8"), response.getContentAsString());
    }
}