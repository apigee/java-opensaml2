/*
 * Licensed to the University Corporation for Advanced Internet Development, 
 * Inc. (UCAID) under one or more contributor license agreements.  See the 
 * NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The UCAID licenses this file to You under the Apache 
 * License, Version 2.0 (the "License"); you may not use this file except in 
 * compliance with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.saml2.metadata.validator;

import javax.xml.namespace.QName;

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.xml.validation.ValidationException;

/**
 * Test case for {@link org.opensaml.saml2.metadata.SPSSODescriptor}.
 */
public class SPSSODescriptorSchemaTest extends SSODescriptorSchemaTestBase {

    /** Constructor */
    public SPSSODescriptorSchemaTest() {
        targetQName = new QName(SAMLConstants.SAML20MD_NS, SPSSODescriptor.DEFAULT_ELEMENT_LOCAL_NAME,
                SAMLConstants.SAML20MD_PREFIX);
        validator = new SPSSODescriptorSchemaValidator();
    }

    /** {@inheritDoc} */
    protected void populateRequiredData() {
        super.populateRequiredData();
        SPSSODescriptor spssoDescriptor = (SPSSODescriptor) target;
        AssertionConsumerService assertionConsumerService = (AssertionConsumerService) buildXMLObject(new QName(SAMLConstants.SAML20MD_NS,
                AssertionConsumerService.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX));
        spssoDescriptor.getAssertionConsumerServices().add(assertionConsumerService);
    }

    /**
     * Tests for AssertionConsumerService failure.
     * 
     * @throws ValidationException
     */
    public void testAssertionConsumerServiceFailure() throws ValidationException {
        SPSSODescriptor spssoDescriptor = (SPSSODescriptor) target;

        spssoDescriptor.getAssertionConsumerServices().clear();
        assertValidationFail("AssertionConsumerService list was empty, should raise a Validation Exception.");
    }
}