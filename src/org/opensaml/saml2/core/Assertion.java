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

package org.opensaml.saml2.core;

import java.util.List;

import org.joda.time.DateTime;
import org.opensaml.common.SAMLObject;
import org.opensaml.xml.signature.SignableXMLObject;

/**
 * SAML 2.0 Core Assertion
 */
public interface Assertion extends SignableXMLObject, SAMLObject{

    /** Element local name */
    public final static String LOCAL_NAME = "Assertion";

    /** IssueInstant attribute name */
    public final static String ISSUE_INSTANT_ATTRIB_NAME = "IssueInstant";

    /** ID attribute name */
    public final static String ID_ATTRIB_NAME = "ID";

    /*
     * Gets the issue instance of this assertion.
     * 
     * @return the issue instance of this assertion
     */
    public DateTime getIssueInstant();

    /*
     * Sets the issue instance of this assertion.
     * 
     * @param newIssueInstance the issue instance of this assertion
     */
    public void setIssueInstant(DateTime newIssueInstance);

    /*
     * Sets the ID of this assertion.
     * 
     * @return the ID of this assertion
     */
    public String getID();

    /*
     * Sets the ID of this assertion.
     * 
     * @param newID the ID of this assertion
     */
    public void setID(String newID);

    /*
     * Gets the Issuer of this assertion.
     * 
     * @return the Issuer of this assertion
     */
    public Issuer getIssuer();

    /*
     * Sets the Issuer of this assertion.
     * 
     * @param newIssuer the Issuer of this assertion
     */
    public void setIssuer(Issuer newIssuer);

    /*
     * Gets the Subject of this assertion.
     * 
     * @return the Subject of this assertion
     */
    public Subject getSubject();

    /*
     * Sets the Subject of this assertion.
     * 
     * @param newSubject the Subject of this assertion
     */
    public void setSubject(Subject newSubject);

    /*
     * Gets the Conditions placed on this assertion.
     * 
     * @return the Conditions placed on this assertion
     */
    public Conditions getConditions();

    /*
     * Sets the Conditions placed on this assertion.
     * 
     * @param newConditions the Conditions placed on this assertion
     */
    public void setConditions(Conditions newConditions);

    /*
     * Gets the Advice for this assertion.
     * 
     * @return the Advice for this assertion
     */
    public Advice getAdvice();

    /*
     * Sets the Advice for this assertion.
     * 
     * @param newAdvice the Advice for this assertion
     */
    public void setAdvice(Advice newAdvice);

    /*
     * Gets the list of statements attached to this assertion.
     * 
     * @return the list of statements attached to this assertion
     */
    public List<Statement> getStatements();

    /*
     * Gets the list of AuthnStatements attached to this assertion.
     * 
     * @return the list of AuthnStatements attached to this assertion
     */
    public List<AuthnStatement> getAuthnStatements();

    /*
     * Gets the list of AuthzDecisionStatements attached to this assertion.
     * 
     * @return the list of AuthzDecisionStatements attached to this assertion
     */
    public List<AuthzDecisionStatement> getAuthzDecisionStatements();

    /*
     * Gets the list of AttributeStatement attached to this assertion.
     * 
     * @return the list of AttributeStatement attached to this assertion
     */
    public List<AttributeStatement> getAttributeStatement();
}