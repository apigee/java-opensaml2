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

package org.opensaml.saml1.core.validator;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObjectValidatorBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.AttributeQuery;
import org.opensaml.saml1.core.AuthorityBinding;

/**
 * Test case for {@link org.opensaml.saml1.core.validator.AuthorityBindingValidator}.
 */
public class AuthorityBindingSchemaTest extends SAMLObjectValidatorBaseTestCase {

    /** Constructor */
    public AuthorityBindingSchemaTest() {
        super();
        targetQName = new QName(SAMLConstants.SAML1_NS, AuthorityBinding.LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
        validator = new AuthorityBindingValidator();

    }

    /*
     * @see org.opensaml.common.SAMLObjectValidatorBaseTestCase#populateRequiredData()
     */
    protected void populateRequiredData() {
        super.populateRequiredData();

        AuthorityBinding authorityBinding = (AuthorityBinding) target;
        // this attribute is a Schema QName type, e.g. AuthorityKind="samlp:AttributeQuery"
        authorityBinding.setAuthorityKind(new QName(SAMLConstants.SAML1P_NS, AttributeQuery.LOCAL_NAME, SAMLConstants.SAML1P_PREFIX));
        authorityBinding.setLocation("location");
        authorityBinding.setBinding("binding");
    }
    
    public void testMissingAuthorityKind() {
        AuthorityBinding authorityBinding = (AuthorityBinding) target;

        authorityBinding.setAuthorityKind(null);
        assertValidationFail("No AuthorityBinding attribute - should fail");
    }

    public void testMissingLocation() {
        AuthorityBinding authorityBinding = (AuthorityBinding) target;

        authorityBinding.setLocation(null);
        assertValidationFail("No Location attribute - should fail");
    }

    public void testMissingBinding() {
        AuthorityBinding authorityBinding = (AuthorityBinding) target;

        authorityBinding.setBinding(null);
        assertValidationFail("No Binding attribute - should fail");
    }
}