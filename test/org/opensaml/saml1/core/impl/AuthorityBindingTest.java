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

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.saml1.core.AuthorityBinding;

/**
 *  Test for {@link org.opensaml.saml1.core.impl.AuthorityBinding}
 */
public class AuthorityBindingTest extends SAMLObjectBaseTestCase {

    /** Value of AuthorityKind in test file */
    private final String expectedAuthorityKind;

    /** Value of Location in test file */
    private final String expectedLocation;

    /** Value of Binding in test file */
    private final String expectedBinding;

    /**
     * Constructor
     */
    public AuthorityBindingTest() {
        super();
        expectedAuthorityKind = "none";
        expectedLocation = "here";
        expectedBinding = "binding";
        singleElementFile = "/data/org/opensaml/saml1/singleAuthorityBinding.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/singleAuthorityBindingAttributes.xml";
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    @Override
    public void testSingleElementUnmarshall() {
        AuthorityBinding authorityBinding = (AuthorityBinding) unmarshallElement(singleElementFile);
        assertNull("AuthorityKind attribute present", authorityBinding.getAuthorityKind());
        assertNull("Binding attribute present", authorityBinding.getBinding());
        assertNull("Location attribute present", authorityBinding.getLocation());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesUnmarshall() {
        AuthorityBinding authorityBinding = (AuthorityBinding) unmarshallElement(singleElementOptionalAttributesFile);
        assertEquals("AuthorityKind attribute", expectedAuthorityKind, authorityBinding.getAuthorityKind());
        assertEquals("Binding attribute", expectedBinding, authorityBinding.getBinding());
        assertEquals("Location attribute", expectedLocation, authorityBinding.getLocation());        
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    @Override
    public void testSingleElementMarshall() {
        assertEquals(expectedDOM, new AuthorityBindingImpl());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesMarshall() {
        AuthorityBinding authorityBinding = new AuthorityBindingImpl();
        authorityBinding.setAuthorityKind(expectedAuthorityKind);
        authorityBinding.setBinding(expectedBinding);
        authorityBinding.setLocation(expectedLocation);
        assertEquals(expectedOptionalAttributesDOM, authorityBinding);
    }

}
