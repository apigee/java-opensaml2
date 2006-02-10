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
import org.opensaml.saml2.metadata.IDPSSODescriptor;

/**
 *
 */
public class IDPSSODescriptorTest extends SAMLObjectBaseTestCase {

    /** List of expected supported protocols */
    protected ArrayList<String> expectedSupportedProtocol;
    
    /** Expected cacheDuration value in miliseconds */
    protected long expectedCacheDuration;

    /** Expected validUntil value */
    protected DateTime expectedValidUntil;
    
    /** Expected error url */
    protected String expectedErrorURL;

    /** expected value for WantAuthnRequestSigned attribute */
    protected Boolean expectedWantAuthnReqSigned;
    
    /**
     * Constructor
     */
    public IDPSSODescriptorTest(){
        singleElementFile = "/data/org/opensaml/saml2/metadata/impl/IDPSSODescriptor.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/metadata/impl/IDPSSODescriptorOptionalAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml2/metadata/impl/IDPSSODescriptorChildElements.xml";
    }
    
    protected void setUp() throws Exception {
        super.setUp();
        
        expectedSupportedProtocol = new ArrayList<String>();
        expectedSupportedProtocol.add("urn:foo:bar");
        expectedSupportedProtocol.add("urn:fooz:baz");
        
        expectedCacheDuration = 90000;
        expectedValidUntil = new DateTime(2005, 12, 7, 10, 21, 0, 0, ISOChronology.getInstanceUTC());
        
        expectedErrorURL = "http://example.org";
        
        expectedWantAuthnReqSigned = Boolean.TRUE;
    }

    public void testSingleElementUnmarshall() {
        IDPSSODescriptor descriptor = (IDPSSODescriptor) unmarshallElement(singleElementFile);
        
        assertEquals("Supported protocols not equal to expected value", expectedSupportedProtocol, descriptor.getSupportedProtocols());
    }

    public void testSingleElementOptionalAttributesUnmarshall() {
        IDPSSODescriptor descriptor = (IDPSSODescriptor) unmarshallElement(singleElementOptionalAttributesFile);
        
        assertEquals("Cache duration was not expected value", expectedCacheDuration, descriptor.getCacheDuration().longValue());
        assertEquals("ValidUntil was not expected value", expectedValidUntil, descriptor.getValidUntil());
        assertEquals("WantAuthnRequestsSigned attribute was not expected value", expectedWantAuthnReqSigned, descriptor.wantAuthnRequestsSigned());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    public void testChildElementsUnmarshall()
    {
        IDPSSODescriptor descriptor = (IDPSSODescriptor) unmarshallElement(childElementsFile);

        // TODO Extensions
        // TODO KeyDescriptor
        // TODO Attributes
        assertNotNull("Organization child", descriptor.getOrganization());
        assertEquals("ContactPerson count", 2, descriptor.getContactPersons().size());

        assertEquals("ArtifactResolutionService count", 1, descriptor.getArtifactResolutionServices().size());
        assertEquals("SingleLogoutService count", 2, descriptor.getSingleLogoutServices().size());
        assertEquals("ManageNameIDService count", 4, descriptor.getManageNameIDServices().size());
        assertEquals("NameIDFormat count", 1, descriptor.getNameIDFormats().size());
        
        assertEquals("SingleSignOnService count", 3, descriptor.getSingleSignOnServices().size());
        assertEquals("NameIDMappingService count", 2, descriptor.getNameIDMappingServices().size());
        assertEquals("AssertionIDRequestService count", 3, descriptor.getAssertionIDRequestServices().size());
        assertEquals("AttributeProfile count", 3, descriptor.getAttributeProfiles().size());
    }
    
    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, IDPSSODescriptor.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        IDPSSODescriptor descriptor = (IDPSSODescriptor) buildSAMLObject(qname);
        
        for(String protocol : expectedSupportedProtocol){
            descriptor.addSupportedProtocol(protocol);
        }
        descriptor.setWantAuthnRequestSigned(expectedWantAuthnReqSigned);
        
        assertEquals(expectedDOM, descriptor);
    }

    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, IDPSSODescriptor.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        IDPSSODescriptor descriptor = (IDPSSODescriptor) buildSAMLObject(qname);
        
        for(String protocol : expectedSupportedProtocol){
            descriptor.addSupportedProtocol(protocol);
        }
        
        descriptor.setCacheDuration(expectedCacheDuration);
        descriptor.setValidUntil(expectedValidUntil);
        descriptor.setErrorURL(expectedErrorURL);
        descriptor.setWantAuthnRequestSigned(expectedWantAuthnReqSigned);
        
        assertEquals(expectedOptionalAttributesDOM, descriptor);
    }
    
    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    public void testChildElementsMarshall()
    {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, IDPSSODescriptor.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        IDPSSODescriptor descriptor = (IDPSSODescriptor) buildSAMLObject(qname);
   
        // TODO Extensions
        // TODO KeyDescriptor
        // TODO Attributes

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
        for (int i = 0; i < 3; i ++) {
            descriptor.getSingleSignOnServices().add(new SingleSignOnServiceImpl());
        }
        for (int i = 0; i < 2; i ++) {
            descriptor.getNameIDMappingServices().add(new NameIDMappingServiceImpl());
        }
        for (int i = 0; i < 3; i ++) {
            descriptor.getAssertionIDRequestServices().add(new AssertionIDRequestServiceImpl());
        }
        for (int i = 0; i < 3; i ++) {
            descriptor.getAttributeProfiles().add(new AttributeProfileImpl());
        }
        assertEquals(expectedChildElementsDOM, descriptor);   
    }

}