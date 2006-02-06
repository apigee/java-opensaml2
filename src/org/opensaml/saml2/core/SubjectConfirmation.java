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

import org.opensaml.common.SAMLObject;

/**
 * SAML 2.0 Core SubjectConfirmation
 */
public interface SubjectConfirmation extends SAMLObject {
    
    /** Element local name */
    public final static String LOCAL_NAME = "SubjectConfirmation";
    
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
     * Gets the Identifier, {@link BaseID}, {@link NameID}, or {@link EncryptedID} for this subject.
     * 
     * @return the Identifier for this subject
     */
    public Identifier getIdentifier();

    /**
     * Sets the Identifier, {@link BaseID}, {@link NameID}, or {@link EncryptedID} for this subject.
     * 
     * @param newIdentifier the Identifier for this subject
     */
    public void setIdentifier(Identifier newIdentifier);

    /**
     * Gets the data about how this subject was confirmed or constraints on the confirmation.
     * 
     * @return the data about how this subject was confirmed or constraints on the confirmation
     */
    public SubjectConfirmationData getSubjectConfirmationData();
}