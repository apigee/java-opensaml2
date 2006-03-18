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

package org.opensaml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.xml.security.keys.KeyInfo;
import org.opensaml.common.SAMLVersion;
import org.opensaml.saml1.core.ConfirmationMethod;
import org.opensaml.saml1.core.SubjectConfirmation;
import org.opensaml.saml1.core.SubjectConfirmationData;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of a <code> SubjectConfirmation </code> object
 */
public class SubjectConfirmationImpl extends AbstractAssertionSAMLObject implements SubjectConfirmation {

    /** Contains the list of ConfirmationMethods */
    private final List<ConfirmationMethod> confirmationMethods;

    /** Contains the SubjectConfirmationData element */
    private SubjectConfirmationData subjectConfirmationData;
    
    /** Contains the KeyInfo element */
    KeyInfo keyInfo;
    
    //TODO looks like KeyInfo needs to be changed to the XMLTooling KeyInfo type, check with Chad.

    /**
     * Hidden Constructor
     * @deprecated
     */
    private SubjectConfirmationImpl() {
        super(SubjectConfirmation.LOCAL_NAME, null);

        confirmationMethods = null;
    }
    
    /**
     * Constructor
     *
     * @param version the {@link SAMLVersion} to set
     * 
     */
    protected SubjectConfirmationImpl(SAMLVersion version) {
        super(SubjectConfirmation.LOCAL_NAME, version);

        confirmationMethods = new XMLObjectChildrenList<ConfirmationMethod>(this);
    }

    /*
     * @see org.opensaml.saml1.core.SubjectConfirmation#getSubjectConfirmationMethods()
     */
    public List<ConfirmationMethod> getConfirmationMethods() {
        return confirmationMethods;
    }

    /*
     * @see org.opensaml.saml1.core.SubjectConfirmation#setSubjectConfirmationData(org.opensaml.saml1.core.SubjectConfirmationData)
     */
    public void setSubjectConfirmationData(SubjectConfirmationData subjectConfirmationData)
            throws IllegalArgumentException {

        this.subjectConfirmationData = prepareForAssignment(this.subjectConfirmationData, subjectConfirmationData);
    }

    /*
     * @see org.opensaml.saml1.core.SubjectConfirmation#getSubjectConfirmationData()
     */
    public SubjectConfirmationData getSubjectConfirmationData() {
        return subjectConfirmationData;
    }

    /*
     * @see org.opensaml.saml1.core.SubjectConfirmation#getKeyInfo()
     */
    public KeyInfo getKeyInfo() {
        return keyInfo;
    }

    /*
     * @see org.opensaml.saml1.core.SubjectConfirmation#setKeyInfo(org.apache.xml.security.keys.KeyInfo)
     */
    public void setKeyInfo(KeyInfo keyInfo) {
        this.keyInfo = prepareForAssignment(this.keyInfo, keyInfo);
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {

        List<XMLObject> list = new ArrayList<XMLObject>(confirmationMethods.size() + 1);

        list.addAll(confirmationMethods);
        
        if (subjectConfirmationData != null) {
            list.add(subjectConfirmationData);
        }
        
        // TODO KeyInfo
        
        if (list.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(list);
    }
}