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

package org.opensaml.saml2.metadata;

import org.opensaml.common.SAMLObject;

/**
 * SAML 2.0 Metadata ServiceDescription
 */
public interface ServiceDescription extends SAMLObject {

    /** Element local name */
    public final static String LOCAL_NAME = "ServiceDescription";
    
    /**
     * Gets the description of the service.
     * 
     * @return the description of the service
     */
    public LocalizedString getDescription();
    
    /**
     * Sets the description of the service.
     * 
     * @param newDescription the description of the service
     */
    public void setDescription(LocalizedString newDescription);
}
