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

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Audience;

/**
 * Test for org.opensaml.saml1.core.Audience Objects
 */
public class AudienceTest extends SAMLObjectBaseTestCase {

    /** name used to generate objects */
    private final QName qname;

    private final String expectedUri;
    
    /**
     * Constructor
     */
    public AudienceTest() {
        super();
        singleElementFile = "/data/org/opensaml/saml1/singleAudience.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/singleAudienceAttributes.xml";
        expectedUri = "urn:oasis:names:tc:SAML:1.0:assertion";
        qname = new QName(SAMLConstants.SAML1_NS, Audience.LOCAL_NAME, SAMLConstants.SAML1_PREFIX);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    @Override
    public void testSingleElementUnmarshall() {
        Audience audience = (Audience) unmarshallElement(singleElementFile);
        
        assertNull("Uri is non-null", audience.getUri());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     * 
     * No attributes, so test content
     */
    @Override
    public void testSingleElementOptionalAttributesUnmarshall() {
        Audience audience = (Audience) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertEquals("Uri", expectedUri, audience.getUri());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    @Override
    public void testSingleElementMarshall() {
        assertEquals(expectedDOM, buildXMLObject(qname));
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesMarshall() {
        Audience audience = (Audience) buildXMLObject(qname);
        
        audience.setUri(expectedUri);
        assertEquals(expectedOptionalAttributesDOM, audience);
        
    }

}
