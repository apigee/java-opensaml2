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

package org.opensaml.saml1.core;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.xml.SAMLConstants;

/**
 * Interface to define how a <code> SubjectLocality  <\code> element behaves
 */
public interface SubjectLocality extends SAMLObject {

    /** Element name, no namespace. */
    public final static String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectLocality";
    
    /** Default element name */
    public final static QName DEFUALT_ELEMENT_NAME = new QName(SAMLConstants.SAML1P_NS, DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML1P_PREFIX);
    
    /** Local name of the XSI type */
    public final static String TYPE_LOCAL_NAME = "SubjectLocalityType"; 
        
    /** QName of the XSI type */
    public final static QName TYPE_NAME = new QName(SAMLConstants.SAML1P_NS, TYPE_LOCAL_NAME, SAMLConstants.SAML1P_PREFIX);

    /** Name for the IPAddress attribute  */
    public final static String IPADDRESS_ATTRIB_NAME = "IPAddress";

    /** Name for the DNSAddress attribute  */
    public final static String DNSADDRESS_ATTRIB_NAME = "DNSAddress";
    
    /** Getter for IPAddress */
    public String getIPAddress();

    /** Setter for IPAddress */
    public void setIPAddress(String address);

    /** Getter for DNSAddress */
    public String getDNSAddress();

    /** Setter for DNSAddress */
    public void setDNSAddress(String address);
}
