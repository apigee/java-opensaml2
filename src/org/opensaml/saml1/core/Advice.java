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

package org.opensaml.saml1.core;

import java.util.Collection;
import java.util.List;

import org.opensaml.common.SAMLObject;

/**
 * This interface defines how the object representing a SAML 1 <code> Advice </code> element behaves.
 */
public interface Advice extends SAMLObject {

    /** Element name, no namespace. */
    public final static String LOCAL_NAME = "Advice";

    /**
     * Get the AssertionIdReferences.
     * 
     * @return The AssertionIdReferences in order
     */
    public List<AssertionIDReference> getAssertionIDReferences();

    /**
     * Add an AssertionIDReference.
     * 
     * @param assertionIDReference what to add
     * 
     * @throws IllegalArgumentException if the element is already added somewhere
     */
    public void addAssertionIDReference(AssertionIDReference assertionIDReference) throws IllegalArgumentException;

    /**
     * Remove a single AssertionIDReference.
     * 
     * @param assertionIDReference what to remove
     */
    public void removeAssertionIDReference(AssertionIDReference assertionIDReference);

    //  TODO Use XMLObjectChildList
    
    /**
     * Remove several AssertionIDReferences.
     * 
     * @param assertionIDReferences what to remove
     */
    public void removeAssertionIDReferences(Collection<AssertionIDReference> assertionIDReferences);

    /** Remove all AssertionIDReferences */
    public void removeAllAssertionIDReferences();

    /**
     * Get the Assertions.
     * 
     * @return the assertions (in order)
     */
    public List<Assertion> getAssertions();

    //  TODO Use XMLObjectChildList

    /**
     * Add an Assertion.
     * 
     * @param assertion what to add
     * 
     * @throws IllegalArgumentException if the element is already added somewhere
     */
    public void addAssertion(Assertion assertion) throws IllegalArgumentException;

    /** Remove a single Assertion */
    public void removeAssertion(Assertion assertion);

    /** Remove several Assertions */
    public void removeAssertions(Collection<Assertion> assertions);

    /** Remove all Assertions */
    public void removeAllAssertions();
}