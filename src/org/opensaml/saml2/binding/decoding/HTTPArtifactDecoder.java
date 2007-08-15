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

package org.opensaml.saml2.binding.decoding;

import org.apache.log4j.Logger;
import org.opensaml.common.binding.artifact.SAMLArtifact;
import org.opensaml.common.binding.artifact.SAMLArtifactFactory;
import org.opensaml.common.binding.decoding.SAMLMessageDecoder;
import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.message.decoder.BaseMessageDecoder;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.xml.parse.ParserPool;

/** SAML 2 Artifact Binding decoder, support both HTTP GET and POST. */
public class HTTPArtifactDecoder extends BaseMessageDecoder implements SAMLMessageDecoder {

    /** Class logger. */
    private static Logger log = Logger.getLogger(HTTPArtifactDecoder.class);

    /** Factory for building artifacts. */
    private SAMLArtifactFactory artifactFactory;

    /** Artifact generated for the given SAML message. */
    private SAMLArtifact artifact;

    /** Constructor. */
    public HTTPArtifactDecoder() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param pool parser pool used to deserialize messages
     */
    public HTTPArtifactDecoder(ParserPool pool) {
        super(pool);
    }

    /** {@inheritDoc} */
    public String getBindingURI() {
        // TODO Auto-generated method stub
        return null;
    }

    /** {@inheritDoc} */
    protected void doDecode(MessageContext messageContext) throws MessageDecodingException {
        // TODO Auto-generated method stub

    }
}