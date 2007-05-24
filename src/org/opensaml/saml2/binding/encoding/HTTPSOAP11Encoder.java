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

package org.opensaml.saml2.binding.encoding;

import org.opensaml.common.binding.encoding.impl.AbstractSOAPHTTPEncoder;

/**
 * SAML 2.0 SOAP 1.1 over HTTP binding encoder.
 */
public class HTTPSOAP11Encoder extends AbstractSOAPHTTPEncoder {

    /** URI for this binding. */
    public static final String BINDING_URI = "urn:oasis:names:tc:SAML:2.0:bindings:SOAP";

    /** {@inheritDoc} */
    public String getBindingURI() {
        return BINDING_URI;
    }
}