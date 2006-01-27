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
import java.util.Collections;
import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Action;
import org.opensaml.saml1.core.AuthorizationDecisionQuery;
import org.opensaml.saml1.core.Evidence;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of the {@link org.opensaml.saml1.core.AuthorizationDecisionQuery} interface
 */
public class AuthorizationDecisionQueryImpl extends SubjectQueryImpl implements AuthorizationDecisionQuery {

    /** Contains the resource attribute */
    private String resource;
    
    /** Contains all the Action child elements*/ 
    private final List<Action> actions;
    
    /** Contains the Evidence child element */
    private Evidence evidence;
    
    /**
     * Constructor
     */
    public AuthorizationDecisionQueryImpl() {
        super(SAMLConstants.SAML1P_NS, AuthorizationDecisionQuery.LOCAL_NAME);
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

    public List<Action> getActions() {
        return actions;
    }

    public Evidence getEvidence() {
        return evidence;
    }

    public void setEvidence(Evidence evidence) {
        this.evidence = prepareForAssignment(this.evidence, evidence);
    }

    public List<SAMLObject> getOrderedChildren() {
        List<SAMLObject> list = new ArrayList<SAMLObject>(actions.size()+2);
        if (getSubject() != null) {
            list.add(getSubject());
        }
        list.addAll(actions);
        if (evidence != null) {
            list.add(evidence);
        }
        return Collections.unmodifiableList(list);
        
    }

}