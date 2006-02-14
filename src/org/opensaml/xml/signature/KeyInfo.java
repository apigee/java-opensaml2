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

package org.opensaml.xml.signature;

import org.opensaml.xml.XMLObject;

/**
 * XMLObject representing XML Digital Signature, version 20020212, KeyInfo element.
 */
public interface KeyInfo extends XMLObject {

    /** Element local name */
    public final static String LOCAL_NAME = "KeyInfo";
    
    /** Id attribute name */
    public final static String ID_ATTRIB_NAME = "Id";

    /**
     * Gets the XML ID of this key info.
     * 
     * @return the XML ID of this key info
     */
    public String getId();

    /**
     * Sets the XML ID of this key info.
     * 
     * @param newId the XML ID of this key info
     */
    public void setId(String newId);
    
    /**
     * Gets the name of the key.
     * 
     * @return the name of the key
     */
    public KeyName getKeyName();
    
    /**
     * Sets the name of the key.
     * 
     * @param newKeyName the name of the key
     */
    public void setKeyName(KeyName newKeyName);
    
    /**
     * Gets the value of the key.
     * 
     * @return the value of the key
     */
    public KeyValue getKeyValue();
    
    /**
     * Sets the value of the key.
     * 
     * @param newKeyValue the value of the key
     */
    public void setKeyValue(KeyValue newKeyValue);
    
    /**
     * Gets the method used to retrieve additional key data.
     * 
     * @return the method used to retrieve additional key data
     */
    public RetrievalMethod getRetrievalMethod();
    
    /**
     * Sets the method used to retrieve additional key data.
     * 
     * @param newRetrievalMethod the method used to retrieve additional key data
     */
    public void setRetrievalMethod(RetrievalMethod newRetrievalMethod);
    
    /**
     * Gets X509 data (keys or certs).
     * 
     * @return X509 data (keys or certs)
     */
    public X509Data getX509Data();
    
    /**
     * Sets X509 data (keys or certs).
     * 
     * @param newX509Data X509 data (keys or certs)
     */
    public void setX509Data(X509Data newX509Data);
    
    /**
     * Get information related to PGP keys.
     * 
     * @return information related to PGP keys
     */
    public PGPData getPGPData();
    
    /**
     * Sets information related to PGP keys.
     * 
     * @param newPGPData information related to PGP keys
     */
    public void setPGPData(PGPData newPGPData);
    
    /**
     * Gets information related to SPKI keys.
     * 
     * @return information related to SPKI keys
     */
    public SPKIData getSPKIData();
    
    /**
     * Sets information related to SPKI keys.
     * 
     * @param newSPKIData information related to SPKI keys
     */
    public void setSPKIData(SPKIData newSPKIData);
    
    /**
     * Gets information for conveying information through in-band transmission.
     * 
     * @return information for conveying information through in-band transmission
     */
    public MgmtData getMgmtData();
    
    /**
     * Sets information for conveying information through in-band transmission.
     * 
     * @param newMgmtData information for conveying information through in-band transmission
     */
    public void setMgmtData(MgmtData newMgmtData);
}