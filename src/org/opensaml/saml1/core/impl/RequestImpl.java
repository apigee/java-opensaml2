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

import org.opensaml.saml1.core.AssertionArtifact;
import org.opensaml.saml1.core.AssertionIDReference;
import org.opensaml.saml1.core.AttributeQuery;
import org.opensaml.saml1.core.AuthenticationQuery;
import org.opensaml.saml1.core.AuthorizationDecisionQuery;
import org.opensaml.saml1.core.Query;
import org.opensaml.saml1.core.Request;
import org.opensaml.saml1.core.SubjectQuery;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Concrete implementation of {@link org.opensaml.saml1.core.Request}
 */
public class RequestImpl extends RequestAbstractTypeImpl implements Request {

    /** Saves the query (one of Query, SubjectQuery, AuthenticationQuery, AttributeQuery, AuthorizationDecisionQuery */
    private Query query;

    /** The List of AssertionIDReferences */
    private final XMLObjectChildrenList<AssertionIDReference> assertionIDReferences;

    /** The List of AssertionArtifacts */
    private final XMLObjectChildrenList<AssertionArtifact> assertionArtifacts;

    /**
     * Constructor
     * 
     * @param namespaceURI the namespace the element is in
     * @param elementLocalName the local name of the XML element this Object represents
     * @param namespacePrefix the prefix for the given namespace
     */
    protected RequestImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
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
     * @see org.opensaml.saml1.core.Request#getSubjectQuery()
     */
    public SubjectQuery getSubjectQuery() {
        return (query instanceof SubjectQuery ? (SubjectQuery) query : null);
    }

    /*
     * @see org.opensaml.saml1.core.Request#getAttributeQuery()
     */
    public AttributeQuery getAttributeQuery() {
        return (query instanceof AttributeQuery ? (AttributeQuery) query : null);
    }

    /*
     * @see org.opensaml.saml1.core.Request#getAuthenticationQuery()
     */
    public AuthenticationQuery getAuthenticationQuery() {
        return (query instanceof AuthenticationQuery ? (AuthenticationQuery) query : null);
    }

    /*
     * @see org.opensaml.saml1.core.Request#getAuthorizationDecisionQuery()
     */
    public AuthorizationDecisionQuery getAuthorizationDecisionQuery() {
        return (query instanceof AuthorizationDecisionQuery ? (AuthorizationDecisionQuery) query : null);
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
    public List<XMLObject> getOrderedChildren() {

        List<XMLObject> list = new ArrayList<XMLObject>();

        if (super.getOrderedChildren() != null) {
            list.addAll(super.getOrderedChildren());
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

        if (list.size() == 0) {
            return null;
        }

        return Collections.unmodifiableList(list);
    }
}