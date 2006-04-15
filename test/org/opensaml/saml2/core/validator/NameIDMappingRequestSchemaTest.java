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

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.NameIDMappingRequest;
import org.opensaml.saml2.core.NameIDPolicy;

/**
 *
 */
public class NameIDMappingRequestSchemaTest extends RequestSchemaTest {

    /**
     * Constructor
     *
     */
    public NameIDMappingRequestSchemaTest() {
        super();
        targetQName = new QName(SAMLConstants.SAML20P_NS, NameIDMappingRequest.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20P_PREFIX);
        validator = new NameIDMappingRequestSchemaValidator();
    }

    /*
     * @see org.opensaml.saml2.core.validator.RequestSchemaTest#populateRequiredData()
     */
    protected void populateRequiredData() {
        super.populateRequiredData();
        
        NameIDMappingRequest request = (NameIDMappingRequest) target;
        NameID nameid = (NameID) buildXMLObject(new QName(SAMLConstants.SAML20_NS, NameID.DEFAULT_ELEMENT_LOCAL_NAME));
        NameIDPolicy policy = (NameIDPolicy) buildXMLObject(new QName(SAMLConstants.SAML20P_NS, NameIDPolicy.DEFAULT_ELEMENT_LOCAL_NAME));
        
        request.setNameID(nameid);
        request.setNameIDPolicy(policy);
    }
    
    /**
     *  Test invalid identifiers (NameID, BaseID, EncryptedID).
     */
    public void testIdentifiersFailure() {
        NameIDMappingRequest request = (NameIDMappingRequest) target;
        
        request.setNameID(null);
        assertValidationFail("NameID was null");
    
        // TODO need a BaseID concrete impl to test combinations
    }
    
    
    /**
     *  Test invalid NameIDPolicy
     */
    public void testNameIDPolicyFailure() {
        NameIDMappingRequest request = (NameIDMappingRequest) target;
        
        request.setNameIDPolicy(null);
        assertValidationFail("NameIDPolicy was null");
    }

}
