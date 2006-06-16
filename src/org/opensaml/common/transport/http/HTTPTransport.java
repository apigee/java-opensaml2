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

package org.opensaml.common.transport.http;

import org.opensaml.common.transport.Transport;

/**
 * A transport that uses HTTP over TCP/IP.
 */
public interface HTTPTransport extends Transport<HTTPRequest, HTTPResponse> {

    /**
     * Gets the HTTP version that this transport uses.
     * 
     * @return HTTP version that this transport uses
     */
    public HTTPVersion getHTTPVersion();

    /**
     * Gets whether the connection should be kept alive between message sends.
     * 
     * @return whether the connection should be kept alive between message sends
     */
    public boolean keepConnectionAlive();

    /**
     * Gets whether to follow redirects.
     * 
     * @return whether to follow redirects
     */
    public boolean followRedirects();

    /**
     * Type safe enumeration of HTTP protocol versions.
     */
    public final class HTTPVersion {

        /** HTTP 1.1 */
        public final static HTTPVersion HTTP11 = new HTTPVersion(1, 1);

        /** HTTP 1.0 */
        public final static HTTPVersion HTTP10 = new HTTPVersion(1, 0);

        /** HTTP major version number */
        private int majorVersion;

        /** HTTP minor version number */
        private int minorVersion;

        private String versionField;

        /**
         * Constructor
         * 
         * @param majorVersion HTTP major version number
         * @param minorVersion HTTP minor verion number
         */
        private HTTPVersion(int majorVersion, int minorVersion) {
            this.majorVersion = majorVersion;
            this.minorVersion = minorVersion;

            versionField = majorVersion + "." + minorVersion;
        }

        /**
         * Gets HTTP the major version number.
         * 
         * @return major version number
         */
        public int getMajorVersion() {
            return majorVersion;
        }

        /**
         * Gets HTTP the minor version number.
         * 
         * @return minor version number
         */
        public int getMinorVersion() {
            return minorVersion;
        }

        /**
         * Gets the string that may be used at the value for the HTTP-Version HTTP header field.
         * 
         * @return string that may be used at the value for the HTTP-Version HTTP header field
         */
        public String getVersionFieldValue() {
            return versionField;
        }
    }
}