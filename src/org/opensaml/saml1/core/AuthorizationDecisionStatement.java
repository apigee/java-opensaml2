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

package org.opensaml.saml1.core;

import java.util.Collection;
import java.util.List;

import org.opensaml.common.SAMLObject;

/**
 * This interface defines how the object representing a SAML1 <code> AuthorizationDecisionStatement </code> element
 * behaves.
 */
public interface AuthorizationDecisionStatement extends SAMLObject, SubjectStatement {

    /** Element name, no namespace. */
    public final static String LOCAL_NAME = "AuthorizationDecisionStatement";

    /** Name for Resource attribute */
    public final static String RESOURCE_ATTRIB_NAME = "Resource";
    
    /** Name for Decision attribute */
    public final static String DECISION_ATTRIB_NAME = "Decision";
    
    /** Return the contents of the Resource attribute */
    public String getResource();
    
    /** Set the contents of the Resource attribute */
    public void setResource(String resource);
    
    /** Return the contents of the Decision attribute */
    public DecisionType getDecision();
    
    /** Set the contents of the Decision attribute */
    public void setDecision(DecisionType decision);
    
    /** Get the Action Elements */
    public List<Action> getActions();
    
    /** Add an Action Element 
     * @throws IllegalArgumentException */
    public void addAction(Action action) throws IllegalArgumentException;
    
    /** Remove an Action Element */
    public void removeAction(Action action);
    
    /** Remove several Action elements */
    public void removeActions(Collection<Action> actions);
    
    /** Remove all Action elements */
    public void removeallActions();
    
    /** Return the Evidence element */
    public Evidence getEvidence();
    
    /** Set the Evidence element 
     * @throws IllegalArgumentException */
    public void setEvidence(Evidence evidence) throws IllegalArgumentException;
   
}