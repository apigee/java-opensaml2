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
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.opensaml.common.BaseTestCase;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;

import com.google.common.io.Files;

public class FilesystemMetadataProviderTest extends BaseTestCase {

    private FilesystemMetadataProvider metadataProvider;
    
    private File mdFile;

    private String entityID;

    private String supportedProtocol;

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();

        entityID = "urn:mace:incommon:washington.edu";
        supportedProtocol = "urn:oasis:names:tc:SAML:1.1:protocol";

        URL mdURL = FilesystemMetadataProviderTest.class
                .getResource("/data/org/opensaml/saml2/metadata/InCommon-metadata.xml");
        mdFile = new File(mdURL.toURI());

        metadataProvider = new FilesystemMetadataProvider(mdFile);
        metadataProvider.setParserPool(parser);
        metadataProvider.initialize();
    }

    /**
     * Tests the {@link HTTPMetadataProvider#getEntityDescriptor(String)} method.
     */
    public void testGetEntityDescriptor() throws MetadataProviderException {
        EntityDescriptor descriptor = metadataProvider.getEntityDescriptor(entityID);
        assertNotNull("Retrieved entity descriptor was null", descriptor);
        assertEquals("Entity's ID does not match requested ID", entityID, descriptor.getEntityID());
    }

    /**
     * Tests the {@link HTTPMetadataProvider#getRole(String, javax.xml.namespace.QName)} method.
     */
    public void testGetRole() throws MetadataProviderException {
        List<RoleDescriptor> roles = metadataProvider.getRole(entityID, IDPSSODescriptor.DEFAULT_ELEMENT_NAME);
        assertNotNull("Roles for entity descriptor was null", roles);
        assertEquals("Unexpected number of roles", 1, roles.size());
    }

    /**
     * Test the {@link HTTPMetadataProvider#getRole(String, javax.xml.namespace.QName, String)} method.
     */
    public void testGetRoleWithSupportedProtocol() throws MetadataProviderException {
        RoleDescriptor role = metadataProvider.getRole(entityID, IDPSSODescriptor.DEFAULT_ELEMENT_NAME,
                supportedProtocol);
        assertNotNull("Roles for entity descriptor was null", role);
    }
    
    /**
     * Tests failure mode of an invalid metadata file that does not exist.
     */
    public void testNonexistentMetadataFile() {
        try {
            metadataProvider = new FilesystemMetadataProvider(new File("I-Dont-Exist.xml"));
            metadataProvider.setParserPool(parser);
            metadataProvider.initialize();
            fail("Specified metadata file does not exist");
        } catch (MetadataProviderException e) {
            // expected, do nothing
        }
    }
    
    /**
     * Tests failure mode of an invalid metadata file that is actually a directory.
     * 
     * @throws IOException 
     */
    public void testInvalidMetadataFile() throws IOException {
        File targetFile = new File(System.getProperty("java.io.tmpdir"), "filesystem-md-provider-test");
        if (targetFile.exists()) {
            assertTrue(targetFile.delete());
        }
        assertTrue(targetFile.mkdir());
        assertTrue(targetFile.exists());
        assertTrue(targetFile.isDirectory());
        
        try {
            metadataProvider = new FilesystemMetadataProvider(targetFile);
            metadataProvider.setParserPool(parser);
            metadataProvider.initialize();
            fail("Specified metadata file is actually a directory");
        } catch (MetadataProviderException e) {
            // expected, do nothing
        } finally {
            targetFile.delete();
        }
    }
    
    /**
     * Tests failure mode of an invalid metadata file that is unreadable.
     * 
     * @throws IOException 
     */
    public void testUnreadableMetadataFile() throws IOException {
        File targetFile = File.createTempFile("filesystem-md-provider-test", "xml");
        assertTrue(targetFile.exists());
        assertTrue(targetFile.isFile());
        assertTrue(targetFile.canRead());
        
        targetFile.setReadable(false);
        assertFalse(targetFile.canRead());
        
        try {
            metadataProvider = new FilesystemMetadataProvider(targetFile);
            metadataProvider.setParserPool(parser);
            metadataProvider.initialize();
            fail("Specified metadata file is unreadable");
        } catch (MetadataProviderException e) {
            // expected, do nothing
        } finally {
            targetFile.delete();
        }
    }
    
    /**
     * Tests failure mode of a metadata file which disappears after initial creation of the provider.
     * 
     * @throws IOException 
     */
    public void testDisappearingMetadataFile() throws IOException {
        File targetFile = new File(System.getProperty("java.io.tmpdir"), "filesystem-md-provider-test.xml");
        if (targetFile.exists()) {
            assertTrue(targetFile.delete());
        }
        Files.copy(mdFile, targetFile);
        assertTrue(targetFile.exists());
        assertTrue(targetFile.canRead());
        
        try {
            metadataProvider = new FilesystemMetadataProvider(targetFile);
            metadataProvider.setParserPool(parser);
            metadataProvider.initialize();
        } catch (MetadataProviderException e) {
            fail("Filesystem metadata provider init failed with file: " + targetFile.getAbsolutePath());
        }
        
        assertTrue(targetFile.delete());
        
        try {
            metadataProvider.refresh();
            fail("Specified metadata file was removed after creation");
        } catch (MetadataProviderException e) {
            // expected, do nothing
        }
    }
    
    /**
     * Tests failfast init of false, with graceful recovery when file later appears.
     * 
     * @throws IOException 
     * @throws InterruptedException 
     */
    public void testRecoveryFromNoFailFast() throws IOException, InterruptedException {
        File targetFile = new File(System.getProperty("java.io.tmpdir"), "filesystem-md-provider-test.xml");
        if (targetFile.exists()) {
            assertTrue(targetFile.delete());
        }
        
        try {
            metadataProvider = new FilesystemMetadataProvider(targetFile);
            metadataProvider.setFailFastInitialization(false);
            metadataProvider.setParserPool(parser);
            metadataProvider.initialize();
        } catch (MetadataProviderException e) {
            fail("Filesystem metadata provider init failed with non-existent file and fail fast = false");
        }
        
        // Test that things don't blow up when initialized, no fail fast, but have no data.
        try {
            assertNull(metadataProvider.getMetadata());
            EntityDescriptor entity = metadataProvider.getEntityDescriptor(entityID);
            assertNull("Retrieved entity descriptor was not null", entity); 
        } catch (MetadataProviderException e) {
            fail("Metadata provider failed non-gracefully when initialized with fail fast = false");
        }
        
        // Filesystem timestamp may only have 1-second precision, so need to sleep for a couple of seconds just 
        // to make sure that the new copied file's timestamp is later than the Jodatime lastRefresh time
        // in the metadata provider.
        //Thread.sleep(2000);
        
        Files.copy(mdFile, targetFile);
        assertTrue(targetFile.exists());
        assertTrue(targetFile.canRead());
        
        try {
            metadataProvider.refresh();
            assertNotNull(metadataProvider.getMetadata());
            EntityDescriptor descriptor = metadataProvider.getEntityDescriptor(entityID);
            assertNotNull("Retrieved entity descriptor was null", descriptor);
        } catch (MetadataProviderException e) {
            fail("Filesystem metadata provider refresh failed recovery from initial init failure");
        }
    }
    
}