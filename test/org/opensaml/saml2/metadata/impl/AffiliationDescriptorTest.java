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

import javax.xml.namespace.QName;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.common.Extensions;
import org.opensaml.saml2.metadata.AffiliateMember;
import org.opensaml.saml2.metadata.AffiliationDescriptor;

/**
 * Test case for creating, marshalling, and unmarshalling
 * {@link org.opensaml.saml2.metadata.impl.AffiliationDescriptorImpl}.
 */
public class AffiliationDescriptorTest extends SAMLObjectBaseTestCase {

    /** Expected affiliationOwnerID value */
    protected String expectedOwnerID;

    /** Expceted ID value */
    protected String expectedID;

    /** Expected cacheDuration value in miliseconds */
    protected long expectedCacheDuration;

    /** Expected validUntil value */
    protected DateTime expectedValidUntil;

    /**
     * Constructor
     */
    public AffiliationDescriptorTest() {
        singleElementFile = "/data/org/opensaml/saml2/metadata/impl/AffiliationDescriptor.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/metadata/impl/AffiliationDescriptorOptionalAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml2/metadata/impl/AffiliationDescriptorChildElements.xml";
    }

    protected void setUp() throws Exception {
        super.setUp();

        expectedOwnerID = "urn:example.org";
        expectedID = "id";
        expectedCacheDuration = 90000;
        expectedValidUntil = new DateTime(2005, 12, 7, 10, 21, 0, 0, ISOChronology.getInstanceUTC());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        AffiliationDescriptor descriptor = (AffiliationDescriptor) unmarshallElement(singleElementFile);

        String ownerId = descriptor.getOwnerID();
        assertEquals("entityID attribute has a value of " + ownerId + ", expected a value of " + expectedOwnerID,
                expectedOwnerID, ownerId);

        Long duration = descriptor.getCacheDuration();
        assertNull("cacheDuration attribute has a value of " + duration + ", expected no value", duration);

        DateTime validUntil = descriptor.getValidUntil();
        assertNull("validUntil attribute has a value of " + validUntil + ", expected no value", validUntil);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        AffiliationDescriptor descriptor = (AffiliationDescriptor) unmarshallElement(singleElementOptionalAttributesFile);

        String ownerId = descriptor.getOwnerID();
        assertEquals("entityID attribute has a value of " + ownerId + ", expected a value of " + expectedOwnerID,
                expectedOwnerID, ownerId);

        String id = descriptor.getID();
        assertEquals("ID attribute has a value of " + id + ", expected a value of " + expectedID, expectedID, id);

        long duration = descriptor.getCacheDuration().longValue();
        assertEquals("cacheDuration attribute has a value of " + duration + ", expected a value of "
                + expectedCacheDuration, expectedCacheDuration, duration);

        DateTime validUntil = descriptor.getValidUntil();
        assertEquals("validUntil attribute value did not match expected value", 0, expectedValidUntil
                .compareTo(validUntil));
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    public void testChildElementsUnmarshall() {
        AffiliationDescriptor descriptor = (AffiliationDescriptor) unmarshallElement(childElementsFile);

        assertNotNull("Extensions", descriptor.getExtensions());
        // TODO KeyDescriptor unmarshall
        assertEquals("KeyDescriptor count", 0, descriptor.getKeyDescriptors().size());
        assertEquals("Affiliate Member count ", 3, descriptor.getMembers().size());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, AffiliationDescriptor.DEFAULT_ELEMENT_LOCAL_NAME,
                SAMLConstants.SAML20MD_PREFIX);
        AffiliationDescriptor descriptor = (AffiliationDescriptor) buildXMLObject(qname);

        descriptor.setOwnerID(expectedOwnerID);

        assertEquals(expectedDOM, descriptor);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, AffiliationDescriptor.DEFAULT_ELEMENT_LOCAL_NAME,
                SAMLConstants.SAML20MD_PREFIX);
        AffiliationDescriptor descriptor = (AffiliationDescriptor) buildXMLObject(qname);

        descriptor.setOwnerID(expectedOwnerID);
        descriptor.setID(expectedID);
        descriptor.setValidUntil(expectedValidUntil);
        descriptor.setCacheDuration(expectedCacheDuration);

        assertEquals(expectedOptionalAttributesDOM, descriptor);
    }

    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, AffiliationDescriptor.DEFAULT_ELEMENT_LOCAL_NAME,
                SAMLConstants.SAML20MD_PREFIX);
        AffiliationDescriptor descriptor = (AffiliationDescriptor) buildXMLObject(qname);

        descriptor.setOwnerID(expectedOwnerID);

        QName extensionsQName = new QName(SAMLConstants.SAML20MD_NS, Extensions.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        descriptor.setExtensions((Extensions) buildXMLObject(extensionsQName));
        // TODO KeyDescriptor to be tested

        QName affilMemberQName = new QName(SAMLConstants.SAML20MD_NS, AffiliateMember.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        descriptor.getMembers().add((AffiliateMember) buildXMLObject(affilMemberQName));
        descriptor.getMembers().add((AffiliateMember) buildXMLObject(affilMemberQName));
        descriptor.getMembers().add((AffiliateMember) buildXMLObject(affilMemberQName));

        assertEquals(expectedChildElementsDOM, descriptor);
    }

}