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

/**
 * Exception indicating a problem communicating with the peer.
 */
public class CommunicationException extends BindingException {

    /** Serial version UID */
    private static final long serialVersionUID = -694241076620960317L;

    /**
     * Constructor.
     */
    public CommunicationException() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param message exception message
     */
    public CommunicationException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * 
     * @param wrappedException exception to be wrapped by this one
     */
    public CommunicationException(Exception wrappedException) {
        super(wrappedException);
    }

    /**
     * Constructor.
     * 
     * @param message exception message
     * @param wrappedException exception to be wrapped by this one
     */
    public CommunicationException(String message, Exception wrappedException) {
        super(message, wrappedException);
    }
}