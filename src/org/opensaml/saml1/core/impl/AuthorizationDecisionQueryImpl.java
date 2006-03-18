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

import org.opensaml.common.SAMLVersion;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Action;
import org.opensaml.saml1.core.AuthorizationDecisionQuery;
import org.opensaml.saml1.core.Evidence;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of the {@link org.opensaml.saml1.core.AuthorizationDecisionQuery} interface
 */
public class AuthorizationDecisionQueryImpl extends SubjectQueryImpl implements AuthorizationDecisionQuery {

    /** Contains the resource attribute */
    private String resource;

    /** Contains all the Action child elements */
    private final List<Action> actions;

    /** Contains the Evidence child element */
    private Evidence evidence;

    /**
     * Constructor
     * @deprecated
     */
    private AuthorizationDecisionQueryImpl() {
        super(AuthorizationDecisionQuery.LOCAL_NAME, null);
        setElementNamespacePrefix(SAMLConstants.SAML1P_PREFIX);
        actions = null;
    }

    /**
     * Constructor
     *
     * @param version the {@link SAMLVersion} to create
     */
    protected AuthorizationDecisionQueryImpl(SAMLVersion version) {
        super(AuthorizationDecisionQuery.LOCAL_NAME, version);
        setElementNamespacePrefix(SAMLConstants.SAML1P_PREFIX);
        actions = new XMLObjectChildrenList<Action>(this);
    }

    /*
     * @see org.opensaml.saml1.core.AttributeQuery#getResource()
     */
    public String getResource() {
        return resource;
    }

    /*
     * @see org.opensaml.saml1.core.AttributeQuery#setResource(java.lang.String)
     */
    public void setResource(String resource) {
        this.resource = prepareForAssignment(this.resource, resource);
    }

    /*
     * @see org.opensaml.saml1.core.AuthorizationDecisionQuery#getActions()
     */
    public List<Action> getActions() {
        return actions;
    }

    /*
     * @see org.opensaml.saml1.core.AuthorizationDecisionQuery#getEvidence()
     */
    public Evidence getEvidence() {
        return evidence;
    }

    /*
     * @see org.opensaml.saml1.core.AuthorizationDecisionQuery#setEvidence(org.opensaml.saml1.core.Evidence)
     */
    public void setEvidence(Evidence evidence) {
        this.evidence = prepareForAssignment(this.evidence, evidence);
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
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