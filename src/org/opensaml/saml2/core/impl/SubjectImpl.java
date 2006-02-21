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

package org.opensaml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Identifier;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.SubjectConfirmation;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.Subject}
 */
public class SubjectImpl extends AbstractSAMLObject implements Subject {

    /** Identifier of the Subject */
    private Identifier identifier;

    /** Subject Confirmations of the Subject */
    private XMLObjectChildrenList<SubjectConfirmation> subjectConfirmations;

    /** Constructor */
    public SubjectImpl() {
        super(SAMLConstants.SAML20_NS, Subject.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20_PREFIX);
        
        subjectConfirmations = new XMLObjectChildrenList<SubjectConfirmation>(this);
    }
    
    /*
     * @see org.opensaml.saml2.core.Subject#getIdentifier()
     */
    public Identifier getIdentifier() {
        return identifier;
    }

    /*
     * @see org.opensaml.saml2.core.Subject#setIdentifier(org.opensaml.saml2.core.Identifier)
     */
    public void setIdentifier(Identifier newIdentifier) {
        this.identifier = prepareForAssignment(this.identifier, newIdentifier);
    }

    /*
     * @see org.opensaml.saml2.core.Subject#getSubjectConfirmation()
     */
    public List<SubjectConfirmation> getSubjectConfirmations() {
        return subjectConfirmations;
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();

        children.add(identifier);
        children.addAll(subjectConfirmations);
        
        return Collections.unmodifiableList(children);
    }
}