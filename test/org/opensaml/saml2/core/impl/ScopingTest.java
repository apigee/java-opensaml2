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

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.IDPList;
import org.opensaml.saml2.core.RequesterID;
import org.opensaml.saml2.core.Scoping;

/**
 *Test case for creating, marshalling, and unmarshalling
 * {@link org.opensaml.saml2.core.impl.ScopingImpl}.
 */
public class ScopingTest extends SAMLObjectBaseTestCase {
    
    /** Expected ProxyCount*/
    private int expectedProxyCount;

    /** Expected number of child RequesterID's */
    private int expectedNumRequestIDs;
    
    /**
     * Constructor
     *
     */
    public ScopingTest() {
        singleElementFile = "/data/org/opensaml/saml2/core/impl/Scoping.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/core/impl/ScopingOptionalAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml2/core/impl/ScopingChildElements.xml";
    }
    
    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        expectedProxyCount = 5;
        expectedNumRequestIDs = 3;
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, Scoping.LOCAL_NAME);
        Scoping scoping = (Scoping) buildXMLObject(qname);
        
        assertEquals(expectedDOM, scoping);

    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, Scoping.LOCAL_NAME);
        Scoping scoping = (Scoping) buildXMLObject(qname);
        
        scoping.setProxyCount(new Integer(expectedProxyCount));
        
        assertEquals(expectedOptionalAttributesDOM, scoping);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, Scoping.LOCAL_NAME);
        Scoping scoping = (Scoping) buildXMLObject(qname);
        
        QName idpListQName = new QName(SAMLConstants.SAML20_NS, IDPList.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        scoping.setIDPList((IDPList) buildXMLObject(idpListQName));
        
        QName requesterIDQName = new QName(SAMLConstants.SAML20_NS, RequesterID.LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
        for (int i = 0; i<expectedNumRequestIDs; i++){
            scoping.getRequesterIDs().add((RequesterID) buildXMLObject(requesterIDQName));
        }
        
        assertEquals(expectedChildElementsDOM, scoping);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        Scoping scoping = (Scoping) unmarshallElement(singleElementFile);
        
        assertNull("ProxyCount", scoping.getProxyCount());
        assertNull("IDPList", scoping.getIDPList());
        assertEquals("RequesterID count", 0 , scoping.getRequesterIDs().size());

    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        Scoping scoping = (Scoping) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertNotNull("ProxyCount", scoping.getProxyCount());
        assertNull("IDPList", scoping.getIDPList());
        assertEquals("RequesterID count", 0, scoping.getRequesterIDs().size());
    }
    
    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    public void testChildElementsUnmarshall() {
        Scoping scoping = (Scoping) unmarshallElement(childElementsFile);
        
        assertNull("ProxyCount", scoping.getProxyCount());
        assertNotNull("IDPList", scoping.getIDPList());
        assertEquals("RequesterID count", expectedNumRequestIDs, scoping.getRequesterIDs().size());
    }
}