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

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.saml2.metadata.AffiliationDescriptor;

public class AffiliationDescriptorTest extends SAMLObjectBaseTestCase {

    /** Expected affiliationOwnerID value */
    protected String expectedOwnerID;
    
    /** Expected cacheDuration value in miliseconds */
    protected long expectedCacheDuration;

    /** Expected validUntil value */
    protected GregorianCalendar expectedValidUntil;
    
    /**
     * Constructor
     */
    public AffiliationDescriptorTest(){
        singleElementFile = "/data/org/opensaml/saml2/metadata/impl/AffiliationDescriptor.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/metadata/impl/AffiliationDescriptorOptionalAttributes.xml";
    }
    
    protected void setUp() throws Exception {
        super.setUp();
        
        expectedOwnerID = "urn:example.org";
        expectedCacheDuration = 90000;
        expectedValidUntil = new GregorianCalendar(2005, Calendar.DECEMBER, 7, 10, 21, 0);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        AffiliationDescriptor descriptor = (AffiliationDescriptor) unmarshallElement(singleElementFile);
        
        String ownerId = descriptor.getOwnerID();
        assertEquals("entityID attribute has a value of " + ownerId + ", expected a value of " + expectedOwnerID, expectedOwnerID,
                ownerId);
        
        Long duration = descriptor.getCacheDuration();
        assertNull("cacheDuration attribute has a value of " + duration + ", expected no value", duration);

        GregorianCalendar validUntil = descriptor.getValidUntil();
        assertNull("validUntil attribute has a value of " + validUntil + ", expected no value", validUntil);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        AffiliationDescriptor descriptor = (AffiliationDescriptor) unmarshallElement(singleElementOptionalAttributesFile);
        
        String ownerId = descriptor.getOwnerID();
        assertEquals("entityID attribute has a value of " + ownerId + ", expected a value of " + expectedOwnerID, expectedOwnerID,
                ownerId);

        long duration = descriptor.getCacheDuration().longValue();
        assertEquals("cacheDuration attribute has a value of " + duration + ", expected a value of "
                + expectedCacheDuration, expectedCacheDuration, duration);

        GregorianCalendar validUntil = descriptor.getValidUntil();
        assertEquals("validUntil attribute value did not match expected value", 0, expectedValidUntil
                .compareTo(validUntil));
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementMarshall()
     */
    public void testSingleElementMarshall() {
        AffiliationDescriptor descriptor = (AffiliationDescriptor) buildSAMLObject(AffiliationDescriptor.QNAME);
        
        descriptor.setOwnerID(expectedOwnerID);
        
        assertEquals(expectedDOM, descriptor);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        AffiliationDescriptor descriptor = (AffiliationDescriptor) buildSAMLObject(AffiliationDescriptor.QNAME);
        
        descriptor.setOwnerID(expectedOwnerID);
        descriptor.setValidUntil(expectedValidUntil);
        descriptor.setCacheDuration(expectedCacheDuration);
        
        assertEquals(expectedOptionalAttributesDOM, descriptor);
    }
}