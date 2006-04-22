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

package org.opensaml.saml2.core;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.xml.SAMLConstants;

/**
 * SAML 2.0 Core SubjectConfirmation
 */
public interface SubjectConfirmation extends SAMLObject {

    /** Element local name */
    public final static String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectConfirmation";
    
    /** Default element name */
    public final static QName DEFAULT_ELEMENT_NAME = new QName(SAMLConstants.SAML20_NS, DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
    
    /** Local name of the XSI type */
    public final static String TYPE_LOCAL_NAME = "SubjectConfirmationType"; 
        
    /** QName of the XSI type */
    public final static QName TYPE_NAME = new QName(SAMLConstants.SAML20_NS, TYPE_LOCAL_NAME, SAMLConstants.SAML20_PREFIX);

    /** Method attribute name */
    public final static String METHOD_ATTRIB_NAME = "Method";

    /**
     * Get the method used to confirm this subject.
     * 
     * @return the method used to confirm this subject
     */
    public String getMethod();

    /**
     * Sets the method used to confirm this subject.
     * 
     * @param newMethod the method used to confirm this subject
     */
    public void setMethod(String newMethod);

    /**
     * Gets the base identifier of the principal for this request.
     * 
     * @return the base identifier of the principal for this request
     */
    public BaseID getBaseID();

    /**
     * Sets the base identifier of the principal for this request.
     * 
     * @param newBaseID the base identifier of the principal for this request
     */
    public void setBaseID(BaseID newBaseID);

    /**
     * Gets the name identifier of the principal for this request.
     * 
     * @return the name identifier of the principal for this request
     */
    public NameID getNameID();

    /**
     * Sets the name identifier of the principal for this request.
     * 
     * @param newNameID the name identifier of the principal for this request
     */
    public void setNameID(NameID newNameID);

    // TODO EncryptedID

    /**
     * Gets the data about how this subject was confirmed or constraints on the confirmation.
     * 
     * @return the data about how this subject was confirmed or constraints on the confirmation
     */
    public SubjectConfirmationData getSubjectConfirmationData();

    /**
     * Sets the data about how this subject was confirmed or constraints on the confirmation.
     * 
     * @param newSubjectConfirmationData the data about how this subject was confirmed or constraints on the
     *            confirmation
     */
    public void setSubjectConfirmationData(SubjectConfirmationData newSubjectConfirmationData);
}