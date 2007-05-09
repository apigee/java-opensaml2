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

package org.opensaml.common.binding.encoding.impl;

import javax.servlet.http.HttpServletResponse;

import org.opensaml.common.binding.BindingException;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * Base class handling boilerplate code for HTTP message encoders.
 */
public abstract class AbstractHTTPMessageEncoder extends AbstractMessageEncoder<HttpServletResponse> {

    /** Relay state. */
    private String relayState;

    /** {@inheritDoc} */
    public String getRelayState() {
        return relayState;
    }

    /** {@inheritDoc} */
    public void setRelayState(String state) {
        relayState = state;
    }

    /**
     * Adds cache control and pragma headers that are meant to disable caching.
     */
    protected void addNoCacheResponseHeaders() {
        getResponse().addHeader("Cache-control", "no-cache, no-store");
        getResponse().addHeader("Pragma", "no-cache");
    }
    
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
}