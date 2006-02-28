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

import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.AssertionIDReference;
import org.opensaml.saml1.core.Evidence;
import org.opensaml.xml.XMLObject;

/**
 * Concrete implementation of the {@link org.opensaml.saml1.core.Evidence} interface
 */
public class EvidenceImpl extends AbstractAssertionSAMLObject implements Evidence {

    /** Contains the AssertionIDReference */
    AssertionIDReference assertionIDReference;

    /** Contains the Assertion */
    Assertion assertion;

    /**
     * Constructor
     */
    protected EvidenceImpl() {
        super(Evidence.LOCAL_NAME);
    }

    /*
     * @see org.opensaml.saml1.core.Evidence#getAssertionIDReference()
     */
    public AssertionIDReference getAssertionIDReference() {
        return assertionIDReference;
    }

    /*
     * @see org.opensaml.saml1.core.Evidence#setAssertionIDReference(org.opensaml.saml1.core.AssertionIDReference)
     */
    public void setAssertionIDReference(AssertionIDReference assertionIDReference) throws IllegalArgumentException {
        this.assertionIDReference = prepareForAssignment(this.assertionIDReference, assertionIDReference);
    }

    /*
     * @see org.opensaml.saml1.core.Evidence#getAssertion()
     */
    public Assertion getAssertion() {
        return assertion;
    }

    /*
     * @see org.opensaml.saml1.core.Evidence#setAssertion(org.opensaml.saml1.core.Assertion)
     */
    public void setAssertion(Assertion assertion) throws IllegalArgumentException {
        this.assertion = prepareForAssignment(this.assertion, assertion);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> list = new ArrayList<XMLObject>(2);

        if (assertionIDReference != null) {
            list.add(assertionIDReference);
        }
        if (assertion != null) {
            list.add(assertion);
        }
        if (list.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(list);
    }
}