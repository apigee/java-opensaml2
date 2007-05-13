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

package org.opensaml.common.binding.artifact;

import org.joda.time.DateTime;
import org.opensaml.common.SAMLObject;
import org.opensaml.util.ExpiringObject;
import org.opensaml.util.StorageService;

/**
 * Maps an artifact to a SAML message and back again.
 */
public class SAMLArtifactMap {
    
    /** Backing storage service. */
    private StorageService<String, SAMLObject> store;
    
    /** Time to live for Artifacts, in milliseconds. */
    private long artifactLifetime;

    /**
     * Constructor.
     *
     * @param backingStore backing store used to persist the map
     * @param lifetime time to live for persisted artifacts, in milliseconds
     */
    public SAMLArtifactMap(StorageService<String, SAMLObject> backingStore, long lifetime){
        store = backingStore;
        artifactLifetime = lifetime;
    }
    
    /**
     * Maps an SAML message to an artifact.
     * 
     * @param artifact the artifact
     * @param samlMessage the SAML message
     */
    public void put(SAMLArtifact artifact, SAMLObject samlMessage){
        //TODO
        //store.put(context, artifact.toString(), samlMessage, new DateTime().plusSeconds(artifactTTL));
    }
    
    /**
     * Gets the SAML message for the given artifact.
     * 
     * @param artifact the artifact to retrive the SAML message for
     * 
     * @return the SAML message or null if the artifact has already expired or did not exist
     */
    public SAMLObject get(SAMLArtifact artifact){
        return null;
        //TODO
        //return store.get(context, artifact.toString());
    }
}