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

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObjectValidatorBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Artifact;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.Validator;

/**
 *
 */
public class ArtifactSchemaTest extends SAMLObjectValidatorBaseTestCase {
    
    private QName qname;
    private Validator validator;

    /**
     * Constructor
     *
     */
    public ArtifactSchemaTest() {
        qname = new QName(SAMLConstants.SAML20P_NS, Artifact.LOCAL_NAME, SAMLConstants.SAML20P_PREFIX);
        validator = new ArtifactSchemaValidator();
        
    }
    
   /*
     * @see org.opensaml.common.SAMLObjectValidatorBaseTestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }
    

    /**
     *  Tests the correct case.
     */
    public void testProper() {
       Artifact artifact = (Artifact) buildXMLObject(qname);
       
       artifact.setArtifact("artifact");
       try {
           validator.validate(artifact);
       } catch (ValidationException e) {
           fail("Artifact was valid, should NOT raise a ValidationException");
       }
   } 
   
   /**
    * Tests null or empty artifact.
    */
    public void testArtifactFailure() {
       Artifact artifact = (Artifact) buildXMLObject(qname);
       
       artifact.setArtifact(null);
       try {
           validator.validate(artifact);
           fail("Artifact was null, should raise a ValidationException");
       } catch (ValidationException e) {
       }
       
       artifact.setArtifact("");
       try {
           validator.validate(artifact);
           fail("Artifact was empty string, should raise a ValidationException");
       } catch (ValidationException e) {
       }
    
   }


}
