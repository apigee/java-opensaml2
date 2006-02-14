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

import java.util.List;

import org.opensaml.xml.XMLObject;

/**
 * XMLObject representing XML Encryption, version 20021210, EncryptionProperties element.
 */
public interface EncryptionProperties extends XMLObject {

    /** Element local name */
    public final static String LOCAL_NAME = "EncryptionProperties";

    /** Id attribute name */
    public final static String ID_ATTRIB_NAME = "Id";

    /**
     * Gets the ID for the xml element.
     * 
     * @return the ID for the xml element
     */
    public String getId();

    /**
     * Sets the ID for the xml element.
     * 
     * @param newId the ID for the xml element
     */
    public void setId(String newId);

    /**
     * Gets the encryption properties.
     * 
     * @return the encryption properties
     */
    public List<EncryptionProperty> getProperties();
}