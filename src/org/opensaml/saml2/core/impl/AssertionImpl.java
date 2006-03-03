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

import javax.xml.namespace.QName;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Advice;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.AuthzDecisionStatement;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Statement;
import org.opensaml.saml2.core.Subject;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.util.IndexedXMLObjectChildrenList;

/**
 * A concrete implementation of {@link org.opensaml.saml2.core.Assertion}.
 */
public class AssertionImpl extends AbstractSignableAssertionSAMLObject implements Assertion {

    /** Issue Instant of the assertion */
    private DateTime issueInstant;

    /** ID of the assertion */
    private String id;

    /** Issuer of the assertion */
    private Issuer issuer;

    /** Signature of the assertion */
    private Signature signature;

    /** Subject of the assertion */
    private Subject subject;

    /** Conditions of the assertion */
    private Conditions conditions;

    /** Advice of the assertion */
    private Advice advice;

    /** Statements of the assertion */
    private IndexedXMLObjectChildrenList<Statement> statements;

    /** Constructor */
    protected AssertionImpl() {
        super(Assertion.LOCAL_NAME);

        statements = new IndexedXMLObjectChildrenList<Statement>(this);
    }

    /*
     * @see org.opensaml.saml2.core.Assertion#getIssueInstant()
     */
    public DateTime getIssueInstant() {
        return issueInstant;
    }

    /*
     * @see org.opensaml.saml2.core.Assertion#setIssueInstant(org.joda.time.DateTime)
     */
    public void setIssueInstant(DateTime newIssueInstance) {
        if (newIssueInstance != null) {
            this.issueInstant = prepareForAssignment(this.issueInstant, newIssueInstance.withZone(DateTimeZone.UTC));
        } else {
            this.issueInstant = null;
        }

    }

    /*
     * @see org.opensaml.saml2.core.Assertion#getID()
     */
    public String getID() {
        return id;
    }

    /*
     * @see org.opensaml.saml2.core.Assertion#setID(java.lang.String)
     */
    public void setID(String newID) {
        this.id = prepareForAssignment(this.id, newID);
    }

    /*
     * @see org.opensaml.saml2.core.Assertion#getIssuer()
     */
    public Issuer getIssuer() {
        return issuer;
    }

    /*
     * @see org.opensaml.saml2.core.Assertion#setIssuer(org.opensaml.saml2.core.Issuer)
     */
    public void setIssuer(Issuer newIssuer) {
        this.issuer = prepareForAssignment(this.issuer, newIssuer);
    }

    /*
     * @see org.opensaml.xml.signature.SignableXMLObject#getSignature()
     */
    public Signature getSignature() {
        return signature;
    }

    /*
     * @see org.opensaml.xml.signature.SignableXMLObject#setSignature(org.opensaml.xml.signature.Signature)
     */
    public void setSignature(Signature newSignature) {
        this.signature = prepareForAssignment(this.signature, newSignature);
    }

    /*
     * @see org.opensaml.saml2.core.Assertion#getSubject()
     */
    public Subject getSubject() {
        return subject;
    }

    /*
     * @see org.opensaml.saml2.core.Assertion#setSubject(org.opensaml.saml2.core.Subject)
     */
    public void setSubject(Subject newSubject) {
        this.subject = prepareForAssignment(this.subject, newSubject);
    }

    /*
     * @see org.opensaml.saml2.core.Assertion#getConditions()
     */
    public Conditions getConditions() {
        return conditions;
    }

    /*
     * @see org.opensaml.saml2.core.Assertion#setConditions(org.opensaml.saml2.core.Conditions)
     */
    public void setConditions(Conditions newConditions) {
        this.conditions = prepareForAssignment(this.conditions, newConditions);
    }

    /*
     * @see org.opensaml.saml2.core.Assertion#getAdvice()
     */
    public Advice getAdvice() {
        return advice;
    }

    /*
     * @see org.opensaml.saml2.core.Assertion#setAdvice(org.opensaml.saml2.core.Advice)
     */
    public void setAdvice(Advice newAdvice) {
        this.advice = prepareForAssignment(this.advice, newAdvice);
    }

    /*
     * @see org.opensaml.saml2.core.Assertion#getStatements()
     */
    public List<Statement> getStatements() {
        return statements;
    }

    /*
     * @see org.opensaml.saml2.core.Assertion#getAuthnStatements()
     */
    public List<AuthnStatement> getAuthnStatements() {
        QName statementQName = new QName(SAMLConstants.SAML20_NS, AuthnStatement.LOCAL_NAME,
                SAMLConstants.SAML20_PREFIX);
        return (List<AuthnStatement>) statements.subList(statementQName);
    }

    /*
     * @see org.opensaml.saml2.core.Assertion#getAuthzDecisionStatements()
     */
    public List<AuthzDecisionStatement> getAuthzDecisionStatements() {
        QName statementQName = new QName(SAMLConstants.SAML20_NS, AuthzDecisionStatement.LOCAL_NAME,
                SAMLConstants.SAML20_PREFIX);
        return (List<AuthzDecisionStatement>) statements.subList(statementQName);
    }

    /*
     * @see org.opensaml.saml2.core.Assertion#getAttributeStatement()
     */
    public List<AttributeStatement> getAttributeStatement() {
        QName statementQName = new QName(SAMLConstants.SAML20_NS, AttributeStatement.LOCAL_NAME,
                SAMLConstants.SAML20_PREFIX);
        return (List<AttributeStatement>) statements.subList(statementQName);
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

        children.add(issuer);
        children.add(subject);
        children.add(conditions);
        children.add(advice);
        children.addAll(statements);

        return Collections.unmodifiableList(children);
    }
}