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

package org.opensaml.saml2.binding;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.opensaml.common.binding.BasicEndpointSelector;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.saml2.metadata.IndexedEndpoint;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * An endpoint selector that implements the additional selection constraints described within the SAML 2.0 AuthnRequest
 * specification. If an endpoint can not be resolved using either the information within the assertion consumer service
 * index or the assertion consumer service URL given in the authentication request, or if this information isn't
 * present, than the rules for the {@link BasicEndpointSelector} are used.
 */
public class AuthnResponseEndpointSelector extends BasicEndpointSelector {

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public Endpoint selectEndpoint() {
        List<? extends Endpoint> endpoints = getRelyingPartyRole().getEndpoints(getEndpointType());
        if (endpoints == null || endpoints.size() == 0) {
            return null;
        }

        Endpoint endpoint = null;

        if (getSamlRequest() != null) {
            AuthnRequest request = (AuthnRequest) getSamlRequest();
            if (request.getAssertionConsumerServiceIndex() != null) {
                endpoint = selectEndpointByACSIndex(request, (List<IndexedEndpoint>) endpoints);
            } else if (request.getAssertionConsumerServiceURL() != null) {
                endpoint = selectEndpointByACSURL(request, (List<IndexedEndpoint>) endpoints);
            }
        }

        if (endpoint == null) {
            if (endpoints.get(0) instanceof IndexedEndpoint) {
                endpoint = selectIndexedEndpoint((List<IndexedEndpoint>) endpoints);
            } else {
                endpoint = selectNonIndexedEndpoint((List<Endpoint>) endpoints);
            }
        }

        return endpoint;
    }

    /**
     * Selects the endpoint by way of the assertion consumer service index given in the AuthnRequest.
     * 
     * @param request the AuthnRequest
     * @param endpoints list of endpoints to select from
     * 
     * @return the selected endpoint
     */
    protected Endpoint selectEndpointByACSIndex(AuthnRequest request, List<IndexedEndpoint> endpoints) {
        Integer acsIndex = request.getAssertionConsumerServiceIndex();
        for (IndexedEndpoint endpoint : endpoints) {
            if (endpoint.getIndex() != null && endpoint.getIndex().equals(acsIndex)) {
                return endpoint;
            }
        }

        return null;
    }

    /**
     * Selects the endpoint by way of the assertion consumer service URL given in the AuthnRequest.
     * 
     * @param request the AuthnRequest
     * @param endpoints list of endpoints to select from
     * 
     * @return the selected endpoint
     */
    protected Endpoint selectEndpointByACSURL(AuthnRequest request, List<IndexedEndpoint> endpoints) {
        try {
            URL acsURL = new URL(request.getAssertionConsumerServiceURL());
            String acsBinding = DatatypeHelper.safeTrimOrNullString(request.getProtocolBinding());

            URL endpointURL;
            String endpointBinding;
            for (IndexedEndpoint endpoint : endpoints) {
                endpointBinding = DatatypeHelper.safeTrimOrNullString(endpoint.getBinding());

                if ((acsBinding != null && acsBinding.equals(endpointBinding) && getSupportedIssuerBindings().contains(
                        endpointBinding))
                        || getSupportedIssuerBindings().contains(endpointBinding)) {
                    endpointURL = new URL(endpoint.getLocation());
                    if (endpointURL.equals(acsURL)) {
                        return endpoint;
                    } else {
                        endpointURL = new URL(endpoint.getResponseLocation());
                        if (endpointURL.equals(acsURL)) {
                            return endpoint;
                        }
                    }
                }
            }
        } catch (MalformedURLException e) {
            // do nothing
        }

        return null;
    }
}
