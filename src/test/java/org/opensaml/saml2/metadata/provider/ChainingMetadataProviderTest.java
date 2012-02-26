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

import java.io.File;
import java.net.URL;
import java.util.List;

import org.opensaml.common.BaseTestCase;
import org.opensaml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;

public class ChainingMetadataProviderTest extends BaseTestCase {

    private ChainingMetadataProvider metadataProvider;

    private String entityID;

    private String entityID2;

    private String supportedProtocol;

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();

        entityID = "urn:mace:incommon:washington.edu";
        entityID2 = "urn:mace:switch.ch:SWITCHaai:ethz.ch";
        supportedProtocol = "urn:oasis:names:tc:SAML:1.1:protocol";

        metadataProvider = new ChainingMetadataProvider();

        URL mdURL = FilesystemMetadataProviderTest.class
                .getResource("/data/org/opensaml/saml2/metadata/InCommon-metadata.xml");
        File mdFile = new File(mdURL.toURI());
        FilesystemMetadataProvider fileProvider = new FilesystemMetadataProvider(mdFile);
        fileProvider.setParserPool(parser);
        fileProvider.initialize();
        metadataProvider.addMetadataProvider(fileProvider);

        URL mdURL2 = FilesystemMetadataProviderTest.class
                .getResource("/data/org/opensaml/saml2/metadata/metadata.switchaai_signed.xml");
        File mdFile2 = new File(mdURL2.toURI());
        FilesystemMetadataProvider fileProvider2 = new FilesystemMetadataProvider(mdFile2);
        fileProvider2.setParserPool(parser);
        fileProvider2.initialize();
        metadataProvider.addMetadataProvider(fileProvider2);
    }

    /** Test the {@link ChainingMetadataProvider#getMetadata()} method. */
    public void testGetMetadata() throws MetadataProviderException {
        EntitiesDescriptor descriptor1 = (EntitiesDescriptor) metadataProvider.getMetadata();
        assertEquals(2, descriptor1.getEntitiesDescriptors().size());
        assertEquals(0, descriptor1.getEntityDescriptors().size());

        EntitiesDescriptor descriptor2 = (EntitiesDescriptor) metadataProvider.getMetadata();
        assertEquals(2, descriptor2.getEntitiesDescriptors().size());
        assertEquals(0, descriptor2.getEntityDescriptors().size());
    }

    /** Tests the {@link ChainingMetadataProvider#getEntityDescriptor(String)} method. */
    public void testGetEntityDescriptor() throws MetadataProviderException {
        EntityDescriptor descriptor = metadataProvider.getEntityDescriptor(entityID);
        assertNotNull("Retrieved entity descriptor was null", descriptor);
        assertEquals("Entity's ID does not match requested ID", entityID, descriptor.getEntityID());

        EntityDescriptor descriptor2 = metadataProvider.getEntityDescriptor(entityID2);
        assertNotNull("Retrieved entity descriptor was null", descriptor2);
        assertEquals("Entity's ID does not match requested ID", entityID2, descriptor2.getEntityID());
    }

    /** Tests the {@link ChainingMetadataProvider#getRole(String, javax.xml.namespace.QName)} method.  */
    public void testGetRole() throws MetadataProviderException {
        List<RoleDescriptor> roles = metadataProvider.getRole(entityID, IDPSSODescriptor.DEFAULT_ELEMENT_NAME);
        assertNotNull("Roles for entity descriptor was null", roles);
        assertEquals("Unexpected number of roles", 1, roles.size());

        List<RoleDescriptor> roles2 = metadataProvider.getRole(entityID2, IDPSSODescriptor.DEFAULT_ELEMENT_NAME);
        assertNotNull("Roles for entity descriptor was null", roles2);
        assertEquals("Unexpected number of roles", 1, roles2.size());
    }

    /** Test the {@link ChainingMetadataProvider#getRole(String, javax.xml.namespace.QName, String)} method.  */
    public void testGetRoleWithSupportedProtocol() throws MetadataProviderException {
        RoleDescriptor role = metadataProvider.getRole(entityID, IDPSSODescriptor.DEFAULT_ELEMENT_NAME,
                supportedProtocol);
        assertNotNull("Roles for entity descriptor was null", role);

        RoleDescriptor role2 = metadataProvider.getRole(entityID2, IDPSSODescriptor.DEFAULT_ELEMENT_NAME,
                supportedProtocol);
        assertNotNull("Roles for entity descriptor was null", role2);
    }

    /** Tests that metadata filters are disallowed on the chaining provider. */
    public void testFilterDisallowed() {
        try {
            metadataProvider.setMetadataFilter(new SchemaValidationFilter(new String[] {}));
            fail("Should fail with an UnsupportedOperationException");
        } catch (MetadataProviderException e) {
            fail("Should fail with an UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected, do nothing
        }
    }

}