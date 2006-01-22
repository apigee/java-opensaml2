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
package org.opensaml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.xml.security.keys.KeyInfo;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.ConfirmationMethod;
import org.opensaml.saml1.core.SubjectConfirmation;
import org.opensaml.saml1.core.SubjectConfirmationData;

/**
 * Concrete implementation of a <code> SubjectConfirmation </code> object 
 */
public class SubjectConfirmationImpl extends AbstractSAMLObject implements SubjectConfirmation {

    /** Contains the list of ConfirmationMethods */
    private final List<ConfirmationMethod> confirmationMethods;

    /** Contains the SubjectConfirmationData element */
    private SubjectConfirmationData subjectConfirmationData;
    
    /** Contains the KeyInfo element */
    KeyInfo keyInfo;
    
    /**
     * Constructor
     */
    public SubjectConfirmationImpl() {
        super(SAMLConstants.SAML1_NS, SubjectConfirmation.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML1_PREFIX);
        confirmationMethods = new ArrayList<ConfirmationMethod>();
    }

    /*
     * @see org.opensaml.saml1.core.SubjectConfirmation#addConfirmationMethod(org.opensaml.saml1.core.ConfirmationMethod)
     */
    public void addConfirmationMethod(ConfirmationMethod confirmationMethod) throws IllegalArgumentException {
        addXMLObject(confirmationMethods, confirmationMethod);
    }

    /*
     * @see org.opensaml.saml1.core.SubjectConfirmation#getSubjectConfirmationMethods()
     */
    public List<ConfirmationMethod> getConfirmationMethods() {
        if (confirmationMethods.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(confirmationMethods);
    }

    /*
     * @see org.opensaml.saml1.core.SubjectConfirmation#removeConfirmationMethod(org.opensaml.saml1.core.ConfirmationMethod)
     */
    public void removeConfirmationMethod(ConfirmationMethod confirmationMethod) {
        removeXMLObject(confirmationMethods, confirmationMethod);
    }

    /*
     * @see org.opensaml.saml1.core.SubjectConfirmation#removeConfirmationMethods(java.util.List)
     */
    public void removeConfirmationMethods(Collection<ConfirmationMethod> confirmationMethods) {

        if (confirmationMethods == null) {
            return;
        }

        for (ConfirmationMethod confirmationMethod : confirmationMethods) {
            removeConfirmationMethod(confirmationMethod);
        }
    }

    /*
     * @see org.opensaml.saml1.core.SubjectConfirmation#removeAllConfirmationMethods()
     */
    public void removeAllConfirmationMethods() {
        for (ConfirmationMethod confirmationMethod : confirmationMethods) {
            removeConfirmationMethod(confirmationMethod);
        }
    }

    /*
     * @see org.opensaml.saml1.core.SubjectConfirmation#setSubjectConfirmationData(org.opensaml.saml1.core.SubjectConfirmationData)
     */
    public void setSubjectConfirmationData(SubjectConfirmationData subjectConfirmationData) throws IllegalArgumentException {

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
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        
        List<SAMLObject> list = new ArrayList<SAMLObject>(confirmationMethods.size()+1);
        
        list.addAll(confirmationMethods);
        if (subjectConfirmationData != null) {
            list.add(subjectConfirmationData);
        }
        if (list.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(list);
    }

}
