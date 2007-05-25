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

package org.opensaml.saml2.binding.encoding;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.opensaml.Configuration;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.encoding.impl.AbstractHTTPMessageEncoder;
import org.opensaml.common.impl.SAMLObjectContentReference;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.Signer;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * Base class for SAML 2 HTTP message encoders.
 */
public abstract class AbstractSAML2HTTPMessageEncoder extends AbstractHTTPMessageEncoder {

    /**
     * Gets the response URL from the relying party endpoint. If the SAML message is a {@link Response} and the relying
     * party endpoint contains a response location then that location is returned otherwise the normal endpoint location
     * is returned.
     * 
     * @return response URL from the relying party endpoint
     * 
     * @throws BindingException throw if no relying party endpoint is available
     */
    protected String getEndpointURL() throws BindingException {
        Endpoint endpoint = getRelyingPartyEndpoint();
        if (endpoint == null) {
            throw new BindingException("Relying party endpoint provided we null.");
        }

        if (getSamlMessage() instanceof Response && !DatatypeHelper.isEmpty(endpoint.getResponseLocation())) {
            return endpoint.getResponseLocation();
        } else {
            if (DatatypeHelper.isEmpty(endpoint.getLocation())) {
                throw new BindingException("Relying party endpoint location was null or empty.");
            }
            return endpoint.getLocation();
        }
    }

    /**
     * Checks that the relay state is 80 bytes or less.
     * 
     * @return true if the relay state is not empty and is less than 80 bytes
     * 
     * @throws BindingException thrown if the relay state is larger than 80 bytes
     */
    protected boolean checkRelayState() throws BindingException {
        if (!DatatypeHelper.isEmpty(getRelayState())) {
            if (getRelayState().getBytes().length > 80) {
                throw new BindingException("Relay state exceeds 80 bytes.");
            }
            
            return true;
        }
        
        return false;
    }

    /**
     * Signs the given SAML message if it a {@link SignableSAMLObject} and this encoder has signing credentials.
     */
    @SuppressWarnings("unchecked")
    protected void signMessage() {
        if (getSamlMessage() instanceof SignableSAMLObject && getSigningCredential() != null) {
            SignableSAMLObject signableMessage = (SignableSAMLObject) getSamlMessage();

            SAMLObjectContentReference contentRef = new SAMLObjectContentReference(signableMessage);
            XMLObjectBuilder<Signature> signatureBuilder = Configuration.getBuilderFactory().getBuilder(
                    Signature.DEFAULT_ELEMENT_NAME);
            Signature signature = signatureBuilder.buildObject(Signature.DEFAULT_ELEMENT_NAME);
            signature.getContentReferences().add(contentRef);
            signableMessage.setSignature(signature);

            Signer.signObject(signature);
        }
    }
}