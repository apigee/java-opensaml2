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

import java.util.List;

import org.opensaml.xml.XMLObject;

/**
 * XMLObject representing XML Digital Signature, version 20020212, Manifest element.
 */
public interface Manifest extends XMLObject {

    /** Element local name */
    public final static String LOCAL_NAME = "Manifest";

    /**
     * Get the ID of this object.
     * 
     * @return the ID of this object
     */
    public String getId();

    /**
     * Sets the ID of this object.
     * 
     * @param newId the ID of this object
     */
    public void setId(String newId);
    
    /**
     * The list of references in this manifest.
     * 
     * @return list of references in this manifest
     */
    public List<Reference> getReference();
}