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
 * XMLObject representing XML Encryption, version 20021210, ReferenceList element.
 */
public interface ReferenceList extends XMLObject {

    /** Element local name */
    public final static String LOCAL_NAME = "ReferenceList";

    /**
     * Gets the list of references; both data and key references.
     * 
     * @return the list of references; both data and key references
     */
    public List<ReferenceType> getReferences();

    /**
     * Gets the list of data references.
     * 
     * @return the list of data references
     */
    public List<DataReference> getDataReferences();

    /**
     * Sets the list of key references.
     * 
     * @return the list of key references
     */
    public List<KeyReference> getKeyReferences();
}