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

package org.opensaml.saml2.metadata.provider;

import java.util.List;


import org.opensaml.common.BaseTestCase;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;

/**
 * Unit tests for {@link HTTPMetadataProvider}.
 */
public class HTTPMetadataProviderTest extends BaseTestCase {

    private String inCommonMDURL;
    private String badMDURL;
    private String entitiesDescriptorName;
    private String entityID;
    private String supportedProtocol;
    private HTTPMetadataProvider metadataProvider;
    
    /**{@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        
        inCommonMDURL = "https://svn.shibboleth.net/java-opensaml2"
                + "/branches/REL_2/src/test/resources/data/org/opensaml/saml2/metadata/InCommon-metadata.xml";
        badMDURL = "http://www.google.com/";
        entitiesDescriptorName = "urn:mace:incommon";
        entityID = "urn:mace:incommon:washington.edu";
        supportedProtocol ="urn:oasis:names:tc:SAML:1.1:protocol";
        metadataProvider = new HTTPMetadataProvider(inCommonMDURL, 1000 * 5);
        metadataProvider.setParserPool(parser);
        metadataProvider.initialize();
    }
    
    /**
     * Tests the {@link HTTPMetadataProvider#getMetadata()} method.
     */
    public void testGetMetadata() throws MetadataProviderException {
        EntitiesDescriptor descriptor = (EntitiesDescriptor) metadataProvider.getMetadata();
        assertNotNull("Retrieved metadata was null", descriptor);
        assertEquals("EntitiesDescriptor name was not expected value", entitiesDescriptorName, descriptor.getName());
    }
    
    /**
     * Tests the {@link HTTPMetadataProvider#getEntitiesDescriptor(String)} method.
     */
    public void testGetEntitiesDescriptor() throws MetadataProviderException{
        EntitiesDescriptor descriptor = (EntitiesDescriptor) metadataProvider.getEntitiesDescriptor(entitiesDescriptorName);
        assertNotNull("Retrieved metadata was null", descriptor);
        assertEquals("EntitiesDescriptor name was not expected value", entitiesDescriptorName, descriptor.getName());
    }
    
    /**
     * Tests the {@link HTTPMetadataProvider#getEntityDescriptor(String)} method.
     */
    public void testGetEntityDescriptor() throws MetadataProviderException{
        EntityDescriptor descriptor = metadataProvider.getEntityDescriptor(entityID);
        assertNotNull("Retrieved entity descriptor was null", descriptor);
        assertEquals("Entity's ID does not match requested ID", entityID, descriptor.getEntityID());
    }
    
    /**
     * Tests the {@link HTTPMetadataProvider#getRole(String, javax.xml.namespace.QName)} method.
     */
    public void testGetRole() throws MetadataProviderException{
        List<RoleDescriptor> roles = metadataProvider.getRole(entityID, IDPSSODescriptor.DEFAULT_ELEMENT_NAME);
        assertNotNull("Roles for entity descriptor was null", roles);
        assertEquals("Unexpected number of roles", 1, roles.size());
    }
    
    /**
     * Test the {@link HTTPMetadataProvider#getRole(String, javax.xml.namespace.QName, String)} method.
     */
    public void testGetRoleWithSupportedProtocol() throws MetadataProviderException{
        RoleDescriptor role = metadataProvider.getRole(entityID, IDPSSODescriptor.DEFAULT_ELEMENT_NAME, supportedProtocol);
        assertNotNull("Roles for entity descriptor was null", role);
    }
    
    /**
     * Test fail-fast = true with known bad metadata URL.
     */
    public void testFailFastBadURL() throws MetadataProviderException {
        metadataProvider = new HTTPMetadataProvider(badMDURL, 1000 * 5);
        
        metadataProvider.setFailFastInitialization(true);
        metadataProvider.setParserPool(parser);
        
        try {
            metadataProvider.initialize();
            fail("metadata provider claims to have parsed known invalid data");
        } catch (MetadataProviderException e) {
            //expected, do nothing
        }
    }
    
    /**
     * Test fail-fast = false with known bad metadata URL.
     */
    public void testNoFailFastBadURL() throws MetadataProviderException {
        metadataProvider = new HTTPMetadataProvider(badMDURL, 1000 * 5);
        
        metadataProvider.setFailFastInitialization(false);
        metadataProvider.setParserPool(parser);
        
        try {
            metadataProvider.initialize();
        } catch (MetadataProviderException e) {
            fail("Provider failed init with fail-fast=false");
        }
    }
    
}