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
import org.opensaml.saml2.core.AuthzDecisionQuery;

/**
 *
 */
public class AuthzDecisionQueryTest extends SubjectQueryTest {
    
    /** Expected Resource attribute value */
    private String expectedResource;
    
    /** Expected number of Action child elements */
    private int expectedNumActions;
    

    /**
     * Constructor
     *
     */
    public AuthzDecisionQueryTest() {
        super();
        singleElementFile = "/data/org/opensaml/saml2/core/impl/AuthzDecisionQuery.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/core/impl/AuthzDecisionQueryOptionalAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml2/core/impl/AuthzDecisionQueryChildElements.xml";
    }

    /**
     * @see org.opensaml.saml2.core.impl.SubjectQueryTest#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        expectedResource = "urn:string:resource";
        expectedNumActions = 2;
    }

    /**
     * @see org.opensaml.saml2.core.impl.SubjectQueryTest#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, AuthzDecisionQuery.LOCAL_NAME);
        AuthzDecisionQuery query = (AuthzDecisionQuery) buildSAMLObject(qname);
        
        super.populateRequiredAttributes(query);
        query.setResource(expectedResource);
        
        assertEquals(expectedDOM, query);
    }
    
    
    
    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, AuthzDecisionQuery.LOCAL_NAME);
        AuthzDecisionQuery query = (AuthzDecisionQuery) buildSAMLObject(qname);
        
        super.populateRequiredAttributes(query);
        super.populateOptionalAttributes(query);
        query.setResource(expectedResource);
        
        assertEquals(expectedOptionalAttributesDOM, query);
    }
    
    

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, AuthzDecisionQuery.LOCAL_NAME);
        AuthzDecisionQuery query = (AuthzDecisionQuery) buildSAMLObject(qname);
        
        super.populateChildElements(query);
        for (int i=0; i<expectedNumActions; i++)
            query.getActions().add(new ActionImpl());
        query.setEvidence(new EvidenceImpl());
        
        assertEquals(expectedChildElementsDOM, query);
    }

    /**
     * 
     * @see org.opensaml.saml2.core.impl.SubjectQueryTest#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        AuthzDecisionQuery query = (AuthzDecisionQuery) unmarshallElement(singleElementFile);
        
        assertNotNull("AuthzDecisionQuery was null", query);
        assertEquals("Unmarshalled Resource attribute was not the expected value", expectedResource, query.getResource());
        super.helperTestSingleElementUnmarshall(query);

    }
    
    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        AuthzDecisionQuery query = (AuthzDecisionQuery) unmarshallElement(singleElementOptionalAttributesFile);
        
        super.helperTestSingleElementOptionalAttributesUnmarshall(query);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    public void testChildElementsUnmarshall() {
        AuthzDecisionQuery query = (AuthzDecisionQuery) unmarshallElement(childElementsFile);
        
        assertEquals("Action count", expectedNumActions, query.getActions().size());
        assertNotNull("Evidence was null", query.getEvidence());
        super.helperTestChildElementsUnmarshall(query);
    }

  
    

}
