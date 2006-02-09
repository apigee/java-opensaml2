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
 * SAML 2.0 Core Scoping
 */
public interface Scoping extends SAMLObject {

    /** Element Local Name */
    public final static String LOCAL_NAME = "Scoping";

    /** ProxyCount attribute name */
    public final static String PROXY_COUNT_ATTRIB_NAME = "ProxyCount";

    /**
     * Gets the ProxyCount attrib value
     * 
     * @return the ProxyCount attrib value
     */
    public Integer getProxyCount();

    /**
     * Sets the ProxyCount attrib value
     * 
     * @param newProxyCount the new ProxyCount attrib value
     */
    public void setProxyCount(Integer newProxyCount);

    /**
     * Gets the IDPList 
     * 
     * @return IDPList
     */
    public IDPList getIDPList();

    /**
     * Sets the IDPList
     * 
     * @param newIDPList the new IDPList
     */
    public void setIDPList(IDPList newIDPList);
    
    /**
     * Gets the list of RequesterID's
     * 
     * @return list of RequesterID's
     */
    public List<RequesterID> getRequesterIDs();
}
