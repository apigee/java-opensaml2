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
import org.opensaml.saml2.core.ManageNameIDRequest;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.NewID;
import org.opensaml.saml2.core.Terminate;

/**
 *
 */
public class ManageNameIDRequestSchemaTest extends RequestSchemaTest {
    
    private NameID nameid;
    private NewID newid;
    private Terminate terminate;

    /**
     * Constructor
     *
     */
    public ManageNameIDRequestSchemaTest() {
        super();
        targetQName = new QName(SAMLConstants.SAML20P_NS, ManageNameIDRequest.LOCAL_NAME, SAMLConstants.SAML20P_PREFIX);
        validator = new ManageNameIDRequestSchemaValidator();
    }

    /*
     * @see org.opensaml.saml2.core.validator.RequestSchemaTest#populateRequiredData()
     */
    protected void populateRequiredData() {
        super.populateRequiredData();
        ManageNameIDRequest request = (ManageNameIDRequest) target;
        
        nameid  = (NameID) buildXMLObject(new QName(SAMLConstants.SAML20_NS, NameID.LOCAL_NAME));
        newid = (NewID) buildXMLObject(new QName(SAMLConstants.SAML20P_NS, NewID.LOCAL_NAME));
        terminate = (Terminate) buildXMLObject(new QName(SAMLConstants.SAML20P_NS, Terminate.LOCAL_NAME));
        
        request.setNameID(nameid);
        request.setNewID(newid);
    }
    
    /**
     *  Tests invalid NameID child element.
     */
    public void testNameIDFailure() {
        ManageNameIDRequest request = (ManageNameIDRequest) target;
       
        request.setNameID(null);
        assertValidationFail("NameID was null");
    } 
   
    /**
     *  Tests NewID and Terminate combination failures
     */
    public void testNewIDandTerminateFailure() {
        ManageNameIDRequest request = (ManageNameIDRequest) target;
        
        request.setTerminate(terminate);
        assertValidationFail("Both NewID and Terminate were present");
        
        request.setNewID(null);
        request.setTerminate(null);
        assertValidationFail("Both NewID and Terminate were null");
    }
    

}
