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

import javax.xml.namespace.QName;

import org.joda.time.DateTime;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.impl.AbstractSignableSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Advice;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.AttributeStatement;
import org.opensaml.saml1.core.AuthenticationStatement;
import org.opensaml.saml1.core.AuthorizationDecisionStatement;
import org.opensaml.saml1.core.Conditions;
import org.opensaml.saml1.core.Statement;
import org.opensaml.saml1.core.SubjectStatement;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.IndexedXMLObjectChildrenList;

/**
 * This class implements the SAML 1 <code> Assertion </code> statement.
 */
public class AssertionImpl extends AbstractSignableSAMLObject implements Assertion {

    /** The <code> AssertionID </code> attrribute */
    private String id;
    
    /** SAML version of this assertion */
    private SAMLVersion version;
    
    /** Object version of the <code> Issuer </code> attribute. */
    private String issuer;

    /** Object version of the <code> IssueInstant </code> attribute. */
    private DateTime issueInstant;

    /** (Possibly null) Singleton object version of the <code> Conditions </code> element. */
    private Conditions conditions;

    /** (Possibly null) Singleton object version of the <code> Advice </code> element. */
    private Advice advice;

    /** Object representnation of all the <code> Statement <\code> elements. */
    private final IndexedXMLObjectChildrenList<Statement> statements;
    
    /**
     * Constructor
     * 
     * @param namespaceURI the namespace the element is in
     * @param elementLocalName the local name of the XML element this Object represents
     * @param namespacePrefix the prefix for the given namespace
     */
    protected AssertionImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        statements = new IndexedXMLObjectChildrenList<Statement>(this);
        version = SAMLVersion.VERSION_11;
    }
    
    /*
     * @see org.opensaml.saml1.core.Assertion#getMajorVersion()
     */
    public int getMajorVersion(){
        return version.getMajorVersion();
    }
    
    /*
     * @see org.opensaml.saml1.core.Assertion#removeSubjectStatements(java.util.Set)
     */
    public int getMinorVersion() {
        return version.getMinorVersion();
    }
    
    /*
     * @see org.opensaml.saml1.core.Assertion#setVersion(org.opensaml.common.SAMLVersion)
     */
    public void setVersion(SAMLVersion newVersion){
        version = prepareForAssignment(version, newVersion);
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#getID()
     */
    public String getID() {
        return id;
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#setID(java.lang.String)
     */
    public void setID(String id) {
        this.id = prepareForAssignment(this.id, id);   
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
    public DateTime getIssueInstant() {
        return this.issueInstant;
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#setIssueInstant(java.util.Date)
     * 
     * There is (as yet) no helper function for Date values so all the logic is in here.
     */
    public void setIssueInstant(DateTime issueInstant) {
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
    public void setConditions(Conditions conditions) throws IllegalArgumentException {
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
    public void setAdvice(Advice advice) throws IllegalArgumentException {
        this.advice = prepareForAssignment(this.advice, advice);
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#getStatements()
     */
    public List<Statement> getStatements() {
        return statements;
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#getStatements(javax.xml.namespace.QName)
     */
    public List<Statement> getStatements(QName typeOrName) {
        return (List<Statement>) statements.subList(typeOrName);
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#getSubjectStatements()
     */
    public List<SubjectStatement> getSubjectStatements() {
        QName statementQName = new QName(SAMLConstants.SAML1_NS, SubjectStatement.DEFAULT_ELEMENT_LOCAL_NAME);
        return (List<SubjectStatement>) statements.subList(statementQName);
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#getAuthenticationStatements()
     */
    public List<AuthenticationStatement> getAuthenticationStatements() {
        QName statementQName = new QName(SAMLConstants.SAML1_NS, AuthenticationStatement.DEFAULT_ELEMENT_LOCAL_NAME);
        return (List<AuthenticationStatement>) statements.subList(statementQName);
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#getAuthorizationDecisionStatements()
     */
    public List<AuthorizationDecisionStatement> getAuthorizationDecisionStatements() {
        QName statementQName = new QName(SAMLConstants.SAML1_NS, AuthorizationDecisionStatement.LOCAL_NAME);
        return (List<AuthorizationDecisionStatement>) statements.subList(statementQName);
    }

    /*
     * @see org.opensaml.saml1.core.Assertion#getAttributeStatements()
     */
    public List<AttributeStatement> getAttributeStatements() {
        QName statementQName = new QName(SAMLConstants.SAML1_NS, AttributeStatement.DEFAULT_ELEMENT_LOCAL_NAME);
        return (List<AttributeStatement>) statements.subList(statementQName);
    }

    /*
     * @see org.opensaml.xml.XMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {

        ArrayList<XMLObject> children = new ArrayList<XMLObject>();

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