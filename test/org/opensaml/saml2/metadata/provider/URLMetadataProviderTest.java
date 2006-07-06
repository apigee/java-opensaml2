/*
 * Copyright [2006] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.saml2.metadata.provider;

import java.util.List;

import org.opensaml.common.SAMLObjectTestCaseConfigInitializer;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;

/**
 * Unit tests for {@link URLMetadataProvider}.
 */
public class URLMetadataProviderTest extends SAMLObjectTestCaseConfigInitializer {

    private URLMetadataProvider metadataProvider;
    private String inCommonMDURL;
    private String entityID;
    private String supportedProtocol;
    
    /**{@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        
        inCommonMDURL = "http://wayf.incommonfederation.org/InCommon/InCommon-metadata.xml";
        entityID = "urn:mace:incommon:washington.edu";
        supportedProtocol ="urn:oasis:names:tc:SAML:1.1:protocol";
        
        metadataProvider = new URLMetadataProvider(inCommonMDURL, 1000 * 5);
    }
    
    /**
     * Tests the {@link URLMetadataProvider#getEntityDescriptor(String)} method.
     */
    public void testGetEntityDescriptor(){
        EntityDescriptor descriptor = metadataProvider.getEntityDescriptor(entityID);
        assertNotNull("Retrieved entity descriptor was null", descriptor);
        assertEquals("Entity's ID does not match requested ID", entityID, descriptor.getEntityID());
    }
    
    /**
     * Tests the {@link URLMetadataProvider#getRole(String, javax.xml.namespace.QName) method.
     */
    public void testGetRole(){
        List<RoleDescriptor> roles = metadataProvider.getRole(entityID, IDPSSODescriptor.DEFAULT_ELEMENT_NAME);
        assertNotNull("Roles for entity descriptor was null", roles);
        assertEquals("Unexpected number of roles", 1, roles.size());
    }
    
    /**
     * Test the {@link URLMetadataProvider#getRole(String, javax.xml.namespace.QName, String) method.
     */
    public void testGetRoleWithSupportedProtocol(){
        List<RoleDescriptor> roles = metadataProvider.getRole(entityID, IDPSSODescriptor.DEFAULT_ELEMENT_NAME, supportedProtocol);
        assertNotNull("Roles for entity descriptor was null", roles);
        assertEquals("Unexpected number of roles", 1, roles.size());
    }
}