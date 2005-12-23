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

import java.util.Date;
import java.util.Set;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.util.OrderedSet;
import org.opensaml.common.util.UnmodifiableOrderedSet;
import org.opensaml.saml1.core.Advice;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.AttributeStatement;
import org.opensaml.saml1.core.AuthenticationStatement;
import org.opensaml.saml1.core.AuthorizationDecisionStatement;
import org.opensaml.saml1.core.Conditions;
import org.opensaml.saml1.core.Statement;
import org.opensaml.saml1.core.SubjectStatement;

/**
 * This class implements the SAML 1 <code> Assertion </code> statement. 
 */

public class AssertionImpl extends AbstractSAMLObject implements Assertion {

    /**
     * Automatically generated serialVersionUID
     */
    private static final long serialVersionUID = -7215570300403868068L;
    
    /*
     * The implementation is just a series of getter/setters on the follow members which
     * are either Attributes or SubElements 
     */
    
    /** Object version of the <code> MinorVersion </code> attribute. */
    
    private int minorVersion; 
    
    /* Note that there is no MajorVersion.  We test against this being 1 in the 
     * unmarshaller and allow the  
     */

    /** Object version of the <code> AssertionID </code> attribute. */
    
    private String id;
    
    /** Object version of the <code> Issuer </code> attribute. */
    
    private String issuer;
    
    /** Object version of the <code> IssueInstant </code> attribute. */
    
    private Date issueInstant;

    /** (Possibly null) Singleton object version of the <code> Conditions </code> element. */
    
    private Conditions conditions;
    
    /** (Possibly null) Singleton object version of the <code> Advice </code> element. */
    
    private Advice advice;
    
    /** Object representnation of all the <code> Statement <\code> elements. */
    
    private final OrderedSet<Statement> statements;

    /** Object representation  of all the <code> SubjectStatement <\code> elements. */
        
    private final OrderedSet<SubjectStatement> subjectStatements;

    /** Object representation  of all the <code> AuthenticationStatement <\code> elements. */
    
    private final OrderedSet<AuthenticationStatement> authenticationStatements;

    /** Object representation of all the <code> AuthorizationStatement <\code> elements. */
    
    private final OrderedSet<AuthorizationDecisionStatement> authorizationDecisionStatements;

    /** Object representation  of all the <code> AttributeStatement <\code> elements. */

    private final OrderedSet<AttributeStatement> attributeStatements;
    
    /** 
     * Object representation of all the <code> Subject,
     *  AuthenticationStatement, AuthorizationStatement and AttributeStatement
     *   <\code> elements (in order). */

    private final OrderedSet<SAMLObject> orderedList;
    
    /**
     * Constructor
     */

    public AssertionImpl() {
        
        super();

        //
        // Now intialized all the Sets.
        //
        // TODO, if I read the spec correctly only one of the sets will have members, but 
        // do we want to police it?
        
        statements = new OrderedSet<Statement>();
        subjectStatements = new OrderedSet<SubjectStatement>();
        authenticationStatements = new OrderedSet<AuthenticationStatement>();
        authorizationDecisionStatements = new OrderedSet<AuthorizationDecisionStatement>();
        attributeStatements = new OrderedSet<AttributeStatement>();
        orderedList = new OrderedSet<SAMLObject>();
        
        setQName(Assertion.QNAME);
   }

