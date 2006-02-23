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
package org.opensaml.saml2.core.impl;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.RequesterID;

/**
 * Test case for creating, marshalling, and unmarshalling
 * {@link org.opensaml.saml2.core.impl.RequesterIDImpl}.
 */
public class RequesterIDTest extends SAMLObjectBaseTestCase {
    
    /** Expected element content*/
    private String expectedRequesterID;

    /**
     * Constructor
     */
    public RequesterIDTest() {
        super();
        
       singleElementFile = "/data/org/opensaml/saml2/core/impl/RequesterID.xml";
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        expectedRequesterID = "urn:string:requester";
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        RequesterID reqID = (RequesterID) unmarshallElement(singleElementFile);
        
       assertEquals("Unmarshalled requester ID was not the expected value", expectedRequesterID, reqID.getRequesterID()); 
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, RequesterID.LOCAL_NAME);
        RequesterID reqID = (RequesterID) buildSAMLObject(qname);

        reqID.setRequesterID(expectedRequesterID);
        
        assertEquals(expectedDOM, reqID);
    }
}