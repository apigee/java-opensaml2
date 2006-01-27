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

import java.util.GregorianCalendar;
import java.util.List;

import org.opensaml.common.SAMLObject;

/**
 * This interface defines how the object representing a SAML1 <code> AuthenticationStatment </code> element behaves.
 */
public interface AuthenticationStatement extends SAMLObject, SubjectStatement {

    /** Element name, no namespace. */
    public final static String LOCAL_NAME = "AuthenticationStatement";

    /** Name of the AuthenticationMethod attribute */
    public final static String AUTHENTICATIONMETHOD_ATTRIB_NAME = "AuthenticationMethod";

    /** Name of the AuthenticationInstant attribute */
    public final static String AUTHENTICATIONINSTANT_ATTRIB_NAME = "AuthenticationInstant";

    /** Return the contents of the AuthenticationMethod attribute */
    public String getAuthenticationMethod();

    /** Set the contents of the AuthenticationMethod attribute */
    public void setAuthenticationMethod(String authenticationMethod);

    /** Return the contents of the AuthenticationInstant attribute */
    public GregorianCalendar getAuthenticationInstant();

    /** Set the contents of the AuthenticationInstant attribute */
    public void setAuthenticationInstant(GregorianCalendar authenticationInstant);

    /** Set the (single) SubjectLocality child element */
    public SubjectLocality getSubjectLocality();

    /** Get the (single) SubjectLocality child element 
     * @throws IllegalArgumentException */
    public void setSubjectLocality(SubjectLocality subjectLocality) throws IllegalArgumentException;

    /** return all the AuthorityBinding subelement */
    public List<AuthorityBinding> getAuthorityBindings();

}
