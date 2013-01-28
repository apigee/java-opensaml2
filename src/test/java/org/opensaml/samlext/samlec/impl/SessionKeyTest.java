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

package org.opensaml.samlext.samlec.impl;

import org.opensaml.common.BaseSAMLObjectProviderTestCase;
import org.opensaml.samlext.samlec.GeneratedKey;
import org.opensaml.samlext.samlec.SessionKey;

/**
 * Test case for creating, marshalling, and unmarshalling {@link SessionKey}.
 */
public class SessionKeyTest extends BaseSAMLObjectProviderTestCase {
    
    private String expectedEncType;
    
    private String expectedSOAP11Actor;
    
    private Boolean expectedSOAP11MustUnderstand;
    
    public SessionKeyTest() {
        singleElementFile = "/data/org/opensaml/samlext/samlec/impl/SessionKey.xml";
        childElementsFile = "/data/org/opensaml/samlext/samlec/impl/SessionKeyChildElements.xml";
    }
 
    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        
        expectedEncType = "des-cbc-crc";
        expectedSOAP11Actor = "https://soap11actor.example.org";
        expectedSOAP11MustUnderstand = true;
    }

    /** {@inheritDoc} */
    public void testSingleElementUnmarshall() {
        SessionKey key = (SessionKey) unmarshallElement(singleElementFile);
        
        assertNotNull(key);
        
        assertEquals("SOAP mustUnderstand had unxpected value", expectedSOAP11MustUnderstand, key.isSOAP11MustUnderstand());
        assertEquals("SOAP actor had unxpected value", expectedSOAP11Actor, key.getSOAP11Actor());
        assertEquals("EncType had unexpected value", expectedEncType, key.getEncType());
    }
 
    /** {@inheritDoc} */
    public void testChildElementsUnmarshall() {
        SessionKey key = (SessionKey) unmarshallElement(childElementsFile);
        
        assertNotNull(key);
        
        assertEquals("SOAP mustUnderstand had unxpected value", expectedSOAP11MustUnderstand, key.isSOAP11MustUnderstand());
        assertEquals("SOAP actor had unxpected value", expectedSOAP11Actor, key.getSOAP11Actor());
        assertEquals("EncType had unexpected value", expectedEncType, key.getEncType());
        
        assertNotNull("GeneratedKey was null", key.getGeneratedKey());
    }
    
    /** {@inheritDoc} */
    public void testSingleElementMarshall() {
        SessionKey key = (SessionKey) buildXMLObject(SessionKey.DEFAULT_ELEMENT_NAME);
        
        key.setSOAP11Actor(expectedSOAP11Actor);
        key.setSOAP11MustUnderstand(expectedSOAP11MustUnderstand);
        key.setEncType(expectedEncType);
        
        assertEquals(expectedDOM, key);
    }

    /** {@inheritDoc} */
    public void testChildElementsMarshall() {
        SessionKey key = (SessionKey) buildXMLObject(SessionKey.DEFAULT_ELEMENT_NAME);
        
        key.setSOAP11Actor(expectedSOAP11Actor);
        key.setSOAP11MustUnderstand(expectedSOAP11MustUnderstand);
        key.setEncType(expectedEncType);
        
        key.setGeneratedKey((GeneratedKey) buildXMLObject(GeneratedKey.DEFAULT_ELEMENT_NAME));
        
        assertEquals(expectedChildElementsDOM, key);
    }

}