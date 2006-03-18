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
package org.opensaml.saml1.core.impl;

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.saml1.core.NameIdentifier;

/**
 * Test case for NameIdentifier
 */
public class NameIdentifierTest extends SAMLObjectBaseTestCase {

    private String expectedNameIdentifier;
    private String expectedFormat;
    private String expectedNameQualifier;
    
    /**
     * Constructor
     */
    public NameIdentifierTest() {
        super();
        singleElementFile = "/data/org/opensaml/saml1/singleNameIdentifier.xml";
        singleElementOptionalAttributesFile  = "/data/org/opensaml/saml1/singleNameIdentifierAttributes.xml";
        expectedFormat = "format";
        expectedNameIdentifier = "IdentifierText";
        expectedNameQualifier = "Qualifier";
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    @Override
    public void testSingleElementUnmarshall() {
        NameIdentifier nameIdentifier = (NameIdentifier) unmarshallElement(singleElementFile);
        
        assertNull("Name Identifer contents present", nameIdentifier.getNameIdentifier());
        assertNull("NameQualifier present", nameIdentifier.getNameQualifier());
        assertNull("Format present", nameIdentifier.getFormat());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesUnmarshall() {
        NameIdentifier nameIdentifier = (NameIdentifier) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertEquals("Name Identifier contents", expectedNameIdentifier, nameIdentifier.getNameIdentifier());
        assertEquals("NameQualfier attribute", expectedNameQualifier, nameIdentifier.getNameQualifier());
        assertEquals("Format attribute", expectedFormat, nameIdentifier.getFormat());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    @Override
    public void testSingleElementMarshall() {
        NameIdentifier nameIdentifier = new NameIdentifierImpl(null);
        
        assertEquals(expectedDOM, nameIdentifier);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesMarshall() {
        NameIdentifier nameIdentifier = new NameIdentifierImpl(null);
        
        nameIdentifier.setFormat(expectedFormat);
        nameIdentifier.setNameIdentifier(expectedNameIdentifier);
        nameIdentifier.setNameQualifier(expectedNameQualifier);
        
        assertEquals(expectedOptionalAttributesDOM, nameIdentifier);
    }
}
