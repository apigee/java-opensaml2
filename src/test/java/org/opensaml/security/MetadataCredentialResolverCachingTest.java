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

package org.opensaml.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.xml.namespace.QName;

import org.opensaml.common.BaseTestCase;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml2.metadata.provider.FilesystemMetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.opensaml.xml.security.criteria.UsageCriteria;

/**
 * Testing various cases of caching by the metadata credential resolver.
 * See in particular Jira issue SIDP-229.
 */
public class MetadataCredentialResolverCachingTest extends BaseTestCase {
    
    private String protocolFoo = "PROTOCOL_FOO";
    
    private QName spRole = SPSSODescriptor.DEFAULT_ELEMENT_NAME;
    
    private String spEntityID = "http://sp.example.org/";
    
    private MetadataCredentialResolver mdResolver;
    
    private EntityIDCriteria entityCriteria;
    
    private MetadataCriteria mdCriteria;
    
    private CriteriaSet criteriaSet;
    
    private String mdFileNameCacheTestUnspecified = "/data/org/opensaml/security/cachetest-metadata-unspecified.xml";
    private String mdFileNameCacheTestSigning = "/data/org/opensaml/security/cachetest-metadata-signing.xml";
    private String mdFileNameCacheTestEncryption = "/data/org/opensaml/security/cachetest-metadata-encryption.xml";
    
    private File mdFileUnspec;
    private File mdFileEncryption;
    private File mdFile;
    
    private boolean simulateWorkaround;
    

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        
        entityCriteria = new EntityIDCriteria(spEntityID);
        mdCriteria = new MetadataCriteria(spRole, protocolFoo);
        
        criteriaSet = new CriteriaSet();
        criteriaSet.add(entityCriteria);
        criteriaSet.add(mdCriteria);
        
        mdFileUnspec = new File(this.getClass().getResource(mdFileNameCacheTestUnspecified).toURI());
        new File(this.getClass().getResource(mdFileNameCacheTestSigning).toURI());
        mdFileEncryption = new File(this.getClass().getResource(mdFileNameCacheTestEncryption).toURI());
        
        mdFile = new File( System.getProperty("java.io.tmpdir"), "cachetest-metadata-temp.xml");
        if (mdFile.exists()) {
            mdFile.delete();
        }
        assertFalse(mdFile.exists());
        
        simulateWorkaround = false;
    }
   
    /** {@inheritDoc} */
    protected void tearDown() throws Exception {
        super.tearDown();
        
        if (mdFile.exists()) {
            mdFile.delete();
        }
    }
    
    /**
     * Test caching of resolution of a signing key, when the metadata key descriptor 'use' attrib
     * changes from not present (use is unspecified) to 'encryption'.
     * 
     * @throws IOException
     * @throws MetadataProviderException
     * @throws SecurityException
     * @throws InterruptedException
     */
    public void testSigning_UnspecToEncryption() 
            throws IOException, MetadataProviderException, SecurityException, InterruptedException {
        
        copyFile(mdFileUnspec, mdFile);
        assertTrue(mdFile.exists());
        
        FilesystemMetadataProvider fsProvider = new FilesystemMetadataProvider(mdFile);
        fsProvider.setParserPool(parser);
        fsProvider.setMinRefreshDelay(1);
        fsProvider.setMaxRefreshDelay(2000);
        fsProvider.initialize();
        
        mdResolver = new MetadataCredentialResolver(fsProvider);
        criteriaSet.add( new UsageCriteria(UsageType.SIGNING) );
        
        Credential cred = null;
        
        cred = mdResolver.resolveSingle(criteriaSet);
        assertNotNull("Initial query", cred);
        
        Thread.sleep(1000);
        cred = mdResolver.resolveSingle(criteriaSet);
        assertNotNull("Cached query", cred);
        
        Thread.sleep(1000);
        copyFile(mdFileEncryption, mdFile);
        
        if (simulateWorkaround) {
            Thread.sleep(1000);
            //fsProvider.getEntityDescriptor("foo");
            fsProvider.getMetadata();
        }
    }
    
    /**
     * Test caching of resolution of a signing key, when the metadata key descriptor 'use' attrib
     * changes from 'encryption' to not present (use is unspecified).
     * 
     * @throws IOException
     * @throws MetadataProviderException
     * @throws SecurityException
     * @throws InterruptedException
     */
    public void testSigning_EncryptionToUnspec() 
            throws IOException, MetadataProviderException, SecurityException, InterruptedException {
        
        copyFile(mdFileEncryption, mdFile);
        assertTrue(mdFile.exists());
        
        FilesystemMetadataProvider fsProvider = new FilesystemMetadataProvider(mdFile);
        fsProvider.setParserPool(parser);
        fsProvider.setMinRefreshDelay(1);
        fsProvider.setMaxRefreshDelay(2000);
        fsProvider.initialize();
        
        mdResolver = new MetadataCredentialResolver(fsProvider);
        criteriaSet.add( new UsageCriteria(UsageType.SIGNING) );
        
        Credential cred = null;
        
        cred = mdResolver.resolveSingle(criteriaSet);
        assertNull("Initial query", cred);
        
        Thread.sleep(1000);
        cred = mdResolver.resolveSingle(criteriaSet);
        assertNull("Cached query", cred);
        
        Thread.sleep(1000);
        copyFile(mdFileUnspec, mdFile);
        
        if (simulateWorkaround) {
            Thread.sleep(1000);
            //fsProvider.getEntityDescriptor("foo");
            fsProvider.getMetadata();
        }
    }
    
    private void copyFile(File in, File out) throws IOException {
        if (out.exists()) {
            out.delete();
        }
        FileInputStream fis  = new FileInputStream(in);
        FileOutputStream fos = new FileOutputStream(out);
        byte[] buf = new byte[1024];
        int i = 0;
        while((i=fis.read(buf))!=-1) {
            fos.write(buf, 0, i);
        }
        fis.close();
        fos.close();
        out.setLastModified( new Date().getTime() );
    }
    
}
