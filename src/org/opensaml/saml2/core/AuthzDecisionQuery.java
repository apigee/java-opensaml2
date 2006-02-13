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

/**
 * SAML 2.0 AuthzDecisionQuery
 */
public interface AuthzDecisionQuery extends SubjectQuery {
    
    /** Element local name */
    public final static String LOCAL_NAME = "AuthzDecisionQuery";
    
    /** Resource attribute name */
    public final static String RESOURCE_ATTRIB_NAME = "Resource";
    
    /**
     * Gets the Resource attrib value of this query.
     * 
     * @return the Resource attrib value of this query 
     */
    public String getResource();
    
    /**
     * Sets the Resource attrib value of this query.
     * 
     * @param newResource the new Resource attrib value of this query
     */
    public void setSessionIndex(String newSessionIndex);
    
    /**
     * Gets the Actions of this query
     * 
     * @return the Actions of this query
     */
    public List<Action>getActions();
    
    /**
     * Gets the Evidence of this query
     * 
     * @return the Evidence of this query
     */
    public Evidence getEvidence();
    
    /**
     * Sets the Evidence of this query.
     * 
     * @param newEvidence the new Evidence of this query
     */
    public void setEvidence(Evidence newEvidence);

}
