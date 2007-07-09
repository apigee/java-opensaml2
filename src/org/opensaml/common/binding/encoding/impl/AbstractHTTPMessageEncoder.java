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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.opensaml.common.binding.BindingException;
import org.opensaml.common.binding.encoding.HTTPMessageEncoder;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.util.DatatypeHelper;

/**
 * Base class handling boilerplate code for HTTP message encoders.
 */
public abstract class AbstractHTTPMessageEncoder extends AbstractMessageEncoder<HttpServletResponse> implements
        HTTPMessageEncoder {

    /**
     * Adds cache control and pragma headers that are meant to disable caching.
     */
    protected void initializeResponse() {
        getResponse().setCharacterEncoding("UTF-8");
        getResponse().addHeader("Cache-control", "no-cache, no-store");
        getResponse().addHeader("Pragma", "no-cache");
    }

    /**
     * Gets the relay state in a URL-encoded form.
     * 
     * @return the URL-encoded relay state
     * 
     * @throws BindingException thrown if there is a problem encoding the relay state
     */
    protected String getEncodeRelayState() throws BindingException {
        if (!DatatypeHelper.isEmpty(getRelayState())) {
            try {
                return URLEncoder.encode(getRelayState(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new BindingException("VM does not support UTF-8 encoding");
            }
        }

        return null;
    }

    /**
     * Gets the SAML message as a base64, no line break, string.
     * 
     * @return base64 encoded message
     * 
     * @throws BindingException thrown if there is a problem encoding the message
     */
    protected String getBase64EncodedMessage() throws BindingException {
        String messageXML = marshallMessage(getSamlMessage());
        return new String(Base64.encodeBytes(messageXML.getBytes(), Base64.DONT_BREAK_LINES));
    }
}