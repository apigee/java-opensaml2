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
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.impl.AbstractSignableSAMLObject;
import org.opensaml.common.impl.TypeNameIndexedSAMLObjectList;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Advice;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.AttributeStatement;
import org.opensaml.saml1.core.AuthenticationStatement;
import org.opensaml.saml1.core.AuthorizationDecisionStatement;
import org.opensaml.saml1.core.Conditions;
import org.opensaml.saml1.core.Statement;
import org.opensaml.saml1.core.SubjectStatement;
import org.opensaml.xml.IllegalAddException;

/**
 * This class implements the SAML 1 <code> Assertion </code> statement.
 */
public class AssertionImpl extends AbstractSignableSAMLObject implements Assertion {

    /** Object version of the <code> MinorVersion </code> attribute. */
    private int minorVersion;

    /** Object version of the <code> Issuer </code> attribute. */
    private String issuer;

    /** Object version of the <code> IssueInstant </code> attribute. */
    private GregorianCalendar issueInstant;

    /** (Possibly null) Singleton object version of the <code> Conditions </code> element. */
    private Conditions conditions;

    /** (Possibly null) Singleton object version of the <code> Advice </code> element. */
    private Advice advice;

    /** Object representnation of all the <code> Statement <\code> elements. */
    private TypeNameIndexedSAMLObjectList<Statement> statements = new TypeNameIndexedSAMLObjectList<Statement>();

    /**
     * Constructor
     */
    public AssertionImpl() {
        super(SAMLConstants.SAML1_NS, Assertion.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML1_PREFIX);
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
     * Since we know that we are SAML1 we can also set the SAML version if we can
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
    public GregorianCalendar getIssueInstant() {
        return this.issueInstant;
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#setIssueInstant(java.util.Date)
     * 
     * There is (as yet) no helper function for Date values so all the logic is in here.
     */
    public void setIssueInstant(GregorianCalendar issueInstant) {
        this.issueInstant = prepareForAssignment(this.issueInstant, issueInstant);
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
    public List<Statement> getStatements() {
        if (statements.size() == 0) {
            return null;
        } 
        return Collections.unmodifiableList(statements);
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#getStatements(javax.xml.namespace.QName)
     */
    public List<Statement> getStatements(QName typeOrName) {
        
        List <Statement> list = statements.get(typeOrName);
        
        if (list == null || list.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(list);
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#addStatement(org.opensaml.saml1.core.Statement)
     */
    public void addStatement(Statement statement) throws IllegalAddException {
        addXMLObject(this.statements, statement);
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#removeStatement(org.opensaml.saml1.core.Statement)
     */
    public void removeStatement(Statement statement) {
        removeXMLObject(this.statements, statement);
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#removeStatements(java.util.Set)
     */
    public void removeStatements(Collection<Statement> statements) {
        if (statements == null) {
            return;
        }
        removeXMLObjects(this.statements, statements);
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#removeAllStatements()
     */
    public void removeAllStatements() {
        for (Statement statement : statements) {
            removeStatement(statement);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#removeAllStatements(javax.xml.namespace.QName)
     */
    public void removeAllStatements(QName typeOrName) {
        for (Statement statement : statements.get(typeOrName)) {
            removeStatement(statement);
        }
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#getSubjectStatements()
     */
    public List<Statement> getSubjectStatements() {
        QName statementQName = new QName(SAMLConstants.SAML1_NS, SubjectStatement.LOCAL_NAME);
        return getStatements(statementQName);
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#getAuthenticationStatements()
     */
    public List<Statement> getAuthenticationStatements() {
        QName statementQName = new QName(SAMLConstants.SAML1_NS, AuthenticationStatement.LOCAL_NAME);

        return getStatements(statementQName);
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#getAuthorizationDecisionStatements()
     */
    public List<Statement> getAuthorizationDecisionStatements() {
        QName statementQName = new QName(SAMLConstants.SAML1_NS, AuthorizationDecisionStatement.LOCAL_NAME);
        return getStatements(statementQName);
    }
    
    /*
     * @see org.opensaml.saml1.core.Assertion#getAttributeStatements()
     */
    public List<Statement> getAttributeStatements() {
        QName statementQName = new QName(SAMLConstants.SAML1_NS, AttributeStatement.LOCAL_NAME);
        return getStatements(statementQName);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {

        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();

        if (conditions != null) {
            children.add(conditions);
        }

        if (advice != null) {
            children.add(advice);
        }

        children.addAll(statements);
        
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}