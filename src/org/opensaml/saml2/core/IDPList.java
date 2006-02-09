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

package org.opensaml.saml2.core;

import java.util.List;

import org.opensaml.common.SAMLObject;

/**
 * SAML 2.0 Core IDPList
 */
public interface IDPList extends SAMLObject {

    /** Element Local Name */
    public final static String LOCAL_NAME = "IDPList";

    /**
     * Gets the IDPEntry list
     * 
     * @return the IDPEntry list
     */
    public List<IDPEntry> getIDPEntrys();

    /**
     * Gets the GetComplete URI
     * 
     * @return GetComplete URI
     */
    public GetComplete getGetComplete();

    /**
     * Sets the GetComplete URI
     * 
     * @param newGetComplete the new GetComplete URI
     */
    public void setGetComplete(GetComplete newGetComplete);
    
}
