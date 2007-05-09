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

package org.opensaml.common.binding;

import java.util.Iterator;
import java.util.List;

import org.opensaml.saml2.binding.AuthnResponseEndpointSelector;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.saml2.metadata.IndexedEndpoint;

/**
 * This endpoint selector retrieves all the endpoints for a given role. A first filter pass removes those endpoints that
 * use bindings which are not supported by the issuer. If the remaining endpoints are not {@link IndexedEndpoint}s the
 * first endpoint in the list is returned. If the remaining endpoints are {@link IndexedEndpoint}s the first endpoint
 * with the isDefault attribute set to true is returned, if no isDefault attribute is set to true the first endpoint to
 * ommit this attribute is returned, and if all the endpoints have the isDefault attribute set to false then the first
 * endpoint in the list is returned.
 * 
 * Prior to selecting the endpoint the following fields <strong>must</strong> have had values set: entity role,
 * endpoint type, issuer supported bindings.
 * 
 * While this algorithm with work for selecting the endpoint for responses to {@link AuthnRequest}s the SAML
 * specification does stipulate additional endpoint selection criteria and as such the use of an endpoint selector
 * specifically meant to handler this situation should be used, for example: {@link AuthnResponseEndpointSelector}.
 */
public class BasicEndpointSelector extends AbstractEndpointSelector {

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public Endpoint selectEndpoint() {
        List<? extends Endpoint> endpoints = getRelyingPartyRole().getEndpoints(getEndpointType());
        if (endpoints == null || endpoints.size() == 0) {
            return null;
        }

        if (endpoints.get(0) instanceof IndexedEndpoint) {
            return selectIndexedEndpoint((List<IndexedEndpoint>) endpoints);
        } else {
            return selectNonIndexedEndpoint((List<Endpoint>) endpoints);
        }
    }

    /**
     * Selects an appropriate endpoint from a list of indexed endpoints.
     * 
     * @param endpoints list of indexed endpoints
     * 
     * @return appropriate endpoint from a list of indexed endpoints or null
     */
    protected Endpoint selectIndexedEndpoint(List<IndexedEndpoint> endpoints) {
        Iterator<IndexedEndpoint> endpointItr = endpoints.iterator();
        IndexedEndpoint firstNoDefaultEndpoint = null;
        IndexedEndpoint currentEndpoint;
        while (endpointItr.hasNext()) {
            currentEndpoint = endpointItr.next();
            // if endpoint binding not supported, remove it
            if (!getSupportedIssuerBindings().contains(currentEndpoint.getBinding())) {
                endpointItr.remove();
                continue;
            }

            // endpoint is the default endpoint
            if (currentEndpoint.isDefault() != null) {
                if (currentEndpoint.isDefault()) {
                    return currentEndpoint;
                }

                if (firstNoDefaultEndpoint == null) {
                    firstNoDefaultEndpoint = currentEndpoint;
                }
            }
        }

        if (firstNoDefaultEndpoint != null) {
            // no endpoint was marked as the default, return first unmarked endpoint
            return firstNoDefaultEndpoint;
        } else {
            if (endpoints.size() > 0) {
                // no endpoint had an index so return the first one
                return endpoints.get(0);
            } else {
                // no endpoints made it through the supported binding filter
                return null;
            }
        }
    }

    /**
     * Selects an appropriate endpoint from a list of non-indexed endpoints.
     * 
     * @param endpoints list of non-indexed endpoints
     * 
     * @return appropriate endpoint from a list of non-indexed endpoints or null
     */
    protected Endpoint selectNonIndexedEndpoint(List<Endpoint> endpoints) {
        Iterator<Endpoint> endpointItr = endpoints.iterator();
        Endpoint endpoint;
        while (endpointItr.hasNext()) {
            endpoint = endpointItr.next();
            // if endpoint binding not supported, remove it
            if (!getSupportedIssuerBindings().contains(endpoint.getBinding())) {
                endpointItr.remove();
                continue;
            }

            // Endpoint is first one of acceptable binding, return it.
            return endpoint;
        }

        // No endpoints had acceptable binding
        return null;
    }
}