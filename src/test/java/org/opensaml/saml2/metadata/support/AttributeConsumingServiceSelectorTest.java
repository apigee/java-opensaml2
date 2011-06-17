/*
 * Licensed to the University Corporation for Advanced Internet Development, 
 * Inc. (UCAID) under one or more contributor license agreements.  See the 
 * NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The UCAID licenses this file to You under the Apache 
 * License, Version 2.0 (the "License"); you may not use this file except in 
 * compliance with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.saml2.metadata.support;

import java.io.File;
import java.net.URL;

import org.opensaml.common.BaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.AttributeConsumingService;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml2.metadata.provider.FilesystemMetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.samlext.saml2mdquery.AttributeQueryDescriptorType;

/**
 * Tests of AttributeConsumingServiceSelector.
 */
public class AttributeConsumingServiceSelectorTest extends BaseTestCase {
    
    private String mdFileName;
    
    private FilesystemMetadataProvider mdProvider;
    
    private AttributeConsumingServiceSelector acsSelector;

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        
        mdFileName = "/data/org/opensaml/saml2/metadata/support/metadata-AttributeConsumingService.xml";
        
        URL mdURL = AttributeConsumingServiceSelectorTest.class.getResource(mdFileName);
        File mdFile = new File(mdURL.toURI());
        
        mdProvider = new FilesystemMetadataProvider(mdFile);
        mdProvider.setParserPool(parser);
        mdProvider.initialize();
        
        acsSelector = new AttributeConsumingServiceSelector();
    }
    
    // Success cases
    
    /**
     * Test valid index.
     * @throws MetadataProviderException
     */
    public void testWithValidIndex() throws MetadataProviderException {
        RoleDescriptor role =  mdProvider.getRole("urn:test:entity:A", SPSSODescriptor.DEFAULT_ELEMENT_NAME,
                SAMLConstants.SAML20P_NS);
        assertNotNull(role);
        acsSelector.setRoleDescriptor(role);
        
        acsSelector.setIndex(1);
        
        AttributeConsumingService acs = acsSelector.selectService();
        assertNotNull(acs);
        
        assertEquals("Wrong service selected", "A-SP-1", getName(acs));
    }
    
    /**
     * Test explicit isDefault="true".
     * @throws MetadataProviderException 
     * @throws MetadataProviderException
     */
    public void testExplicitDefault() throws MetadataProviderException {
        RoleDescriptor role =  mdProvider.getRole("urn:test:entity:A", SPSSODescriptor.DEFAULT_ELEMENT_NAME,
                SAMLConstants.SAML20P_NS);
        assertNotNull(role);
        acsSelector.setRoleDescriptor(role);
        
        AttributeConsumingService acs = acsSelector.selectService();
        assertNotNull(acs);
        
        assertEquals("Wrong service selected", "A-SP-0", getName(acs));
    }
    
    /**
     * Test default as first missing default.
     * @throws MetadataProviderException
     */
    public void testFirstMissingDefault() throws MetadataProviderException {
        RoleDescriptor role =  mdProvider.getRole("urn:test:entity:B", SPSSODescriptor.DEFAULT_ELEMENT_NAME,
                SAMLConstants.SAML20P_NS);
        assertNotNull(role);
        acsSelector.setRoleDescriptor(role);
        
        AttributeConsumingService acs = acsSelector.selectService();
        assertNotNull(acs);
        
        assertEquals("Wrong service selected", "B-SP-2", getName(acs));
    }
    
    /**
     * Test default as first isDefault="false".
     * @throws MetadataProviderException
     */
    public void testFirstFalseDefault() throws MetadataProviderException {
        RoleDescriptor role =  mdProvider.getRole("urn:test:entity:C", SPSSODescriptor.DEFAULT_ELEMENT_NAME,
                SAMLConstants.SAML20P_NS);
        assertNotNull(role);
        acsSelector.setRoleDescriptor(role);
        
        AttributeConsumingService acs = acsSelector.selectService();
        assertNotNull(acs);
        
        assertEquals("Wrong service selected", "C-SP-0", getName(acs));
    }
    
    /**
     * Test AttributeQueryDescriptorType.
     * @throws MetadataProviderException
     */
    public void testAttributeQueryType() throws MetadataProviderException {
        RoleDescriptor role =  mdProvider.getRole("urn:test:entity:A", AttributeQueryDescriptorType.TYPE_NAME,
                SAMLConstants.SAML20P_NS);
        assertNotNull(role);
        acsSelector.setRoleDescriptor(role);
        
        acsSelector.setIndex(0);
        
        AttributeConsumingService acs = acsSelector.selectService();
        assertNotNull(acs);
        
        assertEquals("Wrong service selected", "A-AQ-0", getName(acs));
    }
    
    /**
     * Test invalid index.
     * @throws MetadataProviderException
     */
    public void testInvalidIndex() throws MetadataProviderException {
        RoleDescriptor role =  mdProvider.getRole("urn:test:entity:A", SPSSODescriptor.DEFAULT_ELEMENT_NAME,
                SAMLConstants.SAML20P_NS);
        assertNotNull(role);
        acsSelector.setRoleDescriptor(role);
        
        acsSelector.setIndex(3);
        
        AttributeConsumingService acs = acsSelector.selectService();
        assertNull("Service should have been null due to invalid index", acs);
    }
    
    /**
     * Test invalid index with onBadIndexUseDefault of true.
     * @throws MetadataProviderException
     */
    public void testInvalidIndexWithUseDefault() throws MetadataProviderException {
        RoleDescriptor role =  mdProvider.getRole("urn:test:entity:A", SPSSODescriptor.DEFAULT_ELEMENT_NAME,
                SAMLConstants.SAML20P_NS);
        assertNotNull(role);
        acsSelector.setRoleDescriptor(role);
        
        acsSelector.setIndex(3);
        acsSelector.setOnBadIndexUseDefault(true);
        
        AttributeConsumingService acs = acsSelector.selectService();
        assertNotNull(acs);
        
        assertEquals("Wrong service selected", "A-SP-0", getName(acs));
    }
    
    /**
     * Test missing RoleDescriptor input.
     * @throws MetadataProviderException
     */
    public void testNoRoleDescriptor() throws MetadataProviderException {
        AttributeConsumingService acs = acsSelector.selectService();
        assertNull("Service should have been null due to lack of role descriptor", acs);
    }
    
    
    /////////////////////////////////
    
    /**
     * Get  the first service name of an AttributeConsumingService.
     * 
     * @param acs the attribute consuming service
     * @return the first name of the service
     */
    private String getName(AttributeConsumingService acs) {
        return acs.getNames().get(0).getName().getLocalString();
    }

}
