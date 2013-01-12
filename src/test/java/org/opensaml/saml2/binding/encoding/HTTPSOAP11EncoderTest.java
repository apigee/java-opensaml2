/*
 * Licensed to the University Corporation for Advanced Internet Development, 
 * Inc. (UCAID) under one or more contributor license agreements.  See the 
 * NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The UCAID licenses this file to You under the Apache 
 * License, Version 2.0 (the "License"); you may not use this file except in 
 * compliance with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.saml2.binding.encoding;

import org.joda.time.DateTime;
import org.opensaml.common.BaseTestCase;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.ws.soap.common.SOAPObjectBuilder;
import org.opensaml.ws.soap.soap11.Envelope;
import org.opensaml.ws.soap.soap11.Header;
import org.opensaml.ws.transport.http.HttpServletResponseAdapter;
import org.opensaml.ws.wsaddressing.Action;
import org.opensaml.ws.wsaddressing.WSAddressingObjectBuilder;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * Test for SAML 2 SOAP 1.1 message encoder.
 */
public class HTTPSOAP11EncoderTest extends BaseTestCase {

    /**
     * Tests encoding a SAML message to an servlet response.
     * 
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void testResponseEncoding() throws Exception {
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
        Response samlMessage = responseBuilder.buildObject();
        samlMessage.setID("foo");
        samlMessage.setVersion(SAMLVersion.VERSION_20);
        samlMessage.setIssueInstant(new DateTime(0));
        samlMessage.setStatus(responseStatus);

        SAMLObjectBuilder<Endpoint> endpointBuilder = (SAMLObjectBuilder<Endpoint>) builderFactory
                .getBuilder(AssertionConsumerService.DEFAULT_ELEMENT_NAME);
        Endpoint samlEndpoint = endpointBuilder.buildObject();
        samlEndpoint.setLocation("http://example.org");
        samlEndpoint.setResponseLocation("http://example.org/response");

        MockHttpServletResponse response = new MockHttpServletResponse();
        HttpServletResponseAdapter outTransport = new HttpServletResponseAdapter(response, false);
        
        BasicSAMLMessageContext messageContext = new BasicSAMLMessageContext();
        messageContext.setOutboundMessageTransport(outTransport);
        messageContext.setPeerEntityEndpoint(samlEndpoint);
        messageContext.setOutboundSAMLMessage(samlMessage);
        messageContext.setRelayState("relay");
        
        HTTPSOAP11Encoder encoder = new HTTPSOAP11Encoder();
        encoder.encode(messageContext);

        assertEquals("Unexpected content type", "text/xml", response.getContentType());
        assertEquals("Unexpected character encoding", response.getCharacterEncoding(), "UTF-8");
        assertEquals("Unexpected cache controls", "no-cache, no-store", response.getHeader("Cache-control"));
        assertEquals("http://www.oasis-open.org/committees/security", response.getHeader("SOAPAction"));
        assertEquals(2034125980, response.getContentAsString().hashCode());
    }
    
    /**
     * Tests encoding a SAML message to an servlet response using a pre-existing SOAP Envelope.
     * 
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void testExistingEnvelope() throws Exception {
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
        Response samlMessage = responseBuilder.buildObject();
        samlMessage.setID("foo");
        samlMessage.setVersion(SAMLVersion.VERSION_20);
        samlMessage.setIssueInstant(new DateTime(0));
        samlMessage.setStatus(responseStatus);

        SAMLObjectBuilder<Endpoint> endpointBuilder = (SAMLObjectBuilder<Endpoint>) builderFactory
                .getBuilder(AssertionConsumerService.DEFAULT_ELEMENT_NAME);
        Endpoint samlEndpoint = endpointBuilder.buildObject();
        samlEndpoint.setLocation("http://example.org");
        samlEndpoint.setResponseLocation("http://example.org/response");
        
        SOAPObjectBuilder<Envelope> envelopeBuilder = (SOAPObjectBuilder<Envelope>) builderFactory
                .getBuilder(Envelope.DEFAULT_ELEMENT_NAME);
        Envelope envelope = envelopeBuilder.buildObject();
        
        SOAPObjectBuilder<Header> headerBuilder = (SOAPObjectBuilder<Header>) builderFactory
                .getBuilder(Header.DEFAULT_ELEMENT_NAME);
        Header header = headerBuilder.buildObject();
        envelope.setHeader(header);
        
        WSAddressingObjectBuilder<Action> actionBuilder = (WSAddressingObjectBuilder<Action>) builderFactory
                .getBuilder(Action.ELEMENT_NAME);
        Action action = actionBuilder.buildObject();
        action.setValue("urn:test:action");
        header.getUnknownXMLObjects().add(action);

        MockHttpServletResponse response = new MockHttpServletResponse();
        HttpServletResponseAdapter outTransport = new HttpServletResponseAdapter(response, false);
        
        BasicSAMLMessageContext messageContext = new BasicSAMLMessageContext();
        messageContext.setOutboundMessageTransport(outTransport);
        messageContext.setPeerEntityEndpoint(samlEndpoint);
        messageContext.setOutboundSAMLMessage(samlMessage);
        messageContext.setOutboundMessage(envelope);
        messageContext.setRelayState("relay");
        
        HTTPSOAP11Encoder encoder = new HTTPSOAP11Encoder();
        encoder.encode(messageContext);

        assertEquals("Unexpected content type", "text/xml", response.getContentType());
        assertEquals("Unexpected character encoding", response.getCharacterEncoding(), "UTF-8");
        assertEquals("Unexpected cache controls", "no-cache, no-store", response.getHeader("Cache-control"));
        assertEquals("http://www.oasis-open.org/committees/security", response.getHeader("SOAPAction"));
        assertEquals(1218694958, response.getContentAsString().hashCode());
    }
}