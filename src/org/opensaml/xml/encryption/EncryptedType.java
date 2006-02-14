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

package org.opensaml.xml.encryption;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.KeyInfo;

/**
 * XMLObject representing XML Encryption, version 20021210, EncryptedType schema type.
 */
public interface EncryptedType extends XMLObject {

    /** Id attribute name */
    public final static String ID_ATTRIB_NAME = "Id";

    /** Type attribute name */
    public final static String TYPE_ATTRIB_NAME = "Type";

    /** Mime type attribute name */
    public final static String MIME_TYPE_ATTRIB_NAME = "MimeType";

    /** Encoding attribute name */
    public final static String ENCODING_ATTRIB_NAME = "Encoding";

    /**
     * Gets the Id of the element.
     * 
     * @return the Id of the element
     */
    public String getId();

    /**
     * Sets the Id of the element.
     * 
     * @param newId the Id of the element
     */
    public void setId(String newId);

    /**
     * Gets information about the type of thing that was encrypted.
     * 
     * @return information about the type of thing that was encrypted
     */
    public String getType();

    /**
     * Sets information about the type of thing that was encrypted.
     * 
     * @param newType information about the type of thing that was encrypted
     */
    public void setType(String newType);

    /**
     * Gets the mime type of the encrypted thing.
     * 
     * @return the mime type of the encrypted thing
     */
    public String getMimeType();

    /**
     * Sets the mime type of the encrypted thing.
     * 
     * @param newType the mime type of the encrypted thing
     */
    public void setMimeType(String newType);

    /**
     * Gets the encoding of the encrypted thing.
     * 
     * @return the encoding of the encrypted thing
     */
    public String getEncoding();

    /**
     * Sets the encoding of the encrypted thing.
     * 
     * @param newEncoding the encoding of the encrypted thing
     */
    public void setEncoding(String newEncoding);

    /**
     * Gets the encryption method used.
     * 
     * @return the encryption method used
     */
    public EncryptionMethod getEncryptionMethod();

    /**
     * Sets the encryption method used.
     * 
     * @param newMethod the encryption method used
     */
    public void setEncryptionMethod(EncryptionMethod newMethod);

    /**
     * Gets info about the key used to perform the encryption.
     * 
     * @return info about the key used to perform the encryption
     */
    public KeyInfo getKeyInfo();

    /**
     * Sets info about the key used to perform the encryption.
     * 
     * @param newKeyInfo info about the key used to perform the encryption
     */
    public void setKeyInfo(KeyInfo newKeyInfo);

    /**
     * Gets the cipher value, or reference to it.
     * 
     * @return the cipher value, or reference to it
     */
    public CipherData getCipherData();

    /**
     * Sets the cipher value, or reference to it.
     * 
     * @param newCipherData the cipher value, or reference to it
     */
    public void setCipherData(CipherData newCipherData);

    /**
     * Gets additional properties about the encryption.
     * 
     * @return additional properties about the encryption
     */
    public EncryptionProperties getProperties();

    /**
     * Sets additional properties about the encryption.
     * 
     * @param newProperties additional properties about the encryption
     */
    public void setProperties(EncryptionProperties newProperties);
}