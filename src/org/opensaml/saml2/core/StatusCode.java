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

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.xml.SAMLConstants;

/**
 * SAML 2.0 Core StatusCode
 */
public interface StatusCode extends SAMLObject {

    /** Local Name of StatusCode */
    public final static String DEFAULT_ELEMENT_LOCAL_NAME = "StatusCode";
    
    /** Default element name */
    public final static QName DEFUALT_ELEMENT_NAME = new QName(SAMLConstants.SAML20P_NS, DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20P_PREFIX);
    
    /** Local name of the XSI type */
    public final static String TYPE_LOCAL_NAME = "StatusCodeType"; 
        
    /** QName of the XSI type */
    public final static QName TYPE_NAME = new QName(SAMLConstants.SAML20P_NS, TYPE_LOCAL_NAME, SAMLConstants.SAML20P_PREFIX);

    /** Local Name of the Value attribute */
    public final static String VALUE_ATTRIB_NAME = "Value";

    /**
     * Gets the Status Code of this Status Code.
     * 
     * @return StatusCode StatusCode
     */
    public StatusCode getStatusCode();

    /**
     * Sets the Status Code of this Status Code.
     * 
     * @param newStatusCode the Status Code of this Status Code.
     */
    public void setStatusCode(StatusCode newStatusCode);

    /**
     * Gets the Value of this Status Code.
     * 
     * @return StatusCode Value
     */
    public String getValue();

    /**
     * Sets the Value of this Status Code.
     * 
     * @param newValue the Value of this Status Code
     */
    public void setValue(String newValue);
}
