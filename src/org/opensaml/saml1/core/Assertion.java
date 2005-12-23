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

package org.opensaml.saml1.core;

import java.util.Date;
import java.util.Set;

import javax.xml.namespace.QName;

import org.opensaml.common.IllegalAddException;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.util.UnmodifiableOrderedSet;
import org.opensaml.common.util.xml.XMLConstants;

/**
 * This interface defines how the object representing a SAML 1 <code> Assertion </code> element behaves. 
 */
public interface Assertion extends SAMLObject{

    /** Element name, no namespace. */
   
    public final static String LOCAL_NAME = "Assertion";
    
    /** QName for this element. */
    
    public final static QName QNAME = new QName(XMLConstants.SAML1_NS, LOCAL_NAME, XMLConstants.SAML1_PREFIX);

    /** Name for the attribute which defines Major Version (attribute's value must be 1) */
    public final static String MAJORVERSION_ATTRIB_NAME = "MajorVersion";
    
    /** Name for the attribute which defines Minor Version. */
    public final static String MINORVERSION_ATTRIB_NAME = "MinorVersion";
    
    /** Name for the attribute which defines Assertion ID */
    public final static String ASSERTIONID_ATTRIB_NAME = "AssertionID";
    
    /** Name for the attribute which defines Issuer */
    public final static String ISSUER_ATTRIB_NAME = "Issuer";
    
    /** Name for the attribute which defines the issue instant */
    public final static String ISSUEINSTANT_ATTRIB_NAME = "IssueInstant";
    
    /* attributes */
    
    /** 
     * Get the MinorVersion attribute.
     *  
     * @return The stored MinorVersion 
     */
    public int getMinorVersion();

    /** 
     * Set the MinorVersion (attribute).
     * 
     * @param version the value to set
     */
    public void setMinorVersion(int version);
    
    /** 
     * Get the AssertionID value (from the attribute).
     *
     * @return The AssertionID
     */
    public String getId();
    
    /** 
     * Set the AssertionID (attribute).
     *   
     * @param Id the Id to set
     */
    public void setId(String Id);
    
    /** 
     * Get the Issuer (which is an attribute) .
     * 
     * @return the Issuer
     */
    public String getIssuer();
    
    /** 
     * Set the Issuer (attribute).
     *
     * @param Issuer the value to set
     */
    public void setIssuer(String Issuer);
    
    /** 
     * Get the IssueInstant (attribute).
     * 
     * @return the Issue Instant (as a Date) 
     */
    public Date getIssueInstant();
    
    /**
     *  Set the IssueInstance (attribute). 
     *
     * @param issueInstant the issue instant value to set
     */
    public void setIssueInstant(Date issueInstant);
    
    /* Singleton Elements */
    
    /** 
     * Return the (singleton) Object, representing the <code> Conditions </code> sub element.
     * 
     * @return the Conditions object.
     */
    public Conditions getConditions();
    
    /**
     *  Set the Object representing the <code> Conditions </code> Sub element. 
     * 
     * @param conditions the condition to set
     *  
     * @throws IllegalAddException if the condition has already been set into another object
     */
    public void setConditions(Conditions conditions) throws IllegalAddException;

    /** 
     * advice is a (singleton) Object, representing the <code> Advice </code> sub element 
     * 
     * @return the advice object in this assertion
     */
    public Advice getAdvice();

    /** 
     * Set the Object representing the <code> Advice </code> sub element.
     *
     * @param advice the object to set
     * 
     * @throws IllegalAddException if the object has already been put into another SAMLObject
     */
    public void setAdvice(Advice advice) throws IllegalAddException;
    
    /* Mutiple Elements */

    /**
     * Return the Set representing all the <code> Statement </code> sub elements.
     *  
     * There can be 1 or more Statement subelements and so these are
     * stored in a Set
     * 
     * @return the set representing all the statements
     */
    public UnmodifiableOrderedSet<Statement> getStatements();
    
    /** 
     * Add a single <code> statement </code> to the set (if appropriate). 
     * 
     * @param statement what to add
     * 
     * @throws IllegalAddException if the object has already been put into an SAMLObject
     */
    public void addStatement(Statement statement) throws IllegalAddException;
    
    /** 
     * Remove a single <code> Statement </code> from the set.
     *
     * @param statement what to remove
     */
    public void removeStatement(Statement statement);
    
    /** 
     * Remove the presented statements from the set.
     *
     * @param statements which statements to remove
     */
    public void removeStatements(Set<Statement> statements);
    
    /** 
     * Remove the all the statements from the set.
     */

    public void removeAllStatements();
    
