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

package org.opensaml.saml1.core.impl;

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.saml1.core.StatusCode;

/**
 * Test class for org.opensaml.saml1.core.StatusCode
 */
public class StatusCodeTest extends SAMLObjectBaseTestCase {

    /** The expected value for the value attribute */

    private final String value;

    /** The expected value for the value attribute okf the child element */

    private final String childValue;

    /**
     * Constructor
     * 
     */
    public StatusCodeTest() {
        childElementsFile = "/data/org/opensaml/saml1/FullStatusCode.xml";
        singleElementFile = "/data/org/opensaml/saml1/singleStatusCode.xml";
        value = "samlp:Success";
        childValue = "samlp:VersionMismatch";
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    @Override
    public void testSingleElementUnmarshall() {

        StatusCode code = (StatusCode) unmarshallElement(singleElementFile);

        assertEquals("Single Element Value wrong", value, code.getValue());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    @Override
    public void testChildElementsUnmarshall() {

        StatusCode code = (StatusCode) unmarshallElement(childElementsFile);

        assertNotNull("Child StatusCode", code.getStatusCode());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    @Override
    public void testSingleElementMarshall() {
        StatusCode code = new StatusCodeImpl();

        code.setValue(value);

        assertEquals(expectedDOM, code);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    @Override
    public void testChildElementsMarshall() {

        StatusCode code = new StatusCodeImpl();

        code.setValue(value);

        code.setStatusCode(new StatusCodeImpl());

        code.getStatusCode().setValue(childValue);

        assertEquals(expectedChildElementsDOM, code);
    }
}
