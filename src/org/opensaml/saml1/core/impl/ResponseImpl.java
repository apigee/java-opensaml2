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
import java.util.Collections;
import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.Response;
import org.opensaml.saml1.core.Status;

/**
 * Implementation of the {@link org.opensaml.saml1.core.Response} Object
 */
public class ResponseImpl extends ResponseAbstractTypeImpl implements Response {

    /** Status associated with this element */
    private Status status = null;

    /** Assertion associated with this element */
    private Assertion assertion = null;

    /**
     * Constructor
     * 
     */
    protected ResponseImpl() {
        super(SAMLConstants.SAML1P_NS, Response.LOCAL_NAME);
        setElementNamespacePrefix(SAMLConstants.SAML1P_PREFIX);
    }

    /*
     * @see org.opensaml.saml1.core.Response#getStatus()
     */
    public Status getStatus() {
        return status;
    }

    /*
     * @see org.opensaml.saml1.core.Response#getStatus(org.opensaml.saml1.core.Status)
     */
    public void setStatus(Status status) throws IllegalArgumentException {
        this.status = prepareForAssignment(this.status, status);
    }

    /*
     * @see org.opensaml.saml1.core.Response#getAssertions()
     */
    public Assertion getAssertion() {
        return assertion;
    }

    /*
     * @see org.opensaml.saml1.core.Response#addAssertion(org.opensaml.saml1.core.Assertion)
     */
    public void setAssertion(Assertion assertion) throws IllegalArgumentException {
        this.assertion = prepareForAssignment(this.assertion, assertion);
    }

    public List<SAMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>(2);

        if (assertion != null) {
            children.add(assertion);
        }

        if (status != null) {
            children.add(status);
        }

        if (children.size() == 0) {
            return null;
        }
        
        return Collections.unmodifiableList(children);
    }
}
