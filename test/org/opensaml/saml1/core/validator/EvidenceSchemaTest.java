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

import org.opensaml.common.SAMLObject;
import org.opensaml.common.SAMLObjectValidatorBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.AssertionIDReference;
import org.opensaml.saml1.core.DoNotCacheCondition;
import org.opensaml.saml1.core.Evidence;

/**
 * Test case for {@link org.opensaml.saml1.core.validator.EvidenceSchemaValidator}.
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
        QName assertionQname = new QName(SAMLConstants.SAML1_NS, Assertion.LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
        QName assertionIDRefQname = new QName(SAMLConstants.SAML1_NS, AssertionIDReference.LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
        // These aren't technically required, but makes the test more interesting.
        evidence.getAssertions().add((Assertion)buildXMLObject(assertionQname));
        evidence.getAssertionIDReferences().add((AssertionIDReference)buildXMLObject(assertionIDRefQname));
    }
    
    public void testInvalidChild() {
        Evidence evidence = (Evidence) target;
        
        // Just a random invalid child object type
        QName qname = new QName(SAMLConstants.SAML1_NS, DoNotCacheCondition.LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
        SAMLObject invalidChild = (SAMLObject) buildXMLObject(qname);
        
        // Case: if getEvidence returns a modifiable list
        //evidence.getEvidence().add(invalidChild);
        //assertValidationFail("Evidence had an invalid child object type");
        
        // Case: if getEvidence() returns an unmodifiable list
        try {
            evidence.getEvidence().add(invalidChild);
            fail("Evidence had an invalid child object type" + " : Modification success, expected failure to raise UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
        }
    }
}