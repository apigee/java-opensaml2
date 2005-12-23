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

import org.opensaml.common.BaseTestCase;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.SAMLObjectBuilderFactory;
import org.opensaml.common.io.Marshaller;
import org.opensaml.common.io.MarshallerFactory;
import org.opensaml.common.io.MarshallingException;
import org.opensaml.common.io.UnknownAttributeException;
import org.opensaml.common.io.UnknownElementException;
import org.opensaml.common.io.Unmarshaller;
import org.opensaml.common.io.UnmarshallerFactory;
import org.opensaml.common.io.UnmarshallingException;
import org.opensaml.common.util.OrderedSet;
import org.opensaml.common.util.xml.ParserPoolManager;
import org.opensaml.common.util.xml.XMLConstants;
import org.opensaml.common.util.xml.XMLParserException;
import org.opensaml.saml2.metadata.AuthnAuthorityDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * Test case for creating, marshalling, and unmarshalling
 * {@link org.opensaml.saml2.metadata.impl.AuthnAuthorityDescriptorImpl}.
 */
public class AuthnAuthorityDescriptorTest extends BaseTestCase {

    /** Location of file containing a single EntitiesDescriptor element with NO optional attributes */
    private static String singleElementFile = "/data/org/opensaml/saml2/metadata/impl/singleAuthnAuthorityDescriptor.xml";

    /** Location of file containing a single EntitiesDescriptor element with all optional attributes */
    private static String singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/metadata/impl/singleAuthnAuthorityDescriptorOptionalAttributes.xml";

    /** Expected supported protocol enumeration */
    private Set<String> expectedSupportedProtocols;
    
    /** Expected cacheDuration value in miliseconds */
    private long expectedCacheDuration;

    /** Expected validUntil value */
    private GregorianCalendar expectedValidUntil;

    private String expectedErrorURL;

    /** The expected result of a marshalled single EntitiesDescriptor element with no optional attributes */
    private Document expectedDOM;

    /** The expected result of a marshalled single EntitiesDescriptor element with all optional attributes */
    private Document expectedOptionalAttributesDOM;

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

