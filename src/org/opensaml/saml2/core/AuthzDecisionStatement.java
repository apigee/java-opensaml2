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

package org.opensaml.saml2.core;

import java.util.List;

/**
 * SAML 2.0 Core AuthzDecisionStatement
 */
public interface AuthzDecisionStatement extends Statement {
    
    /** Element local name */
    public final static String LOCAL_NAME = "AuthzDecisionStatement";
    
    /** Resource attribute name */
    public final static String RESOURCE_ATTRIB_NAME = "Resource";
    
    /** Decision attribute name */
    public final static String DECISION_ATTRIB_NAME = "Decision";

    /**
     * Get URI of the resource to which authorization is saught.
     * 
     * @return URI of the resource to which authorization is saught
     */
    public String getResource();

    /**
     * Sets URI of the resource to which authorization is saught.
     * 
     * @param newResourceURI URI of the resource to which authorization is saught
     */
    public void setResource(String newResourceURI);

    /**
     * Gets the decision of the authorization request.
     * 
     * @return the decision of the authorization request
     */
    public DecisionType getDecision();

    /**
     * Sets the decision of the authorization request.
     * 
     * @param newDescision the decision of the authorization request
     */
    public void setDecision(DecisionType newDecision);

    /**
     * Gets the actions authorized to be performed.
     * 
     * @return the actions authorized to be performed
     */
    public List<Action> getActions();

    /**
     * Get the SAML assertion the authority relied on when making the authorization decision.
     * 
     * @return the SAML assertion the authority relied on when making the authorization decision
     */
    public Evidence getEvidence();

    /**
     * Sets the SAML assertion the authority relied on when making the authorization decision.
     * 
     * @param newEvidence the SAML assertion the authority relied on when making the authorization decision
     */
    public void setEvidence(Evidence newEvidence);
}