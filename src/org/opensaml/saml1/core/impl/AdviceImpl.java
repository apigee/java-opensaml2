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

import java.util.Iterator;
import java.util.Set;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.util.OrderedSet;
import org.opensaml.common.util.UnmodifiableOrderedSet;
import org.opensaml.saml1.core.Advice;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.AssertionIDReference;

/**
 * Concrete Implementation of the {@link org.opensaml.saml1.core.Advice} Object
 */
public class AdviceImpl extends AbstractSAMLObject implements Advice {

    /**
     * Seralization GUID
     */
    private static final long serialVersionUID = 716943356771050436L;

    /** Contains all the AssertionIDReference objects (in order) */

    private final OrderedSet<AssertionIDReference> assertionIDReferences;

    /** Contains all the Assertion child objects (in order) */

    private final OrderedSet<Assertion> assertions;

    /** Contains all the SAML objects we have added */

    private final OrderedSet<SAMLObject> orderedChildren;

    /**
     * Constructor
     */
    public AdviceImpl() {
        super();
        assertionIDReferences = new OrderedSet<AssertionIDReference>();
        assertions = new OrderedSet<Assertion>();
        orderedChildren = new OrderedSet<SAMLObject>();
        setQName(Advice.QNAME);
    }

    /*
     * @see org.opensaml.saml1.core.Advice#getAssertionIDReferences()
     */
    public UnmodifiableOrderedSet<AssertionIDReference> getAssertionIDReferences() {
        return new UnmodifiableOrderedSet<AssertionIDReference>(assertionIDReferences);
    }

    /*
     * @see org.opensaml.saml1.core.Advice#addAssertionIDReference(org.opensaml.saml1.core.AssertionIDReference)
     */
    public void addAssertionIDReference(AssertionIDReference assertionIDReference) throws IllegalAddException {

        if (addSAMLObject(assertionIDReferences, assertionIDReference)) {
            orderedChildren.add(assertionIDReference);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Advice#removeAssertionIDReference(org.opensaml.saml1.core.AssertionIDReference)
     */
    public void removeAssertionIDReference(AssertionIDReference assertionIDReference) {
        if (removeSAMLObject(assertionIDReferences, assertionIDReference)) {
            orderedChildren.remove(assertionIDReference);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Advice#removeAssertionIDReferences(java.util.Set)
     */
    public void removeAssertionIDReferences(Set<AssertionIDReference> assertionIDReferences) {
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

    public UnmodifiableOrderedSet<Assertion> getAssertions() {
        return new UnmodifiableOrderedSet<Assertion>(assertions);
    }

    /*
     * @see org.opensaml.saml1.core.Advice#addAssertion(org.opensaml.saml1.core.Assertion)
     */
    public void addAssertion(Assertion assertion) throws IllegalAddException {
        if (addSAMLObject(assertions, assertion)) {
            orderedChildren.add(assertion);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Advice#removeAssertion(org.opensaml.saml1.core.Assertion)
     */
    public void removeAssertion(Assertion assertion) {
        if (removeSAMLObject(assertions, assertion)) {
            orderedChildren.remove(assertion);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Advice#removeAssertions(java.util.Set)
     */
    public void removeAssertions(Set<Assertion> assertions) {
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
    public UnmodifiableOrderedSet<SAMLObject> getOrderedChildren() {

        return new UnmodifiableOrderedSet<SAMLObject>(orderedChildren);
    }

    /*
     * @see org.opensaml.common.SAMLObject#equals(org.opensaml.common.SAMLObject)
     */
    public boolean equals(SAMLObject element) {
        
        if (!(element instanceof Advice)) {
            return false;
        }
        
        Advice advice = (Advice) element;
        
        if (assertionIDReferences.size() != advice.getAssertionIDReferences().size()) {
            return false;
        }
        
        if (assertions.size() != advice.getAssertions().size()) {
            return false;
        }
        
        Iterator<SAMLObject> children = advice.getOrderedChildren().iterator();
        
        for (SAMLObject object : orderedChildren) {
            if (!object.equals(children.next())) {
                return false;
            }
        }
        return true;
        // TODO - what about the other namespace elements
    }

}
