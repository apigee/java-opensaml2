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

package org.opensaml.saml2.metadata;

import javax.xml.namespace.QName;

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.xml.XMLObject;

/**
 * XMLObject representing XML Encryption, version 20021210, EncryptionMethod element.
 */
public interface EncryptionMethod extends XMLObject {
    
    /** Element local name */
    public final static String DEFAULT_ELEMENT_LOCAL_NAME = "EncryptionMethod";
    
    /** Default element name */
    public final static QName DEFUALT_ELEMENT_NAME = new QName(SAMLConstants.SAML20MD_NS, DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
    
    /** Local name of the XSI type */
    public final static String TYPE_LOCAL_NAME = "EncryptionMethodType"; 
        
    /** QName of the XSI type */
    public final static QName TYPE_NAME = new QName(SAMLConstants.XMLENC_NS, TYPE_LOCAL_NAME, SAMLConstants.XMLENC_PREFIX);

    /**
     * Gets the size of the key used.
     * 
     * @return the size of the key used
     */
    public Integer getKeySize();
    
    /**
     * Sets the size of the key used.
     * 
     * @param newSize the size of the key used
     */
    public void setKeySize(Integer newSize);
    
    /**
     * Gets the OAE parameters used as a Base64 encoded string.
     * 
     * @return the OAE parameters used as a Base64 encoded string
     */
    public String getOAEParams();
    
    /**
     * Sets the OAE parameters used as a Base64 encoded string.
     * 
     * @param newParams the OAE parameters used as a Base64 encoded string
     */
    public void setOAEParams(String newParams);
    
    /**
     * Gets the algorithm used to perform the encryption.
     * 
     * @return the algorithm used to perform the encryption
     */
    public String getAlgorithm();
    
    /**
     * Sets the algorithm used to perform the encryption.
     * 
     * @param newAlgorithm the algorithm used to perform the encryption
     */
    public void setAlgorithm(String newAlgorithm);
}