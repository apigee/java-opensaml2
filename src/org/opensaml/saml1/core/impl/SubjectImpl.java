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
import java.util.Collections;
import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.NameIdentifier;
import org.opensaml.saml1.core.Subject;
import org.opensaml.saml1.core.SubjectConfirmation;

/**
 * Complete implementation of {@link org.opensaml.saml1.core.Subject}
 */
public class SubjectImpl extends AbstractSAMLObject implements Subject {

    /** Contains the NameIdentifier inside the Subject */
    private NameIdentifier nameIdentifier;

    /** Contains the SubjectConfirmation inside the Subject */
    private SubjectConfirmation subjectConfirmation;
    
    /**
     * Constructor
     */
    public SubjectImpl() {
        super(SAMLConstants.SAML1_NS, Subject.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML1_PREFIX);
    }

    /*
     * @see org.opensaml.saml1.core.Subject#getNameIdentifier()
     */
    public NameIdentifier getNameIdentifier() {
        return nameIdentifier;
    }

    /*
     * @see org.opensaml.saml1.core.Subject#setNameIdentifier(org.opensaml.saml1.core.NameIdentifier)
     */
    public void setNameIdentifier(NameIdentifier nameIdentifier) throws IllegalArgumentException {
        this.nameIdentifier = prepareForAssignment(this.nameIdentifier, nameIdentifier);
    }

    /*
     * @see org.opensaml.saml1.core.Subject#getSubjectConfirmation()
     */
    public SubjectConfirmation getSubjectConfirmation() {
        return subjectConfirmation;
    }

    /*
     * @see org.opensaml.saml1.core.Subject#setSubjectConfirmation(org.opensaml.saml1.core.SubjectConfirmation)
     */
    public void setSubjectConfirmation(SubjectConfirmation subjectConfirmation) throws IllegalArgumentException {
        this.subjectConfirmation = prepareForAssignment(this.subjectConfirmation, subjectConfirmation);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
       
        List<SAMLObject> list = new ArrayList<SAMLObject>(2);
        
        if (nameIdentifier != null) {
            list.add(nameIdentifier);
        }
        
        if (subjectConfirmation != null) {
            list.add(subjectConfirmation);
        }
        if (list.size() == 0) {
            return null;
        }
        
        return Collections.unmodifiableList(list);
    }

}
