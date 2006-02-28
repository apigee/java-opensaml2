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

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.opensaml.saml1.core.AuthenticationStatement;
import org.opensaml.saml1.core.AuthorityBinding;
import org.opensaml.saml1.core.SubjectLocality;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * A Concrete implementation of the {@link org.opensaml.saml1.core.AuthenticationStatement} Interface
 */
public class AuthenticationStatementImpl extends SubjectStatementImpl implements AuthenticationStatement {

    /** Contains the AuthenticationMethod attribute contents */
    private String authenticationMethod;

    /** Contains the AuthenticationMethod attribute contents */
    private DateTime authenticationInstant;

    /** Contains the SubjectLocality subelement */
    private SubjectLocality subjectLocality;

    /** Contains the AuthorityBinding subelements */
    private final List<AuthorityBinding> authorityBindings;

    /**
     * Constructor
     */
    protected AuthenticationStatementImpl() {
        super(AuthenticationStatement.LOCAL_NAME);

        authorityBindings = new XMLObjectChildrenList<AuthorityBinding>(this);
    }

    //
    // Attributes
    //

    /*
     * @see org.opensaml.saml1.core.AuthenticationStatement#getAuthenticationMethod()
     */
    public String getAuthenticationMethod() {
        return authenticationMethod;
    }

    /*
     * @see org.opensaml.saml1.core.AuthenticationStatement#setAuthenticationMethod(java.lang.String)
     */
    public void setAuthenticationMethod(String authenticationMethod) {
        this.authenticationMethod = prepareForAssignment(this.authenticationMethod, authenticationMethod);
    }

    /*
     * @see org.opensaml.saml1.core.AuthenticationStatement#getAuthenticationInstant()
     */
    public DateTime getAuthenticationInstant() {
        return authenticationInstant;
    }

    /*
     * @see org.opensaml.saml1.core.AuthenticationStatement#setAuthenticationInstant(java.util.GregorianCalendar)
     */
    public void setAuthenticationInstant(DateTime authenticationInstant) {
        this.authenticationInstant = prepareForAssignment(this.authenticationInstant, authenticationInstant
                .withZone(DateTimeZone.UTC));
    }

    //
    // Elements
    //

    /*
     * @see org.opensaml.saml1.core.AuthenticationStatement#getSubjectLocality()
     */
    public SubjectLocality getSubjectLocality() {
        return subjectLocality;
    }

    /*
     * @see org.opensaml.saml1.core.AuthenticationStatement#setSubjectLocality(org.opensaml.saml1.core.SubjectLocality)
     */
    public void setSubjectLocality(SubjectLocality subjectLocality) throws IllegalArgumentException {
        this.subjectLocality = prepareForAssignment(this.subjectLocality, subjectLocality);
    }

    /*
     * @see org.opensaml.saml1.core.AuthenticationStatement#getAuthorityBindings()
     */
    public List<AuthorityBinding> getAuthorityBindings() {
        return authorityBindings;
    }

    /*
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<XMLObject> getOrderedChildren() {
        List<XMLObject> list = new ArrayList<XMLObject>(authorityBindings.size() + 2);

        if (getSubject() != null) {
            list.add(getSubject());
        }

        if (subjectLocality != null) {
            list.add(subjectLocality);
        }

        list.addAll(authorityBindings);

        if (list.size() == 0) {
            return null;
        }

        return Collections.unmodifiableList(list);
    }
}