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
package org.opensaml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Action;
import org.opensaml.saml2.core.AuthzDecisionQuery;
import org.opensaml.saml2.core.Evidence;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of {@link org.opensaml.saml2.core.AuthzDecisionQuery}
 */
public class AuthzDecisionQueryImpl extends SubjectQueryImpl implements AuthzDecisionQuery {
    
    /** Resource attribute value */
    private String resource;
    
    /** Evidence child element */
    private Evidence evidence;
    
    /** Action child elements */
    private XMLObjectChildrenList<Action> actions;
    

    /**
     * Constructor
     *
     */
    public AuthzDecisionQueryImpl() {
        super(SAMLConstants.SAML20P_NS, AuthzDecisionQuery.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20P_PREFIX);

        actions = new XMLObjectChildrenList<Action>(this);
    }

    /**
     * @see org.opensaml.saml2.core.AuthzDecisionQuery#getResource()
     */
    public String getResource() {
        return this.resource;
    }
    
    /**
     * @see org.opensaml.saml2.core.AuthzDecisionQuery#setResource(java.lang.String)
     */
    public void setResource(String newResource) {
        this.resource = prepareForAssignment(this.resource, newResource);
    }

    /**
     * @see org.opensaml.saml2.core.AuthzDecisionQuery#getActions()
     */
    public List<Action> getActions() {
        return actions;
    }

    /**
     * @see org.opensaml.saml2.core.AuthzDecisionQuery#getEvidence()
     */
    public Evidence getEvidence() {
        return this.evidence;
    }

    /**
     * @see org.opensaml.saml2.core.AuthzDecisionQuery#setEvidence(org.opensaml.saml2.core.Evidence)
     */
    public void setEvidence(Evidence newEvidence) {
        this.evidence = prepareForAssignment(this.evidence, newEvidence);
    }

    /**
     * @see org.opensaml.saml2.core.impl.SubjectQueryImpl#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        
        if (super.getOrderedChildren() != null)
            children.addAll(super.getOrderedChildren());
        children.addAll(actions);
        if (evidence != null)
            children.add(evidence);
        
        if (children.size() == 0)
            return null;
        
        return Collections.unmodifiableList(children);
    }
}