    /**
     * Return the set representing all the <code> SubjectStatement </code> sub elements.
     * 
     * @return all the subjectStatements
     */
    public UnmodifiableOrderedSet<SubjectStatement> getSubjectStatements();
    
    /** 
     * Add a single <code> SubjectStatement </code> to the set (if appropriate).
     *
     * @param subjectStatement the subjectStatement to add
     * 
     * @throws IllegalAddException if the subjectStatement is already in another SAML statement
     */
    public void addSubjectStatement(SubjectStatement subjectStatement) throws IllegalAddException;
    
    /** 
     * Remove a single <code> SubjectStatement </code> from the set    
     * 
     * @param subjectStatement  the element to remove,
     */
    public void removeSubjectStatement(SubjectStatement subjectStatement);
    
    /** 
     * Remove the presented SubjectStatements from the set.
     *
     * @param subjectStatements  the subjectStatements to remove
     */
    public void removeSubjectStatements(Set<SubjectStatement> subjectStatements);
    
    /** Remove the all the SubjectStatements from the set.
     * 
     */
    public void removeAllSubjectStatements();
    
    /**
     * Return the set representing all the <code> AuthenticationStatement </code> sub elements.
     * 
     * @return all the AuthenticationStatements
     */
    public UnmodifiableOrderedSet<AuthenticationStatement> getAuthenticationStatements();
    
    /** 
     * Add a single <code> AuthenticationStatement </code> to the set (if appropriate).
     *
     * @param authenticationStatement the authenticationStatement to add.
     * 
     * @throws IllegalAddException if the authenticationStatement is already in another SAML statement.
     */
    public void addAuthenticationStatement(AuthenticationStatement authenticationStatement) throws IllegalAddException;
    
    /** 
     * Remove a single <code> AuthenticationStatement </code> from the set.    
     * 
     * @param authenticationStatement the element to remove.
     */
    public void removeAuthenticationStatement(AuthenticationStatement authenticationStatement);
    
    /** 
     * Remove the presented <code> AuthenticationStatements </code> from the set/
     *
     * @param authenticationStatements what to remove.
     */
    public void removeAuthenticationStatements(Set<AuthenticationStatement> authenticationStatements);
    
    /** Remove the all the authenticationStatements from the set.  */

    public void removeAllAuthenticationStatements();

    /**
     * Return the set representing all the <code> AuthorizationStatement </code> sub elements.
     * 
     * @return all the authorizationDecisionStatements.
     */
    public UnmodifiableOrderedSet<AuthorizationDecisionStatement> getAuthorizationDecisionStatements();
    
    /** 
     * Add a single <code> AuthorizationDecisionStatement </code> to the set (if appropriate).
     *
     * @param authorizationDecisionStatement what to add,
     * 
     * @throws IllegalAddException if the authorizationDecisionStatement is already in another SAML statement
     */
    public void addAuthorizationDecisionStatement(AuthorizationDecisionStatement authorizationDecisionStatement) throws IllegalAddException;
    
    /** 
     * Remove a single <code> AuthorizationDecisionStatement </code> from the set.
     * 
     * @param authorizationDecisionStatement the element to remove.
     */
    public void removeAuthorizationDecisionStatement(AuthorizationDecisionStatement authorizationDecisionStatement);
    
    /** 
     * Remove the presented authorizationDecisionStatements from the set.
     *
     * @param authorizationDecisionStatements the statements to remove.
     */
    public void removeAuthorizationDecisionStatement(Set<AuthorizationDecisionStatement> authorizationDecisionStatements);
    
    /** Remove the all the authorizationDecisionStatements from the set.  */

    public void removeAllAuthorizationDecisionStatements();
    
    /**
     * Add a single <code> AttributeStatement </code> to the set (if appropriate).
     * 
     * @return all the attributeStatements
     */
    public UnmodifiableOrderedSet<AttributeStatement> getAttributeStatements();
    
    /** 
     * Add a single <code> AttributeStatement </code> to the set (if appropriate).
     *
     * @param attributeStatement the attributeStatement to add
     * 
     * @throws IllegalAddException if the attributeStatement is already in another SAML statement
     */
    public void addAttributeStatement(AttributeStatement attributeStatement) throws IllegalAddException;
    
    /** 
     * Remove a single <code>AttributeStatement</code> from the set.  
     * 
     * @param attributeStatement the element to remove.
     */
    public void removeAttributeStatement(AttributeStatement attributeStatement);
    
    /** 
     * Remove the presented <code> AttributeStatement </code>from the set.
     *
     * @param attributeStatements what to remove.
     */
    public void removeAttributeStatements(Set<AttributeStatement> attributeStatements);
    
    /** Remove the all the attributeStatements from the set.  */

    public void removeAllAttributeStatements();
      
}
