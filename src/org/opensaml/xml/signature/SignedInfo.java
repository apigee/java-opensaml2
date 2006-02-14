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
 * XMLObject representing XML Digital Signature, version 20020212, SignedInfo element.
 */
public interface SignedInfo extends XMLObject {

    /** Element local name */
    public final static String LOCAL_NAME = "SignedInfo";

    /** Id attribute name */
    public final static String ID_ATTRIB_NAME = "Id";
    
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
     * Gets the canonicalization method used for the signature.
     * 
     * @return the canonicalization method used for the signature
     */
    public CanonicalizationMethod getCanonicalizationMethod();

    /**
     * Sets the canonicalization method used for the signature.
     * 
     * @param newMethod the canonicalization method used for the signature
     */
    public void setCanonicalizationMethod(CanonicalizationMethod newMethod);

    /**
     * Gets the algorithm used to generate the signature.
     * 
     * @return the algorithm used to generate the signature
     */
    public SignatureMethod getSignatureMethod();

    /**
     * Sets the algorithm used to generate the signature.
     * 
     * @param newMethod the algorithm used to generate the signature
     */
    public void setSignatureMethod(SignatureMethod newMethod);

    /**
     * Gets the references to the signed content.
     * 
     * @return the references to the signed content
     */
    public List<Reference> getReferences();
}