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
import org.opensaml.saml1.core.AssertionArtifact;

/**
 * Test for {@link org.opensaml.saml1.core.AssertionArtifact}
 */
public class AssertionArtifactTest extends SAMLObjectBaseTestCase {

    private final String expectedAssertionArtifact;  
    
    /**
     * Constructor
     */
    public AssertionArtifactTest() {
        super();
        singleElementFile = "/data/org/opensaml/saml1/singleAssertionArtifact.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/singleAssertionArtifactAttribute.xml";
        expectedAssertionArtifact = "Test Text";
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    @Override
    public void testSingleElementUnmarshall() {
        AssertionArtifact artifact = (AssertionArtifact) unmarshallElement(singleElementFile);
        
        assertNull("AssertionArtifact contents present", artifact.getAssertionArtifact());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesUnmarshall() {
        AssertionArtifact artifact = (AssertionArtifact) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertEquals("AssertionArtifact contents present", expectedAssertionArtifact, artifact.getAssertionArtifact());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    @Override
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML1P_NS, AssertionArtifact.LOCAL_NAME);
        
        assertEquals(expectedDOM, buildSAMLObject(qname));
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML1P_NS, AssertionArtifact.LOCAL_NAME);
        
        AssertionArtifact artifact = (AssertionArtifact) buildSAMLObject(qname);
        artifact.setAssertionArtifact(expectedAssertionArtifact);
        assertEquals(expectedOptionalAttributesDOM, artifact);
    }
}
