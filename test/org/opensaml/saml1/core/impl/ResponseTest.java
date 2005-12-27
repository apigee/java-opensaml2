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

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.util.xml.ParserPoolManager;
import org.opensaml.common.util.xml.XMLHelper;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.Response;
import org.opensaml.saml1.core.Status;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Test class for org.opensaml.saml1.core.Response
 */
public class ResponseTest extends SAMLObjectBaseTestCase {

    /** A file with a Conditions with kids */

    private final String fullElementsFile;

    /** The expected result of a marshalled multiple element */

    private Document expectedFullDOM;

    /**
     * Representation of NotIssueInstant in test file.
     */

    private final GregorianCalendar issueInstant;
    
    private final String inResponseTo;
    private final int minorVersion;
    private final String recipient;

    /**
     * Constructor
     * 
     */
    public ResponseTest() {
        singleElementFile = "/data/org/opensaml/saml1/singleResponse.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/singleResponseAttributes.xml";
        fullElementsFile = "/data/org/opensaml/saml1/ResponseWithChildren.xml";
        //
        // IssueInstant="1970-01-01T00:00:00.100Z"
        //
        issueInstant = new GregorianCalendar(1970,0,1, 0,0,0);
        issueInstant.set(Calendar.MILLISECOND, 100);
        
        inResponseTo="inresponseto";
        minorVersion=1;
        recipient="recipient";
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        ParserPoolManager ppMgr = ParserPoolManager.getInstance();

        expectedFullDOM = ppMgr.parse(new InputSource(SAMLObjectBaseTestCase.class
                .getResourceAsStream(fullElementsFile)));
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    @Override
    public void testSingleElementUnmarshall() {
        
        Response response = (Response) unmarshallElement(singleElementFile);

        GregorianCalendar date = response.getIssueInstant();
        assertNull("IssueInstant attribute has a value of " + 
                            XMLHelper.calendarToString(date) + 
                            ", expected no value", date);
        
        Assertion assertion;
        assertion = response.getAssertion();
        assertNull("Assertion element has a value of " + assertion + ", expected no value", assertion);

        Status status;
        status= response.getStatus();
        assertNull("Status element has a value of " + status + ", expected no value", status);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesUnmarshall() {
        Response response;

        response = (Response) unmarshallElement(singleElementOptionalAttributesFile);

        GregorianCalendar date = response.getIssueInstant();
        assertEquals("IssueInstant attribute ", XMLHelper.calendarToString(issueInstant), XMLHelper.calendarToString(date));
        
        String string = response.getInResponseTo();
        assertEquals("InResponseTo attribute ", inResponseTo, string);

        string = response.getRecipient();
        assertEquals("Recipient attribute ", recipient, string);
        
        int i = response.getMinorVersion();
        assertEquals("MinorVersion attribute ", minorVersion, i);
    }

    /**
     * Test an Response file with children
     */

    public void testFullElementsUnmarshall() {
        Response response;

        response = (Response) unmarshallElement(fullElementsFile);

        Assertion assertion;
        assertion = response.getAssertion();
        assertNotNull("No Assertion element found", assertion);

        Status status;
        status= response.getStatus();
        assertNotNull("No Status element found", status);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    @Override
    public void testSingleElementMarshall() {

        Response response = (Response) buildSAMLObject(Response.QNAME);

        assertEquals(expectedDOM, response);
    }

    /**
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesMarshall() {
        Response response = (Response) buildSAMLObject(Response.QNAME);

        response.setInResponseTo(inResponseTo);
        response.setIssueInstant(issueInstant);
        response.setRecipient(recipient);
        response.setMinorVersion(minorVersion);

        assertEquals(expectedOptionalAttributesDOM, response);
    }
    
    /**
     * Test Marshalling up a file with children
     *
     */

    public void testFullElementsMarshall() {
        Response response = (Response) buildSAMLObject(Response.QNAME);

        try {
            response.setAssertion(new AssertionImpl());
            response.setStatus(new StatusImpl());
        } catch (IllegalAddException e) {
            fail("Threw IllegalAddException");
        }
        
        assertEquals(expectedFullDOM, response);

    }

}
