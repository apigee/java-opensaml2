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
import org.opensaml.saml2.metadata.EntitiesDescriptor;

/**
 * Test case for creating, marshalling, and unmarshalling
 * {@link org.opensaml.saml2.metadata.impl.EntitiesDescriptorImpl}.
 */
public class EntitiesDescriptorTest extends SAMLObjectBaseTestCase {

    /** Expected Name attribute value */
    protected String expectedName;

    /** Expected cacheDuration value in miliseconds */
    protected long expectedCacheDuration;

    /** Expected validUntil value */
    protected DateTime expectedValidUntil;

    /** Expected number of child EntitiesDescriptors */
    protected int expectedEntitiesDescriptorsCount;
    
    /** Expected number of child EntityDescriptors */
    protected int expectedEntityDescriptorsCount;
    
    /**
     * Constructor
     */
    public EntitiesDescriptorTest() {
        singleElementFile = "/data/org/opensaml/saml2/metadata/impl/EntitiesDescriptor.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/metadata/impl/EntitiesDescriptorOptionalAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml2/metadata/impl/EntitiesDescriptorChildElements.xml";
    }

    /*
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        expectedName = "eDescName";
        expectedCacheDuration = 90000;
        expectedValidUntil = new DateTime(2005, 12, 7, 10, 21, 0, 0, ISOChronology.getInstanceUTC());
        expectedEntitiesDescriptorsCount = 3;
        expectedEntityDescriptorsCount = 2;
    }

    /*
     * @see org.opensaml.common.BaseTestCase#testSingleElementUnmarshall()
     */
    @Override
    public void testSingleElementUnmarshall() {
        EntitiesDescriptor entitiesDescriptorObj = (EntitiesDescriptor) unmarshallElement(singleElementFile);

        String name = entitiesDescriptorObj.getName();
        assertNull("Name attribute has a value of " + name + ", expected no value", name);

        Long duration = entitiesDescriptorObj.getCacheDuration();
        assertNull("cacheDuration attribute has a value of " + duration + ", expected no value", duration);

        DateTime validUntil = entitiesDescriptorObj.getValidUntil();
        assertNull("validUntil attribute has a value of " + validUntil + ", expected no value", validUntil);
    }

    /*
     * @see org.opensaml.common.BaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesUnmarshall() {
        EntitiesDescriptor entitiesDescriptorObj = (EntitiesDescriptor) unmarshallElement(singleElementOptionalAttributesFile);

        String name = entitiesDescriptorObj.getName();
        assertEquals("Name attribute has a value of " + name + ", expected a value of " + expectedName, expectedName,
                name);

        long duration = entitiesDescriptorObj.getCacheDuration().longValue();
        assertEquals("cacheDuration attribute has a value of " + duration + ", expected a value of "
                + expectedCacheDuration, expectedCacheDuration, duration);

        DateTime validUntil = entitiesDescriptorObj.getValidUntil();
        assertEquals("validUntil attribute value did not match expected value", 0, expectedValidUntil
                .compareTo(validUntil));
    }
    
    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    @Override
    public void testChildElementsUnmarshall()
    {
        EntitiesDescriptor entitiesDescriptor = (EntitiesDescriptor) unmarshallElement(childElementsFile);
        
        // TODO Extensions..
        
        assertNotNull("Extensions", entitiesDescriptor.getExtensions());
        assertEquals("Entities Descriptor child elements", expectedEntitiesDescriptorsCount, entitiesDescriptor.getEntitiesDescriptors().size());
        assertEquals("Entity Descriptor child elements", expectedEntityDescriptorsCount, entitiesDescriptor.getEntityDescriptors().size());
    }
    
    /*
     * @see org.opensaml.common.BaseTestCase#testSingleElementMarshall()
     */
    @Override
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, EntitiesDescriptor.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        EntitiesDescriptor entitiesDescriptor = (EntitiesDescriptor) buildSAMLObject(qname);

        assertEquals(expectedDOM, entitiesDescriptor);
    }

    /*
     * @see org.opensaml.common.BaseTestCase#testSingleElementOptionalAttributesMarshall()
     */
    @Override
    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, EntitiesDescriptor.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        EntitiesDescriptor entitiesDescriptor = (EntitiesDescriptor) buildSAMLObject(qname);

        entitiesDescriptor.setName(expectedName);
        entitiesDescriptor.setCacheDuration(new Long(expectedCacheDuration));
        entitiesDescriptor.setValidUntil(expectedValidUntil);

        assertEquals(expectedOptionalAttributesDOM, entitiesDescriptor);
    }

    @Override
    public void testChildElementsMarshall(){
        QName qname = new QName(SAMLConstants.SAML20MD_NS, EntitiesDescriptor.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        EntitiesDescriptor entitiesDescriptor = (EntitiesDescriptor) buildSAMLObject(qname);

        entitiesDescriptor.setExtensions(new ExtensionsImpl());
        entitiesDescriptor.getEntitiesDescriptors().add(new EntitiesDescriptorImpl());
        entitiesDescriptor.getEntityDescriptors().add(new EntityDescriptorImpl());
        entitiesDescriptor.getEntitiesDescriptors().add(new EntitiesDescriptorImpl());
        entitiesDescriptor.getEntityDescriptors().add(new EntityDescriptorImpl());
        entitiesDescriptor.getEntitiesDescriptors().add(new EntitiesDescriptorImpl());
        assertEquals(expectedChildElementsDOM, entitiesDescriptor);
    }
}