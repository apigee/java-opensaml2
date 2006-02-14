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

/**
 * XMLObject representing XML Encryption, version 20021210, CipherReference element.
 */
public interface CipherReference extends XMLObject {

    /** Element local name */
    public final static String LOCAL_NAME = "CipherReference";

    /** URI attribute name */
    public final static String URI_ATTRIB_NAME = "URI";

    /**
     * Gets the URI to the cipher value.
     * 
     * @return the URI to the cipher value
     */
    public String getURI();

    /**
     * Sets the URI to the cipher value.
     * 
     * @param newURI the URI to the cipher value
     */
    public void setURI(String newURI);

    /**
     * Gets the transforms applied to the resolved contents of the URI to get the cipher value.
     * 
     * @return the transforms applied to the resolved contents of the URI to get the cipher value
     */
    public Transforms getTransforms();

    /**
     * Sets the transforms applied to the resolved contents of the URI to get the cipher value.
     * 
     * @param newTransforms the transforms applied to the resolved contents of the URI to get the cipher value
     */
    public void setTransforms(Transforms newTransforms);
}