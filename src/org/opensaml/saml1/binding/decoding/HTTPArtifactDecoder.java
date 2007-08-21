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

import java.util.List;

import org.apache.log4j.Logger;
import org.opensaml.common.binding.decoding.SAMLMessageDecoder;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.binding.SAML1ArtifactMessageContext;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.message.decoder.BaseMessageDecoder;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.ws.transport.http.HTTPInTransport;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * SAML 1.X HTTP Artifact message decoder.
 */
public class HTTPArtifactDecoder extends BaseMessageDecoder implements SAMLMessageDecoder {
    
    /** Class logger. */
    private final Logger log = Logger.getLogger(HTTPArtifactDecoder.class);

    /** {@inheritDoc} */
    public String getBindingURI() {
        return SAMLConstants.SAML1_ARTIFACT_BINDING_URI;
    }
    
    /** {@inheritDoc} */
    protected void doDecode(MessageContext messageContext) throws MessageDecodingException {
        if(!(messageContext instanceof SAML1ArtifactMessageContext)){
            log.error("Invalid message context type, this decoder only support SAML1ArtifactMessageContext");
            throw new MessageDecodingException(
                    "Invalid message context type, this decoder only support SAML1ArtifactMessageContext");
        }

        if (!(messageContext.getInboundMessageTransport() instanceof HTTPInTransport)) {
            log.error("Invalid inbound message transport type, this decoder only support HTTPInTransport");
            throw new MessageDecodingException(
                    "Invalid inbound message transport type, this decoder only support HTTPInTransport");
        }
        
        SAML1ArtifactMessageContext artifactContext = (SAML1ArtifactMessageContext) messageContext;
        HTTPInTransport inTransport = (HTTPInTransport) artifactContext.getInboundMessageTransport();
        
        String target = DatatypeHelper.safeTrim(inTransport.getParameterValue("TARGET"));
        if(target == null){
            log.error("URL TARGET parameter was missing or did not contain a value.");
            throw new MessageDecodingException("URL TARGET parameter was missing or did not contain a value.");
        }
        artifactContext.setRelayState(target);
        
        List<String> artifacts = inTransport.getParameterValues("SAMLart");
        if(artifacts == null || artifacts.size() == 0){
            log.error("URL SAMLart parameter was missing or did not contain a value.");
            throw new MessageDecodingException("URL TARGET parameter was missing or did not contain a value.");
        }
        artifactContext.setArtifacts(artifacts);
    }
}