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
 * XMLObject representing XML Digital Signature, version 20020212, RetrievalMethod element.
 */
public interface RetrievalMethod extends XMLObject {

    /** Element local name */
    public final static String LOCAL_NAME = "RetrievalMethod";

    /** URI attribute name */
    public final static String URI_ATTRIB_NAME = "URI";

    /** Type attribute name */
    public final static String TYPE_ATTRIB_NAME = "Type";

    /**
     * Gets the URI reference to the KeyInfo for a KeyValue.
     * 
     * @return the URI reference to the KeyInfo for a KeyValue
     */
    public String getURI();

    /**
     * Sets the URI reference to the KeyInfo for a KeyValue.
     * 
     * @param newURI the URI reference to the KeyInfo for a KeyValue
     */
    public void setURI(String newURI);

    /**
     * Gets the type of data to be retrieved from the URI.
     * 
     * @return the type of data to be retrieved from the URI
     */
    public String getType();

    /**
     * Sets the type of data to be retrieved from the URI.
     * 
     * @param newType the type of data to be retrieved from the URI
     */
    public void setType(String newType);

    /**
     * Gets the transforms to apply to retrieved info.
     * 
     * @return the transforms to apply to retrieved info
     */
    public Transforms getTransforms();

    /**
     * Sets the transforms to apply to retrieved info.
     * 
     * @param newTransforms the transforms to apply to retrieved info
     */
    public void setTransforms(Transforms newTransforms);
}