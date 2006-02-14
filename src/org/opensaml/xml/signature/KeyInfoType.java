
package org.opensaml.xml.signature;

import org.opensaml.xml.XMLObject;

/**
 * XMLObject representing XML Digital Signature, version 20020212, KeyInfoType schema type.
 */
public interface KeyInfoType extends XMLObject {

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