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

package org.opensaml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.saml2.core.BaseID;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.SubjectConfirmation;
import org.opensaml.saml2.core.SubjectConfirmationData;
import org.opensaml.xml.XMLObject;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.SubjectConfirmation}
 */
public class SubjectConfirmationImpl extends AbstractAssertionSAMLObject implements SubjectConfirmation {

    /** BaseID child element */
    private BaseID baseID;

    /** NameID child element */
    private NameID nameID;

    /** SubjectConfirmationData of the Confirmation */
    private SubjectConfirmationData subjectConfirmationData;

    /** Method of the Confirmation */
    private String method;

    /** Constructor */
    protected SubjectConfirmationImpl() {
        super(SubjectConfirmation.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.saml2.core.LogoutRequest#getBaseID()
     */
    public BaseID getBaseID() {
        return baseID;
    }

    /*
     * @see org.opensaml.saml2.core.LogoutRequest#setBaseID(org.opensaml.saml2.core.BaseID)
     */
    public void setBaseID(BaseID newBaseID) {
        baseID = prepareForAssignment(baseID, newBaseID);
    }

    /*
     * @see org.opensaml.saml2.core.LogoutRequest#getNameID()
     */
    public NameID getNameID() {
        return nameID;
    }

    /*
     * @see org.opensaml.saml2.core.LogoutRequest#setNameID(org.opensaml.saml2.core.NameID)
     */
    public void setNameID(NameID newNameID) {
        nameID = prepareForAssignment(nameID, newNameID);
    }

    /*
     * @see org.opensaml.saml2.core.SubjectConfirmation#getSubjectConfirmationData()
     */
    public SubjectConfirmationData getSubjectConfirmationData() {
        return subjectConfirmationData;
    }

    /*
     * @see org.opensaml.saml2.core.SubjectConfirmation#setSubjectConfirmationData(org.opensaml.saml1.core.SubjectConfirmationData)
     */
    public void setSubjectConfirmationData(SubjectConfirmationData newSubjectConfirmationData) {
        this.subjectConfirmationData = prepareForAssignment(this.subjectConfirmationData, newSubjectConfirmationData);

    }

    /*
     * @see org.opensaml.saml2.core.SubjectConfirmation#getMethod()
     */
    public String getMethod() {
        return method;
    }

    /*
     * @see org.opensaml.saml2.core.SubjectConfirmation#setMethod(java.lang.String)
     */
    public void setMethod(String newMethod) {
        this.method = prepareForAssignment(this.method, newMethod);
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

        if (baseID != null) {
            children.add(baseID);
        }

        if (nameID != null) {
            children.add(nameID);
        }
        
        children.add(subjectConfirmationData);

        return Collections.unmodifiableList(children);
    }
}