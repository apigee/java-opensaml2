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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.util.OrderedSet;
import org.opensaml.common.util.xml.XMLConstants;
import org.opensaml.saml2.metadata.AuthnAuthorityDescriptor;

/**
 * Test case for creating, marshalling, and unmarshalling
 * {@link org.opensaml.saml2.metadata.impl.AuthnAuthorityDescriptorImpl}.
 */
public class AuthnAuthorityDescriptorTest extends SAMLObjectBaseTestCase {

    /** Expected supported protocol enumeration */
    protected Set<String> expectedSupportedProtocols;

    /** Expected cacheDuration value in miliseconds */
    protected long expectedCacheDuration;

    /** Expected validUntil value */
    protected GregorianCalendar expectedValidUntil;

    /** Expected errorURL value */
    protected String expectedErrorURL;

    /**
     * Constructor
     */
    public AuthnAuthorityDescriptorTest() {
        singleElementFile = "/data/org/opensaml/saml2/metadata/impl/singleAuthnAuthorityDescriptor.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/metadata/impl/singleAuthnAuthorityDescriptorOptionalAttributes.xml";
    }

    /*
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        expectedSupportedProtocols = new OrderedSet<String>();
        expectedSupportedProtocols.add(XMLConstants.SAML20P_NS);
        expectedCacheDuration = 90000;
        expectedValidUntil = new GregorianCalendar(2005, Calendar.DECEMBER, 7, 10, 21, 0);
        expectedErrorURL = "http://example.org";
    }

    /*
     * @see org.opensaml.common.BaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        AuthnAuthorityDescriptor authnAuthorityObj = (AuthnAuthorityDescriptor) unmarshallElement(singleElementFile);

        Set<String> protoEnum = authnAuthorityObj.getSupportedProtocols();
        assertEquals("Supported protocol enumeration was not equal to expected enumeration",
                expectedSupportedProtocols, protoEnum);

        Long duration = authnAuthorityObj.getCacheDuration();
        assertNull("cacheDuration attribute has a value of " + duration + ", expected no value", duration);

        GregorianCalendar validUntil = authnAuthorityObj.getValidUntil();
        assertNull("validUntil attribute has a value of " + validUntil + ", expected no value", validUntil);

        String errorURL = authnAuthorityObj.getErrorURL();
        assertNull("errorURL attribute has a value of " + errorURL + ", expected no value", errorURL);
    }

    /*
     * @see org.opensaml.common.BaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        AuthnAuthorityDescriptor authnAuthorityObj = (AuthnAuthorityDescriptor) unmarshallElement(singleElementOptionalAttributesFile);

        Set<String> protoEnum = authnAuthorityObj.getSupportedProtocols();
        assertEquals("Supported protocol enumeration was not equal to expected enumeration",
                expectedSupportedProtocols, protoEnum);

        long duration = authnAuthorityObj.getCacheDuration().longValue();
        assertEquals("cacheDuration attribute has a value of " + duration + ", expected a value of "
                + expectedCacheDuration, expectedCacheDuration, duration);

        GregorianCalendar validUntil = authnAuthorityObj.getValidUntil();
        assertEquals("validUntil attribute value did not match expected value", 0, expectedValidUntil
                .compareTo(validUntil));

        String errorURL = authnAuthorityObj.getErrorURL();
        assertEquals("errorURL attribute has a value of " + errorURL + ", expected a value of " + expectedErrorURL,
                expectedErrorURL, errorURL);
    }

    /*
     * @see org.opensaml.common.BaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        AuthnAuthorityDescriptor descriptor = (AuthnAuthorityDescriptor) buildSAMLObject(AuthnAuthorityDescriptor.QNAME);

        descriptor.addSupportedProtocol(XMLConstants.SAML20P_NS);

        assertEquals(expectedDOM, descriptor);
    }

    /*
     * @see org.opensaml.common.BaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        AuthnAuthorityDescriptor descriptor = (AuthnAuthorityDescriptor) buildSAMLObject(AuthnAuthorityDescriptor.QNAME);

        descriptor.addSupportedProtocol(XMLConstants.SAML20P_NS);
        descriptor.setValidUntil(expectedValidUntil);
        descriptor.setCacheDuration(expectedCacheDuration);
        descriptor.setErrorURL(expectedErrorURL);

        assertEquals(expectedOptionalAttributesDOM, descriptor);
    }
}