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

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Response;

/**
 *
 */
public class ResponseTest extends StatusResponseTest {
    
    // TODO may need more depending on the EncryptedAssertion implementation
    
    /** Expected number of Assertion child elements */
    private int expectedNumAssertions;

    /**
     * Constructor
     *
     */
    public ResponseTest() {
        super();
        singleElementFile = "/data/org/opensaml/saml2/core/impl/Response.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/core/impl/ResponseOptionalAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml2/core/impl/ResponseChildElements.xml";
    }

    /**
     * @see org.opensaml.saml2.core.impl.StatusResponseTest#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        expectedNumAssertions = 3;
    }

    /**
     * @see org.opensaml.saml2.core.impl.StatusResponseTest#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, Response.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20P_PREFIX);
        Response resp = (Response) buildXMLObject(qname);
        
        super.populateRequiredAttributes(resp);
        
        assertEquals(expectedDOM, resp);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, Response.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20P_PREFIX);
        Response resp = (Response) buildXMLObject(qname);
        
        super.populateRequiredAttributes(resp);
        super.populateOptionalAttributes(resp);
        
        assertEquals(expectedOptionalAttributesDOM, resp);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, Response.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20P_PREFIX);
        Response resp = (Response) buildXMLObject(qname);
        
        super.populateChildElements(resp);
        
        QName assetionQname = new QName(SAMLConstants.SAML20_NS, Assertion.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        for (int i=0; i<expectedNumAssertions; i++){
            resp.getAssertions().add((Assertion) buildXMLObject(assetionQname));
        }
        
        assertEquals(expectedChildElementsDOM, resp);
    }

    /**
     * @see org.opensaml.saml2.core.impl.StatusResponseTest#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        Response resp = (Response) unmarshallElement(singleElementFile);
        
        assertNotNull("Response was null", resp);
        super.helperTestSingleElementUnmarshall(resp);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        Response resp = (Response) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertNotNull("Response was null", resp);
        super.helperTestSingleElementOptionalAttributesUnmarshall(resp);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    public void testChildElementsUnmarshall() {
        Response resp = (Response) unmarshallElement(childElementsFile);
        
        assertEquals("Assertion count", expectedNumAssertions, resp.getAssertions().size());
        super.helperTestChildElementsUnmarshall(resp);
    }

}
