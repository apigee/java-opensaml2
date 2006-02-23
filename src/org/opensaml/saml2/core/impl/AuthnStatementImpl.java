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

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.impl.AbstractSAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.AuthnContext;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.SubjectLocality;

/**
 * A concrete implementation of {@link org.opensaml.saml2.core.AuthnStatement}.
 */
public class AuthnStatementImpl extends AbstractSAMLObject implements AuthnStatement {

    /** Subject Locality of the Authentication Statement */
    private SubjectLocality subjectLocality;

    /** Authentication Context of the Authentication Statement */
    private AuthnContext authnContext;

    /** Time of the authentication */
    private DateTime authnInstant;

    /** Index of the session */
    private String sessionIndex;

    /** Time at which the session ends */
    private DateTime sessionNotOnOrAfter;

    /** Constructor */
    public AuthnStatementImpl() {
        super(SAMLConstants.SAML20_NS, AuthnStatement.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML20_PREFIX);
    }

    /**
     * @see org.opensaml.saml2.core.AuthnStatement#getSubjectLocality()
     */
    public SubjectLocality getSubjectLocality() {
        return subjectLocality;
    }

    /**
     * @see org.opensaml.saml2.core.AuthnStatement#setSubjectLocality(org.opensaml.saml2.core.SubjectLocality)
     */
    public void setSubjectLocality(SubjectLocality newSubjectLocality) {
        this.subjectLocality = prepareForAssignment(this.subjectLocality, newSubjectLocality);
    }

    /**
     * @see org.opensaml.saml2.core.AuthnStatement#getAuthnContext()
     */
    public AuthnContext getAuthnContext() {
        return authnContext;
    }

    /**
     * @see org.opensaml.saml2.core.AuthnStatement#setAuthnContext(org.opensaml.saml2.core.AuthnContext)
     */
    public void setAuthnContext(AuthnContext newAuthnContext) {
        this.authnContext = prepareForAssignment(this.authnContext, newAuthnContext);
    }

    /**
     * @see org.opensaml.saml2.core.AuthnStatement#getAuthnInstant()
     */
    public DateTime getAuthnInstant() {
        return authnInstant;
    }

    /**
     * @see org.opensaml.saml2.core.AuthnStatement#setAuthnInstant(org.joda.time.DateTime)
     */
    public void setAuthnInstant(DateTime newAuthnInstant) {
        this.authnInstant = prepareForAssignment(this.authnInstant, newAuthnInstant.withZone(DateTimeZone.UTC));
    }

    /**
     * @see org.opensaml.saml2.core.AuthnStatement#getSessionIndex()
     */
    public String getSessionIndex() {
        return sessionIndex;
    }

    /**
     * @see org.opensaml.saml2.core.AuthnStatement#setSessionIndex(java.lang.String)
     */
    public void setSessionIndex(String newSessionIndex) {
        this.sessionIndex = prepareForAssignment(this.sessionIndex, newSessionIndex);
    }

    /**
     * @see org.opensaml.saml2.core.AuthnStatement#getSessionNotOnOrAfter()
     */
    public DateTime getSessionNotOnOrAfter() {
        return sessionNotOnOrAfter;
    }

    /**
     * @see org.opensaml.saml2.core.AuthnStatement#setSessionNotOnOrAfter(org.joda.time.DateTime)
     */
    public void setSessionNotOnOrAfter(DateTime newSessionNotOnOrAfter) {
        this.sessionNotOnOrAfter = prepareForAssignment(this.sessionNotOnOrAfter, newSessionNotOnOrAfter
                .withZone(DateTimeZone.UTC));
    }

    /**
     * @see org.opensaml.common.SAMLObject#getOrderedChildren()
     */
    public List<SAMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();

        children.add(subjectLocality);
        children.add(authnContext);

        return Collections.unmodifiableList(children);
    }
}