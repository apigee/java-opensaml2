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

package org.opensaml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.saml1.core.Action;
import org.opensaml.saml1.core.AuthorizationDecisionStatement;
import org.opensaml.saml1.core.DecisionType;
import org.opensaml.saml1.core.Evidence;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.XMLObjectChildrenList;

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
    protected AuthorizationDecisionStatementImpl() {
        super(AuthorizationDecisionStatement.LOCAL_NAME);

        actions = new XMLObjectChildrenList<Action>(this);
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
        return actions;
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
    public List<XMLObject> getOrderedChildren() {
        List<XMLObject> list = new ArrayList<XMLObject>(actions.size() + 2);

        if (super.getOrderedChildren() != null) {
            list.addAll(super.getOrderedChildren());
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