/*
 * Copyright 2008 Members of the EGEE Collaboration.
 * Copyright 2008 University Corporation for Advanced Internet Development, Inc.
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

package org.opensaml.xacml.ctx.provider;

/** Exception thrown if there is a problem evaluating an obligation. */
public class ObligationProcessingException extends Exception {

    /** Serial version UID. */
    private static final long serialVersionUID = -8978474052544318919L;

    /** Constructor. */
    public ObligationProcessingException() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param message exception message
     */
    public ObligationProcessingException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * 
     * @param wrappedException exception to be wrapped by this one
     */
    public ObligationProcessingException(Exception wrappedException) {
        super(wrappedException);
    }

    /**
     * Constructor.
     * 
     * @param message exception message
     * @param wrappedException exception to be wrapped by this one
     */
    public ObligationProcessingException(String message, Exception wrappedException) {
        super(message, wrappedException);
    }
}