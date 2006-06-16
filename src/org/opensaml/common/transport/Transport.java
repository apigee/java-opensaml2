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

package org.opensaml.common.transport;

import java.io.IOException;

/**
 * Transports are used to transmit SAML from one party to another.
 */
public interface Transport<RequestType extends Request, ResponseType extends Response> {

    /**
     * Connect to the other party.
     * 
     * @throws IOException thrown if a connection can not be made to the other party.
     */
    public void connect() throws IOException;

    /**
     * Sends a request to the other party and returns the response.
     * 
     * @param resquest the request to send
     * 
     * @return the response
     */
    public ResponseType sendRequest(RequestType resquest);
    
    /**
     * Disconnect from the other party.
     */
    public void disconnect();

    /**
     * Check whether this transport is connected.
     * 
     * @return true if this transport is connected, false if not
     */
    public boolean isConnected();

    /**
     * Get the URI representation of the endpoint this transport connects to.
     * 
     * @return URI representation of the endpoint this transport connects to
     */
    public String getLocationURI();

    /**
     * Gets the length of time, in milliseconds, that transport will wait for the other party to answer when attempting
     * to connect.
     * 
     * @return length of time, in milliseconds, that transport will wait for the other party to answer when attempting
     *         to connect
     */
    public long getConnectionTimeout();
}