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

package org.opensaml.saml1.core;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.xml.SAMLConstants;

/**
 * This interface defines how the object representing a SAML 1 <code> StatusCode</code> element behaves.
 */
public interface StatusCode extends SAMLObject {

    /** Element name, no namespace. */
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "StatusCode";

    /** Default element name. */
    public static final QName DEFAULT_ELEMENT_NAME = new QName(SAMLConstants.SAML10P_NS, DEFAULT_ELEMENT_LOCAL_NAME,
            SAMLConstants.SAML1P_PREFIX);

    /** Local name of the XSI type. */
    public static final String TYPE_LOCAL_NAME = "StatusCodeType";

    /** QName of the XSI type. */
    public static final QName TYPE_NAME = new QName(SAMLConstants.SAML10P_NS, TYPE_LOCAL_NAME,
            SAMLConstants.SAML1P_PREFIX);

    /** Success status value. */
    public static final String SUCCESS = "Success";

    /** VersionMismatch status value. */
    public static final String VERSION_MISMATCH = "VersionMismatch";

    /** Requester status value. */
    public static final String REQUESTER = "Requester";
    
    /** Responder status value. */
    public static final String RESPONDER = "Responder";

    /** RequestVersionTooHigh status value. */
    public static final String REQUEST_VERSION_TOO_HIGH = "RequestVersionTooHigh";

    /** RequestVersionTooLow status value. */
    public static final String REQUEST_VERSION_TOO_LOW = "RequestVersionTooLow";

    /** RequestVersionDepricated status value. */
    public static final String REQUEST_VERSION_DEPRICATED = "RequestVersionDepricated";

    /** TooManyResponses status value. */
    public static final String TOO_MANY_RESPONSES = "TooManyResponses";

    /** RequestDenied status value. */
    public static final String REQUEST_DENIED = "RequestDenied";

    /** ResourceNotRecognized status value. */
    public static final String RESOURCE_NOT_RECOGNIZED = "ResourceNotRecognized";

    /** Name for the attribute which defines the Value. */
    public static final String VALUE_ATTRIB_NAME = "Value";

    /**
     * Gets the value of the status code.
     * 
     * @return value of the status code
     */
    public String getValue();

    /**
     * Sets the value of the status code.
     * 
     * @param value value of the status code
     */
    public void setValue(String value);

    /**
     * Gets the second level status code.
     * 
     * @return second level status code
     */
    public StatusCode getStatusCode();

    /**
     * Sets the second level status code.
     * 
     * @param statusCode second level status code
     * @throws IllegalArgumentException
     */
    public void setStatusCode(StatusCode statusCode);
}