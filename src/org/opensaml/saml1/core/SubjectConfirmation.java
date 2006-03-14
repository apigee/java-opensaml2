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

/**
 * 
 */

package org.opensaml.saml1.core;

import java.util.List;

import org.apache.xml.security.keys.KeyInfo;
import org.opensaml.common.SAMLObject;

/**
 * Interface to define how a <code> SubjectConfirmation  <\code> element behaves
 */
public interface SubjectConfirmation extends SAMLObject {

    /** Element name, no namespace. */
    public final static String LOCAL_NAME = "SubjectConfirmation";

    /** Get the list with all the ConfirmationMethods.  This suitable for calls to add() */
    public List<ConfirmationMethod> getConfirmationMethods();

    /** Set the SubjectConfirmationData */
    public void setSubjectConfirmationData(SubjectConfirmationData subjectConfirmationData) throws IllegalArgumentException;

    /** Return the SubjectConfirmationData */
    public SubjectConfirmationData getSubjectConfirmationData();

    // TODO - how do we deal with KeyInfo
    // TODO looks like KeyInfo needs to be changed to the XMLTooling KeyInfo type, check with Chad.
    
    public KeyInfo getKeyInfo();

    public void setKeyInfo(KeyInfo keyInfo);
}
