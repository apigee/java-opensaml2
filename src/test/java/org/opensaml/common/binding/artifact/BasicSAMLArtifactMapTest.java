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

package org.opensaml.common.binding.artifact;

import org.opensaml.common.BaseTestCase;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.artifact.SAMLArtifactMap.SAMLArtifactMapEntry;
import org.opensaml.util.storage.MapBasedStorageService;
import org.opensaml.util.storage.StorageService;
import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Document;

/**
 *
 */
public class BasicSAMLArtifactMapTest extends BaseTestCase {
    
    private BasicSAMLArtifactMap artifactMap;
    private StorageService<String, SAMLArtifactMapEntry> storageService;
    
    private SAMLObject samlObject;
    
    private String artifact = "the-artifact";
    private String issuerId = "urn:test:issuer";
    private String rpId = "urn:test:rp";
    private long lifetime = 60*60*1000;
    
    private Document origDocument;

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        
        samlObject = (SAMLObject) unmarshallElement("/data/org/opensaml/saml2/core/ResponseSuccessAuthnAttrib.xml");
        origDocument = samlObject.getDOM().getOwnerDocument();
        // Drop the DOM for a more realistic test, usuallly the artifact SAMLObject will be built, not unmarshalled
        samlObject.releaseChildrenDOM(true);
        samlObject.releaseDOM();
        
        storageService = new MapBasedStorageService<String, SAMLArtifactMapEntry>();
        
        artifactMap = new BasicSAMLArtifactMap(storageService, lifetime);
    }
    
    public void testBasicPutGet() throws MarshallingException {
        assertFalse(artifactMap.contains(artifact));
        
        artifactMap.put(artifact, rpId, issuerId, samlObject);
        
        assertTrue(artifactMap.contains(artifact));
        
        SAMLArtifactMapEntry entry = artifactMap.get(artifact);
        assertNotNull(entry);
        
        assertEquals("Invalid value for artifact", artifact, entry.getArtifact());
        assertEquals("Invalid value for issuer ID", issuerId, entry.getIssuerId());
        assertEquals("Invalid value for relying party ID", rpId, entry.getRelyingPartyId());
        
        // Test SAMLObject reconstitution
        SAMLObject retrievedObject = entry.getSamlMessage();
        Document newDocument = 
            marshallerFactory.getMarshaller(retrievedObject).marshall(retrievedObject).getOwnerDocument();
        assertXMLEqual(origDocument, newDocument);
    }
    
    public void testRemove() throws MarshallingException {
        assertFalse(artifactMap.contains(artifact));
        
        artifactMap.put(artifact, rpId, issuerId, samlObject);
        
        assertTrue(artifactMap.contains(artifact));
        
        artifactMap.remove(artifact);
        
        assertFalse(artifactMap.contains(artifact));
        
        SAMLArtifactMapEntry entry = artifactMap.get(artifact);
        assertNull("Entry was removed", entry);
    }
    
    public void testEntryExpiration() throws MarshallingException, InterruptedException {
        // lifetime of 1 second should do it
        artifactMap = new BasicSAMLArtifactMap(storageService, 1000);
        
        assertFalse(artifactMap.contains(artifact));
        
        artifactMap.put(artifact, rpId, issuerId, samlObject);
        
        // Hopefully this doesn't get deferred for more than 1000 milliseconds after the put()...
        assertTrue(artifactMap.contains(artifact));
        
        // Sleep for 3 seconds, entry should expire
        Thread.sleep(3000);
        
        SAMLArtifactMapEntry entry = artifactMap.get(artifact);
        assertNull("Entry should have expired", entry);
    }

}
