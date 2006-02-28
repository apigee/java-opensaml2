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
import org.opensaml.saml2.core.ArtifactResponse;

/**
 *
 */
public class ArtifactResponseTest extends StatusResponseTest {

    /**
     * Constructor
     *
     */
    public ArtifactResponseTest() {
        super();
        singleElementFile = "/data/org/opensaml/saml2/core/impl/ArtifactResponse.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/core/impl/ArtifactResponseOptionalAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml2/core/impl/ArtifactResponseChildElements.xml";
    }
    
    
    
    /**
     * @see org.opensaml.saml2.core.impl.StatusResponseTest#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }


    /**
     * @see org.opensaml.saml2.core.impl.StatusResponseTest#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, ArtifactResponse.LOCAL_NAME);
        ArtifactResponse ar = (ArtifactResponse) buildXMLObject(qname);
        
        super.populateRequiredAttributes(ar);
        
        assertEquals(expectedDOM, ar);
    }
    
    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, ArtifactResponse.LOCAL_NAME);
        ArtifactResponse ar = (ArtifactResponse) buildXMLObject(qname);
        
        super.populateRequiredAttributes(ar);
        super.populateOptionalAttributes(ar);
        
        assertEquals(expectedOptionalAttributesDOM, ar);
    }


    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, ArtifactResponse.LOCAL_NAME);
        ArtifactResponse ar = (ArtifactResponse) buildXMLObject(qname);
        
        super.populateChildElements(ar);
        
        assertEquals(expectedChildElementsDOM, ar);
    }


    /**
     * @see org.opensaml.saml2.core.impl.StatusResponseTest#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        ArtifactResponse ar = (ArtifactResponse) unmarshallElement(singleElementFile);
        
        super.helperTestSingleElementUnmarshall(ar);
    }

  

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        ArtifactResponse ar = (ArtifactResponse) unmarshallElement(singleElementOptionalAttributesFile);
        
        super.helperTestSingleElementOptionalAttributesUnmarshall(ar);

    }



    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    public void testChildElementsUnmarshall() {
        ArtifactResponse ar = (ArtifactResponse) unmarshallElement(childElementsFile);
        
        super.helperTestChildElementsUnmarshall(ar);
    }




}
