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
import org.opensaml.samlext.samlec.EncType;
import org.opensaml.samlext.samlec.SessionKeyMethod;

/**
 * Test case for creating, marshalling, and unmarshalling {@link SessionKeyMethod}.
 */
public class SessionKeyMethodTest extends BaseSAMLObjectProviderTestCase {
    
    private String expectedAlgorithm;
    
    public SessionKeyMethodTest() {
        singleElementFile = "/data/org/opensaml/samlext/samlec/impl/SessionKeyMethod.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/samlext/samlec/impl/SessionKeyMethodOptionalAttributes.xml";
        childElementsFile = "/data/org/opensaml/samlext/samlec/impl/SessionKeyMethodChildElements.xml";
    }
 
    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        
        expectedAlgorithm = "http://myalgorithm.example.com";
    }

    /** {@inheritDoc} */
    public void testSingleElementUnmarshall() {
        SessionKeyMethod key = (SessionKeyMethod) unmarshallElement(singleElementFile);
        
        assertNotNull(key);
    }

    /** {@inheritDoc} */
    public void testSingleElementOptionalAttributesUnmarshall() {
        SessionKeyMethod key = (SessionKeyMethod) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertNotNull(key);
        
        assertEquals("Algorithm had unexpected value", expectedAlgorithm, key.getAlgorithm());
    }
    
    /** {@inheritDoc} */
    public void testChildElementsUnmarshall() {
        SessionKeyMethod key = (SessionKeyMethod) unmarshallElement(childElementsFile);
        
        assertNotNull(key);
        assertEquals("Unexpected number of EncTypes", 2, key.getEncTypes().size());
    }
    
    /** {@inheritDoc} */
    public void testSingleElementMarshall() {
        SessionKeyMethod key = (SessionKeyMethod) buildXMLObject(SessionKeyMethod.DEFAULT_ELEMENT_NAME);
        
        assertEquals(expectedDOM, key);
    }

    /** {@inheritDoc} */
    public void testSingleElementOptionalAttributesMarshall() {
        SessionKeyMethod key = (SessionKeyMethod) buildXMLObject(SessionKeyMethod.DEFAULT_ELEMENT_NAME);

        key.setAlgorithm(expectedAlgorithm);
        
        assertEquals(expectedOptionalAttributesDOM, key);
    }
    
    /** {@inheritDoc} */
    public void testChildElementsMarshall() {
        SessionKeyMethod key = (SessionKeyMethod) buildXMLObject(SessionKeyMethod.DEFAULT_ELEMENT_NAME);
        
        key.getEncTypes().add((EncType) buildXMLObject(EncType.DEFAULT_ELEMENT_NAME));
        key.getEncTypes().add((EncType) buildXMLObject(EncType.DEFAULT_ELEMENT_NAME));
        
        assertEquals(expectedChildElementsDOM, key);
    }

}