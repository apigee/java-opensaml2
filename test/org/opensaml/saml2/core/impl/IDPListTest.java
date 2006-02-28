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

/**
 * Test case for creating, marshalling, and unmarshalling
 * {@link org.opensaml.saml2.core.impl.IDPEntryImpl}.
 */
public class IDPListTest extends SAMLObjectBaseTestCase {
    
    /** The expected number of IDPEntry children */
    private int expectedNumIDPEntryChildren;

    /**
     * Constructor
     */
    public IDPListTest() {
        singleElementFile = "/data/org/opensaml/saml2/core/impl/IDPList.xml";
        childElementsFile = "/data/org/opensaml/saml2/core/impl/IDPListChildElements.xml";
    }
    
    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        expectedNumIDPEntryChildren = 3;
    }


    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, IDPList.LOCAL_NAME);
        IDPList list = (IDPList) buildXMLObject(qname);

        assertEquals(expectedDOM, list);
    }
 

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, IDPList.LOCAL_NAME);
        IDPList list = (IDPList) buildXMLObject(qname);
        
        for (int i=0; i<expectedNumIDPEntryChildren; i++)
            list.getIDPEntrys().add(new IDPEntryImpl());
        
        list.setGetComplete(new GetCompleteImpl());
        
        assertEquals(expectedChildElementsDOM, list);
        
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        IDPList list = (IDPList) unmarshallElement(singleElementFile);
        
        assertNotNull("IDPList", list);
        assertEquals("IDPEntry count", 0, list.getIDPEntrys().size());
        assertNull("GetComplete", list.getGetComplete());
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    public void testChildElementsUnmarshall() {
        IDPList list = (IDPList) unmarshallElement(childElementsFile);
        
        assertEquals("IDPEntry count", expectedNumIDPEntryChildren, list.getIDPEntrys().size());
        assertNotNull("GetComplete", list.getGetComplete());
    }
}