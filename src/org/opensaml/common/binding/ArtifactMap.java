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

package org.opensaml.common.binding;

import org.opensaml.common.SAMLObject;
import org.opensaml.xml.util.StorageService;

/**
 * Maps an artifact to a SAML message and back again.
 */
public class ArtifactMap {
    
    /** Backing storage service */
    private StorageService<String, String> store;
    
    /** Storage context for the persisted artifacts */
    private String context;
    
    /** Time to live for Artifacts */
    private long artifactTTL; 

    /**
     * Constructor
     *
     * @param backingStore backing store used to persist the map
     * @param storageContext the context to use with the backing store
     * @param artifactTTL time to live for persisted artifacts
     */
    public ArtifactMap(StorageService<String, String> backingStore, String storageContext, long artifactTTL){
        store = backingStore;
        context = storageContext;
        this.artifactTTL = artifactTTL;
    }
    
    /**
     * Maps an SAML message to an artifact.
     * 
     * @param artifact the artifact
     * @param samlMessage the SAML message
     */
    public void put(SAMLArtifact artifact, SAMLObject samlMessage){
        // TODO
    }
    
    /**
     * Gets the SAML message for the given artifact.
     * 
     * @param artifact the artifact to retrive the SAML message for
     * 
     * @return the SAML message or null if the artifact has already expired or did not exist
     */
    public SAMLObject get(SAMLArtifact artifact){
        // TODO
        return null;
    }
}