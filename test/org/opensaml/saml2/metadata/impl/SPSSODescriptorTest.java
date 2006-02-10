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

import java.util.ArrayList;

import javax.xml.namespace.QName;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.SPSSODescriptor;

/**
 *
 */
public class SPSSODescriptorTest extends SAMLObjectBaseTestCase {

    /** expected value for AuthnRequestSigned attribute */
    protected Boolean expectedAuthnRequestSigned;
    
    /** expected value for WantAssertionsSigned attribute */
    protected Boolean expectedWantAssertionsSigned;
    
    /** List of expected supported protocols */
    protected ArrayList<String> expectedSupportedProtocol;
    
    /** Expected cacheDuration value in miliseconds */
    protected long expectedCacheDuration;

    /** Expected validUntil value */
    protected DateTime expectedValidUntil;
    
    /**
     * Constructor
     */
    public SPSSODescriptorTest(){
        singleElementFile = "/data/org/opensaml/saml2/metadata/impl/SPSSODescriptor.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/metadata/impl/SPSSODescriptorOptionalAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml2/metadata/impl/SPSSODescriptorChildElements.xml";
    }
    
    protected void setUp() throws Exception {
        super.setUp();
        
        expectedAuthnRequestSigned = Boolean.TRUE;
        expectedWantAssertionsSigned = Boolean.TRUE;
        
        expectedSupportedProtocol = new ArrayList<String>();
        expectedSupportedProtocol.add("urn:foo:bar");
        expectedSupportedProtocol.add("urn:fooz:baz");
        
        expectedCacheDuration = 90000;
        expectedValidUntil = new DateTime(2005, 12, 7, 10, 21, 0, 0, ISOChronology.getInstanceUTC());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementUnmarshall()
     */
    public void testSingleElementUnmarshall() {
        SPSSODescriptor descriptor = (SPSSODescriptor) unmarshallElement(singleElementFile);
        
        assertEquals("Supported protocols not equal to expected value", expectedSupportedProtocol, descriptor.getSupportedProtocols());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testSingleElementOptionalAttributesUnmarshall()
     */
    public void testSingleElementOptionalAttributesUnmarshall() {
        SPSSODescriptor descriptor = (SPSSODescriptor) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertEquals("Supported protocols not equal to expected value", expectedSupportedProtocol, descriptor.getSupportedProtocols());
        assertEquals("AuthnRequestsSigned attribute was not expected value", expectedAuthnRequestSigned.booleanValue(), descriptor.authnRequestsSigned());
        assertEquals("WantAssertionsSigned attribute was not expected value", expectedWantAssertionsSigned.booleanValue(), descriptor.wantAssertionsSigned());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    public void testChildElementsUnmarshall()
    {
        SPSSODescriptor descriptor = (SPSSODescriptor) unmarshallElement(childElementsFile);

        // TODO Extensions
        // TODO KeyDescriptor
        assertNotNull("Organization child", descriptor.getOrganization());
        assertEquals("ContactPerson count", 2, descriptor.getContactPersons().size());

        assertEquals("ArtifactResolutionService count", 1, descriptor.getArtifactResolutionServices().size());
        assertEquals("SingleLogoutService count", 2, descriptor.getSingleLogoutServices().size());
        assertEquals("ManageNameIDService count", 4, descriptor.getManageNameIDServices().size());
        assertEquals("NameIDFormat count", 1, descriptor.getNameIDFormats().size());
        
        assertEquals("AssertionConsumerService count", 2, descriptor.getAssertionConsumerServices().size());
        assertEquals("AttributeConsumingService", 1, descriptor.getAttributeConsumingServices().size());
    }

    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, SPSSODescriptor.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        SPSSODescriptor descriptor = (SPSSODescriptor) buildSAMLObject(qname);
        
        for(String protocol : expectedSupportedProtocol){
            descriptor.addSupportedProtocol(protocol);
        }
        
        assertEquals(expectedDOM, descriptor);
    }

    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, SPSSODescriptor.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        SPSSODescriptor descriptor = (SPSSODescriptor) buildSAMLObject(qname);
        
        descriptor.setAuthnRequestsSigned(expectedAuthnRequestSigned);
        descriptor.setWantAssertionsSigned(expectedWantAssertionsSigned);
        
        for(String protocol : expectedSupportedProtocol){
            descriptor.addSupportedProtocol(protocol);
        }
        
        descriptor.setCacheDuration(expectedCacheDuration);
        descriptor.setValidUntil(expectedValidUntil);
        
        assertEquals(expectedOptionalAttributesDOM, descriptor);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    public void testChildElementsMarshall()
    {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, SPSSODescriptor.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        SPSSODescriptor descriptor = (SPSSODescriptor) buildSAMLObject(qname);
   
        // TODO Extensions
        // TODO KeyDescriptor

        descriptor.setOrganization(new OrganizationImpl());
        for (int i = 0; i < 2; i ++) {
            descriptor.getContactPersons().add(new ContactPersonImpl());
        }
        descriptor.getArtifactResolutionServices().add(new ArtifactResolutionServiceImpl());
       for (int i = 0; i < 2; i ++) {
            descriptor.getSingleLogoutServices().add(new SingleLogoutServiceImpl());
        }
        for (int i = 0; i < 4; i ++) {
            descriptor.getManageNameIDServices().add(new ManageNameIDServiceImpl());
        }
        descriptor.getNameIDFormats().add(new NameIDFormatImpl());
        for (int i = 0; i < 2; i++) {
            descriptor.getAssertionConsumerServices().add(new AssertionConsumerServiceImpl());
        }
        descriptor.getAttributeConsumingServices().add(new AttributeConsumingServiceImpl());
        assertEquals(expectedChildElementsDOM, descriptor);
    }
}