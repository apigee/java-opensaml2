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
package org.opensaml.saml2.core.validator;

import org.joda.time.DateTime;
import org.opensaml.common.SAMLObjectValidatorBaseTestCase;
import org.opensaml.saml2.core.Request;


/**
 *
 */
public abstract class RequestSchemaTest extends SAMLObjectValidatorBaseTestCase {
    
    /**
     * Constructor
     *
     */
    public RequestSchemaTest() {
        super();
    }

    /*
     * @see org.opensaml.common.SAMLObjectValidatorBaseTestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    /**
     *  Populate the XMLObject argument with valid values for required elements and attributes.
     *  Useful to subclasses of this test.
     */
    protected void populateRequiredData() {
        Request request = (Request) target;
        request.setID("abc123");
        request.setIssueInstant(new DateTime());
        // note: Version attrib is set automatically by the implementation
    }
    
    /**
     *  Tests empty ID attribute
     */
    public void testIDFailure() {
        Request request = (Request) target;
        request.setID("");
        assertValidationFail("ID attribute was empty string");
        
        request.setID(null);
        assertValidationFail("ID attribute was null, should raise ValidationException");
    }
    
    // TODO don't know that we can really test this since can't change the SAMLVersion
    /**
     *  Tests null or invalid Version attribute
     */
    /*
    public void testVersionFailure() {
        testTarget = (Request) buildXMLObject(qname);
        populateRequired(testTarget);
        
    }
    */
    
    /**
     *  Tests invalid IssueInstant attribute
     */
    public void testIssueInstantFailure() {
        Request request = (Request) target;
        request.setIssueInstant(null);
        assertValidationFail("IssueInstant attribute was null");
    }

}
