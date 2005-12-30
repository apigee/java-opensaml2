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
import java.util.TimeZone;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.EntityDescriptor;

/**
 * Test case for creating, marshalling, and unmarshalling {@link org.opensaml.saml2.metadata.impl.EntityDescriptorImpl}.
 */
public class EntityDescriptorTest extends SAMLObjectBaseTestCase {

    /** Expected entityID value */
    protected String expectedEntityID;
    
    /** Expected cacheDuration value in miliseconds */
    protected long expectedCacheDuration;

    /** Expected validUntil value */
    protected GregorianCalendar expectedValidUntil;
    
    /**
     * Constructor
     */
    public EntityDescriptorTest() {
        singleElementFile = "/data/org/opensaml/saml2/metadata/impl/EntityDescriptor.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/metadata/impl/EntityDescriptorOptionalAttributes.xml";
    }

    /*
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        expectedEntityID = "99ff33";
        expectedCacheDuration = 90000;
        expectedValidUntil = new GregorianCalendar(2005, Calendar.DECEMBER, 7, 10, 21, 0);
        expectedValidUntil.setTimeZone(TimeZone.getTimeZone("Universal"));
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        EntityDescriptor descriptor = (EntityDescriptor) unmarshallElement(singleElementFile);
        
        String entityID = descriptor.getEntityID();
        assertEquals("entityID attribute has a value of " + entityID + ", expected a value of " + expectedEntityID, expectedEntityID,
                entityID);
        
        Long duration = descriptor.getCacheDuration();
        assertNull("cacheDuration attribute has a value of " + duration + ", expected no value", duration);

        GregorianCalendar validUntil = descriptor.getValidUntil();
        assertNull("validUntil attribute has a value of " + validUntil + ", expected no value", validUntil);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        EntityDescriptor descriptor = (EntityDescriptor) unmarshallElement(singleElementOptionalAttributesFile);
        
        String entityID = descriptor.getEntityID();
        assertEquals("entityID attribute has a value of " + entityID + ", expected a value of " + expectedEntityID, expectedEntityID,
                entityID);

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
        QName qname = new QName(SAMLConstants.SAML20MD_NS, EntityDescriptor.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        EntityDescriptor descriptor = (EntityDescriptor) buildSAMLObject(qname);
        
        descriptor.setEntityID(expectedEntityID);
        
        assertEquals(expectedDOM, descriptor);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, EntityDescriptor.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        EntityDescriptor descriptor = (EntityDescriptor) buildSAMLObject(qname);
        
        descriptor.setEntityID(expectedEntityID);
        descriptor.setValidUntil(expectedValidUntil);
        descriptor.setCacheDuration(expectedCacheDuration);
        
        assertEquals(expectedOptionalAttributesDOM, descriptor);
    }
}