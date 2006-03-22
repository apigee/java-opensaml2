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

import java.util.List;

import org.opensaml.common.SAMLObject;

/**
 * SAML 2.0 Core Subject
 */
public interface Subject extends SAMLObject {

    /** Element local name */
    public final static String LOCAL_NAME = "Subject";

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
     * Gets the confirmations made about this subject.
     * 
     * @return the confirmations made about this subject
     */
    public List<SubjectConfirmation> getSubjectConfirmations();
}