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

import org.opensaml.common.BaseTestCase;

/**
 * Test case for {@link FileBackedHTTPMetadataProvider}.
 */
public class FileBackedHTTPMetadataProviderTest extends BaseTestCase {

    private String mdUrl;

    private String badMDURL;

    private String backupFilePath;

    private FileBackedHTTPMetadataProvider metadataProvider;

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();

        mdUrl = "https://svn.shibboleth.net/java-opensaml2"
                + "/branches/REL_2/src/test/resources/data/org/opensaml/saml2/metadata/ukfederation-metadata.xml";
        badMDURL = "http://www.google.com/";
        backupFilePath = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") 
                + "filebacked-http-metadata.xml";
    }

    /** {@inheritDoc} */
    protected void tearDown() {
        File backupFile = new File(backupFilePath);
        backupFile.delete();
    }

    /**
     * Tests the {@link HTTPMetadataProvider#getMetadata()} method.
     */
    public void testGetMetadata() throws MetadataProviderException {
        metadataProvider = new FileBackedHTTPMetadataProvider(mdUrl, 1000 * 5, backupFilePath);
        metadataProvider.setParserPool(parser);
        metadataProvider.initialize();
        
        assertNotNull("Retrieved metadata was null", metadataProvider.getMetadata());

        File backupFile = new File(backupFilePath);
        assertTrue("Backup file was not created", backupFile.exists());
        assertTrue("Backup file contains no data", backupFile.length() > 0);
    }
    
    /**
     * Test fail-fast = true with known bad metadata URL.
     */
    public void testFailFastBadURL() throws MetadataProviderException {
        metadataProvider = new FileBackedHTTPMetadataProvider(badMDURL, 1000 * 5, backupFilePath);
        
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
        metadataProvider = new FileBackedHTTPMetadataProvider(badMDURL, 1000 * 5, backupFilePath);
        
        metadataProvider.setFailFastInitialization(false);
        metadataProvider.setParserPool(parser);
        
        try {
            metadataProvider.initialize();
        } catch (MetadataProviderException e) {
            fail("Provider failed init with fail-fast=false");
        }
    }
    
    /**
     *  Test fail-fast = true and bad backup file
     */
    public void testFailFastBadBackupFile() {
        try {
            // Use a known existing directory as backup file path, which is an invalid argument.
            metadataProvider = new FileBackedHTTPMetadataProvider(mdUrl, 1000 * 5, System.getProperty("java.io.tmpdir"));
        } catch (MetadataProviderException e) {
            fail("Provider failed bad backup file in constructor");
            
        }
        metadataProvider.setFailFastInitialization(true);
        metadataProvider.setParserPool(parser);
        
        try {
            metadataProvider.initialize();
            fail("Provider passed init with bad backup file, fail-fast=true");
        } catch (MetadataProviderException e) {
            // expected do nothing
        }
    }
    
    /**
     *  Test case of fail-fast = false and bad backup file
     * @throws MetadataProviderException 
     */
    public void testNoFailFastBadBackupFile() throws MetadataProviderException {
        try {
            // Use a known existing directory as backup file path, which is an invalid argument.
            metadataProvider = new FileBackedHTTPMetadataProvider(mdUrl, 1000 * 5, System.getProperty("java.io.tmpdir"));
        } catch (MetadataProviderException e) {
            fail("Provider failed bad backup file in constructor");
            
        }
        metadataProvider.setFailFastInitialization(false);
        metadataProvider.setParserPool(parser);
        
        try {
            metadataProvider.initialize();
        } catch (MetadataProviderException e) {
            fail("Provider failed init with bad backup file, fail-fast=false");
        }
        
        assertNotNull("Metadata retrieved from backing file was null", metadataProvider.getMetadata());
    }
    
    /**
     * Tests use of backup file on simulated restart.
     * 
     * @throws MetadataProviderException
     */
    public void testBackupFileOnRestart() throws MetadataProviderException {
        // Do a setup here to get a good backup file
        metadataProvider = new FileBackedHTTPMetadataProvider(mdUrl, 1000 * 5, backupFilePath);
        metadataProvider.setParserPool(parser);
        metadataProvider.initialize();
        
        assertNotNull("Retrieved metadata was null", metadataProvider.getMetadata());

        File backupFile = new File(backupFilePath);
        assertTrue("Backup file was not created", backupFile.exists());
        assertTrue("Backup file contains no data", backupFile.length() > 0);
        
        // Now do a new provider to simulate a restart (have to set fail-fast=false).
        // Verify that can use the data from backing file.
        FileBackedHTTPMetadataProvider badProvider = new FileBackedHTTPMetadataProvider(badMDURL, 1000 * 5,
                backupFilePath);
        badProvider.setParserPool(parser);
        badProvider.setFailFastInitialization(false);
        badProvider.initialize();
        
        assertNotNull("Metadata retrieved from backing file was null", metadataProvider.getMetadata());
    }
}