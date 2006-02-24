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
import org.opensaml.saml2.core.NameIDMappingRequest;

/**
 *
 */
public class NameIDMappingRequestTest extends RequestTest {

    /**
     * Constructor
     *
     */
    public NameIDMappingRequestTest() {
        super();
        singleElementFile = "/data/org/opensaml/saml2/core/impl/NameIDMappingRequest.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/core/impl/NameIDMappingRequestOptionalAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml2/core/impl/NameIDMappingRequestChildElements.xml";
    }
    
    
    /**
     * @see org.opensaml.saml2.core.impl.RequestTest#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }


    /**
     * @see org.opensaml.saml2.core.impl.RequestTest#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, NameIDMappingRequest.LOCAL_NAME);
        NameIDMappingRequest req = (NameIDMappingRequest) buildSAMLObject(qname);
        
        super.populateRequiredAttributes(req);
        
        assertEquals(expectedDOM, req);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, NameIDMappingRequest.LOCAL_NAME);
        NameIDMappingRequest req = (NameIDMappingRequest) buildSAMLObject(qname);
        
        super.populateRequiredAttributes(req);
        super.populateOptionalAttributes(req);
        
        assertEquals(expectedOptionalAttributesDOM, req);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, NameIDMappingRequest.LOCAL_NAME);
        NameIDMappingRequest req = (NameIDMappingRequest) buildSAMLObject(qname);
        
        super.populateChildElements(req);
        req.setIdentifier(new NameIDImpl());
        req.setNameIDPolicy(new NameIDPolicyImpl());
        
        assertEquals(expectedChildElementsDOM, req);
    }

    /**
     * @see org.opensaml.saml2.core.impl.RequestTest#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        NameIDMappingRequest req = (NameIDMappingRequest) unmarshallElement(singleElementFile);
        
        assertNotNull("NameIDMappingRequest was null", req);
        super.helperTestSingleElementUnmarshall(req);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        NameIDMappingRequest req = (NameIDMappingRequest) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertNotNull("NameIDMappingRequest was null", req);
        super.helperTestSingleElementOptionalAttributesUnmarshall(req);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    public void testChildElementsUnmarshall() {
        NameIDMappingRequest req = (NameIDMappingRequest) unmarshallElement(childElementsFile);
        
        assertNotNull("Identifier was null", req.getIdentifier());
        assertNotNull("NameIDPolicy was null", req.getNameIDPolicy());
        super.helperTestChildElementsUnmarshall(req);
    }

}