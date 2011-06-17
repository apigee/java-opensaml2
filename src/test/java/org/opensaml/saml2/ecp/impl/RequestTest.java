/*
 * Licensed to the University Corporation for Advanced Internet Development, 
 * Inc. (UCAID) under one or more contributor license agreements.  See the 
 * NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The UCAID licenses this file to You under the Apache 
 * License, Version 2.0 (the "License"); you may not use this file except in 
 * compliance with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.saml2.ecp.impl;

import org.opensaml.common.BaseSAMLObjectProviderTestCase;
import org.opensaml.saml2.core.IDPList;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.ecp.Request;

/**
 * Test case for creating, marshalling, and unmarshalling {@link Request}.
 */
public class RequestTest extends BaseSAMLObjectProviderTestCase {
    
    private String expectedProviderName;
    
    private Boolean expectedPassive;
    
    private String expectedSOAP11Actor;
    
    private Boolean expectedSOAP11MustUnderstand;
    
    public RequestTest() {
        singleElementFile = "/data/org/opensaml/saml2/ecp/impl/Request.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/ecp/impl/RequestOptionalAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml2/ecp/impl/RequestChildElements.xml";
    }
 
    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        
        expectedProviderName = "https://provider.example.org";
        expectedSOAP11Actor = "https://soap11actor.example.org";
        expectedSOAP11MustUnderstand = true;
        expectedPassive = true;
    }



    /** {@inheritDoc} */
    public void testSingleElementUnmarshall() {
        Request request = (Request) unmarshallElement(singleElementFile);
        
        assertNotNull(request);
        
        assertEquals("SOAP mustUnderstand had unxpected value", expectedSOAP11MustUnderstand, request.isSOAP11MustUnderstand());
        assertEquals("SOAP actor had unxpected value", expectedSOAP11Actor, request.getSOAP11Actor());
    }
 
    /** {@inheritDoc} */
    public void testSingleElementOptionalAttributesUnmarshall() {
        Request request = (Request) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertNotNull(request);
        
        assertEquals("SOAP mustUnderstand had unxpected value", expectedSOAP11MustUnderstand, request.isSOAP11MustUnderstand());
        assertEquals("SOAP actor had unxpected value", expectedSOAP11Actor, request.getSOAP11Actor());
        
        assertEquals("IsPassive had unexpected value", expectedPassive, request.isPassive());
        assertEquals("ProviderName had unexpected value", expectedProviderName, request.getProviderName());
    }
   
    /** {@inheritDoc} */
    public void testChildElementsUnmarshall() {
        Request request = (Request) unmarshallElement(childElementsFile);
        
        assertNotNull(request);
        
        assertEquals("SOAP mustUnderstand had unxpected value", expectedSOAP11MustUnderstand, request.isSOAP11MustUnderstand());
        assertEquals("SOAP actor had unxpected value", expectedSOAP11Actor, request.getSOAP11Actor());
        
        assertNotNull("Issuer was null", request.getIssuer());
        assertNotNull("IDPList was null", request.getIDPList());
    }
    
    /** {@inheritDoc} */
    public void testSingleElementMarshall() {
        Request request = (Request) buildXMLObject(Request.DEFAULT_ELEMENT_NAME);
        
        request.setSOAP11Actor(expectedSOAP11Actor);
        request.setSOAP11MustUnderstand(expectedSOAP11MustUnderstand);
        
        assertEquals(expectedDOM, request);
    }

    /** {@inheritDoc} */
    public void testSingleElementOptionalAttributesMarshall() {
        Request request = (Request) buildXMLObject(Request.DEFAULT_ELEMENT_NAME);
        
        request.setSOAP11Actor(expectedSOAP11Actor);
        request.setSOAP11MustUnderstand(expectedSOAP11MustUnderstand);
        request.setProviderName(expectedProviderName);
        request.setPassive(expectedPassive);
        
        assertEquals(expectedOptionalAttributesDOM, request);
    }

    /** {@inheritDoc} */
    public void testChildElementsMarshall() {
        Request request = (Request) buildXMLObject(Request.DEFAULT_ELEMENT_NAME);
        
        request.setSOAP11Actor(expectedSOAP11Actor);
        request.setSOAP11MustUnderstand(expectedSOAP11MustUnderstand);
        
        request.setIssuer((Issuer) buildXMLObject(Issuer.DEFAULT_ELEMENT_NAME));
        request.setIDPList((IDPList) buildXMLObject(IDPList.DEFAULT_ELEMENT_NAME));
        
        assertEquals(expectedChildElementsDOM, request);
    }

}
