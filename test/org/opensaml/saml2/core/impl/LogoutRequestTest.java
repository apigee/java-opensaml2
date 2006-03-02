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

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.LogoutRequest;

/**
 *
 */
public class LogoutRequestTest extends RequestTest {
    
    /** Expected Reason attribute value */
    private String expectedReason;
    
    /** Expected NotOnOrAfter attribute value */
    private DateTime expectedNotOnOrAfter;
    
    /** Expected number of SessionIndex child elements */
    private int expectedNumSessionIndexes;

    /**
     * Constructor
     *
     */
    public LogoutRequestTest() {
        super();
        singleElementFile = "/data/org/opensaml/saml2/core/impl/LogoutRequest.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/core/impl/LogoutRequestOptionalAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml2/core/impl/LogoutRequestChildElements.xml";
    }

    /**
     * @see org.opensaml.saml2.core.impl.RequestTest#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        expectedReason = "urn:string:reason";
        expectedNotOnOrAfter = new DateTime(2006, 2, 21, 20, 45, 0, 0, ISOChronology.getInstanceUTC());
        expectedNumSessionIndexes = 2;
    }

    /**
     * @see org.opensaml.saml2.core.impl.RequestTest#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, LogoutRequest.LOCAL_NAME);
        LogoutRequest req = (LogoutRequest) buildXMLObject(qname);
        
        super.populateRequiredAttributes(req);
        
        assertEquals(expectedDOM, req);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, LogoutRequest.LOCAL_NAME);
        LogoutRequest req = (LogoutRequest) buildXMLObject(qname);
        
        super.populateRequiredAttributes(req);
        super.populateOptionalAttributes(req);
        req.setReason(expectedReason);
        req.setNotOnOrAfter(expectedNotOnOrAfter);
        
        assertEquals(expectedOptionalAttributesDOM, req);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML20P_NS, LogoutRequest.LOCAL_NAME);
        LogoutRequest req = (LogoutRequest) buildXMLObject(qname);
        
        super.populateChildElements(req);
        req.setNameID(new NameIDImpl());
        for (int i=0; i<expectedNumSessionIndexes; i++)
            req.getSessionIndexes().add(new SessionIndexImpl());
        
        assertEquals(expectedChildElementsDOM, req);
    }
    
    /**
     * @see org.opensaml.saml2.core.impl.RequestTest#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        LogoutRequest req = (LogoutRequest) unmarshallElement(singleElementFile);
        
        assertNotNull("LogoutRequest was null", req);
        assertNull("Reason was not null", req.getReason());
        assertNull("NotOnOrAfter was not null", req.getNotOnOrAfter());
        super.helperTestSingleElementUnmarshall(req);
    }
 
    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        LogoutRequest req = (LogoutRequest) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertEquals("Unmarshalled Reason attribute was not the expectecd value", expectedReason, req.getReason());
        assertEquals("Unmarshalled NotOnOrAfter attribute was not the expectecd value", 0, expectedNotOnOrAfter.compareTo(req.getNotOnOrAfter()));
        super.helperTestSingleElementOptionalAttributesUnmarshall(req);
    }
    
    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    public void testChildElementsUnmarshall() {
        LogoutRequest req = (LogoutRequest) unmarshallElement(childElementsFile);
        
        assertNotNull("Identifier was null", req.getNameID());
        assertEquals("Number of unmarshalled SessionIndexes was not the expected value", expectedNumSessionIndexes, req.getSessionIndexes().size());
        super.helperTestChildElementsUnmarshall(req);
    }
    
}
