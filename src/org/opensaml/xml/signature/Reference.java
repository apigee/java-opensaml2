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
 * XMLObject representing XML Digital Signature, version 20020212, Reference element.
 */
public interface Reference extends XMLObject {

    /** Element local name */
    public final static String LOCAL_NAME = "Reference";

    /** Id attribute name */
    public final static String ID_ATTRIB_NAME = "Id";

    /** URI attribute name */
    public final static String URI_ATTRIB_NAME = "URI";

    /** Type attribute name */
    public final static String TYPE_ATTRIB_NAME = "Type";

    /**
     * Gets the unique ID fo the element.
     * 
     * @return the unique ID fo the element
     */
    public String getId();

    /**
     * Sets the unique ID fo the element.
     * 
     * @param newId the unique ID fo the element
     */
    public void setId(String newId);

    /**
     * Gets the URI of the referenced content.
     * 
     * @return the URI of the referenced content
     */
    public String getURI();

    /**
     * Sets the URI of the referenced content.
     * 
     * @param newURI the URI of the referenced content
     */
    public void setURI(String newURI);

    /**
     * Gets the type of the referenced content.
     * 
     * @return the type of the referenced content
     */
    public String getType();

    /**
     * Sets the type of the referenced content.
     * 
     * @param newType the type of the referenced content
     */
    public void setType(String newType);

    /**
     * Gets the tranforms applied to the referenced content.
     * 
     * @return the tranforms applied to the referenced content
     */
    public Transforms getTransforms();

    /**
     * Sets the tranforms applied to the referenced content.
     * 
     * @param newTransforms the tranforms applied to the referenced content
     */
    public void setTransforms(Transforms newTransforms);

    /**
     * Gets the digest method used when creating the signature.
     * 
     * @return the digest method used when creating the signature
     */
    public DigestMethod getDigestMethod();

    /**
     * Sets the digest method used when creating the signature.
     * 
     * @param newMethod the digest method used when creating the signature
     */
    public void setDigestMethod(DigestMethod newMethod);

    /**
     * Gets the digest value used when creating the signature.
     * 
     * @return the digest value used when creating the signature
     */
    public DigestValue getDigestValue();

    /**
     * Sets the digest value used when creating the signature.
     * 
     * @param newValue the digest value used when creating the signature
     */
    public void setDigestValue(DigestValue newValue);
}