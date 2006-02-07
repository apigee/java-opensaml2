/*
 * Copyright [2005] [University Corporation for Advanced Internet Development, Inc.]
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

/**
 * 
 */

package org.opensaml.saml2.core;

import org.opensaml.common.SAMLObject;

/**
 * SAML 2.0 Core Status
 */
public interface Status extends SAMLObject {

    /** Local Name of Status */
    public final static String LOCAL_NAME = "Status";

    /**
     * Gets the Code of this Status.
     * 
     * @return Status StatusCode
     */
    public StatusCode getStatusCode();

    /**
     * Sets the Code of this Status.
     * 
     * @param newStatusCode the Code of this Status
     */
    public void setStatusCode(StatusCode newStatusCode);

    /**
     * Gets the Message of this Status.
     * 
     * @return Status StatusMessage
     */
    public StatusMessage getStatusMessage();

    /**
     * Sets the Message of this Status.
     * 
     * @param newStatusMessage the Message of this Status
     */
    public void setStatusMessage(StatusMessage newStatusMessage);

    /**
     * Gets the Detail of this Status.
     * 
     * @return Status StatusDetail
     */
    public StatusDetail getStatusDetail();

    /**
     * Sets the Detail of this Status.
     * 
     * @param newStatusDetail the Detail of this Status
     */
    public void setStatusDetail(StatusDetail newStatusDetail);
}