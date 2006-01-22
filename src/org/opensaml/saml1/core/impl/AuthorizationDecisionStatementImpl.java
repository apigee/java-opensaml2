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
package org.opensaml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Action;
import org.opensaml.saml1.core.AuthorizationDecisionStatement;
import org.opensaml.saml1.core.DecisionType;
import org.opensaml.saml1.core.Evidence;

/**
 * A concrete implementation of {@link org.opensaml.saml1.core.AuthorizationDecisionStatement} 
 */
public class AuthorizationDecisionStatementImpl extends SubjectStatementImpl implements AuthorizationDecisionStatement {

    /** Contains the Resource attribute */
    private String resource;
    
    /** Contains the Decision attribute */
    private DecisionType decision;
    
    /** Contains the list of Action elements */
    private final List<Action> actions;
    
    /** Contains the (single) Evidence element */
    private Evidence evidence;
    
    /**
     * Constructor
     */
    public AuthorizationDecisionStatementImpl() {
        super(SAMLConstants.SAML1_NS, AuthorizationDecisionStatement.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML1_PREFIX);
        actions = new ArrayList<Action>();
    }

    /*
     * @see org.opensaml.saml1.core.AuthorizationDecisionStatement#getResource()
     */
    public String getResource() {
        return resource;
    }

    /*
     * @see org.opensaml.saml1.core.AuthorizationDecisionStatement#setResource(java.lang.String)
     */
    public void setResource(String resource) {
        this.resource = prepareForAssignment(this.resource, resource);
    }

    /*
     * @see org.opensaml.saml1.core.AuthorizationDecisionStatement#getDecision()
     */
    public DecisionType getDecision() {
        return decision;
    }

    /*
     * @see org.opensaml.saml1.core.AuthorizationDecisionStatement#setDecision(org.opensaml.saml1.core.DecisionType)
     */
    public void setDecision(DecisionType decision) {
        this.decision = prepareForAssignment(this.decision, decision);
    }

    /*
     * @see org.opensaml.saml1.core.AuthorizationDecisionStatement#getActions()
     */
    public List<Action> getActions() {
        if (actions.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(actions);
    }

    /*
     * @see org.opensaml.saml1.core.AuthorizationDecisionStatement#addAction(org.opensaml.saml1.core.Action)
     */
    public void addAction(Action action) throws IllegalArgumentException {
        addXMLObject(actions, action);
    }

    /*
     * @see org.opensaml.saml1.core.AuthorizationDecisionStatement#removeAction(org.opensaml.saml1.core.Action)
     */
    public void removeAction(Action action) {
        removeXMLObject(actions, action);
    }

    /*
     * @see org.opensaml.saml1.core.AuthorizationDecisionStatement#removeActions(java.util.Collection)
     */
    public void removeActions(Collection<Action> actions) {
        if (actions == null) {
            return;
        }
        removeXMLObjects(this.actions, actions);
    }

    /*
     * @see org.opensaml.saml1.core.AuthorizationDecisionStatement#removeallActions()
     */
    public void removeallActions() {
        // TODO add removeallActions
    }

    /*
     * @see org.opensaml.saml1.core.AuthorizationDecisionStatement#getEvidence()
     */
    public Evidence getEvidence() {
        return evidence;
    }

    /*
     * @see org.opensaml.saml1.core.AuthorizationDecisionStatement#setEvidence(org.opensaml.saml1.core.Evidence)
     */
    public void setEvidence(Evidence evidence) throws IllegalArgumentException {
        this.evidence = prepareForAssignment(this.evidence, evidence);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        List<SAMLObject> list = new ArrayList<SAMLObject>(actions.size() + 2);
        
        if (getSubject() != null) {
            list.add(getSubject());
        }
        list.addAll(actions);
        if (evidence != null) {
            list.add(evidence);
        }
        if (list.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(list);
    }
}
