/*
 * Copyright [2005] [University Corporation for Advanced Internet Development, Inc.]
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

/**
 * 
 */
package org.opensaml.saml2.core.impl;

import org.opensaml.saml2.core.ArtifactResponse;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.ArtifactResponse}
 */
public class ArtifactResponseImpl extends StatusResponseImpl implements ArtifactResponse {

    /**
     * Constructor
     *
     */
    protected ArtifactResponseImpl() {
        super(ArtifactResponse.LOCAL_NAME);
    }
    
    //TODO need to implement anything for children of type
    // <any namespace=##any">

}
