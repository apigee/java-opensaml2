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
import org.opensaml.saml1.core.Status;

/**
 * org.opensaml.saml1.core.Status
 */
public class StatusTest extends SAMLObjectBaseTestCase {

    /**
     * Constructor
     */
    public StatusTest() {
        super();

        childElementsFile = "/data/org/opensaml/saml1/FullStatus.xml";
        singleElementFile = "/data/org/opensaml/saml1/singleStatus.xml";
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

    /**
     * Test an Response file with children
     */
    @Override
    public void testChildElementsUnmarshall() {
        Status status = (Status) unmarshallElement(childElementsFile);

        assertNotNull("StatusCode", status.getStatusCode());
        assertNotNull("StatusMessage", status.getStatusMessage());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    @Override
    public void testSingleElementMarshall() {
        Status status = new StatusImpl();

        status.setStatusCode(new StatusCodeImpl());

        status.getStatusCode().setValue("samlp:Sucess");

        assertEquals(expectedDOM, status);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    @Override
    public void testChildElementsMarshall() {
        Status status = new StatusImpl();

        status.setStatusCode(new StatusCodeImpl());
        status.setStatusMessage(new StatusMessageImpl());

        status.getStatusCode().setValue("samlp:Sucess");

        assertEquals(expectedChildElementsDOM, status);
    }
}
