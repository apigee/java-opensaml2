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
import org.opensaml.saml2.core.ArtifactResolve;

/**
 *
 */
public class ArtifactResolveTest extends RequestTest {

    /**
     * Constructor
     *
     */
    public ArtifactResolveTest() {
        super();
        singleElementFile = "/data/org/opensaml/saml2/core/impl/ArtifactResolve.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/core/impl/ArtifactResolveOptionalAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml2/core/impl/ArtifactResolveChildElements.xml";
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
        QName qname = new QName(SAMLConstants.SAML20P_NS, ArtifactResolve.LOCAL_NAME);
        ArtifactResolve ar = (ArtifactResolve) buildSAMLObject(qname);
        
        super.populateRequiredAttributes(ar);
        
        assertEquals(expectedDOM, ar);
    }
    
    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, ArtifactResolve.LOCAL_NAME);
        ArtifactResolve ar = (ArtifactResolve) buildSAMLObject(qname);
        
        super.populateRequiredAttributes(ar);
        super.populateOptionalAttributes(ar);
        
        assertEquals(expectedOptionalAttributesDOM, ar);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, ArtifactResolve.LOCAL_NAME);
        ArtifactResolve ar = (ArtifactResolve) buildSAMLObject(qname);
        
        super.populateChildElements(ar);
        
        ar.setArtifact(new ArtifactImpl());
        
        assertEquals(expectedChildElementsDOM, ar);
    }
 

    /**
     * @see org.opensaml.saml2.core.impl.RequestTest#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        ArtifactResolve ar = (ArtifactResolve) unmarshallElement(singleElementFile);
        
        assertNotNull("ArtifactResolve was null", ar);
        super.helperTestSingleElementUnmarshall(ar);
    }
    
    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        ArtifactResolve ar = (ArtifactResolve) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertNotNull("ArtifactResolve was null", ar);
        super.helperTestSingleElementOptionalAttributesUnmarshall(ar);
    }


    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    public void testChildElementsUnmarshall() {
        ArtifactResolve ar = (ArtifactResolve) unmarshallElement(childElementsFile);
        
        assertNotNull("Artifact was null", ar.getArtifact());
        super.helperTestChildElementsUnmarshall(ar);
    }
}