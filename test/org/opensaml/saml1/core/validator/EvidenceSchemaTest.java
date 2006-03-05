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
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.AssertionIDReference;
import org.opensaml.saml1.core.Evidence;

/**
 * Test case for {@link org.opensaml.saml1.core.validator.ActionSchemaValidator}.
 */
public class EvidenceSchemaTest extends SAMLObjectValidatorBaseTestCase {

    /** Constructor */
    public EvidenceSchemaTest() {
        super();
        targetQName = new QName(SAMLConstants.SAML1_NS, Evidence.LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
        validator = new EvidenceSchemaValidator();
    }

    /*
     * @see org.opensaml.common.SAMLObjectValidatorBaseTestCase#populateRequiredData()
     */
    protected void populateRequiredData() {
        super.populateRequiredData();
        
        Evidence evidence = (Evidence) target;
        QName qname = new QName(SAMLConstants.SAML1_NS, Assertion.LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
        evidence.setAssertion((Assertion)buildXMLObject(qname));
    }
    
    public void testNeitherPresent() {
        Evidence evidence = (Evidence) target;
        evidence.setAssertion(null);
        assertValidationFail("No Assertion, No AssertionIDReference, should raise a Validation Exception");
    }

    public void testBothPresent() {
        Evidence evidence = (Evidence) target;
        QName qname = new QName(SAMLConstants.SAML1_NS, AssertionIDReference.LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
        evidence.setAssertionIDReference((AssertionIDReference)buildXMLObject(qname));
        assertValidationFail("Both Assertion and AssertionIDReference present, should raise a Validation Exception");
    }
}