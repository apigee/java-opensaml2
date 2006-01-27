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
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Advice;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.AssertionIDReference;

/**
 * Concrete Implementation of the {@link org.opensaml.saml1.core.Advice} Object
 */
public class AdviceImpl extends AbstractSAMLObject implements Advice {

    // TODO use XMLObjectChildList
    /** Contains all the AssertionIDReference objects (in order) */
    private final ArrayList<AssertionIDReference> assertionIDReferences = new ArrayList<AssertionIDReference>();

    // TODO use XMLObjectChildList
    /** Contains all the Assertion child objects (in order) */
    private final ArrayList<Assertion> assertions = new ArrayList<Assertion>();

    /** Contains all the SAML objects we have added */
    private final ArrayList<SAMLObject> orderedChildren = new ArrayList<SAMLObject>();

    /**
     * Constructor
     */
    public AdviceImpl() {
        super(SAMLConstants.SAML1_NS, Advice.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML1_PREFIX);
    }

    /*
     * @see org.opensaml.saml1.core.Advice#getAssertionIDReferences()
     */
    public List<AssertionIDReference> getAssertionIDReferences() {
        if (assertionIDReferences.size() == 0) {
            return null;
        } 
        return Collections.unmodifiableList(assertionIDReferences);
    }

    /*
     * @see org.opensaml.saml1.core.Advice#addAssertionIDReference(org.opensaml.saml1.core.AssertionIDReference)
     */
    public void addAssertionIDReference(AssertionIDReference assertionIDReference) throws IllegalArgumentException {

        if (addXMLObject(assertionIDReferences, assertionIDReference)) {
            orderedChildren.add(assertionIDReference);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Advice#removeAssertionIDReference(org.opensaml.saml1.core.AssertionIDReference)
     */
    public void removeAssertionIDReference(AssertionIDReference assertionIDReference) {
        if (removeXMLObject(assertionIDReferences, assertionIDReference)) {
            orderedChildren.remove(assertionIDReference);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Advice#removeAssertionIDReferences(java.util.Set)
     */
    public void removeAssertionIDReferences(Collection<AssertionIDReference> assertionIDReferences) {
        
        if (assertionIDReferences == null) {
            return;
        }
        
        for (AssertionIDReference assertionIDReference : assertionIDReferences) {
            removeAssertionIDReference(assertionIDReference);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Advice#removeAllAssertionIDReferences()
     */
    public void removeAllAssertionIDReferences() {
        for (AssertionIDReference assertionIDReference : assertionIDReferences) {
            removeAssertionIDReference(assertionIDReference);
        }
    }

    public List<Assertion> getAssertions() {
        if (assertions.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(assertions);
    }

    /*
     * @see org.opensaml.saml1.core.Advice#addAssertion(org.opensaml.saml1.core.Assertion)
     */
    public void addAssertion(Assertion assertion) throws IllegalArgumentException {
        if (addXMLObject(assertions, assertion)) {
            orderedChildren.add(assertion);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Advice#removeAssertion(org.opensaml.saml1.core.Assertion)
     */
    public void removeAssertion(Assertion assertion) {
        if (removeXMLObject(assertions, assertion)) {
            orderedChildren.remove(assertion);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Advice#removeAssertions(java.util.Set)
     */
    public void removeAssertions(Collection<Assertion> assertions) {
        
        if (assertions == null) {
            return;
        }
        
        for (Assertion assertion : assertions) {
            removeAssertion(assertion);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Advice#removeAllAssertions()
     */
    public void removeAllAssertions() {
        for (Assertion assertion : assertions) {
            removeAssertion(assertion);
        }
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        if (orderedChildren.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(orderedChildren);
    }
}