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

package org.opensaml;

import org.opensaml.common.binding.SAMLArtifactFactory;

/**
 * OpenSAML configuration singleton.
 * 
 * The library must be initialized with a set of configurations prior to usage.  This is often done by invoking
 * {@link DefaultBootstrap#bootstrap()} but may done in any manner so long as all the needed object providers and 
 * artifact factory are created and registered with the configuration.
 */
public class Configuration extends org.opensaml.xml.Configuration {
    
    /** SAML Artifact factory. */
    private static SAMLArtifactFactory artifactFactory;

    /**
     * Gets the artifact factory for the library.
     * 
     * @return artifact factory for the library
     */
    public static SAMLArtifactFactory getArtifactFactory(){
        return artifactFactory;
    }
    
    /**
     * Sets the artifact factory for the library.
     * 
     * @param factory artifact factory for the library
     */
    public static void setArtifactFactory(SAMLArtifactFactory factory){
        artifactFactory = factory;
    }
}