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

import org.opensaml.common.SAMLObject;

/**
 * Interface describing how a SAML1.1 <code> Evidence </code> element behaves
 */
public interface Evidence extends SAMLObject {

    /** Element name, no namespace. */
    public final static String LOCAL_NAME = "Evidence";

    /** Get the AssertionIdReference */
    public AssertionIDReference getAssertionIDReference();
    
    /** Set the AssertionIdReference 
     * @throws IllegalArgumentException */
    public void setAssertionIDReference(AssertionIDReference assertionIDReference) throws IllegalArgumentException;
    
    /** Get the Assertion */
    public Assertion getAssertion();
    
    /** Set the Assertion 
     * @throws IllegalArgumentException */
    public void setAssertion(Assertion assertion) throws IllegalArgumentException;
    
    
}
