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

import java.util.List;

import javax.xml.namespace.QName;

import org.opensaml.common.xml.SAMLConstants;

/**
 * SAML 2.0 Core Response
 */
public interface Response extends StatusResponse {
    
    /** Element local name*/
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Response";
    
    /** Default element name */
    public final static QName DEFAULT_ELEMENT_NAME = new QName(SAMLConstants.SAML20P_NS, DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20P_PREFIX);
    
    /** Local name of the XSI type */
    public final static String TYPE_LOCAL_NAME = "ResponseType"; 
        
    /** QName of the XSI type */
    public final static QName TYPE_NAME = new QName(SAMLConstants.SAML20P_NS, TYPE_LOCAL_NAME, SAMLConstants.SAML20P_PREFIX);
    
    /**
     * Return the list of Assertion child elements
     * 
     * @return  the list of Assertion child elements
     */
    public List<Assertion> getAssertions();

    /**
     * Return the list of EncryptedAssertion child elements
     * 
     * @return  the list of EncryptedAssertion child elements
     */
    //TODO enable when EncryptedAssertion available
    //public List<EncryptedAssertion> getEncryptedAssertions();
}