        ParserPoolManager ppMgr = ParserPoolManager.getInstance();
        expectedDOM = ppMgr.parse(new InputSource(AuthnAuthorityDescriptorTest.class
                .getResourceAsStream(singleElementFile)));
        expectedOptionalAttributesDOM = ppMgr.parse(new InputSource(AuthnAuthorityDescriptorTest.class
                .getResourceAsStream(singleElementOptionalAttributesFile)));
    }

    /**
     * Tests unmarshalling a document that contains a single AuthnAuthorityDescriptor element (no children) with no
     * optional attributes.
     */
    public void testSingleElementUnmarshall() {
        ParserPoolManager ppMgr = ParserPoolManager.getInstance();
        try {
            Document doc = ppMgr.parse(new InputSource(EntitiesDescriptorTest.class
                    .getResourceAsStream(singleElementFile)));
            Element authnAuthorityElem = doc.getDocumentElement();

            Unmarshaller unmarshaller = UnmarshallerFactory.getInstance().getUnmarshaller(authnAuthorityElem);
            if (unmarshaller == null) {
                fail("Unable to retrieve unmarshaller by DOM Element");
            }

            AuthnAuthorityDescriptor authnAuthorityObj = (AuthnAuthorityDescriptor) unmarshaller
                    .unmarshall(authnAuthorityElem);

            Set<String> protoEnum = authnAuthorityObj.getSupportedProtocols();
            assertEquals("Supported protocol enumeration was not equal to expected enumeration", expectedSupportedProtocols, protoEnum);

            Long duration = authnAuthorityObj.getCacheDuration();
            assertNull("cacheDuration attribute has a value of " + duration + ", expected no value", duration);

            GregorianCalendar validUntil = authnAuthorityObj.getValidUntil();
            assertNull("validUntil attribute has a value of " + validUntil + ", expected no value", validUntil);
            
            String errorURL = authnAuthorityObj.getErrorURL();
            assertNull("errorURL attribute has a value of " + errorURL + ", expected no value", errorURL);

        } catch (XMLParserException e) {
            fail("Unable to parse file containing single EntitiesDescriptor element");
        } catch (UnknownAttributeException e) {
            fail("Unknown attribute exception thrown but example element does not contain any unknown attributes: " + e);
        } catch (UnknownElementException e) {
            fail("Unknown element exception thrown but example element does not contain any child elements");
        } catch (UnmarshallingException e) {
            fail("Unmarshalling failed with the following error:" + e);
        }
    }

    /**
     * Tests unmarshalling a document that contains a single AuthnAuthorityDescriptor element (no children) with all
     * optional attributes.
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        ParserPoolManager ppMgr = ParserPoolManager.getInstance();
        try {
            Document doc = ppMgr.parse(new InputSource(EntitiesDescriptorTest.class
                    .getResourceAsStream(singleElementOptionalAttributesFile)));
            Element authnAuthorityElem = doc.getDocumentElement();

            Unmarshaller unmarshaller = UnmarshallerFactory.getInstance().getUnmarshaller(authnAuthorityElem);
            if (unmarshaller == null) {
                fail("Unable to retrieve unmarshaller by DOM Element");
            }

            AuthnAuthorityDescriptor authnAuthorityObj = (AuthnAuthorityDescriptor) unmarshaller
                    .unmarshall(authnAuthorityElem);

            Set<String> protoEnum = authnAuthorityObj.getSupportedProtocols();
            assertEquals("Supported protocol enumeration was not equal to expected enumeration", expectedSupportedProtocols, protoEnum);

            long duration = authnAuthorityObj.getCacheDuration().longValue();
            assertEquals("cacheDuration attribute has a value of " + duration + ", expected a value of "
                    + expectedCacheDuration, expectedCacheDuration, duration);

            GregorianCalendar validUntil = authnAuthorityObj.getValidUntil();
            assertEquals("validUntil attribute value did not match expected value", 0, expectedValidUntil
                    .compareTo(validUntil));
            
            String errorURL = authnAuthorityObj.getErrorURL();
            assertEquals("errorURL attribute has a value of " + errorURL + ", expected a value of "
                    + expectedErrorURL, expectedErrorURL, errorURL);

        } catch (XMLParserException e) {
            fail("Unable to parse file containing single EntitiesDescriptor element");
        } catch (UnknownAttributeException e) {
            fail("Unknown attribute exception thrown but example element does not contain any unknown attributes: " + e);
        } catch (UnknownElementException e) {
            fail("Unknown element exception thrown but example element does not contain any child elements");
        } catch (UnmarshallingException e) {
            fail("Unmarshalling failed with the following error:" + e);
        }
    }

    /**
     * Tests marshalling the contents of a single AuthnAuthorityDescriptor element to a DOM document.
     */
    public void testSingleElementMarshall() {
        SAMLObjectBuilder objectBuilder = SAMLObjectBuilderFactory.getInstance().getBuilder(
                AuthnAuthorityDescriptor.QNAME);
        AuthnAuthorityDescriptor descriptor = (AuthnAuthorityDescriptor) objectBuilder.buildObject();

        descriptor.addSupportedProtocol(XMLConstants.SAML20P_NS);

        Marshaller marshaller = MarshallerFactory.getInstance().getMarshaller(descriptor);
        try {
            Element generatedDOM = marshaller.marshall(descriptor);
            assertXMLEqual("Marshalled DOM was not the same as the expected DOM", expectedDOM, generatedDOM
                    .getOwnerDocument());
        } catch (MarshallingException e) {
            fail("Marshalling failed with the following error: " + e);
        }
    }

    /**
     * Tests marshalling the contents of a single AuthnAuthorityDescriptor element with optional attributes to a DOM
     * document.
     */
    public void testSingleElementOptionalAttributesMarshall() throws Exception {
        SAMLObjectBuilder objectBuilder = SAMLObjectBuilderFactory.getInstance().getBuilder(
                AuthnAuthorityDescriptor.QNAME);
        AuthnAuthorityDescriptor descriptor = (AuthnAuthorityDescriptor) objectBuilder.buildObject();

        descriptor.addSupportedProtocol(XMLConstants.SAML20P_NS);
        descriptor.setValidUntil(expectedValidUntil);
        descriptor.setCacheDuration(expectedCacheDuration);
        descriptor.setErrorURL(expectedErrorURL);

        Marshaller marshaller = MarshallerFactory.getInstance().getMarshaller(descriptor);
        try {
            Element generatedDOM = marshaller.marshall(descriptor);
            assertXMLEqual("Marshalled DOM with optional attributes was not the same as the expected DOM",
                    expectedOptionalAttributesDOM, generatedDOM.getOwnerDocument());
        } catch (MarshallingException e) {
            fail("Marshalling failed with the following error: " + e);
        }
    }
}
