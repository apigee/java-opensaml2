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

package org.opensaml.saml2.metadata.impl;

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.saml2.metadata.Endpoint;

/**
 * Test case for creating, marshalling, and unmarshalling {@link org.opensaml.saml2.metadata.impl.EndpointImpl}.
 */
public class EndpointTest extends SAMLObjectBaseTestCase {

    /** Expected Binding value */
    protected String expectedBinding;

    /** Expected Location value */
    protected String expectedLocation;

    /** Expected Location value */
    protected String expectedResponseLocation;

    /**
     * Constructor
     */
    public EndpointTest() {
        singleElementFile = "/data/org/opensaml/saml2/metadata/impl/Endpoint.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/metadata/impl/EndpointOptionalAttributes.xml";
    }

    /*
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        expectedBinding = "urn:example.org:binding:foo";
        expectedLocation = "http://example.org";
        expectedResponseLocation = "http://example.org/response";
    }

    /*
     * @see org.opensaml.common.BaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        Endpoint endpointObj = (Endpoint) unmarshallElement(singleElementFile);

        String binding = endpointObj.getBinding();
        assertEquals("Binding attribute has a value of " + binding + ", expected a value of " + expectedBinding,
                expectedBinding, binding);

        String location = endpointObj.getLocation();
        assertEquals("Location attribute has a value of " + location + ", expected a value of " + expectedLocation,
                expectedLocation, location);

        String responseLocation = endpointObj.getResponseLocation();
        assertNull("ResponseLocation attribute has a value of " + responseLocation + ", expected no value",
                responseLocation);
    }

    /*
     * @see org.opensaml.common.BaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        Endpoint endpointObj = (Endpoint) unmarshallElement(singleElementOptionalAttributesFile);

        String binding = endpointObj.getBinding();
        assertEquals("Binding attribute has a value of " + binding + ", expected a value of " + expectedBinding,
                expectedBinding, binding);

        String location = endpointObj.getLocation();
        assertEquals("Location attribute has a value of " + location + ", expected a value of " + expectedLocation,
                expectedLocation, location);

        String responseLocation = endpointObj.getResponseLocation();
        assertEquals("ResponseLocation attribute has a value of " + responseLocation + ", expected a value of "
                + expectedResponseLocation, expectedResponseLocation, responseLocation);
    }

    /*
     * @see org.opensaml.common.BaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        Endpoint endpoint = (Endpoint) buildSAMLObject(Endpoint.QNAME);

        endpoint.setBinding(expectedBinding);
        endpoint.setLocation(expectedLocation);

        assertEquals(expectedDOM, endpoint);
    }

    /*
     * @see org.opensaml.common.BaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        Endpoint endpoint = (Endpoint) buildSAMLObject(Endpoint.QNAME);

        endpoint.setBinding(expectedBinding);
        endpoint.setLocation(expectedLocation);
        endpoint.setResponseLocation(expectedResponseLocation);

        assertEquals(expectedOptionalAttributesDOM, endpoint);
    }
}