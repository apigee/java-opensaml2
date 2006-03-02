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
package org.opensaml.common;

import javax.xml.namespace.QName;

import org.opensaml.xml.XMLObject;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.Validator;

/**
 * Base test case for all OpenSAML tests that test the {@link org.opensaml.xml.validation.Validator}'s
 * that validate SAML objects.
 */
public abstract class SAMLObjectValidatorBaseTestCase extends SAMLObjectTestCaseConfigInitializer {
    
    /** The primary XMLObject which will be the target of a given test run */
    protected XMLObject target;

    /** QName of the object to be tested */
    protected QName targetQName;

    /** Validator for the type corresponding to the test target */
    protected Validator validator;

    /**
    /**
     * Constructor
     *
     */
    public SAMLObjectValidatorBaseTestCase() {
        super();
    }

    /*
     * @see org.opensaml.common.SAMLObjectTestCaseConfigInitializer#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        if (targetQName == null)
            throw new Exception("targetQName was null");
        
        if (validator == null)
            throw new Exception("validator was null");
        
        target = buildXMLObject(targetQName);
        populateRequiredData();
    }

    /*
     * @see org.opensaml.common.SAMLObjectTestCaseConfigInitializer#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    /**
     *  Subclasses should override to populate required elements and attributes
     */
    protected void populateRequiredData() {
        
    }
    
    /**
     *  Tests the expected proper validation case.
     */
    public void testProperValidation() {
       try {
           validator.validate(target);
       } catch (ValidationException e) {
           fail("SAML object was valid, but raised a ValidationException: " + e.getMessage());
       }
   } 

}
