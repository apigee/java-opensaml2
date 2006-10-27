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

package org.opensaml.common.binding;

import javax.servlet.http.HttpServletRequest;

import org.opensaml.xml.XMLObject;

/**
 * A factory for a rule that checks that certain HTTP request characterisitics are met. Specifically the following items
 * are tested:
 * 
 * <ul>
 * <li>Request's content type contains the given content type</li>
 * <li>Request's character encoding matches the given character encoding</li>
 * <li>Request's scheme (http, https) match the given scheme</li>
 * <li>Request's method (POST, GET) match the given request method</li>
 * <li>Request used a secure transport (SSL, TLS) if it was required to be secure</li>
 * </ul>
 * 
 * Any option that is not set is not evaluated.
 */
public class HTTPRuleFactory implements SecurityPolicyRuleFactory<HttpServletRequest> {

    /** Expected content type of the request */
    private String contentType;

    /** Expected method of the request */
    private String requestMethod;

    /** Expected character encoding of the request */
    private String characterEncoding;

    /** Expected scheme of the request */
    private String requestScheme;

    /** Whether the request must be secure */
    private boolean requireSecured;

    /**
     * Gets the expected content type of the request.
     * 
     * @return expected content type of the request
     */
    public String getCharacterEncoding() {
        return characterEncoding;
    }

    /**
     * Sets the expected content type of the request.
     * 
     * @param characterEncoding expected content type of the request
     */
    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    /**
     * Gets the expected content type of the request.
     * 
     * @return expected content type of the request
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the expected content type of the request.
     * 
     * @param contentType expected content type of the request
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Gets the expected method of the request.
     * 
     * @return expected method of the request
     */
    public String getRequestMethod() {
        return requestMethod;
    }

    /**
     * Sets expected method of the request.
     * 
     * @param requestMethod expected method of the request
     */
    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    /**
     * Gets the expected scheme of the request.
     * 
     * @return expected scheme of the request
     */
    public String getRequestScheme() {
        return requestScheme;
    }

    /**
     * Sets the expected scheme of the request.
     * 
     * @param requestScheme expected scheme of the request
     */
    public void setRequestScheme(String requestScheme) {
        this.requestScheme = requestScheme;
    }

    /**
     * Gets whether a secure request is required.
     * 
     * @return whether a secure request is required
     */
    public boolean isRequireSecured() {
        return requireSecured;
    }

    /**
     * Sets whether a secure request is required.
     * 
     * @param requireSecured whether a secure request is required
     */
    public void setRequireSecured(boolean requireSecured) {
        this.requireSecured = requireSecured;
    }

    /** {@inheritDoc} */
    public SecurityPolicyRule<HttpServletRequest> createRuleInstance() {
        return new HTTPRule(getContentType(), getCharacterEncoding(), getRequestScheme(), getRequestMethod(),
                isRequireSecured());
    }

    /**
     * Policy rule for checking basic HTTP request requirements.
     */
    protected class HTTPRule implements SecurityPolicyRule<HttpServletRequest> {

        /** Expected content type of the request */
        private String contentType;

        /** Expected method of the request */
        private String requestMethod;

        /** Expected character encoding of the request */
        private String characterEncoding;

        /** Expected scheme of the request */
        private String requestScheme;

        /** Whether the request must be secure */
        private boolean requireSecured;

        /**
         * Constructor
         * 
         * @param contentType expected content type
         * @param characterEncoding expected character encoding
         * @param requestScheme expected scheme
         * @param requestMethod expected request method
         * @param requiredSecured whether the request must be secured
         */
        public HTTPRule(String contentType, String characterEncoding, String requestScheme, String requestMethod,
                boolean requiredSecured) {
            this.contentType = contentType;
            this.characterEncoding = characterEncoding;
            this.requestScheme = requestScheme;
            this.requestMethod = requestMethod;
            this.requireSecured = requiredSecured;
        }

        /** {@inheritDoc} */
        public void evaluate(HttpServletRequest request, XMLObject message) throws BindingException {
            if (contentType != null) {
                if (!request.getContentType().contains(contentType)) {
                    throw new BindingException("Invalid content type, expected " + contentType + " but was "
                            + request.getContentType());
                }
            }

            if (characterEncoding != null) {
                if (!request.getCharacterEncoding().equals(characterEncoding)) {
                    throw new BindingException("Invalid character encoding, expected " + characterEncoding
                            + " but was " + request.getCharacterEncoding());
                }
            }

            if (requestScheme != null) {
                if (!request.getScheme().equals(requestScheme)) {
                    throw new BindingException("Invalid request scheme, expected " + requestScheme + " but was "
                            + request.getScheme());
                }
            }

            if (requestMethod != null) {
                if (!request.getMethod().equals(requestMethod)) {
                    throw new BindingException("Invalid request method, expected " + requestMethod + " but was "
                            + request.getMethod());
                }
            }

            if (requireSecured) {
                if (!request.isSecure()) {
                    throw new BindingException("Request was required to be secured but was not");
                }
            }
        }

        /** {@inheritDoc} */
        public String getIssuer() {
            return null;
        }
    }
}
