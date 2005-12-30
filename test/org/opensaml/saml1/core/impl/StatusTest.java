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
import org.opensaml.common.xml.ParserPoolManager;
import org.opensaml.saml1.core.Status;
import org.opensaml.xml.IllegalAddException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * org.opensaml.saml1.core.Status
 */
public class StatusTest extends SAMLObjectBaseTestCase {

    private final String fullElementsFile;
    private Document expectedFullDOM;

    /**
     * Constructor
     *
     */
    public StatusTest() {
        super();

        fullElementsFile = "/data/org/opensaml/saml1/FullStatus.xml";
        singleElementFile = "/data/org/opensaml/saml1/singleStatus.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml1/singleStatus.xml";
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
    
    
    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    @Override
    public void testSingleElementUnmarshall() {

        Status status = (Status) unmarshallElement(singleElementFile);

        assertNotNull("StatusCode", status.getStatusCode());
        assertNull("StatusMessage", status.getStatusMessage());
        assertNull("StatusDetail", status.getStatusDetail());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesUnmarshall() {
        // Northing
    }

    /**
     * Test an Response file with children
     */

    public void testFullElementsUnmarshall() {
        Status status = (Status) unmarshallElement(fullElementsFile);

        assertNotNull("StatusCode", status.getStatusCode());
        assertNotNull("StatusMessage", status.getStatusMessage());
    }
    
    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    @Override
    public void testSingleElementMarshall() {
        Status status = new StatusImpl();

        try {
            status.setStatusCode(new StatusCodeImpl());
        } catch (IllegalAddException e) {
            fail("Threw IllegalAddException");
        }
        status.getStatusCode().setValue("samlp:Sucess");
        
        assertEquals(expectedDOM, status);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesMarshall() {
        // Nothing
    }
   
    public void testFullElementsMarshall() {
        Status status = new StatusImpl();
        
        try {
            status.setStatusCode(new StatusCodeImpl());
            status.setStatusMessage(new StatusMessageImpl());
        } catch (IllegalAddException e) {
            fail("Threw IllegalAddException");
        }
        status.getStatusCode().setValue("samlp:Sucess");
        
        assertEquals(expectedFullDOM, status);
    }
}
