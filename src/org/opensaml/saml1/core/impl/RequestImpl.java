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
import org.opensaml.saml1.core.AssertionArtifact;
import org.opensaml.saml1.core.AssertionIDReference;
import org.opensaml.saml1.core.Query;
import org.opensaml.saml1.core.Request;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 *
 */
public class RequestImpl extends RequestAbstractTypeImpl implements org.opensaml.saml1.core.Request {

    /** Saves the query (one of Query, SubjectQuery, AuthenticationQuery, AttributeQuery, AuthorizationDecisionQuery */
    private Query query;
    
    /** The List of AssertionIDReferences */
    private final List<AssertionIDReference> assertionIDReferences;
    
    /** The List of AssertionArtifacts */
    private final List<AssertionArtifact> assertionArtifacts;
    
    /**
     * Constructor
     */
    public RequestImpl() {
        super(SAMLConstants.SAML1P_NS, Request.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML1P_PREFIX);
        assertionIDReferences = new XMLObjectChildrenList<AssertionIDReference>(this);
        assertionArtifacts = new XMLObjectChildrenList<AssertionArtifact>(this);
    }

    /*
     * @see org.opensaml.saml1.core.Request#getQuery()
     */
    public Query getQuery() {
        return query;
    }

    /*
     * @see org.opensaml.saml1.core.Request#setQuery(org.opensaml.saml1.core.Query)
     */
    public void setQuery(Query query) throws IllegalArgumentException {
        this.query = prepareForAssignment(this.query, query);
    }

    /*
     * @see org.opensaml.saml1.core.Request#getAssertionIDReferences()
     */
    public List<AssertionIDReference> getAssertionIDReferences() {
        return assertionIDReferences;
    }

    /*
     * @see org.opensaml.saml1.core.Request#getAssertionArtifacts()
     */
    public List<AssertionArtifact> getAssertionArtifacts() {
        return assertionArtifacts;
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {

        List<SAMLObject> list = new ArrayList<SAMLObject>();
        
        if (getRespondWiths().size() != 0) {
            list.addAll(getRespondWiths());
        }
        if (query != null) {
            list.add(query);
        }
        if (assertionIDReferences.size() != 0) {
            list.addAll(assertionIDReferences);
        }
        if (assertionArtifacts.size() != 0) {
            list.addAll(assertionArtifacts);
        }
        return Collections.unmodifiableList(list);
    }

}