    /*
     * @see org.opensaml.saml1.core.Assertion#removeSubjectStatements(java.util.Set)
     */
    public int getMinorVersion() {
        
        return minorVersion;
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#removeSubjectStatements(java.util.Set)
     * 
     * Since we know that we are SAML1 we can also set the
     * SAML version if we can
     */
    public void setMinorVersion(int version) {
    
        if (version != minorVersion) {
            releaseThisandParentDOM();
            minorVersion = version;
            if (version == 0) {
                setSAMLVersion(SAMLVersion.VERSION_10);
            } else if (version == 1) {
                setSAMLVersion(SAMLVersion.VERSION_11);
            }
        }
        
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#getId()
     *   
     * get the AssertionID value (from the attribute)
     */
    public String getId() {
        
        return this.id;
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#setId(java.lang.String)
     */
    public void setId(String Id) {

        this.id = prepareForAssignment(this.id, Id);
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#getIssuer()
     */
    public String getIssuer() {

        return this.issuer;
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#setIssuer(java.lang.String)
     */
    public void setIssuer(String issuer) {

        this.issuer = prepareForAssignment(this.issuer, issuer); 
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#getIssueInstant()
     */
    public Date getIssueInstant() {

        return this.issueInstant;
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#setIssueInstant(java.util.Date)
     *
     * There is (as yet) no helper function for
     * Date values so all the logic is in  here.
     */
    public void setIssueInstant(Date issueInstant) {

        if (issueInstant == null && this.issueInstant == null) {
            // no change - return
            return;
        }
        
        if (this.issueInstant == null || !this.issueInstant.equals(issueInstant)) {
            releaseThisandParentDOM();
            this.issueInstant = issueInstant;
        }

    }

    /*
     * @see org.opensaml.saml1.core.Assertion#getConditions()
     */
    public Conditions getConditions() {
   
        return conditions;
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#setConditions(org.opensaml.saml1.core.Conditions)
     */
    public void setConditions(Conditions conditions) throws IllegalAddException {
        
        this.conditions = prepareForAssignment(this.conditions, conditions);
    }


    /*
     * @see org.opensaml.saml1.core.Assertion#getAdvice()
     */
    public Advice getAdvice() {
        
        return advice;
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#setAdvice(org.opensaml.saml1.core.Advice)
     */
    public void setAdvice(Advice advice) throws IllegalAddException {
          
        this.advice = prepareForAssignment(this.advice, advice);
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#getStatements()
     */
    public UnmodifiableOrderedSet<Statement> getStatements() {

        return new UnmodifiableOrderedSet<Statement>(statements);
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#addStatement(org.opensaml.saml1.core.Statement)
     */
    public void addStatement(Statement statement) throws IllegalAddException {

        if (addSAMLObject(this.statements, statement)) {
            orderedList.add(statement);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#removeStatement(org.opensaml.saml1.core.Statement)
     */
    public void removeStatement(Statement statement) {

        if (removeSAMLObject(this.statements, statement)) {
            orderedList.remove(statement);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#removeStatements(java.util.Set)
     */
    public void removeStatements(Set<Statement> statements) {
       
        if (removeSAMLObjects(this.statements, statements)) {
            orderedList.removeAll(statements);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#removeAllStatements()
     */
    public void removeAllStatements() {
        
        for (Statement statement: statements) {
            removeStatement(statement);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#getSubjectStatements()
     */
    public UnmodifiableOrderedSet<SubjectStatement> getSubjectStatements() {

        return new UnmodifiableOrderedSet<SubjectStatement>(this.subjectStatements);
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#addSubjectStatement(org.opensaml.saml1.core.SubjectStatement)
     */
    public void addSubjectStatement(SubjectStatement subjectStatement) throws IllegalAddException {

        if (addSAMLObject(subjectStatements, subjectStatement)) {
            orderedList.add(subjectStatement);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#removeSubjectStatement(org.opensaml.saml1.core.SubjectStatement)
     */
    public void removeSubjectStatement(SubjectStatement subjectStatement) {

        if (removeSAMLObject(subjectStatements, subjectStatement)) {
            orderedList.remove(subjectStatement);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#removeSubjectStatements(java.util.Set)
     */
    public void removeSubjectStatements(Set<SubjectStatement> subjectStatements) {
        for (SubjectStatement subjectStatement: subjectStatements) {
            removeSubjectStatement(subjectStatement);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#removeAllSubjectStatements()
     */
    public void removeAllSubjectStatements() { 
        for (SubjectStatement subjectStatement: this.subjectStatements) {
            removeSubjectStatement(subjectStatement);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#getAuthenticationStatements()
     */
    public UnmodifiableOrderedSet<AuthenticationStatement> getAuthenticationStatements() {

        return new UnmodifiableOrderedSet<AuthenticationStatement>(authenticationStatements);
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#addAuthenticationStatement(org.opensaml.saml1.core.AuthenticationStatement)
     */
    public void addAuthenticationStatement(AuthenticationStatement authenticationStatement) throws IllegalAddException {

        if (addSAMLObject(authenticationStatements, authenticationStatement)) {
            orderedList.add(authenticationStatement);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#removeAuthenticationStatement(org.opensaml.saml1.core.AuthenticationStatement)
     */
    public void removeAuthenticationStatement(AuthenticationStatement authenticationStatement) {

        if (removeSAMLObject(authenticationStatements, authenticationStatement)) {
            orderedList.remove(authenticationStatement);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#removeAuthenticationStatements(java.util.Set)
     */
    public void removeAuthenticationStatements(Set<AuthenticationStatement> authenticationStatements) {

        for (AuthenticationStatement authenticationStatement: authenticationStatements) {
            removeAuthenticationStatement(authenticationStatement);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#removeAllAuthenticationStatements()
     */
    public void removeAllAuthenticationStatements() {

        for (AuthenticationStatement authenticationStatement: this.authenticationStatements) {
            removeAuthenticationStatement(authenticationStatement);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#getAuthorizationDecisionStatements()
     */
    public UnmodifiableOrderedSet<AuthorizationDecisionStatement> getAuthorizationDecisionStatements() {

        return new UnmodifiableOrderedSet<AuthorizationDecisionStatement>(authorizationDecisionStatements);
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#addAuthorizationDecisionStatement(org.opensaml.saml1.core.AuthorizationDecisionStatement)
     */
    public void addAuthorizationDecisionStatement(AuthorizationDecisionStatement authorizationDecisionStatement) throws IllegalAddException {

        if (addSAMLObject(authorizationDecisionStatements, authorizationDecisionStatement)) {
            orderedList.add(authorizationDecisionStatement);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#removeAuthorizationDecisionStatement(org.opensaml.saml1.core.AuthorizationDecisionStatement)
     */
    public void removeAuthorizationDecisionStatement(AuthorizationDecisionStatement authorizationDecisionStatement) {

        if (removeSAMLObject(this.authorizationDecisionStatements, authorizationDecisionStatement)) {
            orderedList.remove(authorizationDecisionStatement);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#removeAuthorizationDecisionStatement(java.util.Set)
     */
    public void removeAuthorizationDecisionStatement(Set<AuthorizationDecisionStatement> authorizationDecisionStatements) {
        
        for (AuthorizationDecisionStatement authorizationDecisionStatement:authorizationDecisionStatements) {
            removeAuthorizationDecisionStatement(authorizationDecisionStatement);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#removeAllAuthorizationDecisionStatements()
     */
    public void removeAllAuthorizationDecisionStatements() {

        for (AuthorizationDecisionStatement authorizationDecisionStatement:this.authorizationDecisionStatements) {
            removeAuthorizationDecisionStatement(authorizationDecisionStatement);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#getAttributeStatements()
     */
    public UnmodifiableOrderedSet<AttributeStatement> getAttributeStatements() {

        return new UnmodifiableOrderedSet<AttributeStatement>(attributeStatements);
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#addStatement(org.opensaml.saml1.core.AttributeStatement)
     */
    public void addAttributeStatement(AttributeStatement attributeStatement) throws IllegalAddException {

        if (addSAMLObject(this.attributeStatements, attributeStatement)) {
            orderedList.add(attributeStatement);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#removeStatement(org.opensaml.saml1.core.AttributeStatement)
     */
    public void removeAttributeStatement(AttributeStatement attributeStatement) {

        if (removeSAMLObject(this.attributeStatements, attributeStatement)) {
            orderedList.remove(attributeStatement);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#removeAttributeStatements(java.util.Set)
     */
    public void removeAttributeStatements(Set<AttributeStatement> attributeStatements) {

        for (AttributeStatement attributeStatement: attributeStatements) {
            removeAttributeStatement(attributeStatement);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#removeAllAttributeStatements()
     */
    public void removeAllAttributeStatements() {

        for (AttributeStatement attributeStatement: this.attributeStatements) {
            removeAttributeStatement(attributeStatement);
        }
    }

    /*
     * @see org.opensaml.common.SAMLObject#equals(org.opensaml.common.SAMLObject)
     */
    public boolean equals(SAMLObject element) {

        if (element instanceof Assertion) {
            Assertion other= (Assertion) element;
            
            // TODO What is the definition to two equivalent assertions?
            
            return id.equals(other.getId());
        }
        return false;
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public UnmodifiableOrderedSet<SAMLObject> getOrderedChildren() {

        OrderedSet <SAMLObject> members = new OrderedSet<SAMLObject>();

        if (conditions != null) {
            members.add(conditions);
        }
               
        if (advice != null) {
            members.add(advice);
        }
        
        if (orderedList.size() != 0) {
            members.addAll(orderedList);
        }

        return new UnmodifiableOrderedSet<SAMLObject>(members);
    }

}
