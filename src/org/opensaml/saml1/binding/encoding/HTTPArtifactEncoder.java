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

import org.opensaml.common.binding.encoding.SAMLMessageEncoder;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.message.encoder.BaseMessageEncoder;
import org.opensaml.ws.message.encoder.MessageEncodingException;

/**
 * SAML 1.X HTTP Artifact message encoder.
 */
public class HTTPArtifactEncoder extends BaseMessageEncoder implements SAMLMessageEncoder {

    /** {@inheritDoc} */
    public String getBindingURI() {
        return "urn:oasis:names:tc:SAML:1.0:profiles:artifact-01";
    }

    /** {@inheritDoc} */
    protected void doEncode(MessageContext messageContext) throws MessageEncodingException {
        // TODO Auto-generated method stub

    }
}