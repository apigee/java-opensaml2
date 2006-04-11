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

package org.opensaml.saml2.metadata.impl;

import java.util.ArrayList;

import javax.xml.namespace.QName;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.common.Extensions;
import org.opensaml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml2.metadata.AuthzService;
import org.opensaml.saml2.metadata.NameIDFormat;
import org.opensaml.saml2.metadata.PDPDescriptor;

/**
 * Test case for creating, marshalling, and unmarshalling {@link org.opensaml.saml2.metadata.impl.PDPDescriptorImpl}.
 */
public class PDPDescriptorTest extends SAMLObjectBaseTestCase {

    /** List of expected supported protocols */
    protected ArrayList<String> expectedSupportedProtocol;

    /** Expected cacheDuration value in miliseconds */
    protected long expectedCacheDuration;

    /** Expected validUntil value */
    protected DateTime expectedValidUntil;

    /** Expected error url */
    protected String expectedErrorURL;

    /**
     * Constructor
     */
    public PDPDescriptorTest() {
        singleElementFile = "/data/org/opensaml/saml2/metadata/impl/PDPDescriptor.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/metadata/impl/PDPDescriptorOptionalAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml2/metadata/impl/PDPDescriptorChildElements.xml";
    }

    /*
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

        expectedSupportedProtocol = new ArrayList<String>();
        expectedSupportedProtocol.add("urn:foo:bar");
        expectedSupportedProtocol.add("urn:fooz:baz");

        expectedCacheDuration = 90000;
        expectedValidUntil = new DateTime(2005, 12, 7, 10, 21, 0, 0, ISOChronology.getInstanceUTC());

        expectedErrorURL = "http://example.org";
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        PDPDescriptor descriptor = (PDPDescriptor) unmarshallElement(singleElementFile);

        assertEquals("Supported protocols not equal to expected value", expectedSupportedProtocol, descriptor
                .getSupportedProtocols());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        PDPDescriptor descriptor = (PDPDescriptor) unmarshallElement(singleElementOptionalAttributesFile);

        assertEquals("Cache duration was not expected value", expectedCacheDuration, descriptor.getCacheDuration()
                .longValue());
        assertEquals("ValidUntil was not expected value", expectedValidUntil, descriptor.getValidUntil());
        assertEquals("ErrorURL was not expected value", expectedErrorURL, descriptor.getErrorURL());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    public void testChildElementsUnmarshall() {
        PDPDescriptor descriptor = (PDPDescriptor) unmarshallElement(childElementsFile);

        assertNotNull("<Extensions>", descriptor.getExtensions());
        // TODO KeyDescriptor
        assertEquals("KeyDescriptor", 0, descriptor.getKeyDescriptors().size());

        assertEquals("AuthzService count", 3, descriptor.getAuthzServices().size());
        assertEquals("AssertionIDRequestService count", 2, descriptor.getAssertionIDRequestServices().size());
        assertEquals("NameIDFormat count", 1, descriptor.getNameIDFormats().size());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, PDPDescriptor.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        PDPDescriptor descriptor = (PDPDescriptor) buildXMLObject(qname);

        for (String protocol : expectedSupportedProtocol) {
            descriptor.addSupportedProtocol(protocol);
        }

        assertEquals(expectedDOM, descriptor);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, PDPDescriptor.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        PDPDescriptor descriptor = (PDPDescriptor) buildXMLObject(qname);

        for (String protocol : expectedSupportedProtocol) {
            descriptor.addSupportedProtocol(protocol);
        }

        descriptor.setCacheDuration(expectedCacheDuration);
        descriptor.setValidUntil(expectedValidUntil);
        descriptor.setErrorURL(expectedErrorURL);

        assertEquals(expectedOptionalAttributesDOM, descriptor);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, PDPDescriptor.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        PDPDescriptor descriptor = (PDPDescriptor) buildXMLObject(qname);

        QName extensionsQName = new QName(SAMLConstants.SAML20MD_NS, Extensions.LOCAL_NAME,
                SAMLConstants.SAML20MD_PREFIX);
        descriptor.setExtensions((Extensions) buildXMLObject(extensionsQName));
        // TODO KeyDescriptor

        QName authzQName = new QName(SAMLConstants.SAML20MD_NS, AuthzService.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        for (int i = 0; i < 3; i++) {
            descriptor.getAuthzServices().add((AuthzService) buildXMLObject(authzQName));
        }

        QName assertIDReqQName = new QName(SAMLConstants.SAML20MD_NS, AssertionIDRequestService.LOCAL_NAME,
                SAMLConstants.SAML20MD_PREFIX);
        for (int i = 0; i < 2; i++) {
            descriptor.getAssertionIDRequestServices()
                    .add((AssertionIDRequestService) buildXMLObject(assertIDReqQName));
        }

        QName nameIDFormatQName = new QName(SAMLConstants.SAML20MD_NS, NameIDFormat.LOCAL_NAME,
                SAMLConstants.SAML20MD_PREFIX);
        descriptor.getNameIDFormats().add((NameIDFormat) buildXMLObject(nameIDFormatQName));

        assertEquals(expectedChildElementsDOM, descriptor);
    }
}