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
import org.opensaml.saml2.ecp.RequestAuthenticated;

/**
 * Test case for creating, marshalling, and unmarshalling {@link RequestAuthenticated}.
 */
public class RequestAuthenticatedTest extends BaseSAMLObjectProviderTestCase {
    
    private String expectedSOAP11Actor;
    
    private Boolean expectedSOAP11MustUnderstand;
    
    public RequestAuthenticatedTest() {
        singleElementFile = "/data/org/opensaml/saml2/ecp/impl/RequestAuthenticated.xml";
    }
 
    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        
        expectedSOAP11Actor = "https://soap11actor.example.org";
        expectedSOAP11MustUnderstand = true;
    }



    /** {@inheritDoc} */
    public void testSingleElementUnmarshall() {
        RequestAuthenticated ra = (RequestAuthenticated) unmarshallElement(singleElementFile);
        
        assertNotNull(ra);
        
        assertEquals("SOAP mustUnderstand had unxpected value", expectedSOAP11MustUnderstand, ra.isSOAP11MustUnderstand());
        assertEquals("SOAP actor had unxpected value", expectedSOAP11Actor, ra.getSOAP11Actor());
    }

    /** {@inheritDoc} */
    public void testSingleElementMarshall() {
        RequestAuthenticated ra = (RequestAuthenticated) buildXMLObject(RequestAuthenticated.DEFAULT_ELEMENT_NAME);
        
        ra.setSOAP11Actor(expectedSOAP11Actor);
        ra.setSOAP11MustUnderstand(expectedSOAP11MustUnderstand);
        
        assertEquals(expectedDOM, ra);
    }

